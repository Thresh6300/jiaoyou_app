package com.qiqi.jiaoyou_app.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.pojo.ClubBuddy;
import com.qiqi.jiaoyou_app.util.ResultUtils;


/**
 * 俱乐部好友表(ClubBuddy)表服务接口
 *
 * @author cfx
 * @since 2020-11-27 11:39:48
 */
public interface ClubBuddyService extends IService<ClubBuddy>
{


    ResultUtils buddyList(ClubBuddy clubBuddy);

    ResultUtils secretaryList(ClubBuddy clubBuddy);

    ClubBuddy getOne(Integer clubId,Integer memberId);
}