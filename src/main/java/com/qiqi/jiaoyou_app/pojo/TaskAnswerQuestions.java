package com.qiqi.jiaoyou_app.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("task_answer_questions")
@ApiModel(value = "task_answer_questions对象", description = "--实体类")
public class TaskAnswerQuestions extends BaseEntity
{

    private static final long serialVersionUID = -99062219054561300L;

    @TableId(value = "questions_id", type = IdType.AUTO)
    private Integer questionsId;

    @ApiModelProperty(value = "用户id")
    @TableField("member_id")
    private Integer memberId;

    @ApiModelProperty(value = "昵称")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty(value = "任务id")
    @TableField("task_id")
    private Integer taskId;

    @ApiModelProperty(value = "任务答案")
    @TableField("answer")
    private String answer;

    @ApiModelProperty(value = "0待审核;1需修改;2通过;3完美")
    @TableField("task_rating")
    private String taskRating;

    @ApiModelProperty(value = "图片地址")
    @TableField("img_url")
    private String imgUrl;

    @ApiModelProperty(value = "视频地址")
    @TableField("video_url")
    private String videoUrl;

    @ApiModelProperty(value = "每日获取的金钻数量")
    @TableField("diamondDayNumber")
    private Long diamondDayNumber;

    @ApiModelProperty(value = "余额")
    @TableField("surplus_szie")
    private Long surplusSzie;

    @ApiModelProperty(value = "每日获取的金钻时间")
    @TableField("wage_time")
    private Date wageTime;

    @ApiModelProperty(value = "0没有;1有")
    @TableField("is_read")
    private String isRead;

}