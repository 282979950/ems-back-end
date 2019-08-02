package com.tdmh.controller.ems;

import com.tdmh.common.JsonData;
import com.tdmh.param.CreateAccountParam;
import com.tdmh.param.UserParam;
import com.tdmh.service.IGasPriceService;
import com.tdmh.service.IMeterService;
import com.tdmh.service.IUserService;
import com.tdmh.util.ShiroUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
//    @RequiresPermissions("account:createAccount:visit")
//    @RequestMapping(value = "/listData.do")
//    @ResponseBody
//    public JsonData getAllNotAccountArchives(Integer pageNum, Integer pageSize) {
//        return userService.getAllNotAccountArchive(pageNum, pageSize);
//    }

    /**
     * 获取所有表具信息
     *
     * @return
     */
    @RequestMapping(value = "/selectAllMeters")
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
    @RequestMapping(value = "/createAccount.do")
    @ResponseBody
    public JsonData createAccount(CreateAccountParam param) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        param.setUpdateBy(currentEmpId);
        return userService.createAccount(param);
    }

    /**
     * 用户发卡
     *
     * @param param
     * @return
     */
    @RequiresPermissions("account:createAccount:update")
    @RequestMapping(value = "/bindCard.do")
    @ResponseBody
    public JsonData bindCard(CreateAccountParam param) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        param.setUpdateBy(currentEmpId);
        return userService.bindCard(param);
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
            ("userType") Integer userType, @Param("userGasType") Integer userGasType, Integer pageNum, Integer pageSize) {
        return userService.searchAllNotAccountArchive(userId, userDistId, userAddress, userType, userGasType, pageNum, pageSize);
    }

   /**
     * 获取相关数据
     *
     * @return
     */
    @RequiresPermissions("account:createAccount:retrieve")
    @RequestMapping(value = "readCard.do")
    @ResponseBody
    public JsonData readCard(@Param("cardId") String cardId) {
        return userService.cardService(cardId);
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

    @RequiresPermissions("account:createAccount:visit")
    @RequestMapping(value = "/listData.do")
    @ResponseBody
    public JsonData listData(Integer userId, Integer userDistId, String userAddress, Integer userType, Integer userStatus, String meterCode,
                             String cardIdentifier, Integer pageNum, Integer pageSize) {
        return userService.listData(userId, userDistId, userAddress, userType, userStatus, meterCode, cardIdentifier, pageNum, pageSize);
    }

    @RequiresPermissions("account:createAccount:update")
    @RequestMapping(value = "/edit.do")
    @ResponseBody
    public JsonData edit(UserParam userParam) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        userParam.setUpdateBy(currentEmpId);
        return userService.edit(userParam);
    }

    @RequestMapping(value = "/checkFreeGasFlag.do")
    @ResponseBody
    public JsonData checkFreeGasFlag(Integer userId) {
        return userService.checkFreeGasFlag(userId);
    }
}
