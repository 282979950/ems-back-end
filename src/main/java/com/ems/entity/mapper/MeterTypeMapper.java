package com.ems.entity.mapper;

import com.ems.entity.MeterType;

public interface MeterTypeMapper {
    int deleteByPrimaryKey(String id);

    int insert(MeterType record);

    int insertSelective(MeterType record);

    MeterType selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MeterType record);

    int updateByPrimaryKey(MeterType record);
}