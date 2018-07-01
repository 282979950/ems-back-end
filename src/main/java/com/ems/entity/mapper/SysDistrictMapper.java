package com.ems.entity.mapper;

import com.ems.entity.SysDistrict;

public interface SysDistrictMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysDistrict record);

    int insertSelective(SysDistrict record);

    SysDistrict selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysDistrict record);

    int updateByPrimaryKey(SysDistrict record);
}