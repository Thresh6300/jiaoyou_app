package com.qiqi.jiaoyou_app.controller;


import com.qiqi.jiaoyou_app.serviceImpl.DiamondsServiceImpl;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author GR123
 * @since 2020-07-15
 */
@Api(tags = {"金额表"})
@RestController
@RequestMapping("/jiaoyou_app/diamonds")
public class DiamondsController {

    @Autowired
    private DiamondsServiceImpl diamondsService;

    @ApiOperation(value = "查询金额(已完成)")
    @GetMapping("/bindingVehicle")
    public ResultUtils bindingVehicle(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
                                      @ApiParam(name = "type",value = "1:黑卡 2：会员 3：钻石",required = true,type = "Integer") Integer type,
                                      @ApiParam(name = "terminal",value = "1:苹果2：安卓",required = true,type = "Integer") Integer terminal){
        return diamondsService.bindingVehicle(type,terminal);
    }


}

