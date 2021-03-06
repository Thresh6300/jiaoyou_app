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
 * 礼物记录表
 * </p>
 *
 * @author sunlaiqian
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Giftconsumption对象", description="礼物记录表")
public class Giftconsumption implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "REVISION", type = IdType.AUTO)
    private Integer revision;

    @ApiModelProperty(value = "接收方ID")
    @TableField("memberId")
    private Integer memberId;

    @ApiModelProperty(value = "发送方id")
    @TableField("sendMemberId")
    private Integer sendMemberId;

    @ApiModelProperty(value = "发送方头像")
    @TableField("sendMemberHead")
    private String sendMemberHead;

    @ApiModelProperty(value = "发送方昵称")
    @TableField("sendMemberNickName")
    private String sendMemberNickName;

    @ApiModelProperty(value = "车标")
    @TableField("sendLable")
    private String sendLable;

    @ApiModelProperty(value = "礼物ID")
    @TableField("giftId")
    private Integer giftId;

    @ApiModelProperty(value = "礼物图片")
    @TableField("giftImages")
    private String giftImages;

    @ApiModelProperty(value = "礼物名称")
    @TableField("giftName")
    private String giftName;

    @ApiModelProperty(value = "礼物数量")
    @TableField("giftSize")
    private Integer giftSize;

    @ApiModelProperty(value = "接收时间")
    @TableField("addTime")
    private Date addTime;

    @ApiModelProperty(value = "结束时间")
    @TableField("end_time")
    private Date endTime;

    @ApiModelProperty(value = "该礼物是否被接收方提现")
    @TableField("drawInProportion")
    private Integer drawInProportion;

    @ApiModelProperty(value = "申请提现时间")
    @TableField("withdrawTime")
    private Date withdrawTime;





}
