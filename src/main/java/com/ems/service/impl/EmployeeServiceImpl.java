package com.ems.service.impl;

import com.ems.entity.Employee;
import com.ems.entity.mapper.EmployeeMapper;
import com.ems.service.IEmployeeService;
import com.ems.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by litairan litairan@whtdmh.com on 2018/7/2.
 */
@Service("iEmployeeService")
@Transactional(readOnly = false)
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Employee login(String empLoginName, String empPassword) {
        String encodePassword = MD5Utils.MD5EncodeUtf8(empPassword);
        return employeeMapper.selectEmpLogin(empLoginName, encodePassword);
    }

    @Override
    public boolean checkEmpLoginName(String empLoginName) {
        if (StringUtils.isEmpty(empLoginName)) {
            return false;
        }
        return employeeMapper.checkEmpLoginName(empLoginName);
    }

    @Override
    @Transactional(readOnly = true)
    public int create(Employee employee, Employee currentEmp) {
        // 当参数中empNumber,empName,empOrgId,empLoginName,empPassword,empType,empManagementDistId,empRoleId存在空值时不能创建
        if (employee.getEmpNumber() == null || employee.getEmpName() == null || employee.getEmpOrgId() == null || employee.getEmpLoginName() == null ||
                employee.getEmpPassword() == null || employee.getEmpType() == null || employee.getEmpManagementDistId() == null) {
            return 0;
        }
        String originPassword = employee.getEmpPassword();
        employee.setEmpPassword(MD5Utils.MD5EncodeUtf8(originPassword));
        employee.setUseable(true);
        employee.setEmpLoginFlag(true);
        Date date = new Date();
        employee.setCreateTime(date);
        employee.setCreateBy(currentEmp.getEmpId());
        employee.setUpdateTime(date);
        employee.setUpdateBy(currentEmp.getEmpId());
        return employeeMapper.insert(employee);
    }

    @Override
    @Transactional(readOnly = false)
    public int delete(Integer employeeId, Employee currentEmployee) {
        Integer updateBy = currentEmployee.getEmpId();
        if (employeeId == null) {
            return 0;
        }
        return employeeMapper.deleteByEmpId(employeeId, new Date(), updateBy);
    }

    @Override
    @Transactional(readOnly = false)
    public int update(Employee employee, Employee currentEmp) {
        // 当参数中empNumber,empName,empOrgId,empLoginName,empPassword,empType,empManagementDistId,empRoleId存在空值时不能更新
        if (employee.getEmpNumber() == null || employee.getEmpName() == null || employee.getEmpOrgId() == null || employee.getEmpLoginName() == null ||
                employee.getEmpPassword() == null || employee.getEmpType() == null || employee.getEmpManagementDistId() == null || employee.getEmpLoginFlag()
                == null) {
            return 0;
        }
        employee.setUpdateBy(currentEmp.getEmpId());
        employee.setUpdateTime(new Date());
        employee.setUseable(true);
        return employeeMapper.updateByPrimaryKey(employee);
    }

    @Override
    public List<Employee> selcetAll() {
        return employeeMapper.selectAll();
    }


    /**
     * 依据empNumber,empName,empOrgId,empLoginName,empType,emp,empPhone,empMobile查询
     *
     * @param empNumber
     * @param empName
     * @param empOrgId
     * @param empDistrictId
     * @param empLoginName
     * @param empPhone
     * @param empMobile
     * @param empType
     * @return
     */
    @Override
    public List<Employee> select(String empNumber, String empName, Integer empOrgId, Integer empDistrictId, String empLoginName, String empPhone, String
            empMobile,
                                 String empType) {
        return employeeMapper.select(empNumber, empName, empOrgId, empDistrictId, empLoginName, empPhone, empMobile, empType);
    }
}
