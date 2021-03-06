package com.qiqi.jiaoyou_app.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.mapper.MemberAssetsMapper;
import com.qiqi.jiaoyou_app.mapper.RechargeRecordMapper;
import com.qiqi.jiaoyou_app.mapper.RedLogMapper;
import com.qiqi.jiaoyou_app.mapper.TaskAnswerQuestionsMapper;
import com.qiqi.jiaoyou_app.pojo.*;
import com.qiqi.jiaoyou_app.service.*;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import com.qiqi.jiaoyou_app.vo.RechargeRecordVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 充值记录 服务实现类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Service
public class RechargeRecordServiceImpl extends ServiceImpl<RechargeRecordMapper, RechargeRecord> implements IRechargeRecordService
{

    @Autowired(required = false)
    private RechargeRecordMapper rechargeRecordMapper;
    @Autowired
    private IDiamondconsumptionrecordService iDiamondconsumptionrecordService;
    @Autowired
    private TaskAnswerQuestionsMapper taskAnswerQuestionsMapper;
    @Autowired
    private WagesLogsService wagesLogsService;
    @Autowired
    private ISendOfflineActivitiesService iSendOfflineActivitiesService;
    @Autowired
    private INewFriendService iNewFriendService;
    @Autowired
    private IMemberService iMemberService;
    @Autowired
    private IMemberAssetsService iMemberAssetsService;
    @Autowired
    private IPlatformParameterSettingService iPlatformParameterSetting;
    @Autowired
    private IGiftconsumptionService iGiftconsumptionService;
    @Autowired
    private IGiftService iGiftService;
    @Autowired
    private IRedLogService iRedLogService;
    @Autowired
    private RedLogMapper redLogMapper;
    @Autowired
    private MemberAssetsMapper memberAssetsMapper;
    @Autowired
    private IRedReceiveLogService redReceiveLogService;
    private final static Logger logger = LoggerFactory.getLogger(RechargeRecordServiceImpl.class);
    // 红包 发红包/接收红包，礼物，某某俱乐部工资，聚会，世界频道发言，评论，打招呼，平台赠送，邀请好友，
    @Override
    @Transient
    public ResultUtils aopRechargeLog(Integer id, Integer pageNum, Integer pageSize)
    {

	  List<RechargeRecordVo> list = new ArrayList<>();
	  ResultUtils resultUtils = new ResultUtils();
	  resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
	  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  resultUtils.setData(list);
	  resultUtils.setCount(0);
	  resultUtils.setPages(0);

	  // 充值记录 1：会员 2：黑卡 3：金钻  4：银钻 5：黑钻 邀请好友 评论，聚会
	  List<RechargeRecord> rechargeRecords = rechargeRecordMapper.selectList(new EntityWrapper<RechargeRecord>().eq("memberId", id).orderBy("addTime", false));
	  if (rechargeRecords != null && rechargeRecords.size() > 0)
	  {
		for (RechargeRecord rechargeRecord : rechargeRecords)
		{
		    RechargeRecordVo rechargeRecordVo = new RechargeRecordVo();
		    rechargeRecordVo.setMemberId(rechargeRecord.getMemberId());
		    rechargeRecordVo.setName(rechargeRecord.getName());
		    rechargeRecordVo.setType(rechargeRecord.getType());
		    //设置狗屎的钻石类型
		    //author:nan
		    //time:20210119 1：会员 2：黑卡 3：金钻  4：银钻 5：黑钻6:小喇叭，
		    rechargeRecordVo.setCurrency(rechargeRecord.getCurrency());
		    rechargeRecordVo.setRunSize(rechargeRecord.getRunSize());
		    rechargeRecordVo.setSurplus(rechargeRecord.getSurplus());
		    rechargeRecordVo.setAddTime(rechargeRecord.getAddTime());
		    list.add(rechargeRecordVo);
		}
	  }

	  // 红包
	  ResultUtils resultUtils1 = iRedLogService.giftsRed(pageSize, pageNum, id);
	  if (resultUtils1.getStatus() == 200)
	  {
		List<RedLog> data = (List<RedLog>) resultUtils1.getData();
		if (data != null && data.size() > 0)
		{
		    for (RedLog datum : data)
		    {
			  RechargeRecordVo rechargeRecordVo = new RechargeRecordVo();
			  rechargeRecordVo.setMemberId(datum.getRedLogMemberId());
			  if (new Date().after(datum.getRedLogEndTime()) && datum.getRedLogNumberRemaining() != 0)
			  {
			      //因为是for循环的缘故 红包里的剩余金额用完之后 需要修改........................................
				rechargeRecordVo.setName("退回红包");
				rechargeRecordVo.setType(1);

				System.out.println("==========================================================chulaile??");
				logger.debug(datum.getRedLogMemberId().toString());
				//获取当前红包的接收用户的总金额信息


				List<RedReceiveLog> redReceiveLogList = redReceiveLogService.selectList(new EntityWrapper<RedReceiveLog>()
					    .eq("red_receive_log_red_id",datum.getRedLogId()));
				Double ints = redReceiveLogList.stream().mapToDouble(RedReceiveLog::getRedReceiveLogGoldSize).sum();


				List<MemberAssets> memberAccess = memberAssetsMapper.selectList(new EntityWrapper<MemberAssets>()
					    .eq("memberId",datum.getRedLogMemberId()));

				Long yuera = Long.valueOf(datum.getRedLogGoldSize() - ints.intValue());
				// Long yuer = Long.valueOf(memberAccess.get(0).getMemberDiamondsizeOfGold());
				// Long yuer = Long.valueOf(memberAccess.get(0).getMemberDiamondsizeOfGold() + ints.intValue());


				//这样子写会出现什么问题呢? 就是会出现问题...... 是否能修改余额(0:不能,1:能)
				// if(data.size() > 1){
				//     RedLog redLog = datum;
				//     redLog.setSurplus(yuer);
				//     redLog.setEnableWrite("0");
				//     redLogMapper.updateById(redLog);
				// }

				//余额
				// rechargeRecordVo.setSurplus(datum.getSurplus() + datum.getRedLogGoldSize());
				rechargeRecordVo.setSurplus(datum.getSurplus() - yuera);
				// rechargeRecordVo.setSurplus(yuer);
				//数量
				rechargeRecordVo.setRunSize(yuera);
				// rechargeRecordVo.setRunSize(Long.valueOf(datum.getRedLogGoldSize()));
				rechargeRecordVo.setAddTime(datum.getRedLogEndTime());
				list.add(rechargeRecordVo);
			  }
		    }
		}

	  }

	  if (list == null || list.size() == 0)
		return resultUtils;

	  List<RechargeRecordVo> lists = new ArrayList<>();
	  for (RechargeRecordVo rechargeRecordVo : list)
	  {
		if (rechargeRecordVo.getAddTime() != null)
		{
		    lists.add(rechargeRecordVo);
		}
	  }


	  List<RechargeRecordVo> collect = lists.stream().sorted(Comparator.comparing(RechargeRecordVo::getAddTime).reversed()).collect(Collectors.toList());
	  Integer count = collect.size(); // 记录总数
	  int ceil = (int) Math.ceil((double) count / (double) pageSize);
	  if (ceil < pageNum)
	  {
		resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
		resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
		resultUtils.setData(new ArrayList<>());
		return resultUtils;
	  }
	  Integer pageCount = 0; // 页数
	  if (count % pageSize == 0)
	  {
		pageCount = count / pageSize;
	  }
	  else
	  {
		pageCount = count / pageSize + 1;
	  }

	  int fromIndex = 0; // 开始索引
	  int toIndex = 0; // 结束索引

	  if (pageNum != pageCount)
	  {
		fromIndex = (pageNum - 1) * pageSize;
		toIndex = fromIndex + pageSize;
	  }
	  else
	  {
		fromIndex = (pageNum - 1) * pageSize;
		toIndex = count;
	  }
	  List<RechargeRecordVo> pageList = collect.subList(fromIndex, toIndex);

	  resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
	  resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
	  resultUtils.setData(pageList);
	  resultUtils.setCount(count);
	  resultUtils.setPages(ceil);
	  return resultUtils;
    }

