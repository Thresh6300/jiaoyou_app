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

/**
 * <p>
 * 喇叭消费记录
 * </p>
 *
 * @author sunlaiqian
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Speakerconsumptionrecord对象", description="喇叭消费记录")
public class Speakerconsumptionrecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "会员ID")
    @TableField("memberId")
    private Integer memberId;

    @ApiModelProperty(value = "消费时间")
    @TableField("consumptionTime")
    private Date consumptionTime;

    @ApiModelProperty(value = "消费数量")
    @TableField("consumptionSize")
    private Long consumptionSize;

    @ApiModelProperty(value = "剩余数量")
    @TableField("surplusSize")
    private Long surplusSize;


}
