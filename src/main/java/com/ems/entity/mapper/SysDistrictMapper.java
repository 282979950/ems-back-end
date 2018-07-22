package com.ems.entity.mapper;

import com.ems.entity.SysDistrict;

import java.util.List;

public interface SysDistrictMapper {
    int deleteByPrimaryKey(Integer distId);

    int insert(SysDistrict record);

    int insertSelective(SysDistrict record);

    SysDistrict selectByPrimaryKey(Integer distId);

    int updateByPrimaryKeySelective(SysDistrict record);

    int updateByPrimaryKey(SysDistrict record);

    int checkDistName(String distName);

    int checkUseable(String distName);

    List<SysDistrict> selectAll();

    int getCountWithUnusable();
}