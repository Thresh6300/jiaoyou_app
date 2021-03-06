package com.qiqi.jiaoyou_app.controller.award;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.qiqi.jiaoyou_app.mapper.PlatformParameterSettingMapper;
import com.qiqi.jiaoyou_app.pojo.Member;
import com.qiqi.jiaoyou_app.pojo.MemberAssets;
import com.qiqi.jiaoyou_app.pojo.PlatformParameterSetting;
import com.qiqi.jiaoyou_app.pojo.RechargeRecord;
import com.qiqi.jiaoyou_app.service.IMemberAssetsService;
import com.qiqi.jiaoyou_app.service.IMemberService;
import com.qiqi.jiaoyou_app.serviceImpl.RechargeRecordServiceImpl;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static java.util.Objects.isNull;

@Component
public class Award {
    private final PlatformParameterSettingMapper parameterSettingMapper;
    private final IMemberService memberService;
    private final IMemberAssetsService memberAssetsService;
    private final RechargeRecordServiceImpl rechargeRecordService;
    public Award(PlatformParameterSettingMapper parameterSettingMapper, IMemberService memberService, IMemberAssetsService memberAssetsService,RechargeRecordServiceImpl rechargeRecordService) {
        this.parameterSettingMapper = parameterSettingMapper;
        this.memberService = memberService;
        this.memberAssetsService = memberAssetsService;
        this.rechargeRecordService = rechargeRecordService;
    }

    /**
     * 一级分销
     * @param uid 会员id
     * @param money 金额
     * @return boolean
     */
    public boolean oneAward(Integer uid,float money,Integer type)
    {
        PlatformParameterSetting conf = awardConf();
        Member user = memberService.selectById(uid);
        Member data = memberService.selectById(uid);
        if(isNull(data)) {
            return false;
        }
        BigDecimal price = money(conf,money,1);
        MemberAssets memberId = memberAssetsService.selectOne(new EntityWrapper<MemberAssets>().eq("memberId", data.getId()));
        Wrapper<MemberAssets> where = new EntityWrapper<MemberAssets>().eq("memberId", data.getId());
        if(price.longValue() < 1) {
            return false;
        }
        boolean res;
        if(type == 1){ // 发银砖
            Long memberDiamondsizeOfSilver = memberId.getMemberDiamondsizeOfSilver();
            memberId.setMemberDiamondsizeOfSilver(memberDiamondsizeOfSilver + price.longValue());
            res = memberAssetsService.update(memberId,where);
            RechargeRecord rechargeRecord = new RechargeRecord();
            rechargeRecord.setMemberId(uid);
            rechargeRecord.setName("银钻返佣");
            rechargeRecord.setCurrency(4);
            rechargeRecord.setMode(4);
            rechargeRecord.setType(1);
            rechargeRecord.setRunSize(price.longValue());
            rechargeRecord.setSurplus(memberId.getMemberDiamondsizeOfSilver());
            rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
            rechargeRecordService.insert(rechargeRecord);
        }else{ // 发金砖
            Long memberDiamondsizeOfSilver = memberId.getMemberDiamondsizeOfGold();
            memberId.setMemberDiamondsizeOfGold(memberDiamondsizeOfSilver + price.longValue());
            res = memberAssetsService.update(memberId,where);
            RechargeRecord rechargeRecord = new RechargeRecord();
            rechargeRecord.setMemberId(uid);
            rechargeRecord.setName("金钻返佣");
            rechargeRecord.setCurrency(3);
            rechargeRecord.setMode(4);
            rechargeRecord.setType(1);
            rechargeRecord.setRunSize(price.longValue());
            rechargeRecord.setSurplus(memberId.getMemberDiamondsizeOfGold());
            rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
            rechargeRecordService.insert(rechargeRecord);
        }
        return res;
    }

    /**
     * 二级分销
     * @param pid 父级id
     * @param money 金额
     * @return boolean
     */
    public boolean twoAward(Integer pid,float money,Integer type)
    {
        PlatformParameterSetting conf = awardConf();
        Member data = memberService.selectById(pid);
        if(isNull(data)) {
            return false;
        }
        BigDecimal price = money(conf,money,2);
        MemberAssets memberId = memberAssetsService.selectOne(new EntityWrapper<MemberAssets>().eq("memberId", data.getId()));
        Wrapper<MemberAssets> whereData = new EntityWrapper<MemberAssets>().eq("memberId",data.getId());
        if(price.longValue() < 1) {
            return false;
        }
        boolean res;
        if (type.equals(1)) { // 发银砖
            Long memberDiamondsizeOfSilver = memberId.getMemberDiamondsizeOfSilver();
            memberId.setMemberDiamondsizeOfSilver(memberDiamondsizeOfSilver + price.longValue());
            res = memberAssetsService.update(memberId, whereData);
            RechargeRecord rechargeRecord = new RechargeRecord();
            rechargeRecord.setMemberId(pid);
            rechargeRecord.setName("银钻返佣");
            rechargeRecord.setCurrency(4);
            rechargeRecord.setMode(4);
            rechargeRecord.setType(1);
            rechargeRecord.setRunSize(price.longValue());
            rechargeRecord.setSurplus(memberId.getMemberDiamondsizeOfSilver());
            rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
            rechargeRecordService.insert(rechargeRecord);
        } else { // 发金砖
            Long memberDiamondsizeOfSilver = memberId.getMemberDiamondsizeOfGold();
            memberId.setMemberDiamondsizeOfGold(memberDiamondsizeOfSilver + price.longValue());
            res = memberAssetsService.update(memberId, whereData);
            RechargeRecord rechargeRecord = new RechargeRecord();
            rechargeRecord.setMemberId(pid);
            rechargeRecord.setName("金钻返佣");
            rechargeRecord.setCurrency(3);
            rechargeRecord.setMode(4);
            rechargeRecord.setType(1);
            rechargeRecord.setRunSize(price.longValue());
            rechargeRecord.setSurplus(memberId.getMemberDiamondsizeOfGold());
            rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
            rechargeRecordService.insert(rechargeRecord);
        }
        return res;
    }

    /**
     * 计算对应比例金额
     * @param conf 配置
     * @param money 金额
     * @return BigDecimal
     */
    public BigDecimal money(PlatformParameterSetting conf,float money,int type)
    {
        BigDecimal config;
        if(type == 1){ // 一级分销配置
            config = conf.getProportionOfReturnedServants();// 比例
        }else{ // 二级分销配置
            config = conf.getProportionOfReturnedServantsTwo();// 比例
        }
        BigDecimal bigDecimal = new BigDecimal(money); // 金额
        return config.divide(BigDecimal.valueOf(100)).multiply(bigDecimal);
    }

    /**
     * 获取返佣比例配置
     * @return PlatformParameterSetting
     */
    private PlatformParameterSetting awardConf()
    {
        return parameterSettingMapper.selectById(1);
    }
}
