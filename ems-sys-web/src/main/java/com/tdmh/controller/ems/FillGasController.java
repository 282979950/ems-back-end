package com.tdmh.controller.ems;

import com.tdmh.common.BeUnLock;
import com.tdmh.common.JsonData;
import com.tdmh.param.FillGasOrderParam;
import com.tdmh.service.IFillGasService;
import com.tdmh.util.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author litairan on 2018/10/17.
 */
@Controller
@RequestMapping("/fillGas/")
public class FillGasController {

    @Autowired
    private IFillGasService fillGasService;

    @RequestMapping(value = "listData.do")
    @ResponseBody
    public JsonData listData(Integer pageNum, Integer pageSize) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        String isAdmin =ShiroUtils.getPrincipal().getLoginName();
        Integer userType = ShiroUtils.getPrincipal().getUserType();
        return fillGasService.listData(pageNum, pageSize, currentEmpId, isAdmin, userType);
    }

    /**
     * 编辑维修单
     */
    @RequestMapping(value = "edit.do")
    @ResponseBody
    @BeUnLock
    public JsonData editFillGasOrder(FillGasOrderParam param) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        param.setUpdateBy(currentEmpId);
        return fillGasService.editFillGasOrder(param);
    }

    /**
     * 查询维修单
     */
    @RequestMapping(value = "search.do")
    @ResponseBody
    public JsonData searchFillGasOrder(String repairOrderId, Integer userId, Integer fillGasOrderType, Integer pageNum, Integer pageSize) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        String isAdmin =ShiroUtils.getPrincipal().getLoginName();
        Integer userType = ShiroUtils.getPrincipal().getUserType();
        return fillGasService.searchFillGasOrder(repairOrderId, userId ,fillGasOrderType, pageNum, pageSize, currentEmpId, isAdmin, userType);
    }

    @RequestMapping(value = "getFlowNum.do")
    @ResponseBody
    public JsonData getFlowNum() {
        return fillGasService.getFlowNum();
    }

    @RequestMapping(value = "getServiceTimesByUserId.do")
    @ResponseBody
    public JsonData getServiceTimesByUserId(Integer userId) {
        return fillGasService.getServiceTimesByUserId(userId);
    }
}