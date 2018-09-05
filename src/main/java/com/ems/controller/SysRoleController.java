package com.ems.controller;
import com.ems.common.JsonData;
import com.ems.entity.SysRole;
import com.ems.param.SysRoleParam;
import com.ems.service.ISysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author liuxia on 2018/8/23.
 */
@Controller
@RequestMapping("/role/")
public class SysRoleController {

    @Autowired
    private ISysRoleService sysRoleService;

    /**
     * 获取角色列表
     *
     * @return JsonData
     */
    @RequiresPermissions("sys:role:visit")
    @RequestMapping("listData.do")
    @ResponseBody
    public JsonData getRoleList() {
        return sysRoleService.selectRole("");
    }

    /**
     * 新增权限
     *
     */
    @RequiresPermissions("sys:role:create")
    @RequestMapping(value = "add.do")
    @ResponseBody
    public JsonData createRole(SysRoleParam roleParam) {
        return sysRoleService.createRole(roleParam);
    }

    /**
     * 删除权限
     */
    @RequiresPermissions("sys:role:delete")
    @RequestMapping("delete.do")
    @ResponseBody
    public JsonData deleteRole(@RequestParam(value = "ids[]")List<Integer> ids) {
        return sysRoleService.deleteRole(ids);
    }

    /**
     * 更新角色
     */
    @RequiresPermissions("sys:role:update")
    @RequestMapping("edit.do")
    @ResponseBody
    public JsonData updateRole(SysRoleParam roleParam) {
        return sysRoleService.updateRole(roleParam);
    }

    /**
     * 获取特定角色
     */
    @RequiresPermissions("sys:role:retrieve")
    @RequestMapping("search.do")
    @ResponseBody
    public JsonData selectRole(String roleName) {
       return sysRoleService.selectRole(roleName);
    }
}
