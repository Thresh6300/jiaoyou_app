package com.qiqi.jiaoyou_app.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.mapper.*;
import com.qiqi.jiaoyou_app.pojo.*;
import com.qiqi.jiaoyou_app.service.IMemberAssetsService;
import com.qiqi.jiaoyou_app.service.IWithdrawService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 提现申请表 服务实现类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@SuppressWarnings("ALL")
@Service
public class WithdrawServiceImpl extends ServiceImpl<WithdrawMapper, Withdraw> implements IWithdrawService
{

    @Autowired
    private ProportionofwithdrawalServiceImpl proportionofwithdrawalService;
    @Autowired
    private GiftconsumptionServiceImpl giftconsumptionService;
    @Autowired
    private GiftconsumptionMapper giftconsumptionMapper;
    @Autowired
    private GiftServiceImpl giftService;
    @Autowired
    private MemberServiceImpl memberService;
    @Autowired
    private BankcardServiceImpl bankcardService;
    @Autowired
    private MemberAssetsMapper memberAssetsMapper;
    @Autowired
    private IMemberAssetsService iMemberAssetsService;
    @Autowired
    private ProportionofwithdrawalMapper proportionofwithdrawalMapper;
    @Autowired
    private PlatformParameterSettingMapper platformParameterSettingMapper;

    @Autowired
    private WithdrawMapper withdrawMapper;


    //发起提现申请
    @Override
    public ResultUtils addWithdraw(Withdraw withdraw)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  //获取当前日期
	  int i = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	  if (i != 3 && i != 4)
	  {
		resultUtils.setStatus(Constant.STATUS_FAILED);
		resultUtils.setMessage("每月只能在3-4号进行提现操作");
		return resultUtils;
	  }


	  //修改内容,将提现的黑钻改为银钻
	  //author:nan
	  //time:20210131

	  //获取用户的提现比例
	  //如果体现比例没有就计算
	  if(withdraw.getProportion() == null || withdraw.getProportion().toString() == ""){
		withdraw.setProportion(bigDecimalAAA(withdraw.getMemberId()));
	  }
	  //获取这个会员的资产信息
	  MemberAssets memberAssets = memberAssetsMapper.selectList(new EntityWrapper<MemberAssets>().eq("memberId", withdraw.getMemberId())).get(0);

	  //如果提现的金额大于当前用户的金额 是不可以的
	  if ((withdraw.getPrice().divide(withdraw.getProportion(), 2, BigDecimal.ROUND_HALF_UP)).compareTo(new BigDecimal(memberAssets.getMemberDiamondsizeOfSilver())) == 1)
	  {
		resultUtils.setStatus(Constant.STATUS_FAILED);
		resultUtils.setMessage(Constant.WITHDRAWAL_AMOUNT_EXCEEDS_THE_UPPER_LIMIT);
		return resultUtils;
	  }
	  //减去提现的银钻数(bigDecimal1.divide(bigDecimal, 2, BigDecimal.ROUND_HALF_UP)).divide(new BigDecimal("0.7"),2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("10"))
	  memberAssets.setMemberDiamondsizeOfSilver(memberAssets.getMemberDiamondsizeOfSilver() - (withdraw.getPrice().divide(withdraw.getProportion(), 2, BigDecimal.ROUND_HALF_UP)).divide(new BigDecimal("0.7"),2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("10")).intValue());

	  Member member = memberService.selectById(withdraw.getMemberId());
	  Bankcard bankcard = bankcardService.selectById(withdraw.getBankId());
	  withdraw.setBalance(new BigDecimal(memberAssets.getMemberDiamondsizeOfSilver()).multiply(withdraw.getProportion()));
	  withdraw.setMemberHead(member.getHead());
	  withdraw.setMemberNickName(member.getNickName());
	  withdraw.setMemberPhone(member.getPhone());
	  withdraw.setBankMemberName(bankcard.getBankMemberName());
	  withdraw.setBankName(bankcard.getOpeningBank());
	  withdraw.setBankNumber(bankcard.getBankNumber());
	  withdraw.setExamineState(3);//待审核
	  //申请时间
	  withdraw.setApplyTime(new Timestamp(System.currentTimeMillis()));
	  //新数据
	  withdraw.setOldOrNew(1);
	  //当前用户提现实际到账金额
	  withdraw.setMoney(withdraw.getPrice());
	  if (withdraw.getMoney().intValue() == 0)
	  {
		resultUtils.setStatus(Constant.STATUS_FAILED);
		resultUtils.setMessage(Constant.WITHDRAWAL_APPLICATION_FAILED);
		return resultUtils;
	  }
	  Integer insert = withdrawMapper.insert(withdraw);
	  if (insert <= 0)
	  {
		resultUtils.setStatus(Constant.STATUS_FAILED);
		resultUtils.setMessage(Constant.WITHDRAWAL_APPLICATION_FAILED);
		return resultUtils;
	  }
	  Integer integer = memberAssetsMapper.updateById(memberAssets);
	  if (integer <= 0)
	  {
		resultUtils.setStatus(Constant.STATUS_FAILED);
		resultUtils.setMessage(Constant.WITHDRAWAL_APPLICATION_FAILED);
		return resultUtils;
	  }
	  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  resultUtils.setMessage(Constant.SUCCESSFUL_WITHDRAWAL_APPLICATION);
	  return resultUtils;


