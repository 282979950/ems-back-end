package com.tdmh.controller.system;

import com.tdmh.common.JsonData;
import com.tdmh.entity.ServiceOutlet;
import com.tdmh.service.IServiceOutletService;
import com.tdmh.util.ShiroUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Liuxia on 2018/11/12.
 */

@Controller
@RequestMapping("/solet/")
public class ServiceOutletController {

    @Autowired
    private IServiceOutletService serviceOutletService;

    /**
     * 显示所有的网点信息
     * @return
     */
    @RequiresPermissions("sys:solet:visit")
    @RequestMapping(value = "/listData.do")
    @ResponseBody
    public JsonData getAllSOLet() {
        return serviceOutletService.getAllSOLet();
    }

    /**
     * 新增网点
     *
     */
    @RequiresPermissions("sys:solet:create")
    @RequestMapping(value = "add.do")
    @ResponseBody
    public JsonData createSOLet(ServiceOutlet serviceOutlet) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        serviceOutlet.setCreateBy(currentEmpId);
        serviceOutlet.setUpdateBy(currentEmpId);
        return serviceOutletService.createSOLet(serviceOutlet);
    }

    /**
     * 删除网点
     */
    @RequiresPermissions("sys:solet:delete")
    @RequestMapping("delete.do")
    @ResponseBody
    public JsonData deleteSOLet(@RequestParam(value = "ids[]")List<Integer> ids) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        return serviceOutletService.deleteSOLet(ids, currentEmpId);
    }

    /**
     * 更新网点
     */
    @RequiresPermissions("sys:solet:update")
    @RequestMapping("edit.do")
    @ResponseBody
    public JsonData updateSOLet(ServiceOutlet serviceOutlet) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        serviceOutlet.setUpdateBy(currentEmpId);
        return serviceOutletService.updateSOLet(serviceOutlet);
    }

    /**
     * 获取特定网点
     */
    @RequiresPermissions("sys:solet:retrieve")
    @RequestMapping("search.do")
    @ResponseBody
    public JsonData selectSOLet(@Param("serviceOutletName") String serviceOutletName, @Param("serviceOutletAddress") String serviceOutletAddress) {
        return serviceOutletService.selectSOLet(serviceOutletName , serviceOutletAddress);
    }

}
