package com.qiqi.jiaoyou_app.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.mapper.AcceptOfflineActivitiesMapper;
import com.qiqi.jiaoyou_app.mapper.MemberMapper;
import com.qiqi.jiaoyou_app.mapper.NoticeMapper;
import com.qiqi.jiaoyou_app.mapper.SystemMessageMapper;
import com.qiqi.jiaoyou_app.pojo.AcceptOfflineActivities;
import com.qiqi.jiaoyou_app.pojo.Member;
import com.qiqi.jiaoyou_app.pojo.Notice;
import com.qiqi.jiaoyou_app.pojo.SystemMessage;
import com.qiqi.jiaoyou_app.service.ISystemMessageService;
import com.qiqi.jiaoyou_app.util.DateUtils;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统消息表 服务实现类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@SuppressWarnings("ALL")
@Service
public class SystemMessageServiceImpl extends ServiceImpl<SystemMessageMapper, SystemMessage> implements ISystemMessageService
{

    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private AcceptOfflineActivitiesMapper acceptOfflineActivitiesMapper;
    @Autowired
    private MemberMapper memberMapper;


    @Override
    public ResultUtils messageList(Integer memberId, Integer pageSize, Integer pageNum, String keyWord)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  Page page = new Page(pageNum, pageSize);
	  page.setOptimizeCountSql(true);
	  page.setSearchCount(true);
	  List<Notice> systemMessages = noticeMapper.selectPage(page, new EntityWrapper<Notice>().like(!StringUtils.isEmpty(keyWord), "title", keyWord).andNew().eq("type", 1).or().eq("of_member", memberId).orderBy("addTime", false));
	  for (Notice systemMessage : systemMessages)
	  {
		systemMessage.setTimeOld(DateUtils.getShortTime(systemMessage.getAddTime()));
		if (systemMessage.getType() == 2)
		{
		    systemMessage.setState(acceptOfflineActivitiesMapper.selectById(systemMessage.getShenqingId()).getKeepAnAppointmentState());
		}
		String readMembers = systemMessage.getReadMembers();
		if (readMembers == null || "".equals(readMembers) || "null".equals(readMembers))
		{
		    systemMessage.setNotRead(false);
		}
		else
		{
		    String[] split = systemMessage.getReadMembers().split(",");
		    if (!Arrays.asList(split).contains(memberId + ""))
		    {
			  systemMessage.setNotRead(false);
		    }
		    else
		    {
			  systemMessage.setNotRead(true);
		    }
		}
	  }
	  resultUtils.setData(systemMessages);
	  resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
	  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  return resultUtils;
    }


    @Override
    public ResultUtils messageDetail(Integer memberId, Integer id, String type)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  if ("0".equals(type))
	  {
		SystemMessage systemMessage = selectById(id);

		if (StringUtils.isEmpty(systemMessage.getReadMembers()))
		{
		    systemMessage.setReadMembers(memberId.toString());
		}
		else if (!systemMessage.getReadMembers().contains(memberId.toString()))
		{
		    systemMessage.setReadMembers(systemMessage.getReadMembers() + "," + memberId);
		}
		updateById(systemMessage);
		resultUtils.setData(systemMessage);
		resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		return resultUtils;
	  }
	  Notice notice = noticeMapper.selectById(id);
	  String readMembers = notice.getReadMembers();

	  if (StringUtils.isEmpty(notice.getReadMembers()))
	  {
		notice.setReadMembers(memberId.toString());
	  }
	  else if (!notice.getReadMembers().contains(memberId.toString()))
	  {
		notice.setReadMembers(notice.getReadMembers() + "," + memberId);
	  }
	  noticeMapper.updateById(notice);
	  notice.setTimeOld(DateUtils.getShortTime(notice.getAddTime()));
	  if (notice.getType() == 2)
	  {
		AcceptOfflineActivities acceptOfflineActivities = acceptOfflineActivitiesMapper.selectById(notice.getShenqingId());
		if (acceptOfflineActivities != null)
		{
		    notice.setState(acceptOfflineActivities.getKeepAnAppointmentState());
		}
	  }
	  resultUtils.setData(notice);
	  resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
	  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  return resultUtils;
    }

    @Override
    public ResultUtils notRead(Integer memberId)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  //查询出属于该会员的消息列表
	  List<Notice> systemMessages = noticeMapper.selectList(new EntityWrapper<Notice>().andNew().eq("type", 1).or().eq("of_member", memberId).orderBy("addTime", false));
	  boolean notRead = true;
	  for (Notice notice : systemMessages)
	  {
		String readMembers = notice.getReadMembers();
		if (readMembers == null || "".equals(readMembers) || "null".equals(readMembers))
		{
		    notRead = false;
		    break;
		}
		else
		{
		    String[] split = notice.getReadMembers().split(",");
		    if (!Arrays.asList(split).contains(memberId + ""))
		    {
			  notRead = false;
			  break;
		    }
		}
	  }
	  if (notRead)
	  {
		resultUtils.setMessage(Constant.ALL_MESSAGES_READ);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  }
	  else
	  {
		resultUtils.setMessage(Constant.THERE_ARE_UNREAD_MESSAGES);
		resultUtils.setStatus(Constant.STATUS_FAILED);
	  }
	  return resultUtils;
    }

    //我的获取消息信息的部分 需要在系统消息和会员专享的地方进行限制  查询消息的开始时间是用户的注册时间 臭狗屎 又不要了??????20210121
    //author：nan
    //time：20210120
    //update info for data 0 3
    @Override
    public ResultUtils messageOnes(Integer memberId, String keyWord)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
	  resultUtils.setData(new SystemMessage("暂无消息"));
	  resultUtils.setData1(new Notice("暂无消息"));
	  resultUtils.setData2(new Notice("暂无消息"));
	  resultUtils.setData3(new Notice("暂无消息"));

	  //获取当前用户的信息
	  Member membersa = memberMapper.selectById(memberId);
	  // 0系统消息
	  //获取未删除的启用的系统消息
	  Date date = new Date();
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  //设置新增时间
	  String format = sdf.format(membersa.getAddTime());
