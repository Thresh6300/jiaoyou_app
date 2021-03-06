package com.qiqi.jiaoyou_app.serviceImpl;

import com.qiqi.jiaoyou_app.constants.Constant;
import com.qiqi.jiaoyou_app.pojo.Opinion;
import com.qiqi.jiaoyou_app.mapper.OpinionMapper;
import com.qiqi.jiaoyou_app.service.IOpinionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * <p>
 * 意见表 服务实现类
 * </p>
 *
 * @author sunLQ
 * @since 2020-04-30
 */
@Service
public class OpinionServiceImpl extends ServiceImpl<OpinionMapper, Opinion> implements IOpinionService {

    @Autowired
    private OpinionMapper opinionMapper;

    @Override
    public ResultUtils feedback(Opinion opinion) {
        ResultUtils resultUtils = new ResultUtils();
        opinion.setAddTime(new Timestamp(System.currentTimeMillis()));
        opinion.setState(2);
        Integer insert = opinionMapper.insert(opinion);
        if (insert <= 0){
            resultUtils.setMessage("意见反馈失败");
            resultUtils.setStatus(Constant.STATUS_FAILED);
            return resultUtils;
        }
        resultUtils.setStatus(Constant.STATUS_SUCCESSFULLY);
        resultUtils.setMessage("意见反馈成功");
        return resultUtils;
    }
}
