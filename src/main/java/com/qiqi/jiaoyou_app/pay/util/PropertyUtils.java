package com.qiqi.jiaoyou_app.pay.util;

import com.qiqi.jiaoyou_app.constants.PathParam;

public class PropertyUtils {
    
    //微信支付
    //微信开放平台审核通过的应用APPID
    public final static String tenpayUnifiedorder="https://api.mch.weixin.qq.com/pay/unifiedorder";
    public final static String tenpayOrderquery="https://api.mch.weixin.qq.com/pay/orderquery";
    public final static String tenpayAppid="wx63b93e16038edba5";
    //商户号
    public final static String tenpayMchId="1601062946";
    //AppSecret
    public final static String tenpayAppSecret="9d57962a3a327f554dceb95a3a611a42";
    //API密钥
    public final static String tenpayPrivateKey="D852F0F6BB68A389BBBC24326E6E093D";
    //微信服务器主动通知商户服务器里指定的页面http/https路径。建议商户使用https
    public final static String tentpayNotifyUrl=PathParam.onlinepath+"/jiaoyou_app/tenpayNotify";
    //本应用IP
    public final static String tenpaySpbillCreateIp=PathParam.IP;

    
    
    //支付宝支付
    //支付宝网关（固定）
    public final static String alipayGATEWAYURL="https://openapi.alipay.com/gateway.do";
    //应用ID
    public final static String alipayAPPID="2021001181654719";
    //应用私钥
    public final static String alipayAPPPRIVATEKEY="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDtXlsJ4EooNfqFHcNmHFzUMXAFDrAvYILfFtkQGO53sHJQhK3mkIezws6e3YKMDYdKFI24he4dMNldHDNhDIhRUivWKqY+fUzd47bsxn1TOr3jw6dXxn83p2AAqguhPsUs6XM4oDEaHW4+WPYqkmN+WEJbzZ+XGaqHj3xijMXtHjXr3CBnJXLB5Ah6GtL67AQUQrCTcNpbOcG2JK63v6g3xxKd1lsWVgAuXZ4kRC5t8eezaUHni+YN/Ep1Vms2hI5rlHEKB4d5yi8C9DNuZ0i+c8VxR1UcwHABtvNQm0abX4ELZWblzuVAPnD7bsOgDbFc9+w0oNCqu+goWGBYASOdAgMBAAECggEAc4j4sO7tyXK0lHRvNlVZzaKejqeelADa0nFRh+dKsxE8IvrhlhcFJjcM0QCqWTcAgctNS+JMiwLK2eovC+7IryRa1+x8bHAaqOn8kHTwbUFrSeBipHOmWlVqgghCjuJ5oKmkfoxiOf7XORud/gywxr941SxZoHdKrJ7ngSRZ9lZNGgTyOGRyRegUmnkGObZRz/ZGyjY62j7m/BTIiXxNUfDk4SWunZ1g7Ce2zItYoSxYXe8zlziBRnxn9ja4zINhNaKPQjPX6lIrWm4wyZmNNdzAKnJP1wxYBZqrVGJTo6RhHgrpK5KF+piqVGQd4FLzgr47+lVdSBloHAKDRJUvgQKBgQD2iPTTV1on9AnZs2isbFhDlO5GKO27E6QAJtbdLtJ/rFRRP2E2Z2M1nvkCmoE/pX6/lAVO14rSDqqu76F7+hQ92CuJZxBMvIg+cuCudQL4fCW9HRJcnd73hyjEi8UtJs5tXLh8jstKiPpdgFSjrGzlyBsMMN90omBx8On+WM3cvQKBgQD2e07j2tyY9cBaZbyiVW6z61/B6Y2SFhAJRCgaPgDSblSlT+UnnDmw056G2MUIs14T78miWz2sY/FjY15yB/GQnipja9KoNiR3HFSgjxXqY+ITWJvmuYMoCIKJ2RIpK0+0WJZnEt+bOHPX7KgpuRWvfL2eXvXfULdVhqkHmUqAYQKBgQCCN6TZXHJKWCQ70P+g0zlruOaCOGwZPC6LxfgHrAuTVDrVD10Rl1pXoDh2n5VVQNzJ5C1Jj5/Lg+Ozsu7gxR6b6+pTERUS8yIjmhoDC9sd1LU6RnjEYr66j7GA3zKuvqiaKzdm0yWdoOxdJsOOEC0U0bU3ozjkKkA27sEpj05tIQKBgCfkdzU7o/6Br9sxoiEn8J3gF4R793TQa1IEnPT6Wgm037BNyQnrKA8tgPfUN7ebyDsz0Q+c8tNm7Zenao7MKFW+s5+ZwOOGt8orZKsD3YktF1cIPVtPZSHilrYW0vCk2OaYe+c8z8IAlgc5f1IZaaAYcVwjhGnbV4DZJZxcPAmhAoGAT3mZjnc2yHex4CxA8NC+CLqLupD5M2MpasOBP5Iv2TgBm+Q0bpOxPMx+7xdHe42pPJG5Be52iDw+QHR+1d4T13rrAHuEmfCt15VJWWwmmyph8H5lUmLLOZIdyU5sYQi8S4NUS1qZqTd9esYjJez0niTfjSEMMlal5GP1znFd0XE=";
    //支付宝公钥
    public final static String alipayALIPAYPUBLICKEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgFOMjlpPEXODOhGZfhRo5QJtTKoBrt2VNZnBROiOG3zIef3vG480lwn1C72PF6ru+ySjcFmu1S/OGHmGDM+7Fw07WsZrhJxr/VR07OFw52MHFUpWgu0xWa5dnYBjb2/cvJtHgQHNXeOL43wjZjfevIg0gkiEj5ssLpNAlYuflZvq78XyQnepxtA1qRaNs07sfE7sd2cfTSkeWPv5v5+vE9wBxEC0Y06z+EyWrGYlsXRd4t8VCf/Ccr7OkWhdeEKbdXaH/Ud19PQb3TnhJLvSmSR9P08q2xrLVaOByr+3v++W7ZBuzhrBbetvJbm5M5dsG8hxeYQ0TTgLK4ypnfRCwQIDAQAB";
    //回调接口
    public final static String alipayNOTIFYURL= PathParam.onlinepath+"/jiaoyou_app/alipayNotify";

}
