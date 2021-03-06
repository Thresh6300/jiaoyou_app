package com.qiqi.jiaoyou_app.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.qiqi.jiaoyou_app.pojo.Car;
import com.qiqi.jiaoyou_app.pojo.Member;
import com.qiqi.jiaoyou_app.service.ICarService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 车辆表 前端控制器
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Api(tags = {"车辆表"})
@RestController
@RequestMapping("/jiaoyou_app/car")
public class CarController {

    @Autowired
    private ICarService carService;

    @ApiOperation(value = "车辆绑定(已完成)")
    @PostMapping("/bindingVehicle")
    @DynamicParameters(name = "bindingVehicle",properties = {
            @DynamicParameter(name = "memberId",value = "会员ID",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "drivingLicensePhoto",value = "行驶证照片",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "images",value = "车辆照片",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "vehicleType",value = "车型",required = false,dataTypeClass = String.class),
            @DynamicParameter(name = "model",value = "型号",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "weight",value = "权重",required = false,dataTypeClass = Integer.class),
    })
    public ResultUtils aopbindingVehicle(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject){
        Car car = JSON.toJavaObject(jsonObject,Car.class);
        return carService.bindingVehicle(car);
    }

}

