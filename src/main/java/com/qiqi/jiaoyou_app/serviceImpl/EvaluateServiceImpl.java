package com.qiqi.jiaoyou_app.serviceImpl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.mapper.EvaluateMapper;
import com.qiqi.jiaoyou_app.pojo.Evaluate;
import com.qiqi.jiaoyou_app.service.EvaluateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 评价(Evaluate)表服务实现类
 * evaluate
 *
 * @author cfx
 * @since 2020-12-05 18:45:30
 */
@Slf4j
@Service("evaluateService")
public class EvaluateServiceImpl extends ServiceImpl<EvaluateMapper, Evaluate> implements EvaluateService
{

}