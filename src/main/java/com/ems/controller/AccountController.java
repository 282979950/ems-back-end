package com.ems.controller;

import com.ems.common.JsonData;
import com.ems.entity.SysDictionary;
import com.ems.param.CreateAccountParam;
import com.ems.service.IMeterService;
import com.ems.service.IUserService;
import com.ems.service.SysDictionaryService;
import com.ems.utils.CalculateUtil;
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
    @RequiresPermissions("account:createAccount:edit")
    @RequestMapping(value = "/edit.do")
    @ResponseBody
    public JsonData createAccount(CreateAccountParam param) {
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
    public JsonData searchArchive(@Param("userId") Integer userId, @Param("distName") String distName, @Param("userAddress") String userAddress, @Param
            ("userType") Integer userType, @Param("userGasType") Integer userGasType) {
        return userService.searchAllNotAccountArchive(userId, distName, userAddress, userType, userGasType);
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
}
