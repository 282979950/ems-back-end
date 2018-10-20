package com.tdmh.service;

import com.tdmh.common.JsonData;
import com.tdmh.param.FillGasOrderParam;

import java.math.BigDecimal;

/**
 * 维修补气服务
 *
 * @author litairan on 2018/10/17.
 */
public interface IFillGasService {

    JsonData listData();

    int createFillGasOrder(FillGasOrderParam param);

    JsonData editFillGasOrder(FillGasOrderParam param);

    JsonData searchFillGasOrder(Integer userId);

    BigDecimal getSumOrderGasByUserId(Integer userId);

    BigDecimal getSumMeterStopCodeByUserId(Integer userId);

    JsonData getFlowNum();

    JsonData getServiceTimesByUserId(Integer userId);

    void setFillGasOrderProps(FillGasOrderParam param, BigDecimal needFillGas);

    void setOveruseOrderProps(FillGasOrderParam param, BigDecimal needFillMoney);
}