package com.qiqi.jiaoyou_app.service;

import com.qiqi.jiaoyou_app.pojo.PlatformParameterSetting;
import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.util.ResultUtils;

/**
 * <p>
 * 平台参数设置 服务类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
public interface IPlatformParameterSettingService extends IService<PlatformParameterSetting> {

    ResultUtils consumerHotline();

    ResultUtils otherParameters();

    ResultUtils mapAndVersion();
}
