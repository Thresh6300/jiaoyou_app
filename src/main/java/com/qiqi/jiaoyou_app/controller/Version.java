package com.qiqi.jiaoyou_app.controller;

import com.qiqi.jiaoyou_app.mapper.PlatformParameterSettingMapper;
import com.qiqi.jiaoyou_app.pojo.PlatformParameterSetting;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"版本管理"})
@RestController
@RequestMapping("/version")
public class Version {
    @Autowired
    private PlatformParameterSettingMapper settingMapper;

    @ApiOperation(value = "版本信息(包括下载地址)")
    @GetMapping("/info")
    public ResultUtils info()
    {
        PlatformParameterSetting data = settingMapper.selectById(1);
        ResultUtils resultUtils = new ResultUtils();
        resultUtils.setStatus(200);
        resultUtils.setMessage("查询成功");
        resultUtils.setCode(0);
        resultUtils.setData(data);
        return resultUtils;
    }
}
