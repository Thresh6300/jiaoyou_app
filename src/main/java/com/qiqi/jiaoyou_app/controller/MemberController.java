package com.qiqi.jiaoyou_app.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.qiqi.jiaoyou_app.Instant.GroupJson;
import com.qiqi.jiaoyou_app.Instant.MemberJson;
import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.controller.award.BaseController;
import com.qiqi.jiaoyou_app.pojo.Lable;
import com.qiqi.jiaoyou_app.pojo.Member;
import com.qiqi.jiaoyou_app.service.IMemberService;
import com.qiqi.jiaoyou_app.serviceImpl.RedisServiceImpl;
import com.qiqi.jiaoyou_app.sms.SmsUtil;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import com.qiqi.jiaoyou_app.vo.LableVo;
import com.qiqi.jiaoyou_app.vo.MemberVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * app会员表 前端控制器
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Api(tags = {"app会员表"})
@RestController
@RequestMapping("/jiaoyou_app/member")
public class MemberController extends BaseController
{

    @Autowired
    private IMemberService memberService;
    @Autowired
    private RedisServiceImpl redisService;

    @ApiOperation(value = "登录(已完成)")
    @PostMapping("/login")
    @DynamicParameters(name = "login", properties = {
		  @DynamicParameter(name = "phone", value = "账号", required = true, dataTypeClass = String.class), @DynamicParameter(name = "password", value = "密码", required = true, dataTypeClass = String.class),
    })
    public ResultUtils login(@RequestBody JSONObject jsonObject)
    {
	  Member member = JSON.toJavaObject(jsonObject, Member.class);
	  return memberService.login(member);
    }

    @ApiOperation(value = "发送短信验证码(已完成)")
    @PostMapping("/sendCode")
    @DynamicParameters(name = "login", properties = {
		  @DynamicParameter(name = "phone", value = "账号", required = true, dataTypeClass = String.class)
    })
    public ResultUtils sendCode(@RequestBody JSONObject jsonObject)
    {
	  Member member = JSON.toJavaObject(jsonObject, Member.class);
	  ResultUtils resultUtils = new ResultUtils();
	  String code = (((Math.random() * 9 + 1) * 100000) + "").substring(0, 6);
	  String send = SmsUtil.send(member.getPhone(), code);
	  JSONObject jsonObject1 = JSONObject.parseObject(send);
	  if (Integer.valueOf(jsonObject1.getString("code")) == 200)
	  {
		redisService.set(member.getPhone(), code, 300L);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setMessage("短信发送成功");
	  }
	  else
	  {
		resultUtils.setStatus(Constant.STATUS_FAILED);
		resultUtils.setMessage("短信发送失败");
	  }
	  return resultUtils;
    }

    @ApiOperation(value = "/验证码登录(已完成)")
    @PostMapping(value = "/codeLogin")
    @DynamicParameters(name = "codeLogin", properties = {
		  @DynamicParameter(name = "phone", value = "手机号码", required = true, dataTypeClass = String.class), @DynamicParameter(name = "code", value = "手机验证码", required = true, dataTypeClass = String.class)
    })
    public ResultUtils codeLogin(@RequestBody JSONObject jsonObject)
    {
	  Member member = JSON.toJavaObject(jsonObject, Member.class);
	  return memberService.codeLogin(member);
    }

    @ApiOperation(value = "忘记密码-修改密码(已完成)")
    @PostMapping(value = "/editPassword")
    @DynamicParameters(name = "editPassword", properties = {
		  @DynamicParameter(name = "phone", value = "手机号码", required = true, dataTypeClass = String.class), @DynamicParameter(name = "code", value = "手机验证码", required = true, dataTypeClass = String.class), @DynamicParameter(name = "password", value = "密码", required = true, dataTypeClass = String.class), @DynamicParameter(name = "oldPassword", value = " 确认密码", required = true, dataTypeClass = String.class),
    })
    public ResultUtils editPassword(@RequestBody JSONObject jsonObject)
    {
	  Member member = JSON.toJavaObject(jsonObject, Member.class);
	  return memberService.editPassword(member);
    }

