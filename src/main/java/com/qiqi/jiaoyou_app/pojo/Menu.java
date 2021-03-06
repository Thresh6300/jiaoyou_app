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
import java.util.List;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author sunlaiqian
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Menu对象", description="菜单表")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "f_id", type = IdType.AUTO)
    private Integer fId;

    @ApiModelProperty(value = "分类 1:一级,2:二级,3:三级")
    @TableField("f_clasid")
    private Integer fClasid;

    @ApiModelProperty(value = "二级id")
    @TableField("f_tid")
    private Integer fTid;

    @ApiModelProperty(value = "一级id")
    @TableField("f_oid")
    private Integer fOid;

    @ApiModelProperty(value = "权限名称")
    @TableField("f_name")
    private String fName;
    @ApiModelProperty(value = "权限权重")
    @TableField("f_weight")
    private Integer fWeight;

    @ApiModelProperty(value = "权限地址")
    @TableField("f_address")
    private String fAddress;

    @ApiModelProperty(value = "下级菜单")
    @TableField(exist = false)
    private List<Menu> list;

    public Integer getfWeight() {
        return fWeight;
    }

    public void setfWeight(Integer fWeight) {
        this.fWeight = fWeight;
    }

    public List<Menu> getList() {
        return list;
    }

    public void setList(List<Menu> list) {
        this.list = list;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getfId() {
        return fId;
    }

    public void setfId(Integer fId) {
        this.fId = fId;
    }

    public Integer getfClasid() {
        return fClasid;
    }

    public void setfClasid(Integer fClasid) {
        this.fClasid = fClasid;
    }

    public Integer getfTid() {
        return fTid;
    }

    public void setfTid(Integer fTid) {
        this.fTid = fTid;
    }

    public Integer getfOid() {
        return fOid;
    }

    public void setfOid(Integer fOid) {
        this.fOid = fOid;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getfAddress() {
        return fAddress;
    }

    public void setfAddress(String fAddress) {
        this.fAddress = fAddress;
    }
}
