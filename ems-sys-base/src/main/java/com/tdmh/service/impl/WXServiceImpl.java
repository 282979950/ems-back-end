package com.tdmh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tdmh.common.JsonData;
import com.tdmh.entity.mapper.WXMapper;
import com.tdmh.param.WXUserInfoParam;
import com.tdmh.param.WXUserParam;
import com.tdmh.service.IWXService;
import com.tdmh.utils.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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
            return JsonData.successMsg("微信号与该户号已绑定");
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
    public JsonData recharge(Integer userId, BigDecimal gas, BigDecimal payment) {
        return null;
    }
}
