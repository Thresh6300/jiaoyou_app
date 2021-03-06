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
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 车友动态评论表
 * </p>
 *
 * @author sunlaiqian
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DynamicCommentsOfCarUsers对象", description="车友动态评论表")
@TableName("dynamic_comments_of_car_users")
public class DynamicCommentsOfCarUsers implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "动态id/评论id 主要看级别")
    @TableField("dynamicIdOrCommentId")
    private Integer dynamicIdOrCommentId;

    @ApiModelProperty(value = "评论人")
    @TableField("memberId")
    private Integer memberId;

    @ApiModelProperty(value = "头像")
    @TableField("memberHead")
    private String memberHead;

    @ApiModelProperty(value = "昵称")
    @TableField("memberNickName")
    private String memberNickName;

    @ApiModelProperty(value = "评论内容")
    private String context;

    @ApiModelProperty(value = "评论时间")
    @TableField("addTime")
    private Date addTime;

    @ApiModelProperty(value = "评论级别 1：直接评论动态2：回复评论")
    private Integer level;

    @ApiModelProperty(value = "车标")
    private String lable;

    @ApiModelProperty(value = "下级评论")
    @TableField(exist = false)
    private List<DynamicCommentsOfCarUsers> lowerList;


}
