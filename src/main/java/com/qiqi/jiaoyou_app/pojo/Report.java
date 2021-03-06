package com.qiqi.jiaoyou_app.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author GR123
 * @since 2020-06-16
 */
public class Report extends Model<Report> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "report_id", type = IdType.AUTO)
    private Integer reportId;
    /**
     * 举报人ID
     */
    @TableField("report_informant_id")
    private Integer reportInformantId;
    /**
     * 举报人头像
     */
    @TableField("report_informant_head")
    private String reportInformantHead;
    /**
     * 举报人昵称
     */
    @TableField("report_informant_nick_name")
    private String reportInformantNickName;
    /**
     * 举报人电话
     */
    @TableField("report_informant_phone")
    private String reportInformantPhone;
    /**
     * 被举报人ID
     */
    @TableField("report_bei_informant_id")
    private Integer reportBeiInformantId;
    /**
     * 被举报人头像
     */
    @TableField("report_bei_informant_head")
    private String reportBeiInformantHead;
    /**
     * 被举报人昵称
     */
    @TableField("report_bei_informant_nick_name")
    private String reportBeiInformantNickName;
    /**
     * 被举报人电话
     */
    @TableField("report_bei_informant_phone")
    private String reportBeiInformantPhone;
    /**
     * 举报原因
     */
    @TableField("report_reason")
    private Integer reportReason;
    /**
     * 举报内容
     */
    @TableField("report_context")
    private String reportContext;
    /**
     * 举报时间
     */
    @TableField("report_add_time")
    private Date reportAddTime;
    /**
     * 审核状态 1：待审核  2：审核通过 3：审核未通过
     */
    @TableField("report_Examine_state")
    private Integer reportExamineState;
    /**
     * 审核原因
     */
    @TableField("report_Examone_reason")
    private String reportExamoneReason;


    public Integer getReportId() {
        return reportId;
    }

    public Report setReportId(Integer reportId) {
        this.reportId = reportId;
        return this;
    }

    public Integer getReportInformantId() {
        return reportInformantId;
    }

    public Report setReportInformantId(Integer reportInformantId) {
        this.reportInformantId = reportInformantId;
        return this;
    }

    public String getReportInformantHead() {
        return reportInformantHead;
    }

    public Report setReportInformantHead(String reportInformantHead) {
        this.reportInformantHead = reportInformantHead;
        return this;
    }

    public String getReportInformantNickName() {
        return reportInformantNickName;
    }

    public Report setReportInformantNickName(String reportInformantNickName) {
        this.reportInformantNickName = reportInformantNickName;
        return this;
    }

    public String getReportInformantPhone() {
        return reportInformantPhone;
    }

    public Report setReportInformantPhone(String reportInformantPhone) {
        this.reportInformantPhone = reportInformantPhone;
        return this;
    }

    public Integer getReportBeiInformantId() {
        return reportBeiInformantId;
    }

    public Report setReportBeiInformantId(Integer reportBeiInformantId) {
        this.reportBeiInformantId = reportBeiInformantId;
        return this;
    }

    public String getReportBeiInformantHead() {
        return reportBeiInformantHead;
    }

    public Report setReportBeiInformantHead(String reportBeiInformantHead) {
        this.reportBeiInformantHead = reportBeiInformantHead;
        return this;
    }

    public String getReportBeiInformantNickName() {
        return reportBeiInformantNickName;
    }

    public Report setReportBeiInformantNickName(String reportBeiInformantNickName) {
        this.reportBeiInformantNickName = reportBeiInformantNickName;
        return this;
    }

    public String getReportBeiInformantPhone() {
        return reportBeiInformantPhone;
    }

    public Report setReportBeiInformantPhone(String reportBeiInformantPhone) {
        this.reportBeiInformantPhone = reportBeiInformantPhone;
        return this;
    }

    public Integer getReportReason() {
        return reportReason;
    }

    public Report setReportReason(Integer reportReason) {
        this.reportReason = reportReason;
        return this;
    }

    public String getReportContext() {
        return reportContext;
    }

    public Report setReportContext(String reportContext) {
        this.reportContext = reportContext;
        return this;
    }

    public Date getReportAddTime() {
        return reportAddTime;
    }

    public Report setReportAddTime(Date reportAddTime) {
        this.reportAddTime = reportAddTime;
        return this;
    }

    public Integer getReportExamineState() {
        return reportExamineState;
    }

    public Report setReportExamineState(Integer reportExamineState) {
        this.reportExamineState = reportExamineState;
        return this;
    }

    public String getReportExamoneReason() {
        return reportExamoneReason;
    }

    public Report setReportExamoneReason(String reportExamoneReason) {
        this.reportExamoneReason = reportExamoneReason;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.reportId;
    }

    @Override
    public String toString() {
        return "Report{" +
        "reportId=" + reportId +
        ", reportInformantId=" + reportInformantId +
        ", reportInformantHead=" + reportInformantHead +
        ", reportInformantNickName=" + reportInformantNickName +
        ", reportInformantPhone=" + reportInformantPhone +
        ", reportBeiInformantId=" + reportBeiInformantId +
        ", reportBeiInformantHead=" + reportBeiInformantHead +
        ", reportBeiInformantNickName=" + reportBeiInformantNickName +
        ", reportBeiInformantPhone=" + reportBeiInformantPhone +
        ", reportReason=" + reportReason +
        ", reportContext=" + reportContext +
        ", reportAddTime=" + reportAddTime +
        ", reportExamineState=" + reportExamineState +
        ", reportExamoneReason=" + reportExamoneReason +
        "}";
    }
}
