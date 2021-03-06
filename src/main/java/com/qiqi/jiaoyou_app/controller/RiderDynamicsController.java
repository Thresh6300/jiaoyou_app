package com.qiqi.jiaoyou_app.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.qiqi.jiaoyou_app.pojo.CircleOfFriends;
import com.qiqi.jiaoyou_app.pojo.Member;
import com.qiqi.jiaoyou_app.pojo.RiderDynamics;
import com.qiqi.jiaoyou_app.service.IRiderDynamicsService;
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
 * 车友圈动态 前端控制器
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Api(tags = {"车友圈动态"})
@RestController
@RequestMapping("/jiaoyou_app/riderDynamics")
public class RiderDynamicsController {

    @Autowired
    private IRiderDynamicsService riderDynamicsService;

    @ApiOperation(value = "好友车友圈动态列表(已完成)")
    @GetMapping("/ridersDynamicList")
    public ResultUtils aopridersDynamicList(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
                                               @ApiParam(name = "pageSize",value = "页数据量",required = true,type = "Integer") Integer pageSize,
                                               @ApiParam(name = "pageNum",value = "页码",required = true,type = "Integer") Integer pageNum,
                                               @ApiParam(name = "id",value = "会员id",required = true,type = "Integer") Integer id){
        //车友动态列表：好友头像，昵称，性别，年龄，所在城市，标题，发布时间，部分动态内容，点赞量，评论量，转发
        return riderDynamicsService.ridersDynamicList(pageSize,pageNum,id);
    }

    @ApiOperation(value = "我的车友圈列表(已完成)")
    @GetMapping("/myRidersDynamicList")
    public ResultUtils aopmyRidersDynamicList(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
                                             @ApiParam(name = "pageSize",value = "页数据量",required = true,type = "Integer") Integer pageSize,
                                             @ApiParam(name = "pageNum",value = "页码",required = true,type = "Integer") Integer pageNum,
                                             @ApiParam(name = "id",value = "会员id",required = true,type = "Integer") Integer id){
        return riderDynamicsService.myRidersDynamicList(pageSize,pageNum,id);
    }

    @ApiOperation(value = "车友圈动态详情(已完成)")
    @GetMapping("/dynamicDetails")
    public ResultUtils aopdynamicDetails(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
                                      @ApiParam(name = "pageSize",value = "页数据量",required = true,type = "Integer") Integer pageSize,
                                      @ApiParam(name = "pageNum",value = "页码",required = true,type = "Integer") Integer pageNum,
                                      @ApiParam(name = "id",value = "动态id",required = true,type = "Integer") Integer id){
        return riderDynamicsService.dynamicDetails(pageSize, pageNum, id,token);
    }


    @ApiOperation(value = "删除动态(已完成)")
    @GetMapping("/del")
    public ResultUtils aopdel(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
                           Integer id){
        return riderDynamicsService.del(id);
    }
}

