package com.tdmh.service;

import com.tdmh.common.JsonData;
import com.tdmh.param.RepairOrderParam;

/**
 * 维修单service
 *
 * @author litairan on 2018/10/12.
 */
public interface IRepairOrderService {

    /**
     * 获取所有维修单
     * @return
     */
    JsonData listData();

    /**
     * 新建维修单
     * @param param
     * @return
     */
    JsonData createRepairOrder(RepairOrderParam param);

    /**
     * 编辑维修单
     * @param param
     * @return
     */
    JsonData editRepairOrder(RepairOrderParam param);

    /**
     * 查询维修单
     * @return
     */
    JsonData searchRepairOrder(String repairOrderId, Integer userId, Integer repairType, Integer empName);

    JsonData getRepairOrderUserById(Integer userId);
}
