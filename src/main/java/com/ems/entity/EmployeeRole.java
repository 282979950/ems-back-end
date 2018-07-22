package com.ems.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 员工角色表
 * 记录所有员工的角色信息
 */
@Getter
@Setter
public class EmployeeRole extends BaseEntity {
    /**
     * 唯一编号
     */
    private Integer id;

    /**
     * 用户编号
     */
    private Integer empId;

    /**
     * 角色编号
     */
    private Integer roleId;

    public EmployeeRole(Integer id, Integer empId, Integer roleId, Date createTime, Integer createBy, Date updateTime, Integer updateBy, Boolean useable,
                        String remarks) {
        super(createTime, createBy, updateTime, updateBy, useable, remarks);
        this.id = id;
        this.empId = empId;
        this.roleId = roleId;
    }

    public EmployeeRole() {
        super();
    }
}