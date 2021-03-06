package com.qiqi.jiaoyou_app.pay.util;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class WXSignUtil {
    /**
     * 生成签名参数
     * 
     * @param param
     * @return
     */
    public static String createSign(SortedMap<String, String> param) {
        StringBuffer sb = new StringBuffer();
        Set es = param.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        // 拼接
        sb.append("key=" + WXPayConstants.PRIVATE_KEY);

        String sign = null;
        try {
            sign = MD5(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sign;
    }


    /**
     * 生成 MD5
     *
     * @param data 待处理数据
     * @return MD5结果
     */
    public static String MD5(String data) throws Exception {
        java.security.MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }
}

