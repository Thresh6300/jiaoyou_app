package com.qiqi.jiaoyou_app.support;

import cn.hutool.setting.dialect.Props;
import com.qiqi.jiaoyou_app.constants.PathParam;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public interface DunPro
{



    Props PROPS = new Props("application.properties");

    // 产品秘钥 id ，由易盾内容安全服务分配，产品标识
    String SECRETID = PROPS.getStr("dun.secretId");

    // 产品秘钥 key
    String SECRETKEY = PROPS.getStr("dun.secretKey");

    // 业务id ，由易盾内容安全服务分配，业务标识
    // # 业务id 普通文本
    String TEXT_BUSINESSID = PROPS.getStr("dun.text.businessId");

    // # 业务id 普通图片
    String IMAGE_BUSINESSID = PROPS.getStr("dun.image.businessId");

    // # 业务id 普通视频
    String VIDEO_BUSINESSID = PROPS.getStr("dun.video.businessId");

    String VIDEO_CALLBACKURL = PathParam.onlinepath + PROPS.getStr("dun.video.callbackurl");

    // 请求当前 UNIX 时间戳，请注意服务器时间是否同步
    Long TIMESTAMP = System.currentTimeMillis();

    // 随机整数，与 timestamp 联合起来，用于防止重放攻击
    Long NONCE = new Random().nextLong();

    // 指定签名算法，国密：SM3,如果使用md5默认签名算法，可以不传
    String SIGNATUREMETHOD = PROPS.getStr("dun.signaturemethod");


    static Map<String, String> getCommonParams()
    {
	  Map<String, String> params = new HashMap<>();
	  // 1.设置公共参数
	  params.put("secretId", SECRETID);
	  params.put("timestamp", TIMESTAMP.toString());
	  params.put("nonce", NONCE.toString());
	  params.put("signatureMethod", SIGNATUREMETHOD); // MD5, SM3, SHA1, SHA256
	  return params;
    }

    /**
     * 通用签名方式
     *
     * @param params 其他所有参数 不包括signature
     * @return
     */
    static String genSignature(Map<String, String> params)
    {
	  // 1. 参数名按照ASCII码表升序排序
	  String[] keys = params.keySet().toArray(new String[0]);
	  Arrays.sort(keys);
	  // 2. 按照排序拼接参数名与参数值
	  StringBuilder sb = new StringBuilder();
	  for (String key : keys)
	  {
		sb.append(key).append(params.get(key));
	  }
	  // 3. 将secretKey拼接到最后
	  sb.append(SECRETKEY);
	  try
	  {
		return DigestUtils.md5Hex(sb.toString().getBytes("UTF-8"));
	  }
	  catch (Exception e)
	  {
		System.out.println("[ERROR] not supposed to happen: " + e.getMessage());
	  }
	  return null;
    }
}
