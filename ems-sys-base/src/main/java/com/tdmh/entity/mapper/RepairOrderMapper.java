package com.tdmh.entity.mapper;

import com.tdmh.param.RepairOrderParam;
import org.apache.ibatis.annotations.Mapper;
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

    List<RepairOrderParam> listData();

    RepairOrderParam getRepairOrderById(Integer id);

    boolean checkRepairOrderExists(String repairOrderId);
}
