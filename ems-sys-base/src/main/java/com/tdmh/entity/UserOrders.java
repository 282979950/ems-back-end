package com.tdmh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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
     * 用户姓名
     */
    private String userName;
    /**
     * 用户电话
     */
    private String userPhone;
    /**
     * 用户身份证号
     */
    private String userIdcard;
    /**
     * 用户地址
     */
    private String userAddress;
    /**
     * 维修次数
     */
    private Integer serviceTimes;
    /**
     * 员工名称
     */
    private String empName;
    /**
     * 员工id
     */
    private Integer empId;


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
     * 订单类型
     */
    private Integer orderType;
    /**
     * order_supplement
     */
    private BigDecimal orderSupplement;

    /**
     * 订单关闭时间
     */
    private Date orderCloseTime;
    /**
     * 临时参数resltTime 转换后的String类型时间
     */
    private String resltTime;
    /**
     * 账务状态
     */
    private Integer accountState;
    /**
     * 账务状态名称
     */
    private String accountStateName;

    /**
     * 订单流水号
     */
    private String flowNumber;
    /**
     * 临时参数，充值时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date rechargeTime;
    /**
     * 临时参数（筛选条件开始时间）
     */
    private String startTime;
    /**
     * 临时参数（筛选条件结束时间）
     */
    private String endTime;

    public UserOrders(Integer orderId, Integer userId,String userName,String userPhone,String userIdcard,String userAddress,Integer serviceTimes,String empName, Integer employeeId, BigDecimal orderPayment, BigDecimal orderGas, Integer orderStatus, Date orderCreateTime, Date
            orderCloseTime, String flowNumber,Integer orderType, Date createTime, Integer createBy, Date updateTime, Integer updateBy, Boolean usable, String remarks) {
        super(createTime, createBy, updateTime, updateBy, usable, remarks);
        this.orderId = orderId;
        this.userId = userId;
        this.userName=userName;
        this.userPhone=userPhone;
        this.userIdcard = userIdcard;
        this.userAddress = userAddress;
        this.serviceTimes = serviceTimes;
        this.empName=empName;
        this.employeeId = employeeId;
        this.orderPayment = orderPayment;
        this.orderGas = orderGas;
        this.orderStatus = orderStatus;
        this.orderCreateTime = orderCreateTime;
        this.orderCloseTime = orderCloseTime;
        this.flowNumber = flowNumber;
        this.orderType = orderType;
    }

    public UserOrders() {
        super();
    }
}