package com.qiqi.jiaoyou_app.controller;


import com.qiqi.jiaoyou_app.service.IProblemService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 常见问题表 前端控制器
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Api(tags = {"常见问题"})
@RestController
@RequestMapping("/jiaoyou_app/problem")
public class ProblemController {

    @Autowired
    private IProblemService problemService;

    @ApiOperation(value = "问题列表(已完成)")
    @GetMapping("/problemList")
    public ResultUtils aopproblemList(@RequestHeader(name = HttpHeaders.LOCK_TOKEN) String token,
                                   @ApiParam(name = "pageSize",value = "页数据量",required = true,type = "Integer") Integer pageSize,
                                   @ApiParam(name = "pageNum",value = "页码",required = true,type = "Integer") Integer pageNum,
                                   @ApiParam(name = "keyWord",value = "关键字",required = true,type = "String") String keyWord){
        return problemService.problemList(pageSize, pageNum, keyWord);
    }
}

