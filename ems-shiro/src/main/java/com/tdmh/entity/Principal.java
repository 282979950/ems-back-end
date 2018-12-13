package com.tdmh.entity;


import com.tdmh.service.impl.SysPermissionServiceImpl;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private Integer userType;

    private Set<String> permissions;

    public Principal(Employee employee) {
        this.id = employee.getEmpId();
        this.loginName = employee.getEmpLoginName();
        this.name = employee.getEmpName();
        this.number = employee.getEmpNumber();
        this.phone = employee.getEmpPhone();
        this.mobile = employee.getEmpMobile();
        this.userType = employee.getEmpType();
        this.distId = employee.getEmpDistrictId();
        permissions = new HashSet<>();
        Set<SysRole> roles = employee.getRoles();
        for (SysRole role : roles) {
            if (("admin").equals(role.getRoleName())) {
                List<SysPermission> permissionList = SysPermissionServiceImpl.getPermissionList();
                for (SysPermission sysPermission : permissionList) {
                    this.permissions.add(sysPermission.getPermName());
                }
                break;
            }
            Set<SysPermission> permissions = role.getPermissions();
            for (SysPermission permission : permissions) {
                this.permissions.add(permission.getPermName());
            }
        }
    }
}