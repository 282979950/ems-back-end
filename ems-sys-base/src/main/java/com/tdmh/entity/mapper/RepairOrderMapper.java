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

    List<RepairOrderParam> searchRepairOrder(@Param("repairOrderId")String repairOrderId, @Param("userId")Integer userId, @Param("repairType")Integer repairType,
                                             @Param("empName") String empName);

    List<RepairOrderParam> listData();

    RepairOrderParam getRepairOrderById(Integer id);

    boolean checkRepairOrderExists(String repairOrderId);

    RepairOrderUserParam getRepairOrderUserById(Integer userId);
}
