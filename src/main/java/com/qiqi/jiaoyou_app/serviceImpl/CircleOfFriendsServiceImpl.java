package com.qiqi.jiaoyou_app.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.Instant.NewFriendList;
import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.constants.PathParam;
import com.qiqi.jiaoyou_app.mapper.*;
import com.qiqi.jiaoyou_app.pojo.*;
import com.qiqi.jiaoyou_app.service.ICircleOfFriendsService;
import com.qiqi.jiaoyou_app.support.DunImagesDo;
import com.qiqi.jiaoyou_app.support.DunUtils;
import com.qiqi.jiaoyou_app.util.DateUtils;
import com.qiqi.jiaoyou_app.util.LocationUtils;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import com.qiqi.jiaoyou_app.util.UserDataItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 朋友圈动态 服务实现类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@SuppressWarnings("ALL")
@Service
public class CircleOfFriendsServiceImpl extends ServiceImpl<CircleOfFriendsMapper, CircleOfFriends> implements ICircleOfFriendsService
{

	@Autowired
	private CircleOfFriendsMapper circleOfFriendsMapper;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private DynamicReviewOfFriendsCircleMapper dynamicReviewOfFriendsCircleMapper;
	@Autowired
	private GoodFriendMapper goodFriendMapper;
	@Autowired
	private RiderDynamicsMapper riderDynamicsMapper;
	@Autowired
	private FabulousMapper fabulousMapper;
	@Autowired
	private MembershipSettingsMapper membershipSettingsMapper;

	@Override
	public ResultUtils dynamicPublishing(CircleOfFriends circleOfFriends)
	{
		ResultUtils resultUtils = new ResultUtils();
		if (StringUtils.isNotBlank(circleOfFriends.getVideo()))
		{
			String s = DunUtils.detectionVideoDun(circleOfFriends.getVideo());


		}
		if (StringUtils.isNotBlank(circleOfFriends.getContext()))
		{
			String s = DunUtils.detectionTextDun(circleOfFriends.getContext());
			switch (s)
			{

				case "1":
					resultUtils.setStatus(500);
					resultUtils.setMessage("文字不合法");
					return resultUtils;
				case "2":
					resultUtils.setStatus(500);
					resultUtils.setMessage("文字不合法");
					return resultUtils;
			}
		}
		if (StringUtils.isNotBlank(circleOfFriends.getImages()))
		{
			String images = circleOfFriends.getImages();

			String[] split = images.split(",");
			List<DunImagesDo> dunImagesDos = new ArrayList<>();
			for (String s : split)
			{
				DunImagesDo dunImagesDo = new DunImagesDo();
				dunImagesDo.setType(1);
				dunImagesDo.setName(s);
				dunImagesDo.setData(PathParam.onlinepath + s);
			}
			String s = DunUtils.detectionImageDun(dunImagesDos);
			switch (s)
			{
				case "1":
					resultUtils.setStatus(500);
					resultUtils.setMessage("图片不合法");
					return resultUtils;
				case "2":
					resultUtils.setStatus(500);
					resultUtils.setMessage("图片不合法");
					return resultUtils;
			}

		}
		//会员资料
		Member member = memberMapper.selectById(circleOfFriends.getMemerId());
		circleOfFriends.setMemberHead(member.getHead());
		circleOfFriends.setMemberNickName(member.getNickName());
		circleOfFriends.setMemberSex(member.getSex());
		circleOfFriends.setMemberAge(member.getAge());
		//发布时间
		circleOfFriends.setAddTime(new Timestamp(System.currentTimeMillis()));
		//点赞量
		circleOfFriends.setLikeSize(0);
		//评论量
		circleOfFriends.setCommentSize(0);
		circleOfFriends.setLable(member.getCarLable());

		//动态类型
		circleOfFriends.setDefaultType(2);
		if (circleOfFriends.getCity() == null || "请选择".equals(circleOfFriends.getCity()) || "".equals(circleOfFriends.getCity()))
		{
			circleOfFriends.setCity(member.getCity());
			circleOfFriends.setState(1);
		}
		else
		{
			circleOfFriends.setState(2);
		}
		//插入
		Integer insert = circleOfFriendsMapper.insert(circleOfFriends);
		boolean a = true;
		if (circleOfFriends.getCitySynchronization() == 1)
		{
			RiderDynamics riderDynamics = new RiderDynamics();
			riderDynamics.setMemerId(circleOfFriends.getMemerId());
			riderDynamics.setContext(circleOfFriends.getContext());
			riderDynamics.setImages(circleOfFriends.getImages());
			riderDynamics.setVideo(circleOfFriends.getVideo());
			riderDynamics.setStrangersInTheSameCity(circleOfFriends.getStrangersInTheSameCity());
			riderDynamics.setStrangersOutsideTheCity(circleOfFriends.getStrangersOutsideTheCity());
			riderDynamics.setCitySynchronization(circleOfFriends.getCitySynchronization());
			riderDynamics.setAddTime(circleOfFriends.getAddTime());
			riderDynamics.setLikeSize(0);
			riderDynamics.setCommentSize(0);
			riderDynamics.setMemberHead(member.getHead());
			riderDynamics.setMemberNickName(member.getNickName());
			riderDynamics.setMemberSex(member.getSex());
			riderDynamics.setMemberAge(member.getAge());
			riderDynamics.setCity(circleOfFriends.getCity());
			riderDynamics.setState(circleOfFriends.getState());
			riderDynamics.setLable(member.getCarLable());
			Integer insert1 = riderDynamicsMapper.insert(riderDynamics);
			if (insert1 <= 0)
			{
				a = false;
			}
		}
		if (insert > 0 && a)
		{
			resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
			resultUtils.setMessage("动态发布成功");
		}
		else
		{
			resultUtils.setStatus(Constant.STATUS_FAILED);
			resultUtils.setMessage("动态发布失败");
		}
		return resultUtils;
	}

