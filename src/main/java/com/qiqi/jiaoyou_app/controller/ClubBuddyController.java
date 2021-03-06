package com.qiqi.jiaoyou_app.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.qiqi.jiaoyou_app.pojo.ClubBuddy;
import com.qiqi.jiaoyou_app.service.ClubBuddyService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 俱乐部好友表(ClubBuddy)表控制层
 *
 * @author cfx
 * @since 2020-11-27 11:39:48
 */
@RestController
@RequestMapping("/jiaoyou_app/clubBuddy")
@Api(tags ="俱乐部好友")
public class ClubBuddyController
{
    /**
     * 服务对象
     *
     * @author cfx
     * @since 2020-11-27 11:39:48
     */
    @Autowired
    private ClubBuddyService clubBuddyService;

    @ApiOperation(value = "字段说明(已完成)")
    @PostMapping(value = "/club")
    public void addClub(ClubBuddy club){

    }
    @ApiOperation(value = "俱乐部好友列表(已完成)")
    @PostMapping(value = "/buddyList")
    @DynamicParameters(name = "buddyList",properties = {
		  @DynamicParameter(name = "clubId",value = "俱乐部id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "oneselfId",value = "自身id",required = true,dataTypeClass = String.class),
    })
    public ResultUtils buddyList(@RequestBody JSONObject jsonObject){
	  ClubBuddy clubBuddy = JSON.toJavaObject(jsonObject, ClubBuddy.class);
	  return clubBuddyService.buddyList(clubBuddy);
    }

    @ApiOperation(value = "秘书列表(已完成)")
    @PostMapping(value = "/secretaryList")
    @DynamicParameters(name = "secretaryList",properties = {
		  @DynamicParameter(name = "clubId",value = "俱乐部id",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils secretaryList(@RequestBody JSONObject jsonObject){
	  ClubBuddy clubBuddy = JSON.toJavaObject(jsonObject, ClubBuddy.class);
	  return clubBuddyService.secretaryList(clubBuddy);
    }

}