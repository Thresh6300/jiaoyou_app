package com.qiqi.jiaoyou_app.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("love_heart_donation_logs")
@ApiModel(value = "love_heart_donation_logs对象", description = "爱心捐赠记录表--实体类")
public class LoveHeartDonationLogs extends BaseEntity
{

    private static final long serialVersionUID = -86882244711568060L;

    @ApiModelProperty(value = "爱心捐赠记录表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "捐赠用户id")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "捐赠项目id")
    @TableField("donation_project_id")
    private Integer donationProjectId;

    @ApiModelProperty(value = "捐赠时间")
    @TableField(value = "donation_time", fill = FieldFill.INSERT)
    private Date donationTime;

    @ApiModelProperty(value = "捐赠金额")
    @TableField("donation_money")
    private BigDecimal donationMoney;

    @ApiModelProperty(value = "备注")
    @TableField("donation_remark")
    private String donationRemark;

    @ApiModelProperty(value = "备注")
    @TableField("head")
    private String head;


}