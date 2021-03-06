package com.qiqi.jiaoyou_app.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.qiqi.jiaoyou_app.pojo.Giftconsumption;
import com.qiqi.jiaoyou_app.pojo.NewFriend;
import com.qiqi.jiaoyou_app.serviceImpl.NewFriendServiceImpl;
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
 * .新朋友列表 前端控制器
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Api(tags = {"新朋友"})
@RestController
@RequestMapping("/jiaoyou_app/newFriend")
public class NewFriendController {

    @Autowired
    private NewFriendServiceImpl newFriendService;

    /**
     * 删除新朋友
     */
    @ApiOperation(value = "拒绝新朋友(已完成)")
    @GetMapping("/refuseFriend")
    public ResultUtils aoprefuseFriend(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
                                  @ApiParam(name = "newFriendId",value = "列表ID",required = true,type = "Integer") Integer newFriendId){
        return newFriendService.refuseFriend(newFriendId);
    }

    /**
     * 删除新朋友
     */
    @ApiOperation(value = "同意新朋友(已完成)")
    @GetMapping("/agreeFriend")
    public ResultUtils aopagreeFriend(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
                                   Integer newFriendId){
        return newFriendService.agreeFriend(newFriendId);
    }

    /**
     * 查询新朋友列表
     */
    @ApiOperation(value = "新朋友列表(已完成)")
    @GetMapping("/friendList")
    public ResultUtils aopfriendList(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
                                @ApiParam(name = "newFriendOneselfId",value = "自身ID",required = true,type = "Integer") Integer newFriendOneselfId,
                                @ApiParam(name = "pageSize",value = "页数据量",required = true,type = "Integer") Integer pageSize,
                                @ApiParam(name = "pageNum",value = "页码",required = true,type = "Integer") Integer pageNum,
                                @ApiParam(name = "keyWord",value = "关键字",required = false,type = "String") String keyWord){
        return newFriendService.friendList(newFriendOneselfId,pageSize,pageNum,keyWord);
    }


    /**
     * 打招呼
     */
    @ApiOperation(value = "打招呼(已完成)")
    @PostMapping("/sayHello")
    @DynamicParameters(name = "sayHello",properties = {
            @DynamicParameter(name = "newFriendOneselfId",value = "打招呼者ID",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "newFriendOtherPartyId",value = "被打招呼者ID",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "newFriendOtherPartyGiftId",value = "礼物ID",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "newFriendOtherPartyGiftSize",value = "礼物数量",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils aopgiveGift(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject){
        NewFriend newFriend = JSON.toJavaObject(jsonObject,NewFriend.class);
        return newFriendService.sayHello(newFriend);
    }



    /**
     * 判断自己的好友上限
     */
    @ApiOperation(value = "判断自己的好友是否达到上限(已完成)")
    @GetMapping("/friendLimit")
    public ResultUtils aopfriendLimit(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, Integer memberId){
        return newFriendService.friendLimit(memberId);
    }
}

