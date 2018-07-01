package com.ems.entity.mapper;

import com.ems.entity.EmployeeLocation;

public interface EmployeeLocationMapper {
    int deleteByPrimaryKey(String id);

    int insert(EmployeeLocation record);

    int insertSelective(EmployeeLocation record);

    EmployeeLocation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EmployeeLocation record);

    int updateByPrimaryKey(EmployeeLocation record);
}