	@Override
	public ResultUtils myCircleOfFriendsList(Integer pageSize, Integer pageNum, Integer id)
	{
		//我的动态列表：好友头像，昵称，性别，年龄，所在城市，标题，发布时间，部分动态内容，点赞量，评论量，转发
		ResultUtils resultUtils = new ResultUtils();
		//查询出会员信息
		Member member = memberMapper.selectById(id);
		//查询出该会员下的列表（分页）
		Page page = new Page(pageNum, pageSize);
		page.setOptimizeCountSql(true);
		page.setSearchCount(true);
		//查询并set进member
		List<CircleOfFriends> circleOfFriendsList = circleOfFriendsMapper.selectPage(page, new EntityWrapper<CircleOfFriends>().eq("memerId", id).orderBy("addTime", false));
		for (CircleOfFriends circleOfFriends : circleOfFriendsList)
		{
			circleOfFriends.setAddTimeStr(DateUtils.getShortTime(circleOfFriends.getAddTime()));
		}
		member.setCircleOfFriendsList(circleOfFriendsList);
		//设置返回值
		resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setData(member);
		resultUtils.setCount((int) page.getTotal());
		resultUtils.setData1(page);
		return resultUtils;
	}

	@Override
	public ResultUtils dynamicDetails(Integer pageSize, Integer pageNum, Integer id, String token, Integer memberId)
	{
		//动态详情：用户头像，昵称，性别，年龄，所在城市，标题，发布时间，动态内容，点赞量，评论量；评论列表：用户头像，昵称，评论时间，评论内容（文字），操作（发表评论，转发）
		ResultUtils resultUtils = new ResultUtils();
		//获取到该动态
		CircleOfFriends circleOfFriends = circleOfFriendsMapper.selectById(id);
		if (circleOfFriends == null)
		{
			resultUtils.setMessage("暂未查询到相关的动态信息");
			resultUtils.setStatus(Constant.STATUS_FAILED);
			return resultUtils;
		}
		List<UserDataItem> newFriendList = NewFriendList.friend_get(memberId.toString());
		Integer[] memberIds = null;
		if (newFriendList == null || newFriendList.size() <= 0)
		{
			memberIds = new Integer[1];
			memberIds[0] = id;
		}
		else
		{
			//好友ID数组
			memberIds = new Integer[newFriendList.size() + 1];
			memberIds[0] = id;
			for (int i = 0; i < newFriendList.size(); i++)
			{
				memberIds[i + 1] = Integer.valueOf(newFriendList.get(i).getTo_Account());
			}
		}
		//获取到该动态的评论内容(分页)
		Page page = new Page(pageNum, pageSize);
		page.setOptimizeCountSql(true);
		page.setSearchCount(true);
		//查询出评论
		List<DynamicReviewOfFriendsCircle> dynamicReviewOfFriendsCircles1 = dynamicReviewOfFriendsCircleMapper.dynamicDetails(id);
		List<DynamicReviewOfFriendsCircle> dynamicReviewOfFriendsCircleList = dynamicReviewOfFriendsCircles1.stream().filter(Objects::nonNull).collect(Collectors.toList());
		circleOfFriends.setDynamicReviewOfFriendsCircleList(dynamicReviewOfFriendsCircleList);
		List<DynamicReviewOfFriendsCircle> dynamicReviewOfFriendsCircles = new ArrayList<>();
		for (DynamicReviewOfFriendsCircle dynamicReviewOfFriendsCircle : dynamicReviewOfFriendsCircles)
		{
			for (Integer integer : memberIds)
			{
				if (integer.equals(dynamicReviewOfFriendsCircle.getMemberId()))
				{
					dynamicReviewOfFriendsCircles.add(dynamicReviewOfFriendsCircle);
				}
			}
		}

		//查询点赞数组
		List<Fabulous> fabulous = fabulousMapper.selectList(new EntityWrapper<Fabulous>().eq("dynamicId", circleOfFriends.getId()).eq("type", 1));
		if (fabulous.size() > 0)
		{
			List<Integer> collect = fabulous.stream().map(Fabulous::getMemberId).collect(Collectors.toList());
			List<Member> members = memberMapper.selectList(new EntityWrapper<Member>().in("id", collect));
			circleOfFriends.setList(members);
			if (collect.contains(memberId))
			{
				circleOfFriends.setIsLike(1);
			}
		}
		// 日期格式化 距离今日
		circleOfFriends.setAddTimeStr(DateUtils.getShortTime(circleOfFriends.getAddTime()));

		// 设置距离
		Member member = memberMapper.selectById(memberId);
		Member member1 = memberMapper.selectById(circleOfFriends.getMemerId());
		if (member != null && member1 != null)
		{
			if (member1.getId().equals(circleOfFriends.getMemerId()))
			{
				if (member.getLongitude() == null || "".equals(member.getLongitude()) || member.getLatitude() == null || "".equals(member.getLatitude()) || member1.getLongitude() == null || "".equals(member1.getLongitude()) || member1.getLatitude() == null || "".equals(member1.getLatitude()))
				{
					circleOfFriends.setDistance(Constant.UNKNOWN);
				}
				else
				{
					double distance = LocationUtils.getDistance(member.getLongitude(), member.getLatitude(), member1.getLongitude(), member1.getLatitude());
					circleOfFriends.setDistance(new BigDecimal(distance).divide(new BigDecimal(1000), 1, BigDecimal.ROUND_HALF_DOWN) + "km");
				}
			}

		}


		//设置返回值
		resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setData(circleOfFriends);
		resultUtils.setCount((int) page.getTotal());
		resultUtils.setData1(page);
		return resultUtils;
	}

