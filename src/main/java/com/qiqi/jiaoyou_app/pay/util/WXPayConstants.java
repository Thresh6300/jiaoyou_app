package com.qiqi.jiaoyou_app.pay.util;

import java.util.Random;

/**
 * 常量
 */
public class WXPayConstants {

    public enum SignType {
        MD5, HMACSHA256
    }
    
    //微信支付商户开通后 微信会提供appid和appsecret和商户号partner  
    public static final String APPID = PropertyUtils.tenpayAppid;//在微信开发平台登记的app应用
    public static final String APPSECRET = PropertyUtils.tenpayAppSecret;
    public static final String PRIVATE_KEY = PropertyUtils.tenpayPrivateKey;
    public static final String MCHID = PropertyUtils.tenpayMchId;//商户号
    public static final String SPBILLCREATEIP = PropertyUtils.tenpaySpbillCreateIp;//
    public static final String UNIFIEDORDER = PropertyUtils.tenpayUnifiedorder;
    public static final String ORDERQUERY = PropertyUtils.tenpayOrderquery;
    //微信支付成功后通知地址 必须要求80端口并且地址不能带参数
    public static final String NOTIFYURL = PropertyUtils.tentpayNotifyUrl;
    public static final String PACKAGE = "Sign=WXPay";

    
    public static final String DOMAIN_API = "api.mch.weixin.qq.com";
    public static final String DOMAIN_API2 = "api2.mch.weixin.qq.com";
    public static final String DOMAIN_APIHK = "apihk.mch.weixin.qq.com";
    public static final String DOMAIN_APIUS = "apius.mch.weixin.qq.com";


    public static final String FAIL     = "FAIL";
    public static final String SUCCESS  = "SUCCESS";
    public static final String OK  = "OK";
    public static final String HMACSHA256 = "HMAC-SHA256";
    public static final String MD5 = "MD5";

    public static final String FIELD_SIGN = "sign";
    public static final String FIELD_SIGN_TYPE = "sign_type";

    public static final String MICROPAY_URL_SUFFIX     = "/pay/micropay";
    public static final String UNIFIEDORDER_URL_SUFFIX = "/pay/unifiedorder";
    public static final String ORDERQUERY_URL_SUFFIX   = "/pay/orderquery";
    public static final String REVERSE_URL_SUFFIX      = "/secapi/pay/reverse";
    public static final String CLOSEORDER_URL_SUFFIX   = "/pay/closeorder";
    public static final String REFUND_URL_SUFFIX       = "/secapi/pay/refund";
    public static final String REFUNDQUERY_URL_SUFFIX  = "/pay/refundquery";
    public static final String DOWNLOADBILL_URL_SUFFIX = "/pay/downloadbill";
    public static final String REPORT_URL_SUFFIX       = "/payitil/report";
    public static final String SHORTURL_URL_SUFFIX     = "/tools/shorturl";
    public static final String AUTHCODETOOPENID_URL_SUFFIX = "/tools/authcodetoopenid";

    // sandbox
    public static final String SANDBOX_MICROPAY_URL_SUFFIX     = "/sandboxnew/pay/micropay";
    public static final String SANDBOX_UNIFIEDORDER_URL_SUFFIX = "/sandboxnew/pay/unifiedorder";
    public static final String SANDBOX_ORDERQUERY_URL_SUFFIX   = "/sandboxnew/pay/orderquery";
    public static final String SANDBOX_REVERSE_URL_SUFFIX      = "/sandboxnew/secapi/pay/reverse";
    public static final String SANDBOX_CLOSEORDER_URL_SUFFIX   = "/sandboxnew/pay/closeorder";
    public static final String SANDBOX_REFUND_URL_SUFFIX       = "/sandboxnew/secapi/pay/refund";
    public static final String SANDBOX_REFUNDQUERY_URL_SUFFIX  = "/sandboxnew/pay/refundquery";
    public static final String SANDBOX_DOWNLOADBILL_URL_SUFFIX = "/sandboxnew/pay/downloadbill";
    public static final String SANDBOX_REPORT_URL_SUFFIX       = "/sandboxnew/payitil/report";
    public static final String SANDBOX_SHORTURL_URL_SUFFIX     = "/sandboxnew/tools/shorturl";
    public static final String SANDBOX_AUTHCODETOOPENID_URL_SUFFIX = "/sandboxnew/tools/authcodetoopenid";
    
    /**
     * 产生随机String
     * @param length
     * @return String
     */
    public static String getRandomString(int length){  
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";  
        Random random = new Random();  
        StringBuffer sb = new StringBuffer();  
        for(int i=0; i<length; i++){  
            int number = random.nextInt(62);  
            sb.append(str.charAt(number));  
        }  
        return sb.toString();  
    }  
}

