package com.ems.service.impl;

import com.ems.common.BeanValidator;
import com.ems.common.JsonData;
import com.ems.entity.Meter;
import com.ems.entity.mapper.MeterMapper;
import com.ems.entity.mapper.MeterTypeMapper;
import com.ems.param.MeterEntryParam;
import com.ems.service.IMeterService;
import com.ems.shiro.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 表具服务实现类
 *
 * @author litairan on 2018/8/8.
 */
@Service("iMeterService")
@Transactional(readOnly = true)
public class MeterServiceImpl implements IMeterService {

    @Autowired
    private MeterMapper meterMapper;

    @Autowired
    private MeterTypeMapper meterTypeMapper;

    @Override
    @Transactional
    public JsonData entryMeter(MeterEntryParam param) {
        BeanValidator.check(param);
        String meterCode = param.getMeterCode();
        if (checkMeterExist(meterCode)) {
            return JsonData.fail("表具已经存在,表具编号：" + meterCode);
        }
        BigDecimal stopCode = param.getMeterStopCode();
        if (stopCode == null) {
            param.setMeterStopCode(BigDecimal.ZERO);
        }
        Integer meterTypeId = meterTypeMapper.getMeterTypeId(param.getMeterCategory(), param.getMeterType());
        if (meterTypeId == null || meterTypeId == 0) {
            return JsonData.fail("不存在的表具类型");
        }
        param.setMeterTypeId(meterTypeId);
        Date date = new Date();
        param.setMeterEntryDate(date);
        // TODO: 2018/8/8 从表具编号中获取生产日期，表具编号只能是12位（IC卡表，4-5位为年份，6-7位为月份）和13位（短信表5-6位为年份，7-8位为月份）
        param.setMeterProdDate(date);
        param.setCreateTime(date);
        param.setUpdateTime(date);
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        param.setCreateBy(currentEmpId);
        param.setUpdateBy(currentEmpId);
        int resultCount = meterMapper.entryMeter(param);
        if (resultCount == 0) {
            return JsonData.fail("表具入库失败,表具编号：" + param.getMeterCode());
        }
        return JsonData.successMsg("表具入库成功");
    }

    @Override
    public JsonData selectAll() {
        List<Meter> meters = meterMapper.selectAll();
        if (meters == null || meters.size() == 0) {
            return JsonData.successMsg("未查询到表具信息");
        }
        return JsonData.success(meters, "查询表具成功");
    }

    /**
     * 校验表具是否已经入库
     *
     * @param meterCode
     * @return
     */
    private boolean checkMeterExist(String meterCode) {
        return meterMapper.checkMeterExist(meterCode);
    }
}
