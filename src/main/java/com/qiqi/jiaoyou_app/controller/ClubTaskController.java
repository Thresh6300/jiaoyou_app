package com.qiqi.jiaoyou_app.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.qiqi.jiaoyou_app.pojo.ClubTask;
import com.qiqi.jiaoyou_app.pojo.TaskAnswerQuestions;
import com.qiqi.jiaoyou_app.service.ClubTaskService;
import com.qiqi.jiaoyou_app.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * (ClubTask)表控制层
 *
 * @author cfx
 * @since 2020-11-28 10:43:00
 */
@RestController
@RequestMapping("/jiaoyou_app/clubTask")
@Api(tags = "俱乐部任务管理")
public class ClubTaskController
{
    /**
     * 服务对象
     *
     * @author cfx
     * @since 2020-11-28 10:43:00
     */
    @Autowired
    private ClubTaskService clubTaskService;

    @ApiOperation(value = "字段说明(已完成)")
    @PostMapping(value = "/club")
    public void addClub(ClubTask club){

    }

    @ApiOperation(value = "俱乐部任务标签")
    @GetMapping(value = "/getTaskLabel")
    public ResultUtils getTaskLabel(){
	  return clubTaskService.getTaskLabel();
    }

    @ApiOperation(value = "俱乐部任务题库")
    @GetMapping(value = "/getTaskGameBank")
    public ResultUtils getTaskGameBank(){
	  return clubTaskService.getTaskGameBank();
    }

    @ApiOperation(value = "俱乐部发布任务(已完成)")
    @PostMapping(value = "/addTask")
    @DynamicParameters(name = "addTask",properties = {
		  @DynamicParameter(name = "gameLabelId",value = "类型id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "taskContent",value = "任务内容",required = true,dataTypeClass = String.class),
		  @DynamicParameter(name = "taskUrl",value = "图片地址",required = true,dataTypeClass = String.class),
		  @DynamicParameter(name = "clubId",value = "俱乐部id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "memberId",value = "会员id/发布人id",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils addTask(@RequestBody JSONObject jsonObject){
	  ClubTask clubTask = JSON.toJavaObject(jsonObject, ClubTask.class);
	  return clubTaskService.addTask(clubTask);
    }


    @ApiOperation(value = "俱乐部/我布置的,任务列表(已完成)")
    @PostMapping(value = "/taskList")
    @DynamicParameters(name = "taskList",properties = {
		  @DynamicParameter(name = "clubId",value = "俱乐部id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "memberId",value = "发布人id",required = false,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "userId",value = "用户id",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils taskList(@RequestBody JSONObject jsonObject){
	  ClubTask clubTask = JSON.toJavaObject(jsonObject, ClubTask.class);
	  return clubTaskService.taskList(clubTask);
    }

    @ApiOperation(value = "俱乐部任务详情(已完成)")
    @PostMapping(value = "/taskDetails")
    @DynamicParameters(name = "taskDetails",properties = {
		  @DynamicParameter(name = "id",value = "俱乐部任务id",required = true,dataTypeClass = Integer.class),
    })
    public ResultUtils taskDetails(@RequestBody JSONObject jsonObject){
	  ClubTask clubTask = JSON.toJavaObject(jsonObject, ClubTask.class);
	  return clubTaskService.taskDetails(clubTask);
    }


    @ApiOperation(value = "俱乐部成员回答问题(已完成)")
    @PostMapping(value = "/addTaskQuestions")
    @ApiImplicitParams({
		  @ApiImplicitParam(name = "taskId",value = "任务id",required = true,dataTypeClass = Integer.class),
		  @ApiImplicitParam(name = "memberId",value = "回答人id",required = true,dataTypeClass = Integer.class),
		  @ApiImplicitParam(name = "answer",value = "任务答案",required = true,dataTypeClass = String.class),
		  @ApiImplicitParam(name = "imgUrl",value = "图片地址",required = true,dataTypeClass = String.class),
		  @ApiImplicitParam(name = "videoUrl",value = "视频地址",required = true,dataTypeClass = String.class),
    })
    public ResultUtils addTaskQuestions(@RequestBody JSONObject jsonObject){
	  TaskAnswerQuestions taskAnswerQuestions = JSON.toJavaObject(jsonObject, TaskAnswerQuestions.class);
	  return clubTaskService.taskQuestions(taskAnswerQuestions);
    }


    @ApiOperation(value = "俱乐部成员回答问题答案的审核(已完成)")
    @PostMapping(value = "/taskQuestionsStatus")
    @DynamicParameters(name = "taskQuestionsStatus",properties = {
		  @DynamicParameter(name = "questionsId",value = "任务答案的id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "taskRating",value = "0待审核;1需修改;2通过;3完美",required = true,dataTypeClass = String.class),
    })
    public ResultUtils taskQuestionsStatus(@RequestBody JSONObject jsonObject){
	  TaskAnswerQuestions taskAnswerQuestions = JSON.toJavaObject(jsonObject, TaskAnswerQuestions.class);
	  return clubTaskService.updateTaskQuestions(taskAnswerQuestions);
    }


    @ApiOperation(value = "修改俱乐部成员回答问题的答案(已完成)")
    @PostMapping(value = "/updateTaskQuestions")
    @DynamicParameters(name = "updateTaskQuestions",properties = {
		  @DynamicParameter(name = "taskId",value = "任务id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "memberId",value = "回答人id",required = true,dataTypeClass = Integer.class),
		  @DynamicParameter(name = "answer",value = "任务答案",required = true,dataTypeClass = String.class),
		  @DynamicParameter(name = "imgUrl",value = "图片地址",required = true,dataTypeClass = String.class),
		  @DynamicParameter(name = "videoUrl",value = "视频地址",required = true,dataTypeClass = String.class),
    })
    public ResultUtils updateTaskQuestions(@RequestBody JSONObject jsonObject){
	  TaskAnswerQuestions taskAnswerQuestions = JSON.toJavaObject(jsonObject, TaskAnswerQuestions.class);
	  return clubTaskService.alterTaskQuestions(taskAnswerQuestions);
    }

    @ApiOperation(value = "俱乐部成员回答问题的答案详情(已完成)")
    @PostMapping(value = "/getTaskQuestions")
    @ApiImplicitParams({
		  @ApiImplicitParam(name = "taskId",value = "任务id",required = true,dataTypeClass = Integer.class),
		  @ApiImplicitParam(name = "memberId",value = "回答人id",required = true,dataTypeClass = Integer.class),

    })
    public ResultUtils getTaskQuestions(@RequestBody JSONObject jsonObject){
	  TaskAnswerQuestions taskAnswerQuestions = JSON.toJavaObject(jsonObject, TaskAnswerQuestions.class);
	  return clubTaskService.getTaskQuestions(taskAnswerQuestions);
    }

    @ApiOperation(value = "俱乐部任务未读")
    @GetMapping(value = "/notAccomplish")
    @ApiImplicitParams({
		  @ApiImplicitParam(name = "clubId",value = "俱乐部id",required = true,dataTypeClass = Integer.class),
		  @ApiImplicitParam(name = "memberId",value = "用户id",required = true,dataTypeClass = Integer.class),

    })
    public ResultUtils notAccomplish(Integer clubId,Integer memberId){
	  return clubTaskService.notAccomplish(clubId,memberId);
    }
}