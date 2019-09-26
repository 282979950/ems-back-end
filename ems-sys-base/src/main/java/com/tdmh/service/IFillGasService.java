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

    JsonData listData(Integer pageNum, Integer pageSize, Integer currentEmpId, String isAdmin, Integer userType);

    int createFillGasOrder(FillGasOrderParam param);

    JsonData editFillGasOrder(FillGasOrderParam param, String name);

    JsonData searchFillGasOrder(String repairOrderId, Integer userId, Integer fillGasOrderType, Integer pageNum, Integer pageSize, Integer currentEmpId, String isAdmin, Integer userType, String userName);

    BigDecimal getSumOrderGasByUserId(Integer userId);

    BigDecimal getHistoryMeterStopCodeByUserId(Integer userId);

    BigDecimal getSumMeterStopCodeByUserId(Integer userId);

    JsonData getFlowNum();

    JsonData getServiceTimesByUserId(Integer userId);

    void setFillGasOrderProps(FillGasOrderParam param, BigDecimal needFillGas);

    void setOveruseOrderProps(FillGasOrderParam param, BigDecimal needFillGas, BigDecimal needFillMoney);
    JsonData selectHistoryFillGasOrderService(Integer userId);

    boolean hasFillGasOrderResolved(Integer userId, String repairOrderId);

    boolean hasUnfinishedFillGasOrder(Integer userId);

    int cancelFillGasByUserId(Integer userId);
}
