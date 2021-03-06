package com.qiqi.jiaoyou_app.service;

import com.baomidou.mybatisplus.service.IService;
import com.qiqi.jiaoyou_app.pojo.ClubTask;
import com.qiqi.jiaoyou_app.pojo.TaskAnswerQuestions;
import com.qiqi.jiaoyou_app.util.ResultUtils;


/**
 * (ClubTask)表服务接口
 *
 * @author cfx
 * @since 2020-11-28 10:42:59
 */
public interface ClubTaskService extends IService<ClubTask>
{


    ResultUtils addTask(ClubTask clubTask);

    ResultUtils taskList(ClubTask clubTask);

    ResultUtils getTaskLabel();

    ResultUtils getTaskGameBank();

    ResultUtils taskQuestions(TaskAnswerQuestions taskAnswerQuestions);

    ResultUtils updateTaskQuestions(TaskAnswerQuestions taskAnswerQuestions);

    ResultUtils taskDetails(ClubTask clubTask);

    ResultUtils getTaskQuestions(TaskAnswerQuestions taskAnswerQuestions);

    ResultUtils alterTaskQuestions(TaskAnswerQuestions taskAnswerQuestions);

    TaskAnswerQuestions selectTaskQuestions(TaskAnswerQuestions taskAnswerQuestions);

    ResultUtils notAccomplish(Integer clubId,Integer memberId);
}