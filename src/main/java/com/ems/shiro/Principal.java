package com.ems.shiro;


import com.ems.entity.Employee;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 授权用户信息
 *
 * @author litairan on 2018/7/25.
 */
@Getter
@ToString
public class Principal implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String loginName;

    private String name;

    private String number;

    private String phone;

    private String mobile;

    private Integer distId;

    private String userType;

    public Principal(Employee employee) {
        this.id = employee.getEmpId();
        this.loginName = employee.getEmpLoginName();
        this.name = employee.getEmpName();
        this.number = employee.getEmpNumber();
        this.phone = employee.getEmpPhone();
        this.mobile = employee.getEmpMobile();
        this.userType = employee.getEmpType();
        this.distId = employee.getEmpDistrictId();
    }

    /**
     * 获取SESSIONID
     */
//    public String getSessionId() {
//        try {
//            return (String) UserUtils.getSession().getId();
//        } catch (Exception e) {
//            return "";
//        }
//    }
}