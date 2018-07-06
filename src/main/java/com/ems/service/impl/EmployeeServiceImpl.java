package com.ems.service.impl;

import com.ems.entity.Employee;
import com.ems.entity.mapper.EmployeeMapper;
import com.ems.service.IEmployeeService;
import com.ems.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by litairan litairan@whtdmh.com on 2018/7/2.
 */
@Service("iEmployeeService")
public class EmployeeServiceImpl implements IEmployeeService {

    private static final Long EMPLOYEE_DEFAULT_ID = 1000000000L;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public int login(String empLoginName, String password) {
        return 0;
    }

    @Override
    public int create(Employee employee) {
        // 当参数中empNumber,empName,empOrgId,empLoginName,empPassword,empType,empManagementDistId,empRoleId存在空值时不能创建
        if (employee.getEmpNumber() == null || employee.getEmpName() == null || employee.getEmpOrgId() == null || employee.getEmpLoginName() == null ||
                employee.getEmpPassword() == null || employee.getEmpType() == null || employee.getEmpManagementDistId() == null || employee.getEmpRoleId() ==
                null) {
            return 0;
        }
        employee.setEmpId(createEmployeeId());
        employee.setId(RandomUtils.getUUID());
        // TODO: 2018/7/6 密码加密
        employee.setUseable(true);
        employee.setEmpLoginFlag(true);
        Date date = new Date();
        employee.setCreateTime(date);
        employee.setUpdateTime(date);
        return employeeMapper.insert(employee);
    }

    /**
     * 生成唯一员工编号
     *
     * @return
     */
    private Long createEmployeeId() {
        return EMPLOYEE_DEFAULT_ID + getCountWithUnuseable();
    }

    /**
     * 获取所有员工数（包含useable为false的员工）
     *
     * @return
     */
    private int getCountWithUnuseable() {
        return employeeMapper.getCountWithUnuseable();
    }

    @Override
    public int delete(Long employeeId) {
        // TODO: 2018/7/6 传入更新者
        return employeeMapper.deleteByEmpId(employeeId, new Date(), null, false);
    }

    @Override
    public int update(Employee employee) {
        // 当参数中empNumber,empName,empOrgId,empLoginName,empPassword,empType,empManagementDistId,empRoleId存在空值时不能创建
        if (employee.getEmpNumber() == null || employee.getEmpName() == null || employee.getEmpOrgId() == null || employee.getEmpLoginName() == null ||
                employee.getEmpPassword() == null || employee.getEmpType() == null || employee.getEmpManagementDistId() == null || employee.getEmpRoleId() ==
                null) {
            return 0;
        }
        return 0;
    }

    @Override
    public List<Employee> selcetAll() {
        return null;
    }


    @Override
    public List<Employee> select(Map<String, Object> args) {
        return null;
    }
}
