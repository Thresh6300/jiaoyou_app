package com.qiqi.jiaoyou_app.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 充值记录
 * </p>
 *
 * @author sunlaiqian
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="RechargeRecord对象", description="充值记录")
@TableName("recharge_record")
public class RechargeRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "会员ID")
    @TableField("memberId")
    private Integer memberId;

    @ApiModelProperty(value = "1：会员 2：黑卡 3：金钻  4：银钻 5：黑钻")
    @TableField("currency")
    private Integer currency;

    @ApiModelProperty(value = "1：微信 2：支付宝 3：苹果 4：系统")
    @TableField("mode")
    private Integer mode;

    @ApiModelProperty(value = "1：增加 2：消耗")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "产生数量")
    @TableField("runSize")
    private Long runSize;

    @ApiModelProperty(value = "剩余数量")
    @TableField("surplus")
    private Long surplus;

    @ApiModelProperty(value = "充值时间")
    @TableField("addTime")
    private Date addTime;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;


}
