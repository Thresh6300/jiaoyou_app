package com.qiqi.jiaoyou_app.service;

import com.qiqi.jiaoyou_app.pojo.NewFriend;
import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.util.ResultUtils;

/**
 * <p>
 * .新朋友列表 服务类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
public interface INewFriendService extends IService<NewFriend> {

    ResultUtils friendList(Integer newFriendOneselfId,Integer pageSize, Integer pageNum, String keyWord);

    ResultUtils sayHello(NewFriend newFriend);

    ResultUtils refuseFriend(Integer newFriendId);

    ResultUtils agreeFriend(Integer newFriendId);

    ResultUtils friendLimit(Integer memberId);
}
