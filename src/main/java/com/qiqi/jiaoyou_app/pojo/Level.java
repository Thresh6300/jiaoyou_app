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
 * 等级表
 * </p>
 *
 * @author sunlaiqian
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Level对象", description="等级表")
public class Level implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "等级名称")
    @TableField("levelName")
    private String levelName;

    @ApiModelProperty(value = "经验值")
    @TableField("empiricalValue")
    private Long empiricalValue;

    @ApiModelProperty(value = "添加时间")
    @TableField("addTime")
    private Date addTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("editTime")
    private Date editTime;


}
