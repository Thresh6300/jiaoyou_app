package com.qiqi.jiaoyou_app.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.mapper.MemberMapper;
import com.qiqi.jiaoyou_app.pojo.Member;
import com.qiqi.jiaoyou_app.pojo.MembershipSettings;
import com.qiqi.jiaoyou_app.mapper.MembershipSettingsMapper;
import com.qiqi.jiaoyou_app.service.IMembershipSettingsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 * 会员设置表 服务实现类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@SuppressWarnings("ALL")
@Service
public class MembershipSettingsServiceImpl extends ServiceImpl<MembershipSettingsMapper, MembershipSettings> implements IMembershipSettingsService {

    @Autowired
    private MembershipSettingsMapper membershipSettingsMapper;
    @Autowired
    private MemberMapper memberMapper;


    @Override
    public ResultUtils updateSetting(Integer id, Integer type, Integer state) {
        ResultUtils resultUtils = new ResultUtils();
        List<MembershipSettings> memberId = membershipSettingsMapper.selectList(new EntityWrapper<MembershipSettings>().eq("memberId", id));
        MembershipSettings membershipSettings = memberId.get(0);
        membershipSettings.setEditState(new Timestamp(System.currentTimeMillis()));
        Member member = memberMapper.selectById(id);
        if (type == 7 && member.getLevel() < 10){
            resultUtils.setMessage(Constant.REACH_VIP10);
            resultUtils.setStatus(Constant.STATUS_FAILED);
            return resultUtils;
        }
        Integer integer = 0;
        if (membershipSettings == null) {
            membershipSettings = new MembershipSettings();
            setValue(membershipSettings, state, type);
            integer = membershipSettingsMapper.insert(membershipSettings);
        } else {
            setValue(membershipSettings, state, type);
            integer = membershipSettingsMapper.updateById(membershipSettings);
        }
        if (integer <= 0) {
            resultUtils.setStatus(Constant.STATUS_FAILED);
            resultUtils.setMessage(Constant.EDIT_FAILED);
            return resultUtils;
        }
        resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
        resultUtils.setMessage(Constant.EDIT_SUCCEEDED);
        return resultUtils;
    }

    public MembershipSettings setValue(MembershipSettings membershipSettings, Integer state, Integer type) {
        switch (type) {
            case 1:
                membershipSettings.setSameCityWithinState(state);
                break;
            case 2:
                membershipSettings.setSameCityExternalState(state);
                break;
            case 3:
                membershipSettings.setConfidentialityOfInformationState(state);
                break;
            case 4:
                membershipSettings.setFriendMessageState(state);
                break;
            case 5:
                membershipSettings.setNoticeState(state);
                break;
            case 6:
                membershipSettings.setDynamicResponseState(state);
                break;
            case 7:
                membershipSettings.setWorldInformation(state);
                break;
        }
        return membershipSettings;
    }
}
