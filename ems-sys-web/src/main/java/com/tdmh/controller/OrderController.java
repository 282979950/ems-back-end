package com.tdmh.controller;

import com.tdmh.common.JsonData;
import com.tdmh.service.IOrderService;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Administrator on 2018/10/20.
 */

@Controller
@RequestMapping("/order/")
public class OrderController {

    @Autowired
    private IOrderService orderService;


    /**
     * 查询特定的已分配的发票
     * @return
     */
    @RequiresPermissions("recharge:order:visit")
    @RequestMapping("/listData.do")
    @ResponseBody
    public JsonData getAllOrderAndInvoiceList(){
        return JsonData.success();
    }
    /**
     * 查询特定的已分配的发票
     * @return
     */
    @RequiresPermissions("recharge:order:visit")
    @RequestMapping("/search.do")
    @ResponseBody
    public JsonData searchOrderAndInvoiceList(@Param("userName") String userName,@Param("iccardId") String iccardId, @Param("iccardIdentifier") String iccardIdentifier, @Param("invoiceCode") String invoiceCode, @Param("invoiceNumber") String invoiceNumber){
        return orderService.searchOrderAndInvoiceList(userName,iccardId,iccardIdentifier, invoiceCode, invoiceNumber);
    }

}
