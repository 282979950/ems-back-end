package com.tdmh.service;

import com.tdmh.common.JsonData;
import com.tdmh.entity.GasPrice;
import com.tdmh.param.GasPriceParam;

import java.util.List;


/**
 * @author Administrator on 2018/10/11.
 */
public interface IGasPriceService {

    public JsonData listAllGasPrice();

    public GasPrice findGasPriceByType(Integer userType,Integer userGasType);

    public JsonData updateGasPrice(GasPriceParam param);
}