    @Override
    public ResultUtils speakerConsumption(RechargeRecord rechargeRecord)
    {
	  ResultUtils resultUtils = new ResultUtils();
        // 根据会员id查询性别，是女生，世界聊条不需要消费银钻 性别 1：男 2：女
        Member member = iMemberService.selectById(rechargeRecord.getMemberId());
        if (member.getSex() == 2) {

            resultUtils.setStatus(200);
            resultUtils.setMessage("操作成功");
            rechargeRecord.setName("世界发言-女神免单");
        } else {
            List<PlatformParameterSetting> platformParameterSettings = iPlatformParameterSetting.selectList(null);
            Long worldSpeakGlod = platformParameterSettings.get(0).getWorldSpeakGlod();
            MemberAssets memberAssets = iMemberAssetsService.selectOne(new EntityWrapper<MemberAssets>().eq("memberId", rechargeRecord.getMemberId()));
            if (worldSpeakGlod > memberAssets.getMemberDiamondsizeOfGold())
            {
                resultUtils.setStatus(500);
                resultUtils.setMessage("您的钻石不够");
                return resultUtils;
            }

            memberAssets.setMemberDiamondsizeOfGold(memberAssets.getMemberDiamondsizeOfGold() - worldSpeakGlod);
            iMemberAssetsService.updateById(memberAssets);
            resultUtils.setStatus(200);
            resultUtils.setMessage("操作成功");
            rechargeRecord.setName("世界发言消费");
            rechargeRecord.setType(2);
            rechargeRecord.setCurrency(3);
            rechargeRecord.setRunSize(worldSpeakGlod);
            //剩余金钻数量
            rechargeRecord.setSurplus(memberAssets.getMemberDiamondsizeOfGold());
            rechargeRecord.setAddTime(new Date());
            Integer insert = rechargeRecordMapper.insert(rechargeRecord);
            if (insert > 0) {
                return resultUtils;
            }
            resultUtils.setStatus(500);
            resultUtils.setMessage("操作失败");
        }
	  return resultUtils;
    }
}
