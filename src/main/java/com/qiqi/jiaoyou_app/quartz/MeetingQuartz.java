package com.qiqi.jiaoyou_app.quartz;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qiqi.jiaoyou_app.mapper.*;
import com.qiqi.jiaoyou_app.pojo.*;
import com.qiqi.jiaoyou_app.service.*;
import com.qiqi.jiaoyou_app.serviceImpl.MemberServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MeetingQuartz
{

    @Autowired
    private IMemberAssetsService iMemberAssetsService;
    @Autowired
    private ISendOfflineActivitiesService iSendOfflineActivitiesService;
    @Autowired
    private IRechargeRecordService iRechargeRecordService;
    @Autowired
    private AcceptOfflineActivitiesMapper acceptOfflineActivitiesMapper;
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

    private final static Logger logger = LoggerFactory.getLogger(MeetingQuartz.class);

	/**
	 * 朋友之间发送礼物的回退操作
	 * @Author: nan
	 * @Date: 2021-02-01 16:24
	 */
	// 每天十一点执行一次
	// @Scheduled(cron = "0 0 23 * * ?")
	//测试使用每分钟
//	@Scheduled(cron = "0 */1 * * * ?")
//	public void backGiftForFriend(){
//		System.out.println("----------------------------------------------------------------------------------");
//		//该礼物是否被接收方接收 1： 是 2 ：否
//		//查询所有没有被接收的礼物信息
//		List<Giftconsumption> list = giftconsumptionMapper.selectList(new EntityWrapper<Giftconsumption>()
//		.eq("drawInProportion",2));
//		logger.info(list.toString());
//
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String str = sdf.format(new Date());
//		MemberAssets listm = new MemberAssets();
//
//
//		if(list.size() > 0) {
//			for(Giftconsumption giftconsumption : list) {
//				System.out.println("====================================comein");
//				//若是添加时间 加上24小时 小于等于当前时间的话 就走回退的逻辑
//				try {
////				if((Long.parseLong(dateToStamp(giftconsumption.getAddTime().toString()) ) / 1000) +  86400 <=   (Long.parseLong(dateToStamp(str) ) / 1000) ){
//					if ((giftconsumption.getAddTime().getTime() / 1000) + 360 <= (Long.parseLong(dateToStamp(str)) / 1000)) {
//						//退钱
//						//获取会员信息
//						System.out.println("====================================backMoney");
//						List<MemberAssets> memberAssetsAS = memberAssetsMapper.selectList(new EntityWrapper<MemberAssets>()
//								.eq("memberId", giftconsumption.getSendMemberId()));
//						//h获取接收人的昵称信息
//						List<Member> memberAssetsRec = memberMapper.selectList(new EntityWrapper<Member>()
//								.eq("id", giftconsumption.getMemberId()));
//						//获取礼物信息
//						Gift gift = giftMapper.selectById(giftconsumption.getGiftId());
//						//计算送出的金钻数量返回给用户
//						Integer integerS = (gift.getPrice() * giftconsumption.getGiftSize());
//						//显示的余额信息
//						Long Remain = (memberAssetsAS.get(0).getMemberDiamondsizeOfGold() + (gift.getPrice() * giftconsumption.getGiftSize()));
//
////						System.out.println("====================================================");
////						logger.info(memberAssetsAS.toString());
////						logger.info(memberAssetsRec.toString());
////						logger.info(giftconsumption.toString());
////						System.out.println(integerS + "===================");
////						logger.info(gift.toString());
////						System.out.println("====================================================");
//						//设置用户的金钻金额
//						memberAssetsAS.get(0).setMemberDiamondsizeOfGold(Remain);
//						//将修改的用户的账户信息放入数组等待更新
//						memberAssetsMapper.updateById(listm);
//
//
//						//加提醒
//						// 发起者
//						SystemMessage systemMessage = new SystemMessage();
//						systemMessage.setTitle("送出礼物退回");
//						systemMessage.setAddTime(new Date());
//						systemMessage.setEnableState(1);
//						systemMessage.setDeleteState(2);
//						systemMessage.setMemberId(giftconsumption.getSendMemberId());
//						systemMessage.setContext("您送给\"" + memberAssetsRec.get(0).getNickName() + "\" 的\"" + gift.getName() + "\"到期自动退回");
//						systemMessageMapper.insert(systemMessage);
//						//加回退的记录
//						RechargeRecord rechargeRecord = new RechargeRecord();
//						rechargeRecord.setMemberId(giftconsumption.getSendMemberId());
//						rechargeRecord.setName("送出礼物退回");
//						rechargeRecord.setCurrency(3);
//						rechargeRecord.setMode(4);
//						rechargeRecord.setType(1);
//						rechargeRecord.setRunSize(Long.parseLong(integerS.toString()));
//						rechargeRecord.setSurplus(Remain);
//						rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
//						rechargeRecordMapper.insert(rechargeRecord);
//						//该礼物是否被接收方接收 1： 是 2 ：否
//						giftconsumption.setDrawInProportion(1);
//					}
//				} catch (ParseException e) {
//					logger.info(e.getMessage());
//					e.printStackTrace();
//				}
//			}
//		}
////		if(listm.size() > 0){
////			memberAssetsMapper.updateBatchById(listm);
////		}
//		if(list.size() > 0){
//			iGiftconsumptionService.updateBatchById(list);
//		}
//
//	}


	/***
     * @Description: 在聚会开始前的半个小时检测这个聚会是不是没有人参加,没有的话取消聚会,回退金额,加上提醒和记录提醒
     * @param: null
     * @return:
     * @Author: nan
     * @Date: 2021-01-31 23:02
     */
    // @Scheduled(cron = "0 */30 * * * ?")
    //测试使用
    @Scheduled(cron = "0 */1 * * * ?")
    public void startThree(){

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



    /***
     * @Description: 在聚会开始前的24小时检测这个聚会是不是没有人参加,没有的话取消聚会,回退金额,加上提醒和记录提醒
     * @return: void
     * @Author: nan
     * @Date: 2021-01-31 23:11
     */

    // 每天十一点执行一次
    // @Scheduled(cron = "0 0 23 * * ?")
    //测试使用
    // @Scheduled(cron = "0 */1 * * * ?")
    public void startTwoFour(){
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


    /**
     * @Description: get 任务结束修改 过去聚会退回 update from nan
     * @return: void
     * @Author: cfx
     * @Date: 2020-12-29 15:19
     */
    // @Scheduled(cron = "*/1 * * * * ?")
    @Scheduled(cron = "0 */1 * * * ?")
    // @Scheduled(cron = "0 0 */1 * * ?")
    public void scheduled()
    {
	  //查询条件 7已取消(已过期) 0没有过期;1有过期 已经开始(过期)的时候  是一个人的时候 状态时7 并且没有过期 开始时间小于等于当前时间
	  List<SendOfflineActivities> sendOfflineActivities = iSendOfflineActivitiesService.selectList(new EntityWrapper<SendOfflineActivities>()
			.eq("state", 7)
			.eq("is_overdueout", "0")
			.le("startTime", new Date()));
	  // .eq("perSize", 1));
	  System.out.println("过期聚会退回....." + sendOfflineActivities.size());
	  //查询的是还么有
	  if (sendOfflineActivities != null && sendOfflineActivities.size() > 0)
	  {
		List<Integer> collect = sendOfflineActivities.stream().map(SendOfflineActivities::getSendMemberId).collect(Collectors.toList());
		List<MemberAssets> memberAssets = iMemberAssetsService.selectList(new EntityWrapper<MemberAssets>().in("memberId", collect));
		List<RechargeRecord> rechargeRecords = new ArrayList<>();
		for (SendOfflineActivities sendOfflineActivity : sendOfflineActivities)
		{
		    for (MemberAssets memberAsset : memberAssets)
		    {
		        //必须是没有过期的 0没有过期;1有过期 才能到里面来
			  if (sendOfflineActivity.getSendMemberId().equals(memberAsset.getMemberId()) && sendOfflineActivity.getIsOverdueout() == "0")
			  {
				sendOfflineActivity.setIsOverdueout("1");
				//状态 1  待报名 2 待对方确认3待自己确认4审核中5审核通过6审核未通过7已取消 聚会过期之后  也要显示审核通过
				sendOfflineActivity.setState(5);
				RechargeRecord rechargeRecord = new RechargeRecord();
				rechargeRecord.setMemberId(sendOfflineActivity.getSendMemberId());
				rechargeRecord.setName("过期聚会退回");
				rechargeRecord.setCurrency(3);
				rechargeRecord.setMode(4);
				rechargeRecord.setType(1);
				rechargeRecord.setRunSize(Long.valueOf(sendOfflineActivity.getAverageDiamondsSize() * sendOfflineActivity.getPerSize()));
				rechargeRecord.setSurplus(memberAsset.getMemberDiamondsizeOfGold());
				rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
				rechargeRecords.add(rechargeRecord);
			  }
		    }
		    System.out.println("过期聚会退回成功.....");
		    iSendOfflineActivitiesService.updateBatchById(sendOfflineActivities);
		    iRechargeRecordService.insertBatch(rechargeRecords);
		}
	  }
    }



    /*
    * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException
    {
	  String res;
	  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  Date date = simpleDateFormat.parse(s);
	  long ts = date.getTime();
	  res = String.valueOf(ts);
	  return res;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
	  String res;
	  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  long lt = new Long(s);
	  Date date = new Date(lt);
	  res = simpleDateFormat.format(date);
	  return res;
    }
}
