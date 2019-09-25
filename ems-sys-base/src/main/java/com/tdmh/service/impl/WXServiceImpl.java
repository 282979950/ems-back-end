package com.tdmh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.tdmh.common.JsonData;
import com.tdmh.config.CustomWXPayConfig;
import com.tdmh.entity.UserOrders;
import com.tdmh.entity.mapper.UserMapper;
import com.tdmh.entity.mapper.UserOrdersMapper;
import com.tdmh.entity.mapper.WXMapper;
import com.tdmh.param.ApplyRepairParam;
import com.tdmh.param.WXOrderParam;
import com.tdmh.param.WXUserInfoParam;
import com.tdmh.param.WXUserParam;
import com.tdmh.service.IApplyRepairService;
import com.tdmh.service.IGasPriceService;
import com.tdmh.service.IWXService;
import com.tdmh.utils.HttpRequestUtil;
import com.tdmh.utils.IdWorker;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author litairan on 2018/10/21.
 */
@Service("iWXLoginService")
@Log4j2
public class WXServiceImpl implements IWXService {

    @Autowired
    private WXMapper wxMapper;

    @Autowired
    private IGasPriceService gasPriceService;

    @Autowired
    private UserOrdersMapper userOrdersMapper;

    @Autowired
    private IApplyRepairService applyRepairService;

    @Autowired
    private UserMapper userMapper;

    private WXPay wxPay;

    private WXPayConfig config;

    @PostConstruct
    private void initWXService() {
        config = new CustomWXPayConfig();
        wxPay = new WXPay(config, WXPayConstants.SignType.MD5, false);
    }

