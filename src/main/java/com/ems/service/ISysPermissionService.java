package com.ems.service;

import com.ems.entity.Employee;
import com.ems.entity.SysPermission;

import java.util.List;

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
    int createPermission(SysPermission permission, Employee currentEmp);

    /**
     * 更新权限
     *
     * @param permission 其中permId,permName,menuId,permType不能为空
     * @return
     */

    int updatePermission(SysPermission permission, Employee currentEmp);

    /**
     * 删除权限（假删除）
     *
     * @param permId
     * @return
     */
    int deletePermission(Integer permId, Employee currentEmp);

    /**
     * 依据权限名称和目录名称查询权限
     *
     * @param permName
     * @param permType
     * @param menuName
     * @return
     */
    List<SysPermission> selectPermission(String permName, Integer permType, String menuName);
}