// .ge("addTime",format)
	  List<SystemMessage> systemMessages = selectList(new EntityWrapper<SystemMessage>()
			.isNull("memberId")
			.eq("deleteState", "2")
			.eq("enableState", "1")
			.eq(StringUtils.isNotBlank(keyWord), "title", keyWord)
			.orderBy("addTime", false));
	  //获取未删除启用的举报消息
	  List<SystemMessage> systemMessages1 = selectList(new EntityWrapper<SystemMessage>()
			.ge("addTime",format).isNotNull("memberId").eq("memberId",memberId).eq("deleteState", "2").eq("enableState", "1").eq(StringUtils.isNotBlank(keyWord), "title", keyWord).orderBy("addTime", false));

	  if (systemMessages != null && systemMessages.size() > 0)
	  {

		List<SystemMessage> collect = systemMessages.stream().sequential().collect(Collectors.toCollection(() -> systemMessages1));
		List<SystemMessage> collect1 = collect.stream().sorted(Comparator.comparing(SystemMessage::getId).reversed()).collect(Collectors.toList());

		//更新是否有红点显示的逻辑 查询所有的符合条件的数据 若是此用户有一条未读的话,那么就是未读
		//author:nan
		//time:202210123
		SystemMessage systemMessage = new SystemMessage();
		for(int a1 = 0;a1 < collect1.size();a1++){
		    systemMessage = collect1.get(a1);
		    if(StringUtils.isNotBlank(systemMessage.getReadMembers()) && !systemMessage.getReadMembers().contains(memberId.toString())){
			  collect1.get(0).setNotRead(true);
		    }
		    if(StringUtils.isEmpty(systemMessage.getReadMembers())){
			  collect1.get(0).setNotRead(true);
		    }
		}
		resultUtils.setData(collect1.get(0));

		// SystemMessage systemMessage = collect1.get(0);
		// if (StringUtils.isNotBlank(systemMessage.getReadMembers()) && !systemMessage.getReadMembers().contains(memberId.toString()))
		// {
		//     systemMessage.setNotRead(true);
		// }
		// if (StringUtils.isEmpty(systemMessage.getReadMembers()))
		// {
		//     systemMessage.setNotRead(true);
		// }
		// resultUtils.setData(systemMessage);

	  }
	  // 1普通公告;
	  List<Notice> notices = noticeMapper.selectList(new EntityWrapper<Notice>().eq("type", "1").like(StringUtils.isNotBlank(keyWord), "title", keyWord).eq("of_member", memberId).orderBy("addTime", false));
	  if (notices != null && notices.size() > 0)
	  {
		//更新是否有红点显示的逻辑 查询所有的符合条件的数据 若是此用户有一条未读的话,那么就是未读
		//author:nan
		//time:202210123
		// Notice notice = new Notice();
		// for(int a1 = 0;a1 < notices.size();a1++){
		//     notice = notices.get(a1);
		//     if(StringUtils.isNotBlank(notice.getReadMembers()) && !notice.getReadMembers().contains(memberId.toString())){
		// 	  notices.get(0).setNotRead(true);
		//     }
		//     if(StringUtils.isEmpty(notice.getReadMembers())){
		// 	  notices.get(0).setNotRead(true);
		//     }
		// }
		// resultUtils.setData1(notices.get(0));

		// Notice notice = getNotice(notices.get(0), memberId);
		Notice notice = getNotice(notices, memberId);
		resultUtils.setData1(notice);
	  }
	  // 2聚会;
	  List<Notice> meeting = noticeMapper.selectList(new EntityWrapper<Notice>().eq("type", "2").like(StringUtils.isNotBlank(keyWord), "title", keyWord).eq("of_member", memberId).orderBy("addTime", false));
	  if (meeting != null && meeting.size() > 0)
	  {
		//更新是否有红点显示的逻辑 查询所有的符合条件的数据 若是此用户有一条未读的话,那么就是未读
		//author:nan
		//time:202210123
		// Notice notice = new Notice();
		// for(int a1 = 0;a1 < notices.size();a1++){
		//     notice = notices.get(a1);
		//     if(StringUtils.isNotBlank(notice.getReadMembers()) && !notice.getReadMembers().contains(memberId.toString())){
		// 	  meeting.get(0).setNotRead(true);
		//     }
		//     if(StringUtils.isEmpty(notice.getReadMembers())){
		// 	  meeting.get(0).setNotRead(true);
		//     }
		// }
		// resultUtils.setData2(meeting.get(0));

		// Notice notice = getNotice(meeting.get(0), memberId);
		Notice notice = getNotice(meeting, memberId);
		resultUtils.setData2(notice);
	  }

	  Date datea = new Date();
	  SimpleDateFormat sdfa = new SimpleDateFormat("yyyy-MM-dd");
	  //
	  String formata = sdf.format(membersa.getAddTime());
	  // 3会员专享
	  System.out.println(formata);
	  System.out.println(format.substring(0,10));

	  List<Notice> member =	noticeMapper.selectList(new EntityWrapper<Notice>()
			.eq("type", 3).like(StringUtils.isNotBlank(keyWord), "title", keyWord).eq("of_member", memberId).orderBy("addTime", false)
			.or().eq("type", 4)
			.like(StringUtils.isNotBlank(keyWord), "title", keyWord)
			.orderBy("addTime", false)
			.ge("add_time",format));
	  if (member != null && member.size() > 0)
	  {
		//更新是否有红点显示的逻辑 查询所有的符合条件的数据 若是此用户有一条未读的话,那么就是未读
		//author:nan
		//time:202210123
		// Notice notice = new Notice();
		// for(int a1 = 0;a1 < notices.size();a1++){
		//     notice = notices.get(a1);
		//     if(StringUtils.isNotBlank(notice.getReadMembers()) && !notice.getReadMembers().contains(memberId.toString())){
		// 	  member.get(0).setNotRead(true);
		//     }
		//     if(StringUtils.isEmpty(notice.getReadMembers())){
		// 	  member.get(0).setNotRead(true);
		//     }
		// }
		// resultUtils.setData3(member.get(0));
		// Notice notice = getNotice(member.get(0), memberId);
		Notice notice = getNotice(member, memberId);
		resultUtils.setData3(notice);
	  }
	  return resultUtils;
    }

    //查看的是会员专享的消息
    @Override
    public ResultUtils messageLists(Integer memberId, Integer pageNum, Integer pageSize, String keyWord, String type)
    {
        //	0系统消息;
	  ResultUtils resultUtils = new ResultUtils();
	  Page page = new Page(pageNum, pageSize);
	  page.setOptimizeCountSql(true);
	  page.setSearchCount(true);
	  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  resultUtils.setMessage("查询成功");
	  resultUtils.setData(new ArrayList<>());

	  //备注？？本来我也想打备注，结果粘贴复制不能用了，算了 自己看吧
	  Member membersa = memberMapper.selectById(memberId);
	  Date date = new Date();
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  String format = sdf.format(membersa.getAddTime());

	  if ("0".equals(type))
	  {
		List<SystemMessage> systemMessages = selectPage(page,new EntityWrapper<SystemMessage>()
			    .isNull("memberId").eq("deleteState", "2").eq("enableState", "1").eq(StringUtils.isNotBlank(keyWord), "title", keyWord).orderBy("addTime", false)).getRecords();
		List<SystemMessage> systemMessages1 = selectPage(page,new EntityWrapper<SystemMessage>()
			    .isNotNull("memberId").eq("memberId",memberId).eq("deleteState", "2").eq("enableState", "1").eq(StringUtils.isNotBlank(keyWord), "title", keyWord).orderBy("addTime", false)).getRecords();

		Page page1 = selectPage(page, new EntityWrapper<SystemMessage>().eq("enableState", "1").eq("deleteState", "2").like(StringUtils.isNotBlank(keyWord), "title", keyWord).orderBy("addTime", false));
		List<SystemMessage> messagesas = new ArrayList<>();
		List<SystemMessage> messages = (List<SystemMessage>)(page1.getRecords());
		if (systemMessages != null && systemMessages.size() > 0)
		{

		    List<SystemMessage> collect = systemMessages.stream().sequential().collect(Collectors.toCollection(() -> systemMessages1));
		    List<SystemMessage> collect1 = collect.stream().sorted(Comparator.comparing(SystemMessage::getId).reversed()).collect(Collectors.toList());

		    for (SystemMessage systemMessage : collect1)
		    {
			  if (StringUtils.isNotBlank(systemMessage.getReadMembers()) && !systemMessage.getReadMembers().contains(memberId.toString()))
			  {
				systemMessage.setNotRead(true);
			  }
			  if (StringUtils.isEmpty(systemMessage.getReadMembers()))
			  {
				systemMessage.setNotRead(true);
			  }
		    }
		    Member member = memberMapper.selectById(memberId);

		    // if(member.getLoginSize() == 1){
			//   resultUtils.setData(null);
		    // }else{
			  resultUtils.setData(collect1);
		    // }

		    resultUtils.setCount((int) page.getTotal());
		    resultUtils.setPages((int) page.getPages());
		    return resultUtils;

		}
		System.out.println();
	  }
	  else
	  {
	      //1普通公告;2聚会;3会员专享
		List<Notice> notices = new ArrayList<>();
		if ("1".equals(type))
		{
		    notices = noticeMapper.selectPage(page, new EntityWrapper<Notice>().eq("type", 1).like(StringUtils.isNotBlank(keyWord), "title", keyWord).eq("of_member", memberId).orderBy("addTime", false));

		}
		else if ("2".equals(type))
		{
		    notices = noticeMapper.selectPage(page, new EntityWrapper<Notice>().eq("type", 2).like(StringUtils.isNotBlank(keyWord), "title", keyWord).eq("of_member", memberId).orderBy("addTime", false));
		}
		else if ("3".equals(type))
		{
		    //查询这个用户的注册时间,必须大于注册时间的才显示
		    //系统消息和会员专享，如果是第一次登陆，那么把这个用户的id加到
		    // 已读用户的id列表，并且不显示系统和会员专享部分的消息
		    Member member = memberMapper.selectById(memberId);

		    Date datea = new Date();
		    SimpleDateFormat sdfa = new SimpleDateFormat("yyyy-MM-dd");
		    String formata = sdf.format(membersa.getAddTime());

		    notices = noticeMapper.selectList(new EntityWrapper<Notice>()
				  .eq("type", 3).like(StringUtils.isNotBlank(keyWord), "title", keyWord).eq("of_member", memberId).orderBy("addTime", false)
				  .or().eq("type", 4)
				  .like(StringUtils.isNotBlank(keyWord), "title", keyWord)
				  .orderBy("addTime", false)
				  .ge("add_time",format));

		}
		if (notices != null && notices.size() > 0)
		{
		    for (Notice systemMessage : notices)
		    {
			  systemMessage.setTimeOld(DateUtils.getShortTime(systemMessage.getAddTime()));
			  if (systemMessage.getType() == 2 && systemMessage.getShenqingId() != null)
			  {
				AcceptOfflineActivities acceptOfflineActivities = acceptOfflineActivitiesMapper.selectById(systemMessage.getShenqingId());
				if (acceptOfflineActivities != null)
				{
				    systemMessage.setState(acceptOfflineActivities.getKeepAnAppointmentState());
				}
				else
				{
				    systemMessage.setState(2);
				}
			  }

			  if (StringUtils.isNotBlank(systemMessage.getReadMembers()) && !systemMessage.getReadMembers().contains(memberId.toString()))
			  {
				systemMessage.setNotRead(true);
			  }
			  if (StringUtils.isEmpty(systemMessage.getReadMembers()))
			  {
				systemMessage.setNotRead(true);
			  }
		    }
		    Member member = memberMapper.selectById(memberId);

		    // if(member.getLoginSize() == 1){
			//   resultUtils.setData(null);
		    // }else{
			  resultUtils.setData(notices);
		    // }

		    resultUtils.setCount((int) page.getTotal());
		    resultUtils.setPages((int) page.getPages());
		}
	  }
	  return resultUtils;
    }

    //我的右上角的是否已读的消息条数标记............................................
    //.........................................................................
    @Override
    public ResultUtils isRead(Integer memberId)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  //获取当前用户的信息
	  Member membersa = memberMapper.selectById(memberId);
	  // 0系统消息
	  //获取未删除的启用的系统消息
	  Date date = new Date();
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  //设置新增时间
	  String format = sdf.format(membersa.getAddTime());


	  List<Notice> notices = noticeMapper.selectList(new EntityWrapper<Notice>()
			.in("type",Arrays.asList(1,2,3)).eq("of_member", memberId)
			.or().eq("type", 4).isNull("of_member")
			.ge("add_time",format));
	  List<SystemMessage> systemMessages = selectList(new EntityWrapper<SystemMessage>().isNull("memberId").eq("deleteState", "2").eq("enableState", "1").orderBy("addTime", false));
	  List<SystemMessage> systemMessages1 = selectList(new EntityWrapper<SystemMessage>().isNotNull("memberId").eq("memberId",memberId).eq("deleteState", "2").eq("enableState", "1").orderBy("addTime", false));

	  List<SystemMessage> systemMessages2 = systemMessages.stream().sequential().collect(Collectors.toCollection(() -> systemMessages1));
	  List<SystemMessage> systemMessage3 = systemMessages2.stream().sorted(Comparator.comparing(SystemMessage::getId).reversed()).collect(Collectors.toList());

	  for (SystemMessage systemMessage : systemMessage3)
	  {
		String readMembers = systemMessage.getReadMembers();
		if (StringUtils.isNotBlank(readMembers) && !readMembers.contains(memberId.toString()))
		{
		    systemMessage.setNotRead(true);
		}
		if (StringUtils.isEmpty(readMembers))
		{
		    systemMessage.setNotRead(true);
		}
	  }
	  for (Notice systemMessage : notices)
	  {
		String readMembers = systemMessage.getReadMembers();
		if (StringUtils.isNotBlank(readMembers) && !readMembers.contains(memberId.toString()))
		{
		    systemMessage.setNotRead(true);
		}
		if (StringUtils.isEmpty(readMembers))
		{
		    systemMessage.setNotRead(true);
		}
	  }

	  List<Boolean> collect = notices.stream().map(Notice::getNotRead).collect(Collectors.toList());
	  List<Boolean> collect1 = systemMessages.stream().map(SystemMessage::getNotRead).collect(Collectors.toList());
	  List<Boolean> collect2 = collect.stream().sequential().collect(Collectors.toCollection(() -> collect1));

	  int c = 0;
	  for (int i = 0; i < collect2.size(); i++)
	  {

		if (collect2.get(i).equals(true))
		{
		    c++;
		}
	  }
	  resultUtils.setData(c);
	  return resultUtils;
    }

    // private Notice getNotice(Notice systemMessage, Integer memberId)
    private Notice getNotice(List<Notice> systemMessage, Integer memberId)
    {
	  systemMessage.get(0).setTimeOld(DateUtils.getShortTime(systemMessage.get(0).getAddTime()));
	  if (systemMessage.get(0).getType() == 2&&systemMessage.get(0).getShenqingId()!=null)
	  {
		AcceptOfflineActivities acceptOfflineActivities = acceptOfflineActivitiesMapper.selectById(systemMessage.get(0).getShenqingId());
		if (acceptOfflineActivities != null)
		{
		    Integer keepAnAppointmentState = acceptOfflineActivities.getKeepAnAppointmentState();
		    if (keepAnAppointmentState != null)
		    {
			  systemMessage.get(0).setState(keepAnAppointmentState);
		    }
		    else
		    {
			  systemMessage.get(0).setState(2);
		    }
		}
	  }
	  Notice notice = new Notice();
	  for(int a1 = 0;a1 < systemMessage.size();a1++){
		// notice = systemMessage.get(a1);
		if(StringUtils.isNotBlank(systemMessage.get(a1).getReadMembers()) && !systemMessage.get(a1).getReadMembers().contains(memberId.toString())){
		    systemMessage.get(0).setNotRead(true);
		}
		if(StringUtils.isEmpty(systemMessage.get(a1).getReadMembers())){
		    systemMessage.get(0).setNotRead(true);
		}
	  }

	  // if (StringUtils.isNotBlank(systemMessage.getReadMembers()) && !systemMessage.getReadMembers().contains(memberId.toString()))
	  // {
		// systemMessage.setNotRead(true);
	  // }
	  // if (StringUtils.isEmpty(systemMessage.getReadMembers()))
	  // {
		// systemMessage.setNotRead(true);
	  // }
	  return  systemMessage.get(0);
	  // return systemMessage;
    }

}
