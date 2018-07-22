package com.ems.controller;

import com.ems.common.Const;
import com.ems.common.ServerResponse;
import com.ems.entity.Employee;
import com.ems.service.IEmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author litairan on 2018/7/2.
 */
@Controller
@RequestMapping("/emp/")
public class EmpController {

    @Autowired
    private IEmployeeService employeeService;

    /**
     * 员工登录
     */
    @RequestMapping(value = "login.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<Employee> login(String empLoginName, String empPassword, HttpSession session) {
        if (!employeeService.checkEmpLoginName(empLoginName)) {
            return ServerResponse.createByErrorMessage(Const.EMP_LOGIN_NOTEXIST);
        }

        Employee employee = employeeService.login(empLoginName, empPassword);
        if (employee == null) {
            return ServerResponse.createByErrorMessage(Const.EMP_LOGIN_ERRORPASSWORD);
        }
        employee.setEmpPassword(null);
        session.setAttribute(Const.CURRENT_EMPLOYEE, employee);
        return ServerResponse.createBySuccess(Const.EMP_LOGIN_SUCCESS, employee);
    }

    /**
     * 员工登出
     */
    @RequestMapping(value = "logout.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<Employee> logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_EMPLOYEE);
        return ServerResponse.createBySuccessMessage(Const.EMP_LOGOUT_SUCCESS);
    }

    /**
     * 新建员工
     */
    @RequestMapping(value = "create.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<Employee> create(Employee employee, HttpSession session) {
        Employee currentEmp = (Employee) session.getAttribute(Const.CURRENT_EMPLOYEE);
        if (currentEmp == null) {
            return ServerResponse.createByErrorMessage(Const.EMP_LOGIN_NOTLOGIN);
        }
        String empLoginName = employee.getEmpLoginName();
        if (employeeService.checkEmpLoginName(empLoginName)) {
            return ServerResponse.createByErrorMessage(Const.EMP_CREATE_EXIST);
        }
        int resultCount = employeeService.create(employee, currentEmp);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage(Const.EMP_CREATE_FAIL);
        }
        return ServerResponse.createBySuccessMessage(Const.EMP_CREATE_SUCCESS);
    }

    /**
     * 删除员工（假删除）
     */
    @RequestMapping(value = "delete.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<Employee> delete(Integer empId, HttpSession session) {
        Employee currentEmp = (Employee) session.getAttribute(Const.CURRENT_EMPLOYEE);
        if (currentEmp == null) {
            return ServerResponse.createByErrorMessage(Const.EMP_LOGIN_NOTLOGIN);
        }
        int resultCount = employeeService.delete(empId, currentEmp);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage(Const.EMP_DELETE_FAIL);
        }
        return ServerResponse.createBySuccessMessage(Const.EMP_DELETE_SUCCESS);
    }

    /**
     * 更新员工
     */
    @RequestMapping(value = "update.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<Employee> update(Employee employee, HttpSession session) {
        Employee currentEmp = (Employee) session.getAttribute(Const.CURRENT_EMPLOYEE);
        if (currentEmp == null) {
            return ServerResponse.createByErrorMessage(Const.EMP_LOGIN_NOTLOGIN);
        }
        int resultCount = employeeService.update(employee, currentEmp);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage(Const.EMP_UPDATE_FAIL);
        }
        return ServerResponse.createBySuccessMessage(Const.EMP_UPDATE_SUCCESS);
    }

    /**
     * 查询员工
     */
    @RequestMapping(value = "select.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<Map<String, Object>> select(String empNumber, String empName, Integer empOrgId, Integer empDistrictId, String empLoginName, String
            empPhone, String empMobile, String empType, HttpSession session) {
        Employee currentEmp = (Employee) session.getAttribute(Const.CURRENT_EMPLOYEE);
        if (currentEmp == null) {
            return ServerResponse.createByErrorMessage(Const.EMP_LOGIN_NOTLOGIN);
        }
        PageHelper.startPage(1, 10);
        List<Employee> employeeList = employeeService.select(empNumber, empName, empOrgId, empDistrictId, empLoginName, empPhone, empMobile, empType);
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
        return ServerResponse.createBySuccess(result);
    }
}
