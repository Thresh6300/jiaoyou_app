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
@TableName("notice")
@ApiModel(value = "notice对象", description = "公告表--实体类")
public class Notice extends BaseEntity
{

    private static final long serialVersionUID = 373278521565394486L;

    @ApiModelProperty(value = "公告表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "报名人员id")
    @TableField("member_id")
    private Integer memberId;

    @ApiModelProperty(value = "发送的会员数组")
    @TableField("member_ids")
    private String memberIds;

    @ApiModelProperty(value = "头像")
    @TableField("head")
    private String head;

    @ApiModelProperty(value = "昵称")
    @TableField("nickName")
    private String nickname;

    @ApiModelProperty(value = "性别 1 :男  2：女")
    @TableField("sex")
    private Integer sex;

    @ApiModelProperty(value = "申请时间")
    @TableField(value = "shenqing_time", fill = FieldFill.INSERT)
    private Date shenqingTime;

    @ApiModelProperty(value = "到场时间")
    @TableField(value = "daochang_time", fill = FieldFill.INSERT)
    private Date daochangTime;

    @ApiModelProperty(value = "聚会主题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "限制人数")
    @TableField("per_size")
    private Integer perSize;

    @ApiModelProperty(value = "开始时间")
    @TableField(value = "start_time", fill = FieldFill.INSERT)
    private Date startTime;

    @ApiModelProperty(value = "地址")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "发起聚会人员ID")
    @TableField("of_member")
    private Integer ofMember;


    @TableField("context")
    private String context;

    @ApiModelProperty(value = "1普通公告;2聚会;3会员专享")
    @TableField("type")
    private Integer type;


    @TableField(value = "add_time", fill = FieldFill.INSERT)
    private Date addTime;

    @ApiModelProperty(value = "申请的ID")
    @TableField("shenqingId")
    private Integer shenqingId;

    @ApiModelProperty(value = "已读会员数组")
    @TableField("readMembers")
    private String readMembers;

    // 当前会员是否已读
    @TableField(exist = false)
    private Boolean notRead = false;

    @TableField(exist = false)
    private Integer state;

    @TableField(exist = false)
    private String timeOld;



    public Notice(String title)
    {
	  this.title = title;
    }

    public Notice()
    {
    }
}