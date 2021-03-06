package com.qiqi.jiaoyou_app.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.qiqi.jiaoyou_app.pojo.Bankcard;
import com.qiqi.jiaoyou_app.pojo.Member;
import com.qiqi.jiaoyou_app.pojo.Withdraw;
import com.qiqi.jiaoyou_app.service.IMemberService;
import com.qiqi.jiaoyou_app.service.IWithdrawService;
import com.qiqi.jiaoyou_app.serviceImpl.WithdrawServiceImpl;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.math.BigDecimal;

/**
 * <p>
 * 提现申请表 前端控制器
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Api(tags = {"提现申请"})
@RestController
@RequestMapping("/jiaoyou_app/withdraw")
public class WithdrawController {

    @Autowired
    private IWithdrawService withdrawService;

    @ApiOperation(value = "添加提现申请(已完成)")
    @PostMapping("/addWithdraw")
    @DynamicParameters(name = "addWithdraw",properties = {
            @DynamicParameter(name = "memberId",value = "会员ID",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "price",value = "提现金额",required = true,dataTypeClass = BigDecimal.class),
            @DynamicParameter(name = "proportion",value = "提现比例",required = true,dataTypeClass = BigDecimal.class),
            @DynamicParameter(name = "bankId",value = "银行卡Id",required = true,dataTypeClass = Integer.class)
    })
    public ResultUtils aopaddWithdraw(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,@RequestBody JSONObject jsonObject){
        Withdraw withdraw = JSON.toJavaObject(jsonObject, Withdraw.class);
        return withdrawService.addWithdraw(withdraw);
    }

    @ApiOperation(value = "提现记录(已完成)")
    @GetMapping("/withdrawLog")
    public ResultUtils aopwithdrawLog(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
    @ApiParam(name = "pageSize",value = "页数据量",required = true,type = "Integer") Integer pageSize,
    @ApiParam(name = "pageNum",value = "页码",required = true,type = "Integer") Integer pageNum,
    @ApiParam(name = "memberId",value = "会员ID",required = true,type = "Integer") Integer memberId){
        return withdrawService.withdrawLog(pageNum,pageSize,memberId);
    }



    @ApiOperation(value = "查询我的可提现余额(已完成)")
    @GetMapping("/withdrawalBalance")
    public ResultUtils aopwithdrawalBalance(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, Integer memberId){
        return withdrawService.withdrawalBalance(memberId);
    }
}

