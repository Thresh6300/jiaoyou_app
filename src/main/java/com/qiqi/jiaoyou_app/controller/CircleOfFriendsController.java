package com.qiqi.jiaoyou_app.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.qiqi.jiaoyou_app.pojo.CircleOfFriends;
import com.qiqi.jiaoyou_app.service.ICircleOfFriendsService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.*;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 朋友圈动态 前端控制器
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Api(tags = {"朋友圈动态"})
@RestController
@RequestMapping("/jiaoyou_app/circleOfFriends")
public class CircleOfFriendsController {

    @Autowired
    private ICircleOfFriendsService circleOfFriendsService;


    @ApiOperation(value = "发布动态(已完成)")
    @PostMapping("/dynamicPublishing")
    @DynamicParameters(name = "dynamicPublishing",properties = {
            @DynamicParameter(name = "memerId",value = " 会员id",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "context",value = " 动态内容",required = false,dataTypeClass = String.class),
            @DynamicParameter(name = "images",value = " 图片数组",required = false,dataTypeClass = String.class),
            @DynamicParameter(name = "video",value = " 视频",required = false,dataTypeClass = String.class),
            @DynamicParameter(name = "city",value = " 地址",required = false,dataTypeClass = String.class),
            @DynamicParameter(name = "strangersInTheSameCity",value = " 是否允许同城陌生人查看  1：允许  2：不允许",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "strangersOutsideTheCity",value = " 是否允许同城以外陌生人 1：允许  2：不允许",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "citySynchronization",value = " 是否同步到HC大咖秀  1：同步  2：不同步",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils aopdynamicPublishing(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject){
        CircleOfFriends circleOfFriends = JSON.toJavaObject(jsonObject,CircleOfFriends.class);
        return circleOfFriendsService.dynamicPublishing(circleOfFriends);
    }

    @ApiOperation(value = "好友朋友圈列表(已完成)")
    @GetMapping("/friendsOfFriendsList")
    public ResultUtils aopfriendsOfFriendsList(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
                                            @ApiParam(name = "pageSize",value = "页数据量",required = true,type = "Integer") Integer pageSize,
                                            @ApiParam(name = "pageNum",value = "页码",required = true,type = "Integer") Integer pageNum,
                                            @ApiParam(name = "id",value = "会员id",required = true,type = "Integer") Integer id){
        return circleOfFriendsService.friendsOfFriendsList(pageSize,pageNum,id);
    }

    @ApiOperation(value = "我的朋友圈列表(已完成)")
    @GetMapping("/myCircleOfFriendsList")
    public ResultUtils aopmyCircleOfFriendsList(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
                                            @ApiParam(name = "pageSize",value = "页数据量",required = true,type = "Integer") Integer pageSize,
                                            @ApiParam(name = "pageNum",value = "页码",required = true,type = "Integer") Integer pageNum,
                                            @ApiParam(name = "id",value = "会员id",required = true,type = "Integer") Integer id){
        return circleOfFriendsService.myCircleOfFriendsList(pageSize,pageNum,id);
    }

    @ApiOperation(value = "动态详情(已完成)")
    @GetMapping("/dynamicDetails")
    public ResultUtils aopdynamicDetails(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
                                      @ApiParam(name = "pageSize",value = "页数据量",required = true,type = "Integer") Integer pageSize,
                                      @ApiParam(name = "pageNum",value = "页码",required = true,type = "Integer") Integer pageNum,
                                      @ApiParam(name = "id",value = "动态id",required = true,type = "Integer") Integer id,
                                      @ApiParam(name = "memberId",value = "会员id",required = true,type = "Integer") Integer memberId
						     )
    {
        return circleOfFriendsService.dynamicDetails(pageSize, pageNum, id,token,memberId);
    }

    

    @ApiOperation(value = "删除动态(已完成)")
    @GetMapping("/del")
    public ResultUtils aopdel(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
                               Integer id){
        return circleOfFriendsService.del(id);
    }



    @ApiOperation(value = "个人主页/会员详情朋友圈列表(已完成)")
    @GetMapping("/memberDetailFriendsList")
    public ResultUtils memberDetailFriendsList(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
                                            @ApiParam(name = "pageSize",value = "页数据量",required = true,type = "Integer") Integer pageSize,
                                            @ApiParam(name = "pageNum",value = "页码",required = true,type = "Integer") Integer pageNum,
                                            @ApiParam(name = "memberId",value = "访问会员id",required = true,type = "Integer") Integer memberId,
                                            @ApiParam(name = "beiMemberId",value = "被访问会员id",required = true,type = "Integer") Integer beiMemberId){
        return circleOfFriendsService.memberDetailFriendsList(pageSize,pageNum,memberId,beiMemberId);
    }



    @ApiOperation(value = "世界圈列表(已完成)")
    @GetMapping("/worldCircleOfFriendsList")
    public ResultUtils worldCircleOfFriendsList(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
								@ApiParam(name = "pageSize",value = "页数据量",required = true,type = "Integer") Integer pageSize,
								@ApiParam(name = "pageNum",value = "页码",required = true,type = "Integer") Integer pageNum,
								@ApiParam(name = "id",value = "会员id",required = true,type = "Integer") Integer id
								){
	  return circleOfFriendsService.worldCircleOfFriendsList(pageSize,pageNum,id);
    }

    @ApiOperation(value = "是否已读")
    @GetMapping("/isRead")
    @ApiImplicitParams({
		  @ApiImplicitParam(name = "memberId", value = "用户id", required = true, dataType = "Integer"),
    })
    public ResultUtils isRead(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,Integer memberId){
	  return circleOfFriendsService.isRead(memberId);
    }
}

