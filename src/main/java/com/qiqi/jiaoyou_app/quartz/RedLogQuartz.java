package com.qiqi.jiaoyou_app.quartz;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qiqi.jiaoyou_app.pojo.MemberAssets;
import com.qiqi.jiaoyou_app.pojo.RedLog;
import com.qiqi.jiaoyou_app.pojo.RedReceiveLog;
import com.qiqi.jiaoyou_app.service.IMemberAssetsService;
import com.qiqi.jiaoyou_app.service.IRedLogService;
import com.qiqi.jiaoyou_app.service.IRedReceiveLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RedLogQuartz
{
    @Autowired
    private IRedLogService iRedLogService;
    @Autowired
    private IMemberAssetsService iMemberAssetsService;
    @Autowired
    private IRedReceiveLogService redReceiveLogService;

    private final static Logger logger = LoggerFactory.getLogger(RedLogQuartz.class);

    /**
     * @Description: getQuestionsTime 红包结束时间未领取退回
     * @return: void
     * @Author: cfx
     * @Date: 2021-01-06 11:43
     */
    //@Scheduled(cron = "0 */1 * * * ?")
    // @Scheduled(cron = "*/1 * * * * ?")
    //每小时执行一次
    // @Scheduled(cron = "0 0 */1 * * ?")
    //测试使用每分钟
    @Scheduled(cron = "0 */1 * * * ?")
    public void getQuestionsTime()
    {

	  List<RedLog> redLogs = iRedLogService.selectList(new EntityWrapper<RedLog>().eq("status", "0").le("red_log_end_time", new Date()).ne("red_log_number_remaining", 0));
	  //AlanModify 如果new date()出来的时间可能查询不到前24小时的内容,应该是获取当前整点信息,然后当作条件
	  //进行查询
	  if (redLogs != null && redLogs.size() > 0)
	  {
		List<Integer> collect = redLogs.stream().map(RedLog::getRedLogMemberId).collect(Collectors.toList());
		List<MemberAssets> memberAssets = iMemberAssetsService.selectList(new EntityWrapper<MemberAssets>().in("memberId",collect));
		for (RedLog redLog : redLogs)
		{
		    for (MemberAssets memberAsset : memberAssets)
		    {
			  if (memberAsset.getMemberId().equals(redLog.getRedLogMemberId()))
			  {
			      //(0:没过期1:过期)
				redLog.setStatus("1");
				System.out.println("退还红包...会员ID:" + memberAsset.getMemberId()+" 原钻石量:"
					    +memberAsset.getMemberDiamondsizeOfGold()
					    +"退还钻石:"
					    +redLog.getRedLogGoldSize());
				System.out.println("剩余:"+memberAsset.getMemberDiamondsizeOfGold() + redLog.getRedLogGoldSize());
				logger.info("退还红包...会员ID:" + memberAsset.getMemberId()+" 原钻石量:"
					    +memberAsset.getMemberDiamondsizeOfGold()
					    +"退还钻石:"
					    +redLog.getRedLogGoldSize());
				logger.info("剩余:"+memberAsset.getMemberDiamondsizeOfGold() + redLog.getRedLogGoldSize());

				//获取当前红包的接收用户的总金额信息
				List<RedReceiveLog> redReceiveLogList = redReceiveLogService.selectList(new EntityWrapper<RedReceiveLog>()
					    .eq("red_receive_log_red_id",redLog.getRedLogId()));
				Double ints = redReceiveLogList.stream().mapToDouble(RedReceiveLog::getRedReceiveLogGoldSize).sum();
				memberAsset.setMemberDiamondsizeOfGold(memberAsset.getMemberDiamondsizeOfGold() + (redLog.getRedLogGoldSize() - ints.intValue())  );
			      //设置红包的余额
				redLog.setSurplus(memberAsset.getMemberDiamondsizeOfGold() + (redLog.getRedLogGoldSize() - ints.intValue()));
			  }
		    }
		}
		iRedLogService.updateBatchById(redLogs);
		iMemberAssetsService.updateBatchById(memberAssets);
		System.out.println("退还红包..." + redLogs.size());
	  }

    }
}
