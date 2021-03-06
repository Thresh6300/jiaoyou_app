package com.qiqi.jiaoyou_app.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author sunLQ
 * @since 2020-05-08
 */
public class Fabulous extends Model<Fabulous>
{

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "fabulousId", type = IdType.AUTO)
    private Integer fabulousId;
    /**
     * 会员ID
     */
    private Integer memberId;

    @ApiModelProperty(value = "头像")
    @TableField("memberHead")
    private String memberHead;

    @ApiModelProperty(value = "昵称")
    @TableField("memberNickName")
    private String memberNickName;

    /**
     * 动态id
     */
    private Integer dynamicId;
    /**
     * 动态类型 1：朋友圈动态 2：车友圈动态
     */
    private Integer type;
    /**
     * 点赞时间
     */
    private Date addTime;

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

    public String getDelFlag()
    {
	  return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
	  this.delFlag = delFlag;
    }

    public static long getSerialVersionUID()
    {
	  return serialVersionUID;
    }

    public String getMemberHead()
    {
	  return memberHead;
    }

    public void setMemberHead(String memberHead)
    {
	  this.memberHead = memberHead;
    }

    public String getMemberNickName()
    {
	  return memberNickName;
    }

    public void setMemberNickName(String memberNickName)
    {
	  this.memberNickName = memberNickName;
    }

    public String getContextType()
    {
	  return contextType;
    }

    public void setContextType(String contextType)
    {
	  this.contextType = contextType;
    }

    public Integer getOneselfId()
    {
	  return oneselfId;
    }

    public void setOneselfId(Integer oneselfId)
    {
	  this.oneselfId = oneselfId;
    }

    public Integer getFabulousId()
    {
	  return fabulousId;
    }

    public Fabulous setFabulousId(Integer fabulousId)
    {
	  this.fabulousId = fabulousId;
	  return this;
    }

    public Integer getMemberId()
    {
	  return memberId;
    }

    public Fabulous setMemberId(Integer memberId)
    {
	  this.memberId = memberId;
	  return this;
    }

    public Integer getDynamicId()
    {
	  return dynamicId;
    }

    public Fabulous setDynamicId(Integer dynamicId)
    {
	  this.dynamicId = dynamicId;
	  return this;
    }

    public Integer getType()
    {
	  return type;
    }

    public Fabulous setType(Integer type)
    {
	  this.type = type;
	  return this;
    }

    public Date getAddTime()
    {
	  return addTime;
    }

    public Fabulous setAddTime(Date addTime)
    {
	  this.addTime = addTime;
	  return this;
    }

    @Override
    protected Serializable pkVal()
    {
	  return this.fabulousId;
    }

    @Override
    public String toString()
    {
	  return "Fabulous{" + "fabulousId=" + fabulousId + ", memberId=" + memberId + ", dynamicId=" + dynamicId + ", type=" + type + ", addTime=" + addTime + "}";
    }
}
