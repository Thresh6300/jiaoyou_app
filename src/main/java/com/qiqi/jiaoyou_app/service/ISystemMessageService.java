package com.qiqi.jiaoyou_app.service;

import com.qiqi.jiaoyou_app.pojo.SystemMessage;
import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.util.ResultUtils;

/**
 * <p>
 * 系统消息表 服务类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
public interface ISystemMessageService extends IService<SystemMessage> {

    ResultUtils messageList(Integer memberId,Integer pageSize, Integer pageNum, String keyWord);

    ResultUtils messageDetail(Integer memberId,Integer id,String type);

    ResultUtils notRead(Integer memberId);

    ResultUtils messageOnes(Integer memberId, String keyWord);

    ResultUtils messageLists(Integer memberId, Integer pageNum, Integer pageSize, String keyWord,String type);

    ResultUtils isRead(Integer memberId);
}
