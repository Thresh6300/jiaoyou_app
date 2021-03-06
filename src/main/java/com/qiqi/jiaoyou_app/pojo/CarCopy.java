package com.qiqi.jiaoyou_app.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("car_copy")
@ApiModel(value = "car_copy对象", description = "车辆表--实体类")
public class CarCopy extends BaseEntity
{

    private static final long serialVersionUID = -10813991563346751L;

    @ApiModelProperty(value = "车辆表id")
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;
    @ApiModelProperty(value = "关联会员")
    @TableField("memberId")
    @JsonIgnore
    private Integer memberId;

    @ApiModelProperty(value = "行驶证照片")
    @TableField("driving_license_photo")
    private String drivingLicensePhoto;

    @ApiModelProperty(value = "车辆照片")
    private String images;

    @ApiModelProperty(value = "车型")
    @TableField("vehicleType")
    private String vehicleType;

    @ApiModelProperty(value = "型号")
    @TableField("model")
    private String model;

    @ApiModelProperty(value = "绑定时间")
    @TableField("addTime")
    @JsonIgnore
    private Date addTime;

    @ApiModelProperty(value = "权重")
    private Integer weight;

    @ApiModelProperty(value = "删除状态 1:已删除2:未删除")
    @TableField("deledeState")
    @JsonIgnore
    private Integer deledeState;

    @ApiModelProperty(value = "审核状态1:待審核  2：審核通過 3：審核未通過")
    @TableField("auditState")
    private Integer auditState;

    @ApiModelProperty(value = "审核原因")
    @TableField("reason")
    private String reason;


}