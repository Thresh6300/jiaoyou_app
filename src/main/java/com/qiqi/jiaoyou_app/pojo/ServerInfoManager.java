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
@TableName("server_info_manager")
@ApiModel(value = "server_info_manager对象", description = "服务管理信息表--实体类")
public class ServerInfoManager extends BaseEntity
{

    private static final long serialVersionUID = -79985064544616151L;

    @ApiModelProperty(value = "服务管理信息表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "服务类型id")
    @TableField("server_type_id")
    private Integer serverTypeId;

    @ApiModelProperty(value = "服务信息封面图")
    @TableField("server_cover")
    private String serverCover;

    @ApiModelProperty(value = "服务标题")
    @TableField("server_title")
    private String serverTitle;

    @ApiModelProperty(value = "价格")
    @TableField("server_price")
    private Double serverPrice;

    @ApiModelProperty(value = "图文简介")
    @TableField("server_introduce")
    private String serverIntroduce;

    @ApiModelProperty(value = "是否置顶 0：未置顶 1：置顶")
    @TableField("server_is_top")
    private String serverIsTop;

    @ApiModelProperty(value = "城市")
    @TableField("city")
    private String city;

    @ApiModelProperty(value = "简介图片的宽高比（宽/高）默认两位小数")
    @TableField("width")
    private String width;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;


    @TableField(value = "update_time", fill = FieldFill.INSERT)
    private Date updateTime;

    @ApiModelProperty(value = "状态 0：删除 1：正常")
    @TableField("server_status")
    private String serverStatus;

    @ApiModelProperty(value = "发货地")
    @TableField("place")
    private String place;

    @ApiModelProperty(value = "付款人数")
    @TableField("payment_number")
    private String paymentNumber;

    @ApiModelProperty(value = "0商品;1服务")
    @TableField("type")
    private String type;

    @ApiModelProperty(value = "规格")
    @TableField("shop_specs")
    private String shopSpecs;

    @ApiModelProperty(value = "店铺")
    @TableField("business_name")
    private String businessName;

    @ApiModelProperty(value = "店铺log")
    @TableField("business_icon")
    private String businessIcon;

    @ApiModelProperty(value = "喜欢 0：喜欢 1：不喜欢")
    @TableField(exist = false)
    private String isLike = "1";

}