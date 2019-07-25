package com.tdmh.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * 2 * @Author: Auser_Qh
 * 3 * @Date: 2019/7/23 8:48
 * 4优惠券实体类
 */
@Getter
@Setter
public class Coupon extends BaseEntity  {
    /**
     * 编号
     */
    private Integer id;

    /**
     * 优惠券编号
     */
    private String couponNumber;

    /**
     * 抵扣卷面值（气量）
     */
    private BigDecimal couponGas;

    /**
     * 优惠券状态（见字典表）
     */

    private Integer couponStatus;

    /**
     * 创建人姓名
     */
    private String createName;

    /**
     * 状态名称
     */
    private String couponStatusName;

    /**
     * 开始时间
     */
    private String startDate;

    /**
     * 终止时间
     */
    private String endDate;

    private List<String> couponNumbers;
}
