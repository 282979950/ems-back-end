package com.tdmh.service;

import com.tdmh.common.JsonData;

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
     * @param gas
     * @return
     */
    JsonData getPayment(Integer userId, BigDecimal gas);

    /**
     * 充值
     *
     * @param userId
     * @param gas
     * @param payment
     * @return
     */
    JsonData recharge(Integer userId, BigDecimal gas, BigDecimal payment);
}
