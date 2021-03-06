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
import java.util.Date;

/**
 * <p>
 * 会员设置表
 * </p>
 *
 * @author sunlaiqian
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MembershipSettings对象", description="会员设置表")
@TableName("membership_settings")
public class MembershipSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "会员id")
    @TableField("memberId")
    private Integer memberId;

    @ApiModelProperty(value = "同城内可查看 1：是2：否")
    @TableField("sameCityWithinState")
    private Integer sameCityWithinState;

    @ApiModelProperty(value = "同城外可查看 1：是2：否")
    @TableField("sameCityExternalState")
    private Integer sameCityExternalState;

    @ApiModelProperty(value = "信息保密开关 1：是2：否")
    @TableField("confidentialityOfInformationState")
    private Integer confidentialityOfInformationState;

    @ApiModelProperty(value = "好友消息提醒开关 1：是2：否")
    @TableField("friendMessageState")
    private Integer friendMessageState;

    @ApiModelProperty(value = "系统消息/公告提醒开关 1：是2：否")
    @TableField("noticeState")
    private Integer noticeState;

    @ApiModelProperty(value = "我的动态回复提醒开关 1：是2：否")
    @TableField("dynamicResponseState")
    private Integer dynamicResponseState;

    @ApiModelProperty(value = "世界之窗开关 1：是2：否")
    @TableField("worldInformation")
    private Integer worldInformation;

    @ApiModelProperty(value = "编辑时间")
    @TableField("editState")
    private Date editState;


}
