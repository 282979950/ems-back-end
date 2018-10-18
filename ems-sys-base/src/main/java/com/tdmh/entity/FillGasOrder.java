package com.tdmh.entity;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 维修补气单实体
 *
 * @author litairan on 2018/10/17.
 */
public class FillGasOrder extends BaseEntity {
    /**
     * ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    @NotNull
    private Integer userId;

    /**
     * 应补气量
     */
    private BigDecimal needFillGas;

    /**
     * 实补气量
     */
    private BigDecimal fillGas;

    /**
     * 剩余气量
     */
    private BigDecimal leftGas;

    /**
     * 应补金额
     */
    private BigDecimal needFillMoney;

    /**
     * 实补金额
     */
    private BigDecimal fillMoney;

    /**
     * 剩余金额
     */
    private BigDecimal leftMoney;

    /**
     * 订单状态
     */
    private Boolean fillGasOrderStatus;
}
