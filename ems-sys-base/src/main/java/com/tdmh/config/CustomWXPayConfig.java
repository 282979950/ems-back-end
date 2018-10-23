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
    private static final String APP_ID = "wx3f09eb2829930acb";

    /**
     * 商户ID
     */
    private static final String MCH_ID = "1431092502";

    /**
     * 商户秘钥
     */
    private static final String KEY = "2BC3F298B931A74CC8C0C25CE3A39694";

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
