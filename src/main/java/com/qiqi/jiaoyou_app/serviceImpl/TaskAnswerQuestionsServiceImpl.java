package com.qiqi.jiaoyou_app.serviceImpl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qiqi.jiaoyou_app.mapper.TaskAnswerQuestionsMapper;
import com.qiqi.jiaoyou_app.pojo.TaskAnswerQuestions;
import com.qiqi.jiaoyou_app.service.TaskAnswerQuestionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * (TaskAnswerQuestions)表服务实现类
 * taskAnswerQuestions
 *
 * @author cfx
 * @since 2020-12-29 15:56:51
 */
@Slf4j
@Service("taskAnswerQuestionsService")
public class TaskAnswerQuestionsServiceImpl extends ServiceImpl<TaskAnswerQuestionsMapper, TaskAnswerQuestions> implements TaskAnswerQuestionsService
{

}