package com.qiqi.jiaoyou_app.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.Instant.GroupJson;
import com.qiqi.jiaoyou_app.Instant.NewFriendList;
import com.qiqi.jiaoyou_app.baiduface.ImageFormatType;
import com.qiqi.jiaoyou_app.baiduface.PersonVerify;
import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.controller.TLSSigAPIv2;
import com.qiqi.jiaoyou_app.controller.award.Award;
import com.qiqi.jiaoyou_app.mapper.*;
import com.qiqi.jiaoyou_app.pojo.*;
import com.qiqi.jiaoyou_app.service.ClubService;
import com.qiqi.jiaoyou_app.service.IMemberService;
import com.qiqi.jiaoyou_app.util.*;
import com.qiqi.jiaoyou_app.vo.JsonRootBean;
import com.qiqi.jiaoyou_app.vo.MemberVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * app会员表 服务实现类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@SuppressWarnings("ALL")
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService
{

	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private RedisServiceImpl redisService;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private CarMapper carMapper;
	@Autowired
	private LevelMapper levelMapper;
	@Autowired
	private MemberAssetsMapper memberAssetsMapper;
	@Autowired
	private CircleOfFriendsMapper circleOfFriendsMapper;
	@Autowired
	private MembershipSettingsMapper membershipSettingsMapper;
	@Autowired
	private LableMapper lableMapper;
	@Autowired
	private MemberMatchingLogMapper memberMatchingLogMapper;
	@Autowired
	private RechargeRecordMapper rechargeRecordMapper;

	@Autowired
	private PlatformParameterSettingMapper platformParameterSettingMapper;
	@Autowired
	private MemberAssetsServiceImpl memberAssetsService;
	@Autowired
	private RechargeRecordServiceImpl rechargeRecordService;

	@Autowired
	private MemberServiceImpl memberService;

	@Autowired
	private ClubService clubService;

	public static boolean isTheSameDay(Date d1, Date d2)
	{
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)) && (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
	}

	public static Date Yesterday(Date date)
	{
		//获取当前时间24小时后的时间
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) + 24);
		Date time = c.getTime();
		return time;
	}

	//获取四个月后的今天
	public static Date getFourAfter()
	{
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(date); // 设置为当前时间
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 4); // 设置为上一个月
		date = calendar.getTime();
		return date;
	}

	//获取三年后的今天
	public static Date getThreeAfter()
	{
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(date); // 设置为当前时间
		calendar.set(Calendar.MONTH, calendar.get(Calendar.YEAR) + 3); // 设置为上一个月
		date = calendar.getTime();
		return date;
	}

	public static List<Member> gteList(List<Member> memberList, String longitude, String latitude, Integer pageSize)
	{
		if (memberList.size() < pageSize)
		{
			int size = memberList.size();
			for (int i = 0; i < size; i++)
			{
				//获取一个随机数
				Random random = new Random();
				int i1 = random.nextInt(size);
				memberList.add(memberList.get(i1));
			}
		}
		for (Member member1 : memberList)
		{
			if (longitude == null || "".equals(longitude) || latitude == null || "".equals(latitude) || member1.getLongitude() == null || "".equals(member1.getLongitude()) || member1.getLatitude() == null || "".equals(member1.getLatitude()))
			{
				member1.setDistance(Constant.UNKNOWN);
				member1.setDistances(1000000.00);
			}
			else
			{
				double distance = LocationUtils.getDistance(longitude, latitude, member1.getLongitude(), member1.getLatitude());
				member1.setDistances(distance);
				member1.setDistance(distance / 1000 + "KM");
			}
		}
		return memberList;
	}

	@Override
	public ResultUtils login(Member member)
	{
		ResultUtils resultUtils = new ResultUtils();
		List<Member> phone = memberMapper.selectList(new EntityWrapper<Member>().eq("phone", member.getPhone()).orderBy("addTime", false));
		if (phone.size() > 0)
		{
			Member member1 = phone.get(0);
			if ("1".equals(member1.getDelUserStatus()))
			{
				resultUtils.setStatus(Constant.STATUS_FAILED);
				resultUtils.setMessage("账户不存在");
				return resultUtils;
			}
			if (member1.getEnableSate() == 2)
			{
				resultUtils.setStatus(Constant.STATUS_FAILED);
				resultUtils.setMessage(Constant.ACCOUNT_HAS_BEEN_DISABLED);
				return resultUtils;
			}
			else
			{
				if (member1.getExamineState() == 2)
				{
					resultUtils.setStatus(2);
					resultUtils.setMessage(Constant.AUDIT_FAILED + HtmlUtils.Html2Text(member1.getReason()));
					resultUtils.setData(member1.getRegistrationChannel());
					resultUtils.setData1(member1.getRegistrationChannel());
					return resultUtils;
				}
				if (member1.getExamineState() == 3)
				{
					resultUtils.setStatus(3);
					resultUtils.setMessage("该账号正在审核中，请稍后再试");
					return resultUtils;
				}
				if (MD5.GetMD5Code(member.getPassword()).equals(member1.getPassword()))
				{

					// if (member1.getIsvip() == 2)
					// {
					// resultUtils.setStatus(500);
					// resultUtils.setMessage("您的会员,已到期,请您先开通");
					// return resultUtils;
					// }

					//修改token登录次数以及在线状态
					String s = member1.getPassword() + System.currentTimeMillis();
					String md5String = MD5.GetMD5Code(s);
					member1.setToken(md5String);
					member1.setLoginSize(member1.getLoginSize() == null || member1.getLoginSize() == 0 ? 1 : member1.getLoginSize() + 1);
					//				0:不在线1：在线
					member1.setOnLine(1);
					member1.setTodayLoginTime(new Date());
					if (member1.getLoginSize() == 1)
					{
						member1.setLoginTimeofOne(new Timestamp(System.currentTimeMillis()));
					}

					Integer integer = memberMapper.updateById(member1);
					if (integer > 0)
					{
						resultUtils.setData1(TLSSigAPIv2.genSig(member1.getId() + "", 15552000L, null));
						resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
						resultUtils.setMessage(Constant.LOGIN_SECCESS);
						resultUtils.setData(member1);
						return resultUtils;
					}
					else
					{
						resultUtils.setStatus(Constant.STATUS_FAILED);
						resultUtils.setMessage(Constant.LOGIN_ERROR);
						return resultUtils;
					}
				}
				else
				{
					resultUtils.setStatus(Constant.STATUS_FAILED);
					resultUtils.setMessage(Constant.WRONG_ACCOUNT_NAME_OR_PASSWORD);
					return resultUtils;
				}
			}
		}
		else
		{
			resultUtils.setStatus(Constant.STATUS_FAILED);
			resultUtils.setMessage(Constant.NOT_REGISTERED_YET);
			return resultUtils;
		}
	}

	@Override
	public ResultUtils membershipDetails(Integer id)
	{
		ResultUtils resultUtils = new ResultUtils();
		Member member = memberMapper.selectById(id);
		if (member == null)
		{
			resultUtils.setMessage(Constant.NOT_FOUND);
			resultUtils.setStatus(Constant.STATUS_FAILED);
		}
		else
		{
			//查询用户等级
			//1.先查询所有的等级
			List<MemberAssets> memberId = memberAssetsMapper.selectList(new EntityWrapper<MemberAssets>().eq("memberId", member.getId()));
			if (memberId != null && memberId.size() > 0)
			{
				Long meberExperienceSize = memberId.get(0).getMeberExperienceSize();
				if (meberExperienceSize == null)
				{
					meberExperienceSize = 0L;
				}
				member.setLevelDifference("距离下一等级还需" + (1000 - meberExperienceSize) + "经验值");
			}
			//获取到会员设置
			List<MembershipSettings> memberId1 = membershipSettingsMapper.selectList(new EntityWrapper<MembershipSettings>().eq("memberId", member.getId()));
			if (memberId1.size() <= 0)
			{
				MembershipSettings membershipSettings = new MembershipSettings();
				membershipSettings.setMemberId(member.getId());
				membershipSettings.setSameCityWithinState(1);
				membershipSettings.setSameCityExternalState(1);
				membershipSettings.setConfidentialityOfInformationState(1);
				membershipSettings.setFriendMessageState(1);
				membershipSettings.setNoticeState(1);
				membershipSettings.setDynamicResponseState(1);
				Integer insert = membershipSettingsMapper.insert(membershipSettings);
				if (insert > 0)
				{
					member.setMembershipSettings(membershipSettings);
				}
				else
				{
					resultUtils.setMessage(Constant.SERVER_ERROR);
					resultUtils.setStatus(Constant.STATUS_FAILED);
					return resultUtils;
				}
			}
			else
			{
				member.setMembershipSettings(memberId1.get(0));
			}
			//查询标签
			Lable member_id = lableMapper.selectOne(new Lable().setMemberId(member.getId()));
			if (member_id != null)
			{
				member.setLable(member_id);
			}

			//查询车辆

			Car car = new Car();
			car.setDeledeState(2);
			car.setMemberId(member.getId());
			Car car2 = carMapper.selectOne(car);
			if (car2 == null)
			{
				car2 = new Car();
			}
			member.setCar(car2);
			List<Club> clubs = clubService.selectList(new EntityWrapper<Club>().eq("memberId", member.getId()));
			if (clubs == null || clubs.size() == 0)
			{
				clubs = new ArrayList<Club>();
			}
			member.setClubs(clubs);
			resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
			resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
			resultUtils.setData(member);
		}
		return resultUtils;
	}

	@Override
	public ResultUtils yenValueRegister(Member member)
	{
		ResultUtils resultUtils = new ResultUtils();
		//判断手机号是否被使用
		List<Member> phone = memberMapper.selectList(new EntityWrapper<Member>().eq("phone", member.getPhone()).ne("examineState", 2));
		//判斷兩次密碼是否一致
		if (!member.getPassword().equals(member.getOldPassword()))
		{
			resultUtils.setMessage(Constant.PASSWORD_INCONSISTENCY);
			resultUtils.setStatus(Constant.STATUS_FAILED);
			return resultUtils;
		}
		if (phone.size() > 0)
		{
			Member member1 = phone.get(0);
			if (member1.getExamineState()==2)
			{
				member1.setExamineState(3);
				memberMapper.updateById(member1);
				resultUtils.setMessage(Constant.lOGIN_WAS_SUCCESSFUL);
				resultUtils.setData(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Yesterday(new Date())));
				resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
				return resultUtils;
			}
			resultUtils.setMessage(Constant.MOBILE_PHONE_NUMBER_HAS_BEEN_REGISTERED);
			resultUtils.setStatus(Constant.STATUS_FAILED);
			return resultUtils;
		}
		member.setFacePhoto(member.getPositivePhotoOfIDCard());
		//判断是否时同一个人
		//liangzhao
		String[] split = member.getPositivePhotoOfIDCard().split(",");
		//三张照片
		// String[] facePhoto = member.getFacePhoto().split(",");
        /*
        //人脸照片
        //查询人脸相似度
        PlatformParameterSetting platformParameterSetting = platformParameterSettingMapper.selectById(1);
        //比对
        for (int i = 0; i < split.length; i++) {
            String s = PersonVerify.personVerify(split[i], facePhoto);
            JsonRootBean jsonRootBean = JsonUtils.jsonToPojo(s, JsonRootBean.class);
            if (!Constant.SUCCESS.equals(jsonRootBean.getError_msg())) {
                //比对失败
                resultUtils.setMessage(ImageFormatType.getValue(Integer.valueOf(jsonRootBean.getError_code())));
                resultUtils.setStatus(Constant.STATUS_FAILED);
                return resultUtils;
            }
            //比对相似度阈值
            if (jsonRootBean.getResult().getScore() < platformParameterSetting.getFaceSimilarity()) {
                //证明 未通过人脸验证
                resultUtils.setMessage("比对相似度：" + jsonRootBean.getResult().getScore() + "，相似度未到达标准值，注册失败");
                resultUtils.setStatus(Constant.STATUS_FAILED);
                return resultUtils;
            }
        }*/
		//证明 通过人脸验证
		//昵称、姓名、头像
		String code = (((Math.random() * 9 + 1) * 100000) + "").substring(0, 6);
		member.setNickName(Constant.USER_ + code);
		member.setName(Constant.USER_ + code);
		member.setHead(split[0]);
		member.setBackgroundImages(split[0]);
		//登录次数
		member.setLoginSize(0);
		//注册方式
		member.setRegistrationChannel(1);
		//审核状态
		member.setExamineState(3);
		//等级
		member.setLevel(0);
		//启用状态
		member.setEnableSate(1);
		//是否是vip
		member.setIsvip(2);
		//注册时间
		member.setAddTime(new Timestamp(System.currentTimeMillis()));
		//密码格式化
		member.setPassword(MD5.GetMD5Code(member.getPassword()));
		if (member.getLongitude() == null || member.getLongitude().equals(""))
		{

		}
		else
		{
			List<String> add = AddressUntils.getAdd(member.getLongitude(), member.getLatitude());
			member.setProvince(add.get(0));
			member.setCity(add.get(1));
			member.setArea(add.get(2));
		}
		//根据邀请码查询父级
		if (member.getInvitationCode() == null)
		{

		}
		else
		{
			List<Member> invitationCode = memberMapper.selectList(new EntityWrapper<Member>().eq("invitationCode", member.getInvitationCode()));
			if (invitationCode.size() > 0)
			{
				member.setPid(invitationCode.get(0).getId());
				Member member1 = invitationCode.get(0);
				member1.setRecommended(member1.getRecommended() + 1);
				memberMapper.updateById(member1);
			}
		}
		getCode(member);
		//插入
		Integer insert = memberMapper.insert(member);
		if (insert <= 0)
		{
			resultUtils.setMessage(Constant.INSERT_FAILED);
			resultUtils.setStatus(Constant.STATUS_FAILED);
			return resultUtils;
		}
		resultUtils.setMessage(Constant.lOGIN_WAS_SUCCESSFUL);
		resultUtils.setData(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Yesterday(new Date())));
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		return resultUtils;
	}

	@Override
	public ResultUtils editPassword(Member member)
	{
		ResultUtils resultUtils = new ResultUtils();
		if (!member.getPassword().equals(member.getOldPassword()))
		{
			resultUtils.setMessage(Constant.PASSWORD_INCONSISTENCY);
			resultUtils.setStatus(Constant.STATUS_FAILED);
			return resultUtils;
		}
		if (verification(member, resultUtils))
		{
			return resultUtils;
		}
		List<Member> phone = memberMapper.selectList(new EntityWrapper<Member>().eq("phone", member.getPhone()));
		Member member1 = phone.get(0);
		//比对短信验证码成功
		member1.setPassword(MD5.GetMD5Code(member.getPassword()));
		Integer integer = memberMapper.updateById(member1);
		if (integer <= 0)
		{
			resultUtils.setMessage(Constant.PASSWORD_MODIFICATION_FAILED);
			resultUtils.setStatus(Constant.STATUS_FAILED);
			return resultUtils;
		}
		resultUtils.setMessage(Constant.PASSWORD_MODIFIED_SUCCESSFULLY);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		return resultUtils;
	}

	@Override
	public ResultUtils registrationRiders(Member member)
	{
		ResultUtils resultUtils = new ResultUtils();
		//判斷兩次密碼是否一致
		if (!member.getPassword().equals(member.getOldPassword()))
		{
			resultUtils.setMessage(Constant.PASSWORD_INCONSISTENCY);
			resultUtils.setStatus(Constant.STATUS_FAILED);
			return resultUtils;
		}
		//判断手机号是否被使用
		List<Member> phone = memberMapper.selectList(new EntityWrapper<Member>().eq("phone", member.getPhone()) .ne("examineState", 2));
		if (phone.size() > 0)
		{
			Member member1 = phone.get(0);
			if (member1.getExamineState()==2)
			{
				member1.setExamineState(3);
				memberMapper.updateById(member1);
				resultUtils.setMessage(Constant.lOGIN_WAS_SUCCESSFUL);
				resultUtils.setData(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Yesterday(new Date())));
				resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
				return resultUtils;
			}
			resultUtils.setMessage(Constant.MOBILE_PHONE_NUMBER_HAS_BEEN_REGISTERED);
			resultUtils.setStatus(Constant.STATUS_FAILED);
			return resultUtils;
		}

		//查询人脸相似度
		// PlatformParameterSetting platformParameterSetting = platformParameterSettingMapper.selectById(1);
		//人脸照片 现场拍照
		// String facePhoto = member.getFacePhoto();
		// String s = PersonVerify.personVerify(member.getPositivePhotoOfIDCard(), facePhoto);
		// JsonRootBean jsonRootBean = JsonUtils.jsonToPojo(s, JsonRootBean.class);
		// if (!Constant.SUCCESS.equals(jsonRootBean.getError_msg())) {
		//     //比对失败
		//     resultUtils.setMessage(ImageFormatType.getValue(Integer.valueOf(jsonRootBean.getError_code())));
		//     resultUtils.setStatus(Constant.STATUS_FAILED);
		//     return resultUtils;
		// }
		//比对相似度阈值
		// if (jsonRootBean.getResult().getScore() < platformParameterSetting.getFaceSimilarity()) {
		//     //证明 未通过人脸验证
		//     resultUtils.setMessage("比对相似度：" + jsonRootBean.getResult().getScore() + "，相似度未到达标准值，注册失败");
		//     resultUtils.setStatus(Constant.STATUS_FAILED);
		//     return resultUtils;
		// }
		//昵称、姓名、头像
		String code = (((Math.random() * 9 + 1) * 100000) + "").substring(0, 6);
		member.setNickName(Constant.USER_ + code);
		member.setName(Constant.USER_ + code);
		if (member.getSex() == 1)
		{
			member.setHead(Constant.NAN_PNG);
			member.setBackgroundImages(Constant.NAN_PNG);
		}
		else
		{
			member.setHead(Constant.NV_PNG);
			member.setBackgroundImages(Constant.NV_PNG);
		}
		String[] split = member.getFacePhoto().split(",");
		member.setHead(split[0]);
		//登录次数
		member.setLoginSize(0);
		//注册方式
		member.setRegistrationChannel(2);
		//审核状态
		member.setExamineState(3);
		//等级
		member.setLevel(0);
		//启用状态
		member.setEnableSate(1);
		//是否是vip
		member.setIsvip(2);
		//注册时间
		member.setAddTime(new Timestamp(System.currentTimeMillis()));
		//密码格式化
		member.setPassword(MD5.GetMD5Code(member.getPassword()));
		//根据邀请码查询父级
		if (member.getInvitationCode() == null)
		{

		}
		else
		{
			List<Member> invitationCode = memberMapper.selectList(new EntityWrapper<Member>().eq("invitationCode", member.getInvitationCode()));
			if (invitationCode.size() > 0)
			{
				member.setPid(invitationCode.get(0).getId());
				Member member1 = invitationCode.get(0);
				member1.setRecommended(member1.getRecommended() + 1);
				memberMapper.updateById(member1);
			}
		}
		getCode(member);

		//插入
		Integer insert = memberMapper.insert(member);
		if (insert <= 0)
		{
			resultUtils.setMessage(Constant.INSERT_FAILED);
			resultUtils.setStatus(Constant.STATUS_FAILED);
			return resultUtils;
		}
		resultUtils.setMessage(Constant.lOGIN_WAS_SUCCESSFUL);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setData(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Yesterday(new Date())));
		return resultUtils;
	}

	@Override
	public ResultUtils recommendAFriend(Integer id, Integer numberOfReferrals)
	{
		ResultUtils resultUtils = new ResultUtils();
		Member member = memberMapper.selectById(id);

		Map<String, Integer> map = new HashMap<>();
		map.put("numberOfReferrals", numberOfReferrals);
		map.put("sex", memberMapper.selectById(id).getSex());
		List<Member> members = memberMapper.recommendAFriend(map);
		resultUtils.setMessage(Constant.SUCCESSFUL_RECOMMENDATION);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		List<Member> members1 = gteList(members, member.getLongitude(), member.getLatitude(), numberOfReferrals);
		List<Member> collect = members1.stream().distinct().collect(Collectors.toList());

		resultUtils.setData(collect);
		return resultUtils;
	}

	@Override
	public ResultUtils ridersMatch(Integer id, String longitude, String latitude, Integer pageNum, Integer pageSize, Integer sex, Integer startAge, Integer endAge, String type)
	{
		if (startAge == null)
		{
			startAge = 0;

		}
		if (endAge == null)
		{
			endAge = 1000;

		}
		ResultUtils resultUtils = new ResultUtils();
		//先判断该名下有没有车辆绑定
		Member member = memberMapper.selectById(id);
		List<Car> cars = carMapper.selectList(new EntityWrapper<Car>().eq("memberId", id).eq("auditState", 2).eq("deledeState", 2));
		// if (cars.size() <= 0 || member.getRegistrationChannel() != 2)
		if (cars.size() <= 0)
		{
			resultUtils.setMessage(Constant.VEHICLE_BINDING_REQUIRED);
			resultUtils.setStatus(Constant.STATUS_FAILED);
			return resultUtils;
		}
		//根据性别，年龄，地区，车型等进行随机匹配
		Map<String, Object> map = new HashMap<>();
		map.put("pageNum", pageNum - 1);
		map.put("pageSize", pageSize);
		//地区
		String province = member.getProvince();
		String city = member.getCity();
		String area = member.getArea();
		map.put("city", "'" + city + "'");
		map.put("startAge", startAge);
		map.put("endAge", endAge);
		map.put("sexs", sex);
		map.put("id", id);
		//查询该会员的好友
		// GroupJson groupJson = new GroupJson();
		// groupJson.setFrom_Account(id.toString());
		List<UserDataItem> newFriendList = NewFriendList.friend_get(id.toString());
		String s = "(" + id;
		if (newFriendList == null || newFriendList.size() <= 0)
		{
			s += ")";
			map.put("fids", s);
		}
		else
		{
			//好友ID数组
			for (int i = 0; i < newFriendList.size(); i++)
			{
				s += "," + Integer.valueOf(newFriendList.get(i).getTo_Account());
			}
			s += ")";
			map.put("fids", s);
		}
		List<Member> memberList = memberMapper.ridersMatch(map);

		if (memberList.size() == 0)
		{
			resultUtils.setMessage(Constant.NO_DATA_AVAILABLE);
			resultUtils.setStatus(Constant.STATUS_FAILED);
			return resultUtils;
		}
		Integer[] integers = new Integer[memberList.size()];
		for (int i = 0; i < memberList.size(); i++)
		{
			integers[i] = memberList.get(i).getId();
		}

		List<Member> lists = getLists(gteList(memberList, longitude, latitude, pageSize), type);
		// List<Member> collect = lists.stream().distinct().collect(Collectors.toList());
		resultUtils.setMessage(Constant.Match_SUCCESSFUL);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setData(lists);
		return resultUtils;
	}

	@Override
	public ResultUtils soulMatch(Integer id, String longitude, String latitude, Integer pageNum, Integer pageSize, Integer sex, Integer startAge, Integer endAge, String type)
	{
		if (startAge == null)
		{
			startAge = 0;

		}
		if (endAge == null)
		{
			endAge = 1000;

		}

		//根据性别，年龄，地区等进行随机匹配，灵魂匹配结果用户为异性
		ResultUtils resultUtils = new ResultUtils();
		Member member = memberMapper.selectById(id);

		Map<String, Object> map = new HashMap<>();
		//性别
		if (sex == null)
		{
			if (1 == member.getSex())
			{
				sex = 2;
			}
			else
			{
				sex = 1;
			}
		}
		//地区
		String province = member.getProvince();
		String city = member.getCity();
		String area = member.getArea();
		map.put("city", "'" + city + "'");
		map.put("startAge", startAge);
		map.put("endAge", endAge);
		map.put("sex", sex);
		map.put("id", id);
		//查询该会员的好友
		// GroupJson groupJson = new GroupJson();
		// groupJson.setFrom_Account(id.toString());
		List<UserDataItem> newFriendList = NewFriendList.friend_get(id.toString());
		String s = "(" + id;
		if (newFriendList == null || newFriendList.size() <= 0)
		{
			s += ")";
			map.put("fids", s);
		}
		else
		{
			//好友ID数组
			for (int i = 0; i < newFriendList.size(); i++)
			{
				s += "," + Integer.valueOf(newFriendList.get(i).getTo_Account());
			}
			s += ")";
			map.put("fids", s);
		}

		map.put("pageNum", pageNum - 1);
		map.put("pageSize", pageSize);
		List<Member> memberList = memberMapper.soulMatch(map);
		// todayLoginTime onLineStatus
		// 三个阶段
		// 一个小时内显示在线
		// 1-12 小时显示在线时间 （1小时前。2小时前。。。。）
		// 12 以外显示 12小时前在线
		Date afterDate = DateUtils.getAfterDate(1);
		System.out.println(afterDate);
		// Date1.after(Date2),当Date1大于Date2时，返回TRUE，当小于等于时，返回false  在……之后；
		// Date1.before(Date2)，当Date1小于Date2时，返回TRUE，当大于等于时，返回false 在……之前；

		if (memberList.size() == 0)
		{
			resultUtils.setMessage(Constant.NO_DATA_AVAILABLE);
			resultUtils.setStatus(Constant.STATUS_FAILED);
			return resultUtils;
		}
		Integer[] integers = new Integer[memberList.size()];
		for (int i = 0; i < memberList.size(); i++)
		{
			integers[i] = memberList.get(i).getId();
		}
		resultUtils.setMessage(Constant.Match_SUCCESSFUL);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setData(getLists(gteList(memberList, longitude, latitude, pageSize), type));
		return resultUtils;
	}

	@Override
	public ResultUtils charmList(Integer numberOfPeople, Integer type)
	{
		ResultUtils resultUtils = new ResultUtils();
		Map<String, Object> map = new HashMap<>();
		switch (type)
		{
			case 1:
				map.put("type", "to_days(b.addTime) = to_days(now())");
				break;
			case 2:
				map.put("type", "YEARWEEK(date_format(b.addTime,'%Y-%m-%d')) = YEARWEEK(now())");
				break;
			case 3:
				map.put("type", "DATE_FORMAT( b.addTime, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )");
				break;
		}
		map.put("numberOfPeople", numberOfPeople);
		//根据收到的礼物价值钻石数量
		List<MemberVo> list = memberMapper.charmList(map);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
		resultUtils.setData(list);
		return resultUtils;
	}

	@Override
	public List<MemberVo> charmList()
	{

		ResultUtils resultUtils = new ResultUtils();
		Map<String, Object> map = new HashMap<>();
		map.put("type", "to_days(b.addTime) = to_days(now())");
		map.put("numberOfPeople", 1);
		//根据收到的礼物价值钻石数量
		return memberMapper.charmList(map);

	}

	@Override
	public ResultUtils prideList(Integer numberOfPeople, Integer type)
	{
		ResultUtils resultUtils = new ResultUtils();
		Map<String, Object> map = new HashMap<>();
		switch (type)
		{
			case 1:
				map.put("type", "to_days(b.addTime) = to_days(now())");
				break;
			case 2:
				map.put("type", "YEARWEEK(date_format(b.addTime,'%Y-%m-%d')) = YEARWEEK(now())");
				break;
			case 3:
				map.put("type", "DATE_FORMAT( b.addTime, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )");
				break;
		}
		map.put("numberOfPeople", numberOfPeople);
		List<MemberVo> list = memberMapper.prideList(map);
		//根据送出的礼物价值钻石数量
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
		resultUtils.setData(list);
		return resultUtils;
	}

	@Override
	public List<MemberVo> prideList()
	{
		ResultUtils resultUtils = new ResultUtils();
		Map<String, Object> map = new HashMap<>();
		map.put("type", "to_days(b.addTime) = to_days(now())");
		map.put("numberOfPeople", 1);
		return memberMapper.prideList(map);
	}

	@Override
	public ResultUtils updatePhone(Member member)
	{
		ResultUtils resultUtils = new ResultUtils();
		if (verification(member, resultUtils))
		{
			return resultUtils;
		}
		Integer integer = memberMapper.updateById(member);
		if (integer <= 0)
		{
			resultUtils.setMessage(Constant.PHONE_EDIT_ERROR);
			resultUtils.setStatus(Constant.STATUS_FAILED);
			return resultUtils;
		}
		resultUtils.setMessage(Constant.PHONR_EDIT_SUCCESS);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		return resultUtils;
	}

	@Override
	public ResultUtils verificationCode(Member member)
	{
		ResultUtils resultUtils = new ResultUtils();
		if (verification(member, resultUtils))
		{
			return resultUtils;
		}
		resultUtils.setMessage(Constant.VERIFICATION_CODE_SUCCESSFUL);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		return resultUtils;
	}

	@Override
	public ResultUtils updatePassWord(Member member)
	{
		ResultUtils resultUtils = new ResultUtils();
		//验证旧密码
		Member member1 = memberMapper.selectById(member.getId());
		if (!member1.getPassword().equals(MD5.GetMD5Code(member.getOldPassword())))
		{
			resultUtils.setMessage(Constant.PASSWORD_IS_INCORRECT);
			resultUtils.setStatus(Constant.STATUS_FAILED);
			return resultUtils;
		}
		//原始密码正确
		member.setPassword(MD5.GetMD5Code(member.getPassword()));
		Integer integer = memberMapper.updateById(member);
		if (integer <= 0)
		{
			resultUtils.setMessage(Constant.PASSWORD_MODIFICATION_FAILED);
			resultUtils.setStatus(Constant.STATUS_FAILED);
			return resultUtils;
		}
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setMessage(Constant.PASSWORD_MODIFIED_SUCCESSFULLY);
		return resultUtils;
	}

	@Override
	public ResultUtils meteorShowerDetails(Integer pageSize, Integer pageNum, Integer id)
	{
		//流星雨详情：用户头像，昵称，性别，年龄，所在城市；TA的朋友圈动态：标题，发布时间，部分动态内容，点赞量，评论量，转发；操作（送礼物）
		ResultUtils resultUtils = new ResultUtils();
		Member member = memberMapper.selectById(id);
		Page page = new Page(pageNum, pageSize);
		page.setOptimizeCountSql(true);
		page.setSearchCount(true);
		List<CircleOfFriends> memerId = circleOfFriendsMapper.selectPage(page, new EntityWrapper<CircleOfFriends>().eq("memerId", id));
		member.setCircleOfFriendsList(memerId);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
		resultUtils.setData(member);
		resultUtils.setCount((int) page.getTotal());
		return resultUtils;
	}

	/**
	 * @param lable
	 * @return
	 * @DynamicParameter(name = "type",value = "标签类型" +
	 * "1:魅力" +
	 * "2:城市" +
	 * "3:地方" +
	 * "4:爱好" +
	 * "5:学历" +
	 * "6:状态" +
	 * "7:年收入" +
	 * "8:车辆" +
	 * "9:形象" +
	 * "10:性格",required = true,dataTypeClass = String.class),
	 * @DynamicParameter(name = "value",value = "标签值",required = true,dataTypeClass = String.class)
	 */
	@Override
	public ResultUtils newCustomLabel(Lable lable)
	{
		ResultUtils resultUtils = new ResultUtils();
		List<Lable> member_id = lableMapper.selectList(new EntityWrapper<Lable>().eq("member_id", lable.getMemberId()));
		Lable lable1 = member_id.get(0);
		switch (lable.getType())
		{
			case 1:
				lable1.setLabelCharmDefault(lable1.getLabelCharmDefault() + "," + lable.getValue());
				break;
			case 2:
				lable1.setLabelCityDefault(lable1.getLabelCityDefault() + "," + lable.getValue());
				break;
			case 3:
				lable1.setLabelLocalDefault(lable1.getLabelLocalDefault() + "," + lable.getValue());
				break;
			case 4:
				lable1.setLabelHobbyDefault(lable1.getLabelHobbyDefault() + "," + lable.getValue());
				break;
			case 5:
				lable1.setLabelEducationDefault(lable1.getLabelEducationDefault() + "," + lable.getValue());
				break;
			case 6:
				lable1.setLableStateDefault(lable1.getLableStateDefault() + "," + lable.getValue());
				break;
			case 7:
				lable1.setLableAnnualIncomeDefault(lable1.getLableAnnualIncomeDefault() + "," + lable.getValue());
				break;
			case 8:
				lable1.setLableCarSizeDefault(lable1.getLableCarSizeDefault() + "," + lable.getValue());
				break;
			case 9:
				lable1.setLableImageDefault(lable1.getLableImageDefault() + "," + lable.getValue());
				break;
			case 10:
				lable1.setLableCharacterDefault(lable1.getLableCharacterDefault() + "," + lable.getValue());
				break;
			default:
				lable1.setLableIndustryDefault(lable1.getLableIndustryDefault() + "," + lable.getValue());
				break;
		}
		Integer integer = lableMapper.updateById(lable1);
		if (integer <= 0)
		{
			resultUtils.setMessage(Constant.CUSTOM_ALBLE_FAILED);
			resultUtils.setStatus(Constant.STATUS_FAILED);
			return resultUtils;
		}
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setMessage(Constant.CUSTOM_LABLE_SUCCESSED);
		return resultUtils;
	}

	@Override
	public ResultUtils modifyLabelSelection(Lable lable)
	{
		ResultUtils resultUtils = new ResultUtils();
		List<Lable> member_id = lableMapper.selectList(new EntityWrapper<Lable>().eq("member_id", lable.getMemberId()));
		Lable lable1 = member_id.get(0);
		switch (lable.getType())
		{
			case 1:
				lable1.setLabelCharmSelect(lable.getValue());
				break;
			case 2:
				lable1.setLabelCitySelect(lable.getValue());
				break;
			case 3:
				lable1.setLabelLocalSelect(lable.getValue());
				break;
			case 4:
				lable1.setLabelHobbySelect(lable.getValue());
				break;
			case 5:
				lable1.setLabelEducationSelect(lable.getValue());
				break;
			case 6:
				lable1.setLableStateSelect(lable.getValue());
				break;
			case 7:
				lable1.setLableAnnualIncomeSelect(lable.getValue());
				break;
			case 8:
				lable1.setLableCarSizeSelect(lable.getValue());
				break;
			case 9:
				lable1.setLableImageSelect(lable.getValue());
				break;
			case 10:
				lable1.setLableCharacterSelect(lable.getValue());
				break;
			default:
				lable1.setLableIndustrySelect(lable.getValue());
				break;
		}
		Integer integer = lableMapper.updateById(lable1);
		if (integer <= 0)
		{
			resultUtils.setMessage(Constant.LABLE_SELECTION_FAILED);
			resultUtils.setStatus(Constant.STATUS_FAILED);
			return resultUtils;
		}
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setMessage(Constant.TAG_SELECTED_SUECCESSFULLY);
		return resultUtils;
	}

	/**
	 * @param lable
	 * @return
	 * @DynamicParameter(name = "type",value = "值类型" +
	 * "1:身高" +
	 * "2:体重" +
	 * "3:喝酒实力" +
	 * "4:我的宠物" +
	 * "5:自我介绍",required = true,dataTypeClass = String.class),
	 */
	@Override
	public ResultUtils modifyCustomValues(Lable lable)
	{
		ResultUtils resultUtils = new ResultUtils();
		List<Lable> member_id = lableMapper.selectList(new EntityWrapper<Lable>().eq("member_id", lable.getMemberId()));
		Lable lable1 = member_id.get(0);
		switch (lable.getType())
		{
			case 1:
				lable1.setLableHeight(lable.getValue());
				break;
			case 2:
				lable1.setLableWeight(lable.getValue());
				break;
			case 3:
				lable1.setLableDrinkingPower(lable.getValue());
				break;
			case 4:
				lable1.setLablePets(lable.getValue());
				break;
			default:
				lable1.setLableIntroduce(lable.getValue());
				break;
		}
		Integer integer = lableMapper.updateById(lable1);
		if (integer <= 0)
		{
			resultUtils.setMessage(Constant.CUSTOM_VALUE_MODIFICATION_FAILED);
			resultUtils.setStatus(Constant.STATUS_FAILED);
			return resultUtils;
		}
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setMessage(Constant.USER_DEFINED_VALUE_MODIFIED_SUCCESSFULLY);
		return resultUtils;
	}

	@Override
	public ResultUtils changeYourAvatarAndNickname(Member member)
	{
		ResultUtils resultUtils = new ResultUtils();
		switch (member.getType())
		{
			case "1":
				member.setHead(member.getValue());
				break;
			default:
				//设置昵称的时候,需要判断,不能有相同的昵称,昵称想同的话,就提示昵称已存在
				List<Member> memberList = memberMapper.selectList(new EntityWrapper<Member>().eq("nickName", member.getValue()));
				if (memberList.size() > 0)
				{
					resultUtils.setStatus(500);
					resultUtils.setMessage("昵称已存在,请更换后重试!");
					return resultUtils;
				}
				member.setNickName(member.getValue());
				break;
		}
		Integer integer = baseMapper.updateById(member);
		if (integer <= 0)
		{
			resultUtils.setMessage(Constant.EDIT_ERROR);
			resultUtils.setStatus(Constant.STATUS_FAILED);
			return resultUtils;
		}
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setMessage(Constant.EDIT_SUCCESSFULLY);
		return resultUtils;
	}

	@Override
	public ResultUtils modifyCity(Member member)
	{
		ResultUtils resultUtils = new ResultUtils();
		Integer integer = baseMapper.updateById(member);
		if (integer <= 0)
		{
			resultUtils.setMessage(Constant.EDIT_ERROR);
			resultUtils.setStatus(Constant.STATUS_FAILED);
			return resultUtils;
		}
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setMessage(Constant.EDIT_SUCCESSFULLY);
		return resultUtils;
	}

	@Override
	public ResultUtils modifyPersonalData(Member member)
	{
		ResultUtils resultUtils = new ResultUtils();
		Member member1 = baseMapper.selectById(member);
		//为了防止出现  UPDATE member WHERE id=？ 没有set sql语句报错
		member.setToken(member1.getToken());
		//判断该会员是不是官方账户
		Integer isCustomer = member1.getIsCustomer();
		if (isCustomer == 1)
		{
			//判断该会员的昵称中是否含有官方标识
			String nickName = member.getNickName();
			if (nickName == null)
			{

			}
			else
			{
				if (nickName.indexOf(Constant.OFFICIAL) < 0)
				{
					member.setNickName(member.getNickName() + Constant.OFFICIAL);
				}
			}
		}
		else
		{
			//判断该会员的昵称中是否含有官方标识
			String nickName = member.getNickName();
			if (nickName == null || nickName.indexOf(Constant.OFFICIAL) >= 0)
			{
				resultUtils.setMessage(Constant.ILLEGAL_MEMBER_NICKNAME);
				resultUtils.setStatus(Constant.STATUS_FAILED);
				return resultUtils;
			}
		}
		/***
		 * @Description: 如果用户修改了头像 那么把头像设置为背景图
		 * @Author: update nan
		 * @Date: 2021-01-28 11:19
		 */
		if (member.getHead() != "" || member.getHead() != null && !member1.getNickName().contains("用户"))
		{
			member.setBackgroundImages(member.getHead());
		}

		Integer integer = baseMapper.updateById(member);
		Lable lable = member.getLable();
		if (lable == null)
		{
			lable = new Lable();
		}
		List<Lable> member_id = lableMapper.selectList(new EntityWrapper<Lable>().eq("member_id", member.getId()));
		if (member_id != null && member_id.size() > 0)
		{
			Lable lable1 = member_id.get(0);
			lable.setLabelId(lable1.getLabelId());
			lable.setMemberId(member.getId());
			Integer integer1 = lableMapper.updateById(lable);
			resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
			resultUtils.setMessage(Constant.EDIT_SUCCESSFULLY);
			return resultUtils;
		}
		lable.setMemberId(member.getId());
		Integer insert1 = lableMapper.insert(lable);
		//为了防止出现  UPDATE lable    WHERE label_id=? 没有set sql语句报错

		if (integer <= 0 || insert1 <= 0)
		{
			resultUtils.setMessage(Constant.EDIT_ERROR);
			resultUtils.setStatus(Constant.STATUS_FAILED);
			return resultUtils;
		}
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setMessage(Constant.EDIT_SUCCESSFULLY);
		return resultUtils;
	}

	@Override
	public ResultUtils canISynchronize(Integer id, Integer type)
	{
		ResultUtils resultUtils = new ResultUtils();
		if (type == 1)
		{
			Member member = memberMapper.selectById(id);
			if (member.getRegistrationChannel() == 2)
			{
				resultUtils.setMessage(Constant.SYNCHRONIZATION_CAN_BE_TURNED_ON);
				resultUtils.setData(1);
				resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
			}
			else
			{
				List<Car> memberId = carMapper.selectList(new EntityWrapper<Car>().eq("memberId", id).eq("deledeState", 2).eq("auditState", 2));
				if (memberId.size() > 0)
				{
					resultUtils.setMessage(Constant.SYNCHRONIZATION_CAN_BE_TURNED_ON);
					resultUtils.setData(1);
					resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
				}
				else
				{
					resultUtils.setMessage(Constant.SYNCHRONIZATION_CANNOT_BE_TURNED_ON);
					resultUtils.setData(2);
					resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
				}
			}
		}
		else
		{
			List<Car> memberId = carMapper.selectList(new EntityWrapper<Car>().eq("memberId", id).eq("deledeState", 2));
			if (memberId.size() <= 0)
			{
				resultUtils.setMessage(Constant.VEHICLE_NOT_BOUND);
				resultUtils.setData(0);
				resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
			}
			else
			{
				Car car = memberId.get(0);
				if (car.getAuditState() == 1)
				{
					resultUtils.setMessage(Constant.UNDER_REVIEW);
					resultUtils.setData(1);
					resultUtils.setData1(Constant.UNDER_REVIEW);
					resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
				}
				else if (car.getAuditState() == 2)
				{
					resultUtils.setMessage(Constant.AUDIT_PASSED);
					resultUtils.setData(2);
					resultUtils.setData1(Constant.SUCCESSFULLY_BOUND);
					resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
				}
				else if (car.getAuditState() == 3)
				{
					resultUtils.setMessage(Constant.AUDIT_FAILED1);
					resultUtils.setData(3);
					resultUtils.setData1(car.getReason());
					resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
				}
			}
		}
		return resultUtils;
	}

	@Override
	public ResultUtils codeLogin(Member member)
	{
		ResultUtils resultUtils = new ResultUtils();
		if (verification(member, resultUtils))
		{
			return resultUtils;
		}
		List<Member> phone = memberMapper.selectList(new EntityWrapper<Member>().eq("phone", member.getPhone()).orderBy("addTime", false));
		if (phone.size() > 0)
		{
			Member member1 = phone.get(0);
			if ("1".equals(member1.getDelUserStatus()))
			{
				resultUtils.setStatus(Constant.STATUS_FAILED);
				resultUtils.setMessage("账户不存在");
				return resultUtils;
			}
			if (member1.getEnableSate() == 2)
			{
				resultUtils.setStatus(Constant.STATUS_FAILED);
				resultUtils.setMessage(Constant.ACCOUNT_HAS_BEEN_DISABLED);
				return resultUtils;
			}
			else
			{
				if (member1.getExamineState() == 2)
				{
					resultUtils.setStatus(2);
					resultUtils.setMessage(Constant.AUDIT_FAILED + HtmlUtils.Html2Text(member1.getReason()));
					resultUtils.setData(member1.getRegistrationChannel());
					resultUtils.setData1(member1.getRegistrationChannel());
					return resultUtils;
				}
				if (member1.getExamineState() == 3)
				{
					resultUtils.setStatus(3);
					resultUtils.setMessage("该账号正在审核中，请稍后再试");
					return resultUtils;
				}
				// if (member1.getIsvip() == 2)
				// {
				//   resultUtils.setStatus(500);
				//   resultUtils.setMessage("您的会员,已到期,请您先开通");
				//   return resultUtils;
				// }
				//修改token以及登录次数
				String s = member1.getPassword() + System.currentTimeMillis();
				String md5String = MD5.GetMD5Code(s);
				member1.setToken(md5String);
				member1.setLoginSize(member1.getLoginSize() == null ? 1 : member1.getLoginSize() + 1);
				Integer integer = memberMapper.updateById(member1);
				if (integer > 0)
				{
					resultUtils.setData1(TLSSigAPIv2.genSig(member1.getId() + "", 15552000L, null));
					resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
					resultUtils.setMessage(Constant.LOGIN_SECCESS);
					resultUtils.setData(member1);
					return resultUtils;
				}
				else
				{
					resultUtils.setStatus(Constant.STATUS_FAILED);
					resultUtils.setMessage(Constant.LOGIN_ERROR);
					return resultUtils;
				}

			}
		}
		else
		{
			resultUtils.setStatus(Constant.STATUS_FAILED);
			resultUtils.setMessage(Constant.NOT_REGISTERED_YET);
			return resultUtils;
		}
	}

	@Override
	public ResultUtils editBackGroundImage(Member member)
	{
		ResultUtils resultUtils = new ResultUtils();
		Integer integer = memberMapper.updateById(member);
		if (integer > 0)
		{
			resultUtils.setMessage(Constant.BACKGROUND_MAP_MODIFIED_SUCCESSFULLY);
			resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		}
		else
		{
			resultUtils.setMessage(Constant.FAILED_TO_MODIFY_BACKGROUND_IMAGE);
			resultUtils.setStatus(Constant.STATUS_FAILED);
		}
		return resultUtils;
	}

	@Override
	public ResultUtils logout(Integer memberId)
	{
		ResultUtils resultUtils = new ResultUtils();
		Member member = new Member();
		member.setId(memberId);
		member.setPushId("0");
		member.setToken("0");
		Integer integer = memberMapper.updateById(member);
		if (integer <= 0)
		{
			resultUtils.setStatus(Constant.STATUS_FAILED);
			resultUtils.setMessage(Constant.EXIT_FAILED);
		}
		else
		{
			resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
			resultUtils.setMessage(Constant.EXIT_SUCCEEDED);
		}
		return resultUtils;
	}

	@Override
	public ResultUtils pushId(Member member)
	{
		ResultUtils resultUtils = new ResultUtils();
		Integer integer = memberMapper.updateById(member);
		if (integer <= 0)
		{
			resultUtils.setStatus(Constant.STATUS_FAILED);
			resultUtils.setMessage(Constant.FAILED_TO_SAVE_PUSH_ID);
		}
		else
		{
			resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
			resultUtils.setMessage(Constant.PUSH_ID_SAVED_SUCCESSFULLY);
		}
		return resultUtils;
	}

	@Override
	public ResultUtils customerList()
	{
		ResultUtils resultUtils = new ResultUtils();
		List<Member> members = memberMapper.selectList(new EntityWrapper<Member>().eq("isCustomer", 1).eq("examineState", 1).eq("enableSate", 1));
		resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setData(members);
		return resultUtils;
	}

	@Override
	public ResultUtils faceContrast(String head, String image)
	{
		ResultUtils resultUtils = new ResultUtils();
		String s = PersonVerify.personVerify(head, image);
		JsonRootBean jsonRootBean = JsonUtils.jsonToPojo(s, JsonRootBean.class);
		//查询人脸相似度
		PlatformParameterSetting platformParameterSetting = platformParameterSettingMapper.selectById(1);
		if (!Constant.SUCCESS.equals(jsonRootBean.getError_msg()))
		{
			//比对失败
			resultUtils.setMessage(ImageFormatType.getValue(Integer.valueOf(jsonRootBean.getError_code())));
			resultUtils.setStatus(Constant.STATUS_FAILED);
			return resultUtils;
		}
		//比对相似度阈值
		if (jsonRootBean.getResult().getScore() < platformParameterSetting.getFaceSimilarity())
		{
			//证明 未通过人脸验证
			resultUtils.setMessage("比对相似度：" + jsonRootBean.getResult().getScore() + "，相似度未到达标准值");
			resultUtils.setStatus(Constant.STATUS_FAILED);
			return resultUtils;
		}
		resultUtils.setMessage(Constant.SUCCESSFUL_COMPARISON);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		return resultUtils;
	}

	@Override
	public ResultUtils registrationBlack(Member member)
	{
		ResultUtils resultUtils = new ResultUtils();
		//判断手机号是否被使用
		List<Member> phone = memberMapper.selectList(new EntityWrapper<Member>().eq("phone", member.getPhone()).ne("examineState", 2));
		if (phone.size() > 0)
		{
			resultUtils.setMessage(Constant.MOBILE_PHONE_NUMBER_HAS_BEEN_REGISTERED);
			resultUtils.setStatus(Constant.STATUS_FAILED);
			return resultUtils;
		}
		//判斷兩次密碼是否一致
		if (!member.getPassword().equals(member.getOldPassword()))
		{
			resultUtils.setMessage(Constant.PASSWORD_INCONSISTENCY);
			resultUtils.setStatus(Constant.STATUS_FAILED);
			return resultUtils;
		}
		//证明 通过人脸验证
		//昵称、姓名、头像
		String code = (((Math.random() * 9 + 1) * 100000) + "").substring(0, 6);
		member.setNickName(Constant.USER_ + code);
		member.setName(Constant.USER_ + code);
		//从默认头像更改为靓照第一张
		if (member.getSex() == 1)
		{
			member.setBackgroundImages(Constant.NAN_PNG);
		}
		else
		{
			member.setBackgroundImages(Constant.NV_PNG);
		}
		member.setHead("/heika_icon_3x.png");
		//登录次数
		member.setLoginSize(0);
		//注册方式
		member.setRegistrationChannel(3);
		//审核状态
		member.setExamineState(1);
		//等级
		member.setLevel(0);
		//启用状态启用状态 1：启用2：禁用
		member.setEnableSate(1);
		//是否是vip
		member.setIsvip(1);
		//删除状态时未删除删除状态 0:未删除，1：删除
		member.setDelUserStatus("0");
		//会员到期时间
		member.setMemberExpirationDate(getThreeAfter());
		//注册时间
		member.setAddTime(new Timestamp(System.currentTimeMillis()));
		//密码格式化
		member.setPassword(MD5.GetMD5Code(member.getPassword()));
		//根据邀请码查询父级
		if (member.getInvitationCode() == null)
		{

		}
		else
		{
			List<Member> invitationCode = memberMapper.selectList(new EntityWrapper<Member>().eq("invitationCode", member.getInvitationCode()));
			if (invitationCode.size() > 0)
			{
				member.setPid(invitationCode.get(0).getId());
				Member member1 = invitationCode.get(0);
				member1.setRecommended(member1.getRecommended() + 1);
				memberMapper.updateById(member1);
			}
		}
		getCode(member);
		//插入
		Integer insert = memberMapper.insert(member);
		if (insert <= 0)
		{
			resultUtils.setMessage(Constant.INSERT_FAILED);
			resultUtils.setStatus(Constant.STATUS_FAILED);
			return resultUtils;
		}
		RechargeRecord rechargeRecord = new RechargeRecord();
		rechargeRecord.setMemberId(member.getId());
		rechargeRecord.setCurrency(2);
		rechargeRecord.setRunSize(28888L);
		rechargeRecord.setSurplus(28888L);
		rechargeRecord.setName(Constant.RECHARGE_BLACK_CARD);
		rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
		rechargeRecord.setType(1);
		rechargeRecord.setMode(3);
		rechargeRecordMapper.insert(rechargeRecord);
		if (member.getRegistrationChannel() != 1)
		{
			if (member.getPid() == null || member.getPid() == 0)
			{

			}
			else
			{
				Integer pid = member.getPid();
				Award award = new Award(platformParameterSettingMapper, memberService, memberAssetsService, rechargeRecordService);
				award.oneAward(pid, rechargeRecord.getRunSize(), 2);
				//获取接收礼物的上上级
				Integer pid1 = memberMapper.selectById(pid).getPid();
				if (pid1 == null || pid1 == 0)
				{

				}
				else
				{
					award.twoAward(pid1, rechargeRecord.getRunSize(), 2);
				}
			}
		}
		resultUtils.setMessage(Constant.LOGIN_WAS_SUCCESSFUL);
		resultUtils.setData(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Yesterday(new Date())));
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		return resultUtils;
	}


	@Override
	public ResultUtils validationToken(String token)
	{
		ResultUtils resultUtils = new ResultUtils();
		List<Member> token1 = memberMapper.selectList(new EntityWrapper<Member>().eq("token", token));
		if (token1.size() <= 0)
		{
			resultUtils.setStatus(300);
			resultUtils.setMessage(Constant.INVALID_ACCOUNT_INFORMATION);
			return resultUtils;
		}
		Member member = token1.get(0);
		if (member.getEnableSate() == 2)
		{
			resultUtils.setStatus(700);
			resultUtils.setMessage(Constant.ACCOUNT_HAS_BEEN_DISABLED);
			return resultUtils;
		}
		if (member.getExamineState() != 1)
		{
			resultUtils.setStatus(800);
			resultUtils.setMessage(Constant.AUDIT_FAILED);
			return resultUtils;
		}
		Integer registrationChannel = member.getRegistrationChannel();
		if (member.getIsvip() == null)
		{
			member.setIsvip(2);
		}
		if (registrationChannel == 3 && member.getIsvip() == 2)
		{
			resultUtils.setStatus(400);
			resultUtils.setMessage(Constant.BLACK_CARD_EXPIRED);
			return resultUtils;
		}
		if (registrationChannel == 2 && member.getIsvip() == 2 && member.getSex() == 1)
		{
			resultUtils.setStatus(600);
			resultUtils.setMessage(Constant.VIP_EXPIRED);
			return resultUtils;
		}
		if (registrationChannel == 1 && member.getIsvip() == 2 && member.getSex() == 1)
		{
			resultUtils.setStatus(600);
			resultUtils.setMessage(Constant.VIP_EXPIRED);
			return resultUtils;
		}
		//增加活跃度
		member.setActivitySize(member.getActivitySize() == null ? 1 : member.getActivitySize() + 1);
		memberMapper.updateById(member);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setMessage(Constant.VALIDATION_PASSED);
		return resultUtils;
	}

	@Override
	public ResultUtils uploadAddress(Member member)
	{
		ResultUtils resultUtils = new ResultUtils();
		Member member1 = memberMapper.selectById(member.getId());
		/*        if (member1.getCity() == null || "".equals(member1.getCity())){*/
		List<String> add = AddressUntils.getAdd(member.getLongitude(), member.getLatitude());
		member.setProvince(add.get(0));
		member.setCity(add.get(1));
		member.setArea(add.get(2));
		/*        }*/
		Integer integer = baseMapper.updateById(member);
		if (integer <= 0)
		{
			resultUtils.setStatus(Constant.STATUS_FAILED);
			resultUtils.setMessage(Constant.FAILED_TO_UPLOAD_LOCATION);
			return resultUtils;
		}
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setMessage(Constant.UPLOAD_LOCATION_SUCCEEDED);
		return resultUtils;
	}

	@Override
	public ResultUtils invitationThisMonth(Integer memberId)
	{
		ResultUtils resultUtils = new ResultUtils();
		Member member = memberMapper.selectById(memberId);
		Integer registrationChannel = member.getRegistrationChannel();
		List<RechargeRecord> rechargeRecords = rechargeRecordMapper.selectList(new EntityWrapper<RechargeRecord>().eq("memberId", memberId).eq("currency", 3).eq("type", 1).eq("name", "金钻返佣"));
		List<RechargeRecord> rechargeRecords1 = rechargeRecordMapper.selectList(new EntityWrapper<RechargeRecord>().eq("memberId", memberId).eq("currency", 4).eq("type", 1).eq("name", "银钻返佣"));
		int size = 0;
		int size1 = 0;
		for (RechargeRecord rechargeRecord : rechargeRecords)
		{
			size += rechargeRecord.getRunSize();
		}
		for (RechargeRecord rechargeRecord : rechargeRecords1)
		{
			size1 += rechargeRecord.getRunSize();
		}
		List<Member> pid = memberMapper.selectList(new EntityWrapper<Member>().eq("pid", memberId).eq("examineState", 1).last(" and DATE_FORMAT(addTime, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )"));
		resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setData(pid);
		resultUtils.setData1(size + "," + size1);
		return resultUtils;
	}

	//新增群成员
	@Override
	public ResultUtils addGroupMember(GroupJson groupJson)
	{
		ResultUtils resultUtils = new ResultUtils();
		String s = NewFriendList.add_group_member(groupJson);
		JSONObject jsonObject = JSONObject.parseObject(s);
		String actionStatus = jsonObject.getString("ActionStatus");

		if ("OK".equals(actionStatus))
		{
			resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
			resultUtils.setMessage(Constant.GROUP_MEMBER_ADDED_SUCCESSFULLY);
		}
		else
		{
			resultUtils.setStatus(Constant.STATUS_FAILED);
			resultUtils.setMessage(Constant.FAILED_TO_ADD_GROUP_MEMBER);
		}
		return resultUtils;
	}

	@Override
	public ResultUtils aopCalculateTheDistance(Integer memberId, Integer otherPartyId)
	{
		ResultUtils resultUtils = new ResultUtils();
		Member member = memberMapper.selectById(memberId);
		Member member1 = memberMapper.selectById(otherPartyId);
		if (member.getLongitude() == null || "".equals(member.getLongitude()) || member.getLatitude() == null || "".equals(member.getLatitude()) || member1.getLongitude() == null || "".equals(member1.getLongitude()) || member1.getLatitude() == null || "".equals(member1.getLatitude()))
		{
			resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
			resultUtils.setMessage(Constant.UNKNOWN);
		}
		else
		{
			double distance = LocationUtils.getDistance(member.getLongitude(), member.getLatitude(), member1.getLongitude(), member1.getLatitude());
			resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
			resultUtils.setMessage(new BigDecimal(distance).divide(new BigDecimal(1000), 1, BigDecimal.ROUND_HALF_DOWN) + "km");
		}
		return resultUtils;
	}

	@Override
	public ResultUtils isHavePhone(String phone)
	{
		ResultUtils resultUtils = new ResultUtils();
		List<Member> list = memberMapper.selectList(new EntityWrapper<Member>().eq("phone", phone));
		if (list.size() > 0)
		{
			resultUtils.setMessage("该手机号码已被注册");
			resultUtils.setStatus(500);
		}
		else
		{
            /*//在redis中查询有没有该手机号
            //如果有
            Boolean aBoolean = redisTemplate.hasKey("isHavePhone" + phone);
            if (aBoolean){
                resultUtils.setMessage("该手机号码正在注册中");
                resultUtils.setStatus(500);
            }else {
                //添加到redis
                redisTemplate.opsForValue().set("isHavePhone" + phone,"isHavePhone" + phone,86400L);
                resultUtils.setMessage("该手机号码未被注册");
                resultUtils.setStatus(200);
            }*/

			resultUtils.setMessage("该手机号码未被注册");
			resultUtils.setStatus(200);
		}
		return resultUtils;
	}

	@Override
	public ResultUtils delFriend(Integer memberId, Integer otherId)
	{
		ResultUtils resultUtils = new ResultUtils();
		Member member = memberMapper.selectById(memberId);
		member.setNumberOfRemainingFriendsToAdd(member.getNumberOfRemainingFriendsToAdd() + 1);
		Integer integer = memberMapper.updateById(member);
		Member member1 = memberMapper.selectById(memberId);
		member1.setNumberOfRemainingFriendsToAdd(member1.getNumberOfRemainingFriendsToAdd() + 1);
		Integer integer1 = memberMapper.updateById(member1);
		if (integer <= 0 || integer1 <= 0)
		{
			resultUtils.setStatus(500);
			resultUtils.setMessage("删除好友失败");
		}
		else
		{
			resultUtils.setStatus(200);
			resultUtils.setMessage("删除好友成功");
		}
		return resultUtils;
	}

	@Override
	public ResultUtils registrationComplete(Lable lable)
	{
		ResultUtils resultUtils = new ResultUtils();
		resultUtils.setStatus(200);
		resultUtils.setMessage("修改完成");

		//设置昵称的时候,需要判断,不能有相同的昵称,昵称想同的话,就提示昵称已存在
		List<Member> memberList = memberMapper.selectList(new EntityWrapper<Member>().eq("nickName", lable.getNickName()));
		if (memberList.size() > 0)
		{
			resultUtils.setStatus(500);
			resultUtils.setMessage("昵称已存在,请更换后重试!");
			return resultUtils;
		}

		Member member = memberMapper.selectById(lable.getMemberId());
		member.setNickName(lable.getNickName());
		if (updateById(member))
		{
			List<Lable> member_id = lableMapper.selectList(new EntityWrapper<Lable>().eq("member_id", lable.getMemberId()));
			if (member_id == null || member_id.size() == 0)
			{
				lableMapper.insert(lable);
				return resultUtils;
			}
			Lable lable1 = member_id.get(0);
			lable.setLabelId(lable1.getLabelId());
			Integer integer = lableMapper.updateById(lable);
			if (integer > 0)
				return resultUtils;
		}
		resultUtils.setStatus(500);
		resultUtils.setMessage("修改失败");
		return resultUtils;
	}

	@Override
	public ResultUtils create_group(GroupJson groupJson)
	{
		ResultUtils resultUtils = new ResultUtils();
		String s = NewFriendList.create_group(groupJson);
		System.out.println(s + "=============================");
		JSONObject jsonObject = JSONObject.parseObject(s);
		String actionStatus = jsonObject.getString("ActionStatus");

		if ("OK".equals(actionStatus))
		{

			String GroupId = jsonObject.getString("GroupId");
			resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
			resultUtils.setMessage("创建群成功");
			resultUtils.setData(GroupId);
		}
		else
		{
			resultUtils.setStatus(Constant.STATUS_FAILED);
			resultUtils.setMessage("创建群失败");
		}
		return resultUtils;
	}

	@Override
	public Member getInfoByToken(String Token)
	{
		List<Member> member = memberMapper.selectList(new EntityWrapper<Member>().eq("token", Token));
		if (member.size() > 0)
		{
			return member.get(0);
		}
		return null;
	}

	public boolean verification(Member member, ResultUtils resultUtils)
	{
		if (!redisTemplate.hasKey(member.getPhone()))
		{
			resultUtils.setMessage(Constant.VERIFICATION_CODE_NOT_SENT_OR_EXPIRED);
			resultUtils.setStatus(Constant.STATUS_FAILED);
			return true;
		}
		String code = (String) redisService.get(member.getPhone());
		if (!code.equals(member.getCode()))
		{
			resultUtils.setMessage(Constant.VERIFICATION_CODE_ERROR);
			resultUtils.setStatus(Constant.STATUS_FAILED);
			return true;
		}
		redisTemplate.delete(member.getPhone());
		return false;
	}

	public Member getCode(Member member)
	{
		String s = InvertCodeGenerator.genCodes(6);
		List<Member> invitationCode = baseMapper.selectList(new EntityWrapper<Member>().eq("invitationCode", s));
		if (invitationCode.size() > 0)
		{
			getCode(member);
		}
		else
		{
			member.setInvitationCode(s);
		}
		return member;
	}

	/**
	 * @Description: getLists 获取在线状态
	 * @param: memberList
	 * @return: java.util.List<com.qiqi.jiaoyou_app.pojo.Member>
	 * @Author: cfx
	 * @Date: 2020-12-22 11:45
	 */
	private List<Member> getLists(List<Member> memberList, String type)
	{
		if (memberList == null || memberList.size() == 0)
		{
			return memberList;
		}

		for (Member member1 : memberList)
		{
			if (member1.getTodayLoginTime() != null)
			{
				member1.setOnLineStatus(DateUtils.getShortTime(member1.getTodayLoginTime()));
			}
		/*if (member1.getTodayLoginTime() != null)
		{
		    if (member1.getTodayLoginTime().after(DateUtils.getAfterDate(1)))
		    {
			  System.out.println(member1.getTodayLoginTime() + "====" + DateUtils.getAfterDate(1));

			  member1.setOnLineStatus("在线");
		    }
		    else if (member1.getTodayLoginTime().after(DateUtils.getAfterDate(1)) && member1.getTodayLoginTime().before(DateUtils.getAfterDate(2)))
		    {
			  member1.setOnLineStatus("1小时前在线");
		    }
		    else if (member1.getTodayLoginTime().after(DateUtils.getAfterDate(2)) && member1.getTodayLoginTime().before(DateUtils.getAfterDate(3)))
		    {
			  member1.setOnLineStatus("2小时前在线");
		    }
		    else if (member1.getTodayLoginTime().after(DateUtils.getAfterDate(3)) && member1.getTodayLoginTime().before(DateUtils.getAfterDate(4)))
		    {
			  member1.setOnLineStatus("3小时前在线");
		    }
		    else if (member1.getTodayLoginTime().after(DateUtils.getAfterDate(4)) && member1.getTodayLoginTime().before(DateUtils.getAfterDate(5)))
		    {
			  member1.setOnLineStatus("4小时前在线");
		    }
		    else if (member1.getTodayLoginTime().after(DateUtils.getAfterDate(5)) && member1.getTodayLoginTime().before(DateUtils.getAfterDate(6)))
		    {
			  member1.setOnLineStatus("5小时前在线");
		    }
		    else if (member1.getTodayLoginTime().after(DateUtils.getAfterDate(6)) && member1.getTodayLoginTime().before(DateUtils.getAfterDate(7)))
		    {
			  member1.setOnLineStatus("6小时前在线");
		    }
		    else if (member1.getTodayLoginTime().after(DateUtils.getAfterDate(7)) && member1.getTodayLoginTime().before(DateUtils.getAfterDate(8)))
		    {
			  member1.setOnLineStatus("7小时前在线");
		    }
		    else if (member1.getTodayLoginTime().after(DateUtils.getAfterDate(8)) && member1.getTodayLoginTime().before(DateUtils.getAfterDate(9)))
		    {
			  member1.setOnLineStatus("8小时前在线");
		    }
		    else if (member1.getTodayLoginTime().after(DateUtils.getAfterDate(9)) && member1.getTodayLoginTime().before(DateUtils.getAfterDate(10)))
		    {
			  member1.setOnLineStatus("9小时前在线");
		    }
		    else if (member1.getTodayLoginTime().after(DateUtils.getAfterDate(10)) && member1.getTodayLoginTime().before(DateUtils.getAfterDate(11)))
		    {
			  member1.setOnLineStatus("10小时前在线");
		    }
		    else if (member1.getTodayLoginTime().after(DateUtils.getAfterDate(11)) && member1.getTodayLoginTime().before(DateUtils.getAfterDate(12)))
		    {
			  member1.setOnLineStatus("11小时前在线");
		    }
		    else if (member1.getTodayLoginTime().after(DateUtils.getAfterDate(12)))
		    {
			  member1.setOnLineStatus("12小时前在线");
		    }
		}*/
		}
		if (StringUtils.isNotBlank(type))
		{
			if ("1".equals(type))
			{
				return memberList.stream().sorted(Comparator.comparingDouble(Member::getDistances)).collect(Collectors.toList());
			}
			else if ("2".equals(type))
			{
				return memberList.stream().sorted(Comparator.comparingLong(Member::getActivitySize).reversed()).collect(Collectors.toList());
			}
			else if ("3".equals(type))
			{
				ArrayList<Member> members = new ArrayList<>();
				for (Member member : memberList)
				{
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(new Date());
					calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 7);
					Date time = calendar.getTime();
					if (member.getAddTime().before(time))
					{
						members.add(member);
					}
				}
				return members;
			}

		}
		return memberList;
	}
}
