package com.qiqi.jiaoyou_app.serviceImpl;

import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.pojo.Agreement;
import com.qiqi.jiaoyou_app.mapper.AgreementMapper;
import com.qiqi.jiaoyou_app.service.IAgreementService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 协议表 服务实现类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Service
public class AgreementServiceImpl extends ServiceImpl<AgreementMapper, Agreement> implements IAgreementService {

    @Autowired
    private AgreementMapper agreementMapper;

    @Override
    public ResultUtils giftList() {
        ResultUtils resultUtils = new ResultUtils();
        Agreement agreement = agreementMapper.selectById(1);
        //todo 苹果发版改成0，发版后改回1
        agreement.setState(1);
//        agreement.setState(0);
        resultUtils.setData(agreement);
        resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
        resultUtils.setMessage(Constant.QUERY_WAS_SUCCESSFUL);
        return resultUtils;
    }
}
