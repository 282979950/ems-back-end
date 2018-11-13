package com.tdmh.controller.vprs;

import com.tdmh.common.JsonData;
import com.tdmh.param.ApplyRepairParam;
import com.tdmh.service.IApplyRepairService;
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
 * @author litairan on 2018/11/12.
 */
@Controller
@ResponseBody
@RequestMapping("/entryApplyRepair/")
public class ApplyRepairController {
    @Autowired
    private IApplyRepairService applyRepairService;

    /**
     * 获取所有的报修单
     *
     * @return
     */
    @RequiresPermissions("applyRepair:entryApplyRepair:visit")
    @RequestMapping("listData.do")
    public JsonData listData() {
        return applyRepairService.listData();
    }

    /**
     * 新增报修单
     *
     */
    @RequiresPermissions("applyRepair:entryApplyRepair:create")
    @RequestMapping(value = "add.do")
    public JsonData create(ApplyRepairParam param) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        param.setCreateBy(currentEmpId);
        param.setUpdateBy(currentEmpId);
        return applyRepairService.create(param);
    }

    /**
     * 删除报修单
     */
    @RequiresPermissions("applyRepair:entryApplyRepair:delete")
    @RequestMapping("delete.do")
    public JsonData delete(@RequestParam(value = "ids[]") List<Integer> ids) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        return applyRepairService.delete(ids, currentEmpId);
    }

    /**
     * 更新报修单
     */
    @RequiresPermissions("applyRepair:entryApplyRepair:update")
    @RequestMapping("edit.do")
    public JsonData update(ApplyRepairParam param) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        param.setUpdateBy(currentEmpId);
        return applyRepairService.update(param);
    }

    /**
     * 查询报修单
     */
    @RequiresPermissions("applyRepair:entryApplyRepair:retrieve")
    @RequestMapping("search.do")
    public JsonData search(@Param("userId") Integer userId, @Param("userName") String userName, @Param("userPhone") String userPhone,
                           @Param("userTelPhone") String userTelPhone) {
        return applyRepairService.search(userId, userName, userPhone, userTelPhone);
    }

    /**
     * 获取报修用户信息
     */
    @RequiresPermissions("applyRepair:entryApplyRepair:retrieve")
    @RequestMapping("getApplyRepairUserInfoById.do")
    public JsonData getApplyRepairUserInfoById(@Param("userId") Integer userId) {
        return applyRepairService.getApplyRepairUserInfoById(userId);
    }
}
