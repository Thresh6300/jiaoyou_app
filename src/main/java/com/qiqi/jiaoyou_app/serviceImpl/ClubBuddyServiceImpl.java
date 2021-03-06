package com.qiqi.jiaoyou_app.serviceImpl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.mapper.ClubBuddyMapper;
import com.qiqi.jiaoyou_app.pojo.ClubBuddy;
import com.qiqi.jiaoyou_app.service.ClubBuddyService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 俱乐部好友表(ClubBuddy)表服务实现类
 * clubBuddy
 *
 * @author cfx
 * @since 2020-11-27 11:39:48
 */
@Slf4j
@Service("clubBuddyService")
public class ClubBuddyServiceImpl extends ServiceImpl<ClubBuddyMapper, ClubBuddy> implements ClubBuddyService
{
    @Autowired
    private ClubBuddyMapper clubBuddyMapper;


    @Override
    public ResultUtils buddyList(ClubBuddy clubBuddy)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  EntityWrapper<ClubBuddy> wrapper = new EntityWrapper<>();
	  wrapper.setSqlSelect("buddy_id buddyId, no_disturbing noDisturbing, oneself_id oneselfId, member_id memberId, secretary_status secretaryStatus, club_id clubId, (SELECT head FROM member WHERE id=member_id) head, (SELECT nickName FROM member WHERE id=member_id) nickName");
	  wrapper.eq("club_id", clubBuddy.getClubId())
			.eq(clubBuddy.getMemberId() != null, "member_id", clubBuddy.getMemberId());
	  Page page = new Page(clubBuddy.getPageNum(), clubBuddy.getPageSize());
	  page.setOptimizeCountSql(true);
	  page.setSearchCount(true);
	  List<ClubBuddy> clubTasks = clubBuddyMapper.selectPage(page, wrapper);
	  resultUtils.setData(clubTasks);
	  resultUtils.setCount((int) page.getTotal());
	  resultUtils.setPages((int) page.getPages());
	  return resultUtils;
    }

    @Override
    public ResultUtils secretaryList(ClubBuddy clubBuddy)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  EntityWrapper<ClubBuddy> wrapper = new EntityWrapper<>();
	  wrapper.setSqlSelect("buddy_id buddyId, oneself_id oneselfId, member_id memberId, secretary_status secretaryStatus, club_id clubId, (SELECT head FROM member WHERE id=member_id) head, (SELECT nickName FROM member WHERE id=member_id) nickName");
	  wrapper.eq("club_id", clubBuddy.getClubId()).eq("secretary_status","1");
	  Page page = new Page(clubBuddy.getPageNum(), clubBuddy.getPageSize());
	  page.setOptimizeCountSql(true);
	  page.setSearchCount(true);
	  List<ClubBuddy> clubTasks = clubBuddyMapper.selectPage(page, wrapper);
	  resultUtils.setData(clubTasks);
	  resultUtils.setCount((int) page.getTotal());
	  resultUtils.setPages((int) page.getPages());
	  return resultUtils;
    }

    @Override
    public ClubBuddy getOne(Integer clubId, Integer memberId)
    {
	  return selectOne(new EntityWrapper<ClubBuddy>().eq("club_id",clubId).eq("member_id",memberId));
    }
}