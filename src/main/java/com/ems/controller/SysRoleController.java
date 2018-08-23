package com.ems.controller;
import com.ems.common.JsonData;
import com.ems.service.ISysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
     * @return
     */
    @RequiresPermissions("sys:role:visit")
    @RequestMapping("listData.do")
    @ResponseBody
    public JsonData getRoleList() {
        return sysRoleService.selectRole("");
    }
}
