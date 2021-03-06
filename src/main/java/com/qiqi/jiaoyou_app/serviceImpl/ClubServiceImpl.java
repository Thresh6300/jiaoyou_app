package com.qiqi.jiaoyou_app.serviceImpl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.Instant.GroupJson;
import com.qiqi.jiaoyou_app.Instant.MemberJson;
import com.qiqi.jiaoyou_app.Instant.NewFriendList;
import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.jPush.utils.JpushUtil;
import com.qiqi.jiaoyou_app.mapper.ClubMapper;
import com.qiqi.jiaoyou_app.mapper.MemberAssetsMapper;
import com.qiqi.jiaoyou_app.mapper.MemberMapper;
import com.qiqi.jiaoyou_app.mapper.NoticeMapper;
import com.qiqi.jiaoyou_app.pojo.*;
import com.qiqi.jiaoyou_app.service.*;
import com.qiqi.jiaoyou_app.util.Convert;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 俱乐部(Club)表服务实现类
 * club
 *
 * @author cfx
 * @since 2020-11-27 09:36:34
 */
@Slf4j
@Service("clubService")
@EnableScheduling
public class ClubServiceImpl extends ServiceImpl<ClubMapper, Club> implements ClubService
{
    @Autowired
    private ClubMapper clubMapper;

    @Autowired
    private ClubBuddyService clubBuddyService;

    @Autowired
    private IGiftService iGiftService;

    @Autowired
    private IMemberService iMemberService;

    @Autowired
    private ClubNoticeService clubNoticeService;
    @Autowired(required = false)
    private MemberAssetsMapper memberAssetsMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private INoticeService iNoticeService;
    @Autowired
    private NoticeMapper noticeMapper;

    public static void main(String[] args)
    {

	  int x = 55;
	  int y = 110;

	  System.out.println(x + "" + "和" + y + "的最大公因数是:" + gcd(x, y) + "\t最小公倍数是:" + lcm(x, y));
    }

    //  计算两个非负整数 p 和 q 的最小公倍数
    public static int lcm(int p, int q)
    {
	  int p1 = p;
	  int q1 = q;

	  while (q != 0)
	  {
		int r = p % q;
		p = q;
		q = r;
	  }
	  return (p1 * q1) / p;
    }

    //  计算两个非负整数 x 和 y 的最大公因数
    public static int gcd(int x, int y)
    {
	  int re = 0;
	  while (x != y)
	  {
		if (x > y)
		{
		    x = x - y;
		    re = x;
		    gcd(x, y);
		}
		else
		{
		    y = y - x;
		    re = y;
		    gcd(x, y);
		}
	  }
	  return re;
    }