    @Override
    public JsonData wxLogin(String code) {
        String params = "appid=" + CustomWXPayConfig.APP_ID + "&secret=" + CustomWXPayConfig.APP_SECRET + "&grant_type=" + CustomWXPayConfig.GRANT_TYPE +
                "&js_code=" + code;
        System.out.println("code:" + code);
        String response = HttpRequestUtil.sendGet(CustomWXPayConfig.WX_JSON2SESSION_URL, params);
        System.out.println("response:" + response);
        JSONObject json = JSONObject.parseObject(response);
        Object errorMsg = json.get("errmsg");
        if (errorMsg == null) {
            String wxUserId = (String) json.get("openid");
            System.out.println(wxUserId);
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
    public JsonData getWXOrders(Integer userId) {
        List<WXOrderParam> wxOrders = userOrdersMapper.getAllWXOrders(userId);
        return wxOrders == null || wxOrders.size() == 0 ? JsonData.successMsg("查询结果为空") : JsonData.successData(wxOrders);
    }

    @Override
    @Transactional
    public JsonData recharge(String wxUserId, Integer userId, BigDecimal gas, String ipAddress) {
        // 查询当前用户的订单列表，如果有未完成的订单则需要支付或取消
        try {
            //限定充值次数.每天限定充值一次，查询当前是否存在：2普通订单，3补卡订单，5微信订单
            int resultOrdersCount = userOrdersMapper.queryCurrentDataByDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), userId);
            if(resultOrdersCount > 0){
                return JsonData.fail("每天只支持充值一次");
            }
//            int resultCount = userMapper.userVerify(userId);
//            if(resultCount>0){
//                return JsonData.fail("只允许民用户在手机充值");
//            }
            Map<String, String> data = new HashMap<>();
            data.put("body", "武汉蓝焰天然气充值-充值气量:" + gas + "方");
            data.put("fee_type", "CNY");
            BigDecimal payment = getOrderPayment(userId, gas);
            System.out.println(payment);
//            data.put("total_fee", String.valueOf(payment.longValue() * 100));
            data.put("total_fee", "1");
            data.put("spbill_create_ip", ipAddress);
            data.put("notify_url", CustomWXPayConfig.NOTIFY_URL);
            data.put("trade_type", CustomWXPayConfig.TRADE_TYPE);
            data.put("openid", wxUserId);
            Integer orderId = createWXOrder(userId, gas, payment);
            data.put("out_trade_no", orderId.toString());
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
                throw new RuntimeException("微信下单失败");
            }
        } catch (Exception e) {
            throw new RuntimeException("微信下单失败");
        }
    }

    @Override
    @Transactional
    public void getOrderNotify(HttpServletRequest request, HttpServletResponse response) {
        String requestString = parseRequest(request);
        BufferedOutputStream output = null;
        try {
            Map<String, String> requestMap = WXPayUtil.xmlToMap(requestString);
            String responseXml = null;
            if (WXPayConstants.SUCCESS.equals(requestMap.get("return_code"))) {
                Integer orderId = Integer.valueOf(requestMap.get("out_trade_no"));
                userOrdersMapper.finishWXOrder(orderId);
                responseXml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml> ";
            } else {
                responseXml = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[报文为空]]></return_msg></xml> ";
            }
            response.setCharacterEncoding("utf-8");
            output = new BufferedOutputStream(response.getOutputStream());
            output.write(responseXml.getBytes());
            output.flush();
        } catch (Exception e) {
            throw new RuntimeException("解析微信支付回调字符串转OBJ错误");
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException ignored) {
            }
        }
    }

    private String parseRequest(HttpServletRequest request) {
        BufferedReader br = null;
        try {
            request.setCharacterEncoding("utf-8");
            String requestBody = "";
            ServletInputStream inputStream = request.getInputStream();
            br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            while (true) {
                String info = br.readLine();
                if (info == null) {
                    break;
                }
                if ("".equals(requestBody)) {
                    requestBody = info;
                } else {
                    requestBody += info;
                }
            }
            return requestBody;
        } catch (IOException e) {
            throw new RuntimeException("解析微信支付回调错误");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private BigDecimal getOrderPayment(Integer userId, BigDecimal gas) {
        JsonData jsonData = gasPriceService.calAmount(userId, gas);
        if (jsonData.getStatus() == 0) {
            return (BigDecimal) jsonData.getData();
        } else {
            throw new RuntimeException("获取充值金额时错误");
        }
    }

    private Integer createWXOrder(Integer userId, BigDecimal gas, BigDecimal payment) {
        UserOrders order = new UserOrders();
        order.setEmployeeId(1000000000); //微信收银员
        order.setUserId(userId);
        order.setOrderGas(gas);
        order.setOrderPayment(payment);
        order.setFlowNumber(String.valueOf(IdWorker.getId().nextId()));
        order.setOrderType(5); //5为微信订单
        Date date = new Date();
        order.setOrderCreateTime(date);
        order.setOrderCloseTime(new Date(date.getTime() + 60 * 60 * 1000));
        order.setUpdateTime(date);
        order.setCreateTime(date);
        order.setCreateBy(1000000000);
        order.setUpdateBy(1000000000);
        order.setOrderStatus(3);
        order.setUsable(true);
        order.setFreeGas(BigDecimal.ZERO);
        int resultCount = userOrdersMapper.insert(order);
        if (resultCount == 0) {
            throw new RuntimeException("生成订单失败");
        }
        return order.getOrderId();
    }

    /**
     * 超时订单定时清理任务30min执行一次
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    @Transactional
    public void scheduled() {
        // 获取所有已超时的未支付微信订单
        log.info("开始清理超时订单");
        List<Integer> orderIds = userOrdersMapper.getAllTimeoutWXOrders();
        if (orderIds != null && orderIds.size() > 0) {
            for (Integer orderId : orderIds) {
                Map<String, String> data = new HashMap<>();
                data.put("out_trade_no", String.valueOf(orderId));
                try {
                    Map<String, String> responseMap = wxPay.closeOrder(data);
                    if (WXPayConstants.SUCCESS.equals(responseMap.get("return_code"))) {
                        log.info("微信订单关闭成功");
                    } else {
                        throw new RuntimeException("微信订单关闭异常");
                    }
                    userOrdersMapper.cancelWxOrder(orderId);
                } catch (Exception e) {
                    throw new RuntimeException("微信订单关闭异常");
                }
            }
        }
        log.info("超时订单清理完毕");
    }

    @Override
    public JsonData getWXApplyRepairByUserId(Integer userId) {
        return applyRepairService.getWXApplyRepairByUserId(userId);
    }

    @Override
    public JsonData createWXApplyRepair(ApplyRepairParam param) {
        return applyRepairService.createWXApplyRepair(param);
    }

    @Override
    public JsonData cancelWXApplyRepair(ApplyRepairParam param) {
        return applyRepairService.cancelWXApplyRepair(param);
    }

    @Override
    public JsonData remindWXApplyRepair(Integer applyRepairId) {
        return applyRepairService.remindWXApplyRepair(applyRepairId);
    }

    @Override
    public JsonData getRepairManTrack(String applyRepairFlowNumber) {
        return applyRepairService.getRepairManTrack(applyRepairFlowNumber);
    }
}
