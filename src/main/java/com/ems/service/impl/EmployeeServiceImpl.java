package com.ems.service.impl;

import com.ems.common.BeanValidator;
import com.ems.common.JsonData;
import com.ems.entity.Employee;
import com.ems.entity.mapper.EmployeeMapper;
import com.ems.entity.mapper.EmployeeRoleMapper;
import com.ems.exception.ParameterException;
import com.ems.service.IEmployeeService;
import com.ems.service.ISysCacheService;
import com.ems.utils.MD5Utils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Transactional(readOnly = true)
    public JsonData create(Employee employee, Employee currentEmp) {
        BeanValidator.check(employee);
        if (checkEmpLoginName(employee.getEmpLoginName())) {
            return JsonData.fail("用户登录名已存在");
        }
        String originPassword = employee.getEmpPassword();
        employee.setEmpPassword(MD5Utils.MD5EncodeUtf8(originPassword));
        employee.setUsable(true);
        employee.setEmpLoginFlag(true);
        employee.setCreateBy(currentEmp.getEmpId());
        employee.setUpdateBy(currentEmp.getEmpId());
        int resultCount = employeeMapper.insertSelective(employee);
        if (resultCount == 0) {
            return JsonData.fail("新建用户失败");
        } else {
            return JsonData.successMsg("新建用户成功");
        }
    }

    @Override
    @Transactional(readOnly = false)
    public JsonData delete(Integer employeeId, Employee currentEmployee) {
        Integer updateBy = currentEmployee.getEmpId();
        if (employeeId == null) {
            return JsonData.fail("用户ID不存在");
        }
        int resultCount = employeeMapper.deleteByEmpId(employeeId, updateBy);
        if (resultCount == 0) {
            return JsonData.fail("删除用户失败");
        } else {
            return JsonData.successMsg("删除用户成功");
        }
    }

    @Override
    @Transactional(readOnly = false)
    public JsonData update(Employee employee, Employee currentEmp) {
        BeanValidator.check(employee);
        employee.setUpdateBy(currentEmp.getEmpId());
        employee.setUsable(true);
        int resultCount = employeeMapper.updateByPrimaryKey(employee);
        if (resultCount == 0) {
            return JsonData.fail("更新用户失败");
        } else {
            return JsonData.successMsg("更新用户成功");
        }
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
    public JsonData select(String empNumber, String empName, Integer empOrgId, Integer empDistrictId, String empLoginName, String empPhone, String
            empMobile,
                           String empType) {
        PageHelper.startPage(1, 10);
        List<Employee> employeeList = employeeMapper.select(empNumber, empName, empOrgId, empDistrictId, empLoginName, empPhone, empMobile, empType);
        if (employeeList == null || employeeList.size() == 0) {
            return JsonData.successMsg("查询结果为空");
        }
        PageInfo pageInfo = new PageInfo(employeeList);
        Map<String, Object> result = new HashMap<>(8);
        result.put("isHasNextPage", pageInfo.isHasNextPage());
        result.put("isHasPreviousPage", pageInfo.isHasPreviousPage());
        result.put("isIsFirstPage", pageInfo.isIsFirstPage());
        result.put("isIsLastPage", pageInfo.isIsLastPage());
        result.put("total", pageInfo.getTotal());
        result.put("pageNum", pageInfo.getPageNum());
        result.put("pageSize", pageInfo.getPageSize());
        result.put("empList", employeeList);
        return JsonData.successData(result);
    }

    public Employee getEmpById(Integer empId) {
        Employee emp = (Employee) sysCacheService.get(USER_CACHE, USER_CACHE_ID_ + empId);
        if (emp != null) {
            return emp;
        }
        emp = employeeMapper.selectByEmpId(empId);
        if (emp == null) {
            return null;
        }
        // TODO: 2018/7/25 设置角色信息
//        emp.setRoleList(employeeRoleMapper.selectByEmpId(empId));
        sysCacheService.put(USER_CACHE, USER_CACHE_ID_ + emp.getEmpId(), emp);
        sysCacheService.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + emp.getEmpLoginName(), emp);
        return emp;
    }
}
