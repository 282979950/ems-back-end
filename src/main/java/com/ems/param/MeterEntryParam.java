package com.ems.param;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 表具入库参数
 *
 * @author litairan on 2018/8/8.
 */
@Getter
@Setter
public class MeterEntryParam {

    /**
     * 表具编号
     */
    @NotNull
    @Length(min = 12, max = 13, message = "表具编号只能是12位或13位")
    private String meterCode;

    /**
     * 表具止码
     */
    private BigDecimal meterStopCode;

    /**
     * 表具型号ID
     */
    private Integer meterTypeId;

    /**
     * 表具类别
     */
    @NotNull
    private String meterCategory;

    /**
     * 表具型号
     */
    @NotNull
    private String meterType;

    /**
     * 表向(ture为左表，false为右表)
     */
    @NotNull
    private Boolean meterDirection;

    /**
     * 表具生产日期
     */
    private Date meterProdDate;

    /**
     * 表具入库日期
     */
    private Date meterEntryDate;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建者
     */
    private Integer createBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新者
     */
    private Integer updateBy;

    /**
     * 是否可用
     */
    private Boolean usable;

    @Length(max = 255, message = "备注长度不能超过255个字")
    private String remarks;
}
