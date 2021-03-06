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
@TableName("club_buddy")
@ApiModel(value = "club_buddy对象", description = "俱乐部好友表--实体类")
public class ClubBuddy extends BaseEntity
{

    private static final long serialVersionUID = -86158719454179620L;

    @ApiModelProperty(value = "俱乐部好友表id")
    @TableId(value = "buddy_id", type = IdType.AUTO)
    private Integer buddyId;

    @ApiModelProperty(value = "自身id/创建人的会员id")
    @TableField("oneself_id")
    private Integer oneselfId;

    @ApiModelProperty(value = "会员id")
    @TableField("member_id")
    private Integer memberId;

    @ApiModelProperty(value = "0不是秘书1是秘书")
    @TableField("secretary_status")
    private String secretaryStatus;

    @ApiModelProperty(value = "俱乐部id")
    @TableField("club_id")
    private Integer clubId;

    @ApiModelProperty(value = "获取的金钻数量")
    @TableField("diamondNumber")
    private Long diamondNumber;

    @ApiModelProperty(value = "每日获取的金钻数量")
    @TableField("diamondDayNumber")
    private Long diamondDayNumber;

    @ApiModelProperty(value = "每周获取的金钻数量")
    @TableField("diamondWeekNumber")
    private Long diamondWeekNumber;

    @ApiModelProperty(value = "每月获取的金钻数量")
    @TableField("diamondMothNumber")
    private Long diamondMothNumber;

    @ApiModelProperty(value = "会员id字符串数组")
    @TableField(exist = false)
    private String memberIds;

    @ApiModelProperty(value = "会员头像")
    @TableField(exist = false)
    private String head;

    @ApiModelProperty(value = "会员昵称")
    @TableField(exist = false)
    private String nickName;

    @ApiModelProperty(value = "消息免打扰 0开-打扰; 1关-不打扰")
    @TableField("no_disturbing")
    private String noDisturbing;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "得金钻时间")
    @TableField("diamond_time")
    private Date diamondTime;
}