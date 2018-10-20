package com.tdmh.param;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Administrator on 2018/10/20.
 */
@Getter
@Setter
public class InvoiceParam {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 订单ID
     */
    private Integer orderId;
    /**
     * 发票代码
     */
    private String invoiceCode;
    /**
     * 发票号码
     */
    private String invoiceNumber;
    /**
     * 支付气量
     */
    private BigDecimal orderGas;
    /**
     * 支付金额
     */
    private BigDecimal orderPayment;
    /**
     * 交易时间
     */
    private Date orderTime;
}
