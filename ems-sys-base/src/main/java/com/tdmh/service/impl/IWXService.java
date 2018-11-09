package com.tdmh.service.impl;

import com.tdmh.common.JsonData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * 微信服务类
 *
 * @author litairan on 2018/10/21.
 */
public interface IWXService {

    /**
     * 微信登录
     *
     * @param code
     * @return
     */
    JsonData wxLogin(String code);

    /**
     * 依据微信Id获取绑定的户号
     *
     * @param userId
     * @return
     */
    JsonData getUsersByWXUserId(String userId);

    /**
     * 依据用户Id获取用户信息
     *
     * @param userId
     * @param userName
     * @return
     */
    JsonData checkUserExists(Integer userId, String userName);

    /**
     * 依据用户Id获取用户信息
     *
     * @param userId
     * @return
     */
    JsonData getUserInfo(Integer userId);

    /**
     * 绑定户号
     *
     * @param wxUserId
     * @param userId
     * @return
     */
    JsonData bindUser(String wxUserId, Integer userId);

    /**
     * 解绑户号
     *
     * @param wxUserId
     * @param userId
     * @return
     */
    JsonData unBindUser(String wxUserId, Integer userId);

    /**
     * 获取充值金额
     *
     * @param userId
     * @return
     */
    JsonData getWXOrders(Integer userId);

    /**
     * 充值
     *
     * @param wxUserId
     * @param userId
     * @param gas
     * @return
     */
    JsonData recharge(String wxUserId, Integer userId, BigDecimal gas, String ipAddress);

    /**
     * 处理微信支付回调
     *
     * @param request
     * @param response
     */
    void getOrderNotify(HttpServletRequest request, HttpServletResponse response);
}
