package com.qiqi.jiaoyou_app.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.pojo.ReportType;
import com.qiqi.jiaoyou_app.mapper.ReportTypeMapper;
import com.qiqi.jiaoyou_app.service.IReportTypeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GR123
 * @since 2020-06-16
 */
@Service
public class ReportTypeServiceImpl extends ServiceImpl<ReportTypeMapper, ReportType> implements IReportTypeService {

    @Autowired
    private ReportTypeMapper reportTypeMapper;
    @Override
    public ResultUtils selectAllType() {
        ResultUtils resultUtils = new ResultUtils();
        List<ReportType> reportTypes = reportTypeMapper.selectList(new EntityWrapper<ReportType>());
        resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
        resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
        resultUtils.setData(reportTypes);
        return resultUtils;
    }
}
