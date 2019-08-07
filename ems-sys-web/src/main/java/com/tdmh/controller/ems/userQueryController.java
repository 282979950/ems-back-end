package com.tdmh.controller.ems;

import com.tdmh.common.JsonData;
import com.tdmh.entity.User;
import com.tdmh.param.UserParam;
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
    @Autowired
    private IPreStrikeService preStrikeService;

    /**
     * 查询
     */
    @RequiresPermissions("queryStats:userQuery:visit")
    @RequestMapping(value = "/listData.do")
    @ResponseBody
    public JsonData userQueryList(Integer pageNum, Integer pageSize){
        return userService.userQueryListService(pageNum, pageSize);
    }
    /**
     * 查询产生变更记录表List
     * @return
     */
    @RequiresPermissions("queryStats:userQuery:historyQuery")
    @RequestMapping(value = "/historyQuery.do")
    @ResponseBody
    public JsonData selectHistoryController(Integer userId){
        return userChangeService.selectUserChangeListService(userId);
    }
    /**
     * 查询充值记录表
     * @return
     */
    @RequiresPermissions("queryStats:userQuery:historyOrderQuery")
    @RequestMapping(value = "/historyOrderQuery.do")
    @ResponseBody
    public JsonData selectHistoryOrdersController(Integer userId){
        return orderService.selectHistoryOrdersService(userId);
    }
    /**
     * 查询补气记录表
     * @return
     */
    @RequiresPermissions("queryStats:userQuery:historyFillGasOrder")
    @RequestMapping(value = "/historyFillGasOrder.do")
    @ResponseBody
    public JsonData selectHistoryHistoryFillGasOrderController(Integer userId){
        return fillGasService.selectHistoryFillGasOrderService(userId);
    }
    /**
     * 条件查询
     */
    @RequiresPermissions("queryStats:userQuery:retrieve")
    @RequestMapping(value = "/search.do")
    @ResponseBody
    public JsonData userQuerySearchListController(UserParam user, Integer pageNum, Integer pageSize){
        return userService.userQuerySearchService(user, pageNum, pageSize);
    }
    /**
     * 用户卡相关记录
     * @return
     */
    @RequiresPermissions("queryStats:userQuery:historyUserCard")
    @RequestMapping(value = "/historyUserCard.do")
    @ResponseBody
    public JsonData selectHistoryUserCardQueryController(Integer userId){
        return userService.selectHistoryUserCardQueryService(userId);
    }
    /**
     * 查询维修记录
     * @return
     */
    @RequiresPermissions("queryStats:userQuery:historyRepairOrder")
    @RequestMapping(value = "/historyRepairOrder.do")
    @ResponseBody
    public JsonData selectHistoryRepairOrderController(Integer userId){
        return repairOrderService.selectHistoryRepairOrderQueryService(userId);
    }
    /**
     * 查询审核的冲账记录
     * @return
     */
    @RequiresPermissions("queryStats:userQuery:userStrike")
    @RequestMapping(value = "/historyUserStrike.do")
    @ResponseBody
    public JsonData selectHistoryStrikeNucleusController(Integer userId){
        return preStrikeService.selectHistoryStrikeNucleusService(userId);
    }

    /**
     * 用户卡相关记录导出
     */
    @RequiresPermissions("queryStats:userQuery:export")
    @RequestMapping("/export.do")
    @ResponseBody
    public JsonData exportUserQueryList(UserParam user){
        return userService.exportUserQuerySearchService(user);
    }

    @RequiresPermissions("queryStats:userQuery:export")
    @RequestMapping("/exportWithPageInfo.do")
    @ResponseBody
    public JsonData exportUserQueryWithPageInfo(UserParam user, Integer pageNum, Integer pageSize){
        return userService.exportUserQueryWithPageInfo(user, pageNum, pageSize);
    }
    @RequiresPermissions("queryStats:userQuery:userMeterType")
    @RequestMapping("/userMeterType.do")
    @ResponseBody
    public JsonData selectUserMeterTypeController(Integer userId){
        return userService.selectUserMeterTypeService(userId);
    }
    @RequestMapping("/ExportUserQuery.do")
    @ResponseBody
    public JsonData ExportUserQueryController(User user){
        return userService.ExportUserQueryService(user);
    }
}
