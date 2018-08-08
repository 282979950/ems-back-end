package com.ems.entity.mapper;

import com.ems.entity.MeterType;
import org.apache.ibatis.annotations.Param;

public interface MeterTypeMapper {
    int insert(MeterType record);

    int insertSelective(MeterType record);

    MeterType selectByPrimaryKey(Integer meterTypeId);

    int updateByPrimaryKeySelective(MeterType record);

    int updateByPrimaryKey(MeterType record);

    Integer getMeterTypeId(@Param("meterCategory") String meterCategory, @Param("meterType")String meterType);
}