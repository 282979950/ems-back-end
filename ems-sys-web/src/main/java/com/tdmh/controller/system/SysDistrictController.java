package com.tdmh.controller.system;

import com.tdmh.common.JsonData;
import com.tdmh.param.SysDistrictParam;
import com.tdmh.service.ISysDistrictService;
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
        return sysDistrictService.treeData();
    }

    /**
     * 新增区域
     *
     */
    @RequiresPermissions("sys:dist:create")
    @RequestMapping(value = "add.do")
    @ResponseBody
    public JsonData createDistrict(SysDistrictParam district) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        district.setCreateBy(currentEmpId);
        district.setUpdateBy(currentEmpId);
        return sysDistrictService.createDistrict(district);
    }

    /**
     * 删除区域
     */
    @RequiresPermissions("sys:dist:delete")
    @RequestMapping("delete.do")
    @ResponseBody
    public JsonData deleteDistrict(@RequestParam(value = "ids[]")List<Integer> ids) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        return sysDistrictService.deleteSysDistrict(ids, currentEmpId);
    }

    /**
     * 更新区域
     */
    @RequiresPermissions("sys:dist:update")
    @RequestMapping("edit.do")
    @ResponseBody
    public JsonData updateDistrict(SysDistrictParam district) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        district.setUpdateBy(currentEmpId);
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

    /**
     * 获取区域树的数据
     */
    @RequiresPermissions("sys:dist:retrieve")
    @RequestMapping("loadTreeData.do")
    @ResponseBody
    public JsonData loadTreeData() {
        return sysDistrictService.loadTreeData();
    }
}