    /**
     * 通过ID查询单条数据
     *
     * @param club 主键
     * @return 实例对象
     * @author cfx
     * @since 2020-11-27 09:36:34
     */
    @Override
    public ResultUtils selectById(Club club)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  resultUtils.setData(new Club());
	  Club club1 = this.clubMapper.selectById(club.getClubId());
	  if (club1 != null)
	  {
		ClubBuddy clubBuddy = clubBuddyService.selectOne(new EntityWrapper<ClubBuddy>().eq("member_id", club.getMemberId()).eq("club_id", club.getClubId()));
		if (clubBuddy != null)
		{
		    club1.setNoDisturbing(clubBuddy.getNoDisturbing());
		}
		Member member = iMemberService.selectById(club1.getMemberId());
		club1.setMember(member);
		resultUtils.setData(club1);
		return resultUtils;
	  }
	  return resultUtils;
    }

    @Override
    public ResultUtils addClub(Club club)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  /**
	   * nan
	   * 创建俱乐部的限制条件：
	   * 金钻 >= 3000
	   * 10以下只能创建一个
	   * 10-50 级可以创建两个
	   * 50-100 可以创建三个
	   * */
	  /*start===========================*/
	  //判断金钻数是否满足
	  int i = selectCount(new EntityWrapper<Club>().eq("club_name", club.getClubName()));
	  if (i > 0)
	  {
		resultUtils.setCode(400);
		resultUtils.setMessage("该俱乐部已经被创建,请您重新更改名称");
		return resultUtils;
	  }
	  if (club.getMemberId() != null)
	  {
		List<MemberAssets> member = memberAssetsMapper.selectList(new EntityWrapper<MemberAssets>().eq("memberId", club.getMemberId()));
		if (member.get(0).getMemberDiamondsizeOfGold() < 3000)
		{
		    resultUtils.setCode(400);
		    resultUtils.setMessage("您不符合俱乐部创建条件，请确保您的金钻数有三千或以上");
		    return resultUtils;
		}
		//判断俱乐部是否满足级别创建条件 <10级 <1俱乐部
		List<Club> clubs = clubMapper.selectList(new EntityWrapper<Club>().eq("memberId", club.getMemberId()));
		List<Member> members = memberMapper.selectList(new EntityWrapper<Member>().eq("id", club.getMemberId()));
		if (members.get(0).getLevel() < 10 && clubs.size() == 1)
		{
		    resultUtils.setCode(400);
		    resultUtils.setMessage("您的账户等级只能创建一个俱乐部，请升级你的账户级别");
		    return resultUtils;
		}
		// 10 <= <50  <2
		if (members.get(0).getLevel() >= 10 && members.get(0).getLevel() < 50 && clubs.size() == 2)
		{
		    resultUtils.setCode(400);
		    resultUtils.setMessage("您的账户等级只能创建两个俱乐部，请升级你的账户级别");
		    return resultUtils;
		}
		// 50 <= <100 <3
		if (members.get(0).getLevel() >= 50 && members.get(0).getLevel() < 100 && clubs.size() == 3)
		{
		    resultUtils.setCode(400);
		    resultUtils.setMessage("您的账户等级只能创建三个俱乐部");
		    return resultUtils;
		}
	  }
	  else
	  {
		resultUtils.setCode(400);
		resultUtils.setMessage("会员id不能为空的哟亲");
		return resultUtils;
	  }
	  /*end=============================*/


	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  club.setType("0");
	  club.setCreateTime(new Date());
	  Gift gift = iGiftService.selectById(club.getGiftId());
	  Member member = iMemberService.selectById(club.getMemberId());
	  club.setClubIcon(member.getHead());
	  Integer insert = clubMapper.insert(club);
	  if (insert > 0)
	  {
		GroupJson groupJson = new GroupJson();
		groupJson.setName(club.getClubName());
		groupJson.setGroupId(club.getClubId().toString());
		groupJson.setOwner_Account(club.getMemberId().toString());
		groupJson.setType("Public");
		ResultUtils group = iMemberService.create_group(groupJson);
		if (group.getStatus().equals(Constant.STATUS_FAILED))
		{
		    clubMapper.deleteById(club.getClubId());
		    return group;
		}
		Club club1 = clubMapper.selectById(club.getClubId());
		resultUtils.setData(club1);
		ClubBuddy clubBuddy = new ClubBuddy();
		clubBuddy.setClubId(club1.getClubId());
		clubBuddy.setOneselfId(club1.getMemberId());
		clubBuddy.setMemberId(club1.getMemberId());
		clubBuddy.setSecretaryStatus("1");
		clubBuddyService.insert(clubBuddy);
		return group;
	  }
	  resultUtils.setStatus(500);
	  resultUtils.setMessage("操作失败");
	  return resultUtils;
    }

    @Override
    public ResultUtils inviteBuddy(ClubBuddy clubBuddy)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  List<ClubBuddy> list = new ArrayList<>();
	  if (StringUtils.isEmpty(clubBuddy.getMemberIds()))
	  {
		resultUtils.setStatus(500);
		resultUtils.setMessage("请选择好友");
		return resultUtils;
	  }

	  List<Integer> memberIds = Convert.toIntList(clubBuddy.getMemberIds());
	  int i = clubBuddyService.selectCount(new EntityWrapper<ClubBuddy>().eq("club_id", clubBuddy.getClubId()).in("member_id", memberIds));
	  if (i > 0)
	  {
		resultUtils.setStatus(500);
		resultUtils.setMessage("不能重复添加");
		return resultUtils;
	  }
	  Club club = selectById(clubBuddy.getClubId());
	  Member member = iMemberService.selectById(club.getMemberId());
	  List<Notice> notices = new ArrayList<>();
	  for (Integer memberId : memberIds)
	  {
		ClubBuddy clubBuddy1 = new ClubBuddy();
		clubBuddy1.setMemberId(memberId);
		clubBuddy1.setClubId(clubBuddy.getClubId());
		clubBuddy1.setOneselfId(clubBuddy.getOneselfId());
		clubBuddy1.setSecretaryStatus("0");
		clubBuddy1.setCreateTime(new Date());
		clubBuddy1.setDiamondTime(new Date());
		list.add(clubBuddy1);

		Notice notice = new Notice();
		notice.setType(1);
		notice.setOfMember(memberId);
		notice.setTitle("俱乐部邀请");
		notice.setContext("您被\"" + member.getNickName() + "\"邀请加入了\"" + club.getClubName() + "\"俱乐部");
		notices.add(notice);
		notice.setAddTime(new Date());
	  }
	  iNoticeService.insertBatch(notices);
	  if (list.size() == 0)
	  {
		resultUtils.setStatus(500);
		resultUtils.setMessage("请选择好友");
		return resultUtils;
	  }
	  if (clubBuddyService.insertBatch(list))
	  {
		// 添加im群成员
		GroupJson groupJson = new GroupJson();
		List<MemberJson> memberList = new ArrayList<>();
		for (int y = 0; y < memberIds.size(); y++)
		{
		    MemberJson memberJson = new MemberJson();
		    memberJson.setMember_Account(memberIds.get(y).toString());
		    memberList.add(memberJson);
		}
		groupJson.setMemberList(memberList);
		groupJson.setGroupId(clubBuddy.getClubId().toString());
		return iMemberService.addGroupMember(groupJson);
	  }


	  resultUtils.setStatus(500);
	  resultUtils.setMessage("操作失败");
	  return resultUtils;
    }

    //对狗屎的设置秘书的时候还是需要给秘书单独推送被设置未秘书的提醒的
    @Override
    public ResultUtils setSecretary(ClubBuddy clubBuddy)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  if ("1".equals(clubBuddy.getSecretaryStatus()))
	  {
		Club club = selectById(clubBuddy.getClubId());
		int i = clubBuddyService.selectCount(new EntityWrapper<ClubBuddy>().eq("club_id", clubBuddy.getClubId()).eq("secretary_status", "1"));
		if (club.getSecretaryNumber() > i)
		{
		    resultUtils.setStatus(500);
		    resultUtils.setMessage("您只能设置" + club.getSecretaryNumber() + "个秘书");
		    return resultUtils;
		}
	  }
	  ClubBuddy clubBuddy2 = clubBuddyService.selectOne(new EntityWrapper<ClubBuddy>().eq("club_id", clubBuddy.getClubId()).eq("member_id", clubBuddy.getMemberId()));
	  if ("0".equals(clubBuddy2.getSecretaryStatus()))
	  {
		resultUtils.setStatus(500);
		resultUtils.setMessage("您不是管理员,不能设置");
		return resultUtils;
	  }

	  if (StringUtils.isEmpty(clubBuddy.getMemberIds()))
	  {
		resultUtils.setStatus(500);
		resultUtils.setMessage("请选择好友");
		return resultUtils;
	  }
	  List<Integer> memberIds = Convert.toIntList(clubBuddy.getMemberIds());
	  List<ClubBuddy> list = clubBuddyService.selectList(new EntityWrapper<ClubBuddy>().eq("club_id", clubBuddy.getClubId()).in("member_id", memberIds));

	  //便利俱乐部好友
	  for (ClubBuddy buddy : list)
	  {
		for (Integer memberId : memberIds)
		{
		    if (memberId.equals(clubBuddy.getMemberId()))
		    {
			  resultUtils.setStatus(500);
			  resultUtils.setMessage("您自己不能设置自己");
			  return resultUtils;
		    }
		    if (memberId.equals(buddy.getOneselfId()) && "0".equals(clubBuddy.getSecretaryStatus()))
		    {
			  resultUtils.setStatus(500);
			  resultUtils.setMessage("您是创建人不能取消");
			  return resultUtils;
		    }
		}
		buddy.setSecretaryStatus(clubBuddy.getSecretaryStatus());
	  }

	  //如果设置秘书成功,那么在这里加上提醒(.....他这里是加入了一个秘书数组id.....)
	  if (clubBuddyService.updateBatchById(list))
	  {
		Club club = baseMapper.selectById(clubBuddy.getClubId());
		Member member = memberMapper.selectById(clubBuddy.getMemberId());

		//循环被操作的数组对象
		List<Notice> notices = new ArrayList<>();
		Notice notice = new Notice();
		for (ClubBuddy buddy : list)
		{
		    //0bu是秘书  1 是秘书
		    if (clubBuddy.getSecretaryStatus().equals("0"))
		    {
			  notice = new Notice();
			  notice.setType(1);
			  notice.setOfMember(buddy.getMemberId());
			  // 您被管理员“昵称”设置为“小花花”俱乐部秘书。
			  notice.setContext("您被管理员\"" + member.getNickName() + "\"取消\"" + club.getClubName() + "\"俱乐部秘书资格");
			  notice.setTitle("秘书设置");
			  notice.setAddTime(new Date());
			  notices.add(notice);
		    }
		    if (clubBuddy.getSecretaryStatus().equals("1"))
		    {
			  notice = new Notice();
			  notice.setType(1);
			  notice.setOfMember(buddy.getMemberId());
			  // 您被管理员“昵称”设置为“小花花”俱乐部秘书。
			  notice.setContext("您被管理员\"" + member.getNickName() + "\"设置为\"" + club.getClubName() + "\"俱乐部秘书");
			  notice.setTitle("秘书设置");
			  notice.setAddTime(new Date());
			  notices.add(notice);
		    }
		}
		if (notices.size() > 0)
		{
		    Boolean bl = iNoticeService.insertBatch(notices);
		    if (bl)
		    {
			  return resultUtils;
		    }
		}
		resultUtils.setCode(500);
		resultUtils.setMessage("秘书设置失败");
		return resultUtils;
	  }


	  resultUtils.setStatus(500);
	  resultUtils.setMessage("操作失败");
	  return resultUtils;
    }

    //设置俱乐部的发工资数量和发工资时间=
    @Override
    public ResultUtils setClub(Club club)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  //要求的是,俱乐部的公告修改,修改的位置是俱乐部的简介
	  if (club.getClubNotice() != null)
	  {
		club.setClubIntroduction(club.getClubNotice());
	  }
	  //{"clubId":"173","wage":"200","wageTime":"23:1"}
	  //俱乐部最高只能设置一万金额的工资
	  if(club.getWage() != null && club.getWage() > 10000){
		resultUtils.setStatus(500);
		resultUtils.setMessage("俱乐部工资最高只能设置10000金钻");
	  }
	  //这个用户的金钻余量,必须能满足给所有用户发三天的金钻数
	  //获取俱乐部信息
	  List<Club> club1 = baseMapper.selectList(new EntityWrapper<Club>()
	  .eq("club_id",club.getClubId()));


	  if(club1 != null){
		//获取俱乐部的会员数量信息
		Integer clubbuddy = clubBuddyService.selectCount(new EntityWrapper<ClubBuddy>()
			    .eq("club_id",club1.get(0).getClubId()));
		//如果是一人的话,给这个值加1防止减为负数
		if(clubbuddy.equals(1)){
		    clubbuddy = 2;
		}
		//获取会员的资产信息
		List<MemberAssets> memberAssets = memberAssetsMapper.selectList(new EntityWrapper<MemberAssets>()
		.eq("memberId",club1.get(0).getMemberId()));
		//计算三天需要的金钻数量
		Long totalGlod = club1.get(0).getWage() * 3 *(clubbuddy - 1);
		//如果金钻不够发三天的发
		if(memberAssets.get(0).getMemberDiamondsizeOfGold() < totalGlod){
		    resultUtils.setStatus(500);
		    resultUtils.setMessage("您的金钻数量不足以给所有群成员发三天工资,请充值后再试!");
		}
	  }


	  Integer integer = clubMapper.updateById(club);
	  if (integer > 0)
		return resultUtils;
	  resultUtils.setStatus(500);
	  resultUtils.setMessage("操作失败");
	  return resultUtils;
    }

    @Override
    public ResultUtils delBuddy(ClubBuddy clubBuddy)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  ClubBuddy clubBuddy1 = clubBuddyService.selectOne(new EntityWrapper<ClubBuddy>().eq("club_id", clubBuddy.getClubId()).eq("member_id", clubBuddy.getMemberId()));


	  if ("0".equals(clubBuddy1.getSecretaryStatus()) || !clubBuddy.getMemberId().equals(clubBuddy1.getOneselfId()))
	  {
		resultUtils.setStatus(500);
		resultUtils.setMessage("您还不是管理员,不能踢人");
		return resultUtils;
	  }
	  List<Integer> integers = Convert.toIntList(clubBuddy.getMemberIds());
	  List<ClubBuddy> list = clubBuddyService.selectList(new EntityWrapper<ClubBuddy>().eq("club_id", clubBuddy.getClubId()).in("member_id", integers));
	  for (ClubBuddy buddy : list)
	  {
		if (buddy.getMemberId().equals(clubBuddy.getMemberId()))
		{
		    resultUtils.setStatus(500);
		    resultUtils.setMessage("您不能踢自己");
		    return resultUtils;
		}
	  }

	  List<Integer> collect = list.stream().map(ClubBuddy::getBuddyId).collect(Collectors.toList());

	  if (clubBuddyService.deleteBatchIds(collect))
	  {
		GroupJson groupJson = new GroupJson();
		groupJson.setGroupId(clubBuddy.getClubId().toString());
		groupJson.setMemberToDel_Account(Convert.toStList(clubBuddy.getMemberIds()));
		String s = NewFriendList.delete_group_member(groupJson);

		Club club = selectById(clubBuddy.getClubId());
		List<Notice> notices = new ArrayList<>();
		for (Integer string : integers)
		{
		    Notice notice = new Notice();
		    notice.setType(1);
		    notice.setOfMember(string);
		    notice.setContext("您被管理员踢出了\"" + club.getClubName() + "\"俱乐部");
		    // notice.setTitle("俱乐部踢人");
		    notice.setTitle("俱乐部删除成员");
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
    public ResultUtils quitBuddy(ClubBuddy clubBuddy)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  int i = clubBuddyService.selectCount(new EntityWrapper<ClubBuddy>().eq("club_id", clubBuddy.getClubId()).eq("member_id", clubBuddy.getMemberId()));
	  if (i == 0)
	  {
		resultUtils.setStatus(500);
		resultUtils.setMessage("成员不存在");
		return resultUtils;
	  }

	  boolean delete = clubBuddyService.delete(new EntityWrapper<ClubBuddy>().eq("club_id", clubBuddy.getClubId()).eq("member_id", clubBuddy.getMemberId()));
	  if (delete)
	  {
		GroupJson groupJson = new GroupJson();
		groupJson.setGroupId(clubBuddy.getClubId().toString());
		groupJson.setMemberToDel_Account(Arrays.asList(clubBuddy.getMemberId().toString()));
		String s = NewFriendList.delete_group_member(groupJson);

		Member member = iMemberService.selectById(clubBuddy.getMemberId());
		List<ClubBuddy> clubBuddies = clubBuddyService.selectList(new EntityWrapper<ClubBuddy>().eq("club_id", clubBuddy.getClubId()).eq("secretary_status", "1"));
		List<Integer> collect = clubBuddies.stream().map(ClubBuddy::getMemberId).collect(Collectors.toList());

		Club club = selectById(clubBuddy.getClubId());
		List<Notice> notices = new ArrayList<>();
		for (Integer string : collect)
		{
		    Notice notice = new Notice();
		    notice.setType(1);
		    notice.setOfMember(string);
		    notice.setTitle("退出俱乐部");
		    notice.setContext("\"" + member.getNickName() + "\"退出了您的\"" + club.getClubName() + "\"俱乐部");
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
    public ResultUtils clubList(Club club)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  resultUtils.setData(new ArrayList<>());
	  Page page = new Page(club.getPageNum(), club.getPageSize());
	  page.setOptimizeCountSql(true);
	  page.setSearchCount(true);


	  EntityWrapper<Club> wrapper = new EntityWrapper<>();
	  wrapper.orderBy("total_wages", false);

	  List<Club> clubList = selectList(wrapper);
	  for (int i = 0; i < clubList.size(); i++)
	  {
		clubList.get(i).setClubSort(i);
	  }
	  /***
	   * @Description: 防止初次进入报500
	   * @Author: nan
	   * @Date: 2021-01-18 11:32
	   */

	  if (clubList != null && clubList.size() > 0)
	  {
		updateBatchById(clubList);
	  }

	  if (club.getMemberId() != null && "2".equals(club.getType()))
	  {
		List<ClubBuddy> list = clubBuddyService.selectList(new EntityWrapper<ClubBuddy>().eq("member_id", club.getMemberId()).ne("oneself_id", club.getMemberId()));
		List<Integer> collect = list.stream().map(ClubBuddy::getClubId).collect(Collectors.toList());

		if (collect != null && collect.size() > 0)
		{
		    wrapper.in("club_id", collect).like(StringUtils.isNotBlank(club.getClubName()), "club_name", club.getClubName());
		}
		else
		{
		    wrapper.in("club_id", Arrays.asList("ooo"));
		}
	  }
	  else if (club.getMemberId() != null && "1".equals(club.getType()))
	  {
		wrapper.eq("memberId", club.getMemberId()).like(StringUtils.isNotBlank(club.getClubName()), "club_name", club.getClubName());
	  }
	  else if ("0".equals(club.getType()))
	  {
		wrapper.orderBy("wage", false).eq("type", "0").like(StringUtils.isNotBlank(club.getClubName()), "club_name", club.getClubName());
	  }
	  else
	  {
		wrapper.like(StringUtils.isNotBlank(club.getClubName()), "club_name", club.getClubName());
	  }
	  List<Club> clubs = clubMapper.selectPage(page, wrapper);
	  if (clubs == null || clubs.size() == 0)
		return resultUtils;
	  resultUtils.setCount((int) page.getTotal());
	  resultUtils.setPages((int) page.getPages());
	  resultUtils.setData(clubs);
	  return resultUtils;
    }

    @Override
    public ResultUtils isSecretaryStatus(ClubBuddy clubBuddy)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("查询成功");
	  resultUtils.setData(new ClubBuddy());

	  ClubBuddy clubBuddy1 = clubBuddyService.selectOne(new EntityWrapper<ClubBuddy>().eq("club_id", clubBuddy.getClubId()).eq("member_id", clubBuddy.getMemberId()));
	  if (clubBuddy1 == null)
		return resultUtils;
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("查询成功");
	  resultUtils.setData(clubBuddy1);
	  return resultUtils;
    }

    @Override
    public ResultUtils dissolveClub(Club club)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  List<ClubBuddy> list = clubBuddyService.selectList(new EntityWrapper<ClubBuddy>().eq("club_id", club.getClubId()));
	  List<Integer> collect = list.stream().map(ClubBuddy::getClubId).collect(Collectors.toList());

	  if (deleteById(club.getClubId()))
	  {
		boolean delete = clubBuddyService.delete(new EntityWrapper<ClubBuddy>().eq("club_id", club.getClubId()));
		if (delete)
		{
		    GroupJson groupJson = new GroupJson();
		    groupJson.setGroupId(club.getClubId().toString());
		    // groupJson.setMemberToDel_Account();
		    String s = NewFriendList.destroy_group(groupJson);

		    return resultUtils;
		}
	  }
	  resultUtils.setStatus(500);
	  resultUtils.setMessage("操作失败");
	  return resultUtils;
    }

    @Override
    public ResultUtils setClubBuddy(ClubBuddy clubBuddy)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  boolean update = clubBuddyService.update(clubBuddy, new EntityWrapper<ClubBuddy>().eq("member_id", clubBuddy.getMemberId()).eq("club_id", clubBuddy.getClubId()));
	  if (update)
		return resultUtils;
	  resultUtils.setStatus(500);
	  resultUtils.setMessage("操作失败");
	  return resultUtils;
    }

    @Override
    public ResultUtils clubSearchList(Club club)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  resultUtils.setData(new ArrayList<Club>());
	  List<Club> clubs = clubMapper.selectList(new EntityWrapper<Club>().like(StringUtils.isNotBlank(club.getClubName()), "club_name", club.getClubName()));
	  if (clubs == null || clubs.size() == 0)
		return resultUtils;
	  resultUtils.setData(clubs);
	  return resultUtils;
    }

    @Override
    public ResultUtils applyfor(ClubNotice clubNotice)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  Member member = iMemberService.selectById(clubNotice.getBuddyNoticeUserId());
	  Club club = selectById(clubNotice.getBuddyNoticeClubId());

	  clubNotice.setBuddyNoticeTitle("\"" + member.getNickName() + "\"申请加入\"" + club.getClubName() + "\"");
	  clubNotice.setBuddyNoticeStatus("0");
	  clubNotice.setBuddyNoticeUserName(member.getNickName());
	  clubNotice.setHend(member.getHead());
	  clubNotice.setBuddyNoticeClubUserId(club.getMemberId());
	  clubNotice.setCreateTime(new Date());
	  JpushUtil.sendToRegistrationId(member.getPushId(), "\"" + member.getNickName() + "\"申请加入\"" + club.getClubName() + "\"", "\"" + member.getNickName() + "\"申请加入\"" + club.getClubName() + "\"", "\"" + member.getNickName() + "\"申请加入\"" + club.getClubName() + "\"", new HashMap<>());
	  if (clubNoticeService.insert(clubNotice))
		return resultUtils;
	  resultUtils.setStatus(500);
	  resultUtils.setMessage("操作失败");
	  return resultUtils;
    }

    @Override
    public ResultUtils inClubStatus(ClubBuddy clubBuddy)
    {
	  Club club = new Club();
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("查询成功");
	  resultUtils.setData(club);

	  ClubBuddy clubBuddy1 = clubBuddyService.selectOne(new EntityWrapper<ClubBuddy>().eq("club_id", clubBuddy.getClubId()).eq("member_id", clubBuddy.getMemberId()));
	  if (clubBuddy1 == null)
		return resultUtils;
	  club.setInClub("0");
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("查询成功");
	  resultUtils.setData(club);
	  return resultUtils;
    }

    /*时候通过加入俱乐部的审核
     * update nan
     * time:20210121
     * con:审核通过之后不能给发工资
     * */
    @Override
    public ResultUtils addApplyfor(ClubNotice clubNotice)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  ClubNotice clubNotice1 = clubNoticeService.selectById(clubNotice.getBuddyNoticeId());
	  clubNotice.setIsReads("");
	  //0审核中; 1通过 2不通过
	  if ("1".equals(clubNotice.getBuddyNoticeStatus()))
	  {
		if (clubNoticeService.updateById(clubNotice))
		{

		    ClubBuddy clubBuddy = new ClubBuddy();
		    clubBuddy.setClubId(clubNotice1.getBuddyNoticeClubId());
		    clubBuddy.setMemberId(clubNotice1.getBuddyNoticeUserId());
		    clubBuddy.setOneselfId(clubNotice1.getBuddyNoticeClubUserId());
		    clubBuddy.setSecretaryStatus("0");
		    clubBuddy.setCreateTime(new Date());
		    clubBuddy.setDiamondTime(new Date());
		    System.out.println("打印加入俱乐部的时间" + new Date());
		    ResultUtils resultUtils1 = isClubBuddy(clubBuddy);
		    if (resultUtils1.getStatus().equals(500))
			  return resultUtils1;
		    clubBuddyService.insert(clubBuddy);
		    // 添加im群成员
		    GroupJson groupJson = new GroupJson();
		    List<MemberJson> memberList = new ArrayList<>();
		    MemberJson memberJson = new MemberJson();
		    memberJson.setMember_Account(clubBuddy.getMemberId().toString());
		    memberList.add(memberJson);
		    groupJson.setMemberList(memberList);
		    groupJson.setGroupId(clubBuddy.getClubId().toString());
		    Member member = iMemberService.selectById(clubBuddy.getMemberId());
		    Member member1 = iMemberService.selectById(clubNotice1.getBuddyNoticeClubUserId());
		    JpushUtil.sendToRegistrationId(member.getPushId(), member1.getNickName() + "通过了您的申请", member1.getNickName() + "通过了您的申请", member1.getNickName() + "通过了您的申请", new HashMap<>());

		    return iMemberService.addGroupMember(groupJson);
		}
	  }
	  else if ("2".equals(clubNotice.getBuddyNoticeStatus()))
	  {
		if (clubNoticeService.updateById(clubNotice))
		{
		    Member member = iMemberService.selectById(clubNotice1.getBuddyNoticeUserId());
		    Member member1 = iMemberService.selectById(clubNotice1.getBuddyNoticeClubUserId());
		    JpushUtil.sendToRegistrationId(member.getPushId(), member1.getNickName() + "拒绝了您的申请", member1.getNickName() + "拒绝了您的申请", member1.getNickName() + "拒绝了您的申请", new HashMap<>());
		    return resultUtils;
		}
	  }
	  resultUtils.setStatus(500);
	  resultUtils.setMessage("操作失败");
	  return resultUtils;
    }

    @Override
    public ResultUtils clubNotice(Integer userId)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("查询成功");
	  resultUtils.setData(new ArrayList<>());
	  List<ClubNotice> clubNotices = clubNoticeService.selectList(new EntityWrapper<ClubNotice>().eq("buddy_notice_club_user_id", userId));
	  List<ClubNotice> clubNotices2 = clubNoticeService.selectList(new EntityWrapper<ClubNotice>().eq("buddy_notice_user_id", userId));
	  if (clubNotices != null && clubNotices.size() > 0)
	  {
		for (ClubNotice clubNotice : clubNotices)
		{
		    clubNotice.setType("1");
		}
	  }
	  if (clubNotices2 != null && clubNotices2.size() > 0)
	  {
		for (ClubNotice clubNotice : clubNotices2)
		{
		    clubNotice.setType("0");
		}
	  }
	  List<ClubNotice> collect = clubNotices.stream().sequential().collect(Collectors.toCollection(() -> clubNotices2));
	  if (collect == null || collect.size() == 0)
		return resultUtils;
	  List<ClubNotice> collect1 = collect.stream().sorted(Comparator.comparingInt(ClubNotice::getBuddyNoticeId).reversed()).collect(Collectors.toList());


	  resultUtils.setData(collect1);
	  return resultUtils;
    }

    @Override
    public ResultUtils delNotice(Integer buddyNoticeId)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  if (clubNoticeService.deleteById(buddyNoticeId))
		return resultUtils;
	  resultUtils.setStatus(500);
	  resultUtils.setMessage("操作失败");
	  return resultUtils;
    }


    @Override
    public ResultUtils ranking(Integer clubId, Integer type)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  Wrapper<ClubBuddy> clubBuddyWrapper = new EntityWrapper<>();
	  clubBuddyWrapper.eq("club_id", clubId);

	  switch (type)
	  {
		case 1:
		    clubBuddyWrapper.setSqlSelect("member_id memberId, diamondDayNumber diamondNumber,(SELECT head FROM member WHERE id=member_id) head," + "(SELECT nickName FROM member WHERE id=member_id) nickName").orderBy("diamondDayNumber", false);
		    break;
		case 2:
		    clubBuddyWrapper.setSqlSelect("member_id memberId, diamondWeekNumber diamondNumber,(SELECT head FROM member WHERE id=member_id) head," + "(SELECT nickName FROM member WHERE id=member_id) nickName").orderBy("diamondWeekNumber", false);
		    break;
		case 3:
		    clubBuddyWrapper.setSqlSelect("member_id memberId, diamondMothNumber diamondNumber,(SELECT head FROM member WHERE id=member_id) head," + "(SELECT nickName FROM member WHERE id=member_id) nickName").orderBy("diamondMothNumber", false);
		    break;
	  }
	  List<ClubBuddy> list = clubBuddyService.selectList(clubBuddyWrapper);
	  if (list == null && list.size() == 0)
		return resultUtils;
	  resultUtils.setData(list);
	  return resultUtils;
    }

    @Override
    public ResultUtils isclubNotice(Integer userId)
    {
	  ResultUtils resultUtils1 = new ResultUtils();
	  resultUtils1.setStatus(200);
	  resultUtils1.setMessage("查询成功");
	  resultUtils1.setData(0);
	  ResultUtils resultUtils = clubNotice(userId);
	  List<ClubNotice> data = (List<ClubNotice>) resultUtils.getData();
	  for (ClubNotice datum : data)
	  {
		if (StringUtils.isNotBlank(datum.getIsReads()) && !datum.getIsReads().contains(userId.toString()))
		{
		    System.out.println(datum.getIsReads());
		    resultUtils1.setData(1);
		}
	  }
	  return resultUtils1;
    }

    @Override
    public List<Club> getQuestionsTime(Integer[] clubIds)
    {

	  return clubMapper.getQuestionsTime(clubIds);
    }

    private ResultUtils isClubBuddy(ClubBuddy clubBuddy)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("查询成功");
	  int i = clubBuddyService.selectCount(new EntityWrapper<ClubBuddy>().eq("club_id", clubBuddy.getClubId()).eq("member_id", clubBuddy.getMemberId()));
	  if (i == 0)
		return resultUtils;
	  resultUtils.setStatus(500);
	  resultUtils.setMessage("已经加入了俱乐部");
	  return resultUtils;
    }
}