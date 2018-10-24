package com.tdmh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.tdmh.common.JsonData;
import com.tdmh.config.CustomWXPayConfig;
import com.tdmh.entity.mapper.WXMapper;
import com.tdmh.param.WXUserInfoParam;
import com.tdmh.param.WXUserParam;
import com.tdmh.service.IWXService;
import com.tdmh.utils.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author litairan on 2018/10/21.
 */
@Service("iWXLoginService")
public class WXServiceImpl implements IWXService {

    private static final String WX_APP_ID = "wx3f09eb2829930acb";

    private static final String WX_APP_SECRET = "e8c74540c7beef1e30b83500afef4cea";

    private static final String GRANT_TYPE = "authorization_code";

    private static final String WX_JSON2SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session";

    private static final String WX_RECHARGE_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    private static final String MCH_ID = "1431092502";

    @Autowired
    private WXMapper wxMapper;

    @Override
    public JsonData wxLogin(String code) {
        String params = "appid=" + WX_APP_ID + "&secret=" + WX_APP_SECRET + "&grant_type=" + GRANT_TYPE + "&js_code=" + code;
        String response = HttpRequestUtil.sendGet(WX_JSON2SESSION_URL, params);
        JSONObject json = JSONObject.parseObject(response);
        Object errorMsg = json.get("errmsg");
        if (errorMsg == null) {
            String wxUserId = (String) json.get("openid");
            return JsonData.successData(wxUserId);
        } else {
            return JsonData.fail("登录失败");
        }
    }

    @Override
    public JsonData getUsersByWXUserId(String wxUserId) {
        List<WXUserParam> bindUsers = wxMapper.getBindUsersByWXUserId(wxUserId);
        return JsonData.successData(bindUsers);
    }

    @Override
    public JsonData checkUserExists(Integer userId, String userName) {
        int resultCount = wxMapper.checkUserExists(userId, userName);
        if (resultCount == 0) {
            return JsonData.fail("用户名或户号错误，请重试");
        }
        return JsonData.successMsg("查询成功");
    }

    @Override
    public JsonData getUserInfo(Integer userId) {
        WXUserInfoParam userInfo = wxMapper.getUserInfo(userId);
        return userInfo == null ? JsonData.fail("查询用户信息失败") : JsonData.success(userInfo, "查询用户信息成功");
    }

    @Override
    public JsonData bindUser(String wxUserId, Integer userId) {
        if (checkBindExists(wxUserId, userId)) {
            return JsonData.fail("微信号与该户号已绑定");
        }
        int resultCount = wxMapper.bindUser(wxUserId, userId);
        if (resultCount == 0) {
            return JsonData.fail("绑定失败请重试！");
        }
        return JsonData.successMsg("绑定成功");
    }

    private boolean checkBindExists(String wxUserId, Integer userId) {
        return wxMapper.checkBindExists(wxUserId, userId);
    }

    @Override
    public JsonData unBindUser(String wxUserId, Integer userId) {
        int resultCount = wxMapper.unBindUser(wxUserId, userId);
        if (resultCount == 0) {
            return JsonData.fail("解绑失败请重试！");
        }
        return JsonData.successMsg("解绑成功");
    }

    @Override
    public JsonData getPayment(Integer userId, BigDecimal gas) {
        return null;
    }

    @Override
    public JsonData recharge(String wxUserId, Integer userId, BigDecimal gas, String ipAddress) {
        WXPayConfig config;
        try {
            config = new CustomWXPayConfig();
        } catch (Exception e) {
            throw new RuntimeException("获取配置信息错误");
        }
        WXPay wxPay = new WXPay(config, WXPayConstants.SignType.MD5, false);
        try {
            Map<String, String> data = new HashMap<>();
            data.put("body", "武汉蓝焰天然气充值-充值气量:" + gas + "方");
            String tradeNo = "100000000001";

            data.put("out_trade_no", tradeNo);
            data.put("fee_type", "CNY");
            String totalFee = "1";
            data.put("total_fee", totalFee);
            data.put("spbill_create_ip", ipAddress);
            data.put("notify_url", "http://39.105.6.33:8081/wx/notify");
            data.put("trade_type", "JSAPI");
            data.put("openid", wxUserId);
            Map<String, String> response = wxPay.unifiedOrder(data);
            String return_code = response.get("return_code");
            if (WXPayConstants.SUCCESS.equals(return_code)) {
                Map<String, String> responseData = new HashMap<>();
                responseData.put("appId", config.getAppID());
                responseData.put("timeStamp", String.valueOf(System.currentTimeMillis()));
                responseData.put("nonceStr", WXPayUtil.generateNonceStr());
                String prepay_id = response.get("prepay_id");
                responseData.put("package", "prepay_id=" + prepay_id);
                responseData.put("signType", WXPayConstants.MD5);
                String paySign = WXPayUtil.generateSignature(responseData, config.getKey());
                responseData.put("paySign", paySign);
                return JsonData.success(responseData, "微信下单成功");
            } else {
                return JsonData.fail("微信下单失败");
            }
        } catch (Exception e) {
            throw new RuntimeException("微信下单失败");
        }
    }

    public static void main(String[] args) {
        System.out.println();
        System.out.println(new Date().getTime());
    }
}
