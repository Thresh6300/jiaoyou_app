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
import java.math.BigDecimal;

/**
 * <p>
 * 平台参数设置
 * </p>
 *
 * @author sunlaiqian
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="PlatformParameterSetting对象", description="平台参数设置")
@TableName("platform_parameter_setting")
public class PlatformParameterSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "app端启用页图片")
    @TableField("launchPage")
    private String launchPage;

    @ApiModelProperty(value = "login背景图")
    @TableField("loginImage")
    private String loginImage;

    @ApiModelProperty(value = "用户收到的礼物平台抽成比例")
    @TableField("drawInProportion")
    private BigDecimal drawInProportion;


    @ApiModelProperty(value = "客服电话")
    private String telephone;

    @ApiModelProperty(value = "设置一级分销返佣比例")
    @TableField("proportionOfReturnedServants")
    private BigDecimal proportionOfReturnedServants;

    @ApiModelProperty(value = "设置二级分销返佣比例")
    @TableField("proportionOfReturnedServantsTwo")
    private BigDecimal proportionOfReturnedServantsTwo;

    @ApiModelProperty(value = "设置通过他人分享注册的用户，自身提现比例提高XX%")
    @TableField("selfWithdrawal")
    private BigDecimal selfWithdrawal;

    @ApiModelProperty(value = "发送礼物获得对应钻石价值比例的经验值数量")
    @TableField("giftExperienceRatio")
    private BigDecimal giftExperienceRatio;

    @ApiModelProperty(value = "发起赴约获得对应钻石价值比例的经验值数量")
    @TableField("proportionOfExperience")
    private BigDecimal proportionOfExperience;

    @ApiModelProperty(value = "设置小喇叭单价")
    @TableField("unitPriceOfLoudspeaker")
    private Integer unitPriceOfLoudspeaker;

    @ApiModelProperty(value = "设置银钻兑换金钻比例")
    @TableField("exchangeRateOfGoldDiamond")
    private BigDecimal exchangeRateOfGoldDiamond;

    @ApiModelProperty(value = "设置银钻兑换黑钻比例")
    @TableField("exchangeRateForBlackDiamonds")
    private BigDecimal exchangeRateForBlackDiamonds;


    @ApiModelProperty(value = "设置黑钻兑换余额比例")
    @TableField("proportionOfExchangeBalance")
    private BigDecimal proportionOfExchangeBalance;

    @ApiModelProperty(value = "设置六个可兑换金钻的选项")
    @TableField("sixGoldSize")
    private String sixGoldSize;

    @ApiModelProperty(value = "设置六个可兑换黑钻的数量")
    @TableField("sixBlackSize")
    private String sixBlackSize;

    @ApiModelProperty(value = "每元金钻数")
    @TableField("goldDiamondsPerYuan")
    private Integer goldDiamondsPerYuan;

    @ApiModelProperty(value = "版本号")
    @TableField("version")
    private BigDecimal version;

    @ApiModelProperty(value = "版本描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "ios下载地址")
    @TableField("applePackageDownloadUrl")
    private String applePackageDownloadUrl;

    @ApiModelProperty(value = "android下载地址")
    @TableField("androidPackageDownloadUrl")
    private String androidPackageDownloadUrl;

    @ApiModelProperty(value = "人脸相似度")
    @TableField("faceSimilarity")
    private Integer faceSimilarity;

    @ApiModelProperty(value = "会员试用时间（小时）")
    @TableField("memberUsageTime")
    private Integer memberUsageTime;

    @ApiModelProperty(value = "苹果版本号")
    @TableField("appleVersion")
    private String appleVersion;

    @ApiModelProperty(value = "世界之窗发言需要金钻设置")
    @TableField("worldSpeakGlod")
    private Long worldSpeakGlod;

}
