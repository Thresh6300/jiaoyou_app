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
@TableName("advance_order")
@ApiModel(value = "advance_order对象", description = "--实体类")
public class AdvanceOrder extends BaseEntity
{

    private static final long serialVersionUID = 558627237554130236L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "到店时间")
    @TableField(value = "to_shop_time", fill = FieldFill.INSERT)
    private Date toShopTime;

    @ApiModelProperty(value = "商品id")
    @TableField("shop_id")
    private Integer shopId;

    @ApiModelProperty(value = "商品图片")
    @TableField("shop_url")
    private String shopUrl;

    @ApiModelProperty(value = "商品名称")
    @TableField("shop_name")
    private String shopName;

    @ApiModelProperty(value = "称呼")
    @TableField("call")
    private String call;

    @ApiModelProperty(value = "入住人数")
    @TableField("check_in")
    private Integer checkIn;

    @ApiModelProperty(value = "订单状态（0:进行中;1：已完成，2：已取消）")
    @TableField("status")
    private String status;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Integer userId;


    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "店铺名称")
    @TableField("business_name")
    private String businessName;

    @ApiModelProperty(value = "店铺log")
    @TableField("business_icon")
    private String businessIcon;

    @ApiModelProperty(value = "商品简介")
    @TableField("shop_introduce")
    private String shopIntroduce;

    @ApiModelProperty(value = "简介图片的宽高比（宽/高）默认两位小数")
    @TableField("width")
    private String width;


}