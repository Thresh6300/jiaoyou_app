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
import java.util.List;

/**
 * <p>
 * 朋友圈动态
 * </p>
 *
 * @author sunlaiqian
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "CircleOfFriends对象", description = "朋友圈动态")
@TableName("circle_of_friends")
public class CircleOfFriends implements Serializable
{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    @TableField("memerId")
    private Integer memerId;

    @ApiModelProperty(value = "用户头像")
    @TableField("memberHead")
    private String memberHead;

    @ApiModelProperty(value = "昵称")
    @TableField("memberNickName")
    private String memberNickName;

    @ApiModelProperty(value = "性别 1:男2:女")
    @TableField("memberSex")
    private Integer memberSex;

    @ApiModelProperty(value = "年龄")
    @TableField("memberAge")
    private Integer memberAge;


    @ApiModelProperty(value = "所在市")
    private String city;



    @ApiModelProperty(value = "发布时间")
    @TableField("addTime")
    private Date addTime;

    @ApiModelProperty(value = "动态内容")
    private String context;

    @ApiModelProperty(value = "图片数组")
    private String images;

    @ApiModelProperty(value = "视频")
    private String video;

    @ApiModelProperty(value = "点赞量")
    @TableField("likeSize")
    private Integer likeSize;

    @ApiModelProperty(value = "评论量")
    @TableField("commentSize")
    private Integer commentSize;

    @ApiModelProperty(value = "是否隐藏地址  1：隐藏 2：不隐藏")
    @TableField("state")
    private Integer state;

    @ApiModelProperty(value = "是否允许同城陌生人查看  1：允许  2：不允许")
    @TableField("strangersInTheSameCity")
    private Integer strangersInTheSameCity;

    @ApiModelProperty(value = "是否允许同城以外陌生人 1：允许  2：不允许")
    @TableField("strangersOutsideTheCity")
    private Integer strangersOutsideTheCity;

    @ApiModelProperty(value = "是否同步到世界圈  1：同步  2：不同步")
    @TableField("citySynchronization")
    private Integer citySynchronization;

    @ApiModelProperty(value = "1;默认动态  2：不是默认动态")
    @TableField("defaultType")
    private Integer defaultType;

    @ApiModelProperty(value = "评论子类")
    @TableField(exist = false)
    private List<DynamicReviewOfFriendsCircle> dynamicReviewOfFriendsCircleList;

    @ApiModelProperty(value = "当前会员是否点赞  1：已点赞   2：未点赞")
    @TableField(exist = false)
    private Integer isLike = 2;

    @ApiModelProperty(value = "点赞头像数组")
    @TableField(exist = false)
    private List<Member> list;


    @ApiModelProperty(value = "日期格式化")
    @TableField(exist = false)
    private String addTimeStr;



    @ApiModelProperty(value = "车标")
    @TableField("lable")
    private String lable;

    @ApiModelProperty(value = "距离")
    @TableField(exist = false)
    private String distance;

    @ApiModelProperty(value = "已读会员数组")
    @TableField("readMembers")
    private String readMembers;

    // 当前会员是否已读
    @TableField(exist = false)
    private Boolean notRead = false;

    @ApiModelProperty(value = "是否是新人报道（0：不是，1：是）")
    @TableField("newComerReport")
    private Integer newComerReport;

}
