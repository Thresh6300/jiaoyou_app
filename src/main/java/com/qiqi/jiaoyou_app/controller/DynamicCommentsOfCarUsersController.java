package com.qiqi.jiaoyou_app.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.qiqi.jiaoyou_app.pojo.DynamicCommentsOfCarUsers;
import com.qiqi.jiaoyou_app.pojo.DynamicReviewOfFriendsCircle;
import com.qiqi.jiaoyou_app.service.IDynamicCommentsOfCarUsersService;
import com.qiqi.jiaoyou_app.service.IRiderDynamicsService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 车友动态评论表 前端控制器
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Api(tags = {"车友动态评论"})
@RestController
@RequestMapping("/jiaoyou_app/dynamicCommentsOfCarUsers")
public class DynamicCommentsOfCarUsersController {

    @Autowired
    private IDynamicCommentsOfCarUsersService dynamicCommentsOfCarUsersService;

    @ApiOperation(value = "车友圈动态评论(已完成)")
    @PostMapping("/dynamicComment")
    @DynamicParameters(name = "RidersCircleDynamicReviews",properties = {
            @DynamicParameter(name = "level",value = "评论级别 1：直接评论动态2：回复评论",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "dynamicIdOrCommentId",value = "动态id/评论id",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "memberId",value = "会员id",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "memberHead",value = "会员头像",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "memberNickName",value = "会员昵称",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "context",value = "评论内容",required = true,dataTypeClass = String.class),
    })
    public ResultUtils aopdynamicDetails(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject){
        DynamicCommentsOfCarUsers dynamicCommentsOfCarUsers = JSON.toJavaObject(jsonObject,DynamicCommentsOfCarUsers.class);
        return dynamicCommentsOfCarUsersService.dynamicDetails(dynamicCommentsOfCarUsers);
    }
}

