package com.qiqi.jiaoyou_app.express;

import cn.hutool.setting.dialect.Props;

public interface CloudPro
{
    Props PROPS = new Props("application.properties");
    // 用户授权key
    String KUAIDI_SECRET_KEY = PROPS.getStr("kuaidi.secret_key");
    String KUAIDI_SECRET_SECRET = PROPS.getStr("kuaidi.secret_secret");
    // 接口编号
    String KUAIDI_SECRET_CODE = PROPS.getStr("kuaidi.secret_code");
    // 加密签名：md5(secret_key+secret_secret)转大写
    String KUAIDI_SECRET_SIGN = PROPS.getStr("kuaidi.secret_sign");
}
