package com.ems.controller;

import com.ems.common.Const;
import com.ems.common.ServerResponse;
import com.ems.entity.Employee;
import com.ems.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by litairan litairan@whtdmh.com on 2018/7/2.
 */
@Controller
@RequestMapping("/emp/")
public class EmpController {

    @Autowired
    private IEmployeeService employeeService;

    /**
     * 员工登出
     */
    @RequestMapping(value = "logout.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<Employee> logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_EMPLOYEE);
        return ServerResponse.createBySuccess();
    }

    /**
     * 新建员工
     */
    @RequestMapping(value = "ceate.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<Employee> create(Employee employee, HttpSession session) {
        // TODO: 2018/7/2
        return null;
    }

    /**
     * 删除员工（假删除）
     */
    @RequestMapping(value = "delete.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<Employee> delete(HttpSession session) {
        // TODO: 2018/7/2
        return null;
    }

    /**
     * 更新员工
     */
    @RequestMapping(value = "update.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<Employee> update(HttpSession session) {
        // TODO: 2018/7/2
        return null;
    }
}
