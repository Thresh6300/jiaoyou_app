package com.qiqi.jiaoyou_app.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.qiqi.jiaoyou_app.pojo.Member;
import com.qiqi.jiaoyou_app.pojo.Opinion;
import com.qiqi.jiaoyou_app.service.IOpinionService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 意见表 前端控制器
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Api(tags = {"意见表"})
@RestController
@RequestMapping("/jiaoyou_app/opinion")
public class OpinionController {


    @Autowired
    private IOpinionService opinionService;

    @ApiOperation(value = "意见反馈(已完成)")
    @PostMapping("/feedback")
    @DynamicParameters(name = "feedback",properties = {
            @DynamicParameter(name = "memberId",value = "会员ID",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "memberName",value = "会员昵称",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "memberHead",value = "会员头像",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "memberPhone",value = "手机号码",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "context",value = "反馈内容",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "images",value = "反馈图片",required = true,dataTypeClass = String.class),
    })
    public ResultUtils aopfeedback(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject){
        Opinion opinion = JSON.toJavaObject(jsonObject, Opinion.class);
        return opinionService.feedback(opinion);
    }
}

