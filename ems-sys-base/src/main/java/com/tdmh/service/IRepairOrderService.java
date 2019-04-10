package com.tdmh.service;

import com.tdmh.common.JsonData;
import com.tdmh.param.BindNewCardParam;
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
    JsonData listData(Integer pageNum, Integer pageSize);

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
    JsonData searchRepairOrder(String repairOrderId, Integer userId, Integer repairType, String empName, Integer pageNum, Integer pageSize);

    JsonData getRepairOrderUserById(Integer userId);

    JsonData hasFillGasOrderResolved(Integer userId, String repairOrderId);

    JsonData isLatestFillGasOrder(Integer id, Integer userId);

    JsonData getBindNewCardParamByUserId(Integer userId);

    JsonData bindNewCard(BindNewCardParam param);
    JsonData selectHistoryRepairOrderQueryService(Integer userId);
}
