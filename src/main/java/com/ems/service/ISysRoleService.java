package com.ems.service;

import com.ems.common.JsonData;
import com.ems.param.SysRoleParam;

/**
 * 系统角色服务接口
 * @author litairan on 2018/7/18.
 */
public interface ISysRoleService {
    /**
     * 新增角色
     * @return
     */
    JsonData createRole(SysRoleParam role);

    /**
     * 修改角色
     * @param role
     * @return
     */
    JsonData updateRole(SysRoleParam role);

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    JsonData deleteRole(Integer roleId);

    /**
     * 查询角色
     * @param roleName
     * @return
     */
    JsonData selectRole(String roleName);
}
