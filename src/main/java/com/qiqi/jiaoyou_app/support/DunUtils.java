package com.qiqi.jiaoyou_app.support;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.qiqi.jiaoyou_app.util.JsonUtils;

import java.util.*;

public class DunUtils
{

    /**
     * @Description: detectionKeywordDun 调用易盾反垃圾云服务敏感词查询接口
     * @param: dunImagesDo
     * @return: java.lang.String
     * @Author: cfx
     * @Date: 2020-12-24 9:56
     */
    public static String detectionKeywordDun(DunImagesDo dunImagesDo)
    {

	  // 1.设置公共参数
	  String requestUrl = "http://as.dun.163.com/v1/keyword/query";
	  Map<String, String> params = DunPro.getCommonParams();
	  params.put("version", "v1");
	  params.put("businessId", DunPro.TEXT_BUSINESSID);

	  // 2.设置私有参数
	  params.put("id", "163");
	  // 100: 色情，110: 性感，200: 广告，210: 二维码，300: 暴恐，400: 违禁，500: 涉政，600: 谩骂，700: 灌水
	  params.put("category", "100");
	  params.put("keyword", "色情敏感词");
	  params.put("orderType", "1");
	  params.put("pageNum", "1");
	  params.put("pageSize", "20");
	  // 3.生成签名信息
	  String signature = DunPro.genSignature(params);
	  params.put("signature", signature);

	  // 4.发送HTTP请求，这里使用的是HttpClient工具包，产品可自行选择自己熟悉的工具包发送请求
	  String response = HttpClient4Utils.sendPost(requestUrl, params);

	  // 5.解析接口返回值
	  JsonObject resultObject = new JsonParser().parse(response).getAsJsonObject();
	  int code = resultObject.get("code").getAsInt();
	  String msg = resultObject.get("msg").getAsString();
	  if (code == 200)
	  {
		JsonObject result = resultObject.get("result").getAsJsonObject();
		JsonObject words = result.get("words").getAsJsonObject();
		Long count = words.get("count").getAsLong();
		JsonArray rows = words.get("rows").getAsJsonArray();
		for (JsonElement jsonElement : rows)
		{
		    JsonObject row = jsonElement.getAsJsonObject();
		    Long id = row.get("id").getAsLong();
		    String word = row.get("word").getAsString();
		    Integer category = row.get("category").getAsInt();
		    Integer status = row.get("status").getAsInt();
		    Long updateTime = row.get("updateTime").getAsLong();
		    System.out.println(String.format("敏感词查询成功，id: %s，keyword: %s，category: %s，status: %s，updateTime: %s", id, word, category, status, updateTime));
		}
	  }
	  else
	  {
		System.out.println(String.format("ERROR: code=%s, msg=%s", code, msg));
	  }
	  return response;

    }

