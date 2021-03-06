package com.qiqi.jiaoyou_app.support;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * HttpClient工具类
 *
 * @author hzgaomin
 * @version 2016年2月3日
 */
public class HttpClient4Utils
{
    /**
     * 实例化HttpClient，发送http请求使用，可根据需要自行调参
     */
    private static HttpClient httpClient = HttpClient4Utils.createHttpClient(100, 20, 2000, 2000, 2000);

    /**
     * 实例化HttpClient
     *
     * @param maxTotal
     * @param maxPerRoute
     * @param socketTimeout
     * @param connectTimeout
     * @param connectionRequestTimeout
     * @return
     */
    public static HttpClient createHttpClient(int maxTotal, int maxPerRoute, int socketTimeout, int connectTimeout, int connectionRequestTimeout)
    {
	  RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectionRequestTimeout).build();
	  PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
	  cm.setMaxTotal(maxTotal);
	  cm.setDefaultMaxPerRoute(maxPerRoute);
	  CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(defaultRequestConfig).build();
	  return httpClient;
    }

    /**
     * 发送post请求
     *
     * @param url        请求地址
     * @param params     请求参数
     * @return
     */
    public static String sendPost(String url, Map<String, String> params)
    {
        return sendPost(url,params,Consts.UTF_8);
    }
    /**
     * 发送post请求
     *
     * @param url        请求地址
     * @param params     请求参数
     * @param encoding   编码
     * @return
     */
    public static String sendPost(String url, Map<String, String> params, Charset encoding)
    {
	  String resp = "";
	  HttpPost httpPost = new HttpPost(url);
	  if (params != null && params.size() > 0)
	  {
		List<NameValuePair> formParams = new ArrayList<>();
		Iterator<Map.Entry<String, String>> itr = params.entrySet().iterator();
		while (itr.hasNext())
		{
		    Map.Entry<String, String> entry = itr.next();
		    formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(formParams, encoding);
		httpPost.setEntity(postEntity);
	  }
	  CloseableHttpResponse response = null;
	  try
	  {
		response = (CloseableHttpResponse) httpClient.execute(httpPost);
		resp = EntityUtils.toString(response.getEntity(), encoding);
	  }
	  catch (Exception e)
	  {
		// log
		e.printStackTrace();
	  }
	  finally
	  {
		if (response != null)
		{
		    try
		    {
			  response.close();
		    }
		    catch (IOException e)
		    {
			  // log
			  e.printStackTrace();
		    }
		}
	  }
	  return resp;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param requestUrl   发送请求的 URL
     * @param params 请求参数，请求参数是 Map key-value 的形式 value可以是json格式.
     * @return 所代表远程资源的响应结果
     */
    public static String sendPosts(String requestUrl, Map<String, String> params)
    {

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

		URL url = new URL(requestUrl);

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

		reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), Consts.UTF_8));

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