	@Override
	public ResultUtils friendsOfFriendsList(Integer pageSize, Integer pageNum, Integer id)
	{
		//好友动态列表：好友头像，昵称，性别，年龄，所在城市，标题，发布时间，部分动态内容，点赞量，评论量，转发
		ResultUtils resultUtils = new ResultUtils();
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
		resultUtils.setData(new ArrayList<>());
		//查询该会员的好友
		// GroupJson groupJson = new GroupJson();
		// groupJson.setFrom_Account(id.toString());
		List<UserDataItem> newFriendList = NewFriendList.friend_get(id.toString());
		Integer[] integers = null;
		if (newFriendList == null || newFriendList.size() <= 0)
		{
			integers = new Integer[1];
			integers[0] = id;
		}
		else
		{
			//好友ID数组
			integers = new Integer[newFriendList.size() + 1];
			integers[0] = id;
			for (int i = 0; i < newFriendList.size(); i++)
			{
				integers[i + 1] = Integer.valueOf(newFriendList.get(i).getTo_Account());
			}
		}

		//获取到自己的城市
		Member member = memberMapper.selectById(id);
        /*String city = member.getCity();
        List<Member> city1 = memberMapper.selectList(new EntityWrapper<Member>().eq("enableSate", 1).eq("examineState", 1).eq("city", city));
        String str = "";
        for (int i = 0; i < city1.size();i++){
            if ( i == city1.size() - 1){
                str += city1.get(i).getId();
            }else {
                str += city1.get(i).getId() +",";
            }
        }
        List<Member> city2 = memberMapper.selectList(new EntityWrapper<Member>().eq("enableSate", 2).or().eq("examineState", 2).ne("city", city));
        String str2 = "";
        for (int i = 0; i < city2.size();i++){
            if ( i == city2.size() - 1){
                str2 += city2.get(i).getId();
            }else {
                str2 += city2.get(i).getId() +",";
            }
        }
*/
		// List<Member> list = memberMapper.selectList(new EntityWrapper<Member>().in("id", integers).eq("enableSate", 1).eq("examineState", 1));

		// List<Integer> collect = list.stream().map(Member::getId).collect(Collectors.toList());
		//分页
		Page page = new Page(pageNum, pageSize);
		page.setOptimizeCountSql(true);
		page.setSearchCount(true);
		for (Integer integer : integers)
		{
			System.out.println(integer);
		}
		List<CircleOfFriends> circleOfFriendsList = circleOfFriendsMapper.selectPage(page, new EntityWrapper<CircleOfFriends>().in("memerId", integers).orderBy("id", false));

		if (circleOfFriendsList == null || circleOfFriendsList.size() == 0)
			return resultUtils;
		List<Member> members = memberMapper.selectList(null);
		for (CircleOfFriends circleOfFriends : circleOfFriendsList)
		{

			for (Member member1 : members)
			{
				if (member1.getId().equals(circleOfFriends.getMemerId()))
				{
					if (member.getLongitude() == null || "".equals(member.getLongitude()) || member.getLatitude() == null || "".equals(member.getLatitude()) || member1.getLongitude() == null || "".equals(member1.getLongitude()) || member1.getLatitude() == null || "".equals(member1.getLatitude()))
					{
						circleOfFriends.setDistance(Constant.UNKNOWN);
					}
					else
					{
						double distance = LocationUtils.getDistance(member.getLongitude(), member.getLatitude(), member1.getLongitude(), member1.getLatitude());
						circleOfFriends.setDistance(new BigDecimal(distance).divide(new BigDecimal(1000), 1, BigDecimal.ROUND_HALF_DOWN) + "km");
					}
				}
			}
			circleOfFriends.setAddTimeStr(DateUtils.getShortTime(circleOfFriends.getAddTime()));
		}
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
		resultUtils.setData(circleOfFriendsList);
		resultUtils.setCount((int) page.getTotal());
		resultUtils.setData1(page);
		return resultUtils;
	}

