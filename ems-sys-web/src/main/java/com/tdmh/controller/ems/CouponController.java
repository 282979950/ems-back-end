package com.tdmh.controller.ems;

import com.tdmh.common.JsonData;
import com.tdmh.entity.Coupon;
import com.tdmh.service.ICouponService;
import com.tdmh.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author qh on 2019/07/23.
 */
@Controller
@RequestMapping("/coupon/")
public class CouponController {

    @Autowired
    private ICouponService couponService;

    /**
     * 数据查询
     * @return
     */
    @RequiresPermissions("recharge:coupon:visit")
    @RequestMapping(value = "/listData.do")
    @ResponseBody
    public JsonData selectCouponListController(Coupon coupon, Integer pageNum, Integer pageSize){
        return  couponService.selectCouponListService(coupon, pageNum, pageSize);

    }

    /**
     * 数据新增
     */
    @RequiresPermissions("recharge:coupon:create")
    @RequestMapping(value = "/add.do")
    @ResponseBody
    public JsonData addCouponController(Coupon coupon){
        String name = ShiroUtils.getPrincipal().getName();
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        return  couponService.addCouponService(coupon,name,currentEmpId);

    }

    /**
     * 数据修改
     */
    @RequiresPermissions("recharge:coupon:update")
    @RequestMapping(value = "/edit.do")
    @ResponseBody
    public JsonData editCouponController(Coupon coupon){
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        return  couponService.editCouponService(coupon,currentEmpId);
    }

    /**
     * 数据删除
     */
    @RequiresPermissions("recharge:coupon:delete")
    @RequestMapping(value = "/delete.do")
    @ResponseBody
    public JsonData deleteCouponController(Integer id){
        return  couponService.deleteCouponService(id);
    }

    /**
     * 头部筛选
     */
    @RequiresPermissions("recharge:coupon:retrieve")
    @RequestMapping(value = "/search.do")
    @ResponseBody
    public JsonData searchCouponController(Coupon coupon, Integer pageNum, Integer pageSize){
        return  couponService.selectCouponListService(coupon, pageNum, pageSize);
    }

    /**
     * 根据购气劵号码查询对应气量的面值
     */
    @RequiresPermissions("recharge:coupon:retrieve")
    @RequestMapping(value = "/getCouponPayment.do")
    @ResponseBody
    public JsonData getCouponPaymentController(String couponNumbers){
        List<String> list = Arrays.asList(couponNumbers.split(","));
        return  couponService.getCouponPaymentService(list);
        //return null;
    }
}
