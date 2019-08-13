package com.tdmh.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 2 * @Author: Auser_Qh
 * 3 * @Date: 2019/8/8 16:02
 * 4操作员日常数据查询
 */
@Setter
@Getter
public class OperatorDataQuery {

    /**
     * ID
     */
    private Integer empId;
    /**
     * 操作员名称
     */
    private String empName;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    /**
     * 订单ID
     */
    private Integer orderId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户电话
     */
    private String userPhone;
    /**
     * 用户身份证号码
     */
    private String userIdcard;
    /**
     * 用户住址
     */
    private String userAddress;
    /**
     * 用户编号
     */
    private Integer userId;
    /**
     * 基本金额
     */
    private BigDecimal baseOrderPayment;
    /**
     * 基本气量
     */
    private BigDecimal baseOrderGas;
    /**
     *发起预冲账金额
     */
    private BigDecimal launchOrderPayment;
    /**
     *发起预冲账气量
     */
    private BigDecimal launchOrderGas;
    /**
     * 补卡金额
     */
    private BigDecimal replacementOrderPayment;
    /**
     *补卡气量
     */
    private BigDecimal replacementOrderGas;
    /**
     * 工本费
     */
    private BigDecimal cardCost;
    /**
     *起始时间
     */
    private String startDate;
    /**
     *截止时间
     */
    private String endDate;
    /**
     *操作
     */
    private String operation;
}