	@Override
	public ResultUtils del(Integer id)
	{
		ResultUtils resultUtils = new ResultUtils();
		CircleOfFriends circleOfFriends = circleOfFriendsMapper.selectById(id);
		if (circleOfFriends.getDefaultType() == null || circleOfFriends.getDefaultType() == 2)
		{
			Integer integer = circleOfFriendsMapper.deleteById(id);
			if (integer <= 0)
			{
				resultUtils.setMessage(Constant.DELETION_FAILED);
				resultUtils.setStatus(Constant.STATUS_FAILED);
				return resultUtils;
			}
			resultUtils.setMessage(Constant.DELETION_SUCCEEDED);
			resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
			return resultUtils;
		}
		else
		{
			resultUtils.setMessage("默认动态不可删除");
			resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
			return resultUtils;
		}
	}

	/*个人主页信息的查看就在这里,没得错了
	 * author:nan
	 * date:20210112
	 * 狗屎的,就是这里了,我来说一下狗屎的代码逻辑
	 * 用户两者要是会员关系的话,可以直接查看动态信息
	 * 然后便是两种的权限分类了,一个是全局的动态权限,一个是单条的动态权限,单条的服从全局的
	 * 分为允许同城陌生人查看 允许同城以外的陌生人查看
	 * 上面两行自动忽略掉
	 * ==================================================================
	 * ==我的动态设置的全新啊只应用于个人主页,也就是世界频道点击头像进入的个人主页部分=
	 * 圈子里设置的动态,是用来管理世界圈的朋友圈动态权限问题=======================
	 * ===================================================================
	 * */
	@Override
	public ResultUtils memberDetailFriendsList(Integer pageSize, Integer pageNum, Integer memberId, Integer beiMemberId)
	{
		ResultUtils resultUtils = new ResultUtils();
		Page<CircleOfFriends> circleOfFriendsPage = new Page<>(pageNum, pageSize);
		List<CircleOfFriends> circleOfFriends = circleOfFriendsMapper.selectPage(circleOfFriendsPage, new EntityWrapper<CircleOfFriends>().eq("memerId", beiMemberId).orderBy("id", false));
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
		resultUtils.setData(circleOfFriends);
		resultUtils.setData1(circleOfFriendsPage);
		// 自己看
		if (memberId.equals(beiMemberId))
			return resultUtils;

		Member member = memberMapper.selectById(memberId);
		Member member1 = memberMapper.selectById(beiMemberId);
		//判断是否是好友
		boolean isFriend = false;
		List<UserDataItem> newFriendList = NewFriendList.friend_get(memberId.toString());
		if (newFriendList != null && newFriendList.size() > 0)
		{
			List<String> collect = newFriendList.stream().map(UserDataItem::getTo_Account).collect(Collectors.toList());
			List<Integer> integer = collect.stream().map(Integer::parseInt).collect(Collectors.toList());
			if (integer.contains(beiMemberId))
			{
				isFriend = true;
			}
		}
		//如果是好友,直接查看信息
		if (isFriend)
			return resultUtils;

		//如果不是好友,直接判断是不是同城
		boolean isCity = false;
		if (StringUtils.isNotBlank(member.getCity()) && StringUtils.isNotBlank(member1.getCity()))
		{
			if (member.getCity().equals(member1.getCity()))
			{
				isCity = true;
			}
		}
		// 同城以内陌生人查看
		if (isCity)
		{
			List<CircleOfFriends> circleOfFriends1 = circleOfFriendsMapper.selectPage(circleOfFriendsPage, new EntityWrapper<CircleOfFriends>().eq("strangersInTheSameCity",1).eq("memerId", beiMemberId).orderBy("id", false));
			resultUtils.setData(circleOfFriends);
			return resultUtils;
		}
		// 同城以外陌生人查看
		List<CircleOfFriends> circleOfFriendsList = circleOfFriendsMapper.selectPage(circleOfFriendsPage, new EntityWrapper<CircleOfFriends>().eq("strangersOutsideTheCity",1).eq("memerId", beiMemberId).orderBy("id", false));
		resultUtils.setData(circleOfFriendsList);
		return resultUtils;
		/*boolean isCity = false;
		if (StringUtils.isNotBlank(member.getCity()) && StringUtils.isNotBlank(member1.getCity()))
		{
			if (member.getCity().equals(member1.getCity()))
			{
				isCity = true;
			}
		}

		List<MembershipSettings> membershipSettings = membershipSettingsMapper.selectList(new EntityWrapper<MembershipSettings>().eq("memberId", beiMemberId));
		//不允许所有人查看
		if (membershipSettings.get(0).getSameCityWithinState() == 2 && membershipSettings.get(0).getSameCityExternalState() == 2)
		{
			resultUtils.setData(null);
			return resultUtils;
		}
		//允许所有人查看
		else if (membershipSettings.get(0).getSameCityWithinState() == 1 && membershipSettings.get(0).getSameCityExternalState() == 1)
		{
			Page<CircleOfFriends> circleOfFriendsPage = new Page<>(pageNum, pageSize);
			List<CircleOfFriends> circleOfFriends = circleOfFriendsMapper.selectPage(circleOfFriendsPage, new EntityWrapper<CircleOfFriends>().eq("memerId", beiMemberId).eq("strangersOutsideTheCity", 1).orderBy("id", false));
			resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
			resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
			resultUtils.setData(circleOfFriends);
			resultUtils.setData1(circleOfFriendsPage);
			return resultUtils;
		}
		//同城以内陌生人查看
		else if (isCity && membershipSettings.get(0).getSameCityWithinState() == 1)
		{
			//同城内可查看 1：是2：否 是同城 并且同城权限可以查看
			Page<CircleOfFriends> circleOfFriendsPage = new Page<>(pageNum, pageSize);
			List<CircleOfFriends> circleOfFriends = circleOfFriendsMapper.selectPage(circleOfFriendsPage, new EntityWrapper<CircleOfFriends>().eq("memerId", beiMemberId).eq("strangersInTheSameCity", 1).orderBy("id", false));
			resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
			resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
			resultUtils.setData(circleOfFriends);
			resultUtils.setData1(circleOfFriendsPage);
			return resultUtils;
		}

		//同城以外陌生人查看
		//如果不是同城,并且设置允许同城以外的人查看 同城外可查看 1：是2：否
		else if (isCity == false && membershipSettings.get(0).getSameCityExternalState() == 1)
		{
			Page<CircleOfFriends> circleOfFriendsPage = new Page<>(pageNum, pageSize);
			List<CircleOfFriends> circleOfFriends = circleOfFriendsMapper.selectPage(circleOfFriendsPage, new EntityWrapper<CircleOfFriends>().eq("memerId", beiMemberId).eq("strangersOutsideTheCity", 1).orderBy("id", false));
			resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
			resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
			resultUtils.setData(circleOfFriends);
			resultUtils.setData1(circleOfFriendsPage);
			return resultUtils;
			//不允许所有人看
		}
		resultUtils.setData(null);*/
		// return resultUtils;

	}

