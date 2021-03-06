package com.tdmh.entity.mapper;

import com.tdmh.param.RepairOrderParam;
import com.tdmh.param.RepairOrderUserParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author litairan on 2018/10/11.
 */
@Mapper
@Component
public interface RepairOrderMapper {

    int addRepairOrder(RepairOrderParam param);

    int editRepairOrder(RepairOrderParam param);

    int cancelRepairOrder(RepairOrderParam param);

    List<RepairOrderParam> searchRepairOrder(@Param("repairOrderId") String repairOrderId, @Param("userId") Integer userId,
                                             @Param("repairType") Integer repairType,
                                             @Param("empName") String empName);

    List<RepairOrderParam> searchQueryRepairOrder(@Param("repairOrderId") String repairOrderId, @Param("userId") Integer userId,
                                             @Param("repairType") Integer repairType,
                                             @Param("empName") String empName,
                                             @Param("createBy") Integer createBy);

    List<RepairOrderParam> listData(@Param("createBy") Integer createBy);

    RepairOrderParam getRepairOrderById(Integer id);

    boolean checkRepairOrderExists(String repairOrderId);

    RepairOrderUserParam getRepairOrderUserById(Integer userId);
    List<RepairOrderParam> selectRepairOrderByuserQuery(Integer userId);

    int lockRepairOrderByUserId(@Param("userId") Integer userId);


    int countPendingRepairOrder(@Param("userId")Integer userId);

    int countProcessingRepairOrder(@Param("userId")Integer userId);

    void updateRepairOrderStatus(@Param("repairOrderId") String repairOrderId, @Param("status") int status);

    boolean isDemolitionTable(@Param("userId")Integer userId);

    int updateDemolitionTableStatus(@Param("userId")Integer userId);
    //查询是否存在拆表工单，排除repairOrderStatus 已撤销的
    int dismantleCount(@Param("userId") Integer userId, @Param("repairOrderStatus") Integer repairOrderStatus, @Param("repairType") Integer repairType, @Param("repairResultType") Integer repairResultType);
}
