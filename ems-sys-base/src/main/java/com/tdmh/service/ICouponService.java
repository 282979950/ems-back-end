package com.tdmh.service;

import com.tdmh.common.JsonData;
import com.tdmh.entity.Coupon;

import java.util.List;

/**
 * 用户服务接口
 *
 * @author qh on 2019/07/23.
 */
public interface ICouponService {

    JsonData selectCouponListService(Coupon coupon, Integer pageNum, Integer pageSize);
    JsonData addCouponService(Coupon coupon,String name,Integer currentEmpId);
    JsonData editCouponService(Coupon coupon,Integer currentEmpId);
    JsonData deleteCouponService(Integer id);
    JsonData getCouponPaymentService(List<String> couponNumbers);
}
