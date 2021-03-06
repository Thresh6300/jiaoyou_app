package com.qiqi.jiaoyou_app.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.qiqi.jiaoyou_app.pojo.Bankcard;
import com.qiqi.jiaoyou_app.pojo.Member;
import com.qiqi.jiaoyou_app.service.IBankcardService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 银行卡表 前端控制器
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Api(tags = {"银行卡"})
@RestController
@RequestMapping("/jiaoyou_app/bankcard")
public class BankcardController {


    @Autowired
    private IBankcardService bankcardService;

    @ApiOperation(value = "添加银行卡(已完成)")
    @PostMapping("/addBankcard")
    @DynamicParameters(name = "addBankcard",properties = {
            @DynamicParameter(name = "memberId",value = "会员ID",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "openingBank",value = "开户行",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "bankMemberName",value = "姓名",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "bankNumber",value = "银行卡号",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "weight",value = "权重",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils aopaddBankcard(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,@RequestBody JSONObject jsonObject){
        Bankcard bankcard = JSON.toJavaObject(jsonObject, Bankcard.class);
        return bankcardService.addBankcard(bankcard);
    }

    @ApiOperation(value = "银行卡列表(已完成)")
    @GetMapping("/bankCardList")
    public ResultUtils aopbankCardList(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
                                           @ApiParam(name = "pageSize",value = "页数据量",required = true,type = "Integer") Integer pageSize,
                                           @ApiParam(name = "pageNum",value = "页码",required = true,type = "Integer") Integer pageNum,
                                           @ApiParam(name = "id",value = "会员id",required = true,type = "Integer") Integer id){
        return bankcardService.bankCardList(pageSize,pageNum,id);
    }
}

