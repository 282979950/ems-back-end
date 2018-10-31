package com.tdmh.controller;



import com.tdmh.common.JsonData;
import com.tdmh.entity.UserOrders;
import com.tdmh.service.IOrderService;
import com.tdmh.utils.StringUtils;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 数据导出
     * @param startTime 起始时间
     * @param endTime 结束时间
     * @param empId  操作人
     * @param accountState 账务状态
     * @return
     */
    @RequiresPermissions("businessDataQuery:data:visit")
    @RequestMapping("/export.do")
    @ResponseBody
    public JsonData exportBusinessDataQueryList(@Param("startTime") String startTime, @Param("endTime") String endTime,@Param("empId") String empId,@Param("accountState") String accountState){
        UserOrders orders = new UserOrders();
        if(StringUtils.isNotBlank(startTime)){
            orders.setStartTime(startTime);
        }
        if(StringUtils.isNotBlank(endTime)){
            orders.setEndTime(endTime);
        }
        if(!empId.equals("null")){
            orders.setEmpId(new Integer(empId));
        }
        if(!accountState.equals("null")){
            orders.setAccountState(new Integer(accountState));
        }
        if((StringUtils.isBlank(startTime) && StringUtils.isNotBlank(endTime))||
                StringUtils.isNotBlank(startTime) && StringUtils.isBlank(endTime)){

            return JsonData.fail("导出有误，导出某时间段请输入起始日期和截止日期，且起始时间小于截止时间");

        }
        orderService.exportBusinessDataQueryListService(orders);
        return null;

    }

}
