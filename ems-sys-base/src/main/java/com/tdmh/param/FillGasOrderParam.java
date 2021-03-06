package com.tdmh.param;

import com.tdmh.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 维修补气订单
 *
 * @author litairan on 2018/10/17.
 */
@Getter
@Setter
public class FillGasOrderParam extends BaseEntity {

    /**
     * ID
     */
    private Integer id;

    /**
     * 订单类型
     */
    @NotNull
    private Integer fillGasOrderType;

    /**
     * 订单类型名称
     */
    private String fillGasOrderTypeName;

    /**
     * 用户ID
     */
    @NotNull
    private Integer userId;

    /**
     * 用户IC卡号
     */
    private Integer cardId;

    /**
     * IC卡识别号
     */
    private String cardIdentifier;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户手机
     */
    private String userPhone;

    /**
     * 用户Id
     */
    private String userAddress;

    /**
     * 维修单Id
     */
    private String repairOrderId;

    /**
     * 历史购气总量
     */
    private BigDecimal gasCount;

    /**
     * 历史表止码
     */
    private BigDecimal stopCodeCount;

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
    private Integer fillGasOrderStatus;

    /**
     * 订单状态名称
     */
    private String fillGasOrderStatusName;

    public FillGasOrderParam(Integer id, Integer fillGasOrderType, String fillGasOrderTypeName, Integer userId, Integer cardId, String cardIdentifier,
                             String userName, String userPhone, String userAddress, String repairOrderId, BigDecimal gasCount, BigDecimal stopCodeCount,
                             BigDecimal needFillGas, BigDecimal fillGas, BigDecimal leftGas, BigDecimal needFillMoney, BigDecimal fillMoney,
                             BigDecimal leftMoney, Integer fillGasOrderStatus, String fillGasOrderStatusName, Date createTime, Integer createBy,
                             Date updateTime, Integer updateBy, Boolean usable, String remarks) {
        super(createTime, createBy, updateTime, updateBy, usable, remarks);
        this.id = id;
        this.fillGasOrderType = fillGasOrderType;
        this.fillGasOrderTypeName = fillGasOrderTypeName;
        this.userId = userId;
        this.cardId = cardId;
        this.cardIdentifier = cardIdentifier;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
        this.repairOrderId = repairOrderId;
        this.gasCount = gasCount;
        this.stopCodeCount = stopCodeCount;
        this.needFillGas = needFillGas;
        this.fillGas = fillGas;
        this.leftGas = leftGas;
        this.needFillMoney = needFillMoney;
        this.fillMoney = fillMoney;
        this.leftMoney = leftMoney;
        this.fillGasOrderStatus = fillGasOrderStatus;
        this.fillGasOrderStatusName = fillGasOrderStatusName;
    }

    public FillGasOrderParam() {
        super();
    }
}
