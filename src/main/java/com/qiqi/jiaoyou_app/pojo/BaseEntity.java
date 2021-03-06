package com.qiqi.jiaoyou_app.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class BaseEntity implements Serializable
{
    /** 排序列 */
    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private String orderByColumn;

    /** 搜索值 */
    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private String searchValue;

    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private Integer pageSize = 8;

    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private Integer pageNum = 1;

    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private Integer startIndex;

    @JsonIgnore
    public String getOrderByColumn()
    {
	  return orderByColumn;
    }
    @JsonProperty
    public void setOrderByColumn(String orderByColumn)
    {
	  this.orderByColumn = orderByColumn;
    }

    @JsonIgnore
    public String getSearchValue()
    {
	  return searchValue;
    }
    @JsonProperty
    public void setSearchValue(String searchValue)
    {
	  this.searchValue = searchValue;
    }

    @JsonIgnore
    public Integer getStartIndex()
    {
	  return (pageNum - 1) * pageSize;
    }
    @JsonProperty
    public void setStartIndex(Integer startIndex)
    {
	  this.startIndex = startIndex;
    }

    @JsonIgnore
    public Integer getPageSize()
    {
	  return pageSize;
    }

    @JsonProperty
    public void setPageSize(Integer pageSize)
    {
	  if (pageSize == 0)return;
	  this.pageSize = pageSize;
    }

    @JsonIgnore
    public Integer getPageNum()
    {
	  return pageNum;
    }

    @JsonProperty
    public void setPageNum(Integer pageNum)
    {
	  if (pageNum == 0)return;
	  this.pageNum = pageNum;
    }

}
