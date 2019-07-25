package com.tdmh.entity.mapper;


import com.tdmh.entity.Coupon;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Mapper @Component
public interface CouponMapper {
    List<Coupon> selectCoupon(Coupon coupon);
    Integer insert(Coupon coupon);
    Integer updateCouponById(Coupon coupon);
    Integer deleteCouponById(Integer id);
    BigDecimal getCouponPayment(List<String> couponNumbers);
    Integer selectCouponByCouponNumber(String couponNumber);
}