	  // if(withdraw.getProportion() == null || withdraw.getProportion().toString() == ""){
	  //     withdraw.setProportion(conf(withdraw.getMemberId()));
	  // }
	  //
	  // MemberAssets memberAssets = memberAssetsMapper.selectList(new EntityWrapper<MemberAssets>().eq("memberId", withdraw.getMemberId())).get(0);
	  //
	  // if ((withdraw.getPrice().divide(withdraw.getProportion(), 2, BigDecimal.ROUND_HALF_UP)).compareTo(new BigDecimal(memberAssets.getMemberDiamondsizeOfBlack())) == 1)
	  // {
		// resultUtils.setStatus(Constant.STATUS_FAILED);
		// resultUtils.setMessage(Constant.WITHDRAWAL_AMOUNT_EXCEEDS_THE_UPPER_LIMIT);
		// return resultUtils;
	  // }
	  // memberAssets.setMemberDiamondsizeOfBlack(memberAssets.getMemberDiamondsizeOfBlack() - (withdraw.getPrice().divide(withdraw.getProportion(), 2, BigDecimal.ROUND_HALF_UP)).intValue());
	  //
	  // Member member = memberService.selectById(withdraw.getMemberId());
	  // Bankcard bankcard = bankcardService.selectById(withdraw.getBankId());
	  // withdraw.setBalance(new BigDecimal(memberAssets.getMemberDiamondsizeOfBlack()).multiply(withdraw.getProportion()));
	  // withdraw.setMemberHead(member.getHead());
	  // withdraw.setMemberNickName(member.getNickName());
	  // withdraw.setMemberPhone(member.getPhone());
	  // withdraw.setBankMemberName(bankcard.getBankMemberName());
	  // withdraw.setBankName(bankcard.getOpeningBank());
	  // withdraw.setBankNumber(bankcard.getBankNumber());
	  // withdraw.setExamineState(3);//待审核
	  // //申请时间
	  // withdraw.setApplyTime(new Timestamp(System.currentTimeMillis()));
	  // //新数据
	  // withdraw.setOldOrNew(1);
	  // //当前用户提现实际到账金额
	  // withdraw.setMoney(withdraw.getPrice());
	  // if (withdraw.getMoney().intValue() == 0)
	  // {
		// resultUtils.setStatus(Constant.STATUS_FAILED);
		// resultUtils.setMessage(Constant.WITHDRAWAL_APPLICATION_FAILED);
		// return resultUtils;
	  // }
	  // Integer insert = withdrawMapper.insert(withdraw);
	  // if (insert <= 0)
	  // {
		// resultUtils.setStatus(Constant.STATUS_FAILED);
		// resultUtils.setMessage(Constant.WITHDRAWAL_APPLICATION_FAILED);
		// return resultUtils;
	  // }
	  // Integer integer = memberAssetsMapper.updateById(memberAssets);
	  // if (integer <= 0)
	  // {
		// resultUtils.setStatus(Constant.STATUS_FAILED);
		// resultUtils.setMessage(Constant.WITHDRAWAL_APPLICATION_FAILED);
		// return resultUtils;
	  // }
	  // resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  // resultUtils.setMessage(Constant.SUCCESSFUL_WITHDRAWAL_APPLICATION);
	  // return resultUtils;
    }

    /***
     * @Description: 获取体现比例
     * @return: java.math.BigDecimal
     * @Author: nan
     * @Date: 2021-01-31 15:08
     */

    public BigDecimal bigDecimalAAA(Integer memberId){
	  //1：查询我的邀请人数
	  Member member = memberService.selectById(memberId);
	  Integer recommended = member.getRecommended();
	  //获取当前用户的资产信息
	  System.out.println("recommended" + recommended);
	  MemberAssets memberAssets = iMemberAssetsService.selectMemberId(memberId);

	  //2：查询提现的银钻
	  Long memberDiamondsizeOfSilver = memberAssets.getMemberDiamondsizeOfSilver();
	  // Long memberDiamondsizeOfSilver = memberAssets.getOldMemberDiamondsizeOfSilver();
	  System.out.println("memberDiamondsizeOfSilver" + memberDiamondsizeOfSilver);
	  BigDecimal bigDecimal = new BigDecimal(memberDiamondsizeOfSilver);
	  bigDecimal = bigDecimal.multiply(new BigDecimal("0.7")).divide(new BigDecimal("10"));

	  //获取提现比例表的信息
	  EntityWrapper<Proportionofwithdrawal> wrapper = new EntityWrapper<>();
	  wrapper.orderBy("numberOfParticipants",false).orderBy("giftCost",false);

	  //如果下级人数或者是礼物金额小于等于
	  wrapper.le("numberOfParticipants", recommended).or().le("giftCost", memberAssets.getOldMemberDiamondsizeOfSilver());

	  //3：查询对应的提现比例
	  List<Proportionofwithdrawal> proportionofwithdrawals = proportionofwithdrawalMapper.selectList(wrapper);
	  if (proportionofwithdrawals != null && proportionofwithdrawals.size() > 0)
	  {
		Proportionofwithdrawal proportionofwithdrawal = proportionofwithdrawals.get(0);
		System.out.println(proportionofwithdrawal);
		bigDecimal = proportionofwithdrawal.getProportion();
		// return  bigDecimal;
	  }
	  return  bigDecimal;
    }


    /**
     * 获取用户实际到账金额
     *
     * @return BigDecimal
     */
    public BigDecimal money(BigDecimal price, Integer uid)
    {
	  return price.multiply(conf(uid).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * 获取提现比例
     *
     * @return BigDecimal
     */
    public BigDecimal conf(Integer id)
    {
	  List<Proportionofwithdrawal> proportion = proportionofwithdrawalService.selectList(new EntityWrapper<Proportionofwithdrawal>().orderBy("proportion", false));
	  Member user = memberService.selectById(id);
	  List<Map<String, Object>> maps = giftconsumptionMapper.groupCount(id);
	  Integer price = 0;
	  for (Map<String, Object> map : maps)
	  {
		Gift gift = giftService.selectOne(new EntityWrapper<Gift>().eq("id", map.get("giftId")));
		price += (gift.getPrice() * ((BigDecimal) map.get("sumSize")).intValue());
	  }
	  BigDecimal ratio = new BigDecimal(0);
	  for (Proportionofwithdrawal data : proportion)
	  {
		if (data.getNumberOfParticipants() <= user.getRecommended() || data.getGiftCost() <= price)
		{
		    ratio = data.getProportion();
		    break;
		}
	  }
	  return ratio;
    }

    @Override
    public ResultUtils withdrawLog(Integer pageNum, Integer pageSize, Integer memberId)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  Page<Withdraw> page = new Page<>();
	  List<Withdraw> memberId1 = withdrawMapper.selectPage(page, new EntityWrapper<Withdraw>().eq("memberId", memberId));
	  resultUtils.setData(memberId1);
	  resultUtils.setData2(memberId1);
	  resultUtils.setData3(memberId1);
	  resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
	  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  resultUtils.setData1(page);
	  return resultUtils;
    }

    /**
     * @Description: withdrawalBalance
     * @param: memberId
     * @return: com.qiqi.jiaoyou_app.util.ResultUtils
     * @Author: cfx
     * @Date: 2021-01-04 19:59
     * 当前银钻*0.7/10*提现比例
     */
    @Override
    public ResultUtils withdrawalBalance(Integer memberId)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
	  resultUtils.setData(new BigDecimal("0.00"));

	  //1：查询我的邀请人数
	  Member member = memberService.selectById(memberId);
	  Integer recommended = member.getRecommended();
	  //获取当前用户的资产信息
	  System.out.println("recommended" + recommended);
	  MemberAssets memberAssets = iMemberAssetsService.selectMemberId(memberId);

	  //2：查询提现的银钻
	  Long memberDiamondsizeOfSilver = memberAssets.getMemberDiamondsizeOfSilver();
	  // Long memberDiamondsizeOfSilver = memberAssets.getOldMemberDiamondsizeOfSilver();
	  System.out.println("memberDiamondsizeOfSilver" + memberDiamondsizeOfSilver);
	  BigDecimal bigDecimal = new BigDecimal(memberDiamondsizeOfSilver);
	  bigDecimal = bigDecimal.multiply(new BigDecimal("0.7")).divide(new BigDecimal("10"));

	  //获取提现比例表的信息
	  EntityWrapper<Proportionofwithdrawal> wrapper = new EntityWrapper<>();
	  wrapper.orderBy("numberOfParticipants",false).orderBy("giftCost",false);

	  //如果下级人数或者是礼物金额小于等于
	  wrapper.le("numberOfParticipants", recommended).or().le("giftCost", memberAssets.getOldMemberDiamondsizeOfSilver());

	  //3：查询对应的提现比例
	  List<Proportionofwithdrawal> proportionofwithdrawals = proportionofwithdrawalMapper.selectList(wrapper);
	  if (proportionofwithdrawals != null && proportionofwithdrawals.size() > 0)
	  {
		Proportionofwithdrawal proportionofwithdrawal = proportionofwithdrawals.get(0);
		System.out.println(proportionofwithdrawal);
		bigDecimal = bigDecimal.multiply(proportionofwithdrawal.getProportion());
		bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);//保留两位小数
		resultUtils.setData(bigDecimal);
		return resultUtils;
	  }


	  return resultUtils;
    }
}
