package com.tdmh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tdmh.utils.excel.annotation.ExcelField;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
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
    @ExcelField(title="订单编号", type=1, align=2, sort=1)
    private Integer orderId;

    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 用户姓名
     */
    @ExcelField(title="用户姓名", type=1, align=2, sort=2)
    private String userName;
    /**
     * 用户电话
     */
    @ExcelField(title="用户电话", type=1, align=2, sort=4)
    private String userPhone;
    /**
     * 用户身份证号
     */
    @ExcelField(title="用户身份证号", type=1, align=2, sort=5, length=5000)
    private String userIdcard;
    /**
     * 用户地址
     */
    @ExcelField(title="用户地址", type=1, align=2, sort=6, length=5000)
    private String userAddress;
    /**
     * 维修次数
     */
    @ExcelField(title="维修次数", type=1, align=2, sort=7)
    private Integer serviceTimes;
    /**
     * 员工名称
     */
    @ExcelField(title="操作人姓名", type=1, align=2, sort=11)
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
    @NotNull(message = "充值金额不能为空")
    @ExcelField(title="支付金额", type=1, align=2, sort=8)
    private BigDecimal orderPayment;

    /**
     * 支付气量
     */
    @NotNull(message = "充值气量不能为空")
    @ExcelField(title="充值气量(单位:方)", type=1, align=2, sort=9, length=5000)
    private BigDecimal orderGas;

    /**
     * 订单状态
     */
    private Integer orderStatus;
    /**
     * 订单状态名称
     */
    private String orderStatusName;


    /**
     * 订单创建时间
     */
    private Date orderCreateTime;
    /**
     * 订单类型
     */
    private Integer orderType;
    /**
     * 订单类型名称
     */
    private String orderTypeName;
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
    @ExcelField(title="账务状态", type=1, align=2, sort=3)
    private String accountStateName;

    /**
     * 订单流水号
     */
    private String flowNumber;
    /**
     * 临时参数，充值时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ExcelField(title="充值时间", type=1, align=2, sort=10, length=5000)
    private Date rechargeTime;
    /**
     * 临时参数（筛选条件开始时间）
     */
    private String startTime;
    /**
     * 临时参数（筛选条件结束时间）
     */
    private String endTime;
    /**
     * 临时参数（次数）
     */
    private String icCard;
    /**
     * 临时参数（机构id）
     */
    private Integer empOrgId;
    /**
     * 临时参数（起始日期）
     */
    private String startDate;
    /**
     * 临时参数（截止日期）
     */
    private String endDate;
    /**
     * 发起冲账时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderStrikeTime;
    /**
     * 赠送气量或优惠券（气量）
     */
    private BigDecimal couponGas;

    /**
     *订单详情
     */
    private String orderDetail;

    /**
     * 优惠券编号，同时使用多张时逗号隔开
     */
    private String couponNumber;

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