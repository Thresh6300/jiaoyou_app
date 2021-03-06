package com.qiqi.jiaoyou_app.service;

import com.qiqi.jiaoyou_app.pojo.DynamicReviewOfFriendsCircle;
import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.pojo.DynamicVo;
import com.qiqi.jiaoyou_app.util.ResultUtils;

/**
 * <p>
 * 朋友圈动态评论表 服务类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
public interface IDynamicReviewOfFriendsCircleService extends IService<DynamicReviewOfFriendsCircle> {

    ResultUtils dynamicDetails(DynamicReviewOfFriendsCircle dynamicReviewOfFriendsCircle);

    ResultUtils aopDeleteComments(Integer memberId, Integer commentId,Integer dynamicId, Integer type, Integer category);

    ResultUtils MyDynamicList(DynamicVo dynamicVo);

    ResultUtils emptyMyDynamicList(DynamicVo dynamicVo);

    ResultUtils isApply(Integer memberId);
}
