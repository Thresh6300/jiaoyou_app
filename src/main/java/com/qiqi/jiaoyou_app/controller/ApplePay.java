package com.qiqi.jiaoyou_app.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.controller.award.Award;
import com.qiqi.jiaoyou_app.mapper.MemberMapper;
import com.qiqi.jiaoyou_app.mapper.PlatformParameterSettingMapper;
import com.qiqi.jiaoyou_app.pay.util.IosVerifyUtil;
import com.qiqi.jiaoyou_app.pojo.Diamonds;
import com.qiqi.jiaoyou_app.pojo.Member;
import com.qiqi.jiaoyou_app.pojo.MemberAssets;
import com.qiqi.jiaoyou_app.pojo.RechargeRecord;
import com.qiqi.jiaoyou_app.serviceImpl.DiamondsServiceImpl;
import com.qiqi.jiaoyou_app.serviceImpl.MemberAssetsServiceImpl;
import com.qiqi.jiaoyou_app.serviceImpl.MemberServiceImpl;
import com.qiqi.jiaoyou_app.serviceImpl.RechargeRecordServiceImpl;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import com.qiqi.jiaoyou_app.vo.ApplePayVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@Api(tags = {"苹果内购验证"})
@RestController
@RequestMapping("/jiaoyou_app")
public class ApplePay {

    @Autowired
    private MemberServiceImpl memberService;
    @Autowired
    private DiamondsServiceImpl diamondsService;
    @Autowired
    private MemberAssetsServiceImpl memberAssetsService;
    @Autowired
    private RechargeRecordServiceImpl rechargeRecordService;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private PlatformParameterSettingMapper platformParameterSettingMapper;

