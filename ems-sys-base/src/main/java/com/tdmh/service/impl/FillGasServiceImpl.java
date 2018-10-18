package com.tdmh.service.impl;

import com.tdmh.common.JsonData;
import com.tdmh.entity.mapper.FillGasOrderMapper;
import com.tdmh.param.FillGasOrderParam;
import com.tdmh.service.IFillGasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author litairan on 2018/10/17.
 */
@Service("iFillGasService")
public class FillGasServiceImpl implements IFillGasService {

    @Autowired
    private FillGasOrderMapper fillGasOrderMapper;

    @Override
    public JsonData listData() {
        List<FillGasOrderParam> fillGasOrders = fillGasOrderMapper.listData();
        return fillGasOrders == null || fillGasOrders.size() == 0 ? JsonData.successMsg("查询结果为空") : JsonData.successData(fillGasOrders);
    }

    @Override
    public int createFillGasOrder(FillGasOrderParam param) {
        return fillGasOrderMapper.createFillGasOrder(param);
    }

    @Override
    public JsonData editFillGasOrder(FillGasOrderParam param) {
        return null;
    }

    @Override
    public JsonData searchFillGasOrder(Integer userId) {
        return null;
    }

    @Override
    public BigDecimal getSumOrderGasByUserId(Integer userId) {
        return fillGasOrderMapper.getSumOrderGasByUserId(userId);
    }

    @Override
    public BigDecimal getSumMeterStopCodeByUserId(Integer userId) {
        return fillGasOrderMapper.getSumMeterStopCodeByUserId(userId);
    }
}
