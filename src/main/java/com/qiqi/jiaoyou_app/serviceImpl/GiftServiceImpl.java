package com.qiqi.jiaoyou_app.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.controller.award.Award;
import com.qiqi.jiaoyou_app.mapper.*;
import com.qiqi.jiaoyou_app.pojo.*;
import com.qiqi.jiaoyou_app.service.IGiftService;
import com.qiqi.jiaoyou_app.service.IMemberAssetsService;
import com.qiqi.jiaoyou_app.service.INewFriendService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 礼物表 服务实现类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Service
public class GiftServiceImpl extends ServiceImpl<GiftMapper, Gift> implements IGiftService
{

    @Autowired
    private GiftMapper giftMapper;
    @Autowired
    private GiftconsumptionMapper giftconsumptionMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private DiamondconsumptionrecordMapper diamondconsumptionrecordMapper;
    @Autowired
    private IMemberAssetsService iMemberAssetsService;
    @Autowired
    private PlatformParameterSettingMapper platformParameterSettingMapper;
    @Autowired
    private SpeakerconsumptionrecordMapper speakerconsumptionrecordMapper;
    @Autowired
    private RechargeRecordServiceImpl rechargeRecordService;
    @Autowired
    private MemberAssetsServiceImpl memberAssetsService;
    @Autowired
    private MemberServiceImpl memberService;
    @Autowired
    private INewFriendService iNewFriendService;


