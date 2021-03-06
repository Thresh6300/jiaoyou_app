package com.qiqi.jiaoyou_app.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 钻石消费记录
 * </p>
 *
 * @author sunlaiqian
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Diamondconsumptionrecord对象", description="钻石消费记录")
public class Diamondconsumptionrecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "接收方会员ID")
    @TableField("memberId")
    private Integer memberId;

    @ApiModelProperty(value = "发送方会员ID")
    @TableField("sendMemberId")
    private Integer sendMemberId;

    @ApiModelProperty(value = "类型 1：一对一红包 2：一对多发红包 3：一对多抢红包 4： 购买礼物")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "消费时间")
    @TableField("consumptionTime")
    private Date consumptionTime;

    @ApiModelProperty(value = "消费数量")
    @TableField("consumptionSize")
    private Long consumptionSize;

    @ApiModelProperty(value = "剩余数量")
    @TableField("surplusSzie")
    private Long surplusSzie;


}
