package com.qiqi.jiaoyou_app.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("server_info_type")
@ApiModel(value = "server_info_type对象", description = "服务信息管理类型表--实体类")
public class ServerInfoType extends BaseEntity
{

    private static final long serialVersionUID = -34199581973995593L;

    @ApiModelProperty(value = "服务信息管理类型表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "标题")
    @TableField("server_title")
    private String serverTitle;

    @ApiModelProperty(value = "排序")
    @TableField("server_sort")
    private String serverSort;

    @ApiModelProperty(value = "排序")
    @TableField("server_icon")
    private String serverIcon;

    @ApiModelProperty(value = "状态 0：删除，1：正常")
    @TableField("server_status")
    private String serverStatus;

    @ApiModelProperty(value = "服务类型 0：商品 1 服务")
    @TableField("server_type")
    private String serverType;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT)
    private Date updateTime;


}