package com.tdmh.service;

import com.tdmh.common.JsonData;
import com.tdmh.entity.UserCard;
import com.tdmh.param.BindNewCardParam;
import com.tdmh.param.RepairOrderParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 查询补卡记录(维修补气补卡操作)
     * @param userId
     * @return
     */
    List<UserCard> selectUserCardByUserIdService(@Param("userId") Integer userId);
}
