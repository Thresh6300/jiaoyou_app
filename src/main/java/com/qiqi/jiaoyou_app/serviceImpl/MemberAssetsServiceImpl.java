package com.qiqi.jiaoyou_app.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.mapper.GiftMapper;
import com.qiqi.jiaoyou_app.mapper.GiftconsumptionMapper;
import com.qiqi.jiaoyou_app.mapper.MemberAssetsMapper;
import com.qiqi.jiaoyou_app.mapper.RedLogMapper;
import com.qiqi.jiaoyou_app.pojo.*;
import com.qiqi.jiaoyou_app.service.IMemberAssetsService;
import com.qiqi.jiaoyou_app.service.IPlatformParameterSettingService;
import com.qiqi.jiaoyou_app.service.WithdrawalLogsService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员资产表 服务实现类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Service("IMemberAssetsService")
public class MemberAssetsServiceImpl extends ServiceImpl<MemberAssetsMapper, MemberAssets> implements IMemberAssetsService
{

    @Autowired(required = false)
    private MemberAssetsMapper memberAssetsMapper;
    @Autowired(required = false)
    private GiftMapper giftMapper;
    @Autowired(required = false)
    private GiftconsumptionMapper giftconsumptionMapper;
    @Autowired(required = false)
    private RedLogMapper redLogMapper;
    @Autowired
    private RechargeRecordServiceImpl rechargeRecordService;
    @Autowired
    private WithdrawalLogsService withdrawalLogsService;
    @Autowired
    private IPlatformParameterSettingService iPlatformParameterSetting;
    @Override
    public ResultUtils myTrumpet(Integer id)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  List<MemberAssets> memberAssets = memberAssetsMapper.selectList(new EntityWrapper<MemberAssets>().eq("memberId", id));
	  MemberAssets memberAssets1;
	  if (memberAssets != null && memberAssets.size() > 0)
	  {
		memberAssets1 = memberAssets.get(0);
		//设置返回的累计提现的银钻数
		memberAssets1.setGetMemberDiamondsizeOfBlack(memberAssets.get(0).getMemberDiamondsizeOfBlack());
	  }
	  else
	  {
		memberAssets1 = new MemberAssets();

	  }
	  //计算累计钻石
	  List<Giftconsumption> list1 = giftconsumptionMapper.selectList(new EntityWrapper<Giftconsumption>().eq("sendMemberId", id).orderBy("addTime", false));
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
		if (map.get(giftId) == null)
		{
		    resultUtils.setStatus(Constant.STATUS_FAILED);
		    resultUtils.setMessage("您还没有资产");
		}
		else
		{
		    price += Integer.valueOf(map.get(giftId)) * giftconsumption.getGiftSize();
		}
	  }
	  memberAssets1.setGiftDiamondsSize(price);
	  //计算总钻石
	  List<RedLog> red_log_member_id = redLogMapper.selectList(new EntityWrapper<RedLog>().eq("red_log_member_id", id));

	  Integer price1 = 0;
	  for (RedLog redLog : red_log_member_id)
	  {
		price1 += redLog.getRedLogGoldSize();
	  }
	  memberAssets1.setRedDiamondsSize(price1);

	  List<PlatformParameterSetting> platformParameterSettings = iPlatformParameterSetting.selectList(null);
	  Long worldSpeakGlod = platformParameterSettings.get(0).getWorldSpeakGlod();
	  memberAssets1.setMemberhornSize(worldSpeakGlod);



	  resultUtils.setData(memberAssets1);
	  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
	  return resultUtils;
    }

	@Override
	public ResultUtils updateInfo(MemberAssets memberAssets) {
    	ResultUtils resultUtils = new ResultUtils();
    	//在这里查到会员的信息
		List<MemberAssets> list = memberAssetsMapper.selectList(new EntityWrapper<MemberAssets>().eq("memberId",memberAssets.getId()));
		MemberAssets memberAssets1 = list.get(0);
    	memberAssets1.setMemberDiamondsizeOfSilver(memberAssets1.getMemberDiamondsizeOfSilver() + memberAssets.getMemberDiamondsizeOfSilver());
    	Integer i = baseMapper.updateById(memberAssets1);
    	if(i > 0){
    		resultUtils.setStatus(200);
    		resultUtils.setMessage("更新成功");
		}
		resultUtils.setStatus(500);
    	resultUtils.setMessage("更新失败");
		return resultUtils;
	}

	@Override
	public ResultUtils GoldDiamondForSilverDiamond(MemberAssets memberAssets) {
		ResultUtils resultUtils = new ResultUtils();
		List<MemberAssets> memberId = memberAssetsMapper.selectList(new EntityWrapper<MemberAssets>().eq("memberId", memberAssets.getMemberId()));
		MemberAssets memberAssets1 = memberId.get(0);
		if (memberAssets1.getMemberDiamondsizeOfGold() < memberAssets.getMemberDiamondsizeOfGold()){
			resultUtils.setStatus(Constant.STATUS_FAILED);
			resultUtils.setMessage(Constant.LACK_OF_SILVER_DIAMONDS);
			return resultUtils;
		}
		memberAssets1.setMemberDiamondsizeOfGold(memberAssets1.getMemberDiamondsizeOfGold()-memberAssets.getMemberDiamondsizeOfGold());
		memberAssets1.setMemberDiamondsizeOfSilver(memberAssets1.getMemberDiamondsizeOfSilver()+memberAssets.getMemberDiamondsizeOfSilver());
		Integer integer = memberAssetsMapper.updateById(memberAssets1);

		if(integer > 0){
			WithdrawalLogs withdrawalLogs = new WithdrawalLogs();
			withdrawalLogs.setMemberid(memberAssets.getMemberId());
		    withdrawalLogs.setGold(memberAssets.getMemberDiamondsizeOfGold()+"");
			withdrawalLogs.setSliver(memberAssets.getMemberDiamondsizeOfSilver()+"");
//			1:金钻兑换银钻 2：银钻兑换金钻
			withdrawalLogs.setType("1");
			withdrawalLogsService.add(withdrawalLogs);
		}


		if (integer <= 0){
		      // 兑换金钻记录
			RechargeRecord rechargeRecord = new RechargeRecord();
			rechargeRecord.setMemberId(memberAssets1.getMemberId());
			rechargeRecord.setName(Constant.EXCHANGE_GOLD_DIAMOND);
			rechargeRecord.setCurrency(4);
			rechargeRecord.setMode(4);
			rechargeRecord.setType(2);
			rechargeRecord.setRunSize(memberAssets.getMemberDiamondsizeOfSilver());
			rechargeRecord.setSurplus(memberAssets1.getMemberDiamondsizeOfSilver());
			rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
			rechargeRecordService.insert(rechargeRecord);
			// 兑换钻石记录
			RechargeRecord rechargeRecord1 = new RechargeRecord();
			rechargeRecord1.setMemberId(memberAssets1.getMemberId());
			rechargeRecord1.setName(Constant.EXCHANGE_GOLD_DIAMOND);
			rechargeRecord1.setCurrency(3);
			rechargeRecord1.setMode(4);
			rechargeRecord1.setType(1);
			rechargeRecord1.setRunSize(memberAssets.getMemberDiamondsizeOfGold());
			rechargeRecord1.setSurplus(memberAssets1.getMemberDiamondsizeOfGold());
			rechargeRecord1.setAddTime(new Timestamp(System.currentTimeMillis()));
			rechargeRecordService.insert(rechargeRecord1);
			resultUtils.setStatus(Constant.STATUS_FAILED);
			resultUtils.setMessage(Constant.EXCHANGE_FAILURE);
			return resultUtils;
		}
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setMessage(Constant.SUCCESSFUL_EXCHANGE);
		return resultUtils;
	}
	//银钻兑换金钻
    @Override
    public ResultUtils silverDiamondForGoldDiamond(MemberAssets memberAssets)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  List<MemberAssets> memberId = memberAssetsMapper.selectList(new EntityWrapper<MemberAssets>().eq("memberId", memberAssets.getMemberId()));
	  MemberAssets memberAssets1 = memberId.get(0);
	  if (memberAssets1.getMemberDiamondsizeOfSilver() < memberAssets.getMemberDiamondsizeOfSilver())
	  {
		resultUtils.setStatus(Constant.STATUS_FAILED);
		resultUtils.setMessage(Constant.LACK_OF_SILVER_DIAMONDS);
		return resultUtils;
	  }
	  memberAssets1.setMemberDiamondsizeOfSilver(memberAssets1.getMemberDiamondsizeOfSilver() - memberAssets.getMemberDiamondsizeOfSilver());
	  memberAssets1.setMemberDiamondsizeOfGold(memberAssets1.getMemberDiamondsizeOfGold() + memberAssets.getMemberDiamondsizeOfGold());

	  //需要设置已现银钻数 memberAssets.getMemberDiamondsizeOfSilver()
	  memberAssets1.setMemberDiamondsizeOfBlack(memberAssets1.getMemberDiamondsizeOfBlack() + memberAssets.getMemberDiamondsizeOfSilver());

	  Integer integer = memberAssetsMapper.updateById(memberAssets1);

	  if(integer > 0){
			WithdrawalLogs withdrawalLogs = new WithdrawalLogs();
			withdrawalLogs.setMemberid(memberAssets.getMemberId());
		      withdrawalLogs.setGold(memberAssets.getMemberDiamondsizeOfGold()+"");
			withdrawalLogs.setSliver(memberAssets.getMemberDiamondsizeOfSilver()+"");
//			1:金钻兑换银钻 2：银钻兑换金钻
			withdrawalLogs.setType("2");
			withdrawalLogsService.add(withdrawalLogs);
		}


	  if (integer <= 0)
	  {
		return resultUtils;
	  }
	  RechargeRecord rechargeRecord = new RechargeRecord();
	  rechargeRecord.setMemberId(memberAssets1.getMemberId());
	  rechargeRecord.setName(Constant.EXCHANGE_GOLD_DIAMOND);
	  rechargeRecord.setCurrency(3);
	  rechargeRecord.setMode(4);
	  rechargeRecord.setType(1);
	  rechargeRecord.setRunSize(memberAssets.getMemberDiamondsizeOfGold());
	  rechargeRecord.setSurplus(memberAssets1.getMemberDiamondsizeOfGold());
	  rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
	  rechargeRecordService.insert(rechargeRecord);
	  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  resultUtils.setMessage(Constant.SUCCESSFUL_EXCHANGE);
	  return resultUtils;
    }

    @Override
    public ResultUtils silverDiamondForBlackDiamond(MemberAssets memberAssets)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  List<MemberAssets> memberId = memberAssetsMapper.selectList(new EntityWrapper<MemberAssets>().eq("memberId", memberAssets.getMemberId()));
	  MemberAssets memberAssets1 = memberId.get(0);
	  if (memberAssets1.getMemberDiamondsizeOfSilver() < memberAssets.getMemberDiamondsizeOfSilver())
	  {
		resultUtils.setStatus(Constant.STATUS_FAILED);
		resultUtils.setMessage(Constant.LACK_OF_SILVER_DIAMONDS);
		return resultUtils;
	  }
	  memberAssets1.setMemberDiamondsizeOfSilver(memberAssets1.getMemberDiamondsizeOfSilver() - memberAssets.getMemberDiamondsizeOfSilver());
	  memberAssets1.setMemberDiamondsizeOfBlack(memberAssets1.getMemberDiamondsizeOfBlack() == null ? memberAssets.getMemberDiamondsizeOfBlack() : memberAssets1.getMemberDiamondsizeOfBlack() + memberAssets.getMemberDiamondsizeOfBlack());
	  Integer integer = memberAssetsMapper.updateById(memberAssets1);
	  if (integer <= 0)
	  {
		RechargeRecord rechargeRecord = new RechargeRecord();
		rechargeRecord.setMemberId(memberAssets1.getMemberId());
		rechargeRecord.setName(Constant.EXCHANGE_FOR_BLACK_DIAMOND);
		rechargeRecord.setCurrency(4);
		rechargeRecord.setMode(4);
		rechargeRecord.setType(2);
		rechargeRecord.setRunSize(memberAssets.getMemberDiamondsizeOfSilver());
		rechargeRecord.setSurplus(memberAssets1.getMemberDiamondsizeOfSilver());
		rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
		rechargeRecordService.insert(rechargeRecord);
		RechargeRecord rechargeRecord1 = new RechargeRecord();
		rechargeRecord1.setMemberId(memberAssets1.getMemberId());
		rechargeRecord1.setName(Constant.EXCHANGE_FOR_BLACK_DIAMOND);
		rechargeRecord1.setCurrency(5);
		rechargeRecord1.setMode(4);
		rechargeRecord1.setType(1);
		rechargeRecord1.setRunSize(memberAssets.getMemberDiamondsizeOfBlack());
		rechargeRecord1.setSurplus(memberAssets1.getMemberDiamondsizeOfBlack());
		rechargeRecord1.setAddTime(new Timestamp(System.currentTimeMillis()));
		rechargeRecordService.insert(rechargeRecord1);
		resultUtils.setStatus(Constant.STATUS_FAILED);
		resultUtils.setMessage(Constant.EXCHANGE_FAILURE);
		return resultUtils;
	  }
	  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  resultUtils.setMessage(Constant.SUCCESSFUL_EXCHANGE);
	  return resultUtils;
    }

    @Override
    public MemberAssets selectMemberId(Integer memberId)
    {
	  return  selectOne(new EntityWrapper<MemberAssets>().eq("memberId",memberId));
    }
}
