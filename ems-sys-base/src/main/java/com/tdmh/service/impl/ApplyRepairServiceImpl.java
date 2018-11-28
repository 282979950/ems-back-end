package com.tdmh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdmh.common.BeanValidator;
import com.tdmh.common.JsonData;
import com.tdmh.config.CustomWXPayConfig;
import com.tdmh.entity.Meter;
import com.tdmh.entity.mapper.ApplyRepairMapper;
import com.tdmh.exception.CustomException;
import com.tdmh.exception.ParameterException;
import com.tdmh.param.ApplyRepairParam;
import com.tdmh.param.ApplyRepairUserInfo;
import com.tdmh.service.IApplyRepairService;
import com.tdmh.service.IFillGasService;
import com.tdmh.service.IMeterService;
import com.tdmh.service.SysDictionaryService;
import com.tdmh.utils.DateUtils;
import com.tdmh.utils.HttpRequestUtil;
import com.tdmh.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ApplyRepairServiceImpl implements IApplyRepairService {

    private static final String LYIMS_STANDARD_URL = "http://192.168.0.142:8080/lyimsstandard/save/workorderSaveByEms";

    private static final String LYIMS_REMIND_URL = "http://192.168.0.142:8080/lyimsstandard/reminder/workorderReminderByEms";

    private static final String LYIMS_REVOKE_URL = "http://192.168.0.142:8080/lyimsstandard/revoke/workorderRevokeByEms";

    @Autowired
    private ApplyRepairMapper applyRepairMapper;

    @Autowired
    private IMeterService meterService;

    @Autowired
    private IFillGasService fillGasService;

    @Autowired
    private SysDictionaryService sysDictionaryService;

    @Override
    public JsonData listData(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ApplyRepairParam> applyRepairs = applyRepairMapper.selectAll();
        PageInfo<ApplyRepairParam> page = new PageInfo<>(applyRepairs);
        return JsonData.successData(page);
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
    public JsonData search(Integer userId, String userName, String userPhone, String userTelPhone, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ApplyRepairParam> applyRepairs = applyRepairMapper.search(userId, userName, userPhone, userTelPhone);
        PageInfo<ApplyRepairParam> page = new PageInfo<>(applyRepairs);
        return applyRepairs.size() == 0 ? JsonData.successMsg("查询结果为空"): JsonData.success(page, "查询成功");
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

    @Override
    @Transactional
    public JsonData cancelWXApplyRepair(ApplyRepairParam param) {
        if (param == null || param.getApplyRepairId() == null) {
            return JsonData.fail("请选择可用的报修单");
        }
        ApplyRepairParam applyRepairParam = getApplyRepairParamById(param.getApplyRepairId());
        if (applyRepairParam == null) {
            return JsonData.fail("找不到可用的报修单");
        }
        if (applyRepairParam.getApplyRepairStatus() == 2) {
            return JsonData.fail("该报修单已签收，无法撤销");
        }
        if (applyRepairParam.getApplyRepairStatus() == 3) {
            return JsonData.fail("该报修单已完成，无法撤销");
        }
        if (applyRepairParam.getApplyRepairStatus() == 4) {
            return JsonData.fail("该报修单已是撤消状态");
        }
        if (applyRepairParam.getApplyRepairStatus() == 5) {
            return JsonData.fail("该报修单正在撤销中，请等待");
        }
        int resultCount = applyRepairMapper.updateRepairStatus(applyRepairParam.getApplyRepairFlowNumber(), 5, param.getFormId(),null,null);
        if (resultCount == 0) {
            return JsonData.fail("撤销报修单失败，请重新撤销");
        }
        log.info("userId=" + applyRepairParam.getUserId() + "&userName=" + applyRepairParam.getUserName() + "&applyRepairFlowNumber=" + applyRepairParam.getApplyRepairFlowNumber());
        String responseString = HttpRequestUtil.sendGet(LYIMS_REVOKE_URL, "userId=" + applyRepairParam.getUserId() + "&userName=" + applyRepairParam.getUserName() + "&applyRepairFlowNumber=" + applyRepairParam.getApplyRepairFlowNumber());
        log.info("撤销报修单流水号: " + applyRepairParam.getApplyRepairFlowNumber() + ",调度系统回复: " + responseString);
        JSONObject json = JSONObject.parseObject(responseString);
        if (json.getBoolean("success")) {
            if (json.getString("msg").equals("撤销中...")) {
                return JsonData.successMsg("正在撤销中");
            } else {
                int resultCount1 = applyRepairMapper.updateRepairStatus(applyRepairParam.getApplyRepairFlowNumber(), 4, null,null,null);
                if (resultCount1 == 0) {
                    return JsonData.fail("撤销报修单失败，请重新撤销");
                }
                return JsonData.successMsg("撤销报修单成功");
            }
        }
        return JsonData.fail("系统异常");
    }

    @Override
    @Transactional
    public JsonData updateApplyRepair(String applyRepairFlowNumber, Integer applyRepairStatus, String signPersonId, String repairmanPhone) {
        if (applyRepairFlowNumber == null) {
            return JsonData.fail("流水号不能为空");
        }
        int resultCount = applyRepairMapper.updateRepairStatus(applyRepairFlowNumber, applyRepairStatus == 11 ? 4 : applyRepairStatus , null, signPersonId, repairmanPhone);
        if (resultCount == 0) {
            log.info("更新订单状态失败，订单流水号:"+applyRepairFlowNumber+",订单状态:"+ applyRepairStatus);
            return JsonData.fail("更新订单状态失败");
        }
        switch (applyRepairStatus) {
            case 2:
                return JsonData.successMsg("该工单已签收，数据传输成功");
            case 3:
                return JsonData.successMsg("该工单已完成，数据传输成功");
            case 11:
                String openid = applyRepairMapper.findOpenidByFlownumber(applyRepairFlowNumber);
                String formid = applyRepairMapper.findFormidByFlownumber(applyRepairFlowNumber);
                JSONObject jo = new JSONObject();
                jo.put("touser", openid);
                jo.put("template_id", CustomWXPayConfig.MBXX_ID);
                jo.put("page", "pages/login/login");
                jo.put("form_id", formid);
                String[] val = {"撤销报修单", applyRepairFlowNumber, "撤销成功", DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss")};
                JSONObject jsonObject = new JSONObject();
                for (int i = 0; i < 4; i++) {
                    JSONObject dataInfo = new JSONObject();
                    dataInfo.put("value", val[i]);
                    dataInfo.put("color", "#ffffff");
                    jsonObject.put("keyword" + (i + 1), dataInfo);
                }
                jo.put("data", jsonObject);
                log.info("token: " + CustomWXPayConfig.getAccess_token() + " , 传参: " + jo.toString());
                String response = HttpRequestUtil.sendTemplateMessage(CustomWXPayConfig.MBXX_URL + "?access_token=" + CustomWXPayConfig.getAccess_token(), jo);
                log.info("token: " + CustomWXPayConfig.getAccess_token());
                log.info("撤销通知微信端回复: " + response);
                JSONObject json = JSONObject.parseObject(response);
                if (json.getInteger("errcode") != 0) {
                    throw new ParameterException("参数错误");
                }
                return JsonData.successMsg("撤销报修单成功");
            default:
                return JsonData.fail("订单状态异常");
        }
    }

    @Override
    @Transactional
    public JsonData remindWXApplyRepair(Integer applyRepairId) {
        ApplyRepairParam applyRepair = getApplyRepairParamById(applyRepairId);
        if (applyRepair == null) {
            return JsonData.fail("订单不存在，请重试");
        }
        String params = "userId=" + applyRepair.getUserId() + "&applyRepairFlowNumber=" + applyRepair.getApplyRepairFlowNumber();
        Integer applyRepairStatus = applyRepair.getApplyRepairStatus();
        switch (applyRepairStatus) {
            case 1:
                return JsonData.fail("订单未签收，不能进行催单操作");
            case 3:
                return JsonData.fail("订单已完成，不能进行催单操作");
            case 4:
                return JsonData.fail("订单已撤销，不能进行催单操作");
            case 5:
                return JsonData.fail("订单撤销中，不能进行催单操作");
        }
        String responseString = HttpRequestUtil.sendGet(LYIMS_REMIND_URL, params);
        JSONObject response = JSONObject.parseObject(responseString);
        if (!response.getBoolean("success")) {
            return JsonData.fail("催单失败");
        }
        int resultCount = applyRepairMapper.setApplyRepairReminded(applyRepair.getApplyRepairId());
        if (resultCount == 0) {
            return JsonData.fail("修改催单状态失败");
        }
        return JsonData.success(false, "催单成功");
    }

    @Override
    public JsonData remindWXApplyRepair(Integer userId, String applyRepairFlowNumber) {
        String params = "userId=" + userId + "&applyRepairFlowNumber=" + applyRepairFlowNumber;
        ApplyRepairParam applyRepair = applyRepairMapper.getApplyRepairParamByFlowNumber(applyRepairFlowNumber);
        if (applyRepair.getApplyRepairStatus().equals(1)) {
            return JsonData.successMsg("订单未签收，不能进行催单操作");
        }
        String responseString = HttpRequestUtil.sendGet(LYIMS_STANDARD_URL, params);
        JSONObject response = JSONObject.parseObject(responseString);
        if (!response.getBoolean("success")) {
            return JsonData.fail("催单失败");
        }
        return JsonData.successMsg("催单成功");
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
        if(applyRepairType == 1){
            ApplyRepairUserInfo userInfo = applyRepairMapper.getApplyRepairUserInfoById(userId);
            param.setUserName(userInfo.getUserName());
            param.setDistName(userInfo.getDistName());
            param.setDistCode(userInfo.getDistCode());
            param.setUserAddress(userInfo.getUserAddress());
            param.setUserPhone(userInfo.getUserPhone());
        }
        int resultCount = applyRepairMapper.create(param);
        if (resultCount == 0) {
            return JsonData.fail("新建报修单失败");
        }
        String responseString = HttpRequestUtil.sendGet(LYIMS_STANDARD_URL, param.toString());
        JSONObject json = JSONObject.parseObject(responseString);
        if (!json.getBoolean("success")) {
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
