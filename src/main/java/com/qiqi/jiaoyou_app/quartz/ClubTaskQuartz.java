package com.qiqi.jiaoyou_app.quartz;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qiqi.jiaoyou_app.mapper.TaskAnswerQuestionsMapper;
import com.qiqi.jiaoyou_app.pojo.*;
import com.qiqi.jiaoyou_app.service.*;
import com.qiqi.jiaoyou_app.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class ClubTaskQuartz
{

    @Autowired
    private IMemberAssetsService iMemberAssetsService;
    @Autowired
    private ClubService clubService;
    @Autowired
    private ClubTaskService clubTaskService;
    @Autowired
    private ClubBuddyService clubBuddyService;
    @Autowired
    private IMemberService iMemberService;
    @Autowired
    private IRechargeRecordService iRechargeRecordService;
    @Autowired
    private TaskAnswerQuestionsMapper taskAnswerQuestionsMapper;
    private final static Logger logger = LoggerFactory.getLogger(ClubTaskQuartz.class);

    /**
     * @Description: get 任务结束修改
     * @return: void
     * @Author: cfx
     * @Date: 2020-12-29 15:19
     */
    // @Scheduled(cron = "*/1 * * * * ?")
    @Scheduled(cron = "0 0 */2 * * ?")
    public void get()
    {
	  List<ClubTask> clubTasks = clubTaskService.selectList(new EntityWrapper<ClubTask>().eq("past_status", "0"));
	  if (clubTasks.size() > 0)
	  {
		// List<Club> clubList = clubService.selectList(null);
		for (ClubTask clubTask : clubTasks)
		{
		    Calendar calendar = Calendar.getInstance();
		    calendar.setTime(clubTask.getCreateTime());
		    calendar.add(Calendar.DATE, 1);
		    long time = calendar.getTime().getTime();
		    long time1 = new Date().getTime();
		    System.out.println(calendar.getTime() + "===" + new Date());
		    System.out.println(time + "===" + time1);
		    if (time1 > time)
		    {
			  clubTask.setPastStatus("1");
		    }
		}
		clubTaskService.updateBatchById(clubTasks);
	  }
    }

    /**
     * @Description: clubQuartzDay 俱乐部每天
     * @return: void
     * @Author: cfx
     * @Date: 2020-11-23 15:38
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void clubQuartzDay()
    {
	  try
	  {
		List<Club> clubList = clubService.selectList(null);

		if (clubList != null && clubList.size() > 0)
		{
		    for (Club club : clubList)
		    {
			  club.setTodayWage("0");
		    }
		    clubService.updateBatchById(clubList);
		    System.out.println("俱乐部每天更改数据");
		}
	  }
	  catch (Exception e)
	  {
		e.printStackTrace();
	  }
    }

    /**
     * @Description: clubQuartzMonth 俱乐部发放工资 狗屎的...........................
     * @return: void
     * @Author: cfx
     * 每个一分钟执行一次 写代码?写什么代码 来一起玩游戏pa...........................
     * @Date: 2020-11-23 15:38
     */
    // @Scheduled(cron = "*/1 * * * * ?")
    // @Scheduled(cron = "0 0 */1 * * ?")
    //测试使用/**/
    @Scheduled(cron = "0 */1 * * * ?")
    public void getQuestionsTime() throws ParseException {
        //========================================================================
	  //俱乐部到点了发工资分为几种情况
	  //1 我是1
	  //2 我是2
	  //3 我是3
	  //4 我是4
	  //========================================================================
	  //获取俱乐部任务列表
	  List<ClubTask> clubTasks = clubTaskService.selectList(new EntityWrapper<ClubTask>().like("create_time", DateUtils.getDate("yyyy-MM-dd")));
	  //获取俱乐部任务列表的所有的任务id,放到数组里面
	  Integer[] clubIds = clubTasks.stream().map(ClubTask::getClubId).toArray(Integer[]::new);
	  //利用俱乐部任务的id列表,查询出没有俱乐部任务 并且发工资时间在当前时间或者是当前时间之前的俱乐部信息
	  List<Club> clubList = clubService.getQuestionsTime(clubIds);
	  System.out.println("俱乐部发工资任务***** " + clubList.size());
	  //查询俱乐部的成员信息 并且成员不在俱乐部任务的俱乐部里面
	  List<ClubBuddy> clubBuddies = clubBuddyService.selectList(new EntityWrapper<ClubBuddy>().notIn("club_id", clubIds).eq("secretary_status", "0"));
	  //查询所有的会员的资产信息
	  List<MemberAssets> memberAssets = iMemberAssetsService.selectList(null);
	  //查询所有会员的信息
	  List<Member> members = iMemberService.selectList(null);
	  List<RechargeRecord> rechargeRecords = new ArrayList<>();
	  //循环所有的俱乐部 并且是没有任务 没有发工资的
	  System.out.println("-------------------------------------------------------");
	  logger.info(clubList.toString());
	  for (Club club : clubList)
	  {
		//循环所有的会员资产
		for (MemberAssets memberAsset : memberAssets)
		{
		    // 群主发工资 如果会员和俱乐部的会员对上眼
		    if (memberAsset.getMemberId().equals(club.getMemberId()))
		    {
			  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			  //发放工资的时候,判断是不是今天新增的用户,是的话新增用户查看是不是到了发放工资的时间
			  //到了发放时间的话走这个逻辑,不是的话直接略过

			  //判断发工资的人是不是有钱发工资
			  if (memberAsset.getMemberDiamondsizeOfGold() > club.getWage() * club.getSum())
			  {
				SimpleDateFormat sdfa = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String strDate = "";
				//2021:14:25 13:20:00 13:1 13:12
				String str = sdfa.format(new Date());
				if(club.getWageTime().length() == 4){
				    strDate = sdfa.format(new Date()).substring(0,11) + club.getWageTime().substring(0,3)+"0"+club.getWageTime().substring(3)+":00";
				    System.out.println("开始日期4"+strDate);
				}
				if(club.getWageTime().length() == 5){
				    strDate = sdfa.format(new Date()).substring(0,11) + club.getWageTime()+":00";
				    System.out.println("开始日期5"+strDate);
				}
				//首先判断俱乐部是不是今天新增的用户 不是得话直接发工资 是的话走这个逻辑
				if(isToday(club.getCreateTime())) {
				    System.out.println("我是今天新增的俱乐部 "+club.getClubName()+" "+str);
				    //若发工资的标识为空,那就设置为还没有发工资的状态
				    if(club.getTodayWage() == null){
					  club.setTodayWage("0");
				    }
				    System.out.println(str.substring(0,16).equals(strDate.substring(0,16)) && club.getTodayWage().equals("0"));
				    //是今天新增的俱乐部 并且发工资时间,刚好是在点上(必须是刚好的发工资时间vvvv) 发工资
				    if(str.substring(0,16).equals(strDate.substring(0,16)) && club.getTodayWage().equals("0")){
				    // if(sdfa.parse(str).after(sdfa.parse(strDate)) && club.getTodayWage()=="0" && club.getCreateTime().before(sdfa.parse(strDate))){
					  System.out.println("是今天新增的俱乐部 并且是在发工资之前 到了(已过时间点)发工资的时间，所以就发工资");
					  // 发放的总工资
					  club.setTotalWages(club.getTotalWages() + club.getWage() * club.getSum());
					  club.setTodayWage("1");
					  // 会员资产
					  memberAsset.setMemberDiamondsizeOfGold(memberAsset.getMemberDiamondsizeOfGold() - club.getWage() * club.getSum());
					  System.out.println(club.getClubName() + "俱乐部任务发放工资记录meide");
					  // 发放记录
					  RechargeRecord rechargeRecord = new RechargeRecord();
					  rechargeRecord.setMemberId(memberAsset.getMemberId());
					  rechargeRecord.setName(club.getClubName() + "俱乐部发放工资");
					  rechargeRecord.setCurrency(3);
					  rechargeRecord.setMode(4);
					  rechargeRecord.setType(2);
					  rechargeRecord.setRunSize(club.getWage() * club.getSum());
					  rechargeRecord.setSurplus(memberAsset.getMemberDiamondsizeOfGold());
					  rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
					  rechargeRecords.add(rechargeRecord);
				    }
				}
				//不是今天新增的俱乐部 直接走的这个逻辑
				else{
				    System.out.println("不是今天新增的俱乐部 那就直接发了 "+club.getClubName());
				    // 发放的总工资
				    club.setTotalWages(club.getTotalWages() + club.getWage() * club.getSum());
				    //今日是否发了工资 0没有发;1发了
				    club.setTodayWage("1");
				    // 会员资产 (减去会员的需要发的俱乐部的工资)
				    memberAsset.setMemberDiamondsizeOfGold(memberAsset.getMemberDiamondsizeOfGold() - club.getWage() * club.getSum());
				    System.out.println(club.getClubName() + "俱乐部任务发放工资记录meide");
				    // 发放记录
				    RechargeRecord rechargeRecord = new RechargeRecord();
				    rechargeRecord.setMemberId(memberAsset.getMemberId());
				    rechargeRecord.setName(club.getClubName() + "俱乐部发放工资");
				    rechargeRecord.setCurrency(3);
				    rechargeRecord.setMode(4);
				    rechargeRecord.setType(2);
				    rechargeRecord.setRunSize(club.getWage() * club.getSum());
				    rechargeRecord.setSurplus(memberAsset.getMemberDiamondsizeOfGold());
				    rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
				    rechargeRecords.add(rechargeRecord);
				}
			  }
		    }
		}
		//所有的有任务的俱乐部成员信息
		for (ClubBuddy clubBuddy : clubBuddies)
		{
		    System.out.println("群成员收工资开始 "+club.getClubName()+"" +new Date());
		    // 群成员收工资
		    if (club.getClubId().equals(clubBuddy.getClubId()))
		    {
			  System.out.println("群成员收工资 "+clubBuddy.getNickName()+"" +new Date());
			  for (MemberAssets asset : memberAssets)
			  {
				if (clubBuddy.getMemberId().equals(asset.getMemberId()))
				{
				    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				    String strDate = "";
				    String str = sdf.format(new Date());
				    // 2021:14:25 13:20:00 13:1 13:12
				    if(club.getWageTime().length() == 4){
					  strDate = sdf.format(new Date()).substring(0,11) + club.getWageTime()+"0:00";
					  System.out.println("开始日期4"+strDate);
				    }
				    if(club.getWageTime().length() == 5){
					  strDate = sdf.format(new Date()).substring(0,11) + club.getWageTime()+":00";
					  System.out.println("开始日期5"+strDate);
				    }
				    //首先判断用户是不是今天新增的用户 并且俱乐部还没有发工资 今日是否发了工资 0没有发;1发了 不是得话直接发工资 是的话走这个逻辑
				    if(isToday(clubBuddy.getCreateTime()) && club.getTodayWage().equals("0") ){
					    System.out.println("我是今天新增的用户");
					    // if(clubBuddy.getCreateTime() != null && clubBuddy.getCreateTime().before(sdf.parse(strDate))){
					        //我是今天新增的用户,并且到了发工资的时间
						  if(clubBuddy.getCreateTime() != null && clubBuddy.getCreateTime().toString().substring(0,16).equals(strDate.substring(0,16))){
						  System.out.println("是今天新增的用户,并且到了发工资的时间，那就发个狗屎的工资吧");

						  //如果这个用户加入的俱乐部有任务的话,判断下完成了没有,没有完成得话是没有工资的
						  // TaskAnswerQuestions taskAnswerQuestions = new TaskAnswerQuestions();

							//获取当前俱乐部的任务
							// List<ClubTask> clubTask = clubTaskService.selectList(new EntityWrapper<ClubTask>()
							// .eq("club_id",club.getClubId()));
							//如果当前俱乐部有任务,走这个逻辑,没得话直接发工资
							// if(clubTask.size() > 0){
							    //如是有任务的话,判断这个用户完成了没有,完成的话发工资,没有完成的话不发工资
							    // List<TaskAnswerQuestions> taskAnswerQuestions = taskAnswerQuestionsMapper.selectList(new EntityWrapper<TaskAnswerQuestions>()
								// 	  .eq("task_id",clubTask.get(0).getId())
								// 	  .eq("member_id",clubBuddy.getOneselfId())
							    //           .gt("task_rating",0));
							    //如果任务完成有数据,那就发工资(不管..因为地垃圾审核会直接发工资)
							    // if(taskAnswerQuestions.size() > 0){
								//   //给用户的余额加上俱乐部工资
								//   asset.setMemberDiamondsizeOfGold(asset.getMemberDiamondsizeOfGold() + club.getWage());
								//   //收取记录
								//   RechargeRecord rechargeRecord = new RechargeRecord();
								//   rechargeRecord.setMemberId(asset.getMemberId());
								//   rechargeRecord.setName("收到" + club.getClubName() + "俱乐部工资");
								//   rechargeRecord.setCurrency(3);
								//   rechargeRecord.setMode(4);
								//   rechargeRecord.setType(1);
								//   rechargeRecord.setRunSize(club.getWage());
								//   rechargeRecord.setSurplus(asset.getMemberDiamondsizeOfGold());
								//   rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
								//   rechargeRecords.add(rechargeRecord);
								//   // 成员的总工资 月周日
								//   clubBuddy.setDiamondTime(new Date());
								//   clubBuddy.setDiamondNumber(clubBuddy.getDiamondNumber() + club.getWage());
								//   clubBuddy.setDiamondDayNumber(club.getWage());
								//   clubBuddy.setDiamondMothNumber(clubBuddy.getDiamondMothNumber() + club.getWage());
								//   clubBuddy.setDiamondWeekNumber(clubBuddy.getDiamondWeekNumber() + club.getWage());
							    // }

							// }else{
							    //给用户的余额加上俱乐部工资
							    asset.setMemberDiamondsizeOfGold(asset.getMemberDiamondsizeOfGold() + club.getWage());
							    //收取记录
							    RechargeRecord rechargeRecord = new RechargeRecord();
							    rechargeRecord.setMemberId(asset.getMemberId());
							    rechargeRecord.setName("收到" + club.getClubName() + "俱乐部工资");
							    rechargeRecord.setCurrency(3);
							    rechargeRecord.setMode(4);
							    rechargeRecord.setType(1);
							    rechargeRecord.setRunSize(club.getWage());
							    rechargeRecord.setSurplus(asset.getMemberDiamondsizeOfGold());
							    rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
							    rechargeRecords.add(rechargeRecord);
							    // 成员的总工资 月周日
							    clubBuddy.setDiamondTime(new Date());
							    clubBuddy.setDiamondNumber(clubBuddy.getDiamondNumber() + club.getWage());
							    clubBuddy.setDiamondDayNumber(club.getWage());
							    clubBuddy.setDiamondMothNumber(clubBuddy.getDiamondMothNumber() + club.getWage());
							    clubBuddy.setDiamondWeekNumber(clubBuddy.getDiamondWeekNumber() + club.getWage());
							// }
					    }else{
						  System.out.println("是今天新增的用户,但是已经过了发工资的时间,所以是没有工资的");
						  }
				    }else{
					    System.out.println("不是今天新增的用户,大哥哥开始给群成员发工资了");
					    //若是刚好到时间,或者是在时间之后,就发工资给这个人
					    System.out.println("群成员收工资");
					  //获取当前俱乐部的任务
					  // List<ClubTask> clubTask = clubTaskService.selectList(new EntityWrapper<ClubTask>()
						// 	.eq("club_id",club.getClubId()));
					  //如果当前俱乐部有任务,走这个逻辑,没得话直接发工资
					  // if(clubTask.size() > 0){
						//如是有任务的话,判断这个用户完成了没有,完成的话发工资,没有完成的话不发工资
						// List<TaskAnswerQuestions> taskAnswerQuestions = taskAnswerQuestionsMapper.selectList(new EntityWrapper<TaskAnswerQuestions>()
						// 	    .eq("task_id",clubTask.get(0).getId())
						// 	    .eq("member_id",clubBuddy.getOneselfId())
						// 	    .gt("task_rating",0));
						//如果任务完成有数据,那就发工资
						// if(taskAnswerQuestions.size() > 0){
						    //给用户的余额加上俱乐部工资
						    // asset.setMemberDiamondsizeOfGold(asset.getMemberDiamondsizeOfGold() + club.getWage());
						    //收取记录
						    // RechargeRecord rechargeRecord = new RechargeRecord();
						    // rechargeRecord.setMemberId(asset.getMemberId());
						    // rechargeRecord.setName("收到" + club.getClubName() + "俱乐部工资");
						    // rechargeRecord.setCurrency(3);
						    // rechargeRecord.setMode(4);
						    // rechargeRecord.setType(1);
						    // rechargeRecord.setRunSize(club.getWage());
						    // rechargeRecord.setSurplus(asset.getMemberDiamondsizeOfGold());
						    // rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
						    // rechargeRecords.add(rechargeRecord);
						    // 成员的总工资 月周日
						    // clubBuddy.setDiamondTime(new Date());
						    // clubBuddy.setDiamondNumber(clubBuddy.getDiamondNumber() + club.getWage());
						    // clubBuddy.setDiamondDayNumber(club.getWage());
						    // clubBuddy.setDiamondMothNumber(clubBuddy.getDiamondMothNumber() + club.getWage());
						    // clubBuddy.setDiamondWeekNumber(clubBuddy.getDiamondWeekNumber() + club.getWage());
						// }

					  // }else{
						//给用户的余额加上俱乐部工资
						asset.setMemberDiamondsizeOfGold(asset.getMemberDiamondsizeOfGold() + club.getWage());
						//收取记录
						RechargeRecord rechargeRecord = new RechargeRecord();
						rechargeRecord.setMemberId(asset.getMemberId());
						rechargeRecord.setName("收到" + club.getClubName() + "俱乐部工资");
						rechargeRecord.setCurrency(3);
						rechargeRecord.setMode(4);
						rechargeRecord.setType(1);
						rechargeRecord.setRunSize(club.getWage());
						rechargeRecord.setSurplus(asset.getMemberDiamondsizeOfGold());
						rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
						rechargeRecords.add(rechargeRecord);
						// 成员的总工资 月周日
						clubBuddy.setDiamondTime(new Date());
						clubBuddy.setDiamondNumber(clubBuddy.getDiamondNumber() + club.getWage());
						clubBuddy.setDiamondDayNumber(club.getWage());
						clubBuddy.setDiamondMothNumber(clubBuddy.getDiamondMothNumber() + club.getWage());
						clubBuddy.setDiamondWeekNumber(clubBuddy.getDiamondWeekNumber() + club.getWage());
					  // }
				    }
				}
			  }
		    }
		}
	  }
	  if (clubList.size() > 0)
	  {
		boolean b = clubService.updateBatchById(clubList);
		if (b)
		{
		    System.out.println("俱乐部发放工资成功" + b);
		    if (rechargeRecords.size() > 0)
		    {
			  boolean b2 = iRechargeRecordService.insertBatch(rechargeRecords);
			  System.out.println("工资记录成功" + b2);
		    }
		    if (memberAssets.size() > 0)
		    {
			  boolean b3 = iMemberAssetsService.updateBatchById(memberAssets);
			  System.out.println("资产更改成功" + b3);
		    }
		    if (clubBuddies.size() > 0)
		    {
			  boolean b4 = clubBuddyService.updateBatchById(clubBuddies);
			  System.out.println("群成员收到工资" + b4);
		    }
		}
	  }
    }
    //author:nan
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
}