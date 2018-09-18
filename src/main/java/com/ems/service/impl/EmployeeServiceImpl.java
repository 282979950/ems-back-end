package com.ems.service.impl;

import com.ems.common.BeanValidator;
import com.ems.common.JsonData;
import com.ems.entity.Employee;
import com.ems.entity.mapper.EmployeeMapper;
import com.ems.entity.mapper.EmployeeRoleMapper;
import com.ems.exception.ParameterException;
import com.ems.param.EmployeeParam;
import com.ems.service.IEmployeeService;
import com.ems.service.ISysCacheService;
import com.ems.shiro.utils.ShiroUtils;
import com.ems.utils.MD5Utils;
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
@Service("iEmployeeService")
@Transactional(readOnly = false)
public class EmployeeServiceImpl implements IEmployeeService {

    public static final String USER_CACHE = "USER_CACHE";

    public static final String USER_CACHE_ID_ = "USER_CACHE_ID_";

    public static final String USER_CACHE_LOGIN_NAME_ = "USER_CACHE_LOGIN_NAME_";

    public static final String USER_CACHE_LIST_BY_OFFICE_ID_ = "USER_CACHE_LIST_BY_OFFICE_ID_";

    @Autowired
    private ISysCacheService sysCacheService;

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
    public JsonData getAllEmployees() {
        List<EmployeeParam> employees = employeeMapper.getAllEmployees();
        if (employees == null || employees.size() == 0) {
            return JsonData.successMsg("搜索结果为空");
        }
        for (EmployeeParam employee : employees) {
            employee.setEmpPassword(null);
        }
        return JsonData.success(employees, "查询成功");
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
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        String originPassword = employee.getEmpPassword();
        employee.setEmpPassword(MD5Utils.MD5EncodeUtf8(originPassword));
        employee.setUsable(true);
        employee.setEmpLoginFlag(true);
        employee.setCreateBy(currentEmpId);
        employee.setUpdateBy(currentEmpId);
        int resultCount = employeeMapper.addEmployee(employee);
        if (resultCount == 0) {
            return JsonData.fail("新建用户失败");
        } else {
            return JsonData.successMsg("新建用户成功");
        }
    }

    @Override
    @Transactional
    public JsonData editEmployee(EmployeeParam employee) {
        BeanValidator.check(employee);
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        employee.setUpdateBy(currentEmpId);
        String password = employee.getEmpPassword();
        if (password != null) {
            employee.setEmpPassword(MD5Utils.MD5EncodeUtf8(password));
        }
        int resultCount = employeeMapper.editEmployee(employee);
        if (resultCount == 0) {
            return JsonData.fail("更新用户失败");
        } else {
            return JsonData.successMsg("更新用户成功");
        }
    }

    @Override
    @Transactional
    public JsonData deleteEmployee(List<Integer> ids) {
        if (ids == null || ids.size()==0) {
            return JsonData.successMsg("请选中一个要删除的用户");
        }
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        List<EmployeeParam> employees = new ArrayList<>();
        for (Integer id : ids) {
            EmployeeParam emp = employeeMapper.getEmpById(id);
            if (emp == null) {
                return JsonData.fail("用户ID不存在");
            }
            emp.setUpdateBy(currentEmpId);
            employees.add(emp);
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
    public JsonData searchEmployee(String empNumber, String empName, Integer empOrgId, Integer empDistrictId, String empLoginName, String empPhone, String
            empMobile, String empType) {
//        PageHelper.startPage(1, 10);
//        List<Employee> employeeList = employeeMapper.select(empNumber, empName, empOrgId, empDistrictId, empLoginName, empPhone, empMobile, empType);
//        if (employeeList == null || employeeList.size() == 0) {
//            return JsonData.successMsg("查询结果为空");
//        }
//        PageInfo pageInfo = new PageInfo(employeeList);
//        Map<String, Object> result = new HashMap<>(8);
//        result.put("isHasNextPage", pageInfo.isHasNextPage());
//        result.put("isHasPreviousPage", pageInfo.isHasPreviousPage());
//        result.put("isIsFirstPage", pageInfo.isIsFirstPage());
//        result.put("isIsLastPage", pageInfo.isIsLastPage());
//        result.put("total", pageInfo.getTotal());
//        result.put("pageNum", pageInfo.getPageNum());
//        result.put("pageSize", pageInfo.getPageSize());
//        result.put("empList", employeeList);
//        return JsonData.successData(result);
        List<Employee> employees = employeeMapper.searchEmployee(empNumber, empName, empOrgId, empDistrictId, empLoginName, empPhone, empMobile, empType);
        return employees == null || employees.size() == 0 ? JsonData.successMsg("查询结果为空") : JsonData.success(employees, "查询成功");
    }
}
