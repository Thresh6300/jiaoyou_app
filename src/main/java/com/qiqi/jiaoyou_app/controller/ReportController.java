package com.qiqi.jiaoyou_app.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.qiqi.jiaoyou_app.pojo.Member;
import com.qiqi.jiaoyou_app.pojo.Report;
import com.qiqi.jiaoyou_app.serviceImpl.ReportServiceImpl;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author GR123
 * @since 2020-06-16
 */
@Api(tags = {"举报"})
@RestController
@RequestMapping("/jiaoyou_app/report")
public class ReportController {

    @Autowired
    private ReportServiceImpl reportService;

    @ApiOperation(value = "举报(已完成)")
    @PostMapping("/report")
    @DynamicParameters(name = "report",properties = {
            @DynamicParameter(name = "reportInformantId",value = "举报人ID",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "reportBeiInformantId",value = "被举报人ID",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "reportReason",value = "举报原因ID",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "reportContext",value = "举报内容",required = true,dataTypeClass = String.class)
    })
    public ResultUtils aopreport(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token, @RequestBody JSONObject jsonObject){
        Report report = JSON.toJavaObject(jsonObject, Report.class);
        return reportService.report(report);
    }

}

