package com.qiqi.jiaoyou_app.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.qiqi.jiaoyou_app.pojo.Club;
import com.qiqi.jiaoyou_app.pojo.ClubBuddy;
import com.qiqi.jiaoyou_app.pojo.ClubNotice;
import com.qiqi.jiaoyou_app.service.ClubNoticeService;
import com.qiqi.jiaoyou_app.service.ClubService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


/**
 * 俱乐部(Club)表控制层
 *
 * @author cfx
 * @since 2020-11-27 09:36:34
 */
@RestController
@RequestMapping("/jiaoyou_app/club")
@Api(tags = {"俱乐部"})
public class ClubController
{
    /**
     * 服务对象
     *
     * @author cfx
     * @since 2020-11-27 09:36:34
     */
    @Autowired
    private ClubService clubService;

    @Autowired
    private ClubNoticeService clubNoticeService;

    @ApiOperation(value = "字段说明(已完成)")
    @PostMapping(value = "/club")
    public void addClub(Club club){

    }

    @ApiOperation(value = "系统消息字段说明(已完成)")
    @PostMapping(value = "/ClubNot11ice")
    public void lsit(ClubNotice club){

    }

    @ApiOperation(value = "创建俱乐部(已完成)")
    @PostMapping(value = "/addClub")
    @DynamicParameters(name = "addClub",properties = {
		  @DynamicParameter(name = "clubName",value = "名称",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "clubIntroduction",value = "简介",required = true,dataTypeClass = String.class),
		  @DynamicParameter(name = "memberId",value = "创建人id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "wage",value = "每日工资",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "wageTime",value = "每日发工资的时间",required = true,dataTypeClass = String.class),
    })
    public ResultUtils addClub(@RequestBody JSONObject jsonObject){
	  Club club = JSON.toJavaObject(jsonObject, Club.class);
	  return clubService.addClub(club);
    }

    @ApiOperation(value = "邀请好友(已完成)")
    @PostMapping(value = "/inviteBuddy")
    @DynamicParameters(name = "inviteBuddy",properties = {
		  @DynamicParameter(name = "clubId",value = "俱乐部id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "oneselfId",value = "自身id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "memberIds",value = "会员id字符串数组",required = true,dataTypeClass = String.class),
    })
    public ResultUtils inviteBuddy(@RequestBody JSONObject jsonObject){
	  ClubBuddy clubBuddy = JSON.toJavaObject(jsonObject, ClubBuddy.class);
	  return clubService.inviteBuddy(clubBuddy);
    }

    @ApiOperation(value = "踢人")
    @PostMapping(value = "/delBuddy")
    @DynamicParameters(name = "delBuddy",properties = {
		  @DynamicParameter(name = "clubId",value = "俱乐部id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "memberIds",value = "会员id字符串数组",required = true,dataTypeClass = String.class),
		  @DynamicParameter(name = "memberId",value = "操作踢人的管理员",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils delBuddy(@RequestBody JSONObject jsonObject){
	  ClubBuddy clubBuddy = JSON.toJavaObject(jsonObject, ClubBuddy.class);
	  return clubService.delBuddy(clubBuddy);
    }


    @ApiOperation(value = "退出俱乐部")
    @PostMapping(value = "/quitBuddy")
    @DynamicParameters(name = "quitBuddy",properties = {
		  @DynamicParameter(name = "clubId",value = "俱乐部id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "memberId",value = "退出人的id",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils quitBuddy(@RequestBody JSONObject jsonObject){
	  ClubBuddy clubBuddy = JSON.toJavaObject(jsonObject, ClubBuddy.class);
	  return clubService.quitBuddy(clubBuddy);
    }


    @ApiOperation(value = "设置/取消俱乐部群秘书(已完成)")
    @PostMapping(value = "/setCancelSecretary")
    @DynamicParameters(name = "setCancelSecretary",properties = {
		  @DynamicParameter(name = "clubId",value = "俱乐部id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "memberIds",value = "会员id字符串数组",required = true,dataTypeClass = String.class),
		  @DynamicParameter(name = "secretaryStatus",value = "0不是秘书1是秘书",required = true,dataTypeClass = String.class),
		  @DynamicParameter(name = "memberId",value = "设置人的id",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils setCancelSecretary(@RequestBody JSONObject jsonObject){
	  ClubBuddy clubBuddy = JSON.toJavaObject(jsonObject, ClubBuddy.class);
	  return clubService.setSecretary(clubBuddy);
    }

    @ApiOperation(value = "俱乐部设置(已完成)")
    @PostMapping(value = "/setClub")
    @DynamicParameters(name = "setClub",properties = {
		  @DynamicParameter(name = "clubId",value = "俱乐部表id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "clubName",value = "名称",required = true,dataTypeClass = String.class),
		  @DynamicParameter(name = "clubIntroduction",value = "简介",required = true,dataTypeClass = String.class),
		  @DynamicParameter(name = "wage",value = "每日工资",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "wageTime",value = "发工资的时间",required = true,dataTypeClass = String.class),
		  @DynamicParameter(name = "clubNotice",value = "公告",required = true,dataTypeClass = String.class),
		  @DynamicParameter(name = "privacyMode",value = "隐私模式 0开; 1关",required = true,dataTypeClass = String.class),
    })
    public ResultUtils setClub(@RequestBody JSONObject jsonObject){
	  Club club = JSON.toJavaObject(jsonObject, Club.class);
	  return clubService.setClub(club);
    }
    @ApiOperation(value = "俱乐部设置2(已完成)")
    @PostMapping(value = "/setClubBuddy")
    @DynamicParameters(name = "setClub",properties = {
		  @DynamicParameter(name = "clubId",value = "俱乐部表id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "memberId",value = "会员id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "noDisturbing",value = "消息免打扰 0开-打扰; 1关-不打扰",required = true,dataTypeClass = String.class),
    })
    public ResultUtils setClubBuddy(@RequestBody JSONObject jsonObject){
	  ClubBuddy clubBuddy = JSON.toJavaObject(jsonObject, ClubBuddy.class);
	  return clubService.setClubBuddy(clubBuddy);
    }

    @ApiOperation(value = "俱乐部列表(已完成)")
    @PostMapping(value = "/clubList")
    @DynamicParameters(name = "setClub",properties = {
		  @DynamicParameter(name = "pageNum",value = "页数",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "pageSize",value = "每页数据量",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "memberId",value = "会员id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "type",value = "0潜力俱乐部;1我的;2参加的",required = true,dataTypeClass = String.class),
		  @DynamicParameter(name = "clubName",value = "俱乐部名称",required = false,dataTypeClass = String.class),
    })
    public ResultUtils clubList(@RequestBody JSONObject jsonObject){
	  Club club = JSON.toJavaObject(jsonObject, Club.class);
	  return clubService.clubList(club);
    }

    @ApiIgnore
    @ApiOperation(value = "搜索俱乐部列表(已完成)")
    @PostMapping(value = "/clubSearchList")
    @DynamicParameters(name = "clubSearchList",properties = {
		  @DynamicParameter(name = "clubName",value = "俱乐部名称",required = false,dataTypeClass = String.class),
    })
    public ResultUtils clubSearchList(@RequestBody JSONObject jsonObject){
	  Club club = JSON.toJavaObject(jsonObject, Club.class);
	  return clubService.clubSearchList(club);
    }

    @ApiOperation(value = "俱乐部详情(已完成)")
    @PostMapping(value = "/clubDetails")
    @DynamicParameters(name = "clubDetails",properties = {
		  @DynamicParameter(name = "clubId",value = "俱乐部id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "memberId",value = "会员id",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils clubDetails(@RequestBody JSONObject jsonObject){
	  Club club = JSON.toJavaObject(jsonObject, Club.class);
	  return clubService.selectById(club);
    }

    @ApiOperation(value = "是否是秘书(已完成)")
    @PostMapping(value = "/isSecretaryStatus")
    @DynamicParameters(name = "isSecretaryStatus",properties = {
		  @DynamicParameter(name = "clubId",value = "俱乐部id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "memberId",value = "会员id",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils isSecretaryStatus(@RequestBody JSONObject jsonObject){
	  ClubBuddy clubBuddy = JSON.toJavaObject(jsonObject, ClubBuddy.class);
	  return clubService.isSecretaryStatus(clubBuddy);
    }

    @ApiOperation(value = "解散俱乐部(已完成)")
    @PostMapping(value = "/dissolveClub")
    @DynamicParameters(name = "dissolveClub",properties = {
		  @DynamicParameter(name = "clubId",value = "俱乐部id",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils dissolveClub(@RequestBody JSONObject jsonObject){
	  Club club = JSON.toJavaObject(jsonObject, Club.class);
	  return clubService.dissolveClub(club);
    }


    @ApiOperation(value = "申请加入俱乐部(已完成)")
    @PostMapping(value = "/applyfor")
    @DynamicParameters(name = "applyfor",properties = {
		  @DynamicParameter(name = "buddyNoticeClubId",value = "俱乐部id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "buddyNoticeUserId",value = "申请人id",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils applyfor(@RequestBody JSONObject jsonObject){
	  ClubNotice clubNotice = JSON.toJavaObject(jsonObject, ClubNotice.class);
	  return clubService.applyfor(clubNotice);
    }

    @ApiOperation(value = "是否通过加入俱乐部(已完成)")
    @PostMapping(value = "/addApplyfor")
    @DynamicParameters(name = "addApplyfor",properties = {
		  @DynamicParameter(name = "buddyNoticeId",value = "通知id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "buddyNoticeStatus",value = "申请审核 0审核中;1通过2不通过",required = true,dataTypeClass = String.class),
		  @DynamicParameter(name = "buddyNoticeReason",value = "不通过理由",required = false,dataTypeClass = String.class),
    })
    public ResultUtils addApplyfor(@RequestBody JSONObject jsonObject){
	  ClubNotice clubNotice = JSON.toJavaObject(jsonObject, ClubNotice.class);
	  return clubService.addApplyfor(clubNotice);
    }

    @ApiOperation(value = "是否在俱乐部(已完成)")
    @PostMapping(value = "/inClubStatus")
    @DynamicParameters(name = "inClubStatus",properties = {
		  @DynamicParameter(name = "clubId",value = "俱乐部id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "memberId",value = "会员id",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils inClubStatus(@RequestBody JSONObject jsonObject){
	  ClubBuddy clubBuddy = JSON.toJavaObject(jsonObject, ClubBuddy.class);
	  return clubService.inClubStatus(clubBuddy);
    }

    @ApiOperation(value = "俱乐部消息列表(已完成)")
    @GetMapping(value = "/clubNotice")
    @DynamicParameters(name = "clubNotice",properties = {
		  @DynamicParameter(name = "userId",value = "用户id",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils clubNotice(Integer userId){

	  ResultUtils resultUtils = clubService.clubNotice(userId);
	  List<ClubNotice> data = (List<ClubNotice>) resultUtils.getData();

	  if (data != null && data.size() > 0)
	  {
		for (ClubNotice clubNotice : data)
		{
		    if (StringUtils.isEmpty(clubNotice.getIsReads()))
		    {
			  clubNotice.setIsReads(userId.toString());
		    }
		    else if (!clubNotice.getIsReads().contains(userId.toString()))
		    {
			  clubNotice.setIsReads(clubNotice.getIsReads() + "," + userId);
		    }
		}
		clubNoticeService.updateBatchById(data);
	  }
	  return resultUtils;
    }
    @ApiOperation(value = "是否已读俱乐部消息列表(已完成)")
    @GetMapping(value = "/isclubNotice")
    @DynamicParameters(name = "isclubNotice",properties = {
		  @DynamicParameter(name = "userId",value = "用户id",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils isclubNotice(Integer userId)
    {
	  return clubService.isclubNotice(userId);
    }
    @ApiOperation(value = "删除俱乐部消息已完成)")
    @GetMapping(value = "/delNotice")
    @DynamicParameters(name = "delNotice",properties = {
		  @DynamicParameter(name = "buddyNoticeId",value = "通知id",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils delNotice(Integer buddyNoticeId){
	  return clubService.delNotice(buddyNoticeId);
    }

    @ApiOperation(value = "统计与排行)")
    @GetMapping(value = "/ranking")
    @DynamicParameters(name = "delNotice",properties = {
		  @DynamicParameter(name = "clubId",value = "俱乐部id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "type",value = "1:每日；2：每周；3：每月",required = true,dataTypeClass = Integer.class),

    })
    public ResultUtils ranking(Integer clubId, Integer type){
	  return clubService.ranking(clubId,type);
    }



}