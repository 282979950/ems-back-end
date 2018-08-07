package com.ems.service;

import com.ems.common.JsonData;
import com.ems.entity.Employee;

/**
 * @author litairan on 2018/7/2.
 */
public interface IEmployeeService {

    /**
     * 员工登录
     *
     * @param empLoginName
     * @param empPassword
     * @return
     */
    JsonData login(String empLoginName, String empPassword);

    /**
     * 获取员工登录信息
     *
     * @param empLoginName
     * @param empPassword
     * @return
     */
    Employee selectEmpLogin(String empLoginName, String empPassword);

    /**
     * 根据登录名获取员工登录信息
     *
     * @param empLoginName
     * @return
     */
    Employee getEmpByLoginName(String empLoginName);

    /**
     * 检查登录名是否存在(0不存在,1存在)
     *
     * @return
     */
    public boolean checkEmpLoginName(String empLoginName);


    /**
     * 新建员工
     * employee中必须含有empNumber,empName,empOrgId,empLoginName,empPassword,empType,empManagementDistId,empRoleId
     *
     * @param employee
     * @return
     */
    JsonData create(Employee employee, Employee currentEmp);

    /**
     * 删除员工
     *
     * @param employeeId
     * @return
     */
    JsonData delete(Integer employeeId, Employee currentEmp);

    /**
     * 更新员工
     * employee中必须含有empNumber,empName,empOrgId,empLoginName,empPassword,empType,empManagementDistId,empRoleId
     *
     * @param employee
     * @return
     */
    JsonData update(Employee employee, Employee currentEmp);

    /**
     * 获取所有的员工信息
     *
     * @return
     */
    JsonData selcetAll();

    /**
     * 依据参数查询员工信息
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
    JsonData select(String empNumber, String empName, Integer empOrgId, Integer empDistrictId, String empLoginName, String empPhone, String empMobile,
                    String empType);
}
