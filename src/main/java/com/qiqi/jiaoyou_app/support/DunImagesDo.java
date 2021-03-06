package com.qiqi.jiaoyou_app.support;

import lombok.Data;

@Data
public class DunImagesDo
{
    // 图片名称
    private String name;
    // 类型，分别为1：图片URL，2:图片BASE64值
    private Integer type;
    // 图片URL检测单次请求最多支持32张
    private String data;

}
