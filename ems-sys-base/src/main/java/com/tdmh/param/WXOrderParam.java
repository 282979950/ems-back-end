package com.tdmh.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author litairan on 2018/10/25.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WXOrderParam {

    /**
     * 订单ID
     */
    private Integer orderId;

    /**
     * 充值金额
     */
    private BigDecimal orderPayment;

    /**
     * 充值气量
     */
    private BigDecimal orderGas;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    private String orderStatusName;

    /**
     * 订单生成时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderCreateTime;

    /**
     * 订单关闭时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderCloseTime;
}
