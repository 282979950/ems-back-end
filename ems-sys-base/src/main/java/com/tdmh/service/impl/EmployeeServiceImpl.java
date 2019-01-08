package com.tdmh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdmh.common.BeanValidator;
import com.tdmh.common.JsonData;
import com.tdmh.entity.Employee;
import com.tdmh.entity.mapper.EmployeeMapper;
import com.tdmh.entity.mapper.EmployeeRoleMapper;
import com.tdmh.exception.ParameterException;
import com.tdmh.param.EmployeeParam;
import com.tdmh.service.IEmployeeService;
import com.tdmh.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 员工服务实现类
 *
 * @author litairan on 2018/7/2.
 */
@Service
@Transactional(readOnly = false)
public class EmployeeServiceImpl implements IEmployeeService {

    public static final String USER_CACHE = "USER_CACHE";

    public static final String USER_CACHE_ID_ = "USER_CACHE_ID_";

    public static final String USER_CACHE_LOGIN_NAME_ = "USER_CACHE_LOGIN_NAME_";

    public static final String USER_CACHE_LIST_BY_OFFICE_ID_ = "USER_CACHE_LIST_BY_OFFICE_ID_";


    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeRoleMapper employeeRoleMapper;

    @Override
    public JsonData login(String empLoginName, String empPassword) {
        return JsonData.success(selectEmpLogin(empLoginName, empPassword), "用户登录成功");
    }

    @Override
    public boolean checkEmpLoginName(String empLoginName) {
        if (StringUtils.isEmpty(empLoginName)) {
            return false;
        }
        return employeeMapper.checkEmpLoginName(empLoginName);
    }

    @Override
    public JsonData getAllEmployees(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<EmployeeParam> employees = employeeMapper.getAllEmployees();
        if (employees == null || employees.size() == 0) {
            return JsonData.successMsg("搜索结果为空");
        }
        for (EmployeeParam employee : employees) {
            employee.setEmpPassword(null);
        }
        PageInfo<EmployeeParam> page = new PageInfo<>(employees);
        return JsonData.success(page, "查询成功");
    }

    @Override
    public Employee getEmpByLoginName(String empLoginName) {
        return employeeMapper.getEmpByLoginName(empLoginName);
    }

    @Override
    public Employee selectEmpLogin(String empLoginName, String empPassword) {
        if (!checkEmpLoginName(empLoginName)) {
            throw new ParameterException("用户登录名不存在");
        }
        String encodePassword = MD5Utils.MD5EncodeUtf8(empPassword);
        Employee employee = employeeMapper.selectEmpLogin(empLoginName, encodePassword);
        if (employee == null) {
            throw new ParameterException("用户密码错误");
        }
        return employee;
    }

    @Override
    @Transactional
    public JsonData addEmployee(EmployeeParam employee) {
        BeanValidator.check(employee);
        if (checkEmpLoginName(employee.getEmpLoginName())) {
            return JsonData.fail("用户登录名已存在");
        }
        String originPassword = employee.getEmpPassword();
        employee.setEmpPassword(MD5Utils.MD5EncodeUtf8(originPassword));
        employee.setUsable(true);
        employee.setEmpLoginFlag(true);
        int resultCount = employeeMapper.addEmployee(employee);
        if (resultCount == 0) {
            return JsonData.fail("新建用户失败");
        }
        int resultCount2 = employeeMapper.addEmployeeRole(employee);
        if(resultCount2 == 0){
            return JsonData.fail("新建用户失败");
        }
        return JsonData.successMsg("新建用户成功");

    }

    @Override
    @Transactional
    public JsonData editEmployee(EmployeeParam employee) {
        BeanValidator.check(employee);
        String password = employee.getEmpPassword();
        if (password != null) {
            employee.setEmpPassword(MD5Utils.MD5EncodeUtf8(password));
        }
        int resultCount = employeeMapper.editEmployee(employee);
        if (resultCount == 0) {
            return JsonData.fail("更新用户失败");
        }
        employeeMapper.deleteEmployeeRole(employee.getEmpId());
        int resultCount2 = employeeMapper.addEmployeeRole(employee);
        if(resultCount2 == 0){
            return JsonData.fail("更新用户失败");
        }
            return JsonData.successMsg("更新用户成功");

    }

    @Override
    @Transactional
    public JsonData deleteEmployee(List<Integer> ids , Integer currentEmpId) {
        if (ids == null || ids.size()==0) {
            return JsonData.successMsg("请选中一个要删除的用户");
        }
        List<EmployeeParam> employees = new ArrayList<>();
        for (Integer id : ids) {
            EmployeeParam emp = employeeMapper.getEmpById(id);
            if (emp == null) {
                return JsonData.fail("用户ID不存在");
            }
            emp.setUpdateBy(currentEmpId);
            employees.add(emp);
            employeeMapper.deleteEmployeeRole(id);
        }
        int resultCount = employeeMapper.deleteEmployee(employees);
        if (resultCount < employees.size()) {
            return JsonData.fail("删除用户失败");
        }
        return JsonData.successMsg("删除用户成功");
    }

    @Override
    public JsonData selcetAll() {
        List<Employee> employeeList = employeeMapper.selectAll();
        if (employeeList == null || employeeList.size() == 0) {
            return JsonData.successMsg("用户列表为空");
        } else {
            return JsonData.successData(employeeList);
        }
    }

    @Override
    public JsonData searchEmployee(String empNumber, String empName, Integer empOrgId, Integer empDistId, Integer roleId, Integer empType, Integer pageNum,
                                   Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<EmployeeParam> employees = employeeMapper.searchEmployee(empNumber, empName, empOrgId, empDistId, roleId, empType);
        if (employees == null) {
            return JsonData.successMsg("查询结果为空");
        }
        PageInfo<EmployeeParam> page = new PageInfo<>(employees);
        return employees.size() == 0 ? JsonData.successMsg("查询结果为空") : JsonData.success(page, "查询成功");
    }

    @Override
    public JsonData getEmpByEmpNumber(String empNumber) {
        EmployeeParam employee = employeeMapper.getEmpByEmpNumber(empNumber);
        return employee == null ? JsonData.successMsg("查询结果为空") : JsonData.success(employee, "查询成功");
    }
}
