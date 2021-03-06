package com.qiqi.jiaoyou_app.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LableVo {
    /**
     * 魅力默认
     */
    @TableField("label_charm_default")
    private String labelCharmDefault;
    /**
     * 魅力选中
     */
    @TableField("label_charm_select")
    private String labelCharmSelect;
    /**
     * 城市默认
     */
    @TableField("label_city_default")
    private String labelCityDefault;
    /**
     * 城市选中
     */
    @TableField("label_city_select")
    private String labelCitySelect;
    /**
     * 地方默认
     */
    @TableField("label_local_default")
    private String labelLocalDefault;
    /**
     * 地方选中
     */
    @TableField("label_local_select")
    private String labelLocalSelect;
    /**
     * 爱好默认
     */
    @TableField("label_hobby_default")
    private String labelHobbyDefault;
    /**
     * 爱好选中
     */
    @TableField("label_hobby_select")
    private String labelHobbySelect;
    /**
     * 学历默认
     */
    @TableField("label_education_default")
    private String labelEducationDefault;
    /**
     * 学历选中
     */
    @TableField("label_education_select")
    private String labelEducationSelect;
    /**
     * 状态默认
     */
    @TableField("lable_state_default")
    private String lableStateDefault;
    /**
     * 状态选中
     */
    @TableField("lable_state_select")
    private String lableStateSelect;
    /**
     * 年收入默认
     */
    @TableField("lable_annual_income_default")
    private String lableAnnualIncomeDefault;
    /**
     * 年收入选中
     */
    @TableField("lable_annual_income_select")
    private String lableAnnualIncomeSelect;
    /**
     * 车辆默认
     */
    @TableField("lable_car_size_default")
    private String lableCarSizeDefault;
    /**
     * 车辆选中
     */
    @TableField("lable_car_size_select")
    private String lableCarSizeSelect;
    /**
     * 形象默认
     */
    @TableField("lable_image_default")
    private String lableImageDefault;
    /**
     * 形象选中
     */
    @TableField("lable_image_select")
    private String lableImageSelect;
    /**
     * 性格默认
     */
    @TableField("lable_character_default")
    private String lableCharacterDefault;
    /**
     * 性格选中
     */
    @TableField("lable_character_select")
    private String lableCharacterSelect;
    /**
     * 行业默认
     */
    @TableField("lable_industry_default")
    private String lableIndustryDefault;
    /**
     * 行业选中
     */
    @TableField("lable_industry_select")
    private String lableIndustrySelect;
    /**
     * 身高
     */
    @TableField("lable_height")
    private String lableHeight;
    /**
     * 体重
     */
    @TableField("lable_weight")
    private String lableWeight;
    /**
     * 喝酒实力
     */
    @TableField("lable_drinking_power")
    private String lableDrinkingPower;
    /**
     * 我的宠物
     */
    @TableField("lable_pets")
    private String lablePets;
    /**
     * 自我介绍
     */
    @TableField("lable_introduce")
    private String lableIntroduce;
    /**
     * 抽烟习惯
     */
    @TableField("lable_smoking_status")
    private String lableSmokingStatus;

    /**
     * 职业
     */
    @TableField("lable_occupation")
    private String lableOccupation;
}