    /**
     * @Description: detectionImageDun 图片检测
     * @param: list json数组
     * @return: java.lang.String
     * @Author: cfx
     * @Date: 2020-12-24 10:22
     */
    public static String detectionImageDun(List<DunImagesDo> list)
    {
	  String images = JsonUtils.objectToJson(list);

	  String requestUrl = "http://as.dun.163.com/v4/image/check";
	  Map<String, String> params = DunPro.getCommonParams();
	  params.put("businessId", DunPro.IMAGE_BUSINESSID);
	  params.put("version", "v4");
	  // 2.设置私有参数 json格式
	  params.put("images", images);
	  // 3.生成签名信息
	  String signature = DunPro.genSignature(params);
	  params.put("signature", signature);

	  // 4.发送HTTP请求，这里使用的是HttpClient工具包，产品可自行选择自己熟悉的工具包发送请求
	  String response = HttpClient4Utils.sendPost(requestUrl, params);

	  // 5.解析接口返回值
	  JsonObject resultObject = new JsonParser().parse(response).getAsJsonObject();
	  int code = resultObject.get("code").getAsInt();
	  String msg = resultObject.get("msg").getAsString();
	  if (code == 200)
	  {
		// 图片反垃圾结果
		JsonArray antispamArray = resultObject.getAsJsonArray("antispam");
		for (JsonElement jsonElement : antispamArray)
		{
		    JsonObject jObject = jsonElement.getAsJsonObject();
		    String name = jObject.get("name").getAsString();
		    String taskId = jObject.get("taskId").getAsString();
		    int status = jObject.get("status").getAsInt();
		    // 图片检测状态码，定义为：0：检测成功，610：图片下载失败，620：图片格式错误，630：其它
		    if (0 == status)
		    {
			  // 图片维度结果
			  int action = jObject.get("action").getAsInt();
			  JsonArray labelArray = jObject.get("labels").getAsJsonArray();
			  System.out.println(String.format("taskId=%s，status=%s，name=%s，action=%s", taskId, status, name, action));
			  // 产品需根据自身需求，自行解析处理，本示例只是简单判断分类级别
			  for (JsonElement labelElement : labelArray)
			  {
				JsonObject lObject = labelElement.getAsJsonObject();
				int label = lObject.get("label").getAsInt();
				int level = lObject.get("level").getAsInt();
				double rate = lObject.get("rate").getAsDouble();
				JsonArray subLabels = lObject.getAsJsonArray("subLabels");
				System.out.println(String.format("label:%s, level=%s, rate=%s, subLabels=%s", label, level, rate, subLabels.toString()));
			  }
			  switch (action)
			  {
				case 0:
				    System.out.println("#图片机器检测结果：最高等级为\"正常\"\n");
				    break;
				case 1:
				    System.out.println("#图片机器检测结果：最高等级为\"嫌疑\"\n");
				    break;
				case 2:
				    System.out.println("#图片机器检测结果：最高等级为\"确定\"\n");
				    break;
				default:
				    break;
			  }
		    }
		    else
		    {
			  // status对应失败状态码：610：图片下载失败，620：图片格式错误，630：其它
			  System.out.println(String.format("图片检测失败，taskId=%s，status=%s，name=%s", taskId, status, name));
		    }
		}
		// 图片OCR结果
		JsonArray ocrArray = resultObject.getAsJsonArray("ocr");
		for (JsonElement jsonElement : ocrArray)
		{
		    JsonObject jObject = jsonElement.getAsJsonObject();
		    String name = jObject.get("name").getAsString();
		    String taskId = jObject.get("taskId").getAsString();
		    JsonArray details = jObject.get("details").getAsJsonArray();
		    System.out.println(String.format("taskId=%s,name=%s", taskId, name));
		    // 产品需根据自身需求，自行解析处理，本示例只是简单输出ocr结果信息
		    for (JsonElement detail : details)
		    {
			  JsonObject lObject = detail.getAsJsonObject();
			  String content = lObject.get("content").getAsString();
			  JsonArray lineContents = lObject.getAsJsonArray("lineContents");
			  System.out.println(String.format("识别ocr文本内容:%s, ocr片段及坐标信息:%s", content, lineContents.toString()));
		    }
		}
		// 图片人脸检测结果
		JsonArray faceArray = resultObject.getAsJsonArray("face");
		for (JsonElement jsonElement : faceArray)
		{
		    JsonObject jObject = jsonElement.getAsJsonObject();
		    String name = jObject.get("name").getAsString();
		    String taskId = jObject.get("taskId").getAsString();
		    JsonArray details = jObject.get("details").getAsJsonArray();
		    System.out.println(String.format("taskId=%s,name=%s", taskId, name));
		    // 产品需根据自身需求，自行解析处理，本示例只是简单输出人脸结果信息
		    for (JsonElement detail : details)
		    {
			  JsonObject lObject = detail.getAsJsonObject();
			  int faceNumber = lObject.get("faceNumber").getAsInt();
			  JsonArray faceContents = lObject.getAsJsonArray("faceContents");
			  System.out.println(String.format("识别人脸数量:%s, 人物信息及坐标信息:%s", faceNumber, faceContents.toString()));
		    }
		}
		// 图片质量检测结果
		JsonArray qualityArray = resultObject.getAsJsonArray("quality");
		for (JsonElement jsonElement : qualityArray)
		{
		    JsonObject jObject = jsonElement.getAsJsonObject();
		    String name = jObject.get("name").getAsString();
		    String taskId = jObject.get("taskId").getAsString();
		    JsonArray details = jObject.get("details").getAsJsonArray();
		    System.out.println(String.format("taskId=%s,name=%s", taskId, name));
		    // 产品需根据自身需求，自行解析处理，本示例只是简单输出质量结果信息
		    for (JsonElement detail : details)
		    {
			  JsonObject lObject = detail.getAsJsonObject();
			  float aestheticsRate = lObject.get("aestheticsRate").getAsFloat();
			  JsonObject metaInfo = lObject.getAsJsonObject("metaInfo");
			  JsonObject boarderInfo = lObject.getAsJsonObject("boarderInfo");
			  System.out.println(String.format("图片美观度分数:%s, 图片基本信息:%s, 图片边框信息:%s", aestheticsRate, metaInfo.toString(), boarderInfo.toString()));
		    }
		}
	  }
	  else
	  {
		System.out.println(String.format("ERROR: code=%s, msg=%s", code, msg));
	  }

	  return response;
    }

