package com.qiqi.jiaoyou_app.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author GR123
 * @since 2020-07-15
 */
public class Diamonds extends Model<Diamonds> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "diamonds_id", type = IdType.AUTO)
    private Integer diamondsId;
    /**
     * 产品ID
     */
    @TableField("diamonds_product_id")
    private String diamondsProductId;

    /**
     * 产品ID
     */
    @TableField("diamonds_name")
    private String diamondsName;
    /**
     * 金额
     */
    @TableField("diamonds_price")
    private BigDecimal diamondsPrice;
    /**
     * 钻石数量
     */
    @TableField("diamonds_size")
    private Integer diamondsSize;
    /**
     * 1:黑卡 2：会员 3：钻石
     */
    @TableField("diamonds_type")
    private Integer diamondsType;
    /**
     * 1:苹果 2;安卓
     */
    @TableField("diamonds_terminal")
    private Integer diamondsTerminal;

    /**
     * 1:苹果 2;安卓
     */
    @TableField("month")
    private String month;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDiamondsName() {
        return diamondsName;
    }

    public void setDiamondsName(String diamondsName) {
        this.diamondsName = diamondsName;
    }

    public Integer getDiamondsId() {
        return diamondsId;
    }

    public Diamonds setDiamondsId(Integer diamondsId) {
        this.diamondsId = diamondsId;
        return this;
    }

    public String getDiamondsProductId() {
        return diamondsProductId;
    }

    public Diamonds setDiamondsProductId(String diamondsProductId) {
        this.diamondsProductId = diamondsProductId;
        return this;
    }

    public BigDecimal getDiamondsPrice() {
        return diamondsPrice;
    }

    public void setDiamondsPrice(BigDecimal diamondsPrice) {
        this.diamondsPrice = diamondsPrice;
    }

    public Integer getDiamondsSize() {
        return diamondsSize;
    }

    public Diamonds setDiamondsSize(Integer diamondsSize) {
        this.diamondsSize = diamondsSize;
        return this;
    }

    public Integer getDiamondsType() {
        return diamondsType;
    }

    public Diamonds setDiamondsType(Integer diamondsType) {
        this.diamondsType = diamondsType;
        return this;
    }

    public Integer getDiamondsTerminal() {
        return diamondsTerminal;
    }

    public Diamonds setDiamondsTerminal(Integer diamondsTerminal) {
        this.diamondsTerminal = diamondsTerminal;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.diamondsId;
    }

    @Override
    public String toString() {
        return "Diamonds{" +
        "diamondsId=" + diamondsId +
        ", diamondsProductId=" + diamondsProductId +
        ", diamondsPrice=" + diamondsPrice +
        ", diamondsSize=" + diamondsSize +
        ", diamondsType=" + diamondsType +
        ", diamondsTerminal=" + diamondsTerminal +
        "}";
    }
}
