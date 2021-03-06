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
@TableName("club")
@ApiModel(value = "club对象", description = "俱乐部--实体类")
public class Club extends BaseEntity
{

    private static final long serialVersionUID = -47296732780677113L;

    @ApiModelProperty(value = "俱乐部id")
    @TableId(value = "club_id", type = IdType.AUTO)
    private Integer clubId;

    @ApiModelProperty(value = "创建人id")
    @TableField("memberId")
    private Integer memberId;

    @ApiModelProperty(value = "名称")
    @TableField("club_name")
    private String clubName;

    @ApiModelProperty(value = "图标 创建人的头像")
    @TableField("club_icon")
    private String clubIcon;

    @ApiModelProperty(value = "等级")
    @TableField("club_grade")
    private Integer clubGrade;

    @ApiModelProperty(value = "简介")
    @TableField("club_introduction")
    private String clubIntroduction;

    @ApiModelProperty(value = "每日工资")
    @TableField("wage")
    private Long wage;

    @ApiModelProperty(value = "每日发工资的时间")
    @TableField(value = "wage_time")
    private String wageTime;

    @ApiModelProperty(value = "公告")
    @TableField("club_notice")
    private String clubNotice;


    @ApiModelProperty(value = "0潜力俱乐部;1阔绰俱乐部")
    @TableField("type")
    private String type;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "累计工资")
    @TableField("total_wages")
    private Long totalWages;

    @ApiModelProperty(value = "隐私模式 0开; 1关")
    @TableField("privacy_mode")
    private String privacyMode;

    @ApiModelProperty(value = "可以设置几个俱乐部秘书")
    @TableField("secretary_number")
    private Integer secretaryNumber;

    @ApiModelProperty(value = "可以设置几个俱乐部秘书")
    @TableField("club_sort")
    private Integer clubSort;

    @ApiModelProperty(value = "今日是否发了工资 0没有发;1发了")
    @TableField("today_wage")
    private String todayWage;

    @ApiModelProperty(value = "礼物id")
    @TableField(exist = false)
    private Integer giftId;

    @ApiModelProperty(value = "消息免打扰 0开-打扰; 1关-不打扰")
    @TableField(exist = false)
    private String noDisturbing;

    @ApiModelProperty(value = "在不在俱乐部 0在;1不在")
    @TableField(exist = false)
    private String inClub = "1";

    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private Member member;


    //不够发三天工资提醒次数,(每天早晚8点提醒,一共是三天六次)
    private Integer alertNum;

    @ApiModelProperty(value = "除去俱乐部管理员,有多少成员",hidden = true)
    @TableField(exist = false)
    private Integer sum;


}