package com.tdmh.controller;

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
    public JsonData calAmount(@Param("userId") Integer userId, @Param("orderGas") BigDecimal orderGas, @Param("userType") Integer userType, @Param("userGasType") Integer userGasType) {
        GasPrice gasPrice = gasPriceService.findGasPriceByType(userType ,userGasType);
        BigDecimal hasUsedGasNum = gasPriceService.findHasUsedGasInYear(userId);
        if(gasPrice != null){
            BigDecimal orderPayment = CalculateUtil.gasToPayment(orderGas.add(hasUsedGasNum), gasPrice);
            BigDecimal hasOrderPayment = CalculateUtil.gasToPayment(hasUsedGasNum, gasPrice);
            return JsonData.success(orderPayment.subtract(hasOrderPayment),"查询成功");
        }
        return JsonData.successMsg("暂未配置天然气区间价格");
    }
}
