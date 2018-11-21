package com.tdmh.config;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPayConfig;
import com.sun.deploy.net.HttpResponse;
import com.tdmh.utils.HttpRequestUtil;
import org.springframework.http.HttpStatus;
import sun.net.www.http.HttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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


    /**
     * TOKEN_URL
     */
    public static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

    /**
     * 模板消息URL
     */
    public static final String MBXX_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send";

    /**
     * 模板消息ID
     */
    public static final String MBXX_ID = "vsHFsT9PVfs0ChZcrguSqKOEDqEApjam2WrCmPcEqCQ";

    /**全局token 所有与微信有交互的前提 */
    public static String ACCESS_TOKEN;

    /**全局token上次获取事件 */
    public static long LASTTOKENTIME;

    /**
     * 获取全局token方法
     * 该方法通过使用HttpClient发送http请求，HttpGet()发送请求
     * 微信返回的json中access_token是我们的全局token
     */
    public static synchronized String getAccess_token(){
        if(ACCESS_TOKEN == null || System.currentTimeMillis() - LASTTOKENTIME > 7000*1000){
            try {
                String responseString = HttpRequestUtil.sendGet(TOKEN_URL,"grant_type=client_credential&appid=" + APP_ID +"&secret="+APP_SECRET);
                JSONObject json = JSONObject.parseObject(responseString);
                //给静态变量赋值，获取到access_token
                ACCESS_TOKEN = (String) json.get("access_token");
                //给获取access_token时间赋值，方便下此次获取时进行判断
                LASTTOKENTIME = System.currentTimeMillis();
            }catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return ACCESS_TOKEN;
    }

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
