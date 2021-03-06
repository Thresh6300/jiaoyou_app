package com.qiqi.jiaoyou_app.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.qiqi.jiaoyou_app.pojo.AcceptOfflineActivities;
import com.qiqi.jiaoyou_app.pojo.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 线下活动列表
 * </p>
 *
 * @author GR123
 * @since 2020-06-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("send_offline_activities")
public class SendOfflineActivities extends BaseEntity
{

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 发起人id
     */
    private Integer sendMemberId;
    /**
     * 活动人数
     */
    private Integer perSize;
    /**
     * 发起人用户头像
     */
    private String sendMemberHead;
    /**
     * 昵称
     */
    private String sendMemberNickName;
    /**
     * 发起人性别
     */
    private Integer sendMemberSex;
    /**
     * 发起人年龄
     */
    private Integer sendMemberAge;
    /**
     * 活动主题
     */
    private String activityTheme;
    /**
     * 地点
     */
    private String address;
    /**
     * 平均钻石数量
     */
    private Integer averageDiamondsSize;
    /**
     * 发起时间
     */
    private Date addTime;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 状态 1  待报名 2 待对方确认3待自己确认4审核中5审核通过6审核未通过7已取消
     */
    private Integer state;
    /**
     * 原因
     */
    private String reason;
    /**
     * 标签
     */
    private String lable;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 经度
     */
    private String longitude;

    @ApiModelProperty(value = "背景图")
    @TableField("backgroundImages")
    private String backgroundImages;

    @ApiModelProperty(value = "已读会员数组")
    @TableField("readMembers")
    private String readMembers;

    @ApiModelProperty(value = "0没有;1有")
    @TableField("is_overdueout")
    private String isOverdueout;


    // 当前会员是否已读
    @TableField(exist = false)
    private Boolean notRead = false;
    /**
     * 开始时间
     */
    @TableField(exist = false)
    private String startTimeStr;

    /**
     * 累计人数
     */
    @TableField(exist = false)
    private Integer cumulativeNumberOfPeople;


    @TableField(exist = false)
    private List<AcceptOfflineActivities> list;

    /**
     * 是否是发起人 0是 ;1不是
     */
    @TableField(exist = false)
    private String type = "1";
    /**
     * 搜索
     */
    @TableField(exist = false)
    private String seacrh;

}
