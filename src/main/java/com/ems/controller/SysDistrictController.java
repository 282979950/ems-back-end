package com.ems.controller;

import com.ems.common.JsonData;
import com.ems.entity.SysDistrict;
import com.ems.service.ISysDistrictService;
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
     * 获取区域树
     *
     * @return
     */
    @RequestMapping("getDistTree.do")
    @ResponseBody
    public JsonData getDistTree() {
        return sysDistrictService.getDistRoot();
    }

    /**
     * 获取区域列表
     *
     * @return
     */
    @RequestMapping("getDistrictList.do")
    @ResponseBody
    public JsonData getDistrictList() {
        return sysDistrictService.getDistrictList();
    }

    /**
     * 新增区域
     *
     */
    @RequestMapping("createDistrict.do")
    @ResponseBody
    public JsonData createDistrict(SysDistrict district) {
        return sysDistrictService.createDistrict(district);
    }

    /**
     * 删除区域
     */
    @RequestMapping("deleteDistrict.do")
    @ResponseBody
    public JsonData deleteDistrict(SysDistrict district) {
        return sysDistrictService.deleteSysDistrict(district);
    }

    /**
     * 更新区域
     */
    @RequestMapping("updateDistrict.do")
    @ResponseBody
    public JsonData updateDistrict(SysDistrict district) {
        return sysDistrictService.updateSysDistrict(district);
    }

    /**
     * 依据区域名称查询
     */
    @RequestMapping("selectByName.do")
    @ResponseBody
    public JsonData selectByName(String name) {
        return sysDistrictService.selectDistByName(name);
    }

    /**
     * 依据区域名称查询
     */
    @RequestMapping("selectByCode.do")
    @ResponseBody
    public JsonData selectByCode(String code) {
        return sysDistrictService.selectDistByCode(code);
    }
}
