package com.tdmh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tdmh.common.JsonData;
import com.tdmh.service.IWXLoginService;
import com.tdmh.utils.HttpRequestUtil;
import com.tdmh.utils.RandomUtils;
import org.springframework.stereotype.Service;

/**
 * @author litairan on 2018/10/21.
 */
@Service("iWXLoginService")
public class WXLoginServiceImpl implements IWXLoginService {

    private static final String WX_APP_ID = "wx3f09eb2829930acb";

    private static final String WX_APP_SECRET = "e8c74540c7beef1e30b83500afef4cea";

    private static final String GRANT_TYPE = "authorization_code";

    private static final String WX_JSON2SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session";

    @Override
    public JsonData wxLogin(String code) {
        String params = "appid=" + WX_APP_ID + "&secret=" + WX_APP_SECRET + "&grant_type=" + GRANT_TYPE + "&js_code=" + code;
        String response = HttpRequestUtil.sendGet(WX_JSON2SESSION_URL, params);
        JSONObject json = JSONObject.parseObject(response);
        Object errorMsg = json.get("errmsg");
        if (errorMsg == null) {
            String openId = (String) json.get("openid");
            // 依据openId查找对应的绑定用户的记录
            System.out.println(openId);
            String sessionKey = (String) json.get("session_key");
            String session = RandomUtils.getUUID();
            return JsonData.successData(session);
        } else {
            return JsonData.fail("登录失败");
        }
    }
}