    /**
     * @Description: detectionTextDun 文本检测
     * @param: content
     * @return: java.lang.String
     * @Author: cfx
     * @Date: 2020-12-24 10:22
     */
    public static String detectionTextDun(String content)
    {
	  String requestUrl = "http://as.dun.163.com/v3/text/check";
	  Map<String, String> params = DunPro.getCommonParams();
	  params.put("version", "v3.1");
	  params.put("businessId", DunPro.TEXT_BUSINESSID);
	  // 2.设置私有参数 json格式
	  params.put("dataId", "1");
	  params.put("content", content);
	  // params.put("checkLabels", "200, 500"); // 指定过检分类

	  // 3.生成签名信息
	  String signature = DunPro.genSignature(params);
	  params.put("signature", signature);

	  // 4.发送HTTP请求，这里使用的是HttpClient工具包，产品可自行选择自己熟悉的工具包发送请求
	  String response = HttpClient4Utils.sendPost(requestUrl, params);

	  // 5.解析接口返回值
	  JsonObject jObject = new JsonParser().parse(response).getAsJsonObject();
	  int code = jObject.get("code").getAsInt();
	  String msg = jObject.get("msg").getAsString();
	  if (code == 200)
	  {
		JsonObject resultObject = jObject.getAsJsonObject("result");
		int action = resultObject.get("action").getAsInt();
		String taskId = resultObject.get("taskId").getAsString();
		JsonArray labelArray = resultObject.getAsJsonArray("labels");
		if (action == 0)
		{
		    System.out.println(String.format("taskId=%s，文本机器检测结果：通过", taskId));
		    response = "0";
		}
		else if (action == 1)
		{
		    response = "1";
		    System.out.println(String.format("taskId=%s，文本机器检测结果：嫌疑，需人工复审，分类信息如下：%s", taskId, labelArray.toString()));
		}
		else if (action == 2)
		{
		    response = "2";
		    System.out.println(String.format("taskId=%s，文本机器检测结果：不通过，分类信息如下：%s", taskId, labelArray.toString()));
		}
	  }
	  else
	  {
		System.out.println(String.format("ERROR: code=%s, msg=%s", code, msg));
	  }
	  System.out.println(params);
	  return response;
    }

    /**
     * @Description: detectionVideoDun 视频检测
     * @param: url
     * @return: java.lang.String
     * @Author: cfx
     * @Date: 2020-12-24 10:22
     */
    public static String detectionVideoDun(String url)
    {
	  String requestUrl = "http://as.dun.163.com/v3/video/submit";

	  Map<String, String> params = DunPro.getCommonParams();
	  params.put("version", "v3.1");
	  params.put("businessId", DunPro.VIDEO_BUSINESSID);
	  params.put("callbackUrl", DunPro.VIDEO_CALLBACKURL);

	  // 2.设置私有参数
	  params.put("dataId", "fbfcad1c-dba1-490c-b4de-e784c2691765");
	  params.put("url", url);

	  // 3.生成签名信息
	  String signature = DunPro.genSignature(params);
	  params.put("signature", signature);

	  // 4.发送HTTP请求，这里使用的是HttpClient工具包，产品可自行选择自己熟悉的工具包发送请求
	  String response = HttpClient4Utils.sendPost(requestUrl, params);

	  // 5.解析接口返回值
	  JsonObject jObject = new JsonParser().parse(response).getAsJsonObject();
	  int code = jObject.get("code").getAsInt();
	  String msg = jObject.get("msg").getAsString();
	  if (code == 200)
	  {
		JsonObject result = jObject.getAsJsonObject("result");
		// status 0:成功，1:失败
		int status = result.get("status").getAsInt();
		String taskId = result.get("taskId").getAsString();
		if (status == 0)
		{
		    System.out.println(String.format("推送成功!taskId=%s", taskId));
		}
		else
		{
		    System.out.println(String.format("推送失败!taskId=%s", taskId));
		}
	  }
	  else
	  {
		System.out.println(String.format("ERROR: code=%s, msg=%s", code, msg));
	  }
	  return response;
    }

