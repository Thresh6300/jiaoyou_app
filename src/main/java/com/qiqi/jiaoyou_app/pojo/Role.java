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
 * 角色表
 * </p>
 *
 * @author sunlaiqian
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Role对象", description="角色表")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "菜单")
    private String menus;

    @ApiModelProperty(value = "添加时间")
    @TableField("addTime")
    private Date addTime;

    @ApiModelProperty(value = "编辑时间")
    @TableField("editTime")
    private Date editTime;

    @ApiModelProperty(value = "启用状态 1:启用2:禁用")
    @TableField("enableState")
    private Integer enableState;

    @ApiModelProperty(value = "删除状态 1:已删除2:未删除")
    @TableField("deleteState")
    private Integer deleteState;


}
