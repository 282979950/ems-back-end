package com.tdmh.controller.ems;

import com.tdmh.common.JsonData;
import com.tdmh.entity.UserOrders;
import com.tdmh.param.PrePaymentParam;
import com.tdmh.service.impl.IReplaceCardService;
import com.tdmh.util.ShiroUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Lucia on 2018/10/16.
 */
@Controller
@RequestMapping("/replaceCard/")
public class ReplaceCardController {

    @Autowired
    private IReplaceCardService replaceCardService;


    @RequiresPermissions("recharge:supplement:visit")
    @RequestMapping("listData.do")
    @ResponseBody
    public JsonData getReplaceCardList() {
        return replaceCardService.getAllReplaceCardInformation();
    }

    //依据条件查询对应数据
    @RequiresPermissions("recharge:supplement:retrieve")
    @RequestMapping(value = "/search.do")
    @ResponseBody
    public JsonData selectFindListByPre(PrePaymentParam param){
        return replaceCardService.selectFindListByPre(param);
    }


    /**
     *补卡
     *
     */
    @RequiresPermissions("recharge:supplement:update")
    @RequestMapping(value = "edit.do")
    @ResponseBody
    public JsonData supplementCard(PrePaymentParam param, UserOrders userOrders) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        param.setCreateBy(currentEmpId);
        param.setUpdateBy(currentEmpId);
        userOrders.setEmployeeId(currentEmpId);
        userOrders.setCreateBy(currentEmpId);
        userOrders.setUpdateBy(currentEmpId);
        return replaceCardService.supplementCard(param,userOrders);
    }

    /**
     * 查询补卡记录
     * @param userId
     * @return
     */
    @RequiresPermissions("recharge:supplement:supList")
    @RequestMapping(value = "/List.do")
    @ResponseBody
    public JsonData searchSupList(@Param("userId") Integer userId) {
        return replaceCardService.searchSupList(userId);
    }

}