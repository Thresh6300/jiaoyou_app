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
@TableName("evaluate")
@ApiModel(value = "evaluate对象", description = "评价--实体类")
public class Evaluate extends BaseEntity
{

    private static final long serialVersionUID = -84296245354293874L;

    @ApiModelProperty(value = "评价id")
    @TableId(value = "evaluate_id", type = IdType.AUTO)
    private Integer evaluateId;

    @ApiModelProperty(value = "评价时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "评价内容")
    @TableField("evaluate_content")
    private String evaluateContent;

    @ApiModelProperty(value = "用户id(买家id值)")
    @TableField("evaluate_user_id")
    private Integer evaluateUserId;

    @ApiModelProperty(value = "订单id")
    @TableField("evaluate_order_id")
    private Integer evaluateOrderId;

    @ApiModelProperty(value = "商品的id")
    @TableField("evaluate_shop_id")
    private Integer evaluateShopId;

    @ApiModelProperty(value = "服务的id")
    @TableField("evaluate_server_id")
    private Integer evaluateServerId;

    @ApiModelProperty(value = "描述相符")
    @TableField("describe_conform")
    private String describeConform;

    @ApiModelProperty(value = "质量满意")
    @TableField("quality_satisfaction")
    private String qualitySatisfaction;

    @ApiModelProperty(value = "服务态度")
    @TableField("service_attitude")
    private String serviceAttitude;

    @ApiModelProperty(value = "物流服务")
    @TableField("logistics_service")
    private String logisticsService;

    @ApiModelProperty(value = "图片")
    @TableField("img_url")
    private String imgUrl;


}