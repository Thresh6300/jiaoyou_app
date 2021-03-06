package com.qiqi.jiaoyou_app.util;

import java.util.List;

public class UserDataItem
{
    private String To_Account;

    private List<ValueItem> ValueItem;

    //添加无参的构造器
    public UserDataItem(){
    }
    public void setTo_Account(String To_Account){
        this.To_Account = To_Account;
    }
    public String getTo_Account(){
        return this.To_Account;
    }
    public void setValueItem(List<ValueItem> ValueItem){
        this.ValueItem = ValueItem;
    }
    public List<ValueItem> getValueItem(){
        return this.ValueItem;
    }

    @Override
    public String toString() {
        return "UserDataItem{" +
                "To_Account='" + To_Account + '\'' +
                ", ValueItem=" + ValueItem +
                '}';
    }
}
