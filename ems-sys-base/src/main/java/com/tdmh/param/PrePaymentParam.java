package com.tdmh.param;

import com.tdmh.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author Administrator on 2018/10/12.
 */
@Getter
@Setter
public class PrePaymentParam extends BaseEntity {

    /**
     * 用户编号
     */
    private Integer userId;
    /**
     * IC卡卡号
     */
    private Integer iccardId;

    /**
     * IC卡识别号
     */
    private String iccardIdentifier;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户手机号码
     */
    private String userPhone;

    /**
     * 用户地址
     */
    private String userAddress;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 用户类型名称
     */
    private String userTypeName;

    /**
     * 用户用气类型
     */
    private Integer userGasType;

    /**
     * 用户用气类型名称
     */
    private String userGasTypeName;

    /**
     * IC卡表类型
     */
    private String meterCategory;

    /**
     * 购气次数
     */
    private Integer totalOrderTimes;

    /**
     * 购气总量
     */
    private BigDecimal totalOrderGas;
    /**
     * 卡面气量(临时参数)
     */
    private BigDecimal cardOrderGas;

    /**
     * 购气总额
     */
    private BigDecimal totalOrderPayment;

    /**
     * 补卡后新的IC卡识别号
     */
    @NotNull(message = "新卡识别号不能为空")
    private String nIcCardIdentifier;

    /**
     * 补卡工本费
     */
    @NotNull(message = "补卡工本费不能为空")
    private BigDecimal cardCost;
}
