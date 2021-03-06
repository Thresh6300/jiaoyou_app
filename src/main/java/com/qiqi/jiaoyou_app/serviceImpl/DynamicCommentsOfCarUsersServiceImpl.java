package com.qiqi.jiaoyou_app.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.jPush.utils.JpushUtil;
import com.qiqi.jiaoyou_app.mapper.MemberMapper;
import com.qiqi.jiaoyou_app.mapper.MembershipSettingsMapper;
import com.qiqi.jiaoyou_app.mapper.RiderDynamicsMapper;
import com.qiqi.jiaoyou_app.pojo.*;
import com.qiqi.jiaoyou_app.mapper.DynamicCommentsOfCarUsersMapper;
import com.qiqi.jiaoyou_app.service.IDynamicCommentsOfCarUsersService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车友动态评论表 服务实现类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Service
public class DynamicCommentsOfCarUsersServiceImpl extends ServiceImpl<DynamicCommentsOfCarUsersMapper, DynamicCommentsOfCarUsers> implements IDynamicCommentsOfCarUsersService {

    @Autowired
    private DynamicCommentsOfCarUsersMapper dynamicCommentsOfCarUsersMapper;
    @Autowired
    private RiderDynamicsMapper riderDynamicsMapper;
    @Autowired
    private MembershipSettingsMapper membershipSettingsMapper;
    @Autowired
    private MemberMapper memberMapper;


    /***
     * @Description: 评论世界圈的动态信息
     * @Author: nan only update
     * @Date: 2021-01-28 16:29
     */

    @Override
    public ResultUtils dynamicDetails(DynamicCommentsOfCarUsers dynamicCommentsOfCarUsers) {
        ResultUtils resultUtils = new ResultUtils();
        Member member = memberMapper.selectById(dynamicCommentsOfCarUsers.getMemberId());
        dynamicCommentsOfCarUsers.setLable(member.getCarLable());
        //评论时间
        dynamicCommentsOfCarUsers.setAddTime(new Timestamp(System.currentTimeMillis()));
        //新增
        Integer insert = dynamicCommentsOfCarUsersMapper.insert(dynamicCommentsOfCarUsers);
        if (insert <= 0) {
            resultUtils.setMessage("评论失败");
            resultUtils.setStatus(Constant.STATUS_FAILED);
            return resultUtils;
        }
        //修改评论量（得到动态id）
        if (dynamicCommentsOfCarUsers.getLevel() == 1) {
            RiderDynamics riderDynamics = riderDynamicsMapper.selectById(dynamicCommentsOfCarUsers.getDynamicIdOrCommentId());
            riderDynamics.setCommentSize(riderDynamics.getCommentSize() == null ? 1 : riderDynamics.getCommentSize() + 1);
            Integer integer = riderDynamicsMapper.updateById(riderDynamics);
            if (integer <= 0) {
                resultUtils.setMessage("评论失败");
                resultUtils.setStatus(Constant.STATUS_FAILED);
                return resultUtils;
            }
            push(dynamicCommentsOfCarUsers,riderDynamics.getId() + "",riderDynamics.getMemerId()+"");
            resultUtils.setMessage("评论成功");
            resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
            return resultUtils;
        } else {
            DynamicCommentsOfCarUsers dynamicCommentsOfCarUsers2 = dynamicCommentsOfCarUsers;
            Integer memberId = dynamicCommentsOfCarUsersMapper.selectById(dynamicCommentsOfCarUsers2.getDynamicIdOrCommentId()).getMemberId();
            boolean isTwo = true;
            while (isTwo) {
                DynamicCommentsOfCarUsers dynamicCommentsOfCarUsers1 = dynamicCommentsOfCarUsersMapper.selectById(dynamicCommentsOfCarUsers.getDynamicIdOrCommentId());
                if (dynamicCommentsOfCarUsers1.getLevel() == 1) {
                    isTwo = false;
                    RiderDynamics riderDynamics = riderDynamicsMapper.selectById(dynamicCommentsOfCarUsers1.getDynamicIdOrCommentId());
                    riderDynamics.setCommentSize(riderDynamics.getCommentSize() == null ? 1 : riderDynamics.getCommentSize() + 1);
                    Integer integer = riderDynamicsMapper.updateById(riderDynamics);
                    if (integer <= 0) {
                        resultUtils.setMessage("评论失败");
                        resultUtils.setStatus(Constant.STATUS_FAILED);
                    } else {
                        push(dynamicCommentsOfCarUsers2,riderDynamics.getId() + "",memberId + "");
                        resultUtils.setMessage("评论成功");
                        resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
                    }
                } else {
                    dynamicCommentsOfCarUsers = dynamicCommentsOfCarUsers1;
                }
            }
            return resultUtils;
        }
    }




    public void push(DynamicCommentsOfCarUsers dynamicCommentsOfCarUsers,String dongtaiId,String pushId){
        String str = "";
        //评论级别
        if (dynamicCommentsOfCarUsers.getLevel() == 1){
            str += dynamicCommentsOfCarUsers.getMemberNickName() +"评论了您的动态";
        }else {
            str += dynamicCommentsOfCarUsers.getMemberNickName() +"回复了您的评论";
        }
        List<MembershipSettings> membershipSettingsList = membershipSettingsMapper.selectList(new EntityWrapper<MembershipSettings>().eq("memberId", pushId));
        MembershipSettings membershipSettings = membershipSettingsList.get(0);
        if (membershipSettings.getDynamicResponseState() == 1){
            Member member = memberMapper.selectById(pushId);
            Map<String, String> xtrasparams = new HashMap<String, String>(); //扩展字段
            xtrasparams.put("id", dongtaiId);
            xtrasparams.put("type", 3 +"");
            if (member.getPushId() == null) {

            } else {
                JpushUtil.sendToRegistrationId(member.getPushId(), str, str, str, xtrasparams);
            }
        }
    }
}
