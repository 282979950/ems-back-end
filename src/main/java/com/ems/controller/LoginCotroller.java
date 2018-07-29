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
 * @author litairan on 2018/7/27.
 */
@Controller
@RequestMapping("/")
public class LoginCotroller {
    @Autowired
    private IEmployeeService employeeService;

    /**
     * 员工登录
     */
    @RequestMapping(value = "login.do", method = RequestMethod.GET)
    @ResponseBody
    public JsonData login(String empLoginName, String empPassword, HttpSession session) {
        JsonData data = employeeService.login(empLoginName, empPassword);
        if (data.isStatus()) {
            Employee employee = (Employee) data.getData();
            employee.setEmpPassword(null);
            session.setAttribute(Const.CURRENT_EMPLOYEE, employee);
        }
        return data;
    }

    /**
     * 员工登出
     */
    @RequestMapping(value = "logout.do", method = RequestMethod.GET)
    @ResponseBody
    public JsonData logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_EMPLOYEE);
        return JsonData.successMsg(Const.EMP_LOGOUT_SUCCESS);
    }
}
