package com.qiqi.jiaoyou_app.service;

import com.qiqi.jiaoyou_app.pojo.ReportType;
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
public interface IReportTypeService extends IService<ReportType> {

    ResultUtils selectAllType();
}
