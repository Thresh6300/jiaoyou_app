package com.qiqi.jiaoyou_app.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qiqi.jiaoyou_app.pojo.DynamicReviewOfFriendsCircle;

import java.util.List;

/**
 * <p>
 * 朋友圈动态评论表 Mapper 接口
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
public interface DynamicReviewOfFriendsCircleMapper extends BaseMapper<DynamicReviewOfFriendsCircle> {

    List<DynamicReviewOfFriendsCircle> dynamicDetails(Integer id);
}
