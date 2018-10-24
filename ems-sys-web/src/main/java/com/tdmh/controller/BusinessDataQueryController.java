package com.tdmh.controller;



import com.tdmh.common.JsonData;
import com.tdmh.entity.UserOrders;
import com.tdmh.service.IOrderService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


/**
 * 营业数据查询controller
 *
 * @author qh on 2018/10/23.
 */
@Controller
@RequestMapping("/businessDataQuery/")
public class BusinessDataQueryController {
    @Resource
    private IOrderService orderService;

    @RequiresPermissions("businessDataQuery:data:visit")
    @RequestMapping(value = "/listData.do")
    @ResponseBody
    public JsonData BusinessDataQueryList(UserOrders orders){
        return orderService.BusinessDataQueryService(orders);
    }
    /**
     * 按条件查询
     */
    @RequiresPermissions("businessDataQuery:data:visit")
    @RequestMapping(value = "/search.do")
    @ResponseBody
    public JsonData BusinessDataQuerySearchList(UserOrders orders){
        return orderService.BusinessDataQuerySearchListService(orders);
    }

}
