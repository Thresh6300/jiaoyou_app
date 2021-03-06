package com.qiqi.jiaoyou_app.controller;


import com.qiqi.jiaoyou_app.service.IMembershipSettingsService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import com.qiqi.jiaoyou_app.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 会员设置表 前端控制器
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Api(tags = {"会员设置"})
@RestController
@RequestMapping("/jiaoyou_app/membershipSettings")
public class MembershipSettingsController {

    @Autowired
    private IMembershipSettingsService membershipSettingsService;

    @ApiOperation(value = "修改设置(已完成)")
    @GetMapping("/updateSetting")
    public ResultUtils aopupdateSetting(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
                                     @ApiParam(name = "id",value = "会员id",required = true,type = "Integer") Integer id,
                                     @ApiParam(name = "type",value = "类型" +
                                             "1:同城内可查看 1：是2：否;" +
                                             "2:同城外可查看 1：是2：否" +
                                             "3:信息保密开关 1：是2：否" +
                                             "4:好友消息提醒开关 1：是2：否" +
                                             "5:系统消息/公告提醒开关 1：是2：否" +
                                             "6:我的动态回复提醒开关 1：是2：否" +
                                             "7:世界之窗隐藏信息 1：是2：否",required = true,type = "Integer") Integer type,
                                     @ApiParam(name = "state",value = "状态",required = true,type = "Integer") Integer state){
        return membershipSettingsService.updateSetting(id,type,state);
    }

}

