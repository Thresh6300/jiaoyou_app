package com.qiqi.jiaoyou_app.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("address")
@ApiModel(value = "address对象", description = "--实体类")
public class Address extends BaseEntity
{

    private static final long serialVersionUID = 572604270930841493L;

    @ApiModelProperty(value = "id")
    @TableId(value = "address_id", type = IdType.AUTO)
    private Integer addressId;

    @ApiModelProperty(value = "收货人姓名")
    @TableField("consignee_name")
    private String consigneeName;

    @ApiModelProperty(value = "收货人地址")
    @TableField("consignee_address")
    private String consigneeAddress;

    @ApiModelProperty(value = "收货人联系方式")
    @TableField("consignee_phone")
    private String consigneePhone;

    @ApiModelProperty(value = "收货地址状态0默认;1不默认")
    @TableField("status")
    private String status;

    @ApiModelProperty(value = "用户id")
    @TableField("address_user_id")
    private Integer addressUserId;

    @ApiModelProperty(value = "省")
    @TableField("province")
    private String province;

    @ApiModelProperty(value = "市")
    @TableField("city")
    private String city;

    @ApiModelProperty(value = "区")
    @TableField("district")
    private String district;


}