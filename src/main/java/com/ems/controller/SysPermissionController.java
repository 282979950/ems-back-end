package com.ems.controller;

import com.ems.common.JsonData;
import com.ems.entity.SysPermission;
import com.ems.service.ISysPermissionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
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
    public JsonData getPermissionList() {
        return sysPermissionService.selectPermission(null,null,null);
    }

    /**
     * 新增权限
     *
     */
    @RequiresPermissions("sys:perm:create")
    @RequestMapping(value = "add.do")
    @ResponseBody
    public JsonData createPermission(SysPermission permission) {
         return sysPermissionService.createPermission(permission);
    }

    /**
     * 删除权限
     */
    @RequiresPermissions("sys:perm:delete")
    @RequestMapping("delete.do")
    @ResponseBody
    public JsonData Permission(@RequestParam(value = "ids[]")List <Integer> ids) {
        return sysPermissionService.deletePermission(ids);
      }

    /**
     * 更新权限
     */
    @RequiresPermissions("sys:perm:update")
    @RequestMapping("edit.do")
    @ResponseBody
    public JsonData updatePermission(SysPermission permission) {
        return sysPermissionService.updatePermission(permission);
    }

    /**
     * 获取特定权限权限
     */
    @RequiresPermissions("sys:perm:retrieve")
    @RequestMapping("search.do")
    @ResponseBody
    public JsonData selectPermission(String permName, String permCaption, String menuName) {
        return sysPermissionService.selectPermission(permName, permCaption, menuName);
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
