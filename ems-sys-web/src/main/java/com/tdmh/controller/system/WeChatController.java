package com.tdmh.controller.system;

import com.tdmh.common.JsonData;
import com.tdmh.param.ApplyRepairParam;
import com.tdmh.param.WxEvalParam;
import com.tdmh.service.IEvalItemService;
import com.tdmh.service.IServiceOutletService;
import com.tdmh.service.IWXNoticeService;
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

    @Autowired
    private IWXNoticeService wxNoticeService;

    @RequestMapping(value = "wxLogin")
    @ResponseBody
    public JsonData wxLogin(@Param("code") String code) {
        return wxService.wxLogin(code);
    }

    @RequestMapping(value = "getUsersByWXUserId")
    @ResponseBody
    public JsonData getUsersByWXUserId(@Param("wxUserId") String wxUserId) {
        return wxService.getUsersByWXUserId(wxUserId);
    }

    @RequestMapping(value = "getUserInfo")
    @ResponseBody
    public JsonData getUserInfo(@Param("userId") Integer userId) {
        return wxService.getUserInfo(userId);
    }

    @RequestMapping(value = "checkUserExists")
    @ResponseBody
    public JsonData checkUserExists(@Param("userId") Integer userId, @Param("userName") String userName) {
        return wxService.checkUserExists(userId, userName);
    }

    @RequestMapping(value = "bindUser")
    @ResponseBody
    public JsonData bindUser(@Param("wxUserId") String wxUserId, @Param("userId") Integer userId) {
        return wxService.bindUser(wxUserId, userId);
    }

    @RequestMapping(value = "unBindUser")
    @ResponseBody
    public JsonData unBindUser(@Param("wxUserId") String wxUserId, @Param("userId") Integer userId) {
        return wxService.unBindUser(wxUserId, userId);
    }

    @RequestMapping(value = "getWXOrders")
    @ResponseBody
    public JsonData getWXOrders(@Param("userId") Integer userId) {
        return wxService.getWXOrders(userId);
    }

    @RequestMapping(value = "recharge")
    @ResponseBody
    public JsonData recharge(@Param("wxUserId") String wxUserId, @Param("userId") Integer userId, @Param("gas") BigDecimal gas, HttpServletRequest request) {
        String ipAddress = HttpRequestUtil.getIpAddress(request);
        return wxService.recharge(wxUserId, userId, gas,ipAddress);
    }

    @RequestMapping(value = "closeOrder")
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

    @RequestMapping(value = "remindWXApplyRepair")
    @ResponseBody
    public JsonData remindWXApplyRepair(@Param("applyRepairId") Integer applyRepairId) {
        return wxService.remindWXApplyRepair(applyRepairId);
    }

    /**
     * 获取网点信息
     * @return
     */
    @GetMapping("/getAllServiceOutlet")
    @ResponseBody
    public JsonData getAllServiceOutlet(){
        return serviceOutletService.getAllSOLet();
    }

    /**
     * 获取评价项信息
     * @return
     */
    @GetMapping("/getEvalItem")
    @ResponseBody
    public JsonData getEvalItem(){
        return evalItemService.getWXEvalItem();
    }

    /**
     * 保存评价
     * @param evalParam
     * @return
     */
    @PostMapping("/saveEval")
    @ResponseBody
    public JsonData saveEval(@RequestBody WxEvalParam evalParam){
        return evalItemService.saveEval(evalParam);
    }

    @RequestMapping("/getWXNotice")
    @ResponseBody
    public JsonData getWXNotice() {
        return wxNoticeService.listData();
    }

    /**
     * 获取维修人员轨迹信息
     * @return
     */
    @RequestMapping("/getRepairManTrack")
    @ResponseBody
    public JsonData getRepairManTrack(@Param("applyRepairFlowNumber") String applyRepairFlowNumber) {
        return wxService.getRepairManTrack(applyRepairFlowNumber);
    }

    /**
     * 微信充值提气接口
     * @param icCardId
     * @return
     */
    @RequestMapping("/loadGas")
    @ResponseBody
    public JsonData loadGas(String icCardId) {
        return wxService.loadGas(icCardId);
    }

    @RequestMapping("/loadGasCallBack")
    @ResponseBody
    public JsonData loadGasCallBack(String flowNumber) {
        return wxService.loadGasCallBack(flowNumber);
    }
}
