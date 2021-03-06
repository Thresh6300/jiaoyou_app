package com.qiqi.jiaoyou_app.controller;


import com.qiqi.jiaoyou_app.serviceImpl.ReportTypeServiceImpl;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author GR123
 * @since 2020-06-16
 */
@Api(tags = {"举报原因查询"})
@RestController
@RequestMapping("/jiaoyou_app/reportType")
public class ReportTypeController {

    @Autowired
    private ReportTypeServiceImpl reportTypeService;


    @ApiOperation(value = "举报原因查询(已完成)")
    @GetMapping("selectAllType")
    public ResultUtils aopselectAllType(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token){
        return reportTypeService.selectAllType();
    }
}

