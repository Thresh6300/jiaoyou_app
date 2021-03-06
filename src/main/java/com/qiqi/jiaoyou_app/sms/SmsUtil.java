package com.qiqi.jiaoyou_app.sms;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.MediaType;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SmsUtil {

    public static String getMD5String(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        send("16638126987","123465");
    }



    public static String send(String phone,String code) {
        String userName = "clevel";
        Long tKey = System.currentTimeMillis()/1000;
        //地址
        String urls = "https://api.mix2.zthysms.com/v2/sendSms";
        //请求入参
        JSONObject requestJson = new JSONObject();
        //账号
        requestJson.put("username", userName);
        //tKey
        requestJson.put("tKey", tKey);
        //明文密码
        requestJson.put("password", SecureUtil.md5(SecureUtil.md5("GG6VGAT1") + tKey));
        //模板ID
        requestJson.put("productid", 33334);
        //手机号码
        requestJson.put("mobile", phone);
        //内容
        requestJson.put("content", "您的验证码为："+code+",请勿泄露与他人。");
        //xh
        requestJson.put("xh", 123456);
        String result = HttpRequest.post(urls)
                .timeout(60000)
                .body(requestJson.toJSONString(), MediaType.APPLICATION_JSON_UTF8_VALUE).execute().body();
        return result.toString();
    }
}




