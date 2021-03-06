package com.qiqi.jiaoyou_app.util;


import java.io.Serializable;

public class ResultUtils implements Serializable {

    private Integer code;
    private Integer status;
    private String message;
    private String userStatus;

    @Override
    public String toString()
    {
	  return "ResultUtils{" + "code=" + code + ", status=" + status + ", message='" + message + '\'' + ", userStatus='" + userStatus + '\'' + ", pages=" + pages + ", count=" + count + ", data=" + data + ", data1=" + data1 + ", data2=" + data2 + ", data3=" + data3 + '}';
    }

    public String getUserStatus()
    {
	  return userStatus;
    }

    public void setUserStatus(String userStatus)
    {
	  this.userStatus = userStatus;
    }

    private Integer pages;
    private Integer count;
    private Object data;
    private Object data1;
    private Object data2;
    private Object data3;

    public ResultUtils(){};

    public Object getData3()
    {
	  return data3;
    }

    public void setData3(Object data3)
    {
	  this.data3 = data3;
    }

    public Object getData2()
    {
	  return data2;
    }

    public void setData2(Object data2)
    {
	  this.data2 = data2;
    }

    public Integer getCode()
    {
	  return code;
    }

    public void setCode(Integer code)
    {
	  this.code = code;
    }

    public Integer getStatus()
    {
	  return status;
    }

    public void setStatus(Integer status)
    {
	  this.status = status;
    }

    public String getMessage()
    {
	  return message;
    }

    public void setMessage(String message)
    {
	  this.message = message;
    }

    public Integer getPages()
    {
	  return pages;
    }

    public void setPages(Integer pages)
    {
	  this.pages = pages;
    }

    public Integer getCount()
    {
	  return count;
    }

    public void setCount(Integer count)
    {
	  this.count = count;
    }

    public Object getData()
    {
	  return data;
    }

    public void setData(Object data)
    {
	  this.data = data;
    }

    public Object getData1()
    {
	  return data1;
    }

    public void setData1(Object data1)
    {
	  this.data1 = data1;
    }
}
