package com.ems.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 权限实体
 */
@Getter
@Setter
public class SysPermission extends BaseEntity {
    /**
     * 权限ID
     */
    private Integer permId;

    /**
     * 权限名称
     */
    private String permName;

    /**
     * 权限类型
     */
    private Integer permType;

    /**
     * 目录ID
     */
    private Integer menuId;

    public SysPermission(Integer permId, String permName, Integer permType, Integer menuId, Date createTime, Integer createBy, Date updateTime, Integer
            updateBy, Boolean useable, String remarks) {
        super(createTime, createBy, updateTime, updateBy, useable, remarks);
        this.permId = permId;
        this.permName = permName;
        this.permType = permType;
        this.menuId = menuId;
    }

    public SysPermission() {
        super();
    }
}