package com.qiqi.jiaoyou_app.controller;

import com.qiqi.jiaoyou_app.service.LoveHeartDonationLogsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 爱心捐赠记录表(LoveHeartDonationLogs)表控制层
 *
 * @author cfx
 * @since 2020-12-03 14:10:45
 */
@RestController
@RequestMapping("/jiaoyou_app/loveHeartDonationLogs")
@Api(tags = " 爱心捐赠记录表管理")
public class LoveHeartDonationLogsController
{
    /**
     * 服务对象
     *
     * @author cfx
     * @since 2020-12-03 14:10:45
     */
    @Autowired
    private LoveHeartDonationLogsService loveHeartDonationLogsService;

}