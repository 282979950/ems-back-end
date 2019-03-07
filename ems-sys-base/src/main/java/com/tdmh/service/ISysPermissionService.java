package com.tdmh.service;

import com.tdmh.common.JsonData;
import com.tdmh.entity.SysPermission;

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
     * @param ids
     * @return
     */
    JsonData deletePermission(List<Integer> ids,Integer currentEmpId);

    /**
     * 依据权限名称和目录名称查询权限
     *
     * @param permName
     * @param permCaption
     * @param menuName
     * @return
     */
    JsonData selectPermission(String permName, String permCaption, String menuName, Integer pageNum, Integer pageSize);

    /**
     * 查询所有菜单列表
     * @return
     */
    JsonData listAllMenus();

    /**
     * 获取所有菜单列表与权限列表关系
     * @return
     */
    JsonData listAllPerms();
}
