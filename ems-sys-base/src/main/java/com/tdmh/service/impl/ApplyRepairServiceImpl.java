package com.tdmh.service.impl;

import com.tdmh.common.JsonData;
import com.tdmh.entity.mapper.ApplyRepairMapper;
import com.tdmh.param.ApplyRepairParam;
import com.tdmh.service.IApplyRepairService;
import com.tdmh.service.IFillGasService;
import com.tdmh.service.IMeterService;
import com.tdmh.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author litairan on 2018/11/12.
 */
@Service("iApplyRepairService")
@Transactional(readOnly = true)
public class ApplyRepairServiceImpl implements IApplyRepairService {

    @Autowired
    private ApplyRepairMapper applyRepairMapper;

    @Autowired
    private IMeterService meterService;

    @Autowired
    private IFillGasService fillGasService;

    @Override
    public JsonData listData() {
        List<ApplyRepairParam> applyRepairs = applyRepairMapper.selectAll();
        return applyRepairs == null || applyRepairs.size() == 0 ? JsonData.successMsg("查询结果为空"): JsonData.successData(applyRepairs);
    }

    @Override
    @Transactional
    public JsonData create(ApplyRepairParam param) {
        //设置报修单状态为待处理
        param.setApplyRepairStatus(1);
        Integer userId = param.getUserId();
        param.setMeterId(meterService.getMeterIdByUserId(userId));
        BigDecimal historyMeterStopCode = fillGasService.getHistoryMeterStopCodeByUserId(userId);
        BigDecimal gasCount = fillGasService.getSumOrderGasByUserId(userId);
        param.setCurrentOrderGasCount(gasCount.subtract(historyMeterStopCode));
        param.setApplyRepairFlowNumber(String.valueOf(IdWorker.getId().nextId()));
        int resultCount = applyRepairMapper.create(param);
        if (resultCount == 0) {
            return JsonData.fail("新建报修单失败");
        }
        return JsonData.successMsg("新建报修单成功");
    }

    @Override
    @Transactional
    public JsonData update(ApplyRepairParam param) {
        int resultCount = applyRepairMapper.update(param);
        if (resultCount == 0) {
            return JsonData.fail("编辑报修单失败");
        }
        return JsonData.successMsg("编辑报修单成功");
    }

    @Override
    @Transactional
    public JsonData delete(List<Integer> ids, Integer currentEmpId) {
        List<ApplyRepairParam> repairs = new ArrayList<>();
        for (Integer id : ids) {
            ApplyRepairParam repair = applyRepairMapper.getApplyRepairParamById(id);
            if (repair != null) {
                repair.setUpdateBy(currentEmpId);
                repairs.add(repair);
            }
        }
        int resultCount = applyRepairMapper.delete(repairs);
        if (resultCount == ids.size()) {
            return JsonData.successMsg("删除报修单成功");
        } else {
            return JsonData.fail("删除报修单失败");
        }
    }

    @Override
    public JsonData search(Integer userId, String userName, String userPhone, String userTelPhone) {
        List<ApplyRepairParam> applyRepairs = applyRepairMapper.search(userId, userName, userPhone, userTelPhone);
        return applyRepairs == null || applyRepairs.size() == 0 ? JsonData.successMsg("查询结果为空"): JsonData.success(applyRepairs, "查询成功");
    }

    @Override
    public JsonData getApplyRepairUserInfoById(Integer userId) {
        return JsonData.successData(applyRepairMapper.getApplyRepairUserInfoById(userId));
    }

    public ApplyRepairParam getApplyRepairParamById(Integer id) {
        return applyRepairMapper.getApplyRepairParamById(id);
    }
}
