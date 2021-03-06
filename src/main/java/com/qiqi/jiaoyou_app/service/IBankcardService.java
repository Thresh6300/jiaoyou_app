package com.qiqi.jiaoyou_app.service;

import com.qiqi.jiaoyou_app.pojo.Bankcard;
import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.util.ResultUtils;

/**
 * <p>
 * 银行卡表 服务类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
public interface IBankcardService extends IService<Bankcard> {

    ResultUtils addBankcard(Bankcard bankcard);

    ResultUtils bankCardList(Integer pageSize, Integer pageNum, Integer id);
}
