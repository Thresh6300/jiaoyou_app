package com.qiqi.jiaoyou_app.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author GR123
 * @since 2020-06-13
 */
@Data
@TableName("new_friend")
public class NewFriend extends Model<NewFriend> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "new_friend_id", type = IdType.AUTO)
    private Integer newFriendId;
    /**
     * 自身ID
     */
    @TableField("new_friend_oneself_id")
    private Integer newFriendOneselfId;
    /**
     * 对方ID
     */
    @TableField("new_friend_other_party_id")
    private Integer newFriendOtherPartyId;
    /**
     * 对方头像
     */
    @TableField("new_friend_other_party_head")
    private String newFriendOtherPartyHead;
    /**
     * 对方头像车标
     */
    @TableField("new_friend_other_party_auto_logos")
    private String newFriendOtherPartyAutoLogos;
    /**
     * 对方昵称
     */
    @TableField("new_friend_other_party_nick_name")
    private String newFriendOtherPartyNickName;
    /**
     * 对方等级
     */
    @TableField("new_friend_other_party_level")
    private Integer newFriendOtherPartyLevel;
    /**
     * 对方性别 1:男 2：女
     */
    @TableField("new_friend_other_party_sex")
    private Integer newFriendOtherPartySex;
    /**
     * 对方年龄
     */
    @TableField("new_friend_other_party_age")
    private Integer newFriendOtherPartyAge;
    /**
     * 对方城市
     */
    @TableField("new_friend_other_party_city")
    private String newFriendOtherPartyCity;
    /**
     * 该礼物是否被接收方接收 1： 是 2 ：否 3退回
     */
    @TableField("is_retreat")
    private Integer isRetreat;
    /**
     * 打招呼时间
     */
    @TableField("new_friend_other_party_add_time")
    private Date newFriendOtherPartyAddTime;
    /**
     * 过期时间
     */
    @TableField("new_friend_other_party_end_time")
    private Date newFriendOtherPartyEndTime;
    /**
     * 礼物图片
     */
    @TableField("new_friend_other_party_gift_image")
    private String newFriendOtherPartyGiftImage;
    /**
     * 礼物名称
     */
    @TableField("new_friend_other_party_gift_name")
    private String newFriendOtherPartyGiftName;
    /**
     * 礼物数量
     */
    @TableField("new_friend_other_party_gift_size")
    private Integer newFriendOtherPartyGiftSize;

    /**
     * 礼物ID
     */
    @TableField("new_friend_other_party_gift_id")
    private Integer newFriendOtherPartyGiftId;
    /**
     * 打招呼送礼物ID
     */
    @TableField("remarks")
    private Integer remarks;

    @ApiModelProperty(value = "数量")
    private Long runSize;

    @ApiModelProperty(value = "余额")
    private Long surplus;
    /**
     * 时间字符串
     */
    @TableField(exist = false)
    private String timeOld;

    @Override
    protected Serializable pkVal() {
        return this.newFriendId;
    }

}
