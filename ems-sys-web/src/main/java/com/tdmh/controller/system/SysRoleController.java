package com.tdmh.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.tdmh.common.JsonData;
import com.tdmh.param.SysRoleParam;
import com.tdmh.service.ISysRoleService;
import com.tdmh.util.ShiroUtils;
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
    public JsonData getRoleList(Integer pageNum, Integer pageSize) {
        return sysRoleService.selectRole(null, pageNum, pageSize);
    }

    /**
     * 获取角色列表
     *
     * @return JsonData
     */
    @RequiresPermissions("sys:role:visit")
    @RequestMapping("getAllRole.do")
    @ResponseBody
    public JsonData getAllRole() {
        return sysRoleService.getAllRole();
    }

    /**
     * 新增权限
     *
     */
    @RequiresPermissions("sys:role:create")
    @RequestMapping(value = "add.do")
    @ResponseBody
    public JsonData createRole(String json) {
        SysRoleParam roleParam = JSONObject.toJavaObject(JSONObject.parseObject(json), SysRoleParam.class);
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        roleParam.setCreateBy(currentEmpId);
        roleParam.setUpdateBy(currentEmpId);
        return sysRoleService.createRole(roleParam);
    }

    /**
     * 删除权限
     */
    @RequiresPermissions("sys:role:delete")
    @RequestMapping("delete.do")
    @ResponseBody
    public JsonData deleteRole(@RequestParam(value = "ids[]")List<Integer> ids) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        return sysRoleService.deleteRole(ids, currentEmpId);
    }

    /**
     * 更新角色
     */
    @RequiresPermissions("sys:role:update")
    @RequestMapping("edit.do")
    @ResponseBody
    public JsonData updateRole(String json) {
        SysRoleParam roleParam = JSONObject.toJavaObject(JSONObject.parseObject(json), SysRoleParam.class);
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        roleParam.setUpdateBy(currentEmpId);
        return sysRoleService.updateRole(roleParam);
    }

    /**
     * 获取特定角色
     */
    @RequiresPermissions("sys:role:retrieve")
    @RequestMapping("search.do")
    @ResponseBody
    public JsonData selectRole(String roleName, Integer pageNum, Integer pageSize) {
       return sysRoleService.selectRole(roleName, pageNum, pageSize);
    }
}
