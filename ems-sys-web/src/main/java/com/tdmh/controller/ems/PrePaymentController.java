package com.tdmh.controller.ems;

import com.tdmh.common.BeUnLock;
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
    @RequiresPermissions("recharge:pre:visit")
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
    @BeUnLock
    public JsonData createUserOrder(UserOrders userOrders, String userType) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        String name = ShiroUtils.getPrincipal().getName();
        userOrders.setEmployeeId(currentEmpId);
        userOrders.setCreateBy(currentEmpId);
        userOrders.setUpdateBy(currentEmpId);
        return prePaymentService.createUserOrder(userOrders,name,userType);
}

    //依据条件查询对应数据
    @RequiresPermissions("recharge:pre:retrieve")
    @RequestMapping(value = "/search.do")
    @ResponseBody
    public JsonData selectFindListByPre(PrePaymentParam param, Integer pageNum, Integer pageSize){
        return prePaymentService.selectFindListByPre(param, pageNum, pageSize);
    }


    //判断数据库中IC卡识别号与IC卡编号是否一致
    @RequestMapping(value = "/verifyCard.do")
    @ResponseBody
    public JsonData verifyCard(PrePaymentParam param){
        return prePaymentService.verifyCard(param);
    }

    //加购调用
    @RequestMapping(value = "/addGas.do")
    @ResponseBody
    public JsonData AddGasController(UserOrders userOrders, String userType){
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        String name = ShiroUtils.getPrincipal().getName();
        userOrders.setEmployeeId(currentEmpId);
        userOrders.setCreateBy(currentEmpId);
        userOrders.setUpdateBy(currentEmpId);
        userOrders.setOrderType(7);
        return prePaymentService.createUserOrder(userOrders,name,userType);
    }
}