    @ApiOperation(value = "颜值注册(已完成)")
    @PostMapping(value = "/yenValueRegister")
    @DynamicParameters(name = "yenValueRegister", properties = {
		  @DynamicParameter(name = "phone", value = "手机号码", required = true, dataTypeClass = String.class), @DynamicParameter(name = "password", value = "密码", required = true, dataTypeClass = String.class), @DynamicParameter(name = "oldPassword", value = "確認密码", required = true, dataTypeClass = String.class), @DynamicParameter(name = "sex", value = "性别", required = true, dataTypeClass = Integer.class), @DynamicParameter(name = "invitationCode", value = "邀请码", required = false, dataTypeClass = String.class), @DynamicParameter(name = "positivePhotoOfIDCard", value = "人脸照片 三张照片", required = false, dataTypeClass = String.class), @DynamicParameter(name = "video", value = "本人视频", required = true, dataTypeClass = String.class), @DynamicParameter(name = "longitude", value = "经度", required = false, dataTypeClass = String.class), @DynamicParameter(name = "latitude", value = "纬度", required = false, dataTypeClass = String.class)
    })
    public ResultUtils yenValueRegister(@RequestBody JSONObject jsonObject)
    {
	  Member member = JSON.toJavaObject(jsonObject, Member.class);
	  return memberService.yenValueRegister(member);
    }

    @ApiOperation(value = "车友注册(已完成)")
    @PostMapping(value = "/registrationRiders")
    @DynamicParameters(name = "registrationRiders", properties = {
		  @DynamicParameter(name = "phone", value = "手机号码", required = true, dataTypeClass = String.class), @DynamicParameter(name = "password", value = "密码", required = true, dataTypeClass = String.class), @DynamicParameter(name = "oldPassword", value = "確認密码", required = true, dataTypeClass = String.class), @DynamicParameter(name = "sex", value = "性别", required = true, dataTypeClass = Integer.class), @DynamicParameter(name = "positivePhotoOfIDCard", value = "身份证正面照片", required = true, dataTypeClass = String.class), @DynamicParameter(name = "facePhoto", value = "现场拍照", required = true, dataTypeClass = String.class), @DynamicParameter(name = "drivingLicensePhoto", value = "行驶证照片", required = true, dataTypeClass = String.class), @DynamicParameter(name = "carModel", value = "车辆型号", required = true, dataTypeClass = String.class), @DynamicParameter(name = "invitationCode", value = "邀请码", required = false, dataTypeClass = String.class)
    })
    public ResultUtils registrationRiders(@RequestBody JSONObject jsonObject)
    {
	  Member member = JSON.toJavaObject(jsonObject, Member.class);
	  return memberService.registrationRiders(member);
    }


    @ApiOperation(value = "黑卡注册(已完成)")
    @PostMapping(value = "/registrationBlack")
    @DynamicParameters(name = "registrationBlack", properties = {
		  @DynamicParameter(name = "phone", value = "手机号码", required = true, dataTypeClass = String.class), @DynamicParameter(name = "password", value = "密码", required = true, dataTypeClass = String.class), @DynamicParameter(name = "oldPassword", value = "確認密码", required = true, dataTypeClass = String.class), @DynamicParameter(name = "sex", value = "性别", required = true, dataTypeClass = Integer.class), @DynamicParameter(name = "positivePhotoOfIDCard", value = "身份证正面照片", required = true, dataTypeClass = String.class), @DynamicParameter(name = "facePhoto", value = "人脸照片", required = true, dataTypeClass = String.class), @DynamicParameter(name = "video", value = "现场视频", required = true, dataTypeClass = String.class), @DynamicParameter(name = "registrationModel", value = "注册机型 1：苹果 2：安卓", required = true, dataTypeClass = Integer.class), @DynamicParameter(name = "invitationCode", value = "邀请码", required = false, dataTypeClass = String.class)
    })
    public ResultUtils registrationBlack(@RequestBody JSONObject jsonObject)
    {
	  Member member = JSON.toJavaObject(jsonObject, Member.class);
	  return memberService.registrationBlack(member);
    }

