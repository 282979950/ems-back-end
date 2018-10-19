package com.tdmh.service.impl;

import com.tdmh.common.JsonData;
import com.tdmh.entity.mapper.FillGasOrderMapper;
import com.tdmh.entity.mapper.UserMapper;
import com.tdmh.param.FillGasOrderParam;
import com.tdmh.service.IFillGasService;
import com.tdmh.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author litairan on 2018/10/17.
 */
@Service("iFillGasService")
public class FillGasServiceImpl implements IFillGasService {

    private static BigDecimal MAX_METER_GAS = new BigDecimal(900);

    @Autowired
    private FillGasOrderMapper fillGasOrderMapper;

    @Autowired
    private UserMapper userMapper;
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
    @Transactional
    public JsonData editFillGasOrder(FillGasOrderParam param) {
        param.setFillGasOrderStatus(1);
        fillGasOrderMapper.editFillGasOrder(param);
        // 当不是第一笔补气单时不需要更新维修次数
        if (param.getGasCount().subtract(param.getStopCodeCount()).compareTo(param.getFillGas()) == 0) {
            userMapper.updateServiceTimesByUserId(param.getUserId());
        }
        // 剩余气量不为0时还需要生成一笔订单
        BigDecimal leftGas = param.getLeftGas();
        if (leftGas.compareTo(BigDecimal.ZERO) > 0) {
            createNewFillGasOrder(param);
            return JsonData.successMsg("该补气单已处理，有新补气单生成用于下次补气");
        } else {
            return JsonData.successMsg("补气单处理完成");
        }
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

    @Override
    public JsonData getFlowNum() {
        return JsonData.successData(String.valueOf(IdWorker.getId().nextId()));
    }

    @Override
    public JsonData getServiceTimesByUserId(Integer userId) {
        return JsonData.successData(userMapper.getServiceTimesByUserId(userId));
    }

    private void createNewFillGasOrder(FillGasOrderParam old) {
        FillGasOrderParam newOrder = new FillGasOrderParam();
        newOrder.setUserId(old.getUserId());
        newOrder.setRepairOrderId(old.getRepairOrderId());
        BigDecimal needFillGas = old.getLeftGas();
        setFillGasOrderProps(newOrder, needFillGas);
        newOrder.setFillGasOrderStatus(0);
        createFillGasOrder(newOrder);
    }

    @Override
    public void setFillGasOrderProps(FillGasOrderParam param, BigDecimal needFillGas) {
        //应补气量大于900
        if (needFillGas.compareTo(MAX_METER_GAS) > 0) {
            param.setNeedFillGas(needFillGas);
            param.setFillGas(MAX_METER_GAS);
            param.setLeftGas(needFillGas.subtract(MAX_METER_GAS));
            param.setNeedFillMoney(BigDecimal.ZERO);
            param.setFillMoney(BigDecimal.ZERO);
            param.setLeftMoney(BigDecimal.ZERO);
        } else {
            param.setNeedFillGas(needFillGas);
            param.setFillGas(needFillGas);
            param.setLeftGas(BigDecimal.ZERO);
            param.setNeedFillMoney(BigDecimal.ZERO);
            param.setFillMoney(BigDecimal.ZERO);
            param.setLeftMoney(BigDecimal.ZERO);
        }
    }
}