    /**
     * @Description: detectionVideoResultsDun 视频检测回调接口
     * @return: java.lang.String
     * @Author: cfx
     * @Date: 2020-12-24 10:23
     */
    public static String detectionVideoResultsDun(String taskIds)
    {
	  String requestUrl = "http://as.dun.163.com/v3/video/callback/results";

	  Map<String, String> params = DunPro.getCommonParams();
	  params.put("version", "v3.1");
	  params.put("businessId", DunPro.VIDEO_BUSINESSID);
	  // 2.设置私有参数
	  // params.put("dataId", "fbfcad1c-dba1-490c-b4de-e784c2691765");
	  params.put("taskId", taskIds);

	  // 3.生成签名信息
	  String signature = DunPro.genSignature(params);
	  params.put("signature", signature);

	  // 4.发送HTTP请求，这里使用的是HttpClient工具包，产品可自行选择自己熟悉的工具包发送请求
	  String response = HttpClient4Utils.sendPost(requestUrl, params);

	  // 5.解析接口返回值
	  JsonObject resultObject = new JsonParser().parse(response).getAsJsonObject();
	  int code = resultObject.get("code").getAsInt();
	  String msg = resultObject.get("msg").getAsString();
	  if (code == 200)
	  {
		JsonArray resultArray = resultObject.getAsJsonArray("result");
		if (resultArray.size() == 0)
		{
		    System.out.println("暂无回调数据");
		}
		else
		{
		    for (JsonElement jsonElement : resultArray)
		    {
			  JsonObject jObject = jsonElement.getAsJsonObject();
			  int status = jObject.get("status").getAsInt();
			  if (status != 0)
			  {// 异常，异常码定义见官网文档
				System.out.println("视频异常，status=" + status);
				continue;
			  }
			  String taskId = jObject.get("taskId").getAsString();
			  String callback = jObject.get("callback").getAsString();
			  int videoLevel = jObject.get("level").getAsInt();
			  if (videoLevel == 0)
			  {
				System.out.println(String.format("taskId:%s, 正常, callback=%s", taskId, callback));
			  }
			  else if (videoLevel == 1 || videoLevel == 2)
			  {
				JsonArray evidenceArray = jObject.get("evidences").getAsJsonArray();
				for (JsonElement evidenceElement : evidenceArray)
				{
				    JsonObject eObject = evidenceElement.getAsJsonObject();
				    long beginTime = eObject.get("beginTime").getAsLong();
				    long endTime = eObject.get("endTime").getAsLong();
				    int type = eObject.get("type").getAsInt();
				    String url = eObject.get("url").getAsString();
				    JsonArray labelArray = eObject.get("labels").getAsJsonArray();
				    for (JsonElement labelElement : labelArray)
				    {
					  JsonObject lObject = labelElement.getAsJsonObject();
					  int label = lObject.get("label").getAsInt();
					  int level = lObject.get("level").getAsInt();
					  double rate = lObject.get("rate").getAsDouble();
				    }
				    System.out.println(String.format("taskId:%s, %s, callback=%s, 证据信息：%s, 证据分类：%s, ", taskId, videoLevel == 1 ? "不确定" : "确定", callback, eObject, labelArray));
				}
			  }
		    }
		}
	  }
	  else
	  {
		System.out.println(String.format("ERROR: code=%s, msg=%s", code, msg));
	  }
	  return response;
    }

