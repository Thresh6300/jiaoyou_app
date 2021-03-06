package com.qiqi.jiaoyou_app.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DynamicVo
{
    @ApiModelProperty(value = "自身的id")
    private Integer oneselfId;

    @ApiModelProperty(value = "0评论;1点赞")
    private String type;

    @ApiModelProperty(value = "0内容;1图片;2视频")
    private String contextType;

    @ApiModelProperty(value = "评论人")
    private Integer memberId;

    @ApiModelProperty(value = "头像")
    private String memberHead;

    @ApiModelProperty(value = "昵称")
    private String memberNickName;

    @ApiModelProperty(value = "被评论人昵称")
    private String otherMemberNickName;

    @ApiModelProperty(value = "发表内容")
    private String context;

    @ApiModelProperty(value = "图片数组")
    private String images;

    @ApiModelProperty(value = "视频")
    private String video;

    @ApiModelProperty(value = "动态ID")
    private Integer circleOfFriendsId;


    @ApiModelProperty(value = "评论内容")
    private String commentContext;

    @ApiModelProperty(value = "评论时间")
    private Date addTime;

    @ApiModelProperty(value = "评论级别 1：直接评论动态2：回复评论")
    private Integer level;
}
