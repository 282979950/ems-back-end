package com.tdmh.service;

import com.tdmh.common.JsonData;
import com.tdmh.entity.GasPrice;
import com.tdmh.param.GasPriceParam;

import java.math.BigDecimal;


/**
 * @author Administrator on 2018/10/11.
 */
public interface IGasPriceService {

    JsonData listAllGasPrice();

    GasPrice findGasPriceByType(Integer userType, Integer userGasType);

    JsonData updateGasPrice(GasPriceParam param);

    BigDecimal findHasUsedGasInYear(Integer userId);

    /**
     * 计算气价
     * @param userId
     * @param orderGas
     * @return
     */
    JsonData calAmount(Integer userId, BigDecimal orderGas);
}
