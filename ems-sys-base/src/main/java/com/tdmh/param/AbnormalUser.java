package com.tdmh.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tdmh.utils.excel.annotation.ExcelField;
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
    @ExcelField(title="用户编号", type=1, align=2, sort=1)
    private Integer userId;
    /**
     * 用户名称
     */
    @ExcelField(title="用户名称", type=1, align=2, sort=2)
    private String userName;
    /**
     * IC卡卡号
     */
    @ExcelField(title="IC卡卡号", type=1, align=2, sort=3)
    private Integer iccardId;

    /**
     * IC卡识别号
     */
    @ExcelField(title="IC卡识别号", type=1, align=2, sort=4)
    private String iccardIdentifier;
    /**
     * 用户手机号码
     */
    @ExcelField(title="用户手机号码", type=1, align=2, sort=5)
    private String userPhone;

    /**
     * 用户所属区域
     */
    @ExcelField(title="用户所属区域", type=1, align=2, sort=6)
    private String userDistName;

    /**
     * 用户地址
     */
    @ExcelField(title="用户地址", type=1, align=2, sort=7, length = 6000)
    private String userAddress;

    /**
     * 购气总量
     */
    @ExcelField(title="购气总量", type=1, align=2, sort=8)
    private BigDecimal totalOrderGas;

    /**
     * 购气总额
     */
    @ExcelField(title="购气总额", type=1, align=2, sort=9)
    private BigDecimal totalOrderPayment;

    /**
     * 初次购气日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "初次购气日期", timezone = "GMT+8")
    @ExcelField(title="初次购气日期", type=1, align=2, sort=10)
    private Date startBuyDay;
    /**
     * 最后购气日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ExcelField(title="最后购气日期", type=1, align=2, sort=11)
    private Date endBuyDay;
    /**
     * 未购气天数
     */
    @ExcelField(title="未购气天数", type=1, align=2, sort=12)
    private int notBuyDayCount;
    /**
     * 月均购气量
     */
    @ExcelField(title="月均购气量", type=1, align=2, sort=13)
    private BigDecimal monthAveGas;
    /**
     * 月均购气金额
     */
    @ExcelField(title="月均购气金额", type=1, align=2, sort=14)
    private BigDecimal monthAvePayment;
}
