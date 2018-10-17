package com.tdmh.service.impl;

import com.tdmh.common.BeanValidator;
import com.tdmh.common.JsonData;
import com.tdmh.entity.Meter;
import com.tdmh.entity.UserMeters;
import com.tdmh.entity.mapper.RepairOrderMapper;
import com.tdmh.entity.mapper.UserMetersMapper;
import com.tdmh.param.FillGasOrderParam;
import com.tdmh.param.RepairOrderParam;
import com.tdmh.service.IFillGasService;
import com.tdmh.service.IMeterService;
import com.tdmh.service.IRepairOrderService;
import com.tdmh.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author litairan on 2018/10/12.
 */
@Service("iRepairOrderService")
@Transactional(readOnly = true)
public class RepairOrderServiceImpl implements IRepairOrderService {

    @Autowired
    private RepairOrderMapper repairOrderMapper;

    @Autowired
    private UserMetersMapper userMetersMapper;

    @Autowired
    private IUserService userService;

    @Autowired
    private IMeterService meterService;

    @Autowired
    private IFillGasService fillGasService;

    private static BigDecimal MAX_METER_GAS = new BigDecimal(900);

    @Override
    public JsonData listData() {
        List<RepairOrderParam> orderParams = repairOrderMapper.listData();
        return orderParams == null || orderParams.size() == 0 ? JsonData.successMsg("查询结果为空") : JsonData.successData(orderParams);
    }

    @Override
    @Transactional
    public JsonData createRepairOrder(RepairOrderParam param) {
        BeanValidator.check(param);
        String repairOrderId = param.getRepairOrderId();
        if (checkRepairOrderExists(repairOrderId)){
            return JsonData.fail("维修单已存在，不能重复录入");
        }
        String newMeterCode = param.getNewMeterCode();
        if (newMeterCode != null && checkNewMeterInstalled(param.getNewMeterCode())) {
            return JsonData.fail("新表已经被其他用户使用");
        }
        String oldMeterCode = param.getOldMeterCode();
        Integer oldMeterId = meterService.getMeterIdByMeterCode(oldMeterCode);
        Meter oldMeter = meterService.getMeterByMeterId(oldMeterId);
        oldMeter.setMeterStopCode(param.getOldMeterStopCode());
        if (param.getNewMeterCode() == null) {
            oldMeter.setUsable(true);
            meterService.updateMeter(oldMeter);
        } else {
            // 如果是换表则需要更新旧表止码和表具绑定关系
            oldMeter.setMeterScrapTime(new Date());
            oldMeter.setUsable(false);
            meterService.updateMeter(oldMeter);
            // 更新新表止码
            Integer newMeterId = meterService.getMeterIdByMeterCode(newMeterCode);
            Meter newMeter = meterService.getMeterByMeterId(newMeterId);
            newMeter.setMeterStopCode(param.getNewMeterStopCode());
            newMeter.setMeterInstallTime(new Date());
            meterService.updateMeter(newMeter);
            //更新表具绑定关系
            Integer userId = param.getUserId();
            deleteOldMeter(userId);
            installNewMeter(userId, newMeterId, param.getCreateBy());
        }
        int resultCount = repairOrderMapper.addRepairOrder(param);
        if (resultCount == 0) {
            return JsonData.fail("新增维修单失败");
        } else {
            if (checkNeedFillGas(param)) {
                switch (createFillGasOrder(param)) {
                    case 1:
                        return JsonData.successMsg("新增维修单成功,该户需要补气");
                    case -1:
                        return JsonData.successMsg("新增维修单成功,该户需要超用补缴结算");
                    default:
                        return JsonData.successMsg("新增维修单成功");
                }
            } else {
                return JsonData.successMsg("新增维修单成功");
            }
        }
    }

    @Override
    @Transactional
    public JsonData editRepairOrder(RepairOrderParam param) {
        BeanValidator.check(param);
        String newMeterCode = param.getNewMeterCode();
        if (newMeterCode != null && checkNewMeterInstalled(param.getNewMeterCode())) {
            return JsonData.fail("新表已经被其他用户使用");
        }
        recoverOldRepairOrder(param.getId(), param.getUpdateBy());
        //更新旧表信息
        String oldMeterCode = param.getOldMeterCode();
        Integer oldMeterId = meterService.getMeterIdByMeterCode(oldMeterCode);
        Meter oldMeter = new Meter();
        oldMeter.setMeterId(oldMeterId);
        oldMeter.setMeterStopCode(param.getOldMeterStopCode());
        oldMeter.setMeterScrapTime(new Date());
        meterService.updateMeter(oldMeter);
        //更新新表
        if (newMeterCode != null) {
            Integer newMeterId = meterService.getMeterIdByMeterCode(newMeterCode);
            Meter newMeter = meterService.getMeterByMeterId(newMeterId);
            newMeter.setMeterStopCode(param.getNewMeterStopCode());
            newMeter.setMeterInstallTime(new Date());
            meterService.updateMeter(newMeter);
            installNewMeter(param.getUserId(),newMeterId, param.getCreateBy());
        }
        int resultCount = repairOrderMapper.editRepairOrder(param);
        if (resultCount == 0) {
            JsonData.fail("编辑维修单失败");
        }
        if (checkNeedFillGas(param)) {
            createFillGasOrder(param);
            return JsonData.successMsg("编辑维修单成功,该户可能需要补气");
        } else {
            return JsonData.successMsg("编辑维修单成功");
        }
    }

