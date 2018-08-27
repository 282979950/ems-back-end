package com.ems.service;

import com.ems.common.JsonData;
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
    JsonData createPermission(SysPermission permission);

    /**
     * 更新权限
     *
     * @param permission 其中permId,permName,menuId,permType不能为空
     * @return
     */

    JsonData updatePermission(SysPermission permission);

    /**
     * 删除权限（假删除）
     *
     * @param permId
     * @return
     */
    JsonData deletePermission(List<Integer> ids);

    /**
     * 依据权限名称和目录名称查询权限
     *
     * @param permName
     * @param permCaption
     * @param menuName
     * @return
     */
    JsonData selectPermission(String permName, String permCaption, String menuName);

    /**
     * 查询所有菜单列表
     * @return
     */
    JsonData listAllMenus();

    /**
     * 获取所有菜单列表与权限列表关系
     * @return
     */
    JsonData listAllMenusAndPerms();
}