    @ApiOperation(value = "注册完成输入(已完成)")
    @PostMapping(value = "/registrationComplete")
    @DynamicParameters(name = "registrationComplete", properties = {
		  @DynamicParameter(name = "memberId", value = "会员id", required = true, dataTypeClass = Integer.class), @DynamicParameter(name = "nickName", value = "昵称", required = true, dataTypeClass = String.class), @DynamicParameter(name = "lableHeight", value = "身高", required = true, dataTypeClass = Double.class), @DynamicParameter(name = "lableWeight", value = "体重", required = true, dataTypeClass = Double.class), @DynamicParameter(name = "lableBirthday", value = "生日", required = true, dataTypeClass = String.class),
    })
    public ResultUtils registrationComplete(@RequestBody JSONObject jsonObject)
    {
	  Lable lable = JSON.toJavaObject(jsonObject, Lable.class);
	  return memberService.registrationComplete(lable);
    }

    @ApiOperation(value = "会员详情(已完成)")
    @GetMapping(value = "/membershipDetails")
    public ResultUtils aopmembershipDetails(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, Integer id)
    {
	  return memberService.membershipDetails(id);
    }

    @ApiOperation(value = "推荐朋友(已完成)")
    @GetMapping(value = "/recommendAFriend")
    public ResultUtils aoprecommendAFriend(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @ApiParam(name = "id", value = "会员id", required = true, type = "Integer") Integer id,
							 @ApiParam(name = "numberOfReferrals", value = "推荐人数", required = true, type = "Integer") Integer numberOfReferrals)
    {
	  return memberService.recommendAFriend(id, numberOfReferrals);
    }


    @ApiOperation(value = "流星雨详情(已完成)")
    @GetMapping(value = "/meteorShowerDetails")
    public ResultUtils aopmeteorShowerDetails(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @ApiParam(name = "pageSize", value = "页数据量", required = true, type = "Integer") Integer pageSize, @ApiParam(name = "pageNum", value = "页码", required = true, type = "Integer") Integer pageNum, @ApiParam(name = "id", value = "会员id", required = true, type = "Integer") Integer id)
    {

	  return memberService.meteorShowerDetails(pageSize, pageNum, id);
    }

    @ApiOperation(value = "车友匹配(已完成)")
    @GetMapping(value = "/ridersMatch")
    public ResultUtils aopridersMatch(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @ApiParam(name = "longitude", value = "经度", required = true, type = "String") String longitude, @ApiParam(name = "latitude", value = "纬度", required = true, type = "String") String latitude, @ApiParam(name = "pageNum", value = "页数", required = true, type = "Integer") Integer pageNum, @ApiParam(name = "pageSize", value = "每页数据量", required = true, type = "Integer") Integer pageSize, @ApiParam(name = "id", value = "会员id", required = true, type = "Integer") Integer id, @ApiParam(name = "sex", value = "性别 1：男2：女", required = true, type = "Integer") Integer sex, @ApiParam(name = "startAge", value = "开始年龄", required = true, type = "Integer") Integer startAge, @ApiParam(name = "endAge", value = "结束年龄", required = true, type = "Integer") Integer endAge, @ApiParam(name = "type", value = "1附近;2活跃3新人", required = true, type = "Integer") String type)
    {
	  return memberService.ridersMatch(id, longitude, latitude, pageNum, pageSize, sex, startAge, endAge, type);
    }


    @ApiOperation(value = "灵魂匹配(已完成)")
    @GetMapping(value = "/soulMatch")
    public ResultUtils aopsoulMatch(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @ApiParam(name = "longitude", value = "经度", required = true, type = "String") String longitude, @ApiParam(name = "latitude", value = "纬度", required = true, type = "String") String latitude, @ApiParam(name = "pageNum", value = "页数", required = true, type = "Integer") Integer pageNum, @ApiParam(name = "pageSize", value = "每页数据量", required = true, type = "Integer") Integer pageSize, @ApiParam(name = "id", value = "会员id", required = true, type = "Integer") Integer id, @ApiParam(name = "sex", value = "性别 1：男2：女", required = true, type = "Integer") Integer sex, @ApiParam(name = "startAge", value = "开始年龄", required = true, type = "Integer") Integer startAge, @ApiParam(name = "endAge", value = "结束年龄", required = true, type = "Integer") Integer endAge, @ApiParam(name = "type", value = "1附近;2活跃3新人", required = true, type = "String") String type)
    {
	  return memberService.soulMatch(id, longitude, latitude, pageNum, pageSize, sex, startAge, endAge, type);
    }


