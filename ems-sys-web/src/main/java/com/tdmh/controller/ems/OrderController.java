package com.tdmh.controller.ems;

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
     * 查询所有订单
     * @return
     */
    @RequiresPermissions("recharge:order:visit")
    @RequestMapping("/listData.do")
    @ResponseBody
    public JsonData getAllOrderAndInvoiceList(Integer pageNum, Integer pageSize){
        return orderService.searchOrderAndInvoiceList(null, null, null, null, null, pageNum, pageSize);
    }
    /**
     * 查询指定订单
     * @return
     */
    @RequiresPermissions("recharge:order:visit")
    @RequestMapping("/search.do")
    @ResponseBody
    public JsonData searchOrderAndInvoiceList(String userName, String iccardId, String iccardIdentifier, String invoiceCode, String invoiceNumber, Integer pageNum, Integer pageSize) {
        return orderService.searchOrderAndInvoiceList(userName, iccardId, iccardIdentifier, invoiceCode, invoiceNumber, pageNum, pageSize);
    }

    /**
     *
     * @param orderId
     * @param orderStatus
     * @return
     */
    @RequestMapping("/updateOrderStatus.do")
    @ResponseBody
    public JsonData updateOrderStatus(@Param("orderId") Integer orderId , @Param("orderStatus") Integer orderStatus){
        return orderService.updateOrderStatus(orderId , orderStatus);
    }

}
