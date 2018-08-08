package com.ems.service;

import com.ems.common.JsonData;
import com.ems.param.MeterEntryParam;

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
    JsonData entryMeter(MeterEntryParam param);


    /**
     * 获取所有的表具信息
     *
     * @return
     */
    JsonData selectAll();
}
