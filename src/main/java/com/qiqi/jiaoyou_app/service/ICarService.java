package com.qiqi.jiaoyou_app.service;

import com.qiqi.jiaoyou_app.pojo.Car;
import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.util.ResultUtils;

/**
 * <p>
 * 车辆表 服务类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
public interface ICarService extends IService<Car> {

    ResultUtils bindingVehicle(Car car);
}
