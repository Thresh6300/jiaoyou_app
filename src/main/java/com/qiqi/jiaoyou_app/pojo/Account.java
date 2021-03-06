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
 * 后台管理员表
 * </p>
 *
 * @author sunlaiqian
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Account对象", description="后台管理员表")
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "角色")
    @TableField("roleId")
    private Integer roleId;

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

    @ApiModelProperty(value = "token")
    @TableField("token")
    private String token;

    @ApiModelProperty(value = "旧密码")
    @TableField(exist = false)
    private String oldPassword;

    @ApiModelProperty(value = "角色名称")
    @TableField(exist = false)
    private String roleName;


}
