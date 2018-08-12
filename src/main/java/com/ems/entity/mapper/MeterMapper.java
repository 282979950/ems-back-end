package com.ems.entity.mapper;

import com.ems.entity.Meter;
import com.ems.param.EntryMeterParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MeterMapper {
    int insert(Meter record);

    int insertSelective(Meter record);

    Meter selectByPrimaryKey(Integer meterId);

    int updateByPrimaryKeySelective(Meter record);

    int updateByPrimaryKey(Meter record);

    int entryMeter(EntryMeterParam param);

    boolean checkMeterExist(@Param("meterCode") String meterCode);

    List<Meter> selectAll();

    Integer getMeterIdByMeterCode(@Param("meterCode") String meterCode);
}