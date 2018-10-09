package com.tdmh.service;

import com.tdmh.common.JsonData;
import com.tdmh.param.SysRoleParam;

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
    JsonData createRole(SysRoleParam role);

    /**
     * 修改角色
     * @param role
     * @return
     */
    JsonData updateRole(SysRoleParam role);

    /**
     * 删除角色
     * @param ids
     * @return
     */
    JsonData deleteRole(List<Integer> ids, Integer currentEmpId);

    /**
     * 查询角色
     * @param roleName
     * @return
     */
    JsonData selectRole(String roleName);
}
