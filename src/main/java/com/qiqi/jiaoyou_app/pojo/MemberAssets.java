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

/**
 * <p>
 * 会员资产表
 * </p>
 *
 * @author sunlaiqian
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="MemberAssets对象", description="会员资产表")
@TableName("member_assets")
public class MemberAssets implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "会员id")
    @TableField("memberId")
    private Integer memberId;

    @ApiModelProperty(value = "会员黑钻石数")
    @TableField("memberDiamondsizeOfBlack")
    private Long memberDiamondsizeOfBlack;

    @ApiModelProperty(value = "会员银钻石数")
    @TableField("memberDiamondsizeOfSilver")
    private Long memberDiamondsizeOfSilver;

    @ApiModelProperty(value = "会员金钻石数")
    @TableField("memberDiamondsizeOfGold")
    private Long memberDiamondsizeOfGold;


    @ApiModelProperty(value = "会员喇叭数")
    @TableField("memberhornSize")
    private Long memberhornSize;

    @ApiModelProperty(value = "会员经验值")
    @TableField("meberExperienceSize")
    private Long meberExperienceSize;

    @ApiModelProperty(value = "会员标签")
    @TableField("memberLabel")
    private String memberLabel;


    @ApiModelProperty(value = "ciphertext")
    @TableField("ciphertext")
    private String ciphertext;


    @ApiModelProperty(value = "oldMemberDiamondsizeOfBlack")
    @TableField("oldMemberDiamondsizeOfBlack")
    private Long oldMemberDiamondsizeOfBlack;

    @ApiModelProperty(value = "累计银钻数/魅力")
    @TableField("oldMemberDiamondsizeOfSilver")
    private Long oldMemberDiamondsizeOfSilver;

    @Override
    public String toString()
    {
	  return "MemberAssets{" + "id=" + id + ", memberId=" + memberId + ", memberDiamondsizeOfBlack=" + memberDiamondsizeOfBlack + ", memberDiamondsizeOfSilver=" + memberDiamondsizeOfSilver + ", memberDiamondsizeOfGold=" + memberDiamondsizeOfGold + ", memberhornSize=" + memberhornSize + ", meberExperienceSize=" + meberExperienceSize + ", memberLabel='" + memberLabel + '\'' + ", ciphertext='" + ciphertext + '\'' + ", oldMemberDiamondsizeOfBlack=" + oldMemberDiamondsizeOfBlack + ", oldMemberDiamondsizeOfSilver=" + oldMemberDiamondsizeOfSilver + ", getMemberDiamondsizeOfBlack=" + getMemberDiamondsizeOfBlack + ", giftDiamondsSize=" + giftDiamondsSize + ", RedDiamondsSize=" + RedDiamondsSize + '}';
    }

    public Long getGetMemberDiamondsizeOfBlack()
    {
	  return getMemberDiamondsizeOfBlack;
    }

    public void setGetMemberDiamondsizeOfBlack(Long getMemberDiamondsizeOfBlack)
    {
	  this.getMemberDiamondsizeOfBlack = getMemberDiamondsizeOfBlack;
    }

    @TableField(exist = false)
    private Long getMemberDiamondsizeOfBlack;



    @ApiModelProperty(value = "送出的礼物金钻总数")
    @TableField(exist = false)
    private Integer giftDiamondsSize;


    @ApiModelProperty(value = "送出的红包金钻总数")
    @TableField(exist = false)
    private Integer RedDiamondsSize;

}
