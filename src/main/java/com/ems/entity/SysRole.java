package com.ems.entity;

import com.ems.common.Const;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 角色实体
 */
@Getter
@Setter
public class SysRole extends BaseEntity {
    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色能访问的区域
     */
    private String roleDists;

    /**
     * 角色能访问的区域
     */
    private List<Integer> roleDistList;

    /**
     * 角色能访问的组织
     */
    private String roleOrgs;

    /**
     * 角色能访问的组织
     */
    private List<Integer> roleOrgList;

    public SysRole(Integer roleId, String roleName, String roleDists, String roleOrgs, Date createTime, Integer createBy, Date updateTime, Integer updateBy,
                   Boolean useable, String remarks) {
        super(createTime, createBy, updateTime, updateBy, useable, remarks);
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleDists = roleDists;
        this.roleOrgs = roleOrgs;
    }

    public SysRole() {
        super();
    }

    private void createDistList() {
        String[] distArray = roleDists.split(Const.DEFAULT_SEPARATOR);
        roleDistList = new ArrayList<>(distArray.length);
        for (String dist : distArray) {
            roleDistList.add(Integer.parseInt(dist));
        }
    }

    private void createOrgList() {
        String[] orgArray = roleOrgs.split(Const.DEFAULT_SEPARATOR);
        roleDistList = new ArrayList<>(orgArray.length);
        for (String org : orgArray) {
            roleDistList.add(Integer.parseInt(org));
        }
    }
}