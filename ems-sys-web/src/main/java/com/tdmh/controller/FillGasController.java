package com.tdmh.controller;

import com.tdmh.common.JsonData;
import com.tdmh.param.FillGasOrderParam;
import com.tdmh.service.IFillGasService;
import com.tdmh.util.ShiroUtils;
import org.apache.ibatis.annotations.Param;
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
    public JsonData listData() {
        return fillGasService.listData();
    }

    /**
     * 编辑维修单
     */
    @RequestMapping(value = "edit.do")
    @ResponseBody
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
    public JsonData searchFillGasOrder(@Param("repairOrderId") String repairOrderId, @Param("userId") Integer userId,
                                       @Param("fillGasOrderType") Integer fillGasOrderType) {
        return fillGasService.searchFillGasOrder(repairOrderId, userId ,fillGasOrderType);
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