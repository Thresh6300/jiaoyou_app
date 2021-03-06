package com.qiqi.jiaoyou_app.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.qiqi.jiaoyou_app.mapper.MemberMapper;
import com.qiqi.jiaoyou_app.pojo.AcceptOfflineActivities;
import com.qiqi.jiaoyou_app.pojo.Member;
import com.qiqi.jiaoyou_app.pojo.SendOfflineActivities;
import com.qiqi.jiaoyou_app.serviceImpl.SendOfflineActivitiesServiceImpl;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.*;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * <p>
 * 线下活动列表 前端控制器
 * </p>
 *
 * @author GR123
 * @since 2020-06-16
 */
@Api(tags = {"发起聚会"})
@RestController
@RequestMapping("/jiaoyou_app/sendOfflineActivities")
public class SendOfflineActivitiesController {

    private final static Logger logger = LoggerFactory.getLogger(SendOfflineActivitiesController.class);

    @Autowired
    private SendOfflineActivitiesServiceImpl sendOfflineActivitiesService;
    @Autowired
    private MemberMapper memberMapper;

    @ApiOperation(value = "发起聚会(已完成)")
    @PostMapping(value = "/startAParty")
    @DynamicParameters(name = "startAParty",properties = {
            @DynamicParameter(name = "sendMemberId",value = "发起人ID",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "activityTheme",value = "主题",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "address",value = "地址",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "perSize",value = "活动人数",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "averageDiamondsSize",value = "平均钻石数",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "startTimeStr",value = "开始时间(yyyy-MM-dd HH:mm)",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "lable",value = "标签",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "latitude",value = "纬度",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "longitude",value = "经度",required = true,dataTypeClass = String.class),
    })
    public ResultUtils aopstartAParty(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,@RequestBody JSONObject jsonObject){
        SendOfflineActivities sendOfflineActivities = JSON.toJavaObject(jsonObject,SendOfflineActivities.class);
	  logger.info(String.valueOf(sendOfflineActivities));
        return sendOfflineActivitiesService.startAParty(sendOfflineActivities);
    }


    @ApiOperation(value = "我发起/参与的(已完成)")
    @GetMapping(value = "/Initiated")
    public ResultUtils aopInitiated(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
                                 @ApiParam(name = "pageSize",value = "页数据量",required = true,type = "Integer") Integer pageSize,
                                 @ApiParam(name = "pageNum",value = "页码",required = true,type = "Integer") Integer pageNum,
                                 @ApiParam(name = "id",value = "会员ID",required = true,type = "Integer") Integer id,
                                 @ApiParam(name = "type",value = "1:发起2:参与",required = true,type = "Integer") Integer type){
        return sendOfflineActivitiesService.Initiated(pageNum,pageSize,id,type);
    }



    @ApiOperation(value = "聚会详情(已完成)")
    @GetMapping(value = "/partyDetails")
    public ResultUtils aoppartyDetails(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
                                 @ApiParam(name = "id",value = "聚会ID",required = true,type = "Integer") Integer id,
                                 @ApiParam(name = "memberId",value = "会员ID",required = true,type = "Integer") Integer memberId

    )
    {
        return sendOfflineActivitiesService.partyDetails(id,memberId);
    }


    @ApiOperation(value = "报名参加(已完成)")
    @PostMapping(value = "/enterFor")
    @DynamicParameters(name = "startAParty",properties = {
            @DynamicParameter(name = "sendOfflineActivities",value = "聚会ID",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "acceptMemberId",value = "报名人ID",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "keepAnAppointmentTimeStr",value = "到场时间",required = true,dataTypeClass = String.class)
    })
    public ResultUtils aopenterFor(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,@RequestBody JSONObject jsonObject){
        AcceptOfflineActivities acceptOfflineActivities = JSON.toJavaObject(jsonObject, AcceptOfflineActivities.class);
        return sendOfflineActivitiesService.enterFor(acceptOfflineActivities);
    }


    @ApiOperation(value = "同意/拒绝申请人参加(已完成)")
    @GetMapping(value = "/beInFor")
    public ResultUtils aopbeInFor(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
                               @ApiParam(name = "id",value = "申请ID",required = true,type = "Integer") Integer id,
                               @ApiParam(name = "type",value = "2:通过 9：未通过",required = true,type = "Integer") Integer type){
        return sendOfflineActivitiesService.beInFor(id,type);
    }


    @ApiOperation(value = "对方点击确定按钮(已完成)")
    @PostMapping(value = "/clickOK")
    @DynamicParameters(name = "clickOK",properties = {
            @DynamicParameter(name = "acceptMemberId",value = "会员ID",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "sendOfflineActivities",value = "聚会ID",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "latitude",value = "纬度",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "longitude",value = "经度",required = true,dataTypeClass = String.class),
    })
    public ResultUtils aopclickOK(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,@RequestBody JSONObject jsonObject){
        AcceptOfflineActivities acceptOfflineActivities = JSON.toJavaObject(jsonObject, AcceptOfflineActivities.class);
        return sendOfflineActivitiesService.clickOK(acceptOfflineActivities);
    }


