package com.tdmh.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tdmh.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

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
public class EntryMeterParam extends BaseEntity {

    /**
     * 表具ID
     */
    private Integer meterId;

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
     * 表向(ture为左表，false为右表)
     */
    private String meterDirectionName;

    /**
     * 表具生产日期
     */
    @DateTimeFormat(pattern="yyyy-MM")
    @JsonFormat(pattern="yyyy-MM",timezone = "GMT+8")
    private Date meterProdDate;

    /**
     * 表具入库日期
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date meterEntryDate;

    public EntryMeterParam(Integer meterId, String meterCode, BigDecimal meterStopCode, String meterCategory, String meterType, Boolean meterDirection,
                           String meterDirectionName, Date meterProdDate, Date meterEntryDate, Date createTime, Integer createBy, Date updateTime, Integer
                                   updateBy, Boolean usable, String remarks) {
        super(createTime, createBy, updateTime, updateBy, usable, remarks);
        this.meterId = meterId;
        this.meterCode = meterCode;
        this.meterStopCode = meterStopCode;
        this.meterCategory = meterCategory;
        this.meterType = meterType;
        this.meterDirection = meterDirection;
        this.meterDirectionName = meterDirectionName;
        this.meterProdDate = meterProdDate;
        this.meterEntryDate = meterEntryDate;
    }

    public EntryMeterParam() {
        super();
    }
}
