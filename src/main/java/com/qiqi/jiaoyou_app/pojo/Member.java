package com.qiqi.jiaoyou_app.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * app会员表
 * </p>
 *
 * @author sunlaiqian
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Member对象", description = "app会员表")
public class Member implements Serializable
{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "昵称")
    @TableField("nickName")
    private String nickName;

    @ApiModelProperty(value = "头像")
    private String head;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "性别 1：男2：女")
    private Integer sex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "身份证号")
    @TableField("IDNumber")
    private String IDNumber;

    @ApiModelProperty(value = "所在省")
    private String province;

    @ApiModelProperty(value = "所在市")
    private String city;

    @ApiModelProperty(value = "所在区")
    private String area;

    @ApiModelProperty(value = "注册时间")
    @TableField("addTime")
    private Date addTime;

    @ApiModelProperty(value = "是否是vip 1:是vip  2：不是vip")
    @TableField("isvip")
    private Integer isvip;

    @ApiModelProperty(value = "会员到期时间")
    @TableField("memberExpirationDate")
    private Date memberExpirationDate;

    @ApiModelProperty(value = "背景图")
    @TableField("backgroundImages")
    private String backgroundImages;

    @ApiModelProperty(value = "启用状态 1：启用2：禁用")
    @TableField("enableSate")
    private Integer enableSate;

    @ApiModelProperty(value = "人脸拍照照片")
    @TableField("facePhoto")
    private String facePhoto;

    @ApiModelProperty(value = "身份证正面照片")
    @TableField("positivePhotoOfIDCard")
    private String positivePhotoOfIDCard;

    @ApiModelProperty(value = "行驶证照片")
    @TableField("drivingLicensePhoto")
    private String drivingLicensePhoto;

    @ApiModelProperty(value = "用户等级")
    @TableField("level")
    private Integer level;

    @ApiModelProperty(value = "邀请码")
    @TableField("invitationCode")
    private String invitationCode;

    @ApiModelProperty(value = "审核状态 1:审核通过 2：审核未过 3：审核中")
    @TableField("examineState")
    private Integer examineState;

    @ApiModelProperty(value = "注册来源 1:颜值注册  2：车友注册")
    @TableField("registrationChannel")
    private Integer registrationChannel;

    @ApiModelProperty(value = "审核原因")
    @TableField("reason")
    private String reason;

    @ApiModelProperty(value = "token")
    @TableField("token")
    private String token;

    @ApiModelProperty(value = "登录次数")
    @TableField("loginSize")
    private Integer loginSize;

    @ApiModelProperty(value = "推送ID")
    @TableField("pushId")
    private String pushId;

    @ApiModelProperty(value = "父级id")
    @TableField("pid")
    private Integer pid;

    @ApiModelProperty(value = "直推人数")
    @TableField("recommended")
    private Integer recommended;

    @ApiModelProperty(value = "车标图片")
    @TableField("carLable")
    private String carLable;

    @ApiModelProperty(value = "是否是客服")
    @TableField("isCustomer")
    private Integer isCustomer;

    @ApiModelProperty(value = "经度")
    @TableField("longitude")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    @TableField("latitude")
    private String latitude;

    @ApiModelProperty(value = "剩余可加好友个数")
    @TableField("numberOfRemainingFriendsToAdd")
    private Integer numberOfRemainingFriendsToAdd;

    @ApiModelProperty(value = "活跃度")
    @TableField("activitySize")
    private Long activitySize;
/*最近的登录时间,用来判断是否进行登录*/

    private Date todayLoginTime;


    @ApiModelProperty(value = "视频")
    @TableField("video")
    private String video;


    @ApiModelProperty(value = "注册机型 1：苹果 2：安卓")
    @TableField("registrationModel")
    private Integer registrationModel;

    @ApiModelProperty(value = "车辆型号")
    @TableField("carModel")
    private String carModel;

    @ApiModelProperty(value = "删除状态 0:未删除，1：删除")
    @TableField("delUserStatus")
    private String delUserStatus;

    @ApiModelProperty(value = "短信验证码")
    @TableField(exist = false)
    private String code;

    @ApiModelProperty(value = "等级差值")
    @TableField(exist = false)
    private String levelDifference;

    @ApiModelProperty(value = "旧密码")
    @TableField(exist = false)
    private String oldPassword;

    @ApiModelProperty(value = "车辆子类")
    @TableField(exist = false)
    private Car car;

    @ApiModelProperty(value = "资产子类")
    @TableField(exist = false)
    private MemberAssets memberAssets;

    @ApiModelProperty(value = "设置子类")
    @TableField(exist = false)
    private MembershipSettings membershipSettings;

    @ApiModelProperty(value = "朋友圈动态子类")
    @TableField(exist = false)
    private List<CircleOfFriends> circleOfFriendsList;

    @ApiModelProperty(value = "车友圈动态子类")
    @TableField(exist = false)
    private List<RiderDynamics> riderDynamicsList;

    @ApiModelProperty(value = "标签子类")
    @TableField(exist = false)
    private Lable lable;


    @ApiModelProperty(value = "距离")
    @TableField(exist = false)
    private String distance;

    @ApiModelProperty(value = "距离")
    @TableField(exist = false)
    private Double distances;

    @ApiModelProperty(value="0:不在线1：在线")
    private Integer onLine;

    @TableField(exist = false)
    private String onLineStatus = "12小时前在线";


    /**
     * 类型
     */
    @TableField(exist = false)
    private String type;

    /**
     * 值
     */
    @TableField(exist = false)
    private String value;

    @TableField("loginTimeofOne")
    private Date loginTimeofOne;

    /**
     * 类型
     */
    @TableField(exist = false)
    private Integer clubId;

    /**
     * 类型
     */
    @TableField(exist = false)
    private List<Club> clubs;
}
