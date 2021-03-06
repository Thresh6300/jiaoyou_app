package com.qiqi.jiaoyou_app.service;

import com.qiqi.jiaoyou_app.pojo.Agreement;
import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.util.ResultUtils;

/**
 * <p>
 * 协议表 服务类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
public interface IAgreementService extends IService<Agreement> {

    ResultUtils giftList();
}
