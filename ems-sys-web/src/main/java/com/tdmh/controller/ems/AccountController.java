package com.tdmh.controller.ems;

import com.tdmh.common.JsonData;
import com.tdmh.param.CreateAccountParam;
import com.tdmh.service.impl.IGasPriceService;
import com.tdmh.service.impl.IMeterService;
import com.tdmh.service.impl.IUserService;
import com.tdmh.util.ShiroUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 账户管理controller
 *
 * @author litairan on 2018/8/8.
 */
@Controller
@RequestMapping("/account/")
public class AccountController {

    @Autowired
    private IMeterService meterService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IGasPriceService gasPriceService;


    /**
     * 显示已经绑定了表具的未开户的用户信息
     * @return
     */
    @RequiresPermissions("account:createAccount:visit")
    @RequestMapping(value = "/listData.do")
    @ResponseBody
    public JsonData getAllNotAccountArchives() {
        return userService.getAllNotAccountArchive();
    }

    /**
     * 获取所有表具信息
     *
     * @return
     */
    @RequestMapping(value = "/selectAllMeters", method = RequestMethod.GET)
    @ResponseBody
    public JsonData selectAll() {
        return meterService.selectAll();
    }

    /**
     * 用户开户
     *
     * @param param
     * @return
     */
    @RequiresPermissions("account:createAccount:update")
    @RequestMapping(value = "/edit.do")
    @ResponseBody
    public JsonData createAccount(CreateAccountParam param) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        param.setUpdateBy(currentEmpId);
        return userService.createAccount(param);
    }

    /**
     * 查询已经绑定了表具的未开户的用户信息
     *
     * @return
     */
    @RequiresPermissions("account:createAccount:retrieve")
    @RequestMapping(value = "/search.do")
    @ResponseBody
    public JsonData searchArchive(@Param("userId") Integer userId, @Param("userDistId") Integer userDistId, @Param("userAddress") String userAddress, @Param
            ("userType") Integer userType, @Param("userGasType") Integer userGasType) {
        return userService.searchAllNotAccountArchive(userId, userDistId, userAddress, userType, userGasType);
    }

   /**
     * 获取相关数据
     *
     * @return
     */
    @RequiresPermissions("account:createAccount:retrieve")
    @RequestMapping(value = "redCard.do")
    @ResponseBody
    public JsonData redCard(@Param("cardId") Integer cardId) {
        return userService.cardService( cardId);
    }
    /**
     * 初始化卡
     *
     * @return
     */
    @RequiresPermissions("account:createAccount:update")
    @RequestMapping(value = "initCard.do")
    @ResponseBody
    public JsonData initCard(@Param("cardId") Integer cardId,@Param("result") String result) {
        return userService.cardInitService( cardId,result);
    }

    /**
     * 依据用户户号查询用户信息
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/searchAccountById.do")
    @ResponseBody
    public JsonData searchAccountById(@Param("userId") Integer userId) {
        return userService.searchAccountById(userId);
    }
}
