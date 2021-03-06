package com.qiqi.jiaoyou_app.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.jPush.utils.JpushUtil;
import com.qiqi.jiaoyou_app.mapper.*;
import com.qiqi.jiaoyou_app.pojo.*;
import com.qiqi.jiaoyou_app.service.IDynamicReviewOfFriendsCircleService;
import com.qiqi.jiaoyou_app.service.IFabulousService;
import com.qiqi.jiaoyou_app.service.IMemberAssetsService;
import com.qiqi.jiaoyou_app.service.IRechargeRecordService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 朋友圈动态评论表 服务实现类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Service
public class DynamicReviewOfFriendsCircleServiceImpl extends ServiceImpl<DynamicReviewOfFriendsCircleMapper, DynamicReviewOfFriendsCircle> implements IDynamicReviewOfFriendsCircleService
{

    @Autowired(required = false)
    private DynamicReviewOfFriendsCircleMapper dynamicReviewOfFriendsCircleMapper;
    @Autowired(required = false)
    private CircleOfFriendsMapper circleOfFriendsMapper;
    @Autowired(required = false)
    private DynamicCommentsOfCarUsersMapper dynamicCommentsOfCarUsersMapper;
    @Autowired(required = false)
    private RiderDynamicsMapper riderDynamicsMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired(required = false)
    private MembershipSettingsMapper membershipSettingsMapper;

    @Autowired(required = false)
    private FabulousMapper fabulousMapper;

    @Autowired
    private IFabulousService iFabulousService;

    @Autowired
    private IMemberAssetsService iMemberAssetsService;
    @Autowired
    private IRechargeRecordService iRechargeRecordService;

    //朋友圈动态评论
    //udpate info edit bug
    //bugContent:用户世界圈评论对方之后,对方的金钻数量会和评论人的数量一样
    //author:nan
    //time:20210129
    @Override
    @Transactional
    public ResultUtils dynamicDetails(DynamicReviewOfFriendsCircle dynamicReviewOfFriendsCircle)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  Member member = memberMapper.selectById(dynamicReviewOfFriendsCircle.getMemberId());
	  dynamicReviewOfFriendsCircle.setLable(member.getCarLable());
	  //评论时间
	  dynamicReviewOfFriendsCircle.setAddTime(new Timestamp(System.currentTimeMillis()));
	  //新增
	  // getOneselfId 动态的id
	  CircleOfFriends circleOfFriends = circleOfFriendsMapper.selectById(dynamicReviewOfFriendsCircle.getOneselfId());
	  //动态 id
	  dynamicReviewOfFriendsCircle.setDynamicId(dynamicReviewOfFriendsCircle.getOneselfId());
	  //发表动态的会员的id
	  dynamicReviewOfFriendsCircle.setOneselfId(circleOfFriends.getMemerId());
	  if (2 == dynamicReviewOfFriendsCircle.getLevel())
	  {
	      // 二级回复评论谁的用户id
		DynamicReviewOfFriendsCircle dynamicReviewOfFriendsCircle1 = dynamicReviewOfFriendsCircleMapper.selectById(dynamicReviewOfFriendsCircle.getDynamicIdOrCommentId());
		dynamicReviewOfFriendsCircle.setOneselfId(dynamicReviewOfFriendsCircle1.getMemberId());

	  }
	  if (StringUtils.isNotBlank(circleOfFriends.getImages()))
	  {
		dynamicReviewOfFriendsCircle.setContextType("1");
	  }
	  else if (StringUtils.isNotBlank(circleOfFriends.getVideo()))
	  {
		dynamicReviewOfFriendsCircle.setContextType("2");
	  }
	  else
	  {
		dynamicReviewOfFriendsCircle.setContextType("0");
	  }

	  // 世界圈发评论要金砖 0朋友圈;1世界圈
	  MemberAssets memberAssets = iMemberAssetsService.selectOne(new EntityWrapper<MemberAssets>().eq("memberId", dynamicReviewOfFriendsCircle.getMemberId()));

