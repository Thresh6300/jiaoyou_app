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
@TableName("order_manage")
@ApiModel(value = "order_manage对象", description = "订单信息表--实体类")
public class OrderManage extends BaseEntity
{

    private static final long serialVersionUID = 968516673956540946L;

    @ApiModelProperty(value = "订单信息表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id（购买者）")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "订单号码")
    @TableField("order_number")
    private String orderNumber;

    @ApiModelProperty(value = "订单状态（1：待支付，2：待收货，3：已完成，4：已取消）")
    @TableField("order_status")
    private String orderStatus;

    @ApiModelProperty(value = "店铺")
    @TableField("business_name")
    private String businessName;

    @ApiModelProperty(value = "店铺log")
    @TableField("business_icon")
    private String businessIcon;

    @ApiModelProperty(value = "商品名称")
    @TableField("order_shop_name")
    private String orderShopName;

    @ApiModelProperty(value = "商品图片")
    @TableField("shop_img")
    private String shopImg;

    @ApiModelProperty(value = "简介图片的宽高比（宽/高）默认两位小数")
    @TableField("width")
    private String width;

    @ApiModelProperty(value = "商品简介")
    @TableField("shop_introduce")
    private String shopIntroduce;

    @ApiModelProperty(value = "商品id")
    @TableField("order_shop_id")
    private Integer orderShopId;

    @ApiModelProperty(value = "订单价格（单价）")
    @TableField("order_price")
    private BigDecimal orderPrice;

    @ApiModelProperty(value = "商品规格")
    @TableField("order_shop_specs")
    private String orderShopSpecs;

    @ApiModelProperty(value = "地址表id")
    @TableField("order_address_id")
    private Integer orderAddressId;

    @ApiModelProperty(value = "实付金额")
    @TableField("order_real_price")
    private BigDecimal orderRealPrice;

    @ApiModelProperty(value = "订单商品数量")
    @TableField("order_shop_number")
    private Integer orderShopNumber;

    @ApiModelProperty(value = "收货人（名字）")
    @TableField("recever_people_name")
    private String receverPeopleName;

    @ApiModelProperty(value = "收货人手机号")
    @TableField("recever_phone")
    private String receverPhone;

    @ApiModelProperty(value = "收货人地址")
    @TableField("recever_address")
    private String receverAddress;

    @ApiModelProperty(value = "删除状态（0：删除，1：正常）")
    @TableField("order_del_status")
    private String orderDelStatus;

    @ApiModelProperty(value = "下单时间（创建时间）")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT)
    private Date updateTime;

    @ApiModelProperty(value = "服务类型 0：商品 1 服务预订")
    @TableField(value = "type")
    private String type;

    @ApiModelProperty(value = "付款方式0微信;1支付宝")
    @TableField(value = "pay_type")
    private String payType;

    @ApiModelProperty(value = "支付状态0待支付;1支付成功;2支付失败")
    @TableField(value = "pay_status")
    private String payStatus;

    @ApiModelProperty(value = "快递单号")
    @TableField(value = "express_num")
    private String expressNum;

    @ApiModelProperty(value = "快递公司编码")
    @TableField(value = "express_code")
    private String expressCode;

    @ApiModelProperty(value = "快递公司名称")
    @TableField(value = "express_name")
    private String expressName;

    @ApiModelProperty(value = "出发地城市，省-市-区")
    @TableField(value = "express_from")
    private String expressFrom;

    @ApiModelProperty(value = "目的地城市，省-市-区")
    @TableField(value = "express_to")
    private String expressTo;

    @ApiModelProperty(value = "买家留言")
    @TableField(value = "buyer_message")
    private String buyerMessage;


    @ApiModelProperty(value = "是否评价1是;0不是")
    @TableField(value = "is_evaluate")
    private String isEvaluate="0";

}