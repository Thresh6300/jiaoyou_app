package com.qiqi.jiaoyou_app.service;

import com.qiqi.jiaoyou_app.pojo.Fabulous;
import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.util.ResultUtils;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sunLQ
 * @since 2020-05-08
 */
public interface IFabulousService extends IService<Fabulous> {

    ResultUtils dynamicThumbsUp(Fabulous fabulous);
}
