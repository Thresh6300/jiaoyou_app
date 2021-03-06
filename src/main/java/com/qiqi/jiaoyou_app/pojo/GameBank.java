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
@TableName("game_bank")
@ApiModel(value = "game_bank对象", description = "答题游戏题库--实体类")
public class GameBank extends BaseEntity
{

    private static final long serialVersionUID = -68916801889264314L;

    @ApiModelProperty(value = "答题游戏题库id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "题干（内容）")
    @TableField("game_stem")
    private String gameStem;

    @ApiModelProperty(value = "选项游戏选项标签表的id（答题游戏视频或者其他）")
    @TableField("game_select_id")
    private String gameSelectId;

    @ApiModelProperty(value = "正确答案")
    @TableField("true_answer")
    private String trueAnswer;

    @ApiModelProperty(value = "排序值")
    @TableField("game_sort")
    private String gameSort;

    @ApiModelProperty(value = "答题游戏标题")
    @TableField("game_title")
    private String gameTitle;

    @ApiModelProperty(value = "标题图标")
    @TableField("game_title_icon")
    private String gameTitleIcon;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT)
    private Date updateTime;


}