package com.tdmh.service;

import com.tdmh.common.JsonData;
import com.tdmh.entity.GasPrice;
import com.tdmh.param.GasPriceParam;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author Administrator on 2018/10/11.
 */
public interface IGasPriceService {

    JsonData listAllGasPrice();

    GasPrice findGasPriceByType(Integer userType,Integer userGasType);

    JsonData updateGasPrice(GasPriceParam param);

    BigDecimal findHasUsedGasInYear(Integer userId);
}
