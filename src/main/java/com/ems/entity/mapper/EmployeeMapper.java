package com.ems.entity.mapper;

import com.ems.entity.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(String id);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    boolean checkEmpLoginName(String empLoginName);

    Employee selectEmpLogin(@Param("empLoginName") String empLoginName, @Param("empPassword") String empPassword);

    List<Employee> selectAll();

    List<Employee> select(@Param("empNumber") String empNumber, @Param("empName") String empName, @Param("empOrgId") Long empOrgId, @Param("empDistrictId")
    Long empDistrictId, @Param("empLoginName") String empLoginName, @Param("empPhone") String empPhone, @Param("empMobile") String empMobile, @Param
            ("empType") String empType, @Param("empRoleId") String empRoleId);

    int getCountWithUnuseable();

    int deleteByEmpId(@Param("empId") Long empId, @Param("updateTime") Date updateTime, @Param("updateBy") Long updateBy);
}