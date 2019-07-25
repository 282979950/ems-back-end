package com.tdmh.param;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.tdmh.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @author Administrator on 2018/10/20.
 */

@Getter
@Setter
public class OrderParam extends BaseEntity {
    /**
     * 订单ID
     */
    private Integer orderId;

    /**
     * 订单类型
     */
    private Integer orderType;

    /**
     * 订单状态
     */
    private Integer orderStatus;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * IC卡卡号
     */
    private Integer iccardId;

    /**
     * 密码
     */
    private String iccardPassword;
    /**
     * IC卡识别号
     */
    private String iccardIdentifier;

    /**
     * 维修次数
     */
    private Integer serviceTimes;
    /**
     * 充值气量
     */
    private BigDecimal orderGas;
    /**
     * 充值金额
     */
    private BigDecimal orderPayment;
    /**
     * 流水号
     */
    private String flowNumber;
    /**
     * 充值订单员工
     */
    private String orderCreateEmpName;
    /**
     * 充值订单时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderCreateTime;
    /**
     * 发票代码
     */
    private String invoiceCode;
    /**
     * 发票号码
     */
    private String invoiceNumber;
    /**
     * 发票状态
     */
    private String invoiceStatusName;
    /**
     * 打印员工
     */
    private String invoicePrintEmpName;
    /**
     *发票打印时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date invoicePrintTime;
    /**
     *报废员工
     */
    private String invoiceCancelEmpName;
    /**
     *发票报废时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date invoiceCancelTime;

    /**
     * 赠送气量或优惠券（气量）
     */
    private BigDecimal couponGas;

    /**
     *订单详情
     */
    private String orderDetail;


}
