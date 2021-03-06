package com.qiqi.jiaoyou_app;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qiqi.jiaoyou_app.mapper.*;
import com.qiqi.jiaoyou_app.pojo.*;
import com.qiqi.jiaoyou_app.service.*;
import com.qiqi.jiaoyou_app.serviceImpl.MemberServiceImpl;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.qiqi.jiaoyou_app.quartz.MeetingQuartz.dateToStamp;

@SpringBootTest
class JiaoyouAppApplicationTests {

    @Autowired
    private IRedLogService iRedLogService;
    @Autowired
    private IMemberAssetsService iMemberAssetsService;
    @Autowired
    private MemberServiceImpl memberService;
    @Autowired
    private ClubService clubService;
    @Autowired
    private ISendOfflineActivitiesService iSendOfflineActivitiesService;
    @Autowired
    private IRechargeRecordService iRechargeRecordService;
    @Autowired
    private AcceptOfflineActivitiesMapper acceptOfflineActivitiesMapper;
    @Autowired
    private ProportionofwithdrawalMapper proportionofwithdrawalMapper;
	@Autowired
	private GiftconsumptionMapper giftconsumptionMapper;



	@Autowired
	private IMemberAssetsService memberAssetsMapper;
	@Autowired
	private GiftMapper giftMapper;
	@Autowired
	private SystemMessageMapper systemMessageMapper;
	@Autowired
	private IMemberService memberMapper;
	@Autowired
	private RechargeRecordMapper rechargeRecordMapper;


	@Autowired
	private IGiftconsumptionService iGiftconsumptionService;
    private final static Logger logger = LoggerFactory.getLogger(JiaoyouAppApplicationTests.class);

	@Test
	void sendGifts(){
 		System.out.println("hai");
	}


    @Test
	void sendGift(){
		//连接本地的 Redis 服务
//		Jedis jedis = new Jedis("http://localhost:6379");
//		System.out.println("连接成功");
//		//查看服务是否运行
//		System.out.println("服务正在运行: "+jedis.ping());
//		System.out.println("======================key==========================");
//		//清除当前数据库所有数据
//		jedis.flushDB();
//		//设置键值对
//		jedis.set("xiaohua","我是小花");
//		//查看存储的键的总数
//		System.out.println(jedis.dbSize());
//		//取出设置的键值对并打印
//		System.out.println(jedis.get("xiaohua"));


		//该礼物是否被接收方接收 1： 是 2 ：否
		//查询所有没有被接收的礼物信息
		List<Giftconsumption> list = giftconsumptionMapper.selectList(new EntityWrapper<Giftconsumption>()
				.eq("drawInProportion",2));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(new Date());

		MemberAssets listm = new MemberAssets();

		for(Giftconsumption giftconsumption : list){
			//若是添加时间 加上24小时 小于等于当前时间的话 就走回退的逻辑
			try {
				if((giftconsumption.getAddTime().getTime() / 1000) +  360 <=   (Long.parseLong(dateToStamp(str) ) / 1000) ){
//					if(giftconsumption.getTime() / 1000) +  86400 <=   (Long.parseLong(dateToStamp(str) ) / 1000) ){
						//退钱
					//获取会员信息
					MemberAssets memberAssets =  memberAssetsMapper.selectById(giftconsumption.getSendMemberId());
					//h获取接收人的昵称信息
					Member memberAssetsRec =  memberMapper.selectById(giftconsumption.getMemberId());

					//获取礼物信息
					Gift gift = giftMapper.selectById(giftconsumption.getGiftId());
					//计算送出的金钻数量返回给用户
					Integer integerS = gift.getPrice() + giftconsumption.getGiftSize();
					//设置用户的金钻金额
					memberAssets.setMemberDiamondsizeOfGold(memberAssets.getMemberDiamondsizeOfGold() + integerS);

					//加提醒
					// 发起者
					SystemMessage systemMessage = new SystemMessage();
					systemMessage.setTitle("送出礼物退回");
					systemMessage.setAddTime(new Date());
					systemMessage.setEnableState(1);
					systemMessage.setDeleteState(2);
					systemMessage.setMemberId(giftconsumption.getSendMemberId());
					systemMessage.setContext("您送给\""+memberAssetsRec.getNickName() +"\" 的\""+gift.getName()+"\"到期自动退回");
					systemMessageMapper.insert(systemMessage);

					//加回退的记录
					RechargeRecord rechargeRecord = new RechargeRecord();
					rechargeRecord.setMemberId(giftconsumption.getSendMemberId());
					rechargeRecord.setName("送出礼物退回");
					rechargeRecord.setCurrency(3);
					rechargeRecord.setMode(4);
					rechargeRecord.setType(1);
					rechargeRecord.setRunSize(Long.parseLong(integerS.toString()));
					rechargeRecord.setSurplus((memberAssets.getMemberDiamondsizeOfGold() + integerS));
					rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
					rechargeRecordMapper.insert(rechargeRecord);

					//该礼物是否被接收方接收 1： 是 2 ：否
					giftconsumption.setDrawInProportion(1);

					//将修改的用户的账户信息放入数组等待更新
					listm = memberAssets;
				}
			} catch (ParseException e) {
				logger.info(e.getMessage());
				e.printStackTrace();
			}
		}
		memberAssetsMapper.updateById(listm);
		iGiftconsumptionService.updateBatchById(list);
	}