    @ApiOperation(value = "发起方点击确定按钮(已完成)")
    @PostMapping(value = "/initiatorClickOK")
    @DynamicParameters(name = "initiatorClickOK",properties = {
            @DynamicParameter(name = "id",value = "聚会ID",required = true,dataTypeClass = Integer.class)
    })
    public ResultUtils aopinitiatorClickOK(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,@RequestBody JSONObject jsonObject){
        SendOfflineActivities sendOfflineActivities = JSON.toJavaObject(jsonObject,SendOfflineActivities.class);
        return sendOfflineActivitiesService.initiatorClickOK(sendOfflineActivities,token);
    }


    @ApiOperation(value = "发起方取消聚会(已完成)")
    @PostMapping(value = "/closeParty")
    @DynamicParameters(name = "closeParty",properties = {
            @DynamicParameter(name = "id",value = "聚会ID",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "sendMemberId",value = "会员ID",required = true,dataTypeClass = Integer.class)
    })
    public ResultUtils aopcloseParty(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,@RequestBody JSONObject jsonObject){
        SendOfflineActivities sendOfflineActivities = JSON.toJavaObject(jsonObject,SendOfflineActivities.class);
        return sendOfflineActivitiesService.closeParty(sendOfflineActivities);
    }



    @ApiOperation(value = "报名方提交取消申请(已完成)")
    @PostMapping(value = "/submitCancellationApplication")
    @DynamicParameters(name = "submitCancellationApplication",properties = {
            @DynamicParameter(name = "sendOfflineActivities",value = "聚会ID",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "acceptMemberId",value = "会员ID",required = true,dataTypeClass = Integer.class)
    })
    public ResultUtils aopsubmitCancellationApplication(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,@RequestBody JSONObject jsonObject){
        AcceptOfflineActivities acceptOfflineActivities = JSON.toJavaObject(jsonObject,AcceptOfflineActivities.class);
        return sendOfflineActivitiesService.submitCancellationApplication(acceptOfflineActivities);
    }

    @ApiOperation(value = "报名方强制取消(已完成)")
    @PostMapping(value = "/compulsoryCancellation")
    @DynamicParameters(name = "compulsoryCancellation",properties = {
            @DynamicParameter(name = "sendOfflineActivities",value = "聚会ID",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "acceptMemberId",value = "会员ID",required = true,dataTypeClass = Integer.class)
    })
    public ResultUtils aopcompulsoryCancellation(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,@RequestBody JSONObject jsonObject){
        AcceptOfflineActivities acceptOfflineActivities = JSON.toJavaObject(jsonObject,AcceptOfflineActivities.class);
        return sendOfflineActivitiesService.compulsoryCancellation(acceptOfflineActivities);
    }

    @ApiOperation(value = "我参与的和我发布的列表")
    @GetMapping("/participate")
    @ApiImplicitParams({
		  @ApiImplicitParam(name = "sendMemberId", value = "用户id", required = true, dataType = "Integer"),
		  @ApiImplicitParam(name = "pageSize",value = "页数据量",required = true,dataType = "Integer"),
		  @ApiImplicitParam(name = "pageNum",value = "页码",required = true,dataType = "Integer"),
		  @ApiImplicitParam(name = "seacrh",value = "搜索",required = true,dataType = "seacrh"),
    })
    public ResultUtils participate(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @ApiIgnore SendOfflineActivities sendOfflineActivities){
	  List<Member> member = memberMapper.selectList(new EntityWrapper<Member>().eq("token",token));
	  if(member != null){
	      sendOfflineActivities.setSendMemberId(member.get(0).getId());
	  }
        return sendOfflineActivitiesService.participate(sendOfflineActivities);
    }

    @ApiOperation(value = "我要报名的列表")
    @GetMapping("/apply")
    @ApiImplicitParams({
		  @ApiImplicitParam(name = "sendMemberId", value = "用户id", required = true, dataType = "Integer"),
		  @ApiImplicitParam(name = "pageSize",value = "页数据量",required = true,dataType = "Integer"),
		  @ApiImplicitParam(name = "pageNum",value = "页码",required = true,dataType = "Integer"),
		  @ApiImplicitParam(name = "seacrh",value = "搜索",required = true,dataType = "seacrh"),
    })
    public ResultUtils apply(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @ApiIgnore SendOfflineActivities sendOfflineActivities){
	  return sendOfflineActivitiesService.apply(sendOfflineActivities);
    }

    @ApiOperation(value = "是否已读")
    @GetMapping("/isApply")
    @ApiImplicitParams({
		  @ApiImplicitParam(name = "memberId", value = "用户id", required = true, dataType = "Integer"),
    })
    public ResultUtils isApply(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,Integer memberId){
	  return sendOfflineActivitiesService.isApply(memberId);
    }


}

