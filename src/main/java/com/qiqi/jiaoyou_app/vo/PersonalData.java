package com.qiqi.jiaoyou_app.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.qiqi.jiaoyou_app.pojo.Lable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="个人资料Vo")
public class PersonalData {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "昵称")
    @TableField("nickName")
    private String nickName;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "头像")
    private String head;

    @ApiModelProperty(value = "性别 1：男2：女")
    private Integer sex;

    @ApiModelProperty(value = "所在省")
    private String province;

    @ApiModelProperty(value = "所在市")
    private String city;

    @ApiModelProperty(value = "所在区")
    private String area;

    @ApiModelProperty(value = "标签子类")
    private LableVo lable;
}
