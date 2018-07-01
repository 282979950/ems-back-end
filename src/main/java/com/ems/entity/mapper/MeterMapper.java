package com.ems.entity.mapper;

import com.ems.entity.Meter;

public interface MeterMapper {
    int deleteByPrimaryKey(String id);

    int insert(Meter record);

    int insertSelective(Meter record);

    Meter selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Meter record);

    int updateByPrimaryKey(Meter record);
}