    /**
     * @Description: detectionVideoQueryDun 点播视频离线接口方式获取
     * @param: set
     * @return: java.lang.String
     * @Author: cfx
     * @Date: 2020-12-24 10:59
     */
    public static String detectionVideoQueryDun(Set<String> set)
    {
	  String requestUrl = "http://as.dun.163.com/v3/video/callback/results";

	  String taskIds = JsonUtils.objectToJson(set);
	  // 1.设置公共参数
	  Map<String, String> params = DunPro.getCommonParams();
	  params.put("version", "v3.1");
	  params.put("businessId", DunPro.VIDEO_BUSINESSID);
	  // 2.设置私有参数
	  params.put("taskIds", taskIds);

	  // 3.生成签名信息
	  String signature = DunPro.genSignature(params);
	  params.put("signature", signature);

	  // 4.发送HTTP请求，这里使用的是HttpClient工具包，产品可自行选择自己熟悉的工具包发送请求
	  String response = HttpClient4Utils.sendPost(requestUrl, params);

	  // 5.解析接口返回值
	  JsonObject resultObject = new JsonParser().parse(response).getAsJsonObject();
	  int code = resultObject.get("code").getAsInt();
	  String msg = resultObject.get("msg").getAsString();
	  if (code == 200)
	  {
		JsonArray resultArray = resultObject.getAsJsonArray("result");
		if (resultArray.size() == 0)
		{
		    System.out.println("暂时没有结果需要获取，请稍后重试！");
		}
		else
		{
		    for (JsonElement jsonElement : resultArray)
		    {
			  JsonObject jObject = jsonElement.getAsJsonObject();
			  int status = jObject.get("status").getAsInt();
			  String taskId = jObject.get("taskId").getAsString();
			  if (status == 0)
			  {
				int result = jObject.get("result").getAsInt();
				System.out.println(String.format("点播音视频, taskId:%s, 检测结果:%s", taskId, result));
				if (jObject.has("evidences"))
				{
				    JsonObject evidences = jObject.get("evidences").getAsJsonObject();
				    if (evidences.has("text"))
				    {
					  JsonObject text = evidences.get("text").getAsJsonObject();
					  System.out.println(String.format("文本信息, taskId:%s, 检测结果:%s", text.get("taskId").getAsString(), text.get("action").getAsInt()));

				    }
				    else if (evidences.has("images"))
				    {
					  JsonArray images = evidences.get("images").getAsJsonArray();
					  for (int i = 0; i < images.size(); i++)
					  {
						JsonObject image = images.get(i).getAsJsonObject();
						System.out.println(String.format("图片信息, taskId:%s, 检测结果:%s", image.get("taskId").getAsString(), image.get("labels").getAsJsonArray().toString()));
					  }
				    }
				    else if (evidences.has("audio"))
				    {
					  JsonObject audio = evidences.get("audio").getAsJsonObject();
					  System.out.println(String.format("语音信息, taskId:%s, 检测状态:%s, 检测结果:%s", audio.get("taskId").getAsString(), audio.get("asrStatus").getAsInt(), audio.get("action").getAsInt()));
				    }
				    else if (evidences.has("video"))
				    {
					  JsonObject video = evidences.get("video").getAsJsonObject();
					  System.out.println(String.format("视频信息, taskId:%s, 检测状态:%s, 检测结果:%s", video.get("taskId").getAsString(), video.get("status").getAsInt(), video.get("level").getAsInt()));
				    }
				}
				else if (jObject.has("reviewEvidences"))
				{
				    JsonObject reviewEvidences = jObject.get("reviewEvidences").getAsJsonObject();
				    String reason = reviewEvidences.get("reason").getAsString();
				    JsonObject detail = reviewEvidences.get("detail").getAsJsonObject();
				    JsonArray text = detail.get("text").getAsJsonArray();
				    JsonArray image = detail.get("image").getAsJsonArray();
				    JsonArray audio = detail.get("audio").getAsJsonArray();
				    JsonArray video = detail.get("video").getAsJsonArray();
				    System.out.println(String.format("人审证据信息, 音视频taskId:%s, reason:%s, 文本:%s, 图片:%s, 语音:%s, 视频:%s", taskId, reason, text, image, audio, video));
				}
			  }
			  else if (status == 20)
			  {
				System.out.println(String.format("点播音视频, taskId:%s, 数据非7天内", taskId));
			  }
			  else if (status == 30)
			  {
				System.out.println(String.format("点播音视频, taskId:%s, 数据不存在", taskId));
			  }
		    }
		}
	  }
	  else
	  {
		System.out.println(String.format("ERROR: code=%s, msg=%s", code, msg));
	  }
	  return response;
    }


