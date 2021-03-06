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
@TableName("game_select_label")
@ApiModel(value = "game_select_label对象", description = "答题游戏库选项标签--实体类")
public class GameSelectLabel extends BaseEntity
{

    private static final long serialVersionUID = 196725132572995122L;

    @ApiModelProperty(value = "答题游戏库选项标签id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "选项标题")
    @TableField("game_select_title")
    private String gameSelectTitle;

    @ApiModelProperty(value = "排序值")
    @TableField("game_select_sort")
    private Integer gameSelectSort;

    @ApiModelProperty(value = "选择标题的图标")
    @TableField("game_select_title_icon")
    private String gameSelectTitleIcon;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT)
    private Date updateTime;

    @ApiModelProperty(value = "1答题任务  2视频任务 3图片任务 ")
    @TableField(value = "game_type")
    private String gameType;

}