package com.tdmh.util;

import com.tdmh.utils.StringUtils;

import java.math.BigDecimal;

/**
 * 2 * @Author: Auser_Qh
 * 3 * @Date: 2019/8/22 17:20
 * 4
 */
public class OrderGasUtil {

    /**
     *检测最大充值气量
     * @param userType 用户类型
     * @param orderGas 本次充值气量
     * @return 1获取数据有误 2 超过商用最大充值气量 3 超过民用最大充值气量
     */
    public static  int MaxOrderGas(String userType, BigDecimal orderGas){
        if (StringUtils.isBlank(userType)) {
            return 1;
        }
        //支持最大充气量
        BigDecimal maxOrderGas = null;
        //商用最大支持9999，民用最大支持充气量999（工商业用户，学校工业用户，工业用户，金湘源）
        if (userType.equals("7") || userType.equals("9") || userType.equals("10") || userType.equals("11")) {
            maxOrderGas = new BigDecimal("9999");
            if (orderGas.compareTo(maxOrderGas) > 0) {
                return 2;
            }
        } else {
            maxOrderGas = new BigDecimal("999");
            if (orderGas.compareTo(maxOrderGas) > 0) {
                return 3;
            }
        }
        return 0;
    }
}
