package com.qiqi.jiaoyou_app.util;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;

public class BankUtils
{
    public static void main(String[] args)
    {


	  String s2= "https://apimg.alipay.com/combo.png?d=cashier&t=PSBC";
	  // String s1 = HttpUtil.get(s);
	  // System.out.println(s1);
	  getBank("6227002434215213555");
    }
    // {"cardType":"DC","bank":"CCB","key":"6227002434215213555","messages":[],"validated":true,"stat":"ok"}
    public static String getBank(String cardNo)
    {
	  String url= "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardNo="+cardNo+"&cardBinCheck=true";
	  String s = HttpUtil.get(url);
	  System.out.println(s);
	  JSONObject jsonObject = JSONObject.parseObject(s);
	  String validated = jsonObject.getString("validated");
	  System.out.println(validated);
	  return validated;
    }
}
