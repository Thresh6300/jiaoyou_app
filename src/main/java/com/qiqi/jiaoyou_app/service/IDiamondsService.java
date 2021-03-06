package com.qiqi.jiaoyou_app.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.pojo.Diamonds;
import com.qiqi.jiaoyou_app.util.ResultUtils;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GR123
 * @since 2020-07-15
 */
public interface IDiamondsService extends IService<Diamonds> {

    ResultUtils bindingVehicle(Integer type, Integer terminal);
}
