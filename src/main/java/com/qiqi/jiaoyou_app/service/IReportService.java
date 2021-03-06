package com.qiqi.jiaoyou_app.service;

import com.qiqi.jiaoyou_app.pojo.Report;
import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.util.ResultUtils;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GR123
 * @since 2020-06-16
 */
public interface IReportService extends IService<Report> {

    ResultUtils report(Report report);
}
