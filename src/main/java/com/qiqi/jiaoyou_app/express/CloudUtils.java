package com.qiqi.jiaoyou_app.express;


import com.alibaba.fastjson.JSONObject;
import com.qiqi.jiaoyou_app.util.JsonUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class CloudUtils
{

    // 参数名称	是否必填	示例值	            参数描述
    // com	      必须	      ems	            查询的快递公司的编码，一律用小写字母
    // num	      必须	      EM263999513JP	查询的快递单号， 单号的最大长度是32个字符
    // phone	可选	      13868688888	      收件人或寄件人的手机号或固话（顺丰单号必填，也可以填写后四位，如果是固话，请不要上传分机号）
    // from	      可选	      广东省深圳市南山区	出发地城市，省-市-区
    // to	      可选	      北京市朝阳区	      目的地城市，省-市-区
    // resultv2	可选	      1	            添加此字段表示开通行政区域解析功能。0：关闭（默认），1：开通行政区域解析功能，2：开通行政解析功能并且返回出发、目的及当前城市信息
    // show	      可选	      0	            返回数据格式。0：json（默认），1：xml，2：html，3：text
    // order	可选	      desc	            返回结果排序方式。desc：降序（默认），asc：升序
    public static void main(String[] args)
    {

	  ExpressDo expressDo = new ExpressDo();
	  expressDo.setCom("yunda");
	  expressDo.setNum("56565656268");
	  // expressDo.setFrom("北京");
	  // expressDo.setTo("深圳");
	  // expressDo.setPhone("orderManage.getReceverPhone()");
	  expressDo.setResultv2("2");
	  String json = JsonUtils.objectToJson(expressDo);
	  System.out.println(json);

	  Map<String, String> params = new HashMap<>();
	  params.put("param", json);
	  String post = post(params);
	  JSONObject obj = JSONObject.parseObject(post);
	  String status = obj.getString("status");
	  String message = obj.getString("message");
	  String returnCode = obj.getString("returnCode");
	  System.out.println(post);
	  System.out.println(status);
	  System.out.println(message);
	  System.out.println(returnCode);
    }

    public static String post(Map<String, String> params)
    {

	  params.put("secret_key", CloudPro.KUAIDI_SECRET_KEY);
	  params.put("secret_code", CloudPro.KUAIDI_SECRET_CODE);
	  params.put("secret_sign", CloudPro.KUAIDI_SECRET_SIGN);
	  // params.put("secret_sign", MD5Utils.encode(CloudPro.KUAIDI_SECRET_KEY + CloudPro.KUAIDI_SECRET_SECRET));
	  StringBuilder response = new StringBuilder();
	  BufferedReader reader = null;
	  try
	  {

		StringBuilder builder = new StringBuilder();

		for (Map.Entry<String, String> param : params.entrySet())
		{

		    if (builder.length() > 0)
		    {

			  builder.append('&');

		    }

		    builder.append(URLEncoder.encode(param.getKey(), "UTF-8"));

		    builder.append('=');

		    builder.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));

		}

		byte[] bytes = builder.toString().getBytes("UTF-8");

		URL url = new URL("http://cloud.kuaidi100.com/api");

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setConnectTimeout(3000);

		conn.setReadTimeout(3000);

		conn.setRequestMethod("POST");

		conn.setRequestProperty("accept", "*/*");

		conn.setRequestProperty("connection", "Keep-Alive");

		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		conn.setRequestProperty("Content-Length", String.valueOf(bytes.length));

		conn.setDoOutput(true);

		conn.getOutputStream().write(bytes);

		reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

		String line;

		while ((line = reader.readLine()) != null)
		{
		    response.append(line);
		}
	  }
	  catch (UnsupportedEncodingException e)
	  {
		e.printStackTrace();
	  }
	  catch (ProtocolException e)
	  {
		e.printStackTrace();
	  }
	  catch (MalformedURLException e)
	  {
		e.printStackTrace();
	  }
	  catch (IOException e)
	  {
		e.printStackTrace();
	  }
	  finally
	  {
		try
		{
		    if (null != reader)
		    {
			  reader.close();
		    }
		}
		catch (IOException e)
		{
		    e.printStackTrace();
		}
	  }
	  return response.toString();
    }
}

