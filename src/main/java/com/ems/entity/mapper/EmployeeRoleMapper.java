package com.ems.entity.mapper;

import com.ems.entity.EmployeeRole;
import org.apache.ibatis.annotations.Param;

import javax.management.relation.Role;
import java.util.List;

public interface EmployeeRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EmployeeRole record);

    int insertSelective(EmployeeRole record);

    EmployeeRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmployeeRole record);

    int updateByPrimaryKey(EmployeeRole record);

    List<Role> selectByEmpId(@Param("empId") Integer empId);
}