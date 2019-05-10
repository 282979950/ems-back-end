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
    @NotNull(message = "表具编号不能为空")
    @Length(min = 12, max = 13, message = "表具编号只能是12位或13位")
    private String meterCode;

    /**
     * 表具止码
     */
    private BigDecimal meterStopCode;

    @NotNull(message = "表具类别不能为空")
    private Integer meterTypeId;
    /**
     * 表具类别
     */

    private String meterCategory;

    /**
     * 表具型号
     */
    private String meterType;

    /**
     * 表向(ture为左表，false为右表)
     */
    @NotNull(message = "表向不能为空")
    private Integer meterDirection;

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

    /**
     * 表具状态
     */
    private Integer meterStatus;

    private String meterStatusName;

    public EntryMeterParam(Integer meterId, String meterCode, BigDecimal meterStopCode, Integer meterTypeId, String meterCategory, String meterType,
                           Integer meterDirection, String meterDirectionName, Date meterProdDate, Date meterEntryDate, Integer meterStatus,
                           String meterStatusName, Date createTime, Integer createBy, Date updateTime, Integer updateBy, Boolean usable, String remarks) {
        super(createTime, createBy, updateTime, updateBy, usable, remarks);
        this.meterId = meterId;
        this.meterCode = meterCode;
        this.meterStopCode = meterStopCode;
        this.meterTypeId = meterTypeId;
        this.meterCategory = meterCategory;
        this.meterType = meterType;
        this.meterDirection = meterDirection;
        this.meterDirectionName = meterDirectionName;
        this.meterProdDate = meterProdDate;
        this.meterEntryDate = meterEntryDate;
        this.meterStatus = meterStatus;
        this.meterStatusName = meterStatusName;
    }

    public EntryMeterParam() {
        super();
    }
}
