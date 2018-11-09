package com.tdmh.controller.ems;

import com.tdmh.common.JsonData;
import com.tdmh.service.impl.IWXService;
import com.tdmh.utils.HttpRequestUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

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

    @RequestMapping(value = "getWXOrders", method = RequestMethod.GET)
    @ResponseBody
    public JsonData getWXOrders(@Param("userId") Integer userId) {
        return wxService.getWXOrders(userId);
    }

    @RequestMapping(value = "recharge", method = RequestMethod.GET)
    @ResponseBody
    public JsonData recharge(@Param("wxUserId") String wxUserId, @Param("userId") Integer userId, @Param("gas") BigDecimal gas, HttpServletRequest request) {
        String ipAddress = HttpRequestUtil.getIpAddress(request);
        return wxService.recharge(wxUserId, userId, gas,ipAddress);
    }

    @RequestMapping(value = "closeOrder", method = RequestMethod.GET)
    @ResponseBody
    public JsonData closeOrder(@Param("wxUserId") String wxUserId, @Param("userId") Integer userId, @Param("gas") BigDecimal gas, HttpServletRequest request) {
        String ipAddress = HttpRequestUtil.getIpAddress(request);
        return wxService.recharge(wxUserId, userId, gas,ipAddress);
    }

    /**
     * 接收订单回调的地址
     *
     * @return
     */
    @RequestMapping(value = "getOrderNotify")
    @ResponseBody
    public void getOrderNotify(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("接收到订单回调");
        wxService.getOrderNotify(request, response);
    }
}