    public static void main(String[] args)
    {
	  List<DunImagesDo> images = new ArrayList<>();
	  DunImagesDo dunImagesDo = new DunImagesDo();
	  dunImagesDo.setType(1);
	  dunImagesDo.setName("2-0-0-a6133509763d4d6eac881a58f1791976.jpg");
	  dunImagesDo.setData("https://nos.netease.com/yidun/2-0-0-a6133509763d4d6eac881a58f1791976.jpg");
	  images.add(dunImagesDo);
	  String json = JsonUtils.objectToJson(images);
	  System.out.println(json);
	  // {images={"images":[{"name":"https://nos.netease.com/yidun/2-0-0-a6133509763d4d6eac881a58f1791976.jpg","type":1,"data":"https://nos.netease.com/yidun/2-0-0-a6133509763d4d6eac881a58f1791976.jpg"}],"dataId":null,"content":null,"url":null}, signature=99ea6f56c89b7ef103a2cd4e1b270268, businessId=72202efed79a1b83b3a923f9c8e98cc3, secretId=57721d55b323cebbea6c37965632d3f8, signatureMethod=MD5, nonce=-3045999408935571744, version=v4, timestamp=1608719363117}
	  // {images={"name":"https://nos.netease.com/yidun/2-0-0-a6133509763d4d6eac881a58f1791976.jpg","type":1,"data":"https://nos.netease.com/yidun/2-0-0-a6133509763d4d6eac881a58f1791976.jpg"}, signature=ba27296ca5eab804ee80638293f9e68d, businessId=72202efed79a1b83b3a923f9c8e98cc3, secretId=57721d55b323cebbea6c37965632d3f8, signatureMethod=MD5, nonce=-6168857917556366880, version=v4, timestamp=1608719528623}
	  // {images=[{"name":"https://nos.netease.com/yidun/2-0-0-a6133509763d4d6eac881a58f1791976.jpg","type":1,"data":"https://nos.netease.com/yidun/2-0-0-a6133509763d4d6eac881a58f1791976.jpg"},{"name":"{\"imageId\": 33451123, \"contentId\": 78978}","type":2,"data":"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"}], signature=2ffc9ecdf100180440474ecc6599d745, businessId=72202efed79a1b83b3a923f9c8e98cc3, secretId=57721d55b323cebbea6c37965632d3f8, signatureMethod=MD5, nonce=1522222099532905770, version=v4, timestamp=1608719563092}
	  // {images=[{"name":"https://nos.netease.com/yidun/2-0-0-a6133509763d4d6eac881a58f1791976.jpg","type":1,"data":"https://nos.netease.com/yidun/2-0-0-a6133509763d4d6eac881a58f1791976.jpg"}], signature=203c301eebe95d88319cc43da82eb9e9, businessId=72202efed79a1b83b3a923f9c8e98cc3, secretId=57721d55b323cebbea6c37965632d3f8, signatureMethod=MD5, nonce=2878904573399119577, version=v4, timestamp=1608719770442}

	  // String s = detectionVideoDun("https://cz-video-view.oss-cn-beijing.aliyuncs.com/20191017/6b7c8203cfd904810c305b54f931bea0.mp4");
	  Set<String> taskIds = new HashSet<>();
	  taskIds.add("f477d8007613493ab32192822ff45aaa");
	  String s1 = detectionVideoResultsDun("f477d8007613493ab32192822ff45aaa");
	  System.out.println(s1);
	  // String s1 = detectionVideoDun("https://cz-video-view.oss-cn-beijing.aliyuncs.com/20191017/6b7c8203cfd904810c305b54f931bea0.mp4");
	  // System.out.println(s1);
	  // System.out.println(s);

    }

}
