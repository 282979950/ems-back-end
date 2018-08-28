package com.ems.controller;

import com.ems.common.JsonData;
import com.ems.param.CreateAccountParam;
import com.ems.service.IMeterService;
import com.ems.service.IUserService;
import org.apache.ibatis.annotations.Param;
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
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private IMeterService meterService;

    @Autowired
    private IUserService userService;


    /**
     * 显示已经绑定了表具的未开户的用户信息
     * @return
     */
    @RequestMapping(value = "/listData.do")
    @ResponseBody
    public JsonData getAllNotAccountArchives() {
        return userService.searchArchive(null, null, null, null, null,  2);
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
    @RequestMapping(value = "/createAccount", method = RequestMethod.GET)
    @ResponseBody
    public JsonData createAccount(CreateAccountParam param) {
        return userService.createAccount(param);
    }

    /**
     * 查询已经绑定了表具的未开户的用户信息
     *
     * @return
     */
    @RequestMapping(value = "/search.do")
    @ResponseBody
    public JsonData searchArchive(@Param("userId") Integer userId, @Param("distName") String distName, @Param("userAddress") String userAddress, @Param
            ("userType") Integer userType, @Param("userGasType") Integer userGasType) {
        return userService.searchArchive(userId, distName, userAddress, userType, userGasType, 2);
    }
}
