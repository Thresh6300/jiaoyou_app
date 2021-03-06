package com.qiqi.jiaoyou_app.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.jPush.utils.JpushUtil;
import com.qiqi.jiaoyou_app.mapper.*;
import com.qiqi.jiaoyou_app.pojo.*;
import com.qiqi.jiaoyou_app.service.*;
import com.qiqi.jiaoyou_app.util.LocationUtils;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 线下活动列表 服务实现类
 * </p>
 *
 * @author GR123
 * @since 2020-06-16
 */
@SuppressWarnings("ALL")
@Service
public class SendOfflineActivitiesServiceImpl extends ServiceImpl<SendOfflineActivitiesMapper, SendOfflineActivities> implements ISendOfflineActivitiesService
{

    private static int corePoolSize = Runtime.getRuntime().availableProcessors();
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, corePoolSize + 1, 10l, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000));
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private SendOfflineActivitiesMapper sendOfflineActivitiesMapper;
    @Autowired
    private AcceptOfflineActivitiesMapper acceptOfflineActivitiesMapper;

    @Autowired
    private IAcceptOfflineActivitiesService iAcceptOfflineActivitiesService;
    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private MembershipSettingsMapper membershipSettingsMapper;
    @Autowired
    private MemberAssetsMapper memberAssetsMapper;
    @Autowired
    private IMemberAssetsService iMemberAssetsService;
    @Autowired
    private IRechargeRecordService iRechargeRecordService;
    @Autowired
    private IRechargeRecordService rechargeRecordService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private INoticeService iNoticeService;

    private final static Logger logger = LoggerFactory.getLogger(SendOfflineActivitiesServiceImpl.class);

    public static Date getDate(Date dates)
    {
	  long currentTime = dates.getTime();
	  currentTime += 720 * 60 * 1000;
	  Date date = new Date(currentTime);
	  return date;
    }

    @Override
    public ResultUtils startAParty(SendOfflineActivities sendOfflineActivities)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  Member member = memberMapper.selectById(sendOfflineActivities.getSendMemberId());
	  Integer level = member.getLevel();
	  if (level < 10)
	  {
		if (sendOfflineActivities.getPerSize() >= 3)
		{
		    resultUtils.setMessage(Constant.MUST_BE_LESS_THAN_3_PEOPLE);
		    resultUtils.setStatus(Constant.STATUS_FAILED);
		    return resultUtils;
		}
	  }
	  else if (level >= 10 && level < 20)
	  {
		if (sendOfflineActivities.getPerSize() > 5)
		{
		    resultUtils.setMessage(Constant.CONTROL_AT_3_5_PEOPLE);
		    resultUtils.setStatus(Constant.STATUS_FAILED);
		    return resultUtils;
		}
	  }
	  //判断开始时间与当前时间的时间差
	  SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constant.YYYY_MM_DD_HH_MM);
	  long from = 0;
	  try
	  {
		from = simpleDateFormat.parse(sendOfflineActivities.getStartTimeStr()).getTime();
	  }
	  catch (ParseException e)
	  {
		e.printStackTrace();
	  }
	  long to = System.currentTimeMillis();
	  int hours = (int) ((from - to) / (1000 * 60 * 60));
	  if (hours < 1)
	  {
		resultUtils.setMessage(Constant.MORE_THAN_AN_HOUR);
		resultUtils.setStatus(Constant.STATUS_FAILED);
		return resultUtils;
	  }
	  //先判断金钻数量是否足够
	  List<MemberAssets> memberId = memberAssetsMapper.selectList(new EntityWrapper<MemberAssets>().eq("memberId", member.getId()));
	  //获取金钻数量
	  Long memberDiamondsizeOfSilver = memberId.get(0).getMemberDiamondsizeOfGold();
	  //获取聚会所需银钻数量
	  Integer size = sendOfflineActivities.getAverageDiamondsSize() * sendOfflineActivities.getPerSize();


	  if (size > memberDiamondsizeOfSilver)
	  {
		resultUtils.setMessage(Constant.THERE_ARE_NOT_ENOUGH_DIAMONDS);
		resultUtils.setStatus(Constant.STATUS_FAILED);
		return resultUtils;
	  }
	  sendOfflineActivities.setSendMemberHead(member.getHead());
	  sendOfflineActivities.setSendMemberAge(member.getAge());
	  sendOfflineActivities.setSendMemberNickName(member.getNickName());
	  sendOfflineActivities.setSendMemberSex(member.getSex());
	  sendOfflineActivities.setBackgroundImages(member.getBackgroundImages());
	  try
	  {
		sendOfflineActivities.setStartTime(new SimpleDateFormat(Constant.YYYY_MM_DD_HH_MM).parse(sendOfflineActivities.getStartTimeStr()));
	  }
	  catch (ParseException e)
	  {
		e.printStackTrace();
	  }
	  sendOfflineActivities.setAddTime(new Timestamp(System.currentTimeMillis()));
	  sendOfflineActivities.setState(1);
	  Integer insert = sendOfflineActivitiesMapper.insert(sendOfflineActivities);
	  //累计报名人数
	  List<AcceptOfflineActivities> sendOfflineActivities1 = acceptOfflineActivitiesMapper.selectList(new EntityWrapper<AcceptOfflineActivities>().eq("sendOfflineActivities", sendOfflineActivities.getId()));
	  sendOfflineActivities.setCumulativeNumberOfPeople(sendOfflineActivities1.size());
	  //格式化日期
	  sendOfflineActivities.setStartTimeStr(new SimpleDateFormat(Constant.MM_月_DD_日_HH_MM).format(sendOfflineActivities.getStartTime()));
	  if (insert <= 0)
	  {
		resultUtils.setMessage(Constant.FAILED_TO_LAUNCH_THE_PARTY);
		resultUtils.setStatus(Constant.STATUS_FAILED);
	  }
	  else
	  {
		resultUtils.setMessage(Constant.SUCCESSFUL_PARTY_LAUNCH);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setData(sendOfflineActivities);
	  }
	  MemberAssets memberAssets = iMemberAssetsService.selectOne(new EntityWrapper<MemberAssets>().eq("memberId", sendOfflineActivities.getSendMemberId()));

	  RechargeRecord rechargeRecord = new RechargeRecord();
	  rechargeRecord.setMemberId(sendOfflineActivities.getSendMemberId());
	  rechargeRecord.setName("聚会发的钻石");
	  rechargeRecord.setType(2);
	  rechargeRecord.setRunSize(Long.valueOf(size));
	  rechargeRecord.setSurplus(memberAssets.getMemberDiamondsizeOfGold());
	  rechargeRecord.setAddTime(new Date());
	  rechargeRecord.setCurrency(3);
	  rechargeRecord.setMode(4);

	  iRechargeRecordService.insert(rechargeRecord);

	  return resultUtils;
    }

    @Override
    public ResultUtils Initiated(Integer pageNum, Integer pageSize, Integer id, Integer type)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  if (type == 1)
	  {
		//我发起的
		Page<SendOfflineActivities> page = new Page<>(pageNum, pageSize);
		List<SendOfflineActivities> sendMemberId = sendOfflineActivitiesMapper.selectPage(page, new EntityWrapper<SendOfflineActivities>().eq("sendMemberId", id).orderBy("addTime", false));
		for (SendOfflineActivities sendOfflineActivities : sendMemberId)
		{
		    sendOfflineActivities.setStartTimeStr(new SimpleDateFormat(Constant.YYYY_年_MM_月_DD_日_HH_MM).format(sendOfflineActivities.getStartTime()));
		}
		resultUtils.setData(sendMemberId);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
		resultUtils.setData1(page);
	  }
	  else
	  {
		//我参考的
		List<AcceptOfflineActivities> acceptMemberId = acceptOfflineActivitiesMapper.selectList(new EntityWrapper<AcceptOfflineActivities>().eq("acceptMemberId", id).ne("keepAnAppointmentState", 5));
		Integer[] integers = new Integer[acceptMemberId.size()];
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < acceptMemberId.size(); i++)
		{
		    integers[i] = acceptMemberId.get(i).getSendOfflineActivities();
		    map.put(acceptMemberId.get(i).getSendOfflineActivities(), acceptMemberId.get(i).getKeepAnAppointmentState());
		}
		//根据 聚会ID数组  查询聚会
		if (acceptMemberId.size() == 0)
		{
		    resultUtils.setData(new ArrayList<>());
		}
		else
		{
		    Page<SendOfflineActivities> page = new Page<>(pageNum, pageSize);
		    List<SendOfflineActivities> id1 = sendOfflineActivitiesMapper.selectPage(page, new EntityWrapper<SendOfflineActivities>().in("id", integers).orderBy("addTime", false));
		    for (SendOfflineActivities sendOfflineActivities : id1)
		    {
			  sendOfflineActivities.setStartTimeStr(new SimpleDateFormat(Constant.YYYY_年_MM_月_DD_日_HH_MM).format(sendOfflineActivities.getStartTime()));
			  sendOfflineActivities.setState(map.get(sendOfflineActivities.getId()));
		    }
		    resultUtils.setData(id1);
		    resultUtils.setData1(page);
		}
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
	  }
	  return resultUtils;
    }

    @Override
    public ResultUtils partyDetails(Integer id, Integer memberId)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  SendOfflineActivities sendOfflineActivities = sendOfflineActivitiesMapper.selectById(id);
	  if (sendOfflineActivities == null)
	  {
		resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setData(new SendOfflineActivities());
		return resultUtils;
	  }
	  //累计报名人数
	  List<AcceptOfflineActivities> sendOfflineActivities1 = acceptOfflineActivitiesMapper.selectList(new EntityWrapper<AcceptOfflineActivities>().eq("sendOfflineActivities", sendOfflineActivities.getId()));
	  Integer[] integers = new Integer[3];
	  integers[0] = 1;
	  integers[1] = 5;
	  integers[2] = 9;
	  List<AcceptOfflineActivities> sendOfflineActivities2 = acceptOfflineActivitiesMapper.selectList(new EntityWrapper<AcceptOfflineActivities>().eq("sendOfflineActivities", sendOfflineActivities.getId()).notIn("keepAnAppointmentState", integers));

	  sendOfflineActivities.setCumulativeNumberOfPeople(sendOfflineActivities1.size());
	  //报名列表
	  sendOfflineActivities.setList(sendOfflineActivities2);
	  sendOfflineActivities.setStartTimeStr(new SimpleDateFormat(Constant.YYYY_年_MM_月_DD_日_HH_MM).format(sendOfflineActivities.getStartTime()));

	  resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
	  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  resultUtils.setData(sendOfflineActivities);
	  return resultUtils;
    }

    @Override
    public ResultUtils enterFor(AcceptOfflineActivities acceptOfflineActivities)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  SendOfflineActivities sendOfflineActivities = sendOfflineActivitiesMapper.selectById(acceptOfflineActivities.getSendOfflineActivities());
	  List<AcceptOfflineActivities> acceptOfflineActivities1 = acceptOfflineActivities.selectList(new EntityWrapper<AcceptOfflineActivities>().eq("acceptMemberId", acceptOfflineActivities.getAcceptMemberId()).eq("sendOfflineActivities", acceptOfflineActivities.getSendOfflineActivities()));
	  Integer count = acceptOfflineActivitiesMapper.selectCount(new EntityWrapper<AcceptOfflineActivities>().eq("sendOfflineActivities", acceptOfflineActivities.getSendOfflineActivities()).notIn("keepAnAppointmentState", 1, 5));
	  if (count >= sendOfflineActivities.getPerSize())
	  {
		resultUtils.setMessage("当前参与人数已满");
		resultUtils.setStatus(Constant.STATUS_FAILED);
		return resultUtils;
	  }
	  if (acceptOfflineActivities1.size() > 0)
	  {
		resultUtils.setStatus(Constant.STATUS_FAILED);
		resultUtils.setMessage(Constant.YOU_CANT_SIGN_UP_FOR_THE_PARTY_AGAIN);
		return resultUtils;
	  }
	  if (sendOfflineActivities.getSendMemberId().equals(acceptOfflineActivities.getAcceptMemberId()))
	  {
		resultUtils.setStatus(Constant.STATUS_FAILED);
		resultUtils.setMessage(Constant.YOU_CANT_SIGN_UP_FOR_YOUR_OWN_PARTY);
		return resultUtils;
	  }
	  if (sendOfflineActivities.getState() == 7)
	  {
		resultUtils.setStatus(Constant.STATUS_FAILED);
		resultUtils.setMessage(Constant.THE_PARTY_HAS_BEEN_CANCELLED_BY_THE_SPONSOR);
		return resultUtils;
	  }
	  if (sendOfflineActivities.getState() == 5)
	  {
		resultUtils.setStatus(Constant.STATUS_FAILED);
		resultUtils.setMessage("该聚会已结束");
		return resultUtils;
	  }
	  if (sendOfflineActivities.getState() == 6)
	  {
		resultUtils.setStatus(Constant.STATUS_FAILED);
		resultUtils.setMessage("该聚会已结束");
		return resultUtils;
	  }
	  if (sendOfflineActivities.getState() == 4)
	  {
		resultUtils.setStatus(Constant.STATUS_FAILED);
		resultUtils.setMessage("发起方已确认该聚会已结束");
		return resultUtils;
	  }
	  //查询我报名的所有聚会
	  List<AcceptOfflineActivities> acceptMemberId = acceptOfflineActivitiesMapper.selectList(new EntityWrapper<AcceptOfflineActivities>().eq("acceptMemberId", acceptOfflineActivities.getAcceptMemberId()).andNew().eq("keepAnAppointmentState", 1).or().eq("keepAnAppointmentState", 2));
	  if (acceptMemberId.size() > 0)
	  {
		Integer[] integer = new Integer[acceptMemberId.size()];
		for (int i = 0; i < acceptMemberId.size(); i++)
		{
		    integer[i] = acceptMemberId.get(i).getSendOfflineActivities();
		}
		List<SendOfflineActivities> id = sendOfflineActivitiesMapper.selectList(new EntityWrapper<SendOfflineActivities>().in("id", integer));
		for (SendOfflineActivities sendOfflineActivities1 : id)
		{
		    /* 开始时间 */
		    Date leftStartDate = sendOfflineActivities.getStartTime();
		    /* 结束时间 */
		    Date leftEndDate = getDate(leftStartDate);

		    /*比较的时间段*/
		    Date rightStartDate = sendOfflineActivities1.getStartTime();
		    Date rightEndDate = getDate(rightStartDate);

		    /*判断*/
		    if (((leftStartDate.getTime() >= rightStartDate.getTime()) && leftStartDate.getTime() < rightEndDate.getTime()) || ((leftStartDate.getTime() > rightStartDate.getTime()) && leftStartDate.getTime() <= rightEndDate.getTime()) || ((rightStartDate.getTime() >= leftStartDate.getTime()) && rightStartDate.getTime() < leftEndDate.getTime()) || ((rightStartDate.getTime() > leftStartDate.getTime()) && rightStartDate.getTime() <= leftEndDate.getTime()))
		    {
			  resultUtils.setStatus(Constant.STATUS_FAILED);
			  resultUtils.setMessage(Constant.THERE_IS_A_CONFLICT_OF_TIME);
			  return resultUtils;
		    }
		}
	  }


	  Member member = memberMapper.selectById(acceptOfflineActivities.getAcceptMemberId());
	  Member member1 = memberMapper.selectById(sendOfflineActivitiesMapper.selectById(acceptOfflineActivities.getSendOfflineActivities()).getSendMemberId());
	  List<MembershipSettings> memberId = membershipSettingsMapper.selectList(new EntityWrapper<MembershipSettings>().eq("memberId", member1.getId()));
	  MembershipSettings membershipSettings = memberId.get(0);
	  acceptOfflineActivities.setAcceptMemberAge(member.getAge());
	  acceptOfflineActivities.setAcceptMemberHead(member.getHead());
	  acceptOfflineActivities.setAcceptMemberNickName(member.getNickName());
	  acceptOfflineActivities.setAcceptMemberSex(member.getSex());
	  acceptOfflineActivities.setKeepAnAppointmentState(1);//已申请
	  try
	  {
		acceptOfflineActivities.setKeepAnAppointmentTime(new SimpleDateFormat(Constant.YYYY_MM_DD_HH_MM).parse(acceptOfflineActivities.getKeepAnAppointmentTimeStr()));
	  }
	  catch (ParseException e)
	  {
		e.printStackTrace();
	  }
	  Integer insert = acceptOfflineActivitiesMapper.insert(acceptOfflineActivities);
	  if (insert <= 0)
	  {
		resultUtils.setStatus(Constant.STATUS_FAILED);
		resultUtils.setMessage(Constant.REGISTRATION_FAILED);
	  }
	  else
	  {
		Notice notice = new Notice();
		notice.setShenqingId(acceptOfflineActivities.getId());
		notice.setMemberId(acceptOfflineActivities.getAcceptMemberId());
		notice.setNickname(acceptOfflineActivities.getAcceptMemberNickName());
		notice.setHead(acceptOfflineActivities.getAcceptMemberHead());
		notice.setSex(acceptOfflineActivities.getAcceptMemberSex());
		notice.setShenqingTime(new Timestamp(System.currentTimeMillis()));
		notice.setDaochangTime(acceptOfflineActivities.getKeepAnAppointmentTime());


		//		您已报名参与用户+“昵称“+发布的+“聚会主题”+聚会
		// 	      notice.setTitle("您已报名参与用户\"" +member.getNickName()+ "\"发起的\"" + sendOfflineActivities1.getActivityTheme() + "\"聚会");
		notice.setTitle("\"" + acceptOfflineActivities.getAcceptMemberNickName() + "\"发起了\"" + sendOfflineActivities.getActivityTheme() + "\"报名申请");
		notice.setPerSize(sendOfflineActivities.getPerSize());
		notice.setStartTime(sendOfflineActivities.getStartTime());
		notice.setAddress(sendOfflineActivities.getAddress());
		notice.setOfMember(sendOfflineActivities.getSendMemberId());
		notice.setType(2);
		notice.setAddTime(new Timestamp(System.currentTimeMillis()));
		Integer insert1 = noticeMapper.insert(notice);

		Notice notice1 = new Notice();
		// notice1.setShenqingId(acceptOfflineActivities.getId());
		notice1.setMemberId(sendOfflineActivities.getSendMemberId());
		notice1.setNickname(sendOfflineActivities.getSendMemberNickName());
		notice1.setHead(sendOfflineActivities.getSendMemberHead());
		notice1.setSex(sendOfflineActivities.getSendMemberSex());
		notice1.setShenqingTime(new Timestamp(System.currentTimeMillis()));
		notice1.setDaochangTime(acceptOfflineActivities.getKeepAnAppointmentTime());

		//		您已报名参与用户+“昵称“+发布的+“聚会主题”+聚会
		notice1.setTitle("您已报名参与用户\"" + sendOfflineActivities.getSendMemberNickName() + "\"发起的\"" + sendOfflineActivities.getActivityTheme() + "\"聚会");
		//		notice.setTitle(acceptOfflineActivities.getAcceptMemberNickName() + "发起了\"" + sendOfflineActivities.getActivityTheme() + "\"报名申请");
		notice1.setPerSize(sendOfflineActivities.getPerSize());
		notice1.setStartTime(sendOfflineActivities.getStartTime());
		notice1.setAddress(sendOfflineActivities.getAddress());
		notice1.setOfMember(acceptOfflineActivities.getAcceptMemberId());
		notice1.setType(2);
		notice1.setAddTime(new Timestamp(System.currentTimeMillis()));
		Integer insert12 = noticeMapper.insert(notice1);

		if (insert1 > 0 && membershipSettings.getNoticeState() == 1)
		{
		    Map<String, String> xtrasparams = new HashMap<String, String>(); //扩展字段
		    xtrasparams.put("id", notice.getId() + "");
		    xtrasparams.put("type", 1 + "");
		    if (member1.getPushId() == null)
		    {

		    }
		    else
		    {
			  JpushUtil.sendToRegistrationId(member1.getPushId(), notice.getTitle(), notice.getTitle(), notice.getTitle(), xtrasparams);
		    }
		}
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setMessage(Constant.SUCCESSFUL_REGISTRATION);

	  }
	  return resultUtils;
    }

    @Override
    public ResultUtils beInFor(Integer id, Integer type)
    {
	  //		2:通过 9：未通过
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setMessage("操作失败");
	  resultUtils.setStatus(Constant.STATUS_FAILED);
	  AcceptOfflineActivities acceptOfflineActivities1 = acceptOfflineActivitiesMapper.selectById(id);
	  if (2 == type)
	  {
		Integer count = acceptOfflineActivitiesMapper.selectCount(new EntityWrapper<AcceptOfflineActivities>().eq("sendOfflineActivities", acceptOfflineActivities1.getSendOfflineActivities()).notIn("keepAnAppointmentState", Arrays.asList(1, 5, 9)));
		Integer perSize = sendOfflineActivitiesMapper.selectById(acceptOfflineActivities1.getSendOfflineActivities()).getPerSize();
		if (count >= perSize)
		{
		    resultUtils.setMessage(Constant.PERSONNEL_LIMIT_HAS_BEEN_REACHED);
		    resultUtils.setStatus(Constant.STATUS_FAILED);
		    return resultUtils;
		}
		acceptOfflineActivities1.setKeepAnAppointmentState(type);
		Integer integer = acceptOfflineActivitiesMapper.updateById(acceptOfflineActivities1);
		if (integer > 0)
		{

		    SendOfflineActivities sendOfflineActivities = sendOfflineActivitiesMapper.selectById(acceptOfflineActivities1.getSendOfflineActivities());
		    Notice notice = new Notice();
		    notice.setShenqingId(acceptOfflineActivities1.getId());
		    notice.setMemberId(sendOfflineActivities.getSendMemberId());
		    notice.setNickname(sendOfflineActivities.getSendMemberNickName());
		    notice.setHead(sendOfflineActivities.getSendMemberHead());
		    notice.setSex(sendOfflineActivities.getSendMemberSex());
		    notice.setShenqingTime(new Timestamp(System.currentTimeMillis()));
		    notice.setDaochangTime(acceptOfflineActivities1.getKeepAnAppointmentTime());

		    /*查询用户参与的聚会主题*/
		    SendOfflineActivities sendOfflineActivities1 = sendOfflineActivitiesMapper.selectById(acceptOfflineActivities1.getSendOfflineActivities());
		    //		  2:通过 9：未通过
		    notice.setTitle("您报名参与的\"" + sendOfflineActivities1.getActivityTheme() + "\"聚会已被通过审核，请准时参与聚会，若有任何疑问可拨打客服热线0731-85206798");
		    notice.setPerSize(sendOfflineActivities1.getPerSize());
		    notice.setStartTime(sendOfflineActivities1.getStartTime());
		    notice.setAddress(sendOfflineActivities1.getAddress());
		    notice.setOfMember(acceptOfflineActivities1.getAcceptMemberId());
		    notice.setType(2);
		    notice.setAddTime(new Timestamp(System.currentTimeMillis()));
		    Integer insert1 = noticeMapper.insert(notice);
		    resultUtils.setMessage(Constant.THE_OPERATION_WAS_SUCCESSFUL);
		    resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		    return resultUtils;
		}
	  }
	  //貌似只有这里才是成功之后的逻辑，在这里吧通知放到数据库
	  else if (9 == type)
	  {
		Integer integer = acceptOfflineActivitiesMapper.deleteById(acceptOfflineActivities1);
		SendOfflineActivities sendOfflineActivities = sendOfflineActivitiesMapper.selectById(acceptOfflineActivities1.getSendOfflineActivities());
		Notice notice = new Notice();
		notice.setShenqingId(acceptOfflineActivities1.getId());
		notice.setMemberId(sendOfflineActivities.getSendMemberId());
		notice.setNickname(sendOfflineActivities.getSendMemberNickName());
		notice.setHead(sendOfflineActivities.getSendMemberHead());
		notice.setSex(sendOfflineActivities.getSendMemberSex());
		notice.setShenqingTime(new Timestamp(System.currentTimeMillis()));
		notice.setDaochangTime(acceptOfflineActivities1.getKeepAnAppointmentTime());
		/*查询用户参与的聚会主题*/
		SendOfflineActivities sendOfflineActivities1 = sendOfflineActivitiesMapper.selectById(acceptOfflineActivities1.getSendOfflineActivities());
		notice.setTitle("您报名参与的\"" + sendOfflineActivities1.getActivityTheme() + "\"聚会已被拒绝");
		notice.setPerSize(sendOfflineActivities1.getPerSize());
		notice.setStartTime(sendOfflineActivities1.getStartTime());
		notice.setAddress(sendOfflineActivities1.getAddress());
		notice.setOfMember(acceptOfflineActivities1.getAcceptMemberId());
		notice.setType(2);
		notice.setAddTime(new Timestamp(System.currentTimeMillis()));
		Integer insert1 = noticeMapper.insert(notice);
		resultUtils.setMessage(Constant.THE_OPERATION_WAS_SUCCESSFUL);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		return resultUtils;
	  }
	  return resultUtils;
    }

    @Override
    public ResultUtils clickOK(AcceptOfflineActivities acceptOfflineActivities)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  //修改状态
	  List<AcceptOfflineActivities> acceptOfflineActivities1 = acceptOfflineActivitiesMapper.selectList(new EntityWrapper<AcceptOfflineActivities>().eq("sendOfflineActivities", acceptOfflineActivities.getSendOfflineActivities()).eq("acceptMemberId", acceptOfflineActivities.getAcceptMemberId()));
	  AcceptOfflineActivities acceptOfflineActivities2 = acceptOfflineActivities1.get(0);
	  //计算两点之间距离
	  double distance = LocationUtils.getDistance(acceptOfflineActivities.getLongitude(), acceptOfflineActivities.getLatitude(), acceptOfflineActivities2.getLongitude(), acceptOfflineActivities2.getLatitude());
	  if (distance > 150)
	  {
		resultUtils.setMessage(Constant.DISTANCE_EXCEEDS_MAXIMUM);
		resultUtils.setStatus(Constant.STATUS_FAILED);
		return resultUtils;
	  }
	  acceptOfflineActivities2.setKeepAnAppointmentState(3);
	  Integer integer = acceptOfflineActivitiesMapper.updateById(acceptOfflineActivities2);
	  if (integer <= 0)
	  {
		resultUtils.setMessage(Constant.OPERATION_FAILED);
		resultUtils.setStatus(Constant.STATUS_FAILED);
	  }
	  else
	  {
		/*查询用户参与的聚会主题*/
		SendOfflineActivities sendOfflineActivities1 = sendOfflineActivitiesMapper.selectById(acceptOfflineActivities1.get(0).getSendOfflineActivities());

		// 发起者
		Notice notice = new Notice();
		notice.setShenqingId(acceptOfflineActivities2.getId());
		notice.setMemberId(acceptOfflineActivities.getAcceptMemberId());
		notice.setNickname(acceptOfflineActivities2.getAcceptMemberNickName());
		notice.setHead(acceptOfflineActivities2.getAcceptMemberHead());
		notice.setSex(acceptOfflineActivities2.getAcceptMemberSex());
		notice.setShenqingTime(new Timestamp(System.currentTimeMillis()));
		notice.setDaochangTime(acceptOfflineActivities2.getKeepAnAppointmentTime());
		notice.setTitle("对方已确认到达聚会地点，请确认对方到达。");
		notice.setPerSize(sendOfflineActivities1.getPerSize());
		notice.setStartTime(sendOfflineActivities1.getStartTime());
		notice.setAddress(sendOfflineActivities1.getAddress());
		notice.setOfMember(sendOfflineActivities1.getSendMemberId());
		notice.setType(2);
		notice.setAddTime(new Timestamp(System.currentTimeMillis()));
		Integer insert1 = noticeMapper.insert(notice);


		// 参与者
		Notice notice1 = new Notice();
		// notice1.setShenqingId(acceptOfflineActivities.getId());
		notice1.setMemberId(sendOfflineActivities1.getSendMemberId());
		notice1.setNickname(sendOfflineActivities1.getSendMemberNickName());
		notice1.setHead(sendOfflineActivities1.getSendMemberHead());
		notice1.setSex(sendOfflineActivities1.getSendMemberSex());
		notice1.setShenqingTime(new Timestamp(System.currentTimeMillis()));
		notice1.setDaochangTime(acceptOfflineActivities2.getKeepAnAppointmentTime());
		notice1.setTitle("您已确认到达聚会地点，请等待对方确认。");
		notice1.setPerSize(sendOfflineActivities1.getPerSize());
		notice1.setStartTime(sendOfflineActivities1.getStartTime());
		notice1.setAddress(sendOfflineActivities1.getAddress());
		notice1.setOfMember(acceptOfflineActivities2.getAcceptMemberId());
		notice1.setType(2);
		notice1.setAddTime(new Timestamp(System.currentTimeMillis()));
		Integer insert12 = noticeMapper.insert(notice1);
		resultUtils.setMessage(Constant.THE_OPERATION_WAS_SUCCESSFUL);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  }
	  return resultUtils;
    }

    @Override
    public ResultUtils initiatorClickOK(SendOfflineActivities sendOfflineActivities, String token)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  Integer count = acceptOfflineActivitiesMapper.selectCount(new EntityWrapper<AcceptOfflineActivities>().eq("sendOfflineActivities", sendOfflineActivities.getId()).eq("keepAnAppointmentState", 1));
	  if (count > 0)
	  {
		resultUtils.setMessage("您还有待审核的报名者,不能确认");
		resultUtils.setStatus(Constant.STATUS_FAILED);
		return resultUtils;
	  }
	  //修改状态
	  sendOfflineActivities.setState(4);
	  Integer integer = sendOfflineActivitiesMapper.updateById(sendOfflineActivities);

	  if (integer <= 0)
	  {
		resultUtils.setMessage(Constant.OPERATION_FAILED);
		resultUtils.setStatus(Constant.STATUS_FAILED);
	  }
	  //update check state success continue next logic
	  else
	  { 
		SendOfflineActivities sendOfflineActivities2 = sendOfflineActivitiesMapper.selectById(sendOfflineActivities.getId());
		List<AcceptOfflineActivities> sendOfflineActivities1 = iAcceptOfflineActivitiesService.selectList(new EntityWrapper<AcceptOfflineActivities>().eq("sendOfflineActivities", sendOfflineActivities.getId()));
		AcceptOfflineActivities acceptOfflineActivities = sendOfflineActivities1.get(0);

		for (AcceptOfflineActivities offlineActivities : sendOfflineActivities1)
		{
		    if(offlineActivities.getKeepAnAppointmentState() != 5){
			  offlineActivities.setKeepAnAppointmentState(4);
		    }
		    
		}
		iAcceptOfflineActivitiesService.updateBatchById(sendOfflineActivities1);
		/*点击确认到达按钮*/
		Notice notice = new Notice();
		notice.setShenqingId(acceptOfflineActivities.getId());
		notice.setMemberId(sendOfflineActivities2.getSendMemberId());
		notice.setNickname(sendOfflineActivities2.getSendMemberNickName());
		notice.setHead(sendOfflineActivities2.getSendMemberHead());
		notice.setSex(sendOfflineActivities2.getSendMemberSex());
		notice.setShenqingTime(new Timestamp(System.currentTimeMillis()));
		notice.setDaochangTime(acceptOfflineActivities.getKeepAnAppointmentTime());

		/*查询用户参与的聚会主题*/
		notice.setTitle("对方已确认，祝您相处愉快！有任何疑问可拨打客服热线0731-85206798。");
		notice.setPerSize(sendOfflineActivities2.getPerSize());
		notice.setStartTime(sendOfflineActivities2.getStartTime());
		notice.setAddress(sendOfflineActivities2.getAddress());
		notice.setOfMember(acceptOfflineActivities.getAcceptMemberId());
		notice.setType(2);
		notice.setAddTime(new Timestamp(System.currentTimeMillis()));
		Integer insert1 = noticeMapper.insert(notice);

		resultUtils.setMessage(Constant.THE_OPERATION_WAS_SUCCESSFUL);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  }
	  return resultUtils;
    }

    //取消聚会
    @Override
    public ResultUtils closeParty(SendOfflineActivities sendOfflineActivities)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  try
	  {
		//该方法是发起方主动取消聚会
		//判断又没人报名这个聚会并且发起方通过
		SendOfflineActivities sendOfflineActivities1 = sendOfflineActivitiesMapper.selectById(sendOfflineActivities.getId());
		List<MemberAssets> memberId = memberAssetsMapper.selectList(new EntityWrapper<MemberAssets>().eq("memberId", sendOfflineActivities1.getSendMemberId()));
		//获取聚会所需银钻数量
		MemberAssets memberAssets = memberId.get(0);
		redisService.set(sendOfflineActivities.getId().toString(), memberAssets.getMemberDiamondsizeOfGold());


		if (!sendOfflineActivities1.getSendMemberId().equals(sendOfflineActivities.getSendMemberId()))
		{
		    resultUtils.setMessage(Constant.NOT_THE_SPONSOR_OF_THE_PARTY);
		    resultUtils.setStatus(Constant.STATUS_FAILED);
		    return resultUtils;
		}
		if (sendOfflineActivities1.getState() == 7)
		{
		    resultUtils.setMessage(Constant.CANNOT_BE_CANCELLED_REPEATEDLY);
		    resultUtils.setStatus(Constant.STATUS_FAILED);
		    return resultUtils;
		}

		final CountDownLatch countDownLatch = new CountDownLatch(2);
		executor.execute(() ->
		{
		    sendOfflineActivities1.setIsOverdueout("1");
		    sendOfflineActivities1.setState(7);
		    Integer integer = sendOfflineActivitiesMapper.updateById(sendOfflineActivities1);
		    if (integer > 0)
		    {

			  Notice notice1 = new Notice();
			  notice1.setTitle("取消聚会");
			  notice1.setPerSize(sendOfflineActivities.getPerSize());
			  notice1.setContext("\"您已取消了\"" + sendOfflineActivities1.getActivityTheme() + "\"聚会");
			  notice1.setType(2);
			  notice1.setAddTime(new Timestamp(System.currentTimeMillis()));
			  notice1.setOfMember(sendOfflineActivities1.getSendMemberId());
			  iNoticeService.insert(notice1);

			  List<AcceptOfflineActivities> acceptOfflineActivities = acceptOfflineActivitiesMapper.selectList(new EntityWrapper<AcceptOfflineActivities>().ne("keepAnAppointmentState", 9).eq("sendOfflineActivities", sendOfflineActivities1.getId()));
			  if (acceptOfflineActivities != null && acceptOfflineActivities.size() > 0)
			  {
				List<Notice> notices = new ArrayList<>();

				for (AcceptOfflineActivities acceptOfflineActivity : acceptOfflineActivities)
				{
				    Notice notice = new Notice();
				    notice.setShenqingId(acceptOfflineActivity.getId());
				    notice.setMemberId(sendOfflineActivities1.getSendMemberId());
				    notice.setNickname(sendOfflineActivities1.getSendMemberNickName());
				    notice.setHead(sendOfflineActivities1.getSendMemberHead());
				    notice.setSex(sendOfflineActivities1.getSendMemberSex());
				    notice.setTitle("取消聚会");
				    notice.setPerSize(sendOfflineActivities.getPerSize());
				    notice.setContext("\"" + sendOfflineActivities1.getSendMemberNickName() + "\"取消了\"" + sendOfflineActivities1.getActivityTheme() + "\"聚会");
				    notice.setType(2);
				    notice.setAddTime(new Timestamp(System.currentTimeMillis()));
				    notice.setOfMember(acceptOfflineActivity.getAcceptMemberId());
				    notices.add(notice);
				}
				iNoticeService.insertBatch(notices);
			  }
			  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
			  resultUtils.setMessage(Constant.PARTY_CANCELLED_SUCCESSFULLY);
		    }
		    else
		    {
			  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
			  resultUtils.setMessage(Constant.PARTY_CANCELLATION_FAILED);
		    }

		    countDownLatch.countDown();
		});
		executor.execute(() ->
		{

		    int sleppTime = (int) (1 + Math.random() * (10 - 1));
		    try
		    {
			  System.out.println(sleppTime);
			  TimeUnit.SECONDS.sleep(sleppTime);
			  RechargeRecord rechargeRecord = new RechargeRecord();
			  Integer o = (Integer) redisService.get(sendOfflineActivities.getId().toString());
			  System.out.println(o);
			  List<MemberAssets> memberIds = memberAssetsMapper.selectList(new EntityWrapper<MemberAssets>().eq("memberId", sendOfflineActivities1.getSendMemberId()));
			  rechargeRecord.setMemberId(sendOfflineActivities1.getSendMemberId());
			  rechargeRecord.setName("取消聚会退回");
			  rechargeRecord.setCurrency(3);
			  rechargeRecord.setMode(4);
			  rechargeRecord.setType(1);
			  rechargeRecord.setRunSize(memberIds.get(0).getMemberDiamondsizeOfGold() - o);
			  rechargeRecord.setSurplus(memberIds.get(0).getMemberDiamondsizeOfGold());
			  rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
			  rechargeRecordService.insert(rechargeRecord);
			  countDownLatch.countDown();
		    }
		    catch (InterruptedException e)
		    {
			  e.printStackTrace();
		    }

		});
		countDownLatch.await();
	  }

	  catch (Exception e)
	  {
		e.printStackTrace();
	  }
	  return resultUtils;
    }

    @Override
    public ResultUtils submitCancellationApplication(AcceptOfflineActivities acceptOfflineActivities)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  List<AcceptOfflineActivities> acceptOfflineActivities1 = acceptOfflineActivitiesMapper.selectList(new EntityWrapper<AcceptOfflineActivities>().eq("sendOfflineActivities", acceptOfflineActivities.getSendOfflineActivities()).eq("acceptMemberId", acceptOfflineActivities.getAcceptMemberId()));
	  AcceptOfflineActivities acceptOfflineActivities2 = acceptOfflineActivities1.get(0);
	  if (acceptOfflineActivities2.getKeepAnAppointmentState() == 3)
	  {
		resultUtils.setMessage(Constant.IT_CANNOT_BE_CANCELLED);
		resultUtils.setStatus(Constant.STATUS_FAILED);
		return resultUtils;
	  }
	  else if (acceptOfflineActivities2.getKeepAnAppointmentState() == 4)
	  {
		resultUtils.setMessage(Constant.PLEASE_DO_NOT_SUBMIT_AGAIN);
		resultUtils.setStatus(Constant.STATUS_FAILED);
		return resultUtils;
	  }
	  else if (acceptOfflineActivities2.getKeepAnAppointmentState() == 5)
	  {
		resultUtils.setMessage(Constant.NOT_SUBMIT_AGAIN);
		resultUtils.setStatus(Constant.STATUS_FAILED);
		return resultUtils;
	  }
	  //author nan
	  //update set state for 5
	  acceptOfflineActivities2.setOldState(acceptOfflineActivities2.getKeepAnAppointmentState());
	  // acceptOfflineActivities2.setKeepAnAppointmentState(4);
	  acceptOfflineActivities2.setKeepAnAppointmentState(5);
	  acceptOfflineActivities2.setCloseType(1);
	  Integer integer = acceptOfflineActivitiesMapper.updateById(acceptOfflineActivities2);
	  if (integer <= 0)
	  {
		resultUtils.setStatus(Constant.STATUS_FAILED);
		resultUtils.setMessage(Constant.FAILED_TO_SUBMIT_APPLICATION);
		return resultUtils;
	  }
	  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  resultUtils.setMessage(Constant.APPLICATION_SUBMITTED_SUCCESSFULLY);
	  return resultUtils;
    }

    @Override
    public ResultUtils compulsoryCancellation(AcceptOfflineActivities acceptOfflineActivities)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  List<AcceptOfflineActivities> acceptOfflineActivities1 = acceptOfflineActivitiesMapper.selectList(new EntityWrapper<AcceptOfflineActivities>().eq("sendOfflineActivities", acceptOfflineActivities.getSendOfflineActivities()).eq("acceptMemberId", acceptOfflineActivities.getAcceptMemberId()));
	  AcceptOfflineActivities acceptOfflineActivities2 = acceptOfflineActivities1.get(0);
	  SendOfflineActivities sendOfflineActivities = sendOfflineActivitiesMapper.selectById(acceptOfflineActivities2.getSendOfflineActivities());
	  if (sendOfflineActivities.getState() == 4 || sendOfflineActivities.getState() == 5 || sendOfflineActivities.getState() == 6)
	  {
		resultUtils.setMessage(Constant.THE_PARTY_IS_OVER);
		resultUtils.setStatus(Constant.STATUS_FAILED);
		return resultUtils;
	  }
	  if (sendOfflineActivities.getState() == 7)
	  {
		resultUtils.setMessage(Constant.THE_PARTY_HAS_BEEN_CANCELLED);
		resultUtils.setStatus(Constant.STATUS_FAILED);
		return resultUtils;
	  }
	  if (acceptOfflineActivities2.getKeepAnAppointmentState() == 3)
	  {
		resultUtils.setMessage(Constant.IT_CANNOT_BE_CANCELLED);
		resultUtils.setStatus(Constant.STATUS_FAILED);
		return resultUtils;
	  }
	  else if (acceptOfflineActivities2.getKeepAnAppointmentState() == 5)
	  {
		resultUtils.setMessage(Constant.NOT_SUBMIT_AGAIN);
		resultUtils.setStatus(Constant.STATUS_FAILED);
		return resultUtils;
	  }
	  acceptOfflineActivities2.setCloseType(2);
	  acceptOfflineActivities2.setKeepAnAppointmentState(5);
	  Integer integer = acceptOfflineActivitiesMapper.updateById(acceptOfflineActivities2);
	  if (integer <= 0)
	  {
		resultUtils.setStatus(Constant.STATUS_FAILED);
		resultUtils.setMessage(Constant.FORCE_CANCEL_FAILED);
		return resultUtils;
	  }
	  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  resultUtils.setMessage(Constant.FORCED_CANCELLATION_SUCCEEDED);
	  return resultUtils;
    }

    //我参与的和我发布的聚会列表
    @Override
    public ResultUtils participate(SendOfflineActivities sendOfflineActivities)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  resultUtils.setData(new ArrayList<>());
	  EntityWrapper<SendOfflineActivities> wrapper = new EntityWrapper<>();

	  // 查询参与报名的 用户信息
	  List<AcceptOfflineActivities> acceptMemberId = acceptOfflineActivitiesMapper.selectList(new EntityWrapper<AcceptOfflineActivities>().eq("acceptMemberId", sendOfflineActivities.getSendMemberId()));
	  if (acceptMemberId != null && acceptMemberId.size() > 0)
	  {
		List<Integer> collect = acceptMemberId.stream().map(AcceptOfflineActivities::getSendOfflineActivities).collect(Collectors.toList());
		wrapper.in(collect != null && collect.size() > 0, "id", collect);

	  }

	  // sendMemberNickName  activityTheme address lable startTime 但是用户id一定是用户的id
	  if (StringUtils.isNotBlank(sendOfflineActivities.getSeacrh()))
	  {
		wrapper.like("sendMemberNickName", sendOfflineActivities.getSeacrh())
			    .or().like("activityTheme", sendOfflineActivities.getSeacrh())
			    .or().like("address", sendOfflineActivities.getSeacrh())
			    .or().like("lable", sendOfflineActivities.getSeacrh() );
	  }
	  //查询自己发起的
	  //修改查询的逻辑 初次进来的时候查询自己的  搜索的时候 隐藏自己的信息
	  List<SendOfflineActivities> sendOfflineActivities1 = new ArrayList<>();
	  Page page = new Page(sendOfflineActivities.getPageNum(), sendOfflineActivities.getPageSize());
	  page.setOptimizeCountSql(true);
	  page.setSearchCount(true);
	  if (sendOfflineActivities.getSeacrh() == "" || sendOfflineActivities.getSeacrh() == null)
	  {
		wrapper.orderBy("state").orderBy("id", false).or().eq("sendMemberId", sendOfflineActivities.getSendMemberId());

		sendOfflineActivities1 = sendOfflineActivitiesMapper.selectPage(page, wrapper);
		if (sendOfflineActivities1 == null || sendOfflineActivities1.size() == 0)
		{
		    return resultUtils;
		}
	  }
	  //如果搜索到了数据,在这里给写道输出的数据里
	  if (sendOfflineActivities.getSeacrh() != "" || sendOfflineActivities.getSeacrh() != null)
	  {
		wrapper.orderBy("state").orderBy("id", false);
		sendOfflineActivities1 = sendOfflineActivitiesMapper.selectPage(page, wrapper);
		if (sendOfflineActivities1 == null || sendOfflineActivities1.size() == 0)
		{
		    return resultUtils;
		}
	  }
	  Integer[] integers = new Integer[acceptMemberId.size()];
	  Map<Integer, Integer> map = new HashMap<>();
	  for (int i = 0; i < acceptMemberId.size(); i++)
	  {
		integers[i] = acceptMemberId.get(i).getSendOfflineActivities();
		map.put(acceptMemberId.get(i).getSendOfflineActivities(), acceptMemberId.get(i).getKeepAnAppointmentState());
	  }
	  //这里不能过滤自己参与的聚会
	  List<SendOfflineActivities> list = new ArrayList<>();
	  for (SendOfflineActivities offlineActivities : sendOfflineActivities1)
	  {
		offlineActivities.setStartTimeStr(new SimpleDateFormat(Constant.YYYY_年_MM_月_DD_日_HH_MM).format(offlineActivities.getStartTime()));
		if (offlineActivities.getSendMemberId().equals(sendOfflineActivities.getSendMemberId()))
		{
		    //是否是发起人 0是 ;1不是
		    offlineActivities.setType("0");
		}
		//状态 1  待报名 2 待对方确认3待自己确认4审核中5审核通过6审核未通过7已取消
		if ("1".equals(offlineActivities.getType()))
		{
		    offlineActivities.setState(map.get(offlineActivities.getId()));
		    //不是发起人也要加到返回列表里
		    list.add(offlineActivities);
		}
		if(offlineActivities.getSendMemberId().toString().equals(sendOfflineActivities.getSendMemberId().toString())){
		    list.add(offlineActivities);
		}
		if(list.size() > 0){
		    sendOfflineActivities1 = list;
		}
		logger.info(offlineActivities.toString());
	  }

	  //如果搜索有内容的话 这里就只放行包含这个用户数据的数据 这里需要吧自己参加的页加到这里
	  List<SendOfflineActivities> sendOfflineActivitiesReturn = sendOfflineActivities1;
	  List<SendOfflineActivities> sendOfflineActivitiesReturnBack = new ArrayList<>();
	  if (sendOfflineActivities.getSeacrh() != "" && sendOfflineActivities.getSeacrh() != null)
	  {
	      //获取当前用户的id  查询自己发起的聚会信息
		for(SendOfflineActivities sendOfflineActivities2 : sendOfflineActivitiesReturn){
		    //查询自己参与的聚会咧列表 别怪我心狠手辣....
		    if(sendOfflineActivities2.getSendMemberId().toString().equals(sendOfflineActivities.getSendMemberId().toString())){
			  sendOfflineActivitiesReturnBack.add(sendOfflineActivities2);
		    }
		}
	  }
	  if(sendOfflineActivitiesReturnBack.size() <= 0){
		resultUtils.setData(sendOfflineActivitiesReturn);
	  }else{
		resultUtils.setData(sendOfflineActivitiesReturnBack);
	  }

	  resultUtils.setPages((int) page.getPages());
	  return resultUtils;
    }

    //我要参加聚会列表
    @Override
    public ResultUtils apply(SendOfflineActivities sendOfflineActivities)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  resultUtils.setData(new ArrayList<>());
	  EntityWrapper<SendOfflineActivities> wrapper = new EntityWrapper<>();
	  // 查询已经参与的和报名的列表
	  List<AcceptOfflineActivities> acceptMemberId = acceptOfflineActivitiesMapper.selectList(new EntityWrapper<AcceptOfflineActivities>().in("keepAnAppointmentState", Arrays.asList(5, 6, 7, 8)).eq("acceptMemberId", sendOfflineActivities.getSendMemberId()));

	  if (acceptMemberId != null && acceptMemberId.size() > 0)
	  {
		List<Integer> collect = acceptMemberId.stream().map(AcceptOfflineActivities::getSendOfflineActivities).collect(Collectors.toList());
		wrapper.notIn(collect != null && collect.size() > 0, "id", collect);
	  }

	  // sendMemberNickName  activityTheme address lable startTime
	  if (StringUtils.isNotBlank(sendOfflineActivities.getSeacrh()))
	  {
		wrapper.like("sendMemberNickName", sendOfflineActivities.getSeacrh()).or().like("activityTheme", sendOfflineActivities.getSeacrh()).or().like("address", sendOfflineActivities.getSeacrh()).or().like("lable", sendOfflineActivities.getSeacrh());
	  }
	  // 排除自己发起的和已经取消的
	  wrapper.orderBy("id", false).notIn("state", 5, 6, 7, 9).ne("sendMemberId", sendOfflineActivities.getSendMemberId());

	  Page page = new Page(sendOfflineActivities.getPageNum(), sendOfflineActivities.getPageSize());
	  page.setOptimizeCountSql(true);
	  page.setSearchCount(true);

	  List<SendOfflineActivities> sendOfflineActivities1 = sendOfflineActivitiesMapper.selectPage(page, wrapper);
	  if (sendOfflineActivities1 == null || sendOfflineActivities1.size() == 0)
		return resultUtils;


	  for (SendOfflineActivities offlineActivities : sendOfflineActivities1)
	  {
		if (StringUtils.isEmpty(offlineActivities.getReadMembers()))
		{
		    offlineActivities.setReadMembers(sendOfflineActivities.getSendMemberId().toString());
		}
		else if (!offlineActivities.getReadMembers().contains(sendOfflineActivities.getSendMemberId().toString()))
		{
		    offlineActivities.setReadMembers(offlineActivities.getReadMembers() + "," + sendOfflineActivities.getSendMemberId());
		}
	  }
	  updateBatchById(sendOfflineActivities1);

	  resultUtils.setData(sendOfflineActivities1);
	  resultUtils.setCount((int) page.getTotal());
	  resultUtils.setPages((int) page.getPages());
	  return resultUtils;
    }

    @Override
    public ResultUtils isApply(Integer memberId)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  resultUtils.setData(0);
	  EntityWrapper<SendOfflineActivities> wrapper = new EntityWrapper<>();
	  // 查询已经参与的和报名的列表
	  List<AcceptOfflineActivities> acceptMemberId = acceptOfflineActivitiesMapper.selectList(new EntityWrapper<AcceptOfflineActivities>().in("keepAnAppointmentState", Arrays.asList(5, 6, 7, 8)).eq("acceptMemberId", memberId));

	  if (acceptMemberId != null && acceptMemberId.size() > 0)
	  {
		List<Integer> collect = acceptMemberId.stream().map(AcceptOfflineActivities::getSendOfflineActivities).collect(Collectors.toList());
		wrapper.notIn(collect != null && collect.size() > 0, "id", collect);
	  }
	  // 排除自己发起的和已经取消的
	  wrapper.orderBy("id", false).notIn("state", 5, 6, 7, 9).ne("sendMemberId", memberId);

	  Page page = new Page(1, 10);
	  List<SendOfflineActivities> sendOfflineActivities = sendOfflineActivitiesMapper.selectPage(page, wrapper);
	  if (sendOfflineActivities == null || sendOfflineActivities.size() == 0)
		return resultUtils;
	  String readMembers = sendOfflineActivities.get(0).getReadMembers();
	  if (StringUtils.isNotBlank(readMembers) && !readMembers.contains(memberId.toString()))
	  {
		resultUtils.setData(1);
	  }
	  if (StringUtils.isEmpty(readMembers))
	  {
		resultUtils.setData(1);
	  }
	  return resultUtils;
    }

}
