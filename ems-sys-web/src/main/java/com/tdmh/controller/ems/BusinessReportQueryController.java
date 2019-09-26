package com.tdmh.controller.ems;


import com.tdmh.common.JsonData;
import com.tdmh.param.OrderParam;
import com.tdmh.service.IOrderService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;


/**
 * 营业数据报表查询controller
 *
 * @author Auser_Qh
 * 2019/06/26
 */
@Controller
@RequestMapping("/businessReportQuery/")
public class BusinessReportQueryController {
    @Resource
    private IOrderService orderService;

//    @RequiresPermissions("querystats:businessReportQuery:visit")
//    @RequestMapping(value = "/listData.do")
//    @ResponseBody
//    public JsonData ReportBusinessDataQueryList(OrderParam orders, Integer pageNum, Integer pageSize){
//        return orderService.ReportBusinessDataQueryService(orders,pageNum,pageSize);
//    }
    /**
     * 按条件查询
     */
    @RequiresPermissions("querystats:businessReportQuery:visit")
    @RequestMapping(value = "/search.do")
    @ResponseBody
    public JsonData ReportBusinessDataQuerySearchList(String empName, String startDate, String endDate, Integer pageNum, Integer pageSize){
        return orderService.ReportBusinessDataQueryService(empName, startDate, endDate, pageNum, pageSize);
    }
}
