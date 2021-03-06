package com.qiqi.jiaoyou_app.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.mapper.CircleOfFriendsMapper;
import com.qiqi.jiaoyou_app.mapper.FabulousMapper;
import com.qiqi.jiaoyou_app.mapper.RiderDynamicsMapper;
import com.qiqi.jiaoyou_app.pojo.CircleOfFriends;
import com.qiqi.jiaoyou_app.pojo.Fabulous;
import com.qiqi.jiaoyou_app.pojo.Member;
import com.qiqi.jiaoyou_app.pojo.RiderDynamics;
import com.qiqi.jiaoyou_app.service.IFabulousService;
import com.qiqi.jiaoyou_app.service.IMemberService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sunLQ
 * @since 2020-05-08
 */
@SuppressWarnings("ALL")
@Service
public class FabulousServiceImpl extends ServiceImpl<FabulousMapper, Fabulous> implements IFabulousService
{

    @Autowired
    private FabulousMapper fabulousMapper;
    @Autowired
    private CircleOfFriendsMapper circleOfFriendsMapper;
    @Autowired
    private RiderDynamicsMapper riderDynamicsMapper;
    @Autowired
    private IMemberService iMemberService;


    @Override
    public ResultUtils dynamicThumbsUp(Fabulous fabulous)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  List<Fabulous> fabulous1 = fabulousMapper.selectList(new EntityWrapper<Fabulous>().eq("memberId", fabulous.getMemberId()).eq("dynamicId", fabulous.getDynamicId()).eq("type", fabulous.getType()));
	  Member member = iMemberService.selectById(fabulous.getMemberId());
	  if (fabulous1.size() > 0)
	  {
		resultUtils.setMessage("您已经点过赞了");
		resultUtils.setStatus(Constant.STATUS_FAILED);
		return resultUtils;
	  }
	  CircleOfFriends circleOfFriends = circleOfFriendsMapper.selectById(fabulous.getDynamicId());
	  fabulous.setOneselfId(circleOfFriends.getMemerId());
	  fabulous.setMemberHead(member.getHead());
	  fabulous.setMemberNickName(member.getNickName());
	  if (StringUtils.isNotBlank(circleOfFriends.getImages()))
	  {
		fabulous.setContextType("1");
	  }
	  else if (StringUtils.isNotBlank(circleOfFriends.getVideo()))
	  {
		fabulous.setContextType("2");
	  }
	  else
	  {
		fabulous.setContextType("0");
	  }


	  //新增点赞记录
	  fabulous.setAddTime(new Timestamp(System.currentTimeMillis()));

	  Integer insert = fabulousMapper.insert(fabulous);
	  if (insert > 0)
	  {
		//动态类型 1：朋友圈动态 2：车友圈动态
		Integer type = fabulous.getType();
		//获取动态id，并修改该动态点赞数量
		Integer dynamicId = fabulous.getDynamicId();
		if (type == 1)
		{

		    circleOfFriends.setLikeSize(circleOfFriends.getLikeSize() == null || circleOfFriends.getLikeSize() == 0 ? 1 : circleOfFriends.getLikeSize() + 1);
		    Integer integer = circleOfFriendsMapper.updateById(circleOfFriends);
		    if (integer > 0)
		    {
			  resultUtils.setMessage("点赞成功");
			  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		    }
		    else
		    {
			  resultUtils.setMessage("改变点赞数量失败");
			  resultUtils.setStatus(Constant.STATUS_FAILED);
		    }
		}
		else if (type == 2)
		{
		    RiderDynamics riderDynamics = riderDynamicsMapper.selectById(fabulous.getDynamicId());
		    riderDynamics.setLikeSize(riderDynamics.getLikeSize() == null || riderDynamics.getLikeSize() == 0 ? 1 : riderDynamics.getLikeSize() + 1);
		    Integer integer = riderDynamicsMapper.updateById(riderDynamics);
		    if (integer > 0)
		    {
			  resultUtils.setMessage("点赞成功");
			  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		    }
		    else
		    {
			  resultUtils.setMessage("改变点赞数量失败");
			  resultUtils.setStatus(Constant.STATUS_FAILED);
		    }
		}
		else
		{
		    resultUtils.setMessage("动态类型有误");
		    resultUtils.setStatus(Constant.STATUS_FAILED);
		}
	  }
	  else
	  {
		resultUtils.setMessage("新增点赞记录失败");
		resultUtils.setStatus(Constant.STATUS_FAILED);
	  }
	  return resultUtils;
    }
}
