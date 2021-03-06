package com.qiqi.jiaoyou_app.quartz;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qiqi.jiaoyou_app.mapper.MemberAssetsMapper;
import com.qiqi.jiaoyou_app.pojo.Club;
import com.qiqi.jiaoyou_app.pojo.ClubBuddy;
import com.qiqi.jiaoyou_app.pojo.MemberAssets;
import com.qiqi.jiaoyou_app.pojo.Notice;
import com.qiqi.jiaoyou_app.service.*;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ClubQuartz
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
    private INoticeService iNoticeService;
    @Autowired
    private MemberAssetsMapper memberAssetsMapper;

    private final static Logger logger = LoggerFactory.getLogger(ClubQuartz.class);

    /***
     * @Description:
     * 每隔12小时(每天上午8点和下午8点),检测俱乐部主子的钱,是不是够发给成员三天的,不是的话给发提醒,
     * 若是不够一天发的,提醒一天,在第二题那给予解散(需要加上解散提醒)
     * @Author: nan
     * @Date: 2021-01-31 11:54
     */

    //测试使用
    @Scheduled(cron = "0 */1 * * * ?")
    // @Scheduled(cron = "0 0 8,20 * * ?")
    public void clubMoney()
    {
        try{
            //查询所有俱乐部的信息
		List<Club> listClub = clubService.selectList(new EntityWrapper<Club>().notIn("club_id",0));

		//判断有哪些俱乐部的钱不够发工资给自己的成员 并记住群主的会员id

		//俱乐部消息
		Notice notice = new Notice();
		List<Notice> listNotice = new ArrayList<>();

		for(Club club : listClub){
		    //这家伙攒够次数了(6次),解散他的俱乐部,再给他加上提醒
		    if(club.getAlertNum() >= 6){
		        //超出时间还不充钱就解散俱乐部
			  ResultUtils resultUtils =  clubService.dissolveClub(club);
			  //俱乐部解散成功之后,发提醒
			  if(resultUtils.getStatus().equals(200)){
				//1俱乐部
				notice.setType(1);
				//需要接收提醒的用户的id
				notice.setOfMember(club.getMemberId());
				notice.setContext("用户您好，由于您目前账户实力已无法支付您所创建的俱乐部所需工资，俱乐部\"" + club.getClubName() + "\"已被解散");
				notice.setTitle("俱乐部强制解散");
				notice.setAddTime(new Date());
				listNotice.add(notice);
			  }
			  }


		    //计算群主钱够发几天工资
		    //获取俱乐部的会员数量信息
		    Integer clubbuddy = clubBuddyService.selectCount(new EntityWrapper<ClubBuddy>()
				  .eq("club_id",club.getClubId()));
		    //获取会员的资产信息
		    List<MemberAssets> memberAssets = memberAssetsMapper.selectList(new EntityWrapper<MemberAssets>()
				  .eq("memberId",club.getMemberId()));
		    //计算三天需要的金钻数量
		    Long totalGlod = club.getWage() * 3 *(clubbuddy - 1);
		    //计算一天需要的金额
		    Long totalOneGlod = club.getWage() *(clubbuddy - 1);

		    //如果是一人的话,不参与这个事件
		    if(!clubbuddy.equals(1)){
			  //如果不够发一天的话(积累次数积累超过两次,解散俱乐部)
			  if(memberAssets.get(0).getMemberDiamondsizeOfGold() <= totalOneGlod){
				//还没攒够两次不解散
				if(club.getAlertNum() <= 2){
				    //1俱乐部
				    notice.setType(1);
				    //需要接收提醒的用户的id
				    notice.setOfMember(club.getMemberId());
				    notice.setContext("用户您好，您目前账户实力即将无法支付您所创建的俱乐部一天所需工资，若无法支付，您的俱乐部将会被解散！请尽快充值。");
				    notice.setTitle("俱乐部解散提醒");
				    notice.setAddTime(new Date());
				    listNotice.add(notice);
				    //怒气值加1
				    club.setAlertNum(club.getAlertNum() + 1);
				}else{
				    //一天钱都发不起的穷逼,解散了解散了
				    ResultUtils resultUtils =  clubService.dissolveClub(club);
				    //俱乐部解散成功之后,发提醒
				    if(resultUtils.getStatus().equals(200)){
					  //1俱乐部
					  notice.setType(1);
					  //需要接收提醒的用户的id
					  notice.setOfMember(club.getMemberId());
					  notice.setContext("用户您好，由于您目前账户实力已无法支付您所创建的俱乐部所需工资，俱乐部\"" + club.getClubName() + "\"已被解散");
					  notice.setTitle("俱乐部强制解散");
					  notice.setAddTime(new Date());
					  listNotice.add(notice);
				    }
				} //不够三天 但是超过一天
			  }else if(memberAssets.get(0).getMemberDiamondsizeOfGold() <= totalGlod && memberAssets.get(0).getMemberDiamondsizeOfGold() > totalOneGlod){
				//如果金钻不够发三天的话,但是够发一天以上,提醒金钻不够发三天,怒气值加1
				//1俱乐部
				notice.setType(1);
				//需要接收提醒的用户的id
				notice.setOfMember(club.getMemberId());
				notice.setContext("用户您好，您目前账户实力即将无法支付您所创建的俱乐部所需工资，若无法支付，您的俱乐部将会被解散！请尽快充值。");
				notice.setTitle("俱乐部解散提醒");
				notice.setAddTime(new Date());
				listNotice.add(notice);
				//怒气值加1
				club.setAlertNum(club.getAlertNum() + 1);
			  }
		    }



		}

		//给用户增加解散俱乐部提醒 直接运行就好了 需要啥提示
		Boolean bl1 = iNoticeService.insertBatch(listNotice);
		Boolean bl2 = clubService.updateBatchById(listClub);
		System.out.println("提醒增加 " + bl1);
		System.out.println("俱乐部信息更新 " + bl2);
        }
        catch (Exception e){
            logger.debug(e.getMessage());
	  }
    }



    /**
     * @Description: clubQuartz 潜力俱乐部到期时间
     * @return: void
     * @Author: cfx
     * @Date: 2020-11-23 15:38
     */
    // @Scheduled(cron = "*/1 * * * * ?")
    @Scheduled(cron = "0 0 */2 * * ?")
    public void clubQuartz()
    {
	  try
	  {
		Calendar c = Calendar.getInstance();
		Date date = new Date();
		c.setTime(date);
		c.add(Calendar.MONTH, 1); //将当bai前du日期加zhi一个月dao

		List<Club> clubs = clubService.selectList(new EntityWrapper<Club>().eq("type", "0").ge("create_time", c.getTime()));
		System.out.println("潜力俱乐部到期时间" + clubs.size());
		if (clubs != null && clubs.size() > 0)
		{
		    for (Club club : clubs)
		    {
			  club.setType("1");
		    }
		    clubService.updateBatchById(clubs);
		    System.out.println("潜力俱乐部到期时间执行成功");
		}
	  }
	  catch (Exception e)
	  {
		e.printStackTrace();
	  }
    }

    /**
     * @Description: clubQuartzDay 俱乐部每天清空数据
     * @return: void
     * @Author: cfx
     * @Date: 2020-11-23 15:38
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void clubQuartzDay()
    {
	  try
	  {
		List<ClubBuddy> list = clubBuddyService.selectList(null);

		if (list != null && list.size() > 0)
		{
		    for (ClubBuddy clubBuddy : list)
		    {
			  clubBuddy.setDiamondDayNumber(0L);
		    }
		    boolean b = clubBuddyService.updateBatchById(list);
		    System.out.println("俱乐部每天清空数据");
		}
	  }
	  catch (Exception e)
	  {
		e.printStackTrace();
	  }
    }

    /**
     * @Description: clubQuartzWeek 俱乐部每周清空数据
     * @return: void
     * @Author: cfx
     * @Date: 2020-11-23 15:38
     */
    @Scheduled(cron = "0 0 0 ? * MON")
    public void clubQuartzWeek()
    {
	  try
	  {
		List<ClubBuddy> list = clubBuddyService.selectList(null);

		if (list != null && list.size() > 0)
		{
		    for (ClubBuddy clubBuddy : list)
		    {
			  clubBuddy.setDiamondWeekNumber(0L);
		    }
		    boolean b = clubBuddyService.updateBatchById(list);
		    System.out.println("俱乐部每周清空数据");
		}
	  }
	  catch (Exception e)
	  {
		e.printStackTrace();
	  }
    }

    /**
     * @Description: clubQuartzMonth 俱乐部每月清空数据
     * @return: void
     * @Author: cfx
     * @Date: 2020-11-23 15:38
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    public void clubQuartzMonth()
    {
	  try
	  {
		List<ClubBuddy> list = clubBuddyService.selectList(null);

		if (list != null && list.size() > 0)
		{
		    for (ClubBuddy clubBuddy : list)
		    {
			  clubBuddy.setDiamondMothNumber(0L);
		    }
		    boolean b = clubBuddyService.updateBatchById(list);
		    System.out.println("俱乐部每月清空数据");
		}
	  }
	  catch (Exception e)
	  {
		e.printStackTrace();
	  }
    }

    /**
     * @Description: clubQuartzMonth 俱乐部等级
     * @return: void
     * @Author: cfx
     * @Date: 2020-11-23 15:38
     */
    @Scheduled(cron = "0 0 0 * * ?")
    // @Scheduled(cron = "*/1 * * * * ?")
    public void clubQuartzRank()
    {
	  try
	  {
		List<Club> totalWages = clubService.selectList(null);
		if (totalWages != null && totalWages.size() > 0)
		{
		    Map<Integer, Long> clubRank = com.qiqi.jiaoyou_app.vo.clubRank.getClubRank();
		    for (int i = 0; i < 70; i++)
		    {
			  for (Club totalWage : totalWages)
			  {
				if (totalWage.getTotalWages() < clubRank.get(i + 1) && totalWage.getTotalWages() > clubRank.get(i))
				{
				    totalWage.setClubGrade(i);
				}
				if (totalWage.getClubGrade() > 6)
				{
				    totalWage.setSecretaryNumber(2);
				}
				if (totalWage.getClubGrade() > 11)
				{
				    totalWage.setSecretaryNumber(5);
				}
			  }
		    }
		    boolean b = clubService.updateBatchById(totalWages);
		    System.out.println("俱乐部等级" + b);
		}
	  }
	  catch (Exception e)
	  {
		e.printStackTrace();
	  }
    }

}
