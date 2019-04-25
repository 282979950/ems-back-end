package com.tdmh.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 角色权限实体
 */
@Getter
@Setter
public class SysRolePerm {
    /**
     * ID
     */
    private Integer id;

    /**
     * 角色ID
     */
    @NotNull
    private Integer roleId;

    /**
     * 权限ID
     */
    @NotNull
    private Integer permId;

    public SysRolePerm(Integer id, Integer roleId, Integer permId) {
        this.id = id;
        this.roleId = roleId;
        this.permId = permId;
    }

    public SysRolePerm() {
        super();
    }
}