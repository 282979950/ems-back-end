package com.tdmh.controller.system;

import com.tdmh.common.JsonData;
import com.tdmh.param.EmployeeParam;
import com.tdmh.service.IEmployeeService;
import com.tdmh.util.ShiroUtils;
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
    public JsonData listData(Integer pageNum, Integer pageSize) {
        return employeeService.getAllEmployees(pageNum, pageSize);
    }

    /**
     * 新建员工
     */
    @RequiresPermissions("sys:emp:create")
    @RequestMapping(value = "add.do")
    @ResponseBody
    public JsonData addEmployee(EmployeeParam employee) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        employee.setCreateBy(currentEmpId);
        employee.setUpdateBy(currentEmpId);
        return employeeService.addEmployee(employee);
    }

    /**
     * 删除员工（假删除）
     */
    @RequiresPermissions("sys:emp:delete")
    @RequestMapping(value = "delete.do")
    @ResponseBody
    public JsonData delete(@RequestParam(value = "ids[]") List<Integer> ids) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        return employeeService.deleteEmployee(ids, currentEmpId);
    }

    /**
     * 更新员工
     */
    @RequiresPermissions("sys:emp:update")
    @RequestMapping(value = "edit.do")
    @ResponseBody
    public JsonData update(EmployeeParam employee) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        employee.setUpdateBy(currentEmpId);
        return employeeService.editEmployee(employee);
    }

    /**
     * 查询员工
     */
    @RequiresPermissions("sys:emp:retrieve")
    @RequestMapping(value = "search.do")
    @ResponseBody
    public JsonData searchEmployee(String empNumber, String empName, Integer empOrgId, Integer empDistId, Integer roleId, Integer empType, Integer pageNum,
                                   Integer pageSize) {
        return employeeService.searchEmployee(empNumber, empName, empOrgId, empDistId, roleId, empType, pageNum, pageSize);
    }

    @RequestMapping(value = "getEmpByEmpNumber.do")
    @ResponseBody
    public JsonData getEmpByEmpNumber(String empNumber) {
//        return employeeService.searchEmployee(empNumber, null, null, null, null, null, null, null);
        return employeeService.getEmpByEmpNumber(empNumber);
    }
}
