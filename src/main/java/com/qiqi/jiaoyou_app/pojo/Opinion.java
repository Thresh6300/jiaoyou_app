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
 * 意见表
 * </p>
 *
 * @author sunlaiqian
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Opinion对象", description="意见表")
public class Opinion implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "会员id")
    @TableField("memberId")
    private Integer memberId;

    @ApiModelProperty(value = "会员昵称")
    @TableField("memberName")
    private String memberName;

    @ApiModelProperty(value = "会员头像")
    @TableField("memberHead")
    private String memberHead;

    @ApiModelProperty(value = "会员手机号")
    @TableField("memberPhone")
    private String memberPhone;

    @ApiModelProperty(value = "反馈内容")
    private String context;

    @ApiModelProperty(value = "反馈图片")
    private String images;

    @ApiModelProperty(value = "反馈时间")
    @TableField("addTime")
    private Date addTime;

    @ApiModelProperty(value = "处理状态 1:已处理2:未处理")
    private Integer state;

    @ApiModelProperty(value = "处理意见")
    private String reason;

    @ApiModelProperty(value = "处理时间")
    @TableField("handleTime")
    private Date handleTime;


}
