package com.qiqi.jiaoyou_app.controller;


import com.qiqi.jiaoyou_app.service.IAgreementService;
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
 * 协议表 前端控制器
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Api(tags = {"协议表"})
@RestController
@RequestMapping("/jiaoyou_app/agreement")
public class AgreementController {

    @Autowired
    private IAgreementService agreementService;


    @ApiOperation(value = "查询单页(已完成)")
    @GetMapping("/getAgreement")
    public ResultUtils giftList(){
        return agreementService.giftList();
    }
}

