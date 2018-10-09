package com.tdmh.entity.mapper;


import com.tdmh.entity.EmployeeRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import java.util.List;
@Repository
public interface EmployeeRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EmployeeRole record);

    int insertSelective(EmployeeRole record);

    EmployeeRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmployeeRole record);

    int updateByPrimaryKey(EmployeeRole record);

    List<Role> selectByEmpId(@Param("empId") Integer empId);
}