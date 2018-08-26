package com.ems.controller;

import com.ems.common.JsonData;
import com.ems.param.CreateAccountParam;
import com.ems.param.CreateArchiveParam;
import com.ems.service.IMeterService;
import com.ems.service.IUserService;
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
     * 用户建档
     *
     * @return
     */
    @RequestMapping(value = "/createArchive", method = RequestMethod.GET)
    @ResponseBody
    public JsonData createArchive(CreateArchiveParam param) {
        return userService.createArchive(param);
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
}
