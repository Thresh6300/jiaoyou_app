package com.qiqi.jiaoyou_app.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.pojo.LoveHeartDonation;
import com.qiqi.jiaoyou_app.util.ResultUtils;

/**
 * 爱心捐赠(LoveHeartDonation)表服务接口
 *
 * @author cfx
 * @since 2020-12-03 14:10:27
 */
public interface LoveHeartDonationService extends IService<LoveHeartDonation>
{

    ResultUtils loveList(LoveHeartDonation loveHeartDonation);

    ResultUtils getOne(LoveHeartDonation loveHeartDonation);
}

