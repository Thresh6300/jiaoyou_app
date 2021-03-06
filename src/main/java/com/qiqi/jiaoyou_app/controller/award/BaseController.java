package com.qiqi.jiaoyou_app.controller.award;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qiqi.jiaoyou_app.mapper.MemberMapper;
import com.qiqi.jiaoyou_app.pojo.Member;
import com.qiqi.jiaoyou_app.service.IMemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author nan
 * 根据Token获取用户的信息
 * @date 2020/12/15 14:31
 */
public class BaseController
{
    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private IMemberService iMemberService;

    /**
     * 根据token获取用户的信息
     *
     * @param token
     * @return
     */
    public Member getMemberInfo(String token)
    {
        if (StringUtils.isNotBlank(token))
	  {
		List<Member> member = memberMapper.selectList(new EntityWrapper<Member>().eq("token", token));
		 if (member.size()>0)
		return member.get(0);
	  }
	return new Member();
    }

    /**
     * 更新用户的最近一次登录时间，Web判断用户在线还是不在线的时候，不是根据状态，而是根据最近一次登录时间是否小于一小时
     *
     * @param member
     * @return
     */
    public Boolean updateMember(Member member)
    {
	  /*设置登录的次数*/
	  //		member.setLoginSize(member.getLoginSize() == null || member.getLoginSize() == 0 ? 1 : member.getLoginSize() + 1);
	  //设置最近的登录时间
	  member.setTodayLoginTime(new Date());
	  //设置在线状态 0 不在线 1 在线
	  member.setOnLine(1);
	  Integer bl = memberMapper.updateById(member);
	  if (bl > 0)
	  {
		return true;
	  }
	  return false;
    }


    /*根据用户的token查询用户的最近一次的操作信息，用来判断用户的在线离线*/
    public boolean UpdateOnline(String token)
    {
	  Member member = iMemberService.getInfoByToken(token);
	  if (member != null)
	  {
		//在这里进行逻辑更新用户的最近登录的信息
		member.setTodayLoginTime(new Date());
		member.setOnLine(1);
		return iMemberService.updateById(member);
	  }
	  return false;
    }
}