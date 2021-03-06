package com.tdmh.entity.mapper;

import com.tdmh.entity.MeterType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper @Component
public interface MeterTypeMapper {
    int insert(MeterType record);

    int insertSelective(MeterType record);

    MeterType selectByPrimaryKey(Integer meterTypeId);

    int updateByPrimaryKeySelective(MeterType record);

    int updateByPrimaryKey(MeterType record);

    Integer getMeterTypeId(@Param("meterCategory") String meterCategory, @Param("meterType") String meterType);

    List<MeterType> getAllMeterTypes();

    String getMeterTypeByMeterTypeId(@Param("meterTypeId") Integer meterTypeId);
}