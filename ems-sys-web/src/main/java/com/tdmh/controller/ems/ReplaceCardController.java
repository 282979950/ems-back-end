package com.tdmh.controller.ems;

import com.tdmh.common.BeUnLock;
import com.tdmh.common.JsonData;
import com.tdmh.entity.UserOrders;
import com.tdmh.param.PrePaymentParam;
import com.tdmh.service.IReplaceCardService;
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


    @RequiresPermissions("recharge:replaceCard:visit")
    @RequestMapping("listData.do")
    @ResponseBody
    public JsonData getReplaceCardList(Integer pageNum, Integer pageSize) {
        return replaceCardService.getAllReplaceCardInformation(pageNum, pageSize);
    }

    //依据条件查询对应数据
    @RequiresPermissions("recharge:replaceCard:retrieve")
    @RequestMapping(value = "/search.do")
    @ResponseBody
    public JsonData selectFindListByPre(PrePaymentParam param, Integer pageNum, Integer pageSize){
        return replaceCardService.selectFindListByPre(param, pageNum, pageSize);
    }


    /**
     *补卡
     *
     */
    @RequiresPermissions("recharge:replaceCard:update")
    @RequestMapping(value = "edit.do")
    @ResponseBody
    @BeUnLock
    public JsonData supplementCard(PrePaymentParam param, UserOrders userOrders, String userType) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        String name = ShiroUtils.getPrincipal().getName();
        param.setCreateBy(currentEmpId);
        param.setUpdateBy(currentEmpId);
        userOrders.setEmployeeId(currentEmpId);
        userOrders.setCreateBy(currentEmpId);
        userOrders.setUpdateBy(currentEmpId);
        return replaceCardService.supplementCard(param,userOrders,name,userType);
    }

    /**
     * 查询补卡记录
     * @param userId
     * @return
     */
    @RequiresPermissions("recharge:replaceCard:history")
    @RequestMapping(value = "/history.do")
    @ResponseBody
    public JsonData searchSupList(@Param("userId") Integer userId) {
        return replaceCardService.searchSupList(userId);
    }

}