	/***
	 * @Description:世界圈列表信息展示,欲知后事如何,请自己杜撰
	 * @param: null
	 * @return:
	 * @Author: nan
	 * @Date: 2021-01-13 10:31
	 */
	@Override
	public ResultUtils worldCircleOfFriendsList(Integer pageSize, Integer pageNum, Integer id)
	{
		//我的动态列表：好友头像，昵称，性别，年龄，所在城市，标题，发布时间，部分动态内容，点赞量，评论量，转发
		ResultUtils resultUtils = new ResultUtils();
		resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setData(new ArrayList<>());
		resultUtils.setCount(0);
		resultUtils.setPages(0);
		//查询出该会员下的列表（分页）
		Page page = new Page(pageNum, pageSize);
		page.setOptimizeCountSql(true);
		page.setSearchCount(true);

		// 查询出所有同步到世界圈的动态信息
		EntityWrapper<CircleOfFriends> wrapper = new EntityWrapper<>();
		wrapper.eq("citySynchronization", "1").orderBy("addTime", false);
		Page addTime = selectPage(page, wrapper);
		//返回的数组信息
		// List<CircleOfFriends> circleOfFriendsList = null;

		//用来放得到的数组信息
		List<CircleOfFriends> circleOfFriendsList = addTime.getRecords();
		//查询刷世界圈的这个会员的id值
		List<Member> members = memberMapper.selectList(null);

		if (circleOfFriendsList == null || circleOfFriendsList.size() == 0)
			return resultUtils;

		Member member = memberMapper.selectById(id);
		String city = member.getCity();
		//主要用来设置距离信息


		for (CircleOfFriends circleOfFriends : circleOfFriendsList)
		{

			for (Member member1 : members)
			{
				//如果查询的会员信息和朋友圈信息是同一个人发的话
				if (member1.getId().equals(circleOfFriends.getMemerId()))
				{
					//设置距离信息,经纬度是空的话,设置距离不详
					if (member.getLongitude() == null || "".equals(member.getLongitude()) || member.getLatitude() == null || "".equals(member.getLatitude()) || member1.getLongitude() == null || "".equals(member1.getLongitude()) || member1.getLatitude() == null || "".equals(member1.getLatitude()))
					{
						circleOfFriends.setDistance(Constant.UNKNOWN);
					}//否则就设置距离信息
					else
					{
						double distance = LocationUtils.getDistance(member.getLongitude(), member.getLatitude(), member1.getLongitude(), member1.getLatitude());
						circleOfFriends.setDistance(new BigDecimal(distance).divide(new BigDecimal(1000), 1, BigDecimal.ROUND_HALF_DOWN) + "km");
					}
				}
			}
			circleOfFriends.setAddTimeStr(DateUtils.getShortTime(circleOfFriends.getAddTime()));
			//设置已读会员数组消息
			if (StringUtils.isEmpty(circleOfFriends.getReadMembers()))
			{
				circleOfFriends.setReadMembers(id.toString());
			}
			//如果现有的会员数据不包含这个用户的id,那么加进去
			else if (!circleOfFriends.getReadMembers().contains(id.toString()))
			{
				circleOfFriends.setReadMembers(circleOfFriends.getReadMembers() + "," + id);
			}
		}

		if (circleOfFriendsList != null && circleOfFriendsList.size() > 0)
		{
			updateBatchById(circleOfFriendsList);
		}

		resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setData(circleOfFriendsList);
		resultUtils.setCount((int) addTime.getTotal());
		resultUtils.setPages((int) addTime.getPages());
		return resultUtils;
	}

	@Override
	public ResultUtils isRead(Integer memberId)
	{
		ResultUtils resultUtils = new ResultUtils();
		resultUtils.setStatus(200);
		resultUtils.setMessage("操作成功");
		resultUtils.setData(0);
		Page page = new Page(1, 10);
		page.setOptimizeCountSql(true);
		page.setSearchCount(true);

		Page addTime = selectPage(page, new EntityWrapper<CircleOfFriends>().eq("citySynchronization", "1").orderBy("addTime", false));
		List<CircleOfFriends> data = addTime.getRecords();

		if (data == null || data.size() == 0)
			return resultUtils;
		String readMembers = data.get(0).getReadMembers();
		if (StringUtils.isEmpty(readMembers))
		{
			resultUtils.setData(1);
			return resultUtils;
		}
		if (!readMembers.contains(memberId.toString()))
		{
			resultUtils.setData(1);
		}
		return resultUtils;
	}


}
