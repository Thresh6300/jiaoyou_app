package com.qiqi.jiaoyou_app.serviceImpl;

import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.pojo.PlatformParameterSetting;
import com.qiqi.jiaoyou_app.mapper.PlatformParameterSettingMapper;
import com.qiqi.jiaoyou_app.service.IPlatformParameterSettingService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import com.qiqi.jiaoyou_app.vo.SettingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 平台参数设置 服务实现类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Service
public class PlatformParameterSettingServiceImpl extends ServiceImpl<PlatformParameterSettingMapper, PlatformParameterSetting> implements IPlatformParameterSettingService {

    @Autowired
    private PlatformParameterSettingMapper platformParameterSettingMapper;

    @Override
    public ResultUtils  consumerHotline() {
        ResultUtils resultUtils = new ResultUtils();
        PlatformParameterSetting platformParameterSetting = platformParameterSettingMapper.selectById(1);
        if (platformParameterSetting == null || platformParameterSetting.getTelephone() == null){
            resultUtils.setMessage(Constant.THERE_IS_NO_CUSTOMER_SERVICE_TELEPHONE_SET_IN_THE_BACKGROUND);
            resultUtils.setStatus(Constant.STATUS_FAILED);
            return resultUtils;
        }
        resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
        resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
        resultUtils.setData(platformParameterSetting.getTelephone());
        return resultUtils;
    }

    @Override
    public ResultUtils otherParameters() {
        ResultUtils resultUtils = new ResultUtils();
        PlatformParameterSetting platformParameterSetting = platformParameterSettingMapper.selectById(1);
        resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
        resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
        resultUtils.setData(platformParameterSetting);
        return resultUtils;
    }

    @Override
    public ResultUtils mapAndVersion() {
        ResultUtils resultUtils = new ResultUtils();
        PlatformParameterSetting platformParameterSetting = platformParameterSettingMapper.selectById(1);
        SettingVO settingVO = new SettingVO();
        settingVO.setLaunchPage(platformParameterSetting.getLaunchPage());
        settingVO.setLoginImage(platformParameterSetting.getLoginImage());
        settingVO.setVersion(platformParameterSetting.getVersion());
        settingVO.setDescription(platformParameterSetting.getDescription());
        settingVO.setApplePackageDownloadUrl(platformParameterSetting.getApplePackageDownloadUrl());
        settingVO.setAndroidPackageDownloadUrl(platformParameterSetting.getAndroidPackageDownloadUrl());
        resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
        resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
        resultUtils.setData(settingVO);
        return resultUtils;
    }
}
