package com.ems.service;

import com.ems.common.JsonData;
import com.ems.entity.Meter;
import com.ems.param.EntryMeterParam;

/**
 * 表具服务类
 *
 * @author litairan on 2018/8/8.
 */
public interface IMeterService {

    /**
     * 表具入库
     *
     * @param param
     * @return
     */
    JsonData entryMeter(EntryMeterParam param);


    /**
     * 获取所有的表具信息
     *
     * @return
     */
    JsonData selectAll();

    /**
     * 获取表具ID
     *
     * @param meterCode
     * @return
     */
    Integer getMeterIdByMeterCode(String meterCode);

    /**
     * 更新表具信息
     *
     * @param meter
     * @return
     */
    int updateMeter(Meter meter);
}
