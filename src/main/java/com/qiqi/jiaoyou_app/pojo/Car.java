package com.qiqi.jiaoyou_app.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 车辆表
 * </p>
 *
 * @author sunlaiqian
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Car对象", description="车辆表")
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    @JsonIgnore
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