    @Override
    public JsonData searchRepairOrder(String repairOrderId, Integer userId, Integer repairType, Integer empName) {
        List<RepairOrderParam> orderParams = repairOrderMapper.searchRepairOrder(repairOrderId, userId, repairType, empName);
        return orderParams == null || orderParams.size() == 0 ? JsonData.successMsg("查询结果为空") : JsonData.success(orderParams, "查询成功");
    }

    private boolean checkRepairOrderExists(String repairOrderId) {
        return repairOrderMapper.checkRepairOrderExists(repairOrderId);
    }

    /**
     * 校验新表是否被使用
     *
     * @param meterCode
     * @return
     */
    private boolean checkNewMeterInstalled(String meterCode) {
        Integer meterId = meterService.getMeterIdByMeterCode(meterCode);
        Meter meter = meterService.getMeterByMeterId(meterId);
        return meter.getMeterInstallTime() != null;
    }

    /**
     * 将之前新表恢复到未安装状态
     */
    private void recoverOldRepairOrder(Integer id, Integer updateBy) {
        RepairOrderParam repairOrder = repairOrderMapper.getRepairOrderById(id);
        Integer newMeterId = repairOrder.getNewMeterId();
        if (newMeterId != null) {
            Meter newMeter = meterService.getMeterByMeterId(newMeterId);
            meterService.clearInstallInfo(newMeter);
            deleteOldMeter(repairOrder.getUserId());
        }
    }

    private void deleteOldMeter(Integer userId) {
        UserMeters userMeter = userMetersMapper.getUserMeterById(userId);
        List<UserMeters> userMetersList = new ArrayList<>();
        userMetersList.add(userMeter);
        userMetersMapper.deleteInstallMeter(userMetersList);
    }

    private void installNewMeter(Integer userId, Integer newMeterId, Integer createBy) {
        UserMeters newUserMeter = new UserMeters();
        newUserMeter.setMeterId(newMeterId);
        newUserMeter.setUserId(userId);
        newUserMeter.setCreateBy(createBy);
        newUserMeter.setUpdateBy(createBy);
        userMetersMapper.installMeter(newUserMeter);
    }

    private boolean checkNeedFillGas(RepairOrderParam param){
        //维修类型为换表，维修处理结果为换测控版或清零开票时需要补气
        return param.getRepairType().equals(0) || param.getRepairResultType().equals(4) || param.getRepairResultType().equals(9);
    }

    /**
     * 新增维修补气单
     */
    private int createFillGasOrder(RepairOrderParam param) {
        Integer userId = param.getUserId();
        String repairOrderId = param.getRepairOrderId();
        BigDecimal sumOrderGas = fillGasService.getSumOrderGasByUserId(userId);
        BigDecimal sumMeterStopCode = fillGasService.getSumMeterStopCodeByUserId(userId);
        switch (sumOrderGas.compareTo(sumMeterStopCode)) {
            case 1:
                // 补气
                FillGasOrderParam fillGasOrder = new FillGasOrderParam();
                fillGasOrder.setUserId(userId);
                fillGasOrder.setRepairOrderId(repairOrderId);
                BigDecimal needFillGas = sumOrderGas.subtract(sumMeterStopCode);
                //应补气量大于900
                if (needFillGas.compareTo(MAX_METER_GAS) > 0) {
                    fillGasOrder.setNeedFillGas(needFillGas);
                    fillGasOrder.setFillGas(MAX_METER_GAS);
                    fillGasOrder.setLeftGas(needFillGas.subtract(MAX_METER_GAS));
                } else {
                    fillGasOrder.setNeedFillGas(needFillGas);
                    fillGasOrder.setFillGas(needFillGas);
                    fillGasOrder.setLeftGas(BigDecimal.ZERO);
                }
                fillGasOrder.setFillGasOrderStatus(false);
                fillGasService.createFillGasOrder(fillGasOrder);
                return 1;
            case -1:
                // 超用补缴
                return -1;
            default:
                // 不需要补气或超用结算
                return 0;
        }
    }
}
