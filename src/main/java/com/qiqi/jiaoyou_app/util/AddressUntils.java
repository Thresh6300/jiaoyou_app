package com.qiqi.jiaoyou_app.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AddressUntils {
    private final static Logger logger = LoggerFactory.getLogger(AddressUntils.class);
 
    /**
     *根据经纬度获取省市区
     * @param log
     * @param lat
     * @return
     */
    public static List<String> getAdd(String log, String lat ){
        //lat 小  log  大
        //参数解释: 纬度,经度 采用高德API可参考高德文档https://lbs.amap.com/
        String key = "2a4a3df5c7c3273cbe11c8cf02875303";
        String urlString = "https://restapi.amap.com/v3/geocode/regeo?location="+log+","+lat+"&key="+key;
        List<String> list = new ArrayList<>();
        String res = "";
        try {
            URL url = new URL(urlString);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                res += line+"\n";
            }
            in.close();
            //解析结果
           JSONObject jsonObject = JSONObject.parseObject(res);
            JSONObject jsonObject1 = jsonObject.getJSONObject("regeocode");
            list.add(jsonObject.getJSONObject("regeocode").getJSONObject("addressComponent").getString("province"));
            String string = jsonObject.getJSONObject("regeocode").getJSONObject("addressComponent").getString("city");
            if ("[]".equals(string)){
                list.add(jsonObject.getJSONObject("regeocode").getJSONObject("addressComponent").getString("district"));
            }else {
                list.add(string);
            }
            list.add(jsonObject.getJSONObject("regeocode").getJSONObject("addressComponent").getString("district"));
        } catch (Exception e) {
            logger.error("获取地址信息异常{}",e.getMessage());
           return null;
        }
        return list;
    }


    public static void main(String[] args) {
        System.out.println(getAdd("113.727476","34.770615"));
    }
 
}