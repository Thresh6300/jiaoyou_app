package com.qiqi.jiaoyou_app.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class SettingVO {


    private String launchPage;

    private String loginImage;

    private BigDecimal version;

    private String description;

    private String applePackageDownloadUrl;

    private String androidPackageDownloadUrl;
}
