package com.tdmh.service.impl;


import com.tdmh.common.JsonData;
import com.tdmh.entity.Meter;
import com.tdmh.param.EntryMeterParam;

import java.util.Date;
import java.util.List;

/**
 * 表具服务类
 *
 * @author litairan on 2018/8/8.
 */
public interface IMeterService {

    /**
     * 获取所有入库表具
     *
     * @return
     */
    JsonData getAllEntryMeters();

    /**
     * 获取所有表具类型
     *
     * @return
     */
    JsonData getAllMeterTypes();

    /**
     * 获取所有的表具信息
     *
     * @return
     */
    JsonData selectAll();

    /**
     * 获取表具信息
     * @param meterId
     */
    Meter getMeterByMeterId(Integer meterId);
    /**
     * 获取表具ID
     *
     * @param meterCode
     * @return
     */
    Integer getMeterIdByMeterCode(String meterCode);

    /**
     * 依据表具编号获取表具
     * @param meterCode
     * @return
     */
    JsonData getMeterByMeterCode(String meterCode);

    /**
     * 更新表具信息
     *
     * @param meter
     * @return
     */
    int updateMeter(Meter meter);

    /**
     * 清理安装信息
     *
     * @param meter
     */
    int clearInstallInfo(Meter meter);

    /**
     * 表具入库
     *
     * @param param
     * @return
     */
    JsonData addEntryMeter(EntryMeterParam param);

    /**
     * 编辑入库信息
     *
     * @param param
     * @return
     */
    JsonData editEntryMeter(EntryMeterParam param);

    /**
     * 删除入库信息
     *
     * @param ids
     * @return
     */
    JsonData deleteEntryMeter(List<Integer> ids, Integer currentEmpId);

    /**
     * 查询入库信息
     *
     * @param meterCode
     * @param meterCategory
     * @param meterType
     * @param meterDirection
     * @param meterProdDate
     * @return
     */
    JsonData searchEntryMeter(String meterCode, String meterCategory, String meterType, Boolean meterDirection, Date meterProdDate);
}
