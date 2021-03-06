package com.qiqi.jiaoyou_app.service;

import com.qiqi.jiaoyou_app.pojo.RiderDynamics;
import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.util.ResultUtils;

/**
 * <p>
 * 车友圈动态 服务类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
public interface IRiderDynamicsService extends IService<RiderDynamics> {


    ResultUtils ridersDynamicList(Integer pageSize, Integer pageNum, Integer id);

    ResultUtils myRidersDynamicList(Integer pageSize, Integer pageNum, Integer id);

    ResultUtils dynamicDetails(Integer pageSize, Integer pageNum, Integer id,String token);


    ResultUtils del(Integer id);
}
