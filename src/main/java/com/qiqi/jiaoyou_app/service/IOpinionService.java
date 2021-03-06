package com.qiqi.jiaoyou_app.service;

import com.qiqi.jiaoyou_app.pojo.Opinion;
import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.util.ResultUtils;

/**
 * <p>
 * 意见表 服务类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
public interface IOpinionService extends IService<Opinion> {

    ResultUtils feedback(Opinion opinion);
}
