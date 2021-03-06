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
 * 银行卡表
 * </p>
 *
 * @author sunlaiqian
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Bankcard对象", description="银行卡表")
public class Bankcard implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "会员id")
    @TableField("memberId")
    private Integer memberId;

    @ApiModelProperty(value = "开户行")
    @TableField("openingBank")
    private String openingBank;

    @ApiModelProperty(value = "姓名")
    @TableField("bankMemberName")
    private String bankMemberName;

    @ApiModelProperty(value = "银行卡号")
    @TableField("bankNumber")
    private String bankNumber;

    @ApiModelProperty(value = "添加时间")
    @TableField("addTime")
    private Date addTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("editTime")
    private Date editTime;

    @ApiModelProperty(value = "权重")
    @TableField("weight")
    private Integer weight;


}
