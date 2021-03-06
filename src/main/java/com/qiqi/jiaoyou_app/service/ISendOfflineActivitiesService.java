package com.qiqi.jiaoyou_app.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.pojo.AcceptOfflineActivities;
import com.qiqi.jiaoyou_app.pojo.SendOfflineActivities;
import com.qiqi.jiaoyou_app.util.ResultUtils;

/**
 * <p>
 * 线下活动列表 服务类
 * </p>
 *
 * @author GR123
 * @since 2020-06-16
 */
public interface ISendOfflineActivitiesService extends IService<SendOfflineActivities> {

    ResultUtils startAParty(SendOfflineActivities sendOfflineActivities);

    ResultUtils Initiated(Integer pageNum, Integer pageSize, Integer id, Integer type);

    ResultUtils partyDetails(Integer id,Integer memberId);

    ResultUtils enterFor(AcceptOfflineActivities acceptOfflineActivities);

    ResultUtils beInFor(Integer id,Integer type);

    ResultUtils clickOK(AcceptOfflineActivities acceptOfflineActivities);

    ResultUtils initiatorClickOK(SendOfflineActivities sendOfflineActivities,String token);

    ResultUtils closeParty(SendOfflineActivities sendOfflineActivities);

    ResultUtils submitCancellationApplication(AcceptOfflineActivities acceptOfflineActivities);

    ResultUtils compulsoryCancellation(AcceptOfflineActivities acceptOfflineActivities);

    ResultUtils participate(SendOfflineActivities sendOfflineActivities);

    ResultUtils apply(SendOfflineActivities sendOfflineActivities);

    ResultUtils isApply(Integer memberId);
}