    @Test
    void hai(){
	  //状态 1  待报名 2 待对方确认3待自己确认4审核中5审核通过6审核未通过7已取消 查询所有的待报名状态的聚会 0没有过期;1有过期
	  //查询所有的待报名状态的 聚会信息
	  List<SendOfflineActivities> sendOfflineActivitiesList = iSendOfflineActivitiesService.selectList(new EntityWrapper<SendOfflineActivities>()
			.eq("state",1)
			.eq("is_overdueout",0));
	  //获取聚会信息数组id集合
	  Integer[] integers = sendOfflineActivitiesList.stream().map(SendOfflineActivities::getId).toArray(Integer[]::new);

	  List<AcceptOfflineActivities> acceptOfflineActivities = acceptOfflineActivitiesMapper.selectList(new EntityWrapper<AcceptOfflineActivities>()
			.notIn("id",0));
	  //获取参加聚会用户信息数组id集合
	  Integer[] integers1 = acceptOfflineActivities.stream().map(AcceptOfflineActivities::getSendOfflineActivities).toArray(Integer[]::new);

	  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  String str = dateFormat.format(new Date());

	  for(SendOfflineActivities sendOfflineActivities : sendOfflineActivitiesList){
		try
		{
		    //如果开始时间 减去三十分钟 小于等于 当前时间 开始判断又没人参加这个聚会
		    if((Long.parseLong(String.valueOf((sendOfflineActivities.getStartTime().getTime() / 1000)))  - (60*30)) <= Long.parseLong(dateToStamp(str)) /1000 ){
			  //如果当前的俱乐部不包含用户 解散 发消息
				if(!sendOfflineActivities.getId().toString().contains(integers1.toString()) ){
				    SendOfflineActivities sendOfflineActivitiesasd = iSendOfflineActivitiesService.selectById(sendOfflineActivities.getId());

				    //取消聚会
				    iSendOfflineActivitiesService.closeParty(sendOfflineActivitiesasd);
				}
		    }
		}
		catch (ParseException e)
		{
		    logger.debug(e.getMessage());
		}
	  }
    }

    @Test
    void clyub(){
	  // List<Club> listClub = clubService.selectList(new EntityWrapper<Club>().notIn("club_id",0));
	  // List lisut =  listClub.stream().collect(Collectors.toList());
	  // System.out.println(lisut);
	  Integer memberId = 1367;
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
		// bigDecimal = bigDecimal.multiply(proportionofwithdrawal.getProportion());
		// bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);//保留两位小数
		// System.out.println(bigDecimal);
	  }
	  BigDecimal bigDecimal1 = new BigDecimal("72");
	  System.out.println((bigDecimal1.divide(bigDecimal, 2, BigDecimal.ROUND_HALF_UP)).divide(new BigDecimal("0.7"),2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("10")));
	  System.out.println(bigDecimal);
    }
    @Test
    void test(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = "2021-01-30 18:44:00";
	  String str1 = "2021-01-29 18:44:00";
	  System.out.println(str.contains(str1));
	  System.out.println(str1.substring(0,16));
	  System.out.println(str.equals(str1.substring(0,16)));
	  try
	  {
		System.out.println(isToday(sdf.parse(str)));
	  }
	  catch (ParseException e)
	  {
		e.printStackTrace();
	  }

    }

    public  boolean isToday(Date inputJudgeDate) {
	  boolean flag = false;
	  //获取当前系统时间
	  long longDate = System.currentTimeMillis();
	  Date nowDate = new Date(longDate);
	  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  String format = dateFormat.format(nowDate);
	  String subDate = format.substring(0, 10);
	  //定义每天的24h时间范围
	  String beginTime = subDate + " 00:00:00";
	  String endTime = subDate + " 23:59:59";
	  Date paseBeginTime = null;
	  Date paseEndTime = null;
	  try {
		paseBeginTime = dateFormat.parse(beginTime);
		paseEndTime = dateFormat.parse(endTime);

	  } catch (ParseException e) {
		System.out.println(e.getMessage());
	  }
	  if(inputJudgeDate.after(paseBeginTime) && inputJudgeDate.before(paseEndTime)) {
		flag = true;
	  }
	  return flag;
    }
    @Test
    void contextLoads()
    {
	  List<RedLog> redLogs = iRedLogService.selectList(new EntityWrapper<RedLog>().eq("status",
			"0").le("red_log_end_time", new Date()).ne("red_log_number_remaining", 0));
	  List<Integer> collect = redLogs.stream().map(RedLog::getRedLogMemberId).collect(Collectors.toList());
	  logger.debug("退还红包...会员ID");
	  logger.info("dfasdfasdfasdf");
	  List<MemberAssets> memberAssets = iMemberAssetsService.selectList(new EntityWrapper<MemberAssets>().in("memberId",collect));
	  for (RedLog redLog : redLogs)
	  {
		for (MemberAssets memberAsset : memberAssets)
		{
		    if (memberAsset.getMemberId().equals(redLog.getRedLogMemberId()))
		    {
			  redLog.setStatus("1");

			  logger.debug("退还红包...会员ID:" + memberAsset.getMemberId()+" 原钻石量:"
					+memberAsset.getMemberDiamondsizeOfGold()
					+"退还钻石:"
					+redLog.getRedLogGoldSize());
			  logger.debug("剩余:"+(memberAsset.getMemberDiamondsizeOfGold() + redLog.getRedLogGoldSize()));
			  memberAsset.setMemberDiamondsizeOfGold(memberAsset.getMemberDiamondsizeOfGold() + redLog.getRedLogGoldSize());
		    }
		}
	  }

    }
}
