package com.qiqi.jiaoyou_app.baiduface;
import com.qiqi.jiaoyou_app.constants.PathParam;
import com.qiqi.jiaoyou_app.util.Base64Utils;
import com.qiqi.jiaoyou_app.vo.Face;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
* 身份验证
*/
public class PersonVerify {

    /**
    * 重要提示代码中所需工具类
    * FileUtil,Base64Util,HttpUtil,GsonUtils请从
    * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
    * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
    * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
    * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
    * 下载
    */
    public static String personVerify(String positivePhotoOfIDCard,String facePhoto) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/match";
        try {
            List<Face> list = new ArrayList<>();
            Face face1 = new Face();
            face1.setImage(Base64Utils.encodeImgageToBase64(PathParam.onlinepath + positivePhotoOfIDCard));
            face1.setImage_type("BASE64");
            face1.setFace_type("LIVE");
            face1.setQuality_control("NORMAL");
            face1.setLiveness_control("NONE");
            Face face2 = new Face();
            face2.setImage(Base64Utils.encodeImgageToBase64(PathParam.onlinepath + facePhoto));
            face2.setImage_type("BASE64");
            face2.setFace_type("LIVE");
            face2.setQuality_control("NORMAL");
            face2.setLiveness_control("NONE");
            list.add(face1);
            list.add(face2);

            String param = GsonUtils.toJson(list);
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "["+AuthService.getAuth()+"]";

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}