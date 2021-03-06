package com.qiqi.jiaoyou_app.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author GR123
 * @since 2020-06-19
 */
@Data
@TableName("red_log")
public class RedLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 红包ID
     */
    @TableId(value = "red_log_id", type = IdType.AUTO)
    private Integer redLogId;

     // 1:已过期  2:未过期
    private Integer state;
    /**
     * 发送人ID
     */
    @TableField("red_log_member_id")
    private Integer redLogMemberId;
    /**
     * 发送人昵称
     */
    @TableField("red_log_member_nick_name")
    private String redLogMemberNickName;
    /**
     * 发送人头像
     */
    @TableField("red_log_member_head")
    private String redLogMemberHead;
    /**
     * 钻石数量
     */
    @TableField("red_log_gold_size")
    private Integer redLogGoldSize;
    /**
     * 钻石数量
     */
    @TableField("surplus")
    private Long surplus;
    /**
     * 红包个数
     */
    @TableField("red_log_red_size")
    private Integer redLogRedSize;
    /**
     * 领取个数
     */
    @TableField("red_log_number_receipts")
    private Integer redLogNumberReceipts;
    /**
     * 剩余个数
     */
    @TableField("red_log_number_remaining")
    private Integer redLogNumberRemaining;

    //是否能否修改狗屎的余额   是否能修改余额(0:不能,1:能)
    @TableField("enableWrite")
    private String enableWrite;

    @TableField("red_log_send_time")
    private Date redLogSendTime;

    @TableField("red_log_end_time")
    private Date redLogEndTime;

    @TableField("red_log_sex")
    private Integer redLogSex;

    @TableField("red_log_remarks")
    private String redLogRemarks;

    @TableField("type")
    private Integer type;

    @TableField("status")
    private String status;

    @TableField(exist = false)
    private List<RedReceiveLog> list;

}
