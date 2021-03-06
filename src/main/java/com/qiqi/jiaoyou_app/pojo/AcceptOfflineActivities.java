package com.qiqi.jiaoyou_app.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 线下活动列表
 * </p>
 *
 * @author GR123
 * @since 2020-06-16
 */
@TableName("accept_offline_activities")
public class AcceptOfflineActivities extends Model<AcceptOfflineActivities> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 聚会ID
     */
    private Integer sendOfflineActivities;
    /**
     * 接收人id
     */
    private Integer acceptMemberId;
    /**
     * 接收人用户头像
     */
    private String acceptMemberHead;
    /**
     * 接收人昵称
     */
    private String acceptMemberNickName;
    /**
     * 接收人性别
     */
    private Integer acceptMemberSex;
    /**
     * 接收人年龄
     */
    private Integer acceptMemberAge;
    /**
     * 赴约时间
     */
    private Date keepAnAppointmentTime;
    /**
     * 当前状态 状态
     * 1已申请赴约 待发起方审核
     * 2发起方通过 赴约申请
     * 3已到达赴约地点,
     * 4待审核,
     * 5已取消,
     * 6平台已拒绝
     * 7平台审核通过
     * 8聚会已结束
     * 9发起者未通过你的申请 已拒绝
     */
    private Integer keepAnAppointmentState;
    /**
     * 历史状态
     */
    private Integer oldState;
    /**
     * 取消类型
     */
    private Integer closeType;

    /**
     * 赴约时间
     */
    @TableField(exist = false)
    private String keepAnAppointmentTimeStr;

    @TableField(exist = false)
    private String latitude;

    @TableField(exist = false)
    private String longitude;
    /**
     * 搜索
     */
    @TableField(exist = false)
    private String seacrh;

    public String getSeacrh()
    {
	  return seacrh;
    }

    public void setSeacrh(String seacrh)
    {
	  this.seacrh = seacrh;
    }
    public Integer getCloseType() {
        return closeType;
    }

    public void setCloseType(Integer closeType) {
        this.closeType = closeType;
    }

    public Integer getOldState() {
        return oldState;
    }

    public void setOldState(Integer oldState) {
        this.oldState = oldState;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }

    public AcceptOfflineActivities setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getSendOfflineActivities() {
        return sendOfflineActivities;
    }

    public AcceptOfflineActivities setSendOfflineActivities(Integer sendOfflineActivities) {
        this.sendOfflineActivities = sendOfflineActivities;
        return this;
    }

    public Integer getAcceptMemberId() {
        return acceptMemberId;
    }

    public AcceptOfflineActivities setAcceptMemberId(Integer acceptMemberId) {
        this.acceptMemberId = acceptMemberId;
        return this;
    }

    public String getAcceptMemberHead() {
        return acceptMemberHead;
    }

    public AcceptOfflineActivities setAcceptMemberHead(String acceptMemberHead) {
        this.acceptMemberHead = acceptMemberHead;
        return this;
    }

    public String getAcceptMemberNickName() {
        return acceptMemberNickName;
    }

    public AcceptOfflineActivities setAcceptMemberNickName(String acceptMemberNickName) {
        this.acceptMemberNickName = acceptMemberNickName;
        return this;
    }

    public Integer getAcceptMemberSex() {
        return acceptMemberSex;
    }

    public AcceptOfflineActivities setAcceptMemberSex(Integer acceptMemberSex) {
        this.acceptMemberSex = acceptMemberSex;
        return this;
    }

    public Integer getAcceptMemberAge() {
        return acceptMemberAge;
    }

    public AcceptOfflineActivities setAcceptMemberAge(Integer acceptMemberAge) {
        this.acceptMemberAge = acceptMemberAge;
        return this;
    }

    public Date getKeepAnAppointmentTime() {
        return keepAnAppointmentTime;
    }

    public AcceptOfflineActivities setKeepAnAppointmentTime(Date keepAnAppointmentTime) {
        this.keepAnAppointmentTime = keepAnAppointmentTime;
        return this;
    }

    public Integer getKeepAnAppointmentState() {
        return keepAnAppointmentState;
    }

    public void setKeepAnAppointmentState(Integer keepAnAppointmentState) {
        this.keepAnAppointmentState = keepAnAppointmentState;
    }

    public String getKeepAnAppointmentTimeStr() {
        return keepAnAppointmentTimeStr;
    }

    public void setKeepAnAppointmentTimeStr(String keepAnAppointmentTimeStr) {
        this.keepAnAppointmentTimeStr = keepAnAppointmentTimeStr;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AcceptOfflineActivities{" +
        "id=" + id +
        ", sendOfflineActivities=" + sendOfflineActivities +
        ", acceptMemberId=" + acceptMemberId +
        ", acceptMemberHead=" + acceptMemberHead +
        ", acceptMemberNickName=" + acceptMemberNickName +
        ", acceptMemberSex=" + acceptMemberSex +
        ", acceptMemberAge=" + acceptMemberAge +
        ", keepAnAppointmentTime=" + keepAnAppointmentTime +
        ", keepAnAppointmentState=" + keepAnAppointmentState +
        "}";
    }
}
