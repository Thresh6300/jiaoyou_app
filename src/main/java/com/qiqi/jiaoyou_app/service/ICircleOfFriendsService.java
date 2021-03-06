package com.qiqi.jiaoyou_app.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.pojo.CircleOfFriends;
import com.qiqi.jiaoyou_app.util.ResultUtils;

/**
 * <p>
 * 朋友圈动态 服务类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
public interface ICircleOfFriendsService extends IService<CircleOfFriends> {

    ResultUtils dynamicPublishing(CircleOfFriends circleOfFriends);

    ResultUtils myCircleOfFriendsList(Integer pageSize, Integer pageNum, Integer id);

    ResultUtils dynamicDetails(Integer pageSize, Integer pageNum, Integer id,String token,Integer memberId);

    ResultUtils friendsOfFriendsList(Integer pageSize, Integer pageNum, Integer id);


    ResultUtils del(Integer id);

    ResultUtils memberDetailFriendsList(Integer pageSize, Integer pageNum, Integer memberId, Integer beiMemberId);

    ResultUtils worldCircleOfFriendsList(Integer pageSize, Integer pageNum,Integer id);

    ResultUtils isRead(Integer memberId);
}
