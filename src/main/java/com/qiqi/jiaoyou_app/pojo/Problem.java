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
 * 常见问题表
 * </p>
 *
 * @author sunlaiqian
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Problem对象", description="常见问题表")
public class Problem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "问题名称")
    private String title;

    @ApiModelProperty(value = "答案")
    private String answer;

    @ApiModelProperty(value = "发布时间")
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
