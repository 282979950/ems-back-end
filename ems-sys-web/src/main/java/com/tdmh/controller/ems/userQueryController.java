package com.tdmh.controller.ems;

import com.tdmh.common.JsonData;
import com.tdmh.entity.User;
import com.tdmh.service.impl.*;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * qh,2018-10-25
 */

@Controller
@RequestMapping("/userQuery/")
public class userQueryController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserChangeService userChangeService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IFillGasService fillGasService;
    @Autowired
    private IRepairOrderService repairOrderService;

    /**
     * 查询
     */
    @RequiresPermissions("querystats:accountdetail:visit")
    @RequestMapping(value = "/listData.do")
    @ResponseBody
    public JsonData userQueryList(){
        return userService.userQueryListService();
    }
    /**
     * 查询产生变更记录表List
     * @return
     */
    @RequiresPermissions("querystats:accountdetail:visit")
    @RequestMapping(value = "/historyQuery.do")
    @ResponseBody
    public JsonData selectHistoryController(@Param("userId") Integer userId){
        return userId==null? JsonData.fail("未获取到相关记录"):userChangeService.selectUserChangeListService(userId);
    }
    /**
     * 查询充值记录表
     * @return
     */
    @RequiresPermissions("querystats:accountdetail:visit")
    @RequestMapping(value = "/historyQueryOrders.do")
    @ResponseBody
    public JsonData selectHistoryOrdersController(@Param("userId") Integer userId){
        return orderService.selectHistoryOrdersService(userId);
    }
    /**
     * 查询补气记录表
     * @return
     */
    @RequiresPermissions("querystats:accountdetail:visit")
    @RequestMapping(value = "/historyFillGasOrder.do")
    @ResponseBody
    public JsonData selectHistoryHistoryFillGasOrderController(@Param("userId") Integer userId){
        return fillGasService.selectHistoryFillGasOrderService(userId);
    }
    /**
     * 条件查询
     */
    @RequiresPermissions("querystats:accountdetail:visit")
    @RequestMapping(value = "/search.do")
    @ResponseBody
    public JsonData userQuerySearchListController(User user){
        return userService.userQuerySearchService(user);
    }
    /**
     * 用户卡相关记录
     * @return
     */
    @RequiresPermissions("querystats:accountdetail:visit")
    @RequestMapping(value = "/historyUserCard.do")
    @ResponseBody
    public JsonData selectHistoryUserCardQueryController(@Param("userId") Integer userId){
        return userService.selectHistoryUserCardQueryService(userId);
    }
    /**
     * 查询维修记录
     * @return
     */
    @RequiresPermissions("querystats:accountdetail:visit")
    @RequestMapping(value = "/historyRepairOrder.do")
    @ResponseBody
    public JsonData selectHistoryRepairOrderController(@Param("userId") Integer userId){
        return repairOrderService.selectHistoryRepairOrderQueryService(userId);
    }

    /**
     * 用户卡相关记录导出
     */
    @RequiresPermissions("querystats:accountdetail:visit")
    @RequestMapping("/export.do")
    @ResponseBody
    public void exportUserQueryList(User user){

        ;userService.exportUserQuerySearchService(user);

    }
}