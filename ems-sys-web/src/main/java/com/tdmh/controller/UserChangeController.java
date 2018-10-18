package com.tdmh.controller;

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

import javax.annotation.Resource;

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
    public JsonData userListController(User user) {
        return userService.userChangeService(user);
    }
    /**
     * 账户结算，变更账户处理
     * @return
     */
    @RequiresPermissions("account:alter:update")
    @RequestMapping(value = "/userChangeSettlement.do")
    @ResponseBody
    public JsonData userChangeSettlement(UserChange userChange, User user ,double userMoney,double OrderSupplement){
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();

        return userChangeService.userChangeSettlementService(userChange,user,currentEmpId,userMoney,OrderSupplement);
    }
}
