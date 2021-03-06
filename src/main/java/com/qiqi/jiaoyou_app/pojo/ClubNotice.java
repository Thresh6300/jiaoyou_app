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
@TableName("club_notice")
@ApiModel(value = "club_notice对象", description = "--实体类")
public class ClubNotice extends BaseEntity
{

    private static final long serialVersionUID = -59662759631788683L;

    @ApiModelProperty(value = "id")
    @TableId(value = "buddy_notice_id", type = IdType.AUTO)
    private Integer buddyNoticeId;

    @ApiModelProperty(value = "申请人id")
    @TableField("buddy_notice_user_id")
    private Integer buddyNoticeUserId;

    @ApiModelProperty(value = "俱乐部id")
    @TableField("buddy_notice_club_id")
    private Integer buddyNoticeClubId;

    @ApiModelProperty(value = "申请人名称")
    @TableField("buddy_notice_user_name")
    private String buddyNoticeUserName;

    @ApiModelProperty(value = "申请人头像")
    @TableField("hend")
    private String hend;

    @ApiModelProperty(value = "申请审核 0审核中;1通过2不通过")
    @TableField("buddy_notice_status")
    private String buddyNoticeStatus;

    @ApiModelProperty(value = "申请标题")
    @TableField("buddy_notice_title")
    private String buddyNoticeTitle;

    @ApiModelProperty(value = "俱乐部创建人id")
    @TableField("buddy_notice_club_user_id")
    private Integer buddyNoticeClubUserId;

    @ApiModelProperty(value = "理由")
    @TableField("buddy_notice_reason")
    private String buddyNoticeReason;

    @ApiModelProperty(value = "理由")
    @TableField("is_reads")
    private String isReads;

    @ApiModelProperty(value = "0已读;1未读")
    @TableField(exist = false)
    private String isRead = "0";

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "0申请者;1创建者")
    @TableField(exist = false)
    private String type;
}