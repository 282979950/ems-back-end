package com.ems.entity.mapper;

import com.ems.entity.SysDistrict;

import java.util.List;

public interface SysDistrictMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysDistrict record);

    int insertSelective(SysDistrict record);

    SysDistrict selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysDistrict record);

    int updateByPrimaryKey(SysDistrict record);

    int checkDistName(String distName);

    int checkUseable(String distName);

    List<SysDistrict> selectAll();

    int getCountWithUnusable();
}