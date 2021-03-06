package com.qiqi.jiaoyou_app.util;

import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

public class Base64Utils {

    public static String encodeImgageToBase64(String remark) {

        ByteArrayOutputStream outputStream = null;

        try {

            URL url = new URL(remark);

            BufferedImage bufferedImage = ImageIO.read(url);

            outputStream = new ByteArrayOutputStream();

            ImageIO.write(bufferedImage,"jpg",outputStream);

        } catch (IOException e) {

            return remark;

        }

        BASE64Encoder encoder = new BASE64Encoder();

        String s= encoder.encode(outputStream.toByteArray());

        return s;

    }
}
