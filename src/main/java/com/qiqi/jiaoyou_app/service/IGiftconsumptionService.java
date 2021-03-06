package com.qiqi.jiaoyou_app.service;

import com.qiqi.jiaoyou_app.pojo.Giftconsumption;
import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.util.ResultUtils;

/**
 * <p>
 * 礼物记录表 服务类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
public interface IGiftconsumptionService extends IService<Giftconsumption> {

    ResultUtils greetingGift(Giftconsumption giftconsumption);
}
