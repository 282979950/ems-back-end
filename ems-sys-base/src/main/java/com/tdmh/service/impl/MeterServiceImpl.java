package com.tdmh.service.impl;

import com.tdmh.common.BeanValidator;
import com.tdmh.common.JsonData;
import com.tdmh.entity.Meter;
import com.tdmh.entity.MeterType;
import com.tdmh.entity.mapper.MeterMapper;
import com.tdmh.entity.mapper.MeterTypeMapper;
import com.tdmh.param.EntryMeterParam;
import com.tdmh.service.IMeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    public JsonData getAllEntryMeters() {
        List<EntryMeterParam> meters = meterMapper.getAllEntryMeters();
        if (meters == null || meters.size() == 0) {
            return JsonData.successMsg("未查询到表具信息");
        }
        return JsonData.success(meters, "查询表具成功");
    }

    @Override
    public JsonData getAllMeterTypes() {
        List<MeterType> meterTypes = meterTypeMapper.getAllMeterTypes();
        if (meterTypes == null || meterTypes.size() == 0) {
            return JsonData.successMsg("未查询到表具信息");
        }
        return JsonData.success(meterTypes, "查询表具成功");
    }

    @Override
    @Transactional
    public JsonData addEntryMeter(EntryMeterParam param) {
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
        Date date = new Date();
        param.setMeterEntryDate(date);
        param.setMeterProdDate(date);
        param.setCreateTime(date);
        param.setUpdateTime(date);
        int resultCount = meterMapper.addEntryMeter(param);
        if (resultCount == 0) {
            return JsonData.fail("表具入库失败,表具编号：" + param.getMeterCode());
        }
        return JsonData.successMsg("表具入库成功");
    }

    @Override
    @Transactional
    public JsonData editEntryMeter(EntryMeterParam param) {
        BeanValidator.check(param);
        // TODO: 2018/8/24 修改表具编号时提示用户
        int resultCount = meterMapper.editEntryMeter(param);
        if (resultCount == 0) {
            return JsonData.fail("更新失败");
        }
        return JsonData.successMsg("更新成功");
    }

    @Override
    @Transactional
    public JsonData deleteEntryMeter(List<Integer> ids,Integer currentEmpId) {
        if (ids == null || ids.size() ==0) {
            return JsonData.successMsg("请选中要删除的数据");
        }
        List<Meter> meters = new ArrayList<>();
        for (Integer id : ids) {
            Meter meter = meterMapper.getMeterByMeterId(id);
            meter.setUpdateBy(currentEmpId);
            meter.setUsable(false);
            meters.add(meter);
        }
        int resultCount = meterMapper.deleteEntryMeter(meters);
        if (resultCount == 0) {
            return JsonData.fail("删除失败");
        }
        return JsonData.successMsg("删除成功");
    }

    @Override
    public JsonData searchEntryMeter(String meterCode, String meterCategory, String meterType, Boolean meterDirection, Date meterProdDate) {
        List<EntryMeterParam> meters = meterMapper.searchEntryMeter(meterCode, meterCategory, meterType, meterDirection, meterProdDate);
        return meters == null || meters.size() == 0 ? JsonData.successMsg("搜索结果为空") : JsonData.success(meters, "查询成功");
    }

    @Override
    public JsonData selectAll() {
        List<Meter> meters = meterMapper.selectAll();
        if (meters == null || meters.size() == 0) {
            return JsonData.successMsg("未查询到表具信息");
        }
        return JsonData.success(meters, "查询表具成功");
    }

    @Override
    public Meter getMeterByMeterId(Integer meterId) {
        return meterMapper.getMeterByMeterId(meterId);
    }

    @Override
    public Integer getMeterIdByUserId(Integer userId) {
        return meterMapper.getMeterIdByUserId(userId);
    }

    @Override
    public Integer getMeterIdByMeterCode(String meterCode) {
        return meterMapper.getMeterIdByMeterCode(meterCode);
    }

    @Override
    public JsonData getMeterByMeterCode(String meterCode) {
        return JsonData.successData(getMeterByMeterId(getMeterIdByMeterCode(meterCode)));
    }

    @Override
    public int updateMeter(Meter meter) {
        if (meter.getUpdateTime() == null) {
            meter.setUpdateTime(new Date());
        }
        return meterMapper.updateByPrimaryKeySelective(meter);
    }

    @Override
    public int clearInstallInfo(Meter meter) {
        return meterMapper.clearInstallInfo(meter);
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
