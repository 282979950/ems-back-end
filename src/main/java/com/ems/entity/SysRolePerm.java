package com.ems.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 角色权限实体
 */
@Getter
@Setter
public class SysRolePerm extends BaseEntity {
    /**
     * ID
     */
    private Integer id;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 权限ID
     */
    private Integer permId;

    public SysRolePerm(Integer id, Integer roleId, Integer permId, Date createTime, Integer createBy, Date updateTime, Integer updateBy, Boolean useable,
                       String remarks) {
        super(createTime, createBy, updateTime, updateBy, useable, remarks);
        this.id = id;
        this.roleId = roleId;
        this.permId = permId;
    }

    public SysRolePerm() {
        super();
    }
}