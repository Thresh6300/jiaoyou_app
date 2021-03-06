package com.qiqi.jiaoyou_app.service;

import com.qiqi.jiaoyou_app.pojo.*;
import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.util.ResultUtils;

/**
 * <p>
 * 礼物表 服务类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
public interface IGiftService extends IService<Gift> {

    ResultUtils giftList(Integer pageSize, Integer pageNum, String keyWord);

    ResultUtils giftsGiven(Integer pageSize, Integer pageNum, Integer id);

    ResultUtils giveGift(Giftconsumption giftconsumption);

    ResultUtils giftsReceived(Integer pageSize, Integer pageNum, Integer id);

    ResultUtils buyASmallHorn(MemberAssets memberAssets);

    ResultUtils speakerConsumption(Speakerconsumptionrecord speakerconsumptionrecord);

    ResultUtils ReceiveGift(Giftconsumption giftconsumption);
}
