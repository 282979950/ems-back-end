package com.tdmh.controller.ems;

import com.tdmh.common.BeUnLock;
import com.tdmh.common.JsonData;
import com.tdmh.param.BindNewCardParam;
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
    @BeUnLock
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
    @BeUnLock
    public JsonData updateRepairOrder(RepairOrderParam param) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        param.setUpdateBy(currentEmpId);
        return repairOrderService.editRepairOrder(param);
    }

    /**
     * 查询维修单
     */
    @RequiresPermissions("repairorder:entry:retrieve")
    @RequestMapping(value = "search.do")
    @ResponseBody
    public JsonData searchRepairOrder(@Param("repairOrderId")String repairOrderId, @Param("userId")Integer userId, @Param("repairType")Integer repairType,
                                      @Param("empName") String empName) {
        return repairOrderService.searchRepairOrder(repairOrderId, userId, repairType, empName);
    }

    /**
     * 查询用户及其表具信息
     */
    @RequestMapping(value = "getRepairOrderUserById.do")
    @ResponseBody
    public JsonData getRepairOrderUserById(@Param("userId")Integer userId) {
        return repairOrderService.getRepairOrderUserById(userId);
    }

    @RequestMapping(value = "hasFillGasOrderResolved.do")
    @ResponseBody
    public JsonData hasFillGasOrderResolved(@Param("userId")Integer userId, @Param("repairOrderId")String repairOrderId) {
        return repairOrderService.hasFillGasOrderResolved(userId, repairOrderId);
    }

    @RequestMapping(value = "isLatestFillGasOrder.do")
    @ResponseBody
    public JsonData isLatestFillGasOrder(@Param("id")Integer id, @Param("userId")Integer userId) {
        return repairOrderService.isLatestFillGasOrder(id, userId);
    }

    @RequestMapping(value = "getBindNewCardParamByUserId.do")
    @ResponseBody
    public JsonData getBindNewCardParamByUserId(@Param("userId")Integer userId) {
        return repairOrderService.getBindNewCardParamByUserId(userId);
    }

    @RequestMapping(value = "bindNewCard.do")
    @ResponseBody
    @BeUnLock
    public JsonData bindNewCard(BindNewCardParam param) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        param.setUpdateBy(currentEmpId);
        return repairOrderService.bindNewCard(param);
    }
}
