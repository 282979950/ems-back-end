package com.ems.entity.mapper;

import com.ems.entity.Employee;

import java.util.Date;
import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(String id);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    int checkEmpLoginName(String empLoginName);

    Employee selectEmpLogin(String empLoginName, String password);

    List<Employee> selectAll();

    int getCountWithUnuseable();

    int deleteByEmpId(Long empId, Date updateTime,Long updateBy,Boolean useable);
}