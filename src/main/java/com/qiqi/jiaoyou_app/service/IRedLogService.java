package com.qiqi.jiaoyou_app.service;

import com.qiqi.jiaoyou_app.pojo.RedLog;
import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.pojo.RedReceiveLog;
import com.qiqi.jiaoyou_app.util.ResultUtils;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GR123
 * @since 2020-06-19
 */
public interface IRedLogService extends IService<RedLog> {

    ResultUtils sendARedEnvelopeOneToOne(RedLog redLog);

    ResultUtils redPacketDetails(RedLog redLog);

    ResultUtils grabARedEnvelope(RedReceiveLog redReceiveLog);

    ResultUtils giftsRed(Integer pageSize, Integer pageNum, Integer id);
}
