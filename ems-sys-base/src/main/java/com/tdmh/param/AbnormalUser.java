package com.tdmh.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Administrator on 2018/10/23.
 */
@Getter
@Setter
public class AbnormalUser {
    /**
     * 用户编号
     */
    private Integer userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * IC卡卡号
     */
    private Integer iccardId;

    /**
     * IC卡识别号
     */
    private String iccardIdentifier;
    /**
     * 用户手机号码
     */
    private String userPhone;

    /**
     * 用户所属区域
     */
    private String userDistName;

    /**
     * 用户地址
     */
    private String userAddress;

    /**
     * 购气总量
     */
    private BigDecimal totalOrderGas;

    /**
     * 购气总额
     */
    private BigDecimal totalOrderPayment;

    /**
     * 初次购气日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startBuyDay;
    /**
     * 最后购气日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endBuyDay;
    /**
     * 未购气天数
     */
    private int notBuyDayCount;
    /**
     * 月均购气量
     */
    private BigDecimal monthAveGas;
    /**
     * 月均购气金额
     */
    private BigDecimal monthAvePayment;
}
