package com.ems.service;

import com.ems.dto.SysRoleDTO;
import com.ems.entity.SysRole;

import java.util.List;

/**
 * 系统角色服务接口
 * @author litairan on 2018/7/18.
 */
public interface ISysRoleService {
    /**
     * 新增角色
     * @return
     */
    int createRole(SysRoleDTO role);

    /**
     * 修改角色
     * @param role
     * @return
     */
    int updateRole(SysRoleDTO role);

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    int deleteRole(Integer roleId);

    /**
     * 查询角色
     * @param roleName
     * @return
     */
    List<SysRole> selectRole(String roleName);
}
