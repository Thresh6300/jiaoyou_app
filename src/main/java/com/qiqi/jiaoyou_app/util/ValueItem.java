package com.qiqi.jiaoyou_app.util;

import java.util.List;

public class ValueItem
{
    private String Tag;

    private String Value;

    //添加无参的构造器
    public ValueItem(){
    }

    public void setTag(String Tag){
        this.Tag = Tag;
    }
    public String getTag(){
        return this.Tag;
    }
    public void setValue(String Value){
        this.Value = Value;
    }
    public String getValue(){
        return this.Value;
    }

    @Override
    public String toString() {
        return "ValueItem{" +
                "Tag='" + Tag + '\'' +
                ", Value='" + Value + '\'' +
                '}';
    }
}

