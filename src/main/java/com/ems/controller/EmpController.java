package com.ems.controller;

import com.ems.common.Const;
import com.ems.common.JsonData;
import com.ems.entity.Employee;
import com.ems.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author litairan on 2018/7/2.
 */
@Controller
@RequestMapping("/emp/")
public class EmpController {

    @Autowired
    private IEmployeeService employeeService;

    /**
     * 新建员工
     */
    @RequestMapping(value = "create.do", method = RequestMethod.GET)
    @ResponseBody
    public JsonData create(Employee employee, HttpSession session) {
        Employee currentEmp = (Employee) session.getAttribute(Const.CURRENT_EMPLOYEE);
        if (currentEmp == null) {
            return JsonData.fail(Const.EMP_LOGIN_NOTLOGIN);
        }
        return employeeService.create(employee, currentEmp);
    }

    /**
     * 删除员工（假删除）
     */
    @RequestMapping(value = "delete.do", method = RequestMethod.GET)
    @ResponseBody
    public JsonData delete(Integer empId, HttpSession session) {
        Employee currentEmp = (Employee) session.getAttribute(Const.CURRENT_EMPLOYEE);
        if (currentEmp == null) {
            return JsonData.fail(Const.EMP_LOGIN_NOTLOGIN);
        }
        return employeeService.delete(empId, currentEmp);
    }

    /**
     * 更新员工
     */
    @RequestMapping(value = "update.do", method = RequestMethod.GET)
    @ResponseBody
    public JsonData update(Employee employee, HttpSession session) {
        Employee currentEmp = (Employee) session.getAttribute(Const.CURRENT_EMPLOYEE);
        if (currentEmp == null) {
            return JsonData.fail(Const.EMP_LOGIN_NOTLOGIN);
        }
        return employeeService.update(employee, currentEmp);
    }

    /**
     * 查询员工
     */
    @RequestMapping(value = "select.do", method = RequestMethod.GET)
    @ResponseBody
    public JsonData select(String empNumber, String empName, Integer empOrgId, Integer empDistrictId, String empLoginName, String
            empPhone, String empMobile, String empType, HttpSession session) {
        Employee currentEmp = (Employee) session.getAttribute(Const.CURRENT_EMPLOYEE);
        if (currentEmp == null) {
            return JsonData.fail(Const.EMP_LOGIN_NOTLOGIN);
        }
        return employeeService.select(empNumber, empName, empOrgId, empDistrictId, empLoginName, empPhone, empMobile, empType);
    }
}
