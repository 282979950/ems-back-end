package com.tdmh.entity.mapper;


import com.tdmh.entity.Employee;
import com.tdmh.param.EmployeeParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper @Component
public interface EmployeeMapper {

    int deleteByPrimaryKey(Integer empId);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Integer empId);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    boolean checkEmpLoginName(String empLoginName);

    Employee selectEmpLogin(@Param("empLoginName") String empLoginName, @Param("empPassword") String empPassword);

    List<Employee> selectAll();

    List<Employee> select(@Param("empNumber") String empNumber, @Param("empName") String empName, @Param("empOrgId") Integer empOrgId, @Param
            ("empDistrictId") Integer empDistrictId, @Param("empLoginName") String empLoginName, @Param("empPhone") String empPhone, @Param("empMobile")
                                  String empMobile, @Param("empType") String empType);

    int getCountWithUnusable();

    int deleteByEmpId(@Param("empId") Integer empId, @Param("updateBy") Integer updateBy);

    Employee selectByEmpId(@Param("empId") Integer empId);

    Employee getEmpByLoginName(@Param("empLoginName") String empLoginName);

    List<EmployeeParam> getAllEmployees();

    int addEmployee(EmployeeParam employee);

    int addEmployeeRole(EmployeeParam employee);

    int deleteEmployeeRole(@Param("empId") Integer empId);

    int editEmployee(EmployeeParam employee);

    int deleteEmployee(List<EmployeeParam> employees);

    List<EmployeeParam> searchEmployee(@Param("empNumber") String empNumber, @Param("empName") String empName, @Param("empOrgId") Integer empOrgId, @Param
            ("empDistrictId") Integer empDistrictId, @Param("roleId") Integer roleId, @Param("empType") Integer empType);

    EmployeeParam getEmpById(@Param("empId") Integer empId);

    EmployeeParam getEmpByEmpNumber(@Param("empNumber") String empNumber);
}