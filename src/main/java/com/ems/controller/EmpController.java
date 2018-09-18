package com.ems.controller;

import com.ems.common.JsonData;
import com.ems.param.EmployeeParam;
import com.ems.service.IEmployeeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author litairan on 2018/7/2.
 */
@Controller
@RequestMapping("/emp/")
public class EmpController {

    @Autowired
    private IEmployeeService employeeService;

    /**
     * 获取所有员工信息
     */
    @RequiresPermissions("sys:emp:visit")
    @RequestMapping(value = "listData.do")
    @ResponseBody
    public JsonData listData() {
        return employeeService.getAllEmployees();
    }

    /**
     * 新建员工
     */
    @RequiresPermissions("sys:emp:create")
    @RequestMapping(value = "add.do")
    @ResponseBody
    public JsonData addEmployee(EmployeeParam employee) {
        return employeeService.addEmployee(employee);
    }

    /**
     * 删除员工（假删除）
     */
    @RequiresPermissions("sys:emp:delete")
    @RequestMapping(value = "delete.do")
    @ResponseBody
    public JsonData delete(@RequestParam(value = "ids[]") List<Integer> ids) {
        return employeeService.deleteEmployee(ids);
    }

    /**
     * 更新员工
     */
    @RequiresPermissions("sys:emp:update")
    @RequestMapping(value = "edit.do")
    @ResponseBody
    public JsonData update(EmployeeParam employee) {
        return employeeService.editEmployee(employee);
    }

    /**
     * 查询员工
     */
    @RequiresPermissions("sys:emp:retrieve")
    @RequestMapping(value = "search.do")
    @ResponseBody
    public JsonData searchEmployee(@RequestParam("empNumber") String empNumber, @RequestParam("empName") String empName, @RequestParam("empOrgId") Integer
            empOrgId, @RequestParam("empDistrictId") Integer empDistrictId, @RequestParam("empLoginName") String empLoginName, @RequestParam("empPhone") String
            empPhone, @RequestParam("empMobile") String empMobile, @RequestParam("empType") String empType) {
        return employeeService.searchEmployee(empNumber, empName, empOrgId, empDistrictId, empLoginName, empPhone, empMobile, empType);
    }
}
