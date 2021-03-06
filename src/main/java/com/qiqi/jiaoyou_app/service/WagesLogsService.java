package com.qiqi.jiaoyou_app.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.pojo.WagesLogs;

/**
 * 工资记录表(WagesLogs)表服务接口
 *
 * @author makejava
 * @since 2020-12-14 14:00:39
 */
public interface WagesLogsService extends IService<WagesLogs> {
Boolean add(WagesLogs wagesLogs);
}