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
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 提现申请表
 * </p>
 *
 * @author sunlaiqian
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Withdraw对象", description="提现申请表")
public class Withdraw implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "会员ID")
    @TableField("memberId")
    private Integer memberId;

    @ApiModelProperty(value = "昵称")
    @TableField("memberNickName")
    private String memberNickName;

    @ApiModelProperty(value = "头像")
    @TableField("memberHead")
    private String memberHead;

    @ApiModelProperty(value = "手机号")
    @TableField("memberPhone")
    private String memberPhone;

    @ApiModelProperty(value = "提现金额")
    private BigDecimal price;

    @ApiModelProperty(value = "实际到账金额")
    private BigDecimal money;

    @ApiModelProperty(value = "开户行")
    @TableField("bankName")
    private String bankName;

    @ApiModelProperty(value = "姓名")
    @TableField("bankMemberName")
    private String bankMemberName;

    @ApiModelProperty(value = "卡号")
    @TableField("bankNumber")
    private String bankNumber;

    @ApiModelProperty(value = "申请时间")
    @TableField("applyTime")
    private Date applyTime;

    @ApiModelProperty(value = "原因")
    private String reason;

    @ApiModelProperty(value = "审核时间")
    @TableField("examineTime")
    private Date examineTime;

    @ApiModelProperty(value = "审核状态 1:通过2:不通过3:待审核")
    @TableField("examineState")
    private Integer examineState;

    @TableField(exist = false)
    private Integer bankId;

    @ApiModelProperty(value = "余额")
    @TableField("balance")
    private BigDecimal balance;


    @ApiModelProperty(value = "1：提现记录  2：失败后返现记录")
    @TableField("oldOrNew")
    private Integer oldOrNew;

    @ApiModelProperty(value = "1:审核过  2：待审核")
    @TableField("isExamine")
    private Integer isExamine;

    @ApiModelProperty(value = "当前提现比例")
    @TableField("proportion")
    private BigDecimal proportion;


}