	  if ("1".equals(dynamicReviewOfFriendsCircle.getType()) && !circleOfFriends.getMemerId().equals(dynamicReviewOfFriendsCircle.getMemberId()))
	  {
		// Fabulous fabulous = iFabulousService.selectOne(new EntityWrapper<Fabulous>().eq("dynamicId", circleOfFriends.getId()).eq("memberId", dynamicReviewOfFriendsCircle.getMemberId()));
		// if (fabulous == null)
		// {
		//     resultUtils.setMessage("您还没点赞不能评论");
		//     resultUtils.setStatus(Constant.STATUS_FAILED);
		//     return resultUtils;
		// }
		if (memberAssets != null && memberAssets.getMemberDiamondsizeOfGold() < 10)
		{
		    resultUtils.setMessage("您的钻石还不够,请充值");
		    resultUtils.setStatus(Constant.STATUS_FAILED);
		    return resultUtils;
		}

	  }


	  Integer insert = dynamicReviewOfFriendsCircleMapper.insert(dynamicReviewOfFriendsCircle);
	  if (insert <= 0)
	  {
		resultUtils.setMessage("评论失败");
		resultUtils.setStatus(Constant.STATUS_FAILED);
		return resultUtils;
	  }
	  // 评论成功扣钱 必须是世界圈才会走这个逻辑
	  if ("1".equals(dynamicReviewOfFriendsCircle.getType()) && !circleOfFriends.getMemerId().equals(dynamicReviewOfFriendsCircle.getMemberId()))
	  {

	      //=-============================================================================================
		//修改评论人的金额数量 (大概就是这里出现的问题了    --nan)
		memberAssets.setMemberDiamondsizeOfGold(memberAssets.getMemberDiamondsizeOfGold() - 10);
		boolean b = iMemberAssetsService.updateById(memberAssets);
		//如果减少评论金额失败,减去相应金额
		if(!b){
		    //拿到发布动态的用户的id(--nan)
		    MemberAssets memberAssets1 = iMemberAssetsService.selectOne(new EntityWrapper<MemberAssets>().eq("memberId", circleOfFriends.getMemerId()));
		    memberAssets1.setMemberDiamondsizeOfGold(memberAssets.getMemberDiamondsizeOfGold() + 10);
		    boolean c = iMemberAssetsService.updateById(memberAssets1);
		    //如果修改成功 那就是失败了
		    if (!c)
		    {
			  resultUtils.setMessage("评论失败");
			  resultUtils.setStatus(Constant.STATUS_FAILED);
			  return resultUtils;
		    }
		}
		//如果减去金额成功,那么就给推送消息
		if(b){
		    RechargeRecord rechargeRecord = new RechargeRecord();
		    rechargeRecord.setMemberId(member.getId());
		    rechargeRecord.setCurrency(3);
		    rechargeRecord.setRunSize(10L);
		    rechargeRecord.setSurplus(memberAssets.getMemberDiamondsizeOfGold());
		    rechargeRecord.setName("评论收费");
		    rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
		    rechargeRecord.setType(2);
		    rechargeRecord.setMode(4);
		    iRechargeRecordService.insert(rechargeRecord);
		}
		//=-============================================================================================

	      // //修改评论人的金额数量 (大概就是这里出现的问题了    --nan)
		// memberAssets.setMemberDiamondsizeOfGold(memberAssets.getMemberDiamondsizeOfGold() - 10);
		//
		// boolean b = iMemberAssetsService.updateById(memberAssets);
		// RechargeRecord rechargeRecord = new RechargeRecord();
		// rechargeRecord.setMemberId(member.getId());
		// rechargeRecord.setCurrency(3);
		// rechargeRecord.setRunSize(10L);
		// rechargeRecord.setSurplus(memberAssets.getMemberDiamondsizeOfGold());
		// rechargeRecord.setName("评论收费");
		// rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
		// rechargeRecord.setType(2);
		// rechargeRecord.setMode(4);
		// iRechargeRecordService.insert(rechargeRecord);
		//
		// if (!b)
		// {
		//     resultUtils.setMessage("评论失败");
		//     resultUtils.setStatus(Constant.STATUS_FAILED);
		//     return resultUtils;
		// }
		// //拿到发布动态的用户的id(--nan)
		// MemberAssets memberAssets1 = iMemberAssetsService.selectOne(new EntityWrapper<MemberAssets>().eq("memberId", circleOfFriends.getMemerId()));
		// memberAssets1.setMemberDiamondsizeOfGold(memberAssets.getMemberDiamondsizeOfGold() + 10);
		// boolean c = iMemberAssetsService.updateById(memberAssets1);
		// if (!c)
		// {
		//     resultUtils.setMessage("评论失败");
		//     resultUtils.setStatus(Constant.STATUS_FAILED);
		//     return resultUtils;
		// }
	  }
	  //修改评论量（得到动态id） 评论级别 1：直接评论动态2：回复评论
	  if (dynamicReviewOfFriendsCircle.getLevel() == 1)
	  {

		circleOfFriends.setCommentSize(circleOfFriends.getCommentSize() == null ? 1 : circleOfFriends.getCommentSize() + 1);
		Integer integer = circleOfFriendsMapper.updateById(circleOfFriends);
		if (integer <= 0)
		{
		    resultUtils.setMessage("评论失败");
		    resultUtils.setStatus(Constant.STATUS_FAILED);
		    return resultUtils;
		}
		push(dynamicReviewOfFriendsCircle, circleOfFriends.getId() + "", circleOfFriends.getMemerId() + "");
		resultUtils.setMessage("评论成功");
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		return resultUtils;
	  }
	  else
	  {
		DynamicReviewOfFriendsCircle dynamicReviewOfFriendsCircle2 = dynamicReviewOfFriendsCircle;
		Integer memberId = dynamicReviewOfFriendsCircleMapper.selectById(dynamicReviewOfFriendsCircle2.getDynamicIdOrCommentId()).getMemberId();
		boolean isTwo = true;
		while (isTwo)
		{
		    DynamicReviewOfFriendsCircle dynamicReviewOfFriendsCircle1 = dynamicReviewOfFriendsCircleMapper.selectById(dynamicReviewOfFriendsCircle.getDynamicIdOrCommentId());
		    if (dynamicReviewOfFriendsCircle1.getLevel() == 1)
		    {
			  isTwo = false;
			  circleOfFriends.setCommentSize(circleOfFriends.getCommentSize() == null ? 1 : circleOfFriends.getCommentSize() + 1);
			  Integer integer = circleOfFriendsMapper.updateById(circleOfFriends);
			  if (integer <= 0)
			  {
				resultUtils.setMessage("评论失败");
				resultUtils.setStatus(Constant.STATUS_FAILED);
			  }
			  else
			  {
				push(dynamicReviewOfFriendsCircle2, circleOfFriends.getId() + "", memberId + "");
				resultUtils.setMessage("评论成功");
				resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
			  }
		    }
		    else
		    {
			  dynamicReviewOfFriendsCircle = dynamicReviewOfFriendsCircle1;
		    }
		}
		return resultUtils;
	  }
    }

    @Override
    public ResultUtils aopDeleteComments(Integer memberId, Integer commentId, Integer dynamicId, Integer type, Integer category)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  //1.先查询删除的评论
	  if (category == 1)
	  {
		DynamicReviewOfFriendsCircle dynamicReviewOfFriendsCircle = dynamicReviewOfFriendsCircleMapper.selectById(commentId);
		CircleOfFriends circleOfFriends = circleOfFriendsMapper.selectById(dynamicId);
		//1.1先判断这条评论/动态是不是我发的
		Integer memberId1 = dynamicReviewOfFriendsCircle.getMemberId();
		Integer memerId = circleOfFriends.getMemerId();
		if (!memberId.equals(memberId1) && !memberId.equals(memerId))
		{
		    resultUtils.setMessage("您没有删除该评论的权限");
		    resultUtils.setStatus(Constant.STATUS_FAILED);
		    return resultUtils;
		}
		//1.2删除 先删除该评论
		Integer integer = dynamicReviewOfFriendsCircleMapper.deleteById(commentId);
		if (integer <= 0)
		{
		    resultUtils.setStatus(Constant.STATUS_FAILED);
		    resultUtils.setMessage("删除评论失败");
		    return resultUtils;
		}
		//1.2是否删除子评论
		if (type == 1)
		{
		    Integer delete = dynamicReviewOfFriendsCircleMapper.delete(new EntityWrapper<DynamicReviewOfFriendsCircle>().eq("dynamicIdOrCommentId", commentId).eq("level", 2));
		    if (delete > 0)
		    {
			  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
			  resultUtils.setMessage("删除评论成功");
			  return resultUtils;
		    }
		}
		//2.不删除
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setMessage("删除评论成功");
		return resultUtils;

	  }
	  else
	  {
		DynamicCommentsOfCarUsers dynamicCommentsOfCarUsers = dynamicCommentsOfCarUsersMapper.selectById(commentId);
		RiderDynamics riderDynamics = riderDynamicsMapper.selectById(dynamicId);
		//1.1先判断这条评论/动态是不是我发的
		Integer memberId1 = dynamicCommentsOfCarUsers.getMemberId();
		Integer memerId = riderDynamics.getMemerId();
		if (!memberId.equals(memberId1) && !memberId.equals(memerId))
		{
		    resultUtils.setMessage("您没有删除该评论的权限");
		    resultUtils.setStatus(Constant.STATUS_FAILED);
		    return resultUtils;
		}
		//1.2删除 先删除该评论
		Integer integer = dynamicCommentsOfCarUsersMapper.deleteById(commentId);
		if (integer <= 0)
		{
		    resultUtils.setStatus(Constant.STATUS_FAILED);
		    resultUtils.setMessage("删除评论失败");
		    return resultUtils;
		}
		//1.2是否删除子评论
		if (type == 1)
		{
		    Integer delete = dynamicCommentsOfCarUsersMapper.delete(new EntityWrapper<DynamicCommentsOfCarUsers>().eq("dynamicIdOrCommentId", commentId).eq("level", 2));
		    if (delete > 0)
		    {
			  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
			  resultUtils.setMessage("删除评论成功");
			  return resultUtils;
		    }
		}
		//2.不删除
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setMessage("删除评论成功");
		return resultUtils;

	  }
    }

    @Override
    public ResultUtils MyDynamicList(DynamicVo dynamicVo)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  resultUtils.setData(new ArrayList<DynamicVo>());
	  List<DynamicVo> list = new ArrayList<>();

	  // 评论
	  EntityWrapper<DynamicReviewOfFriendsCircle> wrapper = new EntityWrapper<>();
	  wrapper.orderBy("id", false).eq("delFlag", "0").eq("oneself_id", dynamicVo.getOneselfId()).or().eq("memberId", dynamicVo.getOneselfId()).eq("delFlag", "0");
	  List<DynamicReviewOfFriendsCircle> dynamicReviewOfFriendsCircles = dynamicReviewOfFriendsCircleMapper.selectList(wrapper);
	  System.out.println("dynamicReviewOfFriendsCircles+" + dynamicReviewOfFriendsCircles);
	  if (dynamicReviewOfFriendsCircles != null && dynamicReviewOfFriendsCircles.size() > 0)
	  {
		List<Integer> collect1 = dynamicReviewOfFriendsCircles.stream().map(DynamicReviewOfFriendsCircle::getDynamicId).collect(Collectors.toList());
		List<CircleOfFriends> circleOfFriends = circleOfFriendsMapper.selectList(new EntityWrapper<CircleOfFriends>().in("id", collect1));
		List<Integer> collect = dynamicReviewOfFriendsCircles.stream().map(DynamicReviewOfFriendsCircle::getOneselfId).collect(Collectors.toList());
		List<Member> members = memberMapper.selectList(new EntityWrapper<Member>().in("id", collect));
		for (int i = 0; i < dynamicReviewOfFriendsCircles.size(); i++)
		{
		    Integer dynamicIdOrCommentId = dynamicReviewOfFriendsCircles.get(i).getDynamicId();
		    String contextType = dynamicReviewOfFriendsCircles.get(i).getContextType();
		    DynamicVo dynamicVo1 = new DynamicVo();
		    dynamicVo1.setLevel(dynamicReviewOfFriendsCircles.get(i).getLevel());
		    for (Member member : members)
		    {
			  if (dynamicReviewOfFriendsCircles.get(i).getOneselfId().equals(member.getId()))
			  {
				dynamicVo1.setOtherMemberNickName(member.getNickName());
			  }
		    }

		    dynamicVo1.setMemberHead(dynamicReviewOfFriendsCircles.get(i).getMemberHead());
		    dynamicVo1.setMemberNickName(dynamicReviewOfFriendsCircles.get(i).getMemberNickName());
		    dynamicVo1.setOneselfId(dynamicReviewOfFriendsCircles.get(i).getOneselfId());
		    dynamicVo1.setMemberId(dynamicReviewOfFriendsCircles.get(i).getMemberId());
		    dynamicVo1.setCommentContext(dynamicReviewOfFriendsCircles.get(i).getContext());
		    dynamicVo1.setAddTime(dynamicReviewOfFriendsCircles.get(i).getAddTime());
		    dynamicVo1.setCircleOfFriendsId(dynamicIdOrCommentId);
		    dynamicVo1.setContextType(contextType);
		    dynamicVo1.setType("0");
		    for (CircleOfFriends circleOfFriend : circleOfFriends)
		    {
			  if (circleOfFriend.getId().equals(dynamicIdOrCommentId))
			  {
				System.out.println(dynamicIdOrCommentId);
				dynamicVo1.setContext(circleOfFriend.getContext());
				dynamicVo1.setImages(circleOfFriend.getImages());
				dynamicVo1.setVideo(circleOfFriend.getVideo());
			  }

		    }
		    list.add(dynamicVo1);
		}
	  }


	  // 点赞
	  EntityWrapper<Fabulous> fabulousEntityWrapper = new EntityWrapper<>();
	  fabulousEntityWrapper.orderBy("fabulousId", false).eq("oneself_id", dynamicVo.getOneselfId()).eq("delFlag", "0");
	  List<Fabulous> fabulous = fabulousMapper.selectList(fabulousEntityWrapper);
	  System.out.println("fabulous+" + fabulous);

	  if (fabulous != null && fabulous.size() > 0)
	  {
		List<Integer> collect = fabulous.stream().map(Fabulous::getDynamicId).collect(Collectors.toList());
		List<CircleOfFriends> circleOfFriends = circleOfFriendsMapper.selectList(new EntityWrapper<CircleOfFriends>().in("id", collect));
		for (int i = 0; i < fabulous.size(); i++)
		{
		    Integer dynamicIdOrCommentId = fabulous.get(i).getDynamicId();
		    DynamicVo dynamicVo1 = new DynamicVo();
		    dynamicVo1.setMemberHead(fabulous.get(i).getMemberHead());
		    dynamicVo1.setMemberNickName(fabulous.get(i).getMemberNickName());
		    dynamicVo1.setOneselfId(fabulous.get(i).getOneselfId());
		    dynamicVo1.setCommentContext("");
		    dynamicVo1.setMemberId(fabulous.get(i).getMemberId());
		    dynamicVo1.setCircleOfFriendsId(dynamicIdOrCommentId);
		    dynamicVo1.setContextType(fabulous.get(i).getContextType());
		    dynamicVo1.setAddTime(fabulous.get(i).getAddTime());
		    dynamicVo1.setType("1");
		    for (CircleOfFriends circleOfFriend : circleOfFriends)
		    {
			  if (circleOfFriend.getId().equals(dynamicIdOrCommentId))
			  {
				dynamicVo1.setContext(circleOfFriend.getContext());
				dynamicVo1.setImages(circleOfFriend.getImages());
				dynamicVo1.setVideo(circleOfFriend.getVideo());
			  }
		    }
		    list.add(dynamicVo1);
		}
	  }


	  for (DynamicReviewOfFriendsCircle dynamicReviewOfFriendsCircle : dynamicReviewOfFriendsCircles)
	  {
		if (StringUtils.isEmpty(dynamicReviewOfFriendsCircle.getReadMembers()))
		{
		    dynamicReviewOfFriendsCircle.setReadMembers(dynamicVo.getOneselfId().toString());
		}
		else if (!dynamicReviewOfFriendsCircle.getReadMembers().contains(dynamicVo.getOneselfId().toString()))
		{
		    dynamicReviewOfFriendsCircle.setReadMembers(dynamicReviewOfFriendsCircle.getReadMembers() + "," + dynamicVo.getOneselfId());
		}
	  }
	  if (dynamicReviewOfFriendsCircles != null && dynamicReviewOfFriendsCircles.size() > 0)
	  {
		updateBatchById(dynamicReviewOfFriendsCircles);
	  }

	  if (list.size() == 0)
		return resultUtils;
	  List<DynamicVo> collect = list.stream().sorted(Comparator.comparing(DynamicVo::getAddTime).reversed()).collect(Collectors.toList());
	  resultUtils.setData(collect);
	  return resultUtils;
    }

    @Override
    public ResultUtils emptyMyDynamicList(DynamicVo dynamicVo)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  boolean b = false;
	  // 评论
	  EntityWrapper<DynamicReviewOfFriendsCircle> wrapper = new EntityWrapper<>();
	  wrapper.orderBy("id", false).eq("delFlag", "0").eq("oneself_id", dynamicVo.getOneselfId())
			.or().eq("memberId", dynamicVo.getOneselfId()).eq("delFlag", "0");
	  List<DynamicReviewOfFriendsCircle> dynamicReviewOfFriendsCircles = dynamicReviewOfFriendsCircleMapper.selectList(wrapper);
	  // 点赞
	  EntityWrapper<Fabulous> fabulousEntityWrapper = new EntityWrapper<>();
	  fabulousEntityWrapper.orderBy("fabulousId", false).eq("oneself_id", dynamicVo.getOneselfId()).eq("delFlag", "0");
	  List<Fabulous> fabulous = fabulousMapper.selectList(fabulousEntityWrapper);
	  if (fabulous.size() == 0 && dynamicReviewOfFriendsCircles.size() == 0)
	  {
		resultUtils.setMessage("没有可清空的数据");
		return resultUtils;

	  }
	  System.out.println(dynamicReviewOfFriendsCircles.size());

	  for (DynamicReviewOfFriendsCircle dynamicReviewOfFriendsCircle : dynamicReviewOfFriendsCircles)
	  {
		System.out.println(dynamicReviewOfFriendsCircle);
	  }
	  if (dynamicReviewOfFriendsCircles != null && dynamicReviewOfFriendsCircles.size() > 0)
	  {
		for (DynamicReviewOfFriendsCircle dynamicReviewOfFriendsCircle : dynamicReviewOfFriendsCircles)
		{
		    dynamicReviewOfFriendsCircle.setDelFlag("1");
		}
		b = updateBatchById(dynamicReviewOfFriendsCircles);
	  }
	  if (fabulous != null && fabulous.size() > 0)
	  {
		for (Fabulous fabulous1 : fabulous)
		{
		    fabulous1.setDelFlag("1");
		}
		b = iFabulousService.updateBatchById(fabulous);
	  }
	  System.out.println(fabulous.size());

	  if (b)
		return resultUtils;
	  resultUtils.setStatus(500);
	  resultUtils.setMessage("操作失败");
	  return resultUtils;
    }

    @Override
    public ResultUtils isApply(Integer memberId)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setStatus(200);
	  resultUtils.setMessage("操作成功");
	  resultUtils.setData(0);
	  EntityWrapper<DynamicReviewOfFriendsCircle> wrapper = new EntityWrapper<>();
	  wrapper.orderBy("id", false).eq("delFlag", "0").eq("oneself_id", memberId).or().eq("memberId", memberId);

	  List<DynamicReviewOfFriendsCircle> dynamicReviewOfFriendsCircles = dynamicReviewOfFriendsCircleMapper.selectList(wrapper);
	  if (dynamicReviewOfFriendsCircles == null || dynamicReviewOfFriendsCircles.size() == 0)
		return resultUtils;
	  String readMembers = dynamicReviewOfFriendsCircles.get(0).getReadMembers();
	  if (StringUtils.isNotBlank(readMembers) && !readMembers.contains(memberId.toString()))
	  {
		resultUtils.setData(1);
	  }
	  if (StringUtils.isEmpty(readMembers))
	  {
		resultUtils.setData(1);
	  }
	  return resultUtils;
    }


    public void push(DynamicReviewOfFriendsCircle dynamicReviewOfFriendsCircle, String dongtaiId, String pushId)
    {
	  String str = "";
	  //评论级别
	  if (dynamicReviewOfFriendsCircle.getLevel() == 1)
	  {
		str += dynamicReviewOfFriendsCircle.getMemberNickName() + "评论了您的动态";

	  }
	  else
	  {
		str += dynamicReviewOfFriendsCircle.getMemberNickName() + "回复了您的评论";
	  }
	  String s = dynamicReviewOfFriendsCircle.getContext();
	  List<MembershipSettings> membershipSettingsList = membershipSettingsMapper.selectList(new EntityWrapper<MembershipSettings>().eq("memberId", pushId));
	  MembershipSettings membershipSettings = membershipSettingsList.get(0);
	  if (membershipSettings.getDynamicResponseState() == 1)
	  {
		Member member = memberMapper.selectById(pushId);
		Map<String, String> xtrasparams = new HashMap<>(); //扩展字段
		xtrasparams.put("id", dongtaiId);
		xtrasparams.put("type", 2 + "");
		if (member.getPushId() == null)
		{

		}
		else
		{
		    JpushUtil.sendToRegistrationId(member.getPushId(), str, s, str, xtrasparams);
		}
	  }
    }
}
