package com.qiqi.jiaoyou_app.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.config.RedisPoolUtils;
import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.mapper.MemberAssetsMapper;
import com.qiqi.jiaoyou_app.mapper.MemberMapper;
import com.qiqi.jiaoyou_app.mapper.RedLogMapper;
import com.qiqi.jiaoyou_app.mapper.RedReceiveLogMapper;
import com.qiqi.jiaoyou_app.pojo.*;
import com.qiqi.jiaoyou_app.red.GrabRedPacket;
import com.qiqi.jiaoyou_app.red.SendRedPacket;
import com.qiqi.jiaoyou_app.service.IRedLogService;
import com.qiqi.jiaoyou_app.util.JsonUtils;
import com.qiqi.jiaoyou_app.util.RedisData;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.sql.Timestamp;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author GR123
 * @since 2020-06-19
 */
@Service
public class RedLogServiceImpl extends ServiceImpl<RedLogMapper, RedLog> implements IRedLogService
{

    @Autowired
    private RedLogMapper redLogMapper;
    @Autowired
    private MemberAssetsMapper memberAssetsMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private RedReceiveLogMapper redReceiveLogMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisServiceImpl redisService;
    @Autowired
    private RechargeRecordServiceImpl rechargeRecordService;

    public static Date Yesterday(Date date)
    {
	  //获取当前时间24小时前的时间
	  Calendar c = Calendar.getInstance();
	  c.setTime(date);
	  c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) + 24);
	  Date time = c.getTime();
	  return time;
    }

    @Override
    public ResultUtils sendARedEnvelopeOneToOne(RedLog redLog)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  //初始化连接池
	  RedisPoolUtils.initialPool();
	  Jedis jedis = RedisPoolUtils.getConn();
	  //判断钻石是否够
	  List<MemberAssets> memberId = memberAssetsMapper.selectList(new EntityWrapper<MemberAssets>().eq("memberId", redLog.getRedLogMemberId()));
	  MemberAssets memberAssets = memberId.get(0);
	  if (memberAssets.getMemberDiamondsizeOfGold() < redLog.getRedLogGoldSize())
	  {
		resultUtils.setMessage(Constant.THERE_ARE_NOT_ENOUGH_DIAMONDS);
		resultUtils.setStatus(Constant.STATUS_FAILED);
		return resultUtils;
	  }
	  Member member = memberMapper.selectById(redLog.getRedLogMemberId());

	  redLog.setSurplus(memberAssets.getMemberDiamondsizeOfGold() - redLog.getRedLogGoldSize());
	  redLog.setRedLogMemberNickName(member.getNickName());
	  redLog.setRedLogMemberHead(member.getHead());
	  if (redLog.getType() == 1)
	  {
		redLog.setRedLogSex(3);
		redLog.setRedLogRedSize(1);
		redLog.setRedLogNumberReceipts(0);
		redLog.setRedLogNumberRemaining(1);
		redLog.setRedLogSendTime(new Timestamp(System.currentTimeMillis()));
		redLog.setRedLogEndTime(Yesterday(new Date()));
	  }
	  else
	  {
		redLog.setRedLogNumberReceipts(0);
		redLog.setRedLogNumberRemaining(redLog.getRedLogRedSize());
		redLog.setRedLogSendTime(new Timestamp(System.currentTimeMillis()));
		redLog.setRedLogEndTime(Yesterday(new Date()));
	  }
	  Integer insert = redLogMapper.insert(redLog);
	  if (insert <= 0)
	  {
		resultUtils.setStatus(Constant.STATUS_FAILED);
		resultUtils.setMessage(Constant.FAILED_TO_SEND_RED_PACKET);
	  }
	  else
	  {
		Thread sendThread = new Thread(new SendRedPacket(redLog.getRedLogGoldSize(), redLog.getRedLogRedSize(), redLog.getRedLogId() + ""));
		sendThread.start();
		try
		{
		    sendThread.join();
		}
		catch (InterruptedException e)
		{
		    e.printStackTrace();
		}

		RechargeRecord rechargeRecord = new RechargeRecord();
		rechargeRecord.setMemberId(redLog.getRedLogMemberId());
		rechargeRecord.setName(Constant.SEND_RED_PACKET);
		rechargeRecord.setCurrency(3);
		rechargeRecord.setMode(4);
		rechargeRecord.setType(2);
		rechargeRecord.setRunSize(Long.valueOf(redLog.getRedLogGoldSize()));
		rechargeRecord.setSurplus(memberAssets.getMemberDiamondsizeOfGold() - redLog.getRedLogGoldSize());
		rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
		rechargeRecordService.insert(rechargeRecord);

		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setMessage(Constant.RED_PACKET_SENT_SUCCESSFULLY);
		resultUtils.setData(redLog);
	  }

	  return resultUtils;
    }

    @Override
    public ResultUtils redPacketDetails(RedLog redLog)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  redLog = redLogMapper.selectById(redLog.getRedLogId());
	  //查询抢红包记录
	  List<RedReceiveLog> red_receive_log_red_id = redReceiveLogMapper.selectList(new EntityWrapper<RedReceiveLog>().eq("red_receive_log_red_id", redLog.getRedLogId()));
	  if (redLog.getRedLogRedSize() == red_receive_log_red_id.size())
	  {
		//红包抢完
		//排序
		Collections.sort(red_receive_log_red_id, (o1, o2) ->
		{
		  //升序
		  return o2.getRedReceiveLogGoldSize().compareTo(o1.getRedReceiveLogGoldSize());
		});
		//设置手气最佳
		red_receive_log_red_id.get(0).setRedReceiveLogIsLuck(1);
	  }
	  for (RedReceiveLog redReceiveLog : red_receive_log_red_id)
	  {
		redReceiveLog.setRedReceiveLogMemberLevel(memberMapper.selectById(redReceiveLog.getRedReceiveLogMemberId()).getLevel());
	  }


	  redLog.setList(red_receive_log_red_id);
	  resultUtils.setData(redLog);
	  resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
	  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  return resultUtils;
    }

    @Override
    public ResultUtils grabARedEnvelope(RedReceiveLog redReceiveLog)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  //初始化连接池
	  RedisPoolUtils.initialPool();
	  Jedis jedis = RedisPoolUtils.getConn();
	  //先判断抢红包的人是不是抢的自己的红包
	  RedLog redLog = redLogMapper.selectById(redReceiveLog.getRedReceiveLogRedId());//红包
	  //判断该红包是不是过期
	  Date redLogSendTime = redLog.getRedLogEndTime();
	  if (System.currentTimeMillis() >= redLogSendTime.getTime())
	  {
		resultUtils.setMessage(Constant.THE_RED_PACKET_HAS_EXPIRED);
		resultUtils.setStatus(Constant.STATUS_FAILED);
		return resultUtils;
	  }
	  Member member = memberMapper.selectById(redReceiveLog.getRedReceiveLogMemberId());//抢红包的人
	  if (redLog.getRedLogMemberId().equals(redReceiveLog.getRedReceiveLogMemberId()) && redLog.getType() == 1)
	  {
		resultUtils.setMessage(Constant.ITS_OVER);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		return resultUtils;
	  }
	  if (!redLog.getRedLogSex().equals(member.getSex()) && redLog.getRedLogSex() != 3)
	  {
		resultUtils.setStatus(Constant.STATUS_FAILED);
		resultUtils.setMessage("该红包只限" + (redLog.getRedLogSex() == 1 ? "男" : "女") + "生抢");
		return resultUtils;
	  }
	  Thread grabThread = new Thread(new GrabRedPacket(redReceiveLog.getRedReceiveLogRedId() + "", redReceiveLog.getRedReceiveLogMemberId() + ""));
	  grabThread.start();
	  boolean alive = grabThread.isAlive();
	  while (alive)
	  {
		alive = grabThread.isAlive();
	  }
	  //判断查询个数
	  List list = getList(redReceiveLog);
	  List<RedisData> list1 = JsonUtils.jsonToList(list.toString(), RedisData.class);
	  for (RedisData redisData : list1)
	  {
		if (Integer.valueOf(redisData.getName()).equals(redReceiveLog.getRedReceiveLogMemberId()))
		{
		    List<RedReceiveLog> redReceiveLogs = redReceiveLogMapper.selectList(new EntityWrapper<RedReceiveLog>().eq("red_receive_log_member_id", redReceiveLog.getRedReceiveLogMemberId()).eq("red_receive_log_red_id", redReceiveLog.getRedReceiveLogRedId()));
		    if (redReceiveLogs.size() > 0)
		    {
			  //已经添加过抢红包记录
			  resultUtils.setMessage(Constant.ITS_OVER);
			  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
			  return resultUtils;
		    }
		    redReceiveLog.setRedReceiveLogMemberId(member.getId());
		    redReceiveLog.setRedReceiveLogMemberNickName(member.getNickName());
		    redReceiveLog.setRedReceiveLogMemberHead(member.getHead());
		    redReceiveLog.setRedReceiveLogGoldSize(Integer.valueOf(redisData.getMoney()));
		    redReceiveLog.setRedReceiveLogTime(new Timestamp(System.currentTimeMillis()));
		    redReceiveLog.setRedReceiveLogIsLuck(2);
		    Integer insert = redReceiveLogMapper.insert(redReceiveLog);
		    if (insert > 0)
		    {
		    	redLogMapper.updateRedNum(redLog.getRedLogId());
		    	resultUtils.setMessage(Constant.ITS_OVER);
		    	resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		    }
		    else
		    {
			  resultUtils.setMessage(Constant.FAILED_TO_SNATCH_THE_RED_ENVELOPE);
			  resultUtils.setStatus(Constant.STATUS_FAILED);
		    }
		}
		else
		{
		    resultUtils.setMessage(Constant.ITS_OVER);
		    resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		    continue;
		}
	  }
	  return resultUtils;
    }

    @Override
    public ResultUtils giftsRed(Integer pageSize, Integer pageNum, Integer id)
    {
	  ResultUtils resultUtils = new ResultUtils();
	  Page<RedLog> objectPage = new Page<>(pageNum, pageSize);
	  List<RedLog> red_log_member_id1 = redLogMapper.selectList(new EntityWrapper<RedLog>().eq("red_log_member_id", id));
	  List<RedLog> red_log_member_id = redLogMapper.selectPage(objectPage, new EntityWrapper<RedLog>().eq("red_log_member_id", id).orderBy("red_log_id", false));

	  //计算总钻石
	  Integer price = 0;
	  for (RedLog redLog : red_log_member_id1)
	  {
		price += redLog.getRedLogGoldSize();
	  }
	  resultUtils.setCount(price);
	  resultUtils.setData(red_log_member_id);
	  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
	  resultUtils.setData1(objectPage);
	  return resultUtils;
    }

    public List getList(RedReceiveLog redReceiveLog)
    {
	  List values = redisTemplate.opsForHash().values("red::draw" + redReceiveLog.getRedReceiveLogRedId());
	  return values;
    }
}
