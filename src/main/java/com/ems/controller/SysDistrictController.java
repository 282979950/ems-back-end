package com.ems.controller;

import com.ems.entity.SysDistrict;
import com.ems.service.ISysDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by litairan litairan@whtdmh.com on 2018/7/2.
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
    public SysDistrict getDistTree() {
        return sysDistrictService.getDistRoot();
    }

    /**
     * 获取区域列表
     *
     * @return
     */
    @RequestMapping("getDistrictList.do")
    @ResponseBody
    public List<SysDistrict> getDistrictList() {
        return sysDistrictService.getDistrictList();
    }

    /**
     * 新增区域
     *
     */
    @RequestMapping("createDistrict.do")
    @ResponseBody
    public int createDistrict(SysDistrict district) {
        return sysDistrictService.createDistrict(district);
    }

    /**
     * 删除区域
     */
    @RequestMapping("deleteDistrict.do")
    @ResponseBody
    public int deleteDistrict(SysDistrict district) {
        return sysDistrictService.deleteSysDistrict(district);
    }

    /**
     * 更新区域
     */
    @RequestMapping("updateDistrict.do")
    @ResponseBody
    public int updateDistrict(SysDistrict district) {
        return sysDistrictService.updateSysDistrict(district);
    }

    /**
     * 依据区域名称查询
     */
    @RequestMapping("selectByName.do")
    @ResponseBody
    public List<SysDistrict> selectByName(String name) {
        return sysDistrictService.selectDistByName(name);
    }

    /**
     * 依据区域名称查询
     */
    @RequestMapping("selectByCode.do")
    @ResponseBody
    public List<SysDistrict> selectByCode(String code) {
        return sysDistrictService.selectDistByCode(code);
    }
}
