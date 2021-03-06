package com.qiqi.jiaoyou_app.service;

import com.qiqi.jiaoyou_app.pojo.MemberAssets;
import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.util.ResultUtils;

/**
 * <p>
 * 会员资产表 服务类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
public interface IMemberAssetsService extends IService<MemberAssets> {

    ResultUtils myTrumpet(Integer id);


    ResultUtils updateInfo(MemberAssets memberAssets);
    ResultUtils silverDiamondForGoldDiamond(MemberAssets memberAssets);
    ResultUtils GoldDiamondForSilverDiamond(MemberAssets memberAssets);
    ResultUtils silverDiamondForBlackDiamond(MemberAssets memberAssets);


    MemberAssets selectMemberId(Integer memberId);
}
