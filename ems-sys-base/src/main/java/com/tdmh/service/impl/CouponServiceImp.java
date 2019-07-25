package com.tdmh.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdmh.common.JsonData;
import com.tdmh.entity.Coupon;
import com.tdmh.entity.mapper.CouponMapper;
import com.tdmh.service.ICouponService;
import com.tdmh.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 优惠券实现类
 *
 * @author qh on 2019/07/23.
 */
@Service("iCouponService")
@Transactional(readOnly = true)
public class CouponServiceImp implements ICouponService {
    @Autowired
    private CouponMapper couponMapper;

    @Override
    public JsonData selectCouponListService(Coupon coupon, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Coupon> list = couponMapper.selectCoupon(coupon);
        PageInfo<Coupon> page = new PageInfo<>(list);
        return JsonData.success(page,"查询成功");
    }

    @Override
    @Transactional(readOnly = false)
    public JsonData addCouponService(Coupon coupon,String name,Integer currentEmpId) {
        if(StringUtils.isBlank(name) && StringUtils.isBlank(coupon.getCouponNumber())){
            return JsonData.fail("获取信息失败请重试");
        }
        int countCouponNumber = couponMapper.selectCouponByCouponNumber(coupon.getCouponNumber());
        if(countCouponNumber>0){
            return JsonData.fail("编号重复，请重新输入");
        }
         coupon.setCouponStatus(1);
         coupon.setCreateName(name);
         coupon.setCreateTime(new Date());
         coupon.setCreateBy(currentEmpId);
         coupon.setUpdateTime(new Date());
         coupon.setUpdateBy(currentEmpId);
         coupon.setUsable(true);
         int count = couponMapper.insert(coupon);
         if(count>0){
             return JsonData.successMsg("操作成功");
         }
        return JsonData.successMsg("操作失败，请重新录入");
    }

    @Override
    @Transactional(readOnly = false)
    public JsonData editCouponService(Coupon coupon, Integer currentEmpId) {
        if(currentEmpId.intValue() == 0 && coupon.getId().intValue() == 0){
            return JsonData.fail("获取信息失败请重试");
        }
        coupon.setUpdateBy(currentEmpId);
        coupon.setUpdateTime(new Date());
        int count = couponMapper.updateCouponById(coupon);
        if(count == 1){
            return JsonData.successMsg("操作成功");
        }
        return JsonData.successMsg("操作失败,请刷新重试");
    }

    @Override
    @Transactional(readOnly = false)
    public JsonData deleteCouponService(Integer id) {
        if(id.intValue() == 0){
            return JsonData.fail("获取信息失败请重试");
        }
        couponMapper.deleteCouponById(id);
        return JsonData.successMsg("操作成功");
    }

    @Override
    public JsonData getCouponPaymentService(List<String> couponNumbers) {
        BigDecimal couponGas= couponMapper.getCouponPayment(couponNumbers);
        return JsonData.successData(couponGas);
    }
}
