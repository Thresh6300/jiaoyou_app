package com.qiqi.jiaoyou_app.controller;

// 使用旧版本 base64 编解码实现增强兼容性

import com.qiqi.jiaoyou_app.tls.base64_url.base64_url;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.zip.Deflater;

@Controller
public class TLSSigAPIv2 {
    public static final Long sdkappid = 1400363596L;
    public static final String sdkappidStr = "1400363596";
    public static final String key = "bb506a445428da1f67e48f9cdcd87b6a2badb8359720b05a12563e646f5a383c";
    public static final String adminName = "administrator";


    private static String hmacsha256(String identifier, long currTime, long expire, String base64Userbuf) {
        String contentToBeSigned = "TLS.identifier:" + identifier + "\n"
                + "TLS.sdkappid:" + sdkappid + "\n"
                + "TLS.time:" + currTime + "\n"
                + "TLS.expire:" + expire + "\n";
        if (null != base64Userbuf) {
            contentToBeSigned += "TLS.userbuf:" + base64Userbuf + "\n";
        }
        try {
            byte[] byteKey = key.getBytes("UTF-8");
            Mac hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec keySpec = new SecretKeySpec(byteKey, "HmacSHA256");
            hmac.init(keySpec);
            byte[] byteSig = hmac.doFinal(contentToBeSigned.getBytes("UTF-8"));
            return (new BASE64Encoder().encode(byteSig)).replaceAll("\\s*", "");
        } catch (UnsupportedEncodingException e) {
            return "";
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (InvalidKeyException e) {
            return "";
        }
    }

    /**
     * 生成 tls 票据
     *
     * @param expire  有效期，单位是秒
     * @param userbuf 默认填写null
     * @return 如果出错，会返回为空，或者有异常打印，成功返回有效的票据
     */
    public static String genSig(String identifier,long expire, byte[] userbuf) {
        long currTime = System.currentTimeMillis() / 1000;

        JSONObject sigDoc = new JSONObject();
        sigDoc.put("TLS.ver", "2.0");
        sigDoc.put("TLS.identifier", identifier);
        sigDoc.put("TLS.sdkappid", sdkappid);
        sigDoc.put("TLS.expire", expire);
        sigDoc.put("TLS.time", currTime);

        String base64UserBuf = null;
        if (null != userbuf) {
            base64UserBuf = new BASE64Encoder().encode(userbuf);
            sigDoc.put("TLS.userbuf", base64UserBuf);
        }
        String sig = hmacsha256(identifier, currTime, expire, base64UserBuf);
        if (sig.length() == 0) {
            return "";
        }
        sigDoc.put("TLS.sig", sig);
        Deflater compressor = new Deflater();
        compressor.setInput(sigDoc.toString().getBytes(Charset.forName("UTF-8")));
        compressor.finish();
        byte[] compressedBytes = new byte[2048];
        int compressedBytesLength = compressor.deflate(compressedBytes);
        compressor.end();
        return (new String(base64_url.base64EncodeUrl(Arrays.copyOfRange(compressedBytes,
                0, compressedBytesLength)))).replaceAll("\\s*", "");

    }




}