package com.qiqi.jiaoyou_app.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.pojo.WithdrawalLogs;

/**
 * 金钻银钻兑换记录表(WithdrawalLogs)表服务接口
 *
 * @author makejava
 * @since 2020-12-12 17:28:39
 */
public interface WithdrawalLogsService extends IService<WithdrawalLogs> {

	Boolean add(WithdrawalLogs withdrawalLogs);


}