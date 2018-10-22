package com.tdmh.controller;

import com.tdmh.common.JsonData;
import com.tdmh.service.IWXService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author litairan on 2018/10/22.
 */
@Controller
@RequestMapping("/wx/")
public class WeChatController {

    @Autowired
    private IWXService wxService;

    @RequestMapping(value = "wxLogin", method = RequestMethod.GET)
    @ResponseBody
    public JsonData wxLogin(@Param("code") String code) {
        return wxService.wxLogin(code);
    }

    @RequestMapping(value = "getUsersByWXUserId", method = RequestMethod.GET)
    @ResponseBody
    public JsonData getUsersByWXUserId(@Param("wxUserId") String wxUserId) {
        return wxService.getUsersByWXUserId(wxUserId);
    }

    @RequestMapping(value = "getUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public JsonData getUserInfo(@Param("userId") Integer userId) {
        return wxService.getUserInfo(userId);
    }

    @RequestMapping(value = "checkUserExists", method = RequestMethod.GET)
    @ResponseBody
    public JsonData checkUserExists(@Param("userId") Integer userId, @Param("userName") String userName) {
        return wxService.checkUserExists(userId, userName);
    }

    @RequestMapping(value = "bindUser", method = RequestMethod.GET)
    @ResponseBody
    public JsonData bindUser(@Param("wxUserId") String wxUserId, @Param("userId") Integer userId) {
        return wxService.bindUser(wxUserId, userId);
    }

    @RequestMapping(value = "unBindUser", method = RequestMethod.GET)
    @ResponseBody
    public JsonData unBindUser(@Param("wxUserId") String wxUserId, @Param("userId") Integer userId) {
        return wxService.unBindUser(wxUserId, userId);
    }
}
