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
 * 系统消息表
 * </p>
 *
 * @author sunlaiqian
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SystemMessage对象", description="系统消息表")
@TableName("system_message")
public class SystemMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String context;

    @ApiModelProperty(value = "添加时间")
    @TableField("addTime")
    private Date addTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("editTime")
    private Date editTime;

    @ApiModelProperty(value = "启用状态 1:启用2:禁用")
    @TableField("enableState")
    private Integer enableState;

    @ApiModelProperty(value = "删除状态 1:已删除2:未删除")
    @TableField("deleteState")
    private Integer deleteState;

    @TableField(exist = false)
    private String timeOld;

    @ApiModelProperty(value = "已读会员数组")
    @TableField("readMembers")
    private String readMembers;


    @ApiModelProperty(value="被举报人会员ID值")
    @TableField("memberId")
    private Integer memberId;

    // 当前会员是否已读
    @TableField(exist = false)
    private Boolean notRead = false;

    public SystemMessage(String title)
    {
	  this.title = title;
    }

    public SystemMessage()
    {
    }
}
