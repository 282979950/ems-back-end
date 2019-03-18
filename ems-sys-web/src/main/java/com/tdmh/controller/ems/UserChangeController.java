package com.tdmh.controller.ems;

import com.tdmh.common.BeUnLock;
import com.tdmh.common.JsonData;
import com.tdmh.entity.User;
import com.tdmh.entity.UserChange;
import com.tdmh.service.IUserChangeService;
import com.tdmh.service.IUserService;
import com.tdmh.util.ShiroUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * 账户变更controller
 *
 * @author qh on 2018/10/16.
 */
@Controller
@RequestMapping("/alter/")
public class UserChangeController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserChangeService userChangeService;

    /**
     * 显示已开户的用户相关信息
     * @return
     */
    @RequiresPermissions("account:alter:visit")
    @RequestMapping(value = "/listData.do")
    @ResponseBody
    public JsonData userListController(User user,Integer pageNum, Integer pageSize) {
        return userService.userChangeService(user,pageNum,pageSize);
    }
    /**
     * 账户结算，变更账户处理
     * @return
     */
    @RequiresPermissions("account:alter:update")
    @RequestMapping(value = "/userChangeSettlement.do")
    @ResponseBody
    @BeUnLock
    public JsonData userChangeSettlement(User user,UserChange userChange, double userMoney,double OrderSupplement){
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();

        return userChangeService.userChangeSettlementService(userChange,user,currentEmpId,userMoney,OrderSupplement);
    }
    /**
     * 账户销户处理
     */
    @RequiresPermissions("account:alter:update")
    @RequestMapping(value = "/userEliminationHead.do")
    @ResponseBody
    public JsonData userEliminationHead(User user,BigDecimal userMoney,BigDecimal OrderSupplement, int flage){
        Integer Id = ShiroUtils.getPrincipal().getId();

        return user==null?JsonData.fail("未获取到该条数据先关信息，请检查数据或联系管理员"):userChangeService.userEliminationHeadService(user,userMoney,OrderSupplement,flage,Id);
    }
    /**
     * 查询产生变更记录表List
     * @return
     */
    @RequiresPermissions("account:alter:visit")
    @RequestMapping(value = "/userChangeList.do")
    @ResponseBody
    public JsonData selectUserChangeListController(@Param("userId") Integer userId){
        return userId==null? JsonData.fail("未获取到相关记录"):userChangeService.selectUserChangeListService(userId);
    }
    /**
     * 显示已开户的用户相关信息（筛选查询）
     * @return
     */
    @RequiresPermissions("account:alter:visit")
    @RequestMapping(value = "/search.do")
    @ResponseBody
    public JsonData userSearchController(User user,Integer pageNum, Integer pageSize) {
        return userService.userChangeService(user,pageNum,pageSize);
    }
}
