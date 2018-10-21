package com.tdmh.service.impl;

import com.tdmh.common.JsonData;
import com.tdmh.entity.UserOrders;
import com.tdmh.entity.mapper.FillGasOrderMapper;
import com.tdmh.entity.mapper.UserMapper;
import com.tdmh.entity.mapper.UserOrdersMapper;
import com.tdmh.param.FillGasOrderParam;
import com.tdmh.service.IFillGasService;
import com.tdmh.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
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

    @Autowired
    private UserOrdersMapper userOrdersMapper;

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
        Integer fillGasOrderType = param.getFillGasOrderType();
        if (fillGasOrderType.equals(1)) {
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
        } else if (fillGasOrderType.equals(2)) {
            fillGasOrderMapper.editFillGasOrder(param);
            // 生成一笔补缴充值
            createOveruseOrder(param.getFillMoney(), param.getStopCodeCount().subtract(param.getGasCount()), param.getUserId(), param.getUpdateBy());
            return JsonData.successMsg("超用补缴单已处理");
        } else if (fillGasOrderType.equals(3)) {
            // TODO: 2018/10/20
            return JsonData.successMsg("销户单已处理");
        } else {
            return JsonData.fail("订单类型不能识别");
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
        newOrder.setCreateBy(old.getUpdateBy());
        newOrder.setUpdateBy(old.getUpdateBy());
        createFillGasOrder(newOrder);
    }

    @Override
    public void setFillGasOrderProps(FillGasOrderParam param, BigDecimal needFillGas) {
        param.setFillGasOrderType(1);
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

    @Override
    public void setOveruseOrderProps(FillGasOrderParam param, BigDecimal needFillGas, BigDecimal needFillMoney) {
        param.setFillGasOrderType(2);
        param.setNeedFillGas(needFillGas);
        param.setFillGas(needFillGas);
        param.setLeftGas(BigDecimal.ZERO);
        param.setNeedFillMoney(needFillMoney);
        param.setFillMoney(needFillMoney);
        param.setLeftMoney(BigDecimal.ZERO);
    }

    private void createOveruseOrder(BigDecimal payment, BigDecimal gas, Integer userId, Integer empId) {
        UserOrders userOrders = new UserOrders();
        userOrders.setUserId(userId);
        userOrders.setEmployeeId(empId);
        userOrders.setUsable(true);
        userOrders.setFlowNumber(String.valueOf(IdWorker.getId().nextId()));
        userOrders.setOrderGas(gas);
        userOrders.setOrderPayment(payment);
        userOrders.setOrderSupplement(payment);
        userOrders.setOrderType(4);
        userOrders.setCreateTime(new Date());
        userOrders.setUpdateTime(new Date());
        userOrders.setOrderStatus(2);
        userOrders.setCreateBy(empId);
        userOrders.setUpdateBy(empId);
        userOrdersMapper.insert(userOrders);
    }
}
