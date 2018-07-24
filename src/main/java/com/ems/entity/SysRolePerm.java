package com.ems.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
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
    @NotNull
    private Integer roleId;

    /**
     * 权限ID
     */
    @NotNull
    private Integer permId;

    public SysRolePerm(Integer id, Integer roleId, Integer permId, Date createTime, Integer createBy, Date updateTime, Integer updateBy, Boolean usable,
                       String remarks) {
        super(createTime, createBy, updateTime, updateBy, usable, remarks);
        this.id = id;
        this.roleId = roleId;
        this.permId = permId;
    }

    public SysRolePerm() {
        super();
    }
}