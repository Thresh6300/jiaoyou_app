package com.qiqi.jiaoyou_app.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("shop_manage")
@ApiModel(value = "shop_manage对象", description = "商品管理--实体类")
public class ShopManage extends BaseEntity
{

    private static final long serialVersionUID = 902595693881268351L;

    @ApiModelProperty(value = "商品管理id")
    @TableId(value = "id", type = IdType.AUTO)

    private Integer id;
    @ApiModelProperty(value = "商品名称")
    @TableField("shop_name")
    private String shopName;

    @ApiModelProperty(value = "商品单价")
    @TableField("shop_price")
    private BigDecimal shopPrice;

    @ApiModelProperty(value = "商品规格")
    @TableField("shop_specs")
    private String shopSpecs;

    @ApiModelProperty(value = "商品简介")
    @TableField("shop_introduce")
    private String shopIntroduce;

    @ApiModelProperty(value = "商品图片")
    @TableField("shop_img")
    private String shopImg;

    @ApiModelProperty(value = "发货地")
    @TableField("place")
    private String place;

    @ApiModelProperty(value = "付款人数")
    @TableField("payment_number")
    private Integer paymentNumber;

    @ApiModelProperty(value = "店铺")
    @TableField("business_name")
    private String businessName;

    @ApiModelProperty(value = "店铺log")
    @TableField("business_icon")
    private String businessIcon;

    @ApiModelProperty(value = "商品状态(0：删除，1：正常)")
    @TableField("shop_status")
    private String shopStatus;

    @ApiModelProperty(value = "是否置顶（0：未置顶，1：置顶）")
    @TableField("shop_is_top")
    private String shopIsTop;

    @ApiModelProperty(value = "0商品;1服务")
    @TableField("type")
    private String type;

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

    @ApiModelProperty(value = "喜欢 0：喜欢 1：不喜欢")
    @TableField(exist = false)
    private String isLike = "1";

    @ApiModelProperty(value = "服务类别id")
    @TableField(exist = false)
    private String serverTypeId;

    @ApiModelProperty(value = "服务类别id")
    @TableField(exist = false)
    private String serverTitle;

    @ApiModelProperty(value = "会员的id")
    @TableField(exist = false)
    private Integer collectUserId;

    @ApiModelProperty(value = "商品的id")
    @TableField(exist = false)
    private Integer collectShopId;

    @ApiModelProperty(value = "服务的id")
    @TableField(exist = false)
    private Integer collectServerId;

    @ApiModelProperty(value = "开始的价格")
    @TableField(exist = false)
    private BigDecimal startPrice;

    @ApiModelProperty(value = "结束的价格")
    @TableField(exist = false)
    private BigDecimal endPrice;

    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private String shopPriceIsAsc = "ASC";


    @JsonIgnore
    public String getShopPriceIsAsc()
    {

	  return shopPriceIsAsc;
    }
    @JsonProperty
    public void setShopPriceIsAsc(String shopPriceIsAsc)
    {
	  if ("0".equals(shopPriceIsAsc))return;
	  if ("1".equals(shopPriceIsAsc))
		this.shopPriceIsAsc = "DESC";
    }

    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private String paymentNumberIsAsc = "ASC";


    @JsonIgnore
    public String getPaymentNumberIsAsc()
    {

	  return shopPriceIsAsc;
    }
    @JsonProperty
    public void setPaymentNumberIsAsc(String paymentNumberIsAsc)
    {
	  if ("0".equals(paymentNumberIsAsc))return;
	  if ("1".equals(paymentNumberIsAsc))
		this.paymentNumberIsAsc = "DESC";
    }
}