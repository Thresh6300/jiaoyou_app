package com.qiqi.jiaoyou_app.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.Instant.NewFriendList;
import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.controller.award.Award;
import com.qiqi.jiaoyou_app.jPush.utils.JpushUtil;
import com.qiqi.jiaoyou_app.mapper.*;
import com.qiqi.jiaoyou_app.pojo.*;
import com.qiqi.jiaoyou_app.service.IGiftconsumptionService;
import com.qiqi.jiaoyou_app.service.INewFriendService;
import com.qiqi.jiaoyou_app.service.IRechargeRecordService;
import com.qiqi.jiaoyou_app.util.DateUtils;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import com.qiqi.jiaoyou_app.util.UserDataItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.*;

/**
 * <p>
 * .新朋友列表 服务实现类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@SuppressWarnings("ALL")
@Service
public class NewFriendServiceImpl extends ServiceImpl<NewFriendMapper, NewFriend> implements INewFriendService
{

    @Autowired
    private NewFriendMapper newFriendMapper;
    @Autowired
    private GiftMapper giftMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private GiftconsumptionMapper giftconsumptionMapper;
    @Autowired
    private MemberAssetsMapper memberAssetsMapper;
    @Autowired
    private RechargeRecordServiceImpl rechargeRecordService;
    @Autowired
    private MemberAssetsServiceImpl memberAssetsService;
    @Autowired
    private MemberServiceImpl memberService;
    @Autowired
    private PlatformParameterSettingMapper platformParameterSettingMapper;

    @Autowired
    private MemberMatchingLogMapper memberMatchingLogMapper;
    @Autowired
    private IRechargeRecordService iRechargeRecordService;
    @Autowired
    private IGiftconsumptionService iGiftconsumptionService;
    @Autowired
    private INewFriendService iNewFriendService;

    public static Date Yesterday(Date date)
    {
	  //获取当前时间24小时前的时间
	  Calendar c = Calendar.getInstance();
	  c.setTime(date);
	  c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) + 24);
	  Date time = c.getTime();
	  return time;
    }

    @Override
    public ResultUtils friendList(Integer newFriendOneselfId, Integer pageSize, Integer pageNum, String keyWord)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  Page<NewFriend> page = new Page<>();
	  List<NewFriend> newFriends = newFriendMapper.selectPage(page, new EntityWrapper<NewFriend>().eq("is_retreat", 2).eq("new_friend_other_party_id", newFriendOneselfId).like(!StringUtils.isEmpty(keyWord), "new_friend_other_party_nick_name", keyWord));
	  for (NewFriend newFriend : newFriends)
	  {
		newFriend.setTimeOld(DateUtils.getShortTime(newFriend.getNewFriendOtherPartyAddTime()));
	  }
	  resultUtils.setData1(page);
	  resultUtils.setData(newFriends);
	  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
	  return resultUtils;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtils sayHello(NewFriend newFriend)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  if (newFriend.getNewFriendOneselfId().equals(newFriend.getNewFriendOtherPartyId()))
	  {
		resultUtils.setMessage(Constant.DONT_SAY_HELLO_TO_YOURSELF);
		resultUtils.setStatus(Constant.STATUS_FAILED);
		return resultUtils;
	  }
	  Member member = memberMapper.selectById(newFriend.getNewFriendOneselfId());
	  Member member1 = memberMapper.selectById(newFriend.getNewFriendOtherPartyId());
	  //获取会员等级
	  if (member.getLevel() < 50)
	  {
		Integer numberOfRemainingFriendsToAdd = member.getNumberOfRemainingFriendsToAdd();
		if (numberOfRemainingFriendsToAdd == 0)
		{
		    resultUtils.setStatus(Constant.STATUS_FAILED);
		    resultUtils.setMessage(Constant.FRIENDS_HAVE_REACHED_THE_LIMIT);
		    return resultUtils;
		}
	  }
	  // 打招呼人的id
	  List<MemberAssets> memberId = memberAssetsMapper.selectList(new EntityWrapper<MemberAssets>().eq("memberId", newFriend.getNewFriendOneselfId()));
	  MemberAssets memberAssets = memberId.get(0);
	  Gift gift1 = giftMapper.selectById(newFriend.getNewFriendOtherPartyGiftId());
	  if (newFriend.getNewFriendOtherPartyGiftSize() * gift1.getPrice() > memberAssets.getMemberDiamondsizeOfGold())
	  {
		resultUtils.setMessage(Constant.THERE_ARE_NOT_ENOUGH_DIAMONDS);
		resultUtils.setStatus(Constant.STATUS_FAILED);
		return resultUtils;
	  }
	  newFriend.setRunSize(Long.valueOf(newFriend.getNewFriendOtherPartyGiftSize() * gift1.getPrice()));
	  newFriend.setSurplus(memberAssets.getMemberDiamondsizeOfGold() - newFriend.getRunSize());
	  newFriend.setNewFriendOtherPartyHead(member.getHead());
	  newFriend.setNewFriendOtherPartyAutoLogos(member.getCarLable());
	  newFriend.setNewFriendOtherPartyNickName(member.getNickName());
	  newFriend.setNewFriendOtherPartySex(member.getSex());
	  newFriend.setNewFriendOtherPartyAge(member.getAge());
	  newFriend.setNewFriendOtherPartyLevel(member.getLevel());
	  newFriend.setNewFriendOtherPartyCity(member.getCity());

	  Gift gift = giftMapper.selectById(newFriend.getNewFriendOtherPartyGiftId());
	  newFriend.setNewFriendOtherPartyAddTime(new Timestamp(System.currentTimeMillis()));
	  newFriend.setNewFriendOtherPartyEndTime(Yesterday(new Date()));
	  newFriend.setNewFriendOtherPartyGiftImage(gift.getImages());
	  newFriend.setNewFriendOtherPartyGiftName(gift.getName());

	  Giftconsumption giftconsumption = new Giftconsumption();
	  giftconsumption.setSendMemberId(newFriend.getNewFriendOneselfId());
	  giftconsumption.setMemberId(newFriend.getNewFriendOtherPartyId());
	  giftconsumption.setSendMemberHead(member.getHead());
	  giftconsumption.setSendMemberNickName(member.getNickName());
	  giftconsumption.setGiftId(newFriend.getNewFriendOtherPartyGiftId());
	  giftconsumption.setGiftImages(newFriend.getNewFriendOtherPartyGiftImage());
	  giftconsumption.setGiftName(newFriend.getNewFriendOtherPartyGiftName());
	  giftconsumption.setGiftSize(newFriend.getNewFriendOtherPartyGiftSize());
	  giftconsumption.setAddTime(new Timestamp(System.currentTimeMillis()));
	  giftconsumption.setEndTime(Yesterday(new Date()));
	  giftconsumption.setDrawInProportion(2);

	  Integer insert = giftconsumptionMapper.insert(giftconsumption);
	  newFriend.setRemarks(giftconsumption.getRevision());
	  newFriend.setIsRetreat(2);
	  Integer insert1 = newFriendMapper.insert(newFriend);
	  if (insert > 0 && insert1 > 0)
	  {

		RechargeRecord rechargeRecord = new RechargeRecord();
		rechargeRecord.setMemberId(newFriend.getNewFriendOneselfId());
		rechargeRecord.setName("打招呼");
		rechargeRecord.setCurrency(3);
		rechargeRecord.setMode(4);
		rechargeRecord.setType(2);
		rechargeRecord.setRunSize(newFriend.getRunSize());
		rechargeRecord.setSurplus(newFriend.getSurplus());
		rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
		rechargeRecordService.insert(rechargeRecord);

		String str = member.getNickName() + "向您打了招呼";
		Map<String, String> xtrasparams = new HashMap<>(); //扩展字段
		xtrasparams.put("type", "5");
		if (member1.getPushId() == null)
		{

		}
		else
		{
		    JpushUtil.sendToRegistrationId(member1.getPushId(), str, str, str, xtrasparams);
		}
		resultUtils.setMessage(Constant.SAY_HELLO_SUCCESSFUL);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  }
	  else
	  {
		resultUtils.setMessage(Constant.GREETING_FAILURE);
		resultUtils.setStatus(Constant.STATUS_FAILED);
	  }
	  return resultUtils;
    }

    @Override
    public ResultUtils refuseFriend(Integer newFriendId)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  NewFriend newFriend = newFriendMapper.selectById(newFriendId);
	  Integer newFriendOneselfId = newFriend.getNewFriendOneselfId();
	  Integer newFriendOtherPartyId = newFriend.getNewFriendOtherPartyId();
	  Member member = memberMapper.selectById(newFriendOneselfId);
	  Member member1 = memberMapper.selectById(newFriendOtherPartyId);
	  Integer insert = newFriendMapper.deleteById(newFriendId);
	  if (insert > 0)
	  {


		String str = member1.getNickName() + "拒绝了您的礼物";
		JpushUtil.sendToRegistrationId(member.getPushId(), str, str, str, new HashMap<>());
		resultUtils.setMessage(Constant.REJECTED_SUCCESSFULLY);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  }
	  else
	  {
		resultUtils.setMessage(Constant.REJECTION_FAILED);
		resultUtils.setStatus(Constant.STATUS_FAILED);
	  }
	  return resultUtils;
    }

    @Override
    public ResultUtils agreeFriend(Integer newFriendId)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  //查询接收方与发送方的打招呼记录
	  NewFriend newFriend = newFriendMapper.selectById(newFriendId);
	  if (newFriend == null)
	  {
		resultUtils.setStatus(Constant.STATUS_FAILED);
		resultUtils.setMessage(Constant.NO_RECORD_OF_GREETING);
		return resultUtils;
	  }
	  else
	  {
		//查询会员的好友
		// GroupJson groupJson = new GroupJson();
		// groupJson.setFrom_Account(newFriend.getNewFriendOneselfId().toString());
		List<UserDataItem> newFriendList = NewFriendList.friend_get(newFriend.getNewFriendOneselfId().toString());
		if (newFriendList == null)
		{
		    newFriendList = new ArrayList<>();
		}
		if (newFriendList.size() > 0)
		{
		    //好友ID数组
		    Integer[] integers = new Integer[newFriendList.size()];
		    for (int i = 0; i < newFriendList.size(); i++)
		    {
			  integers[i] = Integer.valueOf(newFriendList.get(i).getTo_Account());
		    }
		    if (Arrays.asList(integers).contains(newFriend.getNewFriendOtherPartyId()))
		    {
			  resultUtils.setStatus(Constant.STATUS_FAILED);
			  resultUtils.setMessage(Constant.THE_OTHER_PARTY_IS_ALREADY_YOUR_FRIEND);
			  return resultUtils;
		    }
		}
		Member member = memberMapper.selectById(newFriend.getNewFriendOtherPartyId());
		Member member1 = memberMapper.selectById(newFriend.getNewFriendOneselfId());
		//获取会员等级
		if (member.getLevel() < 50)
		{
		    Integer numberOfRemainingFriendsToAdd = member.getNumberOfRemainingFriendsToAdd();
		    if (numberOfRemainingFriendsToAdd == 0)
		    {
			  resultUtils.setStatus(Constant.STATUS_FAILED);
			  resultUtils.setMessage(Constant.FRIENDS_HAVE_REACHED_THE_LIMIT);
			  return resultUtils;
		    }
		}
		if (member1.getLevel() < 50)
		{
		    Integer numberOfRemainingFriendsToAdd = member1.getNumberOfRemainingFriendsToAdd();
		    if (numberOfRemainingFriendsToAdd == 0)
		    {
			  resultUtils.setStatus(Constant.STATUS_FAILED);
			  resultUtils.setMessage(Constant.FRIENDS_HAVE_REACHED_THE_LIMIT1);
			  return resultUtils;
		    }
		}
		Integer insert = 1;
		if (insert <= 0)
		{
		    resultUtils.setStatus(Constant.STATUS_FAILED);
		    resultUtils.setMessage(Constant.FAILED_TO_RECEIVE_GIFT);
		    return resultUtils;
		}
		else
		{
		    List<NewFriend> newFriends = newFriendMapper.selectList(new EntityWrapper<NewFriend>().eq("is_retreat",2).eq("new_friend_other_party_id", newFriend.getNewFriendOtherPartyId()).eq("new_friend_oneself_id", newFriend.getNewFriendOneselfId()));

		    Long runSize = 0l;
		    //设置一个数组，用来放礼物记录表的id
		    List<Long> listId = new ArrayList<>();
		    for (NewFriend friend : newFriends)
		    {
			  friend.setIsRetreat(1);
			  runSize += friend.getRunSize();
			  listId.add(Long.parseLong(friend.getRemarks().toString()) );
		    }
		    // 修改状态
		    boolean b = updateBatchById(newFriends);
		    if (b)
		    {
			  member.setNumberOfRemainingFriendsToAdd(member.getNumberOfRemainingFriendsToAdd() - 1);
			  memberMapper.updateById(member);
			  member1.setNumberOfRemainingFriendsToAdd(member1.getNumberOfRemainingFriendsToAdd() - 1);
			  memberMapper.updateById(member1);



			  // 添加魅力和银钻
			  MemberAssets memberAssets = memberAssetsService.selectMemberId(newFriend.getNewFriendOtherPartyId());
			  memberAssets.setMemberDiamondsizeOfSilver(memberAssets.getMemberDiamondsizeOfSilver() + runSize);
			  memberAssets.setOldMemberDiamondsizeOfSilver(memberAssets.getOldMemberDiamondsizeOfSilver() + runSize);
			  Boolean bl = memberAssetsService.updateById(memberAssets);
			  //如果魅力设置成功，在这里礼物记录表 礼物id的值设置为已接收 remarks礼物记录的id值
			  if(bl){
			      List<Giftconsumption> giftconsumption1 = new ArrayList<>();
			      for(Long ia : listId){
				    Giftconsumption giftconsumption = giftconsumptionMapper.selectById(ia);
				    // 1  是 2否
				    giftconsumption.setDrawInProportion(1);
				    giftconsumption1.add(giftconsumption);
				}
				iGiftconsumptionService.updateBatchById(giftconsumption1);
			  }


			  Giftconsumption giftconsumption = iGiftconsumptionService.selectById(newFriend.getRemarks());
			  giftconsumption.setDrawInProportion(1);
			  iGiftconsumptionService.updateById(giftconsumption);
			  //=================
			  Gift gift = giftMapper.selectById(newFriend.getNewFriendOtherPartyGiftId());
			  Integer i = newFriend.getNewFriendOtherPartyGiftSize() * Integer.valueOf(gift.getPrice());
			  //返佣
			  Integer memberId1 = newFriend.getNewFriendOtherPartyId();
			  //获取接收礼物的上级
			  Member member2 = memberMapper.selectById(memberId1);
			  Integer registrationChannel = member2.getRegistrationChannel();
			  //必须为颜值注册用户
			  if (registrationChannel == 1)
			  {
				Integer pid = member2.getPid();
				if (pid == null || pid == 0)
				{

				}
				else
				{
				    Award award = new Award(platformParameterSettingMapper, memberService, memberAssetsService, rechargeRecordService);
				    award.oneAward(pid, i, 1);
				    //获取接收礼物的上上级
				    Integer pid1 = memberMapper.selectById(pid).getPid();
				    if (pid1 == null || pid1 == 0)
				    {

				    }
				    else
				    {
					  award.twoAward(pid1, i, 1);
				    }
				}
			  }
			  //=================
			  List<MemberMatchingLog> memberMatchingLogs = memberMatchingLogMapper.selectList(new EntityWrapper<MemberMatchingLog>().eq("member_matching_log_member_id", newFriend.getNewFriendOtherPartyId()));
			  for (MemberMatchingLog memberMatchingLog : memberMatchingLogs)
			  {
				if (memberMatchingLog.getMemberMatchingLogMemberIdsSoul() == null || "".equals(memberMatchingLog.getMemberMatchingLogMemberIdsSoul()))
				{

				}
				else
				{
				    StringBuffer stringBuffer = new StringBuffer();
				    String[] split = memberMatchingLog.getMemberMatchingLogMemberIdsSoul().split(",");
				    for (int j = 0; j < split.length; j++)
				    {
					  if (!String.valueOf(newFriend.getNewFriendOtherPartyId()).equals(split[j]))
					  {
						stringBuffer.append(split[j]);
						stringBuffer.append(",");
					  }
				    }
				    memberMatchingLog.setMemberMatchingLogMemberIdsSoul(stringBuffer.toString());
				}
				if (memberMatchingLog.getMemberMatchingLogMemberIdsCar() == null || "".equals(memberMatchingLog.getMemberMatchingLogMemberIdsCar()))
				{

				}
				else
				{
				    StringBuffer stringBuffer = new StringBuffer();
				    String[] split = memberMatchingLog.getMemberMatchingLogMemberIdsCar().split(",");
				    for (int j = 0; j < split.length; j++)
				    {
					  if (!String.valueOf(newFriend.getNewFriendOtherPartyId()).equals(split[j]))
					  {
						stringBuffer.append(split[j]);
						stringBuffer.append(",");
					  }
				    }
				    memberMatchingLog.setMemberMatchingLogMemberIdsCar(stringBuffer.toString());
				}
				memberMatchingLogMapper.updateById(memberMatchingLog);
			  }
			  resultUtils.setMessage(Constant.AGREED_SUCCESSFULLY);
			  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		    }
		    else
		    {
			  resultUtils.setMessage(Constant.CONSENT_FAILED);
			  resultUtils.setStatus(Constant.STATUS_FAILED);
		    }
		    return resultUtils;
		}
	  }
    }

    @Override
    public ResultUtils friendLimit(Integer memberId)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  Member member = memberMapper.selectById(memberId);
	  //获取会员等级
	  if (member.getLevel() < 50)
	  {
		Integer numberOfRemainingFriendsToAdd = member.getNumberOfRemainingFriendsToAdd();
		if (numberOfRemainingFriendsToAdd <= 0)
		{
		    resultUtils.setStatus(Constant.STATUS_FAILED);
		    resultUtils.setMessage("您的好友数量已达上限");
		    return resultUtils;
		}
		else
		{
		    resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		    resultUtils.setMessage("您的好友数量未达上限");
		    return resultUtils;
		}
	  }
	  else
	  {
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setMessage("您的好友数量未达上限");
		return resultUtils;
	  }
    }


}
