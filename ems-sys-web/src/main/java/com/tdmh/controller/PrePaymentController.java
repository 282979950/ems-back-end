package com.tdmh.controller;

import com.tdmh.common.JsonData;
import com.tdmh.entity.UserOrders;
import com.tdmh.param.PrePaymentParam;
import com.tdmh.service.IPrePaymentService;
import com.tdmh.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * @author Lucia on 2018/10/12.
 */
@Controller
@RequestMapping("/prePayment")
public class PrePaymentController {

    @Autowired
    private IPrePaymentService prePaymentService;

    /**
     * 显示所有可正常充值的的用户信息(包括用户，IC卡，消费信息)
     * @return
     */
    @RequiresPermissions("recharge:pre:visit111")
    @RequestMapping(value = "/listData.do")
    @ResponseBody
    public JsonData getAllOrderInformation() {
        return prePaymentService.getAllOrderInformation();
    }

    /**
     * 新增订单
     *
     */
    @RequiresPermissions("recharge:pre:update")
    @RequestMapping(value = "edit.do")
    @ResponseBody
    public JsonData createUserOrder(UserOrders userOrders , Integer iccardId, String iccardIdentifier) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        userOrders.setEmployeeId(currentEmpId);
        userOrders.setCreateBy(currentEmpId);
        userOrders.setUpdateBy(currentEmpId);
        return prePaymentService.createUserOrder(userOrders , iccardId , iccardIdentifier);
    }

    //依据条件查询对应数据
    @RequiresPermissions("recharge:pre:retrieve")
    @RequestMapping(value = "/search.do")
    @ResponseBody
    public JsonData selectFindListByPre(PrePaymentParam param){
        return prePaymentService.selectFindListByPre(param);
    }
}
