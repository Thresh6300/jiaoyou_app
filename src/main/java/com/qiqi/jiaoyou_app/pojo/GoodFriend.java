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
 * 好友列表
 * </p>
 *
 * @author sunlaiqian
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="GoodFriend对象", description="好友列表")
@TableName("good_friend")
public class GoodFriend implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "会员id")
    @TableField("oneselfId")
    private Integer oneselfId;

    @ApiModelProperty(value = "好友id")
    @TableField("memberId")
    private Integer memberId;

    @ApiModelProperty(value = "好友头像")
    @TableField("memberHead")
    private String memberHead;

    @ApiModelProperty(value = "好友昵称")
    @TableField("memberNickName")
    private String memberNickName;

    @ApiModelProperty(value = "好友备注")
    private String remarks;

    @ApiModelProperty(value = "成为好友时间")
    @TableField("addTime")
    private Date addTime;


}
