package com.qiqi.jiaoyou_app.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.qiqi.jiaoyou_app.pojo.DynamicReviewOfFriendsCircle;
import com.qiqi.jiaoyou_app.pojo.DynamicVo;
import com.qiqi.jiaoyou_app.service.IDynamicReviewOfFriendsCircleService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.*;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 朋友圈动态评论表 前端控制器
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Api(tags = {"朋友圈动态评论"})
@RestController
@RequestMapping("/jiaoyou_app/dynamicReviewOfFriendsCircle")
public class DynamicReviewOfFriendsCircleController {

    @Autowired
    private IDynamicReviewOfFriendsCircleService dynamicReviewOfFriendsCircleService;

    @ApiOperation(value = "朋友圈动态评论(已完成)")
    @PostMapping("/dynamicComment")
    @DynamicParameters(name = "DynamicCircleOfFriendsComments",properties = {
            @DynamicParameter(name = "level",value = "评论级别 1：直接评论动态2：回复评论",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "dynamicIdOrCommentId",value = "动态id/评论id",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "memberId",value = "会员id",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "memberHead",value = "会员头像",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "memberNickName",value = "会员昵称",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "context",value = "评论内容",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "type",value = "0朋友圈;1世界圈",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "oneselfId",value = "动态的id",required = true,dataTypeClass = String.class),
    })
    public ResultUtils aopdynamicDetails(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject){
        DynamicReviewOfFriendsCircle dynamicReviewOfFriendsCircle = JSON.toJavaObject(jsonObject,DynamicReviewOfFriendsCircle.class);
        return dynamicReviewOfFriendsCircleService.dynamicDetails(dynamicReviewOfFriendsCircle);
    }


    @ApiOperation(value = "删除评论(已完成)")
    @GetMapping("/deleteComments")
    public ResultUtils aopDeleteComments(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
                                      @ApiParam(name = "memberId",value = "会员id",required = true,type = "Integer") Integer memberId,
                                      @ApiParam(name = "commentId",value = "评论id",required = true,type = "Integer") Integer commentId,
                                      @ApiParam(name = "dynamicId",value = "动态id",required = true,type = "Integer") Integer dynamicId,
                                      @ApiParam(name = "type",value = "1:删除子评论 2：不删除子评论",required = true,type = "Integer") Integer type,
                                      @ApiParam(name = "category",value = "1:朋友圈动态2：车友圈动态",required = true,type = "Integer") Integer category){
        return dynamicReviewOfFriendsCircleService.aopDeleteComments(memberId,commentId,dynamicId,type,category);
    }



    @ApiOperation(value = "我的评论列表(已完成)")
    @PostMapping("/MyDynamicList")
    @DynamicParameters(name = "loveList",properties = {
		  @DynamicParameter(name = "oneselfId",value = "用户id",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils MyDynamicList(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject){
	  DynamicVo dynamicVo = JSON.toJavaObject(jsonObject, DynamicVo.class);
	  return dynamicReviewOfFriendsCircleService.MyDynamicList(dynamicVo);
    }

    @ApiOperation(value = "清空我的评论列表(已完成)")
    @PostMapping("/emptyMyDynamicList")
    @DynamicParameters(name = "loveList",properties = {
		  @DynamicParameter(name = "oneselfId",value = "用户id",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils emptyMyDynamicList(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject){
	  DynamicVo dynamicVo = JSON.toJavaObject(jsonObject, DynamicVo.class);
	  return dynamicReviewOfFriendsCircleService.emptyMyDynamicList(dynamicVo);
    }


    @ApiOperation(value = "我的评论列表说明(已完成)")
    @PostMapping("/list")
    public void list(DynamicVo dynamicVo)
    {

    }

    @ApiOperation(value = "我的评论列表(已完成)")
    @GetMapping("/MyDynamicList")
    @DynamicParameters(name = "loveList",properties = {
		  @DynamicParameter(name = "oneselfId",value = "用户id",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils isReply(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject){
	  DynamicVo dynamicVo = JSON.toJavaObject(jsonObject, DynamicVo.class);
	  return dynamicReviewOfFriendsCircleService.MyDynamicList(dynamicVo);
    }

    @ApiOperation(value = "是否已读")
    @GetMapping("/isApply")
    @ApiImplicitParams({
		  @ApiImplicitParam(name = "memberId", value = "用户id", required = true, dataType = "Integer"),
    })
    public ResultUtils isApply(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,Integer memberId){
	  return dynamicReviewOfFriendsCircleService.isApply(memberId);
    }
}

