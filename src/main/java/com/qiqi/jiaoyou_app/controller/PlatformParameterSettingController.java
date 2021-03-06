package com.qiqi.jiaoyou_app.controller;


import com.qiqi.jiaoyou_app.service.IPlatformParameterSettingService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 平台参数设置 前端控制器
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Api(tags = {"平台参数"})
@RestController
@RequestMapping("/jiaoyou_app/platformParameterSetting")
public class PlatformParameterSettingController {

    @Autowired
    private IPlatformParameterSettingService platformParameterSettingService;

    @ApiOperation(value = "客服电话(已完成)")
    @GetMapping(value = "/consumerHotline")
    public ResultUtils consumerHotline(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token){
        return platformParameterSettingService.consumerHotline();
    }


    @ApiOperation(value = "其他参数(已完成)")
    @GetMapping(value = "/otherParameters")
    public ResultUtils aopotherParameters(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token){
        return platformParameterSettingService.otherParameters();
    }


    @ApiOperation(value = "动图以及版本信息(已完成)")
    @GetMapping(value = "/mapAndVersion")
    public ResultUtils mapAndVersion(){
        return platformParameterSettingService.mapAndVersion();
    }
}

