package com.qiqi.jiaoyou_app.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 工资记录表(WagesLogs)表实体类
 *
 * @author makejava
 * @since 2020-12-14 14:00:36
 */
@TableName("wages_logs")
public class WagesLogs extends Model<WagesLogs> {
  private static final long serialVersionUID = -91993185699707329L;

    @TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	//会员id值
	private String memberid;
	//钱数
	private String monery;


    @ApiModelProperty(value = "余额")
    @TableField("surplus_szie")
    private Long surplusSzie;

	//创建时间
	private Date createtime;
	//任务id
	private Integer taskId;

	@Override
	public String toString() {
		return "WagesLogs{" +
				"id=" + id +
				", memberid='" + memberid + '\'' +
				", monery='" + monery + '\'' +
				", createtime=" + createtime +
				", taskId=" + taskId +
				'}';
	}

    public Long getSurplusSzie()
    {
	  return surplusSzie;
    }

    public void setSurplusSzie(Long surplusSzie)
    {
	  this.surplusSzie = surplusSzie;
    }

    public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public String getMonery() {
		return monery;
	}

	public void setMonery(String monery) {
		this.monery = monery;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	/**
	 * 获取主键值
	 *
	 * @return 主键值
	 */
	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}