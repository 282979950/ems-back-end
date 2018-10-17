package com.tdmh.controller;

import com.tdmh.common.JsonData;
import com.tdmh.param.RepairOrderParam;
import com.tdmh.service.IRepairOrderService;
import com.tdmh.util.ShiroUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 维修补气管理controller
 * @author litairan on 2018/10/11.
 */
@Controller
@RequestMapping("/input/")
public class RepairOrderController {

    @Autowired
    private IRepairOrderService repairOrderService;
    /**
     * 查询所有维修单
     */
    @RequiresPermissions("repairorder:entry:visit")
    @RequestMapping(value = "listData.do")
    @ResponseBody
    public JsonData listData() {
        return repairOrderService.listData();
    }

    /**
     * 维修单录入
     */
    @RequiresPermissions("repairorder:entry:create")
    @RequestMapping(value = "add.do")
    @ResponseBody
    public JsonData createRepairOrder(RepairOrderParam param) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        param.setUpdateBy(currentEmpId);
        param.setCreateBy(currentEmpId);
        return repairOrderService.createRepairOrder(param);
    }

    /**
     * 编辑维修单
     */
    @RequiresPermissions("repairorder:entry:update")
    @RequestMapping(value = "edit.do")
    @ResponseBody
    public JsonData updateRepairOrder(RepairOrderParam param) {
        return repairOrderService.editRepairOrder(param);
    }

    /**
     * 查询维修单
     */
    @RequiresPermissions("repairorder:entry:retrieve")
    @RequestMapping(value = "search.do")
    @ResponseBody
    public JsonData searchRepairOrder(@Param("repairOrderId")String repairOrderId, @Param("userId")Integer userId, @Param("repairType")Integer repairType,
                                      @Param("empName") Integer empName) {
        return repairOrderService.searchRepairOrder(repairOrderId, userId, repairType, empName);
    }
}