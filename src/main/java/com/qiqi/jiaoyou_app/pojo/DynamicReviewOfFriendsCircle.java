package com.qiqi.jiaoyou_app.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName("dynamic_review_of_friends_circle")
@ApiModel(value = "dynamic_review_of_friends_circle对象", description = "朋友圈动态评论表--实体类")
public class DynamicReviewOfFriendsCircle extends BaseEntity
{

    private static final long serialVersionUID = 753215861945657418L;

    @ApiModelProperty(value = "朋友圈动态评论表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "动态id/评论id 主要看级别")
    @TableField("dynamicIdOrCommentId")
    private Integer dynamicIdOrCommentId;

    @ApiModelProperty(value = "动态id")
    @TableField("dynamicId")
    private Integer dynamicId;

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
    @TableField("context")
    private String context;

    @ApiModelProperty(value = "评论时间")
    @TableField(value = "addTime", fill = FieldFill.INSERT)
    private Date addTime;

    @ApiModelProperty(value = "评论级别 1：直接评论动态2：回复评论")
    @TableField("level")
    private Integer level;

    @ApiModelProperty(value = "车标")
    @TableField("lable")
    private String lable;

    @ApiModelProperty(value = "自身的id")
    @TableField("oneself_id")
    private Integer oneselfId;

    @ApiModelProperty(value = "0内容;1图片;2视频")
    @TableField("context_type")
    private String contextType;

    @ApiModelProperty(value = "0未删除1已删除")
    @TableField("delFlag")
    @TableLogic
    private String delFlag;


    @ApiModelProperty(value = "下级评论")
    @TableField(exist = false)
    private List<DynamicReviewOfFriendsCircle> LowerList;

    @ApiModelProperty(value = "0朋友圈;1世界圈")
    @TableField(exist = false)
    private String type;

    @ApiModelProperty(value = "已读会员数组")
    @TableField("readMembers")
    private String readMembers;

    // 当前会员是否已读
    @TableField(exist = false)
    private Boolean notRead = false;
}