    //魅力为啥子别人还没有接受礼物你就给别人加上了,什么鬼逻辑????????
    @ApiOperation(value = "魅力榜(已完成)")
    @GetMapping(value = "/charmList")
    public ResultUtils aopcharmList(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @ApiParam(name = "numberOfPeople", value = "人数", required = true, type = "Integer") Integer numberOfPeople, @ApiParam(name = "type", value = "1:每日；2：每周；3：每月", required = true, type = "Integer") Integer type, @ApiParam(name = "id", value = "用户id", required = true, type = "String") String id)
    {
	  List<MemberVo> members = memberService.charmList();
	  if (members.size() > 0)
	  {
		String charm = (String) redisService.get("charm");
		if (StringUtils.isEmpty(charm) || !charm.equals(members.get(0).getId().toString()))
		{
		    redisService.delete("charmList");
		    redisService.set("charm", members.get(0).getId().toString(), 12L);
		}
		String charmList = (String) redisService.get("charmList");
		if (StringUtils.isEmpty(charmList))
		{
		    redisService.set("charmList", id, 12L);
		}
		else
		{
		    redisService.set("charmList", charmList + "," + id, 12L);
		}
	  }
	  else
	  {
		redisService.delete("charmList");
	  }
	  return memberService.charmList(numberOfPeople, type);
    }

    @ApiOperation(value = "是否已读")
    @GetMapping(value = "/isRead")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, type = "String")
    public ResultUtils isRead(String id)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  resultUtils.setData(0);
	  String charmList = (String) redisService.get("charmList");
	  String aopprideList = (String) redisService.get("aopprideList");
	  if (StringUtils.isNotBlank(charmList) && !charmList.contains(id) && StringUtils.isNotBlank(aopprideList) && !aopprideList.contains(id))
	  {
		resultUtils.setData(1);
	  }
	  if (StringUtils.isEmpty(charmList) || StringUtils.isEmpty(aopprideList))
	  {
		resultUtils.setData(1);
	  }
	  return resultUtils;
    }

    @ApiOperation(value = "豪气榜(已完成)")
    @GetMapping(value = "/prideList")
    public ResultUtils aopprideList(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
						@ApiParam(name = "numberOfPeople", value = "人数", required = true, type = "Integer") Integer numberOfPeople,
						@ApiParam(name = "type", value = "1:每日；2：每周；3：每月", required = true, type = "Integer") Integer type,
						@ApiParam(name = "id", value = "用户id", required = true, type = "String") Integer id)
    {
	  List<MemberVo> members = memberService.prideList();
	  if (members.size() > 0)
	  {
		String aoppride = (String) redisService.get("aoppride");
		if (StringUtils.isEmpty(aoppride) || !aoppride.equals(members.get(0).getId().toString()))
		{
		    redisService.delete("aopprideList");
		    redisService.set("aoppride", members.get(0).getId().toString(), 12L);
		}
		String aopprideList = (String) redisService.get("aopprideList");
		if (StringUtils.isEmpty(aopprideList))
		{
		    redisService.set("aopprideList", id.toString(), 12L);
		}
		else
		{
		    redisService.set("aopprideList", aopprideList + "," + id.toString(), 12L);
		}
	  }
	  else
	  {
		redisService.delete("aopprideList");
	  }
	  return memberService.prideList(numberOfPeople, type);
    }


