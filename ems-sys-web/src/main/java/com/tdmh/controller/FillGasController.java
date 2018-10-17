package com.tdmh.controller;

import com.tdmh.common.JsonData;
import com.tdmh.param.FillGasOrderParam;
import com.tdmh.service.IFillGasService;
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
        return fillGasService.editFillGasOrder(param);
    }

    /**
     * 查询维修单
     */
    @RequestMapping(value = "search.do")
    @ResponseBody
    public JsonData searchFillGasOrder(@Param("userId") Integer userId) {
        return fillGasService.searchFillGasOrder(userId);
    }
}