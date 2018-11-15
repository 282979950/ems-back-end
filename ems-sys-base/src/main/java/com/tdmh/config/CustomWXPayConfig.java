package com.tdmh.config;

import com.github.wxpay.sdk.WXPayConfig;

import java.io.InputStream;

/**
 * @author litairan on 2018/10/23.
 */
public class CustomWXPayConfig implements WXPayConfig {

    /**
     * 小程序ID
     */
    public static final String APP_ID = "wxcb4b102a910ebcab";// TD
//    public static final String APP_ID = "wx3f09eb2829930acb";// LY

    /**
     * 小程序秘钥
     */
    public static final String APP_SECRET = "0daa322b212d11b684d4ad2afffea9b7";//TD
//    public static final String APP_SECRET = "e8c74540c7beef1e30b83500afef4cea";//LY

    /**
     * 商户ID
     */
//    public static final String MCH_ID = "1517235401";//TD
    public static final String MCH_ID = "1431092502";//LY

    /**
     * 商户秘钥
     */
//    public static final String KEY = "3458DF824AFF46C98282F063770DF620";//TD
    public static final String KEY = "2BC3F298B931A74CC8C0C25CE3A39694";//LY

    public static final String GRANT_TYPE = "authorization_code";

    /**
     * 微信登录获取openId
     */
    public static final String WX_JSON2SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session";

    public static final String NOTIFY_URL = "https://www.wuhanlanyan.cn/ems/wx/getOrderNotify";

    public static final String TRADE_TYPE = "JSAPI";

    public CustomWXPayConfig() {
    }

    @Override
    public String getAppID() {
        return APP_ID;
    }

    @Override
    public String getMchID() {
        return MCH_ID;
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}
