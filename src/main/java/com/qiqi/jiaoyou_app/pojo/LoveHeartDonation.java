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
@TableName("love_heart_donation")
@ApiModel(value = "love_heart_donation对象", description = "爱心捐赠--实体类")
public class LoveHeartDonation extends BaseEntity
{

    private static final long serialVersionUID = -81648322002108114L;

    @ApiModelProperty(value = "爱心捐赠id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "标题")
    @TableField("love_title")
    private String loveTitle;

    @ApiModelProperty(value = "说明")
    @TableField("love_explain")
    private String loveExplain;

    @ApiModelProperty(value = "需要金额")
    @TableField("love_need_money")
    private String loveNeedMoney;

    @ApiModelProperty(value = "已捐赠金额")
    @TableField("love_now_money")
    private String loveNowMoney;

    @ApiModelProperty(value = "封面")
    @TableField("love_cover")
    private String loveCover;

    @ApiModelProperty(value = "0:正常，1：删除")
    @TableField("love_state")
    private String loveState;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT)
    private Date updateTime;


}