    /**
     * 苹果内购校验
     *
     * @return
     */
    @ApiOperation(value = "苹果内购验证(已完成)")
    @PostMapping("/iospay")
    @DynamicParameters(name = "ApplePayVo", properties = {
            @DynamicParameter(name = "transactionId", value = "苹果内购交易ID", required = true, dataTypeClass = String.class),
            @DynamicParameter(name = "payload", value = "校验体（base64字符串）", required = true, dataTypeClass = String.class),
            @DynamicParameter(name = "productId", value = "产品ID", required = false, dataTypeClass = String.class),
            @DynamicParameter(name = "memberId", value = "会员ID", required = false, dataTypeClass = Integer.class),
            @DynamicParameter(name = "type", value = "1:购买黑卡 2：开通会员 3：购买金钻", required = true, dataTypeClass = Integer.class),
    })
    public ResultUtils iosPay(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject) {
        ApplePayVo applePayVo = JSON.toJavaObject(jsonObject, ApplePayVo.class);
        String transactionId = applePayVo.getTransactionId();
        String payload = applePayVo.getPayload();
        Integer type = applePayVo.getType();
        Integer memberId = null;
        try {
            memberId = applePayVo.getMemberId();
        } catch (Exception e) {

        }
        System.err.println("苹果内购校验开始，交易ID：" + transactionId + " base64校验体：" + payload);
        ResultUtils resultUtils = new ResultUtils();


        //线上环境验证
        String verifyResult = IosVerifyUtil.buyAppVerify(payload, 1);
        if (verifyResult == null) {
            resultUtils.setStatus(Constant.STATUS_FAILED);
            resultUtils.setMessage("苹果验证失败，返回数据为空");
            return resultUtils;
        } else {
            JSONObject appleReturn = JSONObject.parseObject(verifyResult);
            String states = appleReturn.getString("status");
            //无数据则沙箱环境验证
            if ("21007".equals(states)) {
                verifyResult = IosVerifyUtil.buyAppVerify(payload, 0);
                appleReturn = JSONObject.parseObject(verifyResult);
                states = appleReturn.getString("status");
            }
            // 前端所提供的收据是有效的    验证成功
            if ("0".equals(states)) {
                String receipt = appleReturn.getString("receipt");
                JSONObject returnJson = JSONObject.parseObject(receipt);
                String inApp = returnJson.getString("in_app");
                List<HashMap> inApps = JSONObject.parseArray(inApp, HashMap.class);
                if (!CollectionUtils.isEmpty(inApps)) {
                    ArrayList<String> transactionIds = new ArrayList<String>();
                    Map<String, String> map = new HashMap<>();
                    for (HashMap app : inApps) {
                        transactionIds.add((String) app.get("transaction_id"));
                        map.put((String) app.get("transaction_id"), (String) app.get("product_id"));
                    }
                    //交易列表包含当前交易，则认为交易成功
                    if (transactionIds.contains(transactionId)) {
                        String productId = map.get(transactionId);
                        Diamonds diamonds = diamondsService.selectList(new EntityWrapper<Diamonds>().eq("diamonds_product_id", productId).eq("diamonds_terminal",1)).get(0);
                        RechargeRecord rechargeRecord = new RechargeRecord();
                        //处理业务逻辑
                        boolean status = false;
                        if (type == 1) {
                            if (memberId == null) {

                            } else {
                                MemberAssets memberAssets = memberAssetsService.selectList(new EntityWrapper<MemberAssets>().eq("memberId", memberId)).get(0);
                                Member member = memberService.selectById(memberId);
                                if (member.getIsvip() == 2) {
                                    member.setIsvip(1);
                                    member.setMemberExpirationDate(getFourAfter(new Date(), 36));
                                } else {
                                    member.setMemberExpirationDate(getFourAfter(member.getMemberExpirationDate(), 36));
                                }
                                status = memberService.updateById(member);
                                //增加会员经验值
                                //增加会员钻石数
                                memberAssets.setMemberDiamondsizeOfGold(memberAssets.getMemberDiamondsizeOfGold() + 28888);
                                memberAssets.setMeberExperienceSize(memberAssets.getMeberExperienceSize() + diamonds.getDiamondsPrice().intValue());
                                memberAssetsService.updateById(memberAssets);
                                rechargeRecord.setMemberId(member.getId());
                                rechargeRecord.setCurrency(2);
                                rechargeRecord.setRunSize(28888L);
                                rechargeRecord.setSurplus(memberAssets.getMemberDiamondsizeOfGold());
                                rechargeRecord.setName("续费黑卡");
                                rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
                                rechargeRecord.setType(1);
                                rechargeRecord.setMode(3);
                                rechargeRecordService.insert(rechargeRecord);
                            }
                        } else if (type == 2) {
                            MemberAssets memberAssets = memberAssetsService.selectList(new EntityWrapper<MemberAssets>().eq("memberId", memberId)).get(0);
                            Member member = memberService.selectById(memberId);
                            if ("月卡".equals(diamonds.getDiamondsName())) {
                                if (member.getIsvip() == 2) {
                                    member.setIsvip(1);
                                    member.setMemberExpirationDate(getFourAfter(new Date(), 1));
                                } else {
                                    member.setMemberExpirationDate(getFourAfter(member.getMemberExpirationDate(), 1));
                                }
                                memberAssets.setMemberDiamondsizeOfGold(memberAssets.getMemberDiamondsizeOfGold() + 688);
                                rechargeRecord.setRunSize(688L);
                                rechargeRecord.setSurplus(memberAssets.getMemberDiamondsizeOfGold());
                                rechargeRecord.setName("购买会员月卡");
                              } else if ("季卡".equals(diamonds.getDiamondsName())) {
                                if (member.getIsvip() == 2) {
                                    member.setIsvip(1);
                                    member.setMemberExpirationDate(getFourAfter(new Date(), 3));
                                } else {
                                    member.setMemberExpirationDate(getFourAfter(member.getMemberExpirationDate(), 3));
                                }
                                memberAssets.setMemberDiamondsizeOfGold(memberAssets.getMemberDiamondsizeOfGold() + 1888);
                                rechargeRecord.setRunSize(1888L);
                                rechargeRecord.setSurplus(memberAssets.getMemberDiamondsizeOfGold());
                                rechargeRecord.setName("购买会员季卡");
                            } else if ("年卡".equals(diamonds.getDiamondsName())) {
                                if (member.getIsvip() == 2) {
                                    member.setIsvip(1);
                                    member.setMemberExpirationDate(getFourAfter(new Date(), 12));
                                } else {
                                    member.setMemberExpirationDate(getFourAfter(member.getMemberExpirationDate(), 12));
                                }
                                memberAssets.setMemberDiamondsizeOfGold(memberAssets.getMemberDiamondsizeOfGold() + 7888);
                                rechargeRecord.setRunSize(7888L);
                                rechargeRecord.setSurplus(memberAssets.getMemberDiamondsizeOfGold());
                                rechargeRecord.setName("购买会员年卡");
                            }
                            status = memberAssetsService.updateById(memberAssets);
                            status = memberService.updateById(member);
                            rechargeRecord.setCurrency(1);
                            //增加会员经验值
                            memberAssets.setMeberExperienceSize(memberAssets.getMeberExperienceSize() + diamonds.getDiamondsPrice().intValue());
                            memberAssetsService.updateById(memberAssets);
                            //增加记录
                            rechargeRecord.setMemberId(memberAssets.getMemberId());
                            rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
                            rechargeRecord.setType(1);
                            rechargeRecord.setMode(3);
                            rechargeRecordService.insert(rechargeRecord);
                        } else if (type == 3) {
                            MemberAssets memberAssets = memberAssetsService.selectList(new EntityWrapper<MemberAssets>().eq("memberId", memberId)).get(0);
                            memberAssets.setMemberDiamondsizeOfGold(memberAssets.getMemberDiamondsizeOfGold() + diamonds.getDiamondsSize());
                            status = memberAssetsService.updateById(memberAssets);
                            rechargeRecord.setRunSize(Long.valueOf(diamonds.getDiamondsSize()));
                            rechargeRecord.setSurplus(memberAssets.getMemberDiamondsizeOfGold());
                            rechargeRecord.setCurrency(3);
                            rechargeRecord.setName("购买钻石");
                            //增加会员经验值
                            memberAssets.setMeberExperienceSize(memberAssets.getMeberExperienceSize() + diamonds.getDiamondsPrice().intValue());
                            memberAssetsService.updateById(memberAssets);
                            //增加记录
                            rechargeRecord.setMemberId(memberAssets.getMemberId());
                            rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
                            rechargeRecord.setType(1);
                            rechargeRecord.setMode(3);
                            rechargeRecordService.insert(rechargeRecord);
                        }
                        if (memberId == null || "".equals(memberId) || "null".equals(memberId)){

                        }else {
                            //返佣
                            Integer memberId1 = rechargeRecord.getMemberId();
                            //获取接收礼物的上级
                            Member member1 = memberMapper.selectById(memberId1);
                            Integer registrationChannel = member1.getRegistrationChannel();
                            //必须为车友注册用户以及黑卡注册用户
                            if (registrationChannel != 1) {
                                Integer pid = member1.getPid();
                                if (pid == null || pid == 0) {

                                } else {
                                    Award award = new Award(platformParameterSettingMapper, memberService, memberAssetsService,rechargeRecordService);
                                    award.oneAward(pid, rechargeRecord.getRunSize(),2);
                                    //获取接收礼物的上上级
                                    Integer pid1 = memberMapper.selectById(pid).getPid();
                                    if (pid1 == null || pid1 == 0) {

                                    } else {
                                        award.twoAward(pid1, rechargeRecord.getRunSize(),2);
                                    }
                                }
                            }
                        }
                        resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
                        resultUtils.setMessage("充值成功");
                        return resultUtils;
                    }
                    resultUtils.setStatus(Constant.STATUS_FAILED);
                    resultUtils.setMessage("当前交易不在交易列表中");
                    return resultUtils;
                }
                resultUtils.setStatus(Constant.STATUS_FAILED);
                resultUtils.setMessage("未能获取获取到交易列表");
                return resultUtils;
            } else {
                resultUtils.setStatus(Constant.STATUS_FAILED);
                resultUtils.setMessage("支付失败，错误码：" + states);
                return resultUtils;
            }
        }
    }

    //获取mou个月后的今天
    public static Date getFourAfter(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + month);
        date = calendar.getTime();
        return date;
    }
}
