package com.qiqi.jiaoyou_app.serviceImpl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.mapper.LoveHeartDonationLogsMapper;
import com.qiqi.jiaoyou_app.pojo.LoveHeartDonationLogs;
import com.qiqi.jiaoyou_app.service.LoveHeartDonationLogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 爱心捐赠记录表(LoveHeartDonationLogs)表服务实现类
 * loveHeartDonationLogs
 *
 * @author cfx
 * @since 2020-12-03 14:10:45
 */
@Slf4j
@Service("loveHeartDonationLogsService")
public class LoveHeartDonationLogsServiceImpl extends ServiceImpl<LoveHeartDonationLogsMapper, LoveHeartDonationLogs> implements LoveHeartDonationLogsService
{
    @Autowired
    private LoveHeartDonationLogsMapper loveHeartDonationLogsMapper;
}