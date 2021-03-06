package com.qiqi.jiaoyou_app.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author GR123
 * @since 2020-07-21
 */
@TableName("member_matching_log")
public class MemberMatchingLog extends Model<MemberMatchingLog> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "member_matching_log_id")
    @TableId(value = "member_matching_log_id", type = IdType.AUTO)
    private Integer memberMatchingLogId;
    @TableField("member_matching_log_member_id")
    private Integer memberMatchingLogMemberId;
    @TableField("member_matching_log_member_ids_soul")
    private String memberMatchingLogMemberIdsSoul;
    @TableField("member_matching_log_member_ids_car")
    private String memberMatchingLogMemberIdsCar;


    public Integer getMemberMatchingLogId() {
        return memberMatchingLogId;
    }

    public void setMemberMatchingLogId(Integer memberMatchingLogId) {
        this.memberMatchingLogId = memberMatchingLogId;
    }

    public Integer getMemberMatchingLogMemberId() {
        return memberMatchingLogMemberId;
    }

    public MemberMatchingLog setMemberMatchingLogMemberId(Integer memberMatchingLogMemberId) {
        this.memberMatchingLogMemberId = memberMatchingLogMemberId;
        return this;
    }

    public String getMemberMatchingLogMemberIdsSoul() {
        return memberMatchingLogMemberIdsSoul;
    }

    public MemberMatchingLog setMemberMatchingLogMemberIdsSoul(String memberMatchingLogMemberIdsSoul) {
        this.memberMatchingLogMemberIdsSoul = memberMatchingLogMemberIdsSoul;
        return this;
    }

    public String getMemberMatchingLogMemberIdsCar() {
        return memberMatchingLogMemberIdsCar;
    }

    public MemberMatchingLog setMemberMatchingLogMemberIdsCar(String memberMatchingLogMemberIdsCar) {
        this.memberMatchingLogMemberIdsCar = memberMatchingLogMemberIdsCar;
        return this;
    }

    @Override
    public String toString() {
        return "MemberMatchingLog{" +
                "memberMatchingLogId=" + memberMatchingLogId +
                ", memberMatchingLogMemberId=" + memberMatchingLogMemberId +
                ", memberMatchingLogMemberIdsSoul='" + memberMatchingLogMemberIdsSoul + '\'' +
                ", memberMatchingLogMemberIdsCar='" + memberMatchingLogMemberIdsCar + '\'' +
                '}';
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
