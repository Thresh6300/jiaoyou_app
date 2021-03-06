package com.qiqi.jiaoyou_app.quartz;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qiqi.jiaoyou_app.pojo.MemberAssets;
import com.qiqi.jiaoyou_app.pojo.NewFriend;
import com.qiqi.jiaoyou_app.pojo.RechargeRecord;
import com.qiqi.jiaoyou_app.service.IMemberAssetsService;
import com.qiqi.jiaoyou_app.service.INewFriendService;
import com.qiqi.jiaoyou_app.service.IRechargeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CallogQuartz
{
    @Autowired
    private INewFriendService iNewFriendService;
    @Autowired
    private IMemberAssetsService iMemberAssetsService;
    @Autowired
    private IRechargeRecordService iRechargeRecordService;
    /**
     * @Description: getQuestionsTime 打招呼到期退回
     * @return: void
     * @Author: cfx
     * @Date: 2021-01-06 11:43
     */
    //测试使用
    @Scheduled(cron = "0 */1 * * * ?")
    //每小时执行一次
    // @Scheduled(cron = "0 0 */1 * * ?")
    public void getQuestionsTime()
    {

	  List<NewFriend> newFriends = iNewFriendService.selectList(new EntityWrapper<NewFriend>().le("new_friend_other_party_end_time", new Date()).eq("is_retreat", 2));
	  if (newFriends != null && newFriends.size() > 0)
	  {
		List<Integer> collect = newFriends.stream().map(NewFriend::getNewFriendOneselfId).collect(Collectors.toList());
		List<MemberAssets> memberAssets = iMemberAssetsService.selectList(new EntityWrapper<MemberAssets>().in("memberId",collect));
		List<RechargeRecord> rechargeRecords = new ArrayList<>();
		for (NewFriend newFriend : newFriends)
		{
		    for (MemberAssets memberAsset : memberAssets)
		    {
			  if (newFriend.getNewFriendOneselfId().equals(memberAsset.getMemberId()))
			  {
				System.out.println("退还过期礼物..." + memberAsset.getMemberId());
				newFriend.setIsRetreat(1);
				memberAsset.setMemberDiamondsizeOfGold(memberAsset.getMemberDiamondsizeOfGold() + newFriend.getRunSize());

				RechargeRecord rechargeRecord = new RechargeRecord();
				rechargeRecord.setMemberId(memberAsset.getMemberId());
				rechargeRecord.setName("退还过期礼物");
				rechargeRecord.setCurrency(3);
				rechargeRecord.setMode(4);
				rechargeRecord.setType(1);
				rechargeRecord.setRunSize(newFriend.getRunSize());
				rechargeRecord.setSurplus(memberAsset.getMemberDiamondsizeOfGold());
				rechargeRecord.setAddTime(new Timestamp(System.currentTimeMillis()));
				rechargeRecords.add(rechargeRecord);
			  }

		    }
		}
		iNewFriendService.updateBatchById(newFriends);
		iMemberAssetsService.updateBatchById(memberAssets);
		iRechargeRecordService.insertBatch(rechargeRecords);
		System.out.println("退还礼物..." + newFriends.size());
	  }

    }
}
