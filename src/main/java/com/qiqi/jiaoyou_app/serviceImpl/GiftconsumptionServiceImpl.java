package com.qiqi.jiaoyou_app.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.mapper.GiftMapper;
import com.qiqi.jiaoyou_app.mapper.MemberMapper;
import com.qiqi.jiaoyou_app.mapper.NewFriendMapper;
import com.qiqi.jiaoyou_app.pojo.Giftconsumption;
import com.qiqi.jiaoyou_app.mapper.GiftconsumptionMapper;
import com.qiqi.jiaoyou_app.pojo.Member;
import com.qiqi.jiaoyou_app.pojo.NewFriend;
import com.qiqi.jiaoyou_app.service.IGiftconsumptionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 礼物记录表 服务实现类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Service
public class GiftconsumptionServiceImpl extends ServiceImpl<GiftconsumptionMapper, Giftconsumption> implements IGiftconsumptionService {

    @Autowired
    private NewFriendMapper newFriendMapper;

    @Autowired
    private GiftconsumptionMapper giftconsumptionMapper;

    @Autowired
    private GiftMapper giftMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private GiftconsumptionServiceImpl giftconsumptionService;
    @Override
    public ResultUtils greetingGift(Giftconsumption giftconsumption) {
        ResultUtils resultUtils = new ResultUtils();
        //查询接收方与发送方的打招呼记录
        List<NewFriend> newFriends = newFriendMapper.selectList(new EntityWrapper<NewFriend>().eq("new_friend_oneself_id", giftconsumption.getSendMemberId()).eq("new_friend_other_party_id", giftconsumption.getMemberId()));
        if (newFriends.size() <= 0){
            resultUtils.setStatus(Constant.STATUS_FAILED);
            resultUtils.setMessage(Constant.NO_RECORD_OF_GREETING);
            return resultUtils;
        }else {
            List<Giftconsumption> list = new ArrayList<>();
            Member member = memberMapper.selectById(giftconsumption.getSendMemberId());
            for (NewFriend newFriend : newFriends){
                giftconsumption.setSendMemberHead(member.getHead());
                giftconsumption.setSendMemberNickName(member.getNickName());
                giftconsumption.setGiftId(newFriend.getNewFriendOtherPartyGiftId());
                giftconsumption.setGiftImages(newFriend.getNewFriendOtherPartyGiftImage());
                giftconsumption.setGiftName(newFriend.getNewFriendOtherPartyGiftName());
                giftconsumption.setGiftSize(newFriend.getNewFriendOtherPartyGiftSize());
                giftconsumption.setAddTime(new Timestamp(System.currentTimeMillis()));
                list.add(giftconsumption);
            }
            boolean b = giftconsumptionService.insertBatch(list);
            if (!b){
                resultUtils.setStatus(Constant.STATUS_FAILED);
                resultUtils.setMessage("接收礼物失败");
                return resultUtils;
            }else {
                resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
                resultUtils.setMessage("接收礼物成功");
                return resultUtils;
            }
        }
    }
}
