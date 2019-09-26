package com.tdmh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdmh.common.BeanValidator;
import com.tdmh.common.JsonData;
import com.tdmh.entity.UserOrders;
import com.tdmh.entity.mapper.FillGasOrderMapper;
import com.tdmh.entity.mapper.UserMapper;
import com.tdmh.entity.mapper.UserOrdersMapper;
import com.tdmh.param.FillGasOrderParam;
import com.tdmh.service.IFillGasService;
import com.tdmh.service.IRepairOrderService;
import com.tdmh.utils.IdWorker;
import com.tdmh.utils.RmbConvert;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author litairan on 2018/10/17.
 */
@Service("iFillGasService")
public class FillGasServiceImpl implements IFillGasService {

    private static BigDecimal MAX_METER_GAS = new BigDecimal(900);

    @Autowired
    private FillGasOrderMapper fillGasOrderMapper;

    @Autowired
    private IRepairOrderService repairOrderService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserOrdersMapper userOrdersMapper;

    @Override
    public JsonData listData(Integer pageNum, Integer pageSize, Integer currentEmpId, String isAdmin, Integer userType) {
        List<FillGasOrderParam> fillGasOrders = null;
        Integer createBy = null;
        /*
         *判断是否为admin或者用户类型为管理员，不是查询个人产生的记录
         */
        if(StringUtils.isNotBlank(isAdmin) && "admin".equals(isAdmin) ||userType.intValue()== 2){
            PageHelper.startPage(pageNum, pageSize);
            fillGasOrders = fillGasOrderMapper.listData(createBy);
        }else{
            //若不是管理员则查询个人产生的数据
            createBy = currentEmpId;
            PageHelper.startPage(pageNum, pageSize);
            fillGasOrders = fillGasOrderMapper.listData(createBy);
        }
        PageInfo<FillGasOrderParam> info = new PageInfo<>(fillGasOrders);
        return JsonData.successData(info);
    }

    @Override
    public int createFillGasOrder(FillGasOrderParam param) {
        // 清理未完成的维修补气单
        Integer userId = param.getUserId();
        if (hasUnfinishedFillGasOrder(userId)) {
            cancelFillGasByUserId(userId);
        }
        return fillGasOrderMapper.createFillGasOrder(param);
    }

    @Override
    @Transactional
    public JsonData editFillGasOrder(FillGasOrderParam param, String name) {
        BeanValidator.check(param);
        param.setFillGasOrderStatus(1);
        Integer fillGasOrderType = param.getFillGasOrderType();
        if (fillGasOrderType.equals(1)) {
            Map<String, Object> map = new HashMap<>();
            param.setFillMoney(BigDecimal.ZERO);
            param.setNeedFillMoney(BigDecimal.ZERO);
            param.setLeftMoney(BigDecimal.ZERO);
            fillGasOrderMapper.editFillGasOrder(param);
            // 剩余气量不为0时还需要生成一笔订单
            BigDecimal leftGas = param.getLeftGas();
            if (leftGas.compareTo(BigDecimal.ZERO) > 0) {
                createNewFillGasOrder(param);
                repairOrderService.updateRepairOrderStatus(param.getRepairOrderId(), 2);
                map.put("loginName",name);
                return JsonData.success(map,"该补气单已处理，有新补气单生成用于下次补气");
            } else {
                repairOrderService.updateRepairOrderStatus(param.getRepairOrderId(), 4);
                map.put("loginName",name);
                return JsonData.success(map,"补气单处理完成");
            }
        } else if (fillGasOrderType.equals(2)) {
            fillGasOrderMapper.editFillGasOrder(param);
            repairOrderService.updateRepairOrderStatus(param.getRepairOrderId(), 4);
            // 生成一笔补缴充值
            Integer orderId = createOveruseOrder(param.getFillMoney(), param.getStopCodeCount().subtract(param.getGasCount()), param.getUserId(), param.getUpdateBy());
            //return JsonData.successMsg("超用补缴单已处理");
            //方便打印发票
            RmbConvert rmb = new RmbConvert();
            String rmbBig = rmb.simpleToBig(param.getFillMoney().doubleValue());
            Map<String, Object> map = new HashMap<>();
            map.put("data", param);
            map.put("rmbBig", rmbBig);
            map.put("name", name);
            map.put("orderId", orderId);
            return JsonData.success(map,"超用补缴单已处理");
        } else if (fillGasOrderType.equals(3)) {
            // TODO: 2018/10/20
            return JsonData.successMsg("销户单已处理");
        } else {
            return JsonData.fail("订单类型不能识别");
        }
    }

    @Override
    public JsonData searchFillGasOrder(String repairOrderId, Integer userId, Integer fillGasOrderType, Integer pageNum, Integer pageSize, Integer currentEmpId, String isAdmin, Integer userType, String userName) {
        List<FillGasOrderParam> fillGasOrders = null;
        Integer createBy = null;
        /*
         *判断是否为admin或者用户类型为管理员，不是查询个人产生的记录
         */
        if(StringUtils.isNotBlank(isAdmin) && "admin".equals(isAdmin) ||userType.intValue()== 2){
            PageHelper.startPage(pageNum, pageSize);
            fillGasOrders = fillGasOrderMapper.searchFillGasOrder(repairOrderId, userId, fillGasOrderType, createBy, userName);
        }else{
            createBy = currentEmpId;
            PageHelper.startPage(pageNum, pageSize);
            fillGasOrders = fillGasOrderMapper.searchFillGasOrder(repairOrderId, userId, fillGasOrderType, createBy, userName);
        }
        PageInfo<FillGasOrderParam> info = new PageInfo<>(fillGasOrders);
        return JsonData.success(info, "查询成功");
    }

    @Override
    public BigDecimal getSumOrderGasByUserId(Integer userId) {
        return fillGasOrderMapper.getSumOrderGasByUserId(userId);
    }

    @Override
    public BigDecimal getHistoryMeterStopCodeByUserId(Integer userId) {
        BigDecimal historyMeterStopCode = fillGasOrderMapper.getHistoryMeterStopCodeByUserId(userId);
        return historyMeterStopCode == null ? BigDecimal.ZERO : historyMeterStopCode;
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
        newOrder.setGasCount(old.getGasCount());
        newOrder.setStopCodeCount(old.getStopCodeCount());
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

    @Override
    public boolean hasFillGasOrderResolved(Integer userId, String repairOrderId) {
        return fillGasOrderMapper.hasFillGasOrderResolved(userId, repairOrderId);
    }

    @Override
    public JsonData selectHistoryFillGasOrderService(Integer userId) {
        List<FillGasOrderParam> list = fillGasOrderMapper.selectFillGasOrderQuery(userId);
        return  list.size()==0?JsonData.successMsg("未查询到相关数据"):JsonData.success(list,"查询成功!");
    }

    private Integer createOveruseOrder(BigDecimal payment, BigDecimal gas, Integer userId, Integer empId) {
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
        userOrders.setFreeGas(BigDecimal.ZERO);
        userOrders.setCreateBy(empId);
        userOrders.setUpdateBy(empId);
        userOrdersMapper.insert(userOrders);
        return userOrders.getOrderId();
    }

    @Override
    public boolean hasUnfinishedFillGasOrder(Integer userId) {
        return fillGasOrderMapper.hasUnfinishedFillGasOrder(userId);
    }

    @Override
    public int cancelFillGasByUserId(Integer userId) {
        return fillGasOrderMapper.cancelFillGasByUserId(userId);
    }
}
