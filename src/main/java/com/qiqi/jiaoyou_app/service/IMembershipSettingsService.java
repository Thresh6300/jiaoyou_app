package com.qiqi.jiaoyou_app.service;

import com.qiqi.jiaoyou_app.pojo.MembershipSettings;
import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.util.ResultUtils;

/**
 * <p>
 * 会员设置表 服务类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
public interface IMembershipSettingsService extends IService<MembershipSettings> {

    ResultUtils updateSetting(Integer id, Integer type, Integer state);
}
