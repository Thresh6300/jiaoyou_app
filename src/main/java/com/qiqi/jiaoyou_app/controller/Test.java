package com.qiqi.jiaoyou_app.controller;

import io.swagger.models.auth.In;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        String str = ",1028,1009,1023,1027,1028,1009,1023,1027,1028,1009,1023,1027";


        Integer id = 1027;
        StringBuffer stringBuffer = new StringBuffer();
        String[] split = str.split(",");
        for (int i = 0; i < split.length;i++){
            if (!String.valueOf(id).equals(split[i])){
               stringBuffer.append(split[i]) ;
               stringBuffer.append(",");
            }
        }

        System.out.println(stringBuffer.toString());
    }




}
