package com.tdmh.controller;

import com.tdmh.common.JsonData;
import com.tdmh.entity.SysDictionary;
import com.tdmh.param.CreateAccountParam;
import com.tdmh.service.IMeterService;
import com.tdmh.service.IUserService;
import com.tdmh.service.SysDictionaryService;
import com.tdmh.util.CalculateUtil;
import com.tdmh.util.ShiroUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

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

    @Resource
    private SysDictionaryService sysDictionaryService;


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

    @RequestMapping(value = "/calAmount.do")
    @ResponseBody
    public JsonData calAmount(@Param("orderGas") Integer orderGas) {
        BigDecimal orderPayment = null;
        List<SysDictionary> list = sysDictionaryService.findListByTypeOnPc("gas_price");
        if(null!=list&&list.size()>0) {
            CalculateUtil.callist = CalculateUtil.transfer(list);
            orderPayment = CalculateUtil.gasToPayment(BigDecimal.valueOf(orderGas));
            return JsonData.success(orderPayment,"查询成功");
        }
        return JsonData.successMsg("暂未配置天然气区间价格");
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
