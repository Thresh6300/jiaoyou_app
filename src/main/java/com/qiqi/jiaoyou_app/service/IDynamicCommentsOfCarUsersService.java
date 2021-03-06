package com.qiqi.jiaoyou_app.service;

import com.qiqi.jiaoyou_app.pojo.DynamicCommentsOfCarUsers;
import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.util.ResultUtils;

/**
 * <p>
 * 车友动态评论表 服务类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
public interface IDynamicCommentsOfCarUsersService extends IService<DynamicCommentsOfCarUsers> {

    ResultUtils dynamicDetails(DynamicCommentsOfCarUsers dynamicCommentsOfCarUsers);
}
