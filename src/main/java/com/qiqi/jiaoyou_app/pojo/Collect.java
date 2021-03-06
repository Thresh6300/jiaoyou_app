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
@TableName("collect")
@ApiModel(value = "collect对象", description = "喜欢--实体类")
public class Collect extends BaseEntity
{

    private static final long serialVersionUID = 259486835294095743L;

    @ApiModelProperty(value = "喜欢id")
    @TableId(value = "collect_id", type = IdType.AUTO)
    private Integer collectId;

    @ApiModelProperty(value = "会员的id")
    @TableField("collect_user_id")
    private Integer collectUserId;

    @ApiModelProperty(value = "商品的id")
    @TableField("collect_shop_id")
    private Integer collectShopId;

    @ApiModelProperty(value = "服务的id")
    @TableField("collect_server_id")
    private Integer collectServerId;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;


}