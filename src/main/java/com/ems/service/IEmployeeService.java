package com.ems.service;

import com.ems.entity.Employee;

import java.util.List;
import java.util.Map;

/**
 * Created by litairan litairan@whtdmh.com on 2018/7/2.
 */
public interface IEmployeeService {
    int login(String empLoginName, String password);

    /**
     * 新建员工
     * employee中必须含有empNumber,empName,empOrgId,empLoginName,empPassword,empType,empManagementDistId,empRoleId
     *
     * @param employee
     * @return
     */
    int create(Employee employee);

    /**
     * 删除员工
     *
     * @param employeeId
     * @return
     */
    int delete(Long employeeId);

    /**
     * 更新员工
     * employee中必须含有empNumber,empName,empOrgId,empLoginName,empPassword,empType,empManagementDistId,empRoleId
     * @param employee
     * @return
     */
    int update(Employee employee);

    /**
     * 获取所有的员工信息
     * @return
     */
    List<Employee> selcetAll();

    /**
     * 依据参数查询员工信息
     * @param args
     * @return
     */
    List<Employee> select(Map<String, Object> args);
}
