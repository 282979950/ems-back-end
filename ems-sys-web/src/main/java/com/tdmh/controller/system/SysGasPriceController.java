package com.tdmh.controller.system;

import com.tdmh.common.JsonData;
import com.tdmh.entity.GasPrice;
import com.tdmh.param.GasPriceParam;
import com.tdmh.service.IGasPriceService;
import com.tdmh.util.CalculateUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * @author Administrator on 2018/10/11.
 */
@Controller
@RequestMapping("/gasPrice/")
public class SysGasPriceController {
    @Autowired
    private IGasPriceService gasPriceService;
    /**
     * 获取价格列表
     *
     * @return
     */
    @RequiresPermissions("sys:gasPrice:visit")
    @RequestMapping("listData.do")
    @ResponseBody
    public JsonData getGasPriceList() {
        return gasPriceService.listAllGasPrice();
    }

    @RequiresPermissions("sys:gasPrice:update")
    @RequestMapping("edit.do")
    @ResponseBody
    public JsonData updateGasPrice(GasPriceParam param) {
        return gasPriceService.updateGasPrice(param);
    }

    @RequestMapping(value = "/calAmount.do")
    @ResponseBody
    public JsonData calAmount(@Param("userId") Integer userId, @Param("orderGas") BigDecimal orderGas) {
        return gasPriceService.calAmount(userId,orderGas);
    }
}
