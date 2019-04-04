package com.tdmh.controller.ems;

import com.tdmh.common.JsonData;
import com.tdmh.service.IUserService;
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
    @RequiresPermissions("queryStats:exceptionQuery:visit")
    @RequestMapping("/listData.do")
    @ResponseBody
    public JsonData getAllAbnormalUserList(){
        return JsonData.success();
    }


    @RequiresPermissions("queryStats:exceptionQuery:retrieve")
    @RequestMapping("/search.do")
    @ResponseBody
    public JsonData searchAbnormalUserList(Integer notBuyDayCount, BigDecimal monthAveGas, BigDecimal monthAvePayment, Integer userDistId, String userAddress,
                                           Integer pageNum, Integer pageSize) {
        return userService.searchAbnormalUserList(notBuyDayCount, monthAveGas, monthAvePayment, userDistId, userAddress, pageNum, pageSize);
    }

    @RequiresPermissions("queryStats:exceptionQuery:export")
    @RequestMapping("/export.do")
    @ResponseBody
    public void exportAbnormalUserList(Integer notBuyDayCount, BigDecimal monthAveGas, BigDecimal monthAvePayment, Integer userDistId, String userAddress) {
        userService.exportAbnormalUserList(notBuyDayCount, monthAveGas, monthAvePayment, userDistId, userAddress);
    }
}