/*    修改个人资料
    修改头像：选择相册，确认
    修改昵称：编辑昵称，确认
            选择所在城市
    修改手机号：原手机号（隐藏中间四位），原手机号验证码，新手机号，新手机验证码，确认
    修改密码：原密码，新密码，确认新密码，提交*/


    @ApiOperation(value = "原手机号(已完成)")
    @PostMapping(value = "/verificationCode")
    @DynamicParameters(name = "verificationCode", properties = {
		  @DynamicParameter(name = "id", value = " id", required = true, dataTypeClass = Integer.class), @DynamicParameter(name = "phone", value = "手机号码", required = true, dataTypeClass = String.class), @DynamicParameter(name = "code", value = "手机验证码", required = true, dataTypeClass = String.class)
    })
    public ResultUtils aopverificationCode(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject)
    {
	  Member member = JSON.toJavaObject(jsonObject, Member.class);
	  return memberService.verificationCode(member);
    }

    @ApiOperation(value = "新手机号(已完成)")
    @PostMapping(value = "/updatePhone")
    @DynamicParameters(name = "updatePhone", properties = {
		  @DynamicParameter(name = "id", value = " id", required = true, dataTypeClass = Integer.class), @DynamicParameter(name = "phone", value = "手机号码", required = true, dataTypeClass = String.class), @DynamicParameter(name = "code", value = "手机验证码", required = true, dataTypeClass = String.class)
    })
    public ResultUtils aopupdatePhone(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject)
    {
	  Member member = JSON.toJavaObject(jsonObject, Member.class);
	  return memberService.updatePhone(member);
    }


    @ApiOperation(value = "app内-修改密码(已完成)")
    @PostMapping(value = "/updatePassWord")
    @DynamicParameters(name = "updatePassWord", properties = {
		  @DynamicParameter(name = "id", value = " id", required = true, dataTypeClass = Integer.class), @DynamicParameter(name = "oldPassword", value = "原密码", required = true, dataTypeClass = String.class), @DynamicParameter(name = "password", value = "新密码", required = true, dataTypeClass = String.class)
    })
    public ResultUtils aopupdatePassWord(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject)
    {
	  Member member = JSON.toJavaObject(jsonObject, Member.class);
	  return memberService.updatePassWord(member);
    }

    @ApiOperation(value = "新增自定义标签（已完成）")
    @PostMapping(value = "/newCustomLabel")
    @DynamicParameters(name = "newCustomLabel", properties = {
		  @DynamicParameter(name = "memberId", value = " 会员id", required = true, dataTypeClass = Integer.class), @DynamicParameter(name = "type", value = "标签类型" + "1:魅力" + "2:城市" + "3:地方" + "4:爱好" + "5:学历" + "6:状态" + "7:年收入" + "8:车辆" + "9:形象" + "10:性格" + "11:行业", required = true, dataTypeClass = Integer.class), @DynamicParameter(name = "value", value = "标签值", required = true, dataTypeClass = String.class)
    })
    public ResultUtils aopnewCustomLabel(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject)
    {
	  Lable lable = JSON.toJavaObject(jsonObject, Lable.class);
	  return memberService.newCustomLabel(lable);
    }


    @ApiOperation(value = "修改标签选中（已完成）")
    @PostMapping(value = "/modifyLabelSelection")
    @DynamicParameters(name = "modifyLabelSelection", properties = {
		  @DynamicParameter(name = "memberId", value = " 会员id", required = true, dataTypeClass = Integer.class), @DynamicParameter(name = "type", value = "标签类型" + "1:魅力" + "2:城市" + "3:地方" + "4:爱好" + "5:学历" + "6:状态" + "7:年收入" + "8:车辆" + "9:形象" + "10:性格" + "11:行业", required = true, dataTypeClass = Integer.class), @DynamicParameter(name = "value", value = "标签值", required = true, dataTypeClass = String.class)
    })
    public ResultUtils aopmodifyLabelSelection(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject)
    {
	  Lable lable = JSON.toJavaObject(jsonObject, Lable.class);
	  return memberService.modifyLabelSelection(lable);
    }

    @ApiOperation(value = "修改自定义值（已完成）")
    @PostMapping(value = "/modifyCustomValues")
    @DynamicParameters(name = "modifyCustomValues", properties = {
		  @DynamicParameter(name = "memberId", value = " 会员id", required = true, dataTypeClass = Integer.class), @DynamicParameter(name = "type", value = "值类型" + "1:身高" + "2:体重" + "3:喝酒实力" + "4:我的宠物" + "5:自我介绍", required = true, dataTypeClass = Integer.class), @DynamicParameter(name = "value", value = "值", required = true, dataTypeClass = String.class)
    })
    public ResultUtils aopmodifyCustomValues(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject)
    {
	  Lable lable = JSON.toJavaObject(jsonObject, Lable.class);
	  return memberService.modifyCustomValues(lable);
    }


    @ApiOperation(value = "修改头像及昵称(已完成)")
    @PostMapping(value = "/changeYourAvatarAndNickname")
    @DynamicParameters(name = "changeYourAvatarAndNickname", properties = {
		  @DynamicParameter(name = "id", value = " id", required = true, dataTypeClass = Integer.class), @DynamicParameter(name = "type", value = "值类型" + "1:头像" + "2:昵称", required = true, dataTypeClass = Integer.class), @DynamicParameter(name = "value", value = "值", required = true, dataTypeClass = String.class)
    })
    public ResultUtils aopchangeYourAvatarAndNickname(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject)
    {
	  Member member = JSON.toJavaObject(jsonObject, Member.class);
	  return memberService.changeYourAvatarAndNickname(member);
    }


    @ApiOperation(value = "修改背景图(已完成)")
    @PostMapping(value = "/editBackGroundImage")
    @DynamicParameters(name = "editBackGroundImage", properties = {
		  @DynamicParameter(name = "id", value = " id", required = true, dataTypeClass = Integer.class), @DynamicParameter(name = "backgroundImages", value = "值", required = true, dataTypeClass = String.class)
    })
    public ResultUtils aopeditBackGroundImage(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject)
    {
	  Member member = JSON.toJavaObject(jsonObject, Member.class);
	  return memberService.editBackGroundImage(member);
    }

    @ApiOperation(value = "修改所在城市(已完成)")
    @PostMapping(value = "/modifyCity")
    @DynamicParameters(name = "modifyCity", properties = {
		  @DynamicParameter(name = "id", value = "id", required = true, dataTypeClass = Integer.class), @DynamicParameter(name = "province", value = "省", required = true, dataTypeClass = String.class), @DynamicParameter(name = "city", value = "市", required = true, dataTypeClass = String.class), @DynamicParameter(name = "area", value = "区", required = true, dataTypeClass = String.class),
    })
    public ResultUtils aopmodifyCity(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject)
    {
	  Member member = JSON.toJavaObject(jsonObject, Member.class);
	  return memberService.modifyCity(member);
    }


    @ApiOperation(value = "修改个人资料(已完成)")
    @PostMapping(value = "/modifyPersonalData")
    @DynamicParameters(name = "modifyCity", properties = {
		  @DynamicParameter(name = "id", value = "id", required = true, dataTypeClass = Integer.class),
		  @DynamicParameter(name = "name", value = "姓名", required = true, dataTypeClass = String.class),
		  @DynamicParameter(name = "nickName", value = "昵称", required = true, dataTypeClass = String.class),
		  @DynamicParameter(name = "head", value = "头像", required = true, dataTypeClass = String.class),
		  @DynamicParameter(name = "age", value = "年龄", required = true, dataTypeClass = Integer.class),
		  @DynamicParameter(name = "sex", value = "性别", required = true, dataTypeClass = Integer.class),
		  @DynamicParameter(name = "province", value = "省", required = true, dataTypeClass = String.class),
		  @DynamicParameter(name = "city", value = "市", required = true, dataTypeClass = String.class),
		  @DynamicParameter(name = "area", value = "区", required = true, dataTypeClass = String.class),
		  @DynamicParameter(name = "lable", value = "标签类", required = true, dataTypeClass = LableVo.class),
    })
    public ResultUtils aopmodifyPersonalData(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
							   @RequestBody JSONObject jsonObject)
    {
	  Member member = JSON.toJavaObject(jsonObject, Member.class);
	  return memberService.modifyPersonalData(member);
    }


    @ApiOperation(value = "状态(已完成)")
    @GetMapping("/canISynchronize")
    public ResultUtils aopcanISynchronize(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @ApiParam(name = "id", value = "会员ID", required = true, type = "Integer") Integer id, @ApiParam(name = "type", value = "1:查看是否可以开启同步HC大咖秀" + "2：查看车辆审核状态", required = true, type = "Integer") Integer type)
    {
	  return memberService.canISynchronize(id, type);
    }

    @ApiOperation(value = "退出登录(已完成)")
    @GetMapping("/logout")
    public ResultUtils aoplogout(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, Integer memberId)
    {
	  return memberService.logout(memberId);
    }


    @ApiOperation(value = "接收推送ID(已完成)")
    @PostMapping(value = "/pushId")
    @DynamicParameters(name = "modifyCity", properties = {
		  @DynamicParameter(name = "id", value = "id", required = true, dataTypeClass = Integer.class), @DynamicParameter(name = "pushId", value = "推送ID", required = true, dataTypeClass = String.class)
    })
    public ResultUtils aoppushId(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject)
    {
	  Member member = JSON.toJavaObject(jsonObject, Member.class);
	  return memberService.pushId(member);
    }


    @ApiOperation(value = "客服列表(已完成)")
    @GetMapping("/customerList")
    public ResultUtils aopcustomerList(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token)
    {
	  return memberService.customerList();
    }


    @ApiOperation(value = "上传头像人脸对比(已完成)")
    @GetMapping("/faceContrast")
    public ResultUtils faceContrast(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @ApiParam(name = "head", value = "头像", required = true, type = "String") String head, @ApiParam(name = "image", value = "人脸照片", required = true, type = "String") String image)
    {
	  return memberService.faceContrast(head, image);
    }




    @ApiOperation(value = "上传经纬度(已完成)")
    @PostMapping(value = "/uploadAddress")
    @DynamicParameters(name = "uploadAddress", properties = {
		  @DynamicParameter(name = "id", value = " id", required = true, dataTypeClass = Integer.class), @DynamicParameter(name = "longitude", value = "经度", required = true, dataTypeClass = String.class), @DynamicParameter(name = "latitude", value = "纬度", required = true, dataTypeClass = String.class)
    })
    public ResultUtils aopuploadAddress(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject)
    {
	  Member member = JSON.toJavaObject(jsonObject, Member.class);
	  return memberService.uploadAddress(member);
    }

    @ApiOperation(value = "查询本月邀请list(已完成)")
    @GetMapping("/invitationThisMonth")
    public ResultUtils invitationThisMonth(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, Integer memberId)
    {
	  return memberService.invitationThisMonth(memberId);
    }



    @ApiOperation(value = "加入群聊(已完成)")
    @GetMapping("/addGroup")
    public ResultUtils aopAddGroup(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, Integer memberId)
    {
	  GroupJson groupJson = new GroupJson();
	  groupJson.setGroupId("@TGS#2NA7BGSGI");
	  List<MemberJson> list = new ArrayList<>();
	  list.add(new MemberJson(memberId.toString()));
	  groupJson.setMemberList(list);
	  return memberService.addGroupMember(groupJson);
    }


    @ApiOperation(value = "计算距离(已完成)")
    @GetMapping("/calculateTheDistance")
    public ResultUtils aopCalculateTheDistance(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @ApiParam(name = "memberId", value = "自身ID", required = true, type = "Integer") Integer memberId, @ApiParam(name = "otherPartyId", value = "对方ID", required = true, type = "Integer") Integer otherPartyId)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  Member member = getMemberInfo(token);
	  if (member != null)
	  {
		updateMember(member);
		return memberService.aopCalculateTheDistance(memberId, otherPartyId);
	  }
	  resultUtils.setCode(400);
	  resultUtils.setMessage("系统没有获取到你的信息，请稍后重试");
	  return resultUtils;
    }



    @ApiOperation(value = "判断手机号有没有被注册(已完成)")
    @GetMapping(value = "/isHavePhone")
    public ResultUtils isHavePhone(String phone)
    {
	  return memberService.isHavePhone(phone);
    }



    @ApiOperation(value = "删除好友(已完成)")
    @GetMapping(value = "/delFriend")
    public ResultUtils delFriend(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, Integer memberId, Integer otherId)
    {
	  ResultUtils resultUtils = new ResultUtils();

	  Member member = getMemberInfo(token);
	  if (member != null)
	  {
		updateMember(member);
		return memberService.delFriend(memberId, otherId);
	  }
	  resultUtils.setCode(400);
	  resultUtils.setMessage("系统没有获取到你的信息，请稍后重试");
	  return resultUtils;
    }

}

