package com.qiqi.jiaoyou_app.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author GR123
 * @since 2020-06-16
 */
@TableName("report_type")
public class ReportType extends Model<ReportType> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "report_type_id", type = IdType.AUTO)
    private Integer reportTypeId;
    @TableField("report_type_name")
    private String reportTypeName;
    @TableField("report_type_create_time")
    private Date reportTypeCreateTime;
    @TableField("report_type_edit_time")
    private Date reportTypeEditTime;


    public Integer getReportTypeId() {
        return reportTypeId;
    }

    public ReportType setReportTypeId(Integer reportTypeId) {
        this.reportTypeId = reportTypeId;
        return this;
    }

    public String getReportTypeName() {
        return reportTypeName;
    }

    public ReportType setReportTypeName(String reportTypeName) {
        this.reportTypeName = reportTypeName;
        return this;
    }

    public Date getReportTypeCreateTime() {
        return reportTypeCreateTime;
    }

    public ReportType setReportTypeCreateTime(Date reportTypeCreateTime) {
        this.reportTypeCreateTime = reportTypeCreateTime;
        return this;
    }

    public Date getReportTypeEditTime() {
        return reportTypeEditTime;
    }

    public ReportType setReportTypeEditTime(Date reportTypeEditTime) {
        this.reportTypeEditTime = reportTypeEditTime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.reportTypeId;
    }

    @Override
    public String toString() {
        return "ReportType{" +
        "reportTypeId=" + reportTypeId +
        ", reportTypeName=" + reportTypeName +
        ", reportTypeCreateTime=" + reportTypeCreateTime +
        ", reportTypeEditTime=" + reportTypeEditTime +
        "}";
    }
}
