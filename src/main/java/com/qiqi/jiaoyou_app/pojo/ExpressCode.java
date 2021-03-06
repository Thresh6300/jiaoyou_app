package com.qiqi.jiaoyou_app.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("express_code")
@ApiModel(value = "express_code对象", description = "快递公司编码--实体类")
public class ExpressCode extends BaseEntity
{

    private static final long serialVersionUID = 362004423462487087L;

    @ApiModelProperty(value = "快递公司编码id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "公司名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "公司编码")
    @TableField("code")
    private String code;


}