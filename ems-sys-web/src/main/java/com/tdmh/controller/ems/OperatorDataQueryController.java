package com.tdmh.controller.ems;


import com.tdmh.common.JsonData;
import com.tdmh.param.OperatorDataQuery;
import com.tdmh.service.IOrderService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


/**
 * 操作员日常数据查询controller
 *
 * @author Auser_Qh
 * 2019/08/09
 */
@Controller
@RequestMapping("/operatorDataQuery/")
public class OperatorDataQueryController {
    @Resource
    private IOrderService orderService;

    @RequiresPermissions("queryStats:operatorDataQuery:visit")
    @RequestMapping(value = "/listData.do")
    @ResponseBody
    public JsonData OperatorDataQueryList(OperatorDataQuery dataQuery, Integer pageNum, Integer pageSize){
        return orderService.OperatorDataQueryService(dataQuery, pageNum, pageSize);
    }

    @RequiresPermissions("queryStats:operatorDataQuery:retrieve")
    @RequestMapping(value = "/search.do")
    @ResponseBody
    public JsonData OperatorDataQueryListSearch(OperatorDataQuery dataQuery, Integer pageNum, Integer pageSize){
        return orderService.OperatorDataQueryService(dataQuery, pageNum, pageSize);
    }
}
