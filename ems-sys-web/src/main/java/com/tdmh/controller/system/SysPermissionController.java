package com.tdmh.controller.system;

import com.tdmh.common.JsonData;
import com.tdmh.entity.SysPermission;
import com.tdmh.service.ISysPermissionService;
import com.tdmh.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author liuxia on 2018/8/21.
 */
@Controller
@RequestMapping(value = "/permission/")
public class SysPermissionController {
    @Autowired
    private ISysPermissionService sysPermissionService;
    /**
     * 获取权限列表
     *
     * @return
     */
    @RequiresPermissions("sys:perm:visit")
    @RequestMapping("listData.do")
    @ResponseBody
    public JsonData getPermissionList(Integer pageNum, Integer pageSize) {
        return sysPermissionService.selectPermission(null,null,null, pageNum, pageSize);
    }

    /**
     * 新增权限
     *
     */
    @RequiresPermissions("sys:perm:create")
    @RequestMapping(value = "add.do")
    @ResponseBody
    public JsonData createPermission(SysPermission permission) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        permission.setCreateBy(currentEmpId);
        permission.setUpdateBy(currentEmpId);
        return sysPermissionService.createPermission(permission);
    }

    /**
     * 删除权限
     */
    @RequiresPermissions("sys:perm:delete")
    @RequestMapping("delete.do")
    @ResponseBody
    public JsonData deletePermission(@RequestParam(value = "ids[]")List <Integer> ids) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        return sysPermissionService.deletePermission(ids , currentEmpId);
      }

    /**
     * 更新权限
     */
    @RequiresPermissions("sys:perm:update")
    @RequestMapping("edit.do")
    @ResponseBody
    public JsonData updatePermission(SysPermission permission) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        permission.setUpdateBy(currentEmpId);
        return sysPermissionService.updatePermission(permission);
    }

    /**
     * 获取特定权限权限
     */
    @RequiresPermissions("sys:perm:retrieve")
    @RequestMapping("search.do")
    @ResponseBody
    public JsonData selectPermission(String permName, String permCaption, String menuName, Integer pageNum, Integer pageSize) {
        return sysPermissionService.selectPermission(permName, permCaption, menuName, pageNum, pageSize);
    }

    /**
     * 获取菜单列表
     */
    @RequiresPermissions("sys:perm:visit")
    @RequestMapping("listAllMenus.do")
    @ResponseBody
    public JsonData listAllMenus() {
        return sysPermissionService.listAllMenus();
    }

    /**
     * 获取菜单列表与权限列表关系
     */
    @RequiresPermissions("sys:perm:visit")
    @RequestMapping("listAllMenusAndPerms.do")
    @ResponseBody
    public JsonData listAllMenusAndPerms() {
        return sysPermissionService.listAllMenusAndPerms();
    }


}
