package com.tdmh.controller.ems;

import com.tdmh.common.JsonData;
import com.tdmh.entity.User;
import com.tdmh.service.*;
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
    public JsonData userQueryList(Integer pageNum, Integer pageSize){
        return userService.userQueryListService(pageNum, pageSize);
    }
    /**
     * 查询产生变更记录表List
     * @return
     */
    @RequiresPermissions("querystats:accountdetail:visit")
    @RequestMapping(value = "/historyQuery.do")
    @ResponseBody
    public JsonData selectHistoryController(Integer userId, Integer pageNum, Integer pageSize){
        return userChangeService.selectUserChangeListService(userId, pageNum, pageSize);
    }
    /**
     * 查询充值记录表
     * @return
     */
    @RequiresPermissions("querystats:accountdetail:visit")
    @RequestMapping(value = "/historyQueryOrders.do")
    @ResponseBody
    public JsonData selectHistoryOrdersController(Integer userId, Integer pageNum, Integer pageSize){
        return orderService.selectHistoryOrdersService(userId, pageNum, pageSize);
    }
    /**
     * 查询补气记录表
     * @return
     */
    @RequiresPermissions("querystats:accountdetail:visit")
    @RequestMapping(value = "/historyFillGasOrder.do")
    @ResponseBody
    public JsonData selectHistoryHistoryFillGasOrderController(Integer userId, Integer pageNum, Integer pageSize){
        return fillGasService.selectHistoryFillGasOrderService(userId, pageNum, pageSize);
    }
    /**
     * 条件查询
     */
    @RequiresPermissions("querystats:accountdetail:visit")
    @RequestMapping(value = "/search.do")
    @ResponseBody
    public JsonData userQuerySearchListController(User user, Integer pageNum, Integer pageSize){
        return userService.userQuerySearchService(user, pageNum, pageSize);
    }
    /**
     * 用户卡相关记录
     * @return
     */
    @RequiresPermissions("querystats:accountdetail:visit")
    @RequestMapping(value = "/historyUserCard.do")
    @ResponseBody
    public JsonData selectHistoryUserCardQueryController(Integer userId, Integer pageNum, Integer pageSize ){
        return userService.selectHistoryUserCardQueryService(userId, pageNum, pageSize);
    }
    /**
     * 查询维修记录
     * @return
     */
    @RequiresPermissions("querystats:accountdetail:visit")
    @RequestMapping(value = "/historyRepairOrder.do")
    @ResponseBody
    public JsonData selectHistoryRepairOrderController(Integer userId, Integer pageNum, Integer pageSize){
        return repairOrderService.selectHistoryRepairOrderQueryService(userId);
    }

    /**
     * 用户卡相关记录导出
     */
    @RequiresPermissions("querystats:accountdetail:visit")
    @RequestMapping("/export.do")
    @ResponseBody
    public void exportUserQueryList(User user){
        userService.exportUserQuerySearchService(user);
    }
}
