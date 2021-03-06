package com.qiqi.jiaoyou_app.service;

import com.qiqi.jiaoyou_app.pojo.RechargeRecord;
import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.util.ResultUtils;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
public interface IRechargeRecordService extends IService<RechargeRecord> {

    ResultUtils aopRechargeLog(Integer id, Integer pageNum, Integer pageSize);

    ResultUtils speakerConsumption(RechargeRecord rechargeRecord);
}