    @Override
    public ResultUtils giftList(Integer pageSize, Integer pageNum, String keyWord)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  Page page = new Page(pageNum, pageSize);
	  page.setOptimizeCountSql(true);
	  page.setSearchCount(true);
	  List<Gift> gifts = giftMapper.selectPage(page, new EntityWrapper<Gift>().like(!StringUtils.isEmpty(keyWord), "name", keyWord).eq("enableState", 1).eq("deleteState", 2));
	  resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
	  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  resultUtils.setData(gifts);
	  resultUtils.setCount((int) page.getTotal());
	  return resultUtils;
    }

    @Override
    public ResultUtils giftsGiven(Integer pageSize, Integer pageNum, Integer id)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  Page<Giftconsumption> page = new Page<>();
	  List<Giftconsumption> list1 = giftconsumptionMapper.selectList(new EntityWrapper<Giftconsumption>().eq("sendMemberId", id).orderBy("addTime", false));

	  List<Giftconsumption> list = giftconsumptionMapper.selectPage(page, new EntityWrapper<Giftconsumption>().eq("sendMemberId", id).orderBy("addTime", false));
	  //计算累计钻石
	  List<Gift> gifts = giftMapper.selectList(new EntityWrapper<Gift>());
	  Map<Integer, Integer> map = new HashMap<>();
	  for (Gift gift : gifts)
	  {
		map.put(gift.getId(), gift.getPrice());
	  }
	  Integer price = 0;
	  for (Giftconsumption giftconsumption : list1)
	  {
		Integer giftId = giftconsumption.getGiftId();
		if (map.get(giftId) == null || "".equals(map.get(giftId)))
		{

		}
		else
		{
		    price += Integer.valueOf(map.get(giftId)) * giftconsumption.getGiftSize();
		}
	  }

	  for (Giftconsumption giftconsumption : list)
	  {
		Member member = memberMapper.selectById(giftconsumption.getMemberId());
		giftconsumption.setSendMemberHead(member.getHead());
		giftconsumption.setSendMemberNickName(member.getNickName());
	  }
	  resultUtils.setData(list);
	  resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
	  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  resultUtils.setData1(page);
	  resultUtils.setCount(price);
	  return resultUtils;
    }

    //送礼,没错送礼物的位置
    @Override
    public ResultUtils giveGift(Giftconsumption giftconsumption)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  if (giftconsumption.getSendMemberId().equals(giftconsumption.getMemberId()))
	  {
		resultUtils.setMessage("不可以自己给自己送礼物");
		resultUtils.setStatus(Constant.STATUS_FAILED);
		return resultUtils;
	  }
	  //判断钻石是否够
	  List<MemberAssets> memberId = iMemberAssetsService.selectList(new EntityWrapper<MemberAssets>().eq("memberId", giftconsumption.getSendMemberId()));
	  MemberAssets memberAssets = memberId.get(0);
	  Gift gift = giftMapper.selectById(giftconsumption.getGiftId());
	  Integer i = giftconsumption.getGiftSize() * Integer.valueOf(gift.getPrice());
	  if (memberAssets.getMemberDiamondsizeOfGold() < i)
	  {
		resultUtils.setMessage("钻石数量不足，请充值");
		resultUtils.setStatus(Constant.STATUS_FAILED);
		return resultUtils;
	  }
	  Member member = memberMapper.selectById(giftconsumption.getSendMemberId());
	  giftconsumption.setSendMemberHead(member.getHead());
	  giftconsumption.setSendMemberNickName(member.getNickName());
	  giftconsumption.setGiftName(gift.getName());
	  giftconsumption.setGiftImages(gift.getImages());
	  giftconsumption.setAddTime(new Timestamp(System.currentTimeMillis()));
	  giftconsumption.setDrawInProportion(2);
	  Integer insert = giftconsumptionMapper.insert(giftconsumption);
	  if (insert > 0)
	  {
		resultUtils.setMessage("赠送成功");
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setData(giftconsumption);
	  }
	  else
	  {
		resultUtils.setMessage("赠送失败");
		resultUtils.setStatus(Constant.STATUS_FAILED);
	  }

	  RechargeRecord rechargeRecord = new RechargeRecord();
	  rechargeRecord.setMemberId(giftconsumption.getSendMemberId());
	  rechargeRecord.setName("发送礼物");
	  rechargeRecord.setCurrency(3);
	  rechargeRecord.setMode(4);
	  rechargeRecord.setType(2);
	  rechargeRecord.setRunSize(Long.valueOf(i));
	  rechargeRecord.setSurplus(memberAssets.getMemberDiamondsizeOfGold() - i);
	  rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
	  rechargeRecordService.insert(rechargeRecord);

	  return resultUtils;
    }

    @Override
    public ResultUtils giftsReceived(Integer pageSize, Integer pageNum, Integer id)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  Page<Giftconsumption> page = new Page<>(pageNum, pageSize);
	  List<Giftconsumption> list = giftconsumptionMapper.selectPage(page, new EntityWrapper<Giftconsumption>().eq("memberId", id).eq("drawInProportion", 1).orderBy("addTime", false));
	  for (Giftconsumption giftconsumption : list)
	  {
		Member member = memberMapper.selectById(giftconsumption.getSendMemberId());
		giftconsumption.setSendMemberId(member.getId());
		giftconsumption.setSendMemberNickName(member.getNickName());
		giftconsumption.setSendMemberHead(member.getHead());
		giftconsumption.setSendLable(member.getCarLable());
	  }
	  resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
	  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  resultUtils.setData(list);
	  resultUtils.setData1(page);
	  return resultUtils;
    }

    @Override
    public ResultUtils buyASmallHorn(MemberAssets memberAssets)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  List<MemberAssets> memberId = iMemberAssetsService.selectList(new EntityWrapper<MemberAssets>().eq("memberId", memberAssets.getMemberId()));
	  MemberAssets memberAssets1 = memberId.get(0);
	  PlatformParameterSetting platformParameterSetting = platformParameterSettingMapper.selectById(1);
	  Integer unitPriceOfLoudspeaker = platformParameterSetting.getUnitPriceOfLoudspeaker();
	  long zuanshi = memberAssets.getMemberhornSize() * unitPriceOfLoudspeaker;
	  if (zuanshi > memberAssets1.getMemberDiamondsizeOfGold())
	  {
		resultUtils.setMessage("钻石数量不足,请充值");
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		return resultUtils;
	  }
	  memberAssets1.setMemberDiamondsizeOfGold(memberAssets1.getMemberDiamondsizeOfGold() - zuanshi);
	  memberAssets1.setMemberhornSize(memberAssets1.getMemberhornSize() + memberAssets.getMemberhornSize());
	  boolean b = iMemberAssetsService.updateById(memberAssets1);
	  if (b)
	  {
		RechargeRecord rechargeRecord = new RechargeRecord();
		rechargeRecord.setMemberId(memberAssets1.getMemberId());
		rechargeRecord.setName("购买小喇叭");
		rechargeRecord.setCurrency(3);
		rechargeRecord.setMode(4);
		rechargeRecord.setType(2);
		rechargeRecord.setRunSize(zuanshi);
		rechargeRecord.setSurplus(memberAssets1.getMemberDiamondsizeOfGold());
		rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
		rechargeRecordService.insert(rechargeRecord);
		RechargeRecord rechargeRecord1 = new RechargeRecord();
		rechargeRecord1.setMemberId(memberAssets1.getMemberId());
		rechargeRecord1.setName("购买小喇叭");
		rechargeRecord1.setCurrency(6);
		rechargeRecord1.setMode(4);
		rechargeRecord1.setType(1);
		rechargeRecord1.setRunSize(memberAssets.getMemberhornSize());
		rechargeRecord1.setSurplus(memberAssets1.getMemberhornSize());
		rechargeRecord1.setAddTime(new Timestamp(System.currentTimeMillis()));
		rechargeRecordService.insert(rechargeRecord1);
		resultUtils.setMessage("小喇叭购买成功");
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  }
	  else
	  {
		resultUtils.setMessage("小喇叭购买失败");
		resultUtils.setStatus(Constant.STATUS_FAILED);
	  }
	  return resultUtils;
    }

    @Override
    public ResultUtils speakerConsumption(Speakerconsumptionrecord speakerconsumptionrecord)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  List<MemberAssets> memberId = iMemberAssetsService.selectList(new EntityWrapper<MemberAssets>().eq("memberId", speakerconsumptionrecord.getMemberId()));
	  //会员剩余喇叭数量
	  Long memberhornSize = memberId.get(0).getMemberhornSize();
	  if (speakerconsumptionrecord.getSurplusSize() < 0)
	  {
		resultUtils.setStatus(Constant.STATUS_FAILED);
		resultUtils.setMessage("小喇叭数量不足");
		return resultUtils;
	  }
	  speakerconsumptionrecord.setConsumptionTime(new Timestamp(System.currentTimeMillis()));
	  Integer insert = speakerconsumptionrecordMapper.insert(speakerconsumptionrecord);
	  if (insert <= 0)
	  {
		resultUtils.setStatus(Constant.STATUS_FAILED);
		resultUtils.setMessage("小喇叭消费失败");
	  }
	  else
	  {
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setMessage("小喇叭消费成功");
	  }
	  return resultUtils;
    }

    /***
     * @Description:硬了！我的拳头硬了。。。。。。。。。。。。。。
     * 这里首先需要查看到用户接收的所有的礼物，并给所有礼物设置已经接收的状态，不然魅力排行榜会出错
     * @Author: update nan
     * @Date: 2021-01-20 15:20
     */

    @Override
    public ResultUtils ReceiveGift(Giftconsumption giftconsumption)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  //获取赠送方礼物记录
	  Giftconsumption giftconsumption1 = giftconsumptionMapper.selectById(giftconsumption.getRevision());
	  if (!giftconsumption1.getMemberId().equals(giftconsumption.getMemberId()))
	  {
		resultUtils.setMessage("您不是该礼物的接收方，不可领取");
		resultUtils.setStatus(Constant.STATUS_FAILED);
		return resultUtils;
	  }
	  if (giftconsumption1.getDrawInProportion() == 1)
	  {
		resultUtils.setMessage("该礼物已被领取，不可重复领取");
		resultUtils.setStatus(Constant.STATUS_FAILED);
		return resultUtils;
	  }
	  //在礼物记录表设置礼物已经被收到
	  giftconsumption1.setDrawInProportion(1);
	  Integer integer = giftconsumptionMapper.updateById(giftconsumption1);

	  if (integer <= 0)
	  {
		resultUtils.setStatus(Constant.STATUS_FAILED);
		resultUtils.setMessage("接收礼物失败");
	  }
	  else
	  {
	      //接收礼物成功的时候呢，就
		Gift gift = giftMapper.selectById(giftconsumption1.getGiftId());
		Integer i = giftconsumption1.getGiftSize() * Integer.valueOf(gift.getPrice());
		//返佣
		Integer memberId1 = giftconsumption.getMemberId();
		//获取接收礼物的上级
		Member member1 = memberMapper.selectById(memberId1);
		Integer registrationChannel = member1.getRegistrationChannel();
		//必须为颜值注册用户
		if (registrationChannel == 1)
		{
		    Integer pid = member1.getPid();
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
		// 接收方增加银钻和魅力
		MemberAssets memberAssets = iMemberAssetsService.selectMemberId(memberId1);
		//加银钻
		memberAssets.setMemberDiamondsizeOfSilver(memberAssets.getMemberDiamondsizeOfSilver() + i);
		//加魅力
		memberAssets.setOldMemberDiamondsizeOfSilver(memberAssets.getOldMemberDiamondsizeOfSilver() + i);
		iMemberAssetsService.updateById(memberAssets);

		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setMessage("接收礼物成功");
	  }
	  return resultUtils;
    }
}
