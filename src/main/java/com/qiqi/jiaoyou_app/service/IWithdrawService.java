package com.qiqi.jiaoyou_app.service;

import com.qiqi.jiaoyou_app.pojo.Withdraw;
import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.util.ResultUtils;

/**
 * <p>
 * 提现申请表 服务类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
public interface IWithdrawService extends IService<Withdraw> {

    ResultUtils addWithdraw(Withdraw withdraw);

    ResultUtils withdrawLog(Integer pageNum,Integer pageSize,Integer memberId);

    ResultUtils withdrawalBalance(Integer memberId);
}
