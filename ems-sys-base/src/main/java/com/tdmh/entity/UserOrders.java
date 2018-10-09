package com.tdmh.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户订单实体类
 *
 * @author litairan
 */
@Getter
@Setter
public class UserOrders extends BaseEntity {

    /**
     * 订单ID
     */
    private Integer orderId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 员工ID
     */
    private Integer employeeId;

    /**
     * 支付金额
     */
    private BigDecimal orderPayment;

    /**
     * 支付气量
     */
    private BigDecimal orderGas;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 订单创建时间
     */
    private Date orderCreateTime;

    /**
     * 订单关闭时间
     */
    private Date orderCloseTime;

    public UserOrders(Integer orderId, Integer userId, Integer employeeId, BigDecimal orderPayment, BigDecimal orderGas, Integer orderStatus, Date orderCreateTime, Date
            orderCloseTime, Date createTime, Integer createBy, Date updateTime, Integer updateBy, Boolean usable, String remarks) {
        super(createTime, createBy, updateTime, updateBy, usable, remarks);
        this.orderId = orderId;
        this.userId = userId;
        this.employeeId = employeeId;
        this.orderPayment = orderPayment;
        this.orderGas = orderGas;
        this.orderStatus = orderStatus;
        this.orderCreateTime = orderCreateTime;
        this.orderCloseTime = orderCloseTime;
    }

    public UserOrders() {
        super();
    }
}