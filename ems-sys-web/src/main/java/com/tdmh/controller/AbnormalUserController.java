package com.tdmh.controller;

import com.tdmh.common.JsonData;
import com.tdmh.service.IUserService;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * @author Liuxia on 2018/10/23.
 */
@Controller
@RequestMapping("/exceptionQuery")
public class AbnormalUserController {

    @Autowired
    private IUserService userService;

    /**
     * 查询所有订单
     * @return
     */
    @RequiresPermissions("querystats:abnormaluser:visit")
    @RequestMapping("/listData.do")
    @ResponseBody
    public JsonData getAllAbnormalUserList(){
        return JsonData.success();
    }


    @RequiresPermissions("querystats:abnormaluser:retrieve")
    @RequestMapping("/search.do")
    @ResponseBody
    public JsonData searchAbnormalUserList(@Param("notBuyDayCount") Integer notBuyDayCount, @Param("monthAveGas") BigDecimal monthAveGas, @Param("monthAvePayment") BigDecimal monthAvePayment, @Param("userDistId") Integer userDistId, @Param("userAddress") String userAddress){
        return userService.searchAbnormalUserList(notBuyDayCount, monthAveGas, monthAvePayment, userDistId, userAddress);
    }

    @RequiresPermissions("querystats:abnormaluser:export")
    @RequestMapping("/export.do")
    @ResponseBody
    public void exportAbnormalUserList(@Param("notBuyDayCount") Integer notBuyDayCount, @Param("monthAveGas") BigDecimal monthAveGas, @Param("monthAvePayment") BigDecimal monthAvePayment, @Param("userDistId") Integer userDistId, @Param("userAddress") String userAddress){
        userService.exportAbnormalUserList(notBuyDayCount, monthAveGas, monthAvePayment, userDistId, userAddress);
    }
}
