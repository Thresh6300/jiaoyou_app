package com.qiqi.jiaoyou_app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
@ApiModel(value="RechargeRecord对象", description="账单记录汇总")
public class RechargeRecordVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")

    private Integer id;

    @ApiModelProperty(value = "会员ID")
    private Integer memberId;

    @ApiModelProperty(value = "1：增加 2：消耗")
    private Integer type;

    @ApiModelProperty(value = "数量")
    private Long runSize;

    @ApiModelProperty(value = "余额")
    private Long surplus;

    @ApiModelProperty(value = "充值时间")
    private Date addTime;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "1：会员 2：黑卡 3：金钻  4：银钻 5：黑钻6:小喇叭，")
    private Integer currency;




}
