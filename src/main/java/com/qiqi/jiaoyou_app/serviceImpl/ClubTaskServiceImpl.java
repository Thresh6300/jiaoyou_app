package com.qiqi.jiaoyou_app.serviceImpl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.jPush.utils.JpushUtil;
import com.qiqi.jiaoyou_app.mapper.ClubTaskMapper;
import com.qiqi.jiaoyou_app.mapper.GameBankMapper;
import com.qiqi.jiaoyou_app.mapper.GameSelectLabelMapper;
import com.qiqi.jiaoyou_app.mapper.TaskAnswerQuestionsMapper;
import com.qiqi.jiaoyou_app.pojo.*;
import com.qiqi.jiaoyou_app.service.*;
import com.qiqi.jiaoyou_app.util.Convert;
import com.qiqi.jiaoyou_app.util.DateUtils;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * (ClubTask)表服务实现类
 * clubTask
 *
 * @author cfx
 * @since 2020-11-28 10:43:00
 */
@Slf4j
@Service("clubTaskService")
public class ClubTaskServiceImpl extends ServiceImpl<ClubTaskMapper, ClubTask> implements ClubTaskService
{
    @Autowired
    private ClubTaskMapper clubTaskMapper;

    @Autowired
    private GameSelectLabelMapper gameSelectLabelMapper;

    @Autowired
    private GameBankMapper gameBankMapper;

    @Autowired
    private TaskAnswerQuestionsMapper taskAnswerQuestionsMapper;

    @Autowired
    private TaskAnswerQuestionsService taskAnswerQuestionsService;

    @Autowired
    private ClubBuddyService clubBuddyService;

    @Autowired
    private IMemberService iMemberService;

    @Autowired
    private ClubService clubService;

    @Autowired
    private WagesLogsService wagesLogsService;

    @Autowired
    private IMemberAssetsService iMemberAssetsService;

    @Autowired
    private INoticeService iNoticeService;

    @Autowired
    private IRechargeRecordService iRechargeRecordService;



    @Override
    public ResultUtils addTask(ClubTask clubTask)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  int count = selectCount(new EntityWrapper<ClubTask>().eq("club_id", clubTask.getClubId()).like("create_time", DateUtils.getDate("yyyy-MM-dd")));
	  if (count > 0)
	  {
		resultUtils.setStatus(500);
		resultUtils.setMessage("您今天已经发布过任务了");
		return resultUtils;
	  }
	  // Club club = clubService.selectById(clubTask.getClubId());
	  // Date timeMillis = DateUtils.getTimeMillis(club.getWageTime());
	  // if (timeMillis.before(new Date()))
	  // {
		// resultUtils.setStatus(500);
		// resultUtils.setMessage("您今天已经发布过任务了");
		// return resultUtils;
	  // }
	  int i = clubBuddyService.selectCount(new EntityWrapper<ClubBuddy>().eq("club_id", clubTask.getClubId()).eq("secretary_status", "0"));
	  clubTask.setTaskNumber(i);
	  clubTask.setTaskNotNumber(i);
	  clubTask.setCreateTime(new Date());
	  Integer insert = clubTaskMapper.insert(clubTask);

	  if (insert > 0)
	  {
		// 推送
		List<ClubBuddy> clubBuddies = clubBuddyService.selectList(new EntityWrapper<ClubBuddy>().eq("club_id", clubTask.getClubId()).eq("secretary_status", "0"));

		List<Integer> collect = clubBuddies.stream().map(ClubBuddy::getMemberId).collect(Collectors.toList());
		Member member = iMemberService.selectById(clubTask.getMemberId());
		List<Member> members = iMemberService.selectList(new EntityWrapper<Member>().in("id", collect));
		String[] strings = members.stream().map(Member::getPushId).filter(Objects::nonNull).toArray(String[]::new);
		JpushUtil.sendToRegistrationIds(member.getNickName() + "发布了任务", member.getNickName() + "发布了任务", member.getNickName() + "发布了任务", new HashMap<>(), strings);
		List<Notice> notices = new ArrayList<>();
		for (Integer string : collect)
		{
		    Notice notice = new Notice();
		    notice.setType(1);
		    notice.setOfMember(string);
		    notice.setHead(member.getHead());
		    notice.setNickname(member.getNickName());
		    notice.setTitle("俱乐部发布任务");
		    notice.setContext(member.getNickName() + "发布了任务");
		    notice.setAddTime(new Date());
		    notices.add(notice);
		}
		iNoticeService.insertBatch(notices);
		return resultUtils;
	  }

