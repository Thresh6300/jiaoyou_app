package com.qiqi.jiaoyou_app.pay.util;

import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;

/**
 *
 * @author wangqichang
 * @date 2018/12/24
 */
public class AlipayUtil {
    public static final String GATE_WAY_URL = PropertyUtils.alipayGATEWAYURL;

    public static final String APP_PRIVATE_KEY = PropertyUtils.alipayAPPPRIVATEKEY;

    public static final String APP_ID = PropertyUtils.alipayAPPID;

    public static final String ALIPAY_PUBLIC_KEY = PropertyUtils.alipayALIPAYPUBLICKEY;

    public static final String NOTIFY_URL = PropertyUtils.alipayNOTIFYURL;

    public static final String PRODUCT_CODE = "QUICK_MSECURITY_PAY";

    private static AlipayClient alipayClient = null;
//支付宝sdk客户端类，只需要创建一次即可
    public static synchronized AlipayClient getAlipayClient() {

        if (alipayClient == null) {
            alipayClient = new DefaultAlipayClient(AlipayUtil.GATE_WAY_URL, AlipayUtil.APP_ID, AlipayUtil.APP_PRIVATE_KEY,
                    AlipayConstants.FORMAT_JSON, AlipayConstants.CHARSET_UTF8, AlipayUtil.ALIPAY_PUBLIC_KEY,
                    AlipayConstants.SIGN_TYPE_RSA2);
        }
        return alipayClient;
    }
}

