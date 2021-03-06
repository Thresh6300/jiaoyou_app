package com.qiqi.jiaoyou_app.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("club_task")
@ApiModel(value = "club_task对象", description = "--实体类")
public class ClubTask extends BaseEntity
{

    private static final long serialVersionUID = 703891741182921879L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "任务内容")
    @TableField("task_content")
    private String taskContent;

    @ApiModelProperty(value = "图片地址")
    @TableField("task_url")
    private String taskUrl;

    @ApiModelProperty(value = "参与人数")
    @TableField("task_number")
    private Integer taskNumber;

    @ApiModelProperty(value = "未完成人数")
    @TableField("task_not_number")
    private Integer taskNotNumber;

    @ApiModelProperty(value = "已完成人数")
    @TableField("task_has_number")
    private Integer taskHasNumber;

    @ApiModelProperty(value = "会员id/发布人id")
    @TableField("member_id")
    private Integer memberId;

    @ApiModelProperty(value = "俱乐部id")
    @TableField("club_id")
    private Integer clubId;

    @ApiModelProperty(value = "任务标签id")
    @TableField("gameLabelId")
    private Integer gameLabelId;

    @ApiModelProperty(value = "0未过期;1过期")
    @TableField("past_status")
    private String pastStatus;

    @ApiModelProperty(value = "任务标签标题")
    @TableField(exist = false)
    private String gameTitle;

    @ApiModelProperty(value = "任务标签图标")
    @TableField(exist = false)
    private String gameIcon;

    @ApiModelProperty(value = "1答题任务  2视频任务 3图片任务 ")
    @TableField(exist = false)
    private String gameType;

    @ApiModelProperty(value = "用户id")
    @TableField(exist = false)
    private String userId;

    @ApiModelProperty(value = "0待审核;1需修改;2通过;3完美;4没有回答")
    @TableField(exist = false)
    private String isAnswer = "4";

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "创建时间")
    @TableField(exist = false)
    private String taskTime;

}