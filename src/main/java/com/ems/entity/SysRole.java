package com.ems.entity;

import com.ems.common.Const;
import com.github.pagehelper.util.StringUtil;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    @NotNull
    @Length(max = 20, message = "菜单名称不能超过20个字")
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

    /**
     * 角色权限
     */
    private Set<SysPermission> permissions;
    
    private List<Integer> rolePermList;

    public SysRole(Integer roleId, String roleName, String roleDists, String roleOrgs, Date createTime, Integer createBy, Date updateTime, Integer updateBy,
                   Boolean usable, String remarks) {
        super(createTime, createBy, updateTime, updateBy, usable, remarks);
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleDists = roleDists;
        this.roleOrgs = roleOrgs;
    }

    public SysRole() {
        super();
    }
    
    public void setRoleDistList() {
    	createDistList();
    }
    
    public void setRoleOrgList() {
    	createOrgList();
    }
    
    public void setRolePermList() {
    	createPermList();
    }
    
    private void createDistList() {
    	if(StringUtil.isNotEmpty(roleDists)) {
        String[] distArray = roleDists.split(Const.DEFAULT_SEPARATOR);
        roleDistList = new ArrayList<>(distArray.length);
        for (String dist : distArray) {
            roleDistList.add(Integer.parseInt(dist));
        }
    	}
    }

    private void createOrgList() {
    	if(StringUtil.isNotEmpty(roleOrgs)) {
        String[] orgArray = roleOrgs.split(Const.DEFAULT_SEPARATOR);
        roleOrgList = new ArrayList<>(orgArray.length);
        for (String org : orgArray) {
        	roleOrgList.add(Integer.parseInt(org));
        }
    	}
    }
    
    private void createPermList() {
       rolePermList = new ArrayList<>(permissions.size());
        for (SysPermission perm : permissions) {
        	rolePermList.add(perm.getPermId());
        }
    }
}