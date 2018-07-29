package com.ems.service;

import com.ems.common.JsonData;
import com.ems.entity.Employee;
import com.ems.entity.SysPermission;

/**
 * 系统权限服务接口
 * @author litairan on 2018/7/13.
 */
public interface ISysPermissionService {
    /**
     * 新建权限
     *
     * @param permission 其中permId,permName,menuId,permType不能为空
     * @param currentEmp
     * @return
     */
    JsonData createPermission(SysPermission permission, Employee currentEmp);

    /**
     * 更新权限
     *
     * @param permission 其中permId,permName,menuId,permType不能为空
     * @return
     */

    JsonData updatePermission(SysPermission permission, Employee currentEmp);

    /**
     * 删除权限（假删除）
     *
     * @param permId
     * @return
     */
    JsonData deletePermission(Integer permId, Employee currentEmp);

    /**
     * 依据权限名称和目录名称查询权限
     *
     * @param permName
     * @param permType
     * @param menuName
     * @return
     */
    JsonData selectPermission(String permName, Integer permType, String menuName);
}
