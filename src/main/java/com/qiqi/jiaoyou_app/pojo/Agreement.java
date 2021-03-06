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
 * 协议表
 * </p>
 *
 * @author sunlaiqian
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Agreement对象", description="协议表")
public class Agreement implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "untitled", type = IdType.AUTO)
    private Integer untitled;

    @ApiModelProperty(value = "关于我们")
    @TableField("aboutUS")
    private String aboutUS;

    @ApiModelProperty(value = "注册协议")
    @TableField("registrationAgreement")
    private String registrationAgreement;

    @ApiModelProperty(value = "会员说明")
    @TableField("memberDescription")
    private String memberDescription;

    @ApiModelProperty(value = "分享规则")
    @TableField("sharingRules")
    private String sharingRules;

    @ApiModelProperty(value = "黑卡介绍")
    @TableField("greenCard")
    private String greenCard;

    @ApiModelProperty(value = "编辑时间")
    @TableField("editTime")
    private Date editTime;

    @ApiModelProperty(value = "隐私政策")
    @TableField("privacy")
    private String privacy;

    @TableField(exist = false)
    private Integer state;


}
