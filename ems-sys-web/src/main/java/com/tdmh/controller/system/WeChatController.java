package com.tdmh.controller.system;

import com.tdmh.common.JsonData;
import com.tdmh.param.ApplyRepairParam;
import com.tdmh.param.WxEvalParam;
import com.tdmh.service.IEvalItemService;
import com.tdmh.service.IServiceOutletService;
import com.tdmh.service.IWXService;
import com.tdmh.utils.HttpRequestUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * @author litairan on 2018/10/22.
 */
@Controller
@RequestMapping("/wx/")
public class WeChatController {

    /**
     * 微信收银员ID
     */
    private static final Integer WX_CASHIER_ID = 1000000000;

    @Autowired
    private IWXService wxService;

    @Autowired
    private IServiceOutletService serviceOutletService;

    @Autowired
    private IEvalItemService evalItemService;

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

    @RequestMapping(value = "getWXApplyRepairByUserId")
    @ResponseBody
    public JsonData getWXApplyRepairByUserId(@Param("userId") Integer userId) {
        return wxService.getWXApplyRepairByUserId(userId);
    }

    @RequestMapping(value = "createWXApplyRepair")
    @ResponseBody
    public JsonData createWXApplyRepair(ApplyRepairParam param) {
        param.setCreateBy(WX_CASHIER_ID);
        param.setUpdateBy(WX_CASHIER_ID);
        return wxService.createWXApplyRepair(param);
    }

    @RequestMapping(value = "cancelWXApplyRepair")
    @ResponseBody
    public JsonData cancelWXApplyRepair(ApplyRepairParam param) {
        param.setUpdateBy(WX_CASHIER_ID);
        return wxService.cancelWXApplyRepair(param);
    }

    /**
     * 获取网点信息
     * @return
     */
    @GetMapping("/getAllServiceOutlet")
    public JsonData getAllServiceOutlet(){
        return serviceOutletService.getAllSOLet();
    }

    /**
     * 获取评价项信息
     * @return
     */
    @GetMapping("/getEvalItem")
    public JsonData getEvalItem(){
        return evalItemService.getWXEvalItem();
    }

    /**
     * 保存评价
     * @param evalParam
     * @return
     */
    @PostMapping("/saveEval")
    public JsonData saveEval(@RequestBody WxEvalParam evalParam){
        return evalItemService.saveEval(evalParam);
    }
}
