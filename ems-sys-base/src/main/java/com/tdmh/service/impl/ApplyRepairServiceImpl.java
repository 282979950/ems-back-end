package com.tdmh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tdmh.common.BeanValidator;
import com.tdmh.common.JsonData;
import com.tdmh.entity.Meter;
import com.tdmh.entity.mapper.ApplyRepairMapper;
import com.tdmh.exception.CustomException;
import com.tdmh.param.ApplyRepairParam;
import com.tdmh.service.IApplyRepairService;
import com.tdmh.service.IFillGasService;
import com.tdmh.service.IMeterService;
import com.tdmh.service.SysDictionaryService;
import com.tdmh.utils.HttpRequestUtil;
import com.tdmh.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author litairan on 2018/11/12.
 */
@Service("iApplyRepairService")
@Transactional(readOnly = true)
public class ApplyRepairServiceImpl implements IApplyRepairService {

    private static final String LYIMS_STANDARD_URL = "http://192.168.0.117:8080/lyimsstandard/save/workorderSaveByEms";

    @Autowired
    private ApplyRepairMapper applyRepairMapper;

    @Autowired
    private IMeterService meterService;

    @Autowired
    private IFillGasService fillGasService;

    @Autowired
    private SysDictionaryService sysDictionaryService;

    @Override
    public JsonData listData() {
        List<ApplyRepairParam> applyRepairs = applyRepairMapper.selectAll();
        return applyRepairs == null || applyRepairs.size() == 0 ? JsonData.successMsg("查询结果为空"): JsonData.successData(applyRepairs);
    }

    @Override
    @Transactional
    public JsonData create(ApplyRepairParam param) {
        // 设置报修类型为电话报修
        return create0(param, 2);
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

    @Override
    public JsonData getWXApplyRepairByUserId(Integer userId) {
        List<ApplyRepairParam> applyRepairs = applyRepairMapper.getWXApplyRepairByUserId(userId);
        return applyRepairs == null || applyRepairs.size() == 0 ? JsonData.successMsg("查询结果为空") : JsonData.success(applyRepairs, "查询成功");
    }

    @Override
    @Transactional
    public JsonData createWXApplyRepair(ApplyRepairParam param) {
        // 设置报修类型为微信报修
        return create0(param, 1);
    }

    private JsonData create0(ApplyRepairParam param, Integer applyRepairType) {
        BeanValidator.check(param);
        Integer userId = param.getUserId();
        if (!checkUserIdExists(userId)) {
            return JsonData.fail("户号不存在，请确认");
        }
        if (hasUnsolvedApplyRepair(userId)) {
            return JsonData.fail("该户有未完成的报修单，不能发起新的报修");
        }
        //设置报修类型为微信报修
        param.setApplyRepairType(applyRepairType);
        param.setApplyRepairTypeName(applyRepairType == 1 ? "微信报修" : "电话报修");
        //设置报修单状态为待处理
        param.setApplyRepairStatus(1);
        param.setApplyRepairStatusName("待处理");
        Integer meterId = meterService.getMeterIdByUserId(userId);
        param.setMeterId(meterId);
        Meter meter = meterService.getMeterByMeterId(meterId);
        param.setMeterCode(meter.getMeterCode());
        Integer meterTypeId = meter.getMeterTypeId();
        param.setMeterTypeId(meterTypeId);
        param.setMeterType(meterService.getMeterTypeByMeterTypeId(meterTypeId));
        Boolean meterDirection = meter.getMeterDirection();
        param.setMeterDirection(meterDirection);
        param.setMeterDirectionName(meterDirection ? "左" : "右");
        BigDecimal historyMeterStopCode = fillGasService.getHistoryMeterStopCodeByUserId(userId);
        BigDecimal gasCount = fillGasService.getSumOrderGasByUserId(userId);
        param.setCurrentOrderGasCount(gasCount.subtract(historyMeterStopCode));
        param.setApplyRepairFlowNumber(String.valueOf(IdWorker.getId().nextId()));
        Date date = new Date();
        param.setApplyRepairTime(date);
        int resultCount = applyRepairMapper.create(param);
        if (resultCount == 0) {
            return JsonData.fail("新建报修单失败");
        }
        String responseString = HttpRequestUtil.sendGet(LYIMS_STANDARD_URL, param.toString());
        JSONObject json = JSONObject.parseObject(responseString);
        if (!json.getBoolean("success")){
            throw new CustomException("向调度系统传输报修单时失败，请联系管理员");
        }
        return JsonData.successMsg("新建报修单成功");
    }

    public ApplyRepairParam getApplyRepairParamById(Integer id) {
        return applyRepairMapper.getApplyRepairParamById(id);
    }

    private boolean hasUnsolvedApplyRepair(Integer userId) {
        return applyRepairMapper.hasUnsolvedApplyRepair(userId);
    }

    private boolean checkUserIdExists(Integer userId) {
        return applyRepairMapper.checkUserIdExists(userId);
    }
}