	  resultUtils.setStatus(500);
	  resultUtils.setMessage("操作失败");
	  return resultUtils;
    }

    @Override
    public ResultUtils taskList(ClubTask clubTask)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  resultUtils.setData(new ArrayList<ClubTask>());
	  EntityWrapper<ClubTask> wrapper = new EntityWrapper<>();
	  wrapper.setSqlSelect("id,create_time createTime,past_status pastStatus, gameLabelId, task_content taskContent, task_url taskUrl, task_number taskNumber, task_not_number taskNotNumber, task_has_number taskHasNumber, member_id memberId, club_id clubId, " + "(SELECT game_select_title from game_select_label  WHERE id=gameLabelId) gameTitle, " + "(SELECT game_select_title_icon from game_select_label WHERE id=gameLabelId) gameIcon, " + "(SELECT game_type from game_select_label WHERE id=gameLabelId) gameType");

	  wrapper.orderBy("id", false).eq("club_id", clubTask.getClubId()).
			eq(clubTask.getMemberId() != null, "member_id", clubTask.getMemberId());
	  Page page = new Page(clubTask.getPageNum(), clubTask.getPageSize());
	  page.setOptimizeCountSql(true);
	  page.setSearchCount(true);
	  List<ClubTask> clubTasks = clubTaskMapper.selectPage(page, wrapper);
	  if (clubTasks == null || clubTasks.size() == 0)
		return resultUtils;
	  List<Integer> collect = clubTasks.stream().map(ClubTask::getId).collect(Collectors.toList());
	  List<TaskAnswerQuestions> taskAnswerQuestions = taskAnswerQuestionsMapper.selectList(new EntityWrapper<TaskAnswerQuestions>().eq("member_id", clubTask.getUserId()).in("task_id", collect));
	  for (ClubTask task : clubTasks)
	  {
		for (TaskAnswerQuestions taskAnswerQuestion : taskAnswerQuestions)
		{
		    // taskRating 0待审核;1需修改;2通过;3完美
		    // isAnswer   0待审核;1需修改;2通过;3完美;4没有回答
		    if (taskAnswerQuestion.getTaskId().equals(task.getId()))
		    {
			  task.setIsAnswer(taskAnswerQuestion.getTaskRating());
			  taskAnswerQuestion.setIsRead("0");
		    }
		}
		task.setTaskTime(DateUtils.getWeek(task.getCreateTime()));
	  }
	  if (taskAnswerQuestions!=null&&taskAnswerQuestions.size()>0)
	  {
		taskAnswerQuestionsService.updateBatchById(taskAnswerQuestions);
	  }
	  resultUtils.setData(clubTasks);
	  resultUtils.setCount((int) page.getTotal());
	  resultUtils.setPages((int) page.getPages());
	  return resultUtils;
    }

    @Override
    public ResultUtils getTaskLabel()
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");

	  List<GameSelectLabel> gameSelectLabels = gameSelectLabelMapper.selectList(null);
	  if (gameSelectLabels != null && gameSelectLabels.size() > 0)
	  {
		resultUtils.setData(gameSelectLabels);
		return resultUtils;
	  }
	  resultUtils.setData(new GameSelectLabel());
	  return resultUtils;
    }

    @Override
    public ResultUtils getTaskGameBank()
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  List<GameBank> gameBanks = gameBankMapper.selectList(null);
	  if (gameBanks != null && gameBanks.size() > 0)
	  {
		resultUtils.setData(gameBanks);
		return resultUtils;
	  }
	  resultUtils.setData(new GameBank());
	  return resultUtils;
    }

    @Override
    public ResultUtils taskQuestions(TaskAnswerQuestions taskAnswerQuestions)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  Integer integer = taskAnswerQuestionsMapper.selectCount(getQuestionsWrapper(taskAnswerQuestions));
	  if (integer != 0)
	  {
		resultUtils.setStatus(500);
		resultUtils.setMessage("您已经完成过任务了,不能重复完成");
		return resultUtils;
	  }
	  Member member = iMemberService.selectById(taskAnswerQuestions.getMemberId());
	  taskAnswerQuestions.setNickName(member.getNickName());
	  taskAnswerQuestions.setTaskRating("0");
	  Integer insert = taskAnswerQuestionsMapper.insert(taskAnswerQuestions);
	  if (insert > 0)
	  {

		ClubTask clubTask = selectById(taskAnswerQuestions.getTaskId());
		Club club = clubService.selectById(clubTask.getClubId());
		Member member1 = iMemberService.selectById(club.getMemberId());
		Notice notice = new Notice();
		notice.setType(1);
		notice.setOfMember(member1.getId());
		notice.setContext(member.getNickName() + "回答了"+club.getClubName()+"俱乐部的任务");
		// notice.setTitle(member.getNickName() + "回答了"+club.getClubName()+"俱乐部的工资");
		notice.setTitle(club.getClubName()+"俱乐部任务");
		notice.setAddTime(new Date());
		iNoticeService.insert(notice);
		return resultUtils;
	  }
	  resultUtils.setStatus(500);
	  resultUtils.setMessage("操作失败");
	  return resultUtils;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtils updateTaskQuestions(TaskAnswerQuestions taskAnswerQuestions)
    {
        //taskRating 0待审核;1需修改;2通过;3完美
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  try
	  {
		if ("2".equals(taskAnswerQuestions.getTaskRating()) || "3".equals(taskAnswerQuestions.getTaskRating()))
		{
		    TaskAnswerQuestions taskAnswerQuestions1 = taskAnswerQuestionsMapper.selectById(taskAnswerQuestions.getQuestionsId());
		    ClubTask clubTask = selectById(taskAnswerQuestions1.getTaskId());

		    Club club = clubService.selectById(clubTask.getClubId());
		    MemberAssets memberAssets = iMemberAssetsService.selectOne(new EntityWrapper<MemberAssets>().eq("memberId", taskAnswerQuestions1.getMemberId()));// 完成任务人
		    MemberAssets memberAssets1 = iMemberAssetsService.selectOne(new EntityWrapper<MemberAssets>().eq("memberId", club.getMemberId()));// 发布人

		    if (memberAssets1.getMemberDiamondsizeOfGold() < club.getWage())
		    {
			  resultUtils.setStatus(500);
			  resultUtils.setMessage("您的资产不足");
			  return resultUtils;
		    }

		    clubTask.setTaskHasNumber(clubTask.getTaskHasNumber() + 1);
		    clubTask.setTaskNotNumber(clubTask.getTaskNotNumber() - 1);
		    updateById(clubTask);

		    // 发放的总工资
		    club.setTotalWages(club.getTotalWages() + club.getWage());
		    clubService.updateById(club);

		    // 成员的总工资 月周日
		    ClubBuddy one = clubBuddyService.getOne(club.getClubId(), taskAnswerQuestions1.getMemberId());
		    Member member = iMemberService.selectById(taskAnswerQuestions1.getMemberId());
		    one.setDiamondTime(new Date());
		    one.setDiamondNumber(one.getDiamondNumber() + club.getWage());
		    one.setDiamondDayNumber(club.getWage());
		    one.setDiamondMothNumber(one.getDiamondMothNumber() + club.getWage());
		    one.setDiamondWeekNumber(one.getDiamondWeekNumber() + club.getWage());
		    one.setDiamondTime(new Date());
		    clubBuddyService.updateById(one);

		    // 会员资产
		    memberAssets.setMemberDiamondsizeOfGold(memberAssets.getMemberDiamondsizeOfGold() + club.getWage());
		    iMemberAssetsService.updateById(memberAssets);

		    memberAssets1.setMemberDiamondsizeOfGold(memberAssets1.getMemberDiamondsizeOfGold() - club.getWage());
		    iMemberAssetsService.updateById(memberAssets1);

		    // 发放记录
		    RechargeRecord rechargeRecord = new RechargeRecord();
		    rechargeRecord.setMemberId(memberAssets1.getMemberId());
		    rechargeRecord.setName(club.getClubName() + "俱乐部发放工资");
		    rechargeRecord.setCurrency(3);
		    rechargeRecord.setMode(4);
		    rechargeRecord.setType(2);
		    rechargeRecord.setRunSize(club.getWage());
		    rechargeRecord.setSurplus(memberAssets1.getMemberDiamondsizeOfGold());
		    rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
		    iRechargeRecordService.insert(rechargeRecord);

		    // 资产记录
		    WagesLogs wagesLogs = new WagesLogs();
		    wagesLogs.setMemberid(taskAnswerQuestions1.getMemberId().toString());
		    wagesLogs.setTaskId(taskAnswerQuestions1.getTaskId());
		    wagesLogs.setMonery(club.getWage().toString());
		    wagesLogs.setCreatetime(new Date());
		    wagesLogs.setSurplusSzie(memberAssets1.getMemberDiamondsizeOfGold());
		    wagesLogsService.add(wagesLogs);

		    taskAnswerQuestions.setWageTime(new Date());
		    taskAnswerQuestions.setDiamondDayNumber(club.getWage());
		    taskAnswerQuestions.setSurplusSzie(memberAssets.getMemberDiamondsizeOfGold());
		    Integer integer = taskAnswerQuestionsMapper.updateById(taskAnswerQuestions);
		    Member member1 = iMemberService.selectById(club.getMemberId());
		    Notice notice = new Notice();
		    notice.setType(1);
		    notice.setOfMember(member.getId());
		    notice.setContext(member1.getNickName() + "发了"+club.getClubName()+"俱乐部的工资");
		    notice.setTitle(member1.getNickName() + "发了"+club.getClubName()+"俱乐部的工资");
		    notice.setAddTime(new Date());
		    iNoticeService.insert(notice);

		    // 收取记录
		    RechargeRecord rechargeRecord1 = new RechargeRecord();
		    rechargeRecord1.setMemberId(memberAssets.getMemberId());
		    rechargeRecord1.setName("收到" + club.getClubName() + "俱乐部工资");
		    rechargeRecord1.setCurrency(3);
		    rechargeRecord1.setMode(4);
		    rechargeRecord1.setType(1);
		    rechargeRecord1.setRunSize(club.getWage());
		    rechargeRecord1.setSurplus(memberAssets.getMemberDiamondsizeOfGold());
		    rechargeRecord1.setAddTime(new Timestamp(System.currentTimeMillis()));
		    iRechargeRecordService.insert(rechargeRecord1);

		    if (integer > 0)
			  return resultUtils;
		    resultUtils.setStatus(500);
		    resultUtils.setMessage("操作失败");
		    return resultUtils;
		}
		taskAnswerQuestions.setIsRead("1");
		Integer integer = taskAnswerQuestionsMapper.updateById(taskAnswerQuestions);
		if (integer > 0)
		    return resultUtils;
		resultUtils.setStatus(500);
		resultUtils.setMessage("操作失败");
		return resultUtils;
	  }
	  catch (Exception e)
	  {
		e.printStackTrace();
	  }
	  resultUtils.setStatus(500);
	  resultUtils.setMessage("操作失败");
	  return resultUtils;
    }

    @Override
    public ResultUtils taskDetails(ClubTask clubTask)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  resultUtils.setData(new ClubTask());
	  resultUtils.setData1(new ArrayList<TaskAnswerQuestions>());
	  EntityWrapper<ClubTask> wrapper = new EntityWrapper<>();

	  wrapper.setSqlSelect("id, gameLabelId,past_status pastStatus, task_content taskContent, task_url taskUrl, task_number taskNumber, task_not_number taskNotNumber, task_has_number taskHasNumber, member_id memberId, club_id clubId, " + "(SELECT game_select_title from game_select_label  WHERE id=gameLabelId) gameTitle, " + "(SELECT game_select_title_icon from game_select_label WHERE id=gameLabelId) gameIcon, " + "(SELECT game_type from game_select_label WHERE id=gameLabelId) gameType");
	  wrapper.eq("id", clubTask.getId());
	  ClubTask clubTask1 = selectOne(wrapper);
	  if (clubTask1 == null)
		return resultUtils;
	  List<TaskAnswerQuestions> taskAnswerQuestions = taskAnswerQuestionsMapper.selectList(new EntityWrapper<TaskAnswerQuestions>().eq("task_id", clubTask.getId()));
	  resultUtils.setData(clubTask1);
	  resultUtils.setData1(taskAnswerQuestions);
	  return resultUtils;
    }

    @Override
    public ResultUtils getTaskQuestions(TaskAnswerQuestions taskAnswerQuestions)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  resultUtils.setData(new TaskAnswerQuestions());
	  TaskAnswerQuestions taskAnswerQuestions1 = selectTaskQuestions(taskAnswerQuestions);
	  if (taskAnswerQuestions1 == null)
		return resultUtils;
	  resultUtils.setData(taskAnswerQuestions1);
	  return resultUtils;
    }

    @Override
    public ResultUtils alterTaskQuestions(TaskAnswerQuestions taskAnswerQuestions)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  taskAnswerQuestions.setTaskRating("0");
	  Integer update = taskAnswerQuestionsMapper.update(taskAnswerQuestions, getQuestionsWrapper(taskAnswerQuestions));
	  if (update > 0)
		return resultUtils;
	  resultUtils.setStatus(500);
	  resultUtils.setMessage("操作失败");
	  return resultUtils;
    }


    @Override
    public TaskAnswerQuestions selectTaskQuestions(TaskAnswerQuestions taskAnswerQuestions)
    {
	  return taskAnswerQuestionsMapper.selectOne(taskAnswerQuestions);
    }

    private Wrapper<TaskAnswerQuestions> getQuestionsWrapper(TaskAnswerQuestions taskAnswerQuestions)
    {
	  return new EntityWrapper<TaskAnswerQuestions>().eq("task_id", taskAnswerQuestions.getTaskId()).eq("member_id", taskAnswerQuestions.getMemberId());
    }


    @Override
    public ResultUtils notAccomplish(Integer clubId, Integer memberId)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  resultUtils.setData(0);
	  List<ClubTask> clubTasks = selectList(new EntityWrapper<ClubTask>().like("create_time", DateUtils.getDate("yyyy-MM-dd")).eq("club_id", clubId).orderBy("id", false));
	  if (clubTasks != null && clubTasks.size() > 0)
	  {
		ClubBuddy one = clubBuddyService.getOne(clubId, memberId);
		if (one == null || "1".equals(one.getSecretaryStatus()))
		    return resultUtils;

		ClubTask clubTask = clubTasks.get(0);
		List<TaskAnswerQuestions> taskAnswerQuestions = taskAnswerQuestionsService.selectList(new EntityWrapper<TaskAnswerQuestions>().eq("task_id", clubTask.getId()).eq("member_id", memberId));
		if (taskAnswerQuestions.size() == 0)
		{
		    resultUtils.setData(1);
		}
		else if ("1".equals(taskAnswerQuestions.get(0).getIsRead()))
		{
		    resultUtils.setData(1);
		}

	  }

	  return resultUtils;
    }

    /**
     * @Description: clubQuartzMonth 俱乐部提示工资是否够用
     * @return: void
     * @Author: cfx
     * @Date: 2020-11-23 15:38
     */
    // @Scheduled(cron = "*/1 * * * * ?")
    @Scheduled(cron = "0 0 */2 * * ?")
    public void clubQuartzRank()
    {
	  List<Club> clubList = clubService.selectList(null);
	  List<MemberAssets> memberAssets = iMemberAssetsService.selectList(null);
	  List<Member> members = iMemberService.selectList(null);

	  List<Notice> notices = new ArrayList<>();

	  for (Club club : clubList)
	  {
		for (MemberAssets memberAsset : memberAssets)
		{
		    if (club.getMemberId().equals(memberAsset.getMemberId()))
		    {
			  int club_id = clubBuddyService.selectCount(new EntityWrapper<ClubBuddy>().eq("club_id", club.getClubId()));
			  if (club.getWage() * club_id > memberAsset.getMemberDiamondsizeOfGold())
			  {
				Notice notice = new Notice();
				notice.setOfMember(club.getMemberId());
				notice.setAddTime(new Date());
				notice.setContext("您的钻石不足发放工资,请您充值");
				notice.setTitle("您的钻石不足发放工资,请您充值");
				notice.setType(1);
				for (Member member : members)
				{
				    if (member.getId().equals(club.getMemberId()))
				    {
					  notice.setHead(member.getHead());
					  notice.setNickname(member.getNickName());
				    }
				}
				notices.add(notice);
			  }
		    }
		}
	  }
	  if (notices != null && notices.size() > 0)
	  {
		boolean b = iNoticeService.insertBatch(notices);

	  }
    }


}