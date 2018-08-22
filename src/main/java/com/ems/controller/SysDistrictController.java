package com.ems.controller;

import com.ems.common.JsonData;
import com.ems.entity.SysDistrict;
import com.ems.service.ISysDistrictService;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author litairan on 2018/7/2.
 */
@Controller
@RequestMapping("/dist/")
public class SysDistrictController {

    @Autowired
    private ISysDistrictService sysDistrictService;

    /**
     * 获取区域列表
     *
     * @return
     */
    @RequiresPermissions("sys:dist:visit")
    @RequestMapping("listData.do")
    @ResponseBody
    public JsonData getDistrictList() {
        return sysDistrictService.listData();
    }

    /**
     * 新增区域
     *
     */
    @RequiresPermissions("sys:dist:create")
    @RequestMapping(value = "add.do")
    @ResponseBody
    public JsonData createDistrict(SysDistrict district) {
        return sysDistrictService.createDistrict(district);
    }

    /**
     * 删除区域
     */
    @RequiresPermissions("sys:dist:delete")
    @RequestMapping("delete.do")
    @ResponseBody
    public JsonData deleteDistrict(SysDistrict district) {
        return sysDistrictService.deleteSysDistrict(district);
    }

    /**
     * 更新区域
     */
    @RequiresPermissions("sys:dist:update")
    @RequestMapping("edit.do")
    @ResponseBody
    public JsonData updateDistrict(SysDistrict district) {
        return sysDistrictService.updateSysDistrict(district);
    }

    /**
     * 依据区域名称查询
     */
    @RequiresPermissions("sys:dist:retrieve")
    @RequestMapping("search.do")
    @ResponseBody
    public JsonData selectDistrict(@Param("distName") String distName, @Param("distCode") String distCode) {
        return sysDistrictService.selectDistrict(distName, distCode);
    }
}
