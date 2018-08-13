package com.ems.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 表具实体类
 *
 * @author litairan on 2018/8/7.
 */
@Getter
@Setter
public class Meter extends BaseEntity {

    /**
     * 表具ID
     */
    private Integer meterId;

    /**
     * 表具编号
     */
    private String meterCode;

    /**
     * 表具止码
     */
    private BigDecimal meterStopCode;

    /**
     * 表具类型ID
     */
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
     * 表向
     */
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
     * 表具安装时间
     */
    private Date meterInstallTime;

    /**
     * 表具报废时间
     */
    private Date meterScrapTime;

    /**
     * 表具有效期（单位：年）
     */
    private Integer meterValidityperiod;

    /**
     * 表具状态
     */
    private Integer meterStatus;

    /**
     * 表具通讯密钥
     */
    private String meterCommKey;

    /**
     * 表具通讯号码
     */
    private String meterCommNum;

    public Meter(Integer meterId, String meterCode, BigDecimal meterStopCode, Integer meterTypeId, String meterCategory, String meterType, Boolean
            meterDirection, Date meterProdDate, Date meterEntryDate, Date meterInstallTime, Date meterScrapTime, Integer meterValidityperiod, Integer
            meterStatus, String meterCommKey, String meterCommNum, Date createTime, Integer createBy, Date updateTime, Integer updateBy, Boolean usable,
                 String remarks) {
        super(createTime, createBy, updateTime, updateBy, usable, remarks);
        this.meterId = meterId;
        this.meterCode = meterCode;
        this.meterStopCode = meterStopCode;
        this.meterTypeId = meterTypeId;
        this.meterCategory = meterCategory;
        this.meterType = meterType;
        this.meterDirection = meterDirection;
        this.meterProdDate = meterProdDate;
        this.meterEntryDate = meterEntryDate;
        this.meterInstallTime = meterInstallTime;
        this.meterScrapTime = meterScrapTime;
        this.meterValidityperiod = meterValidityperiod;
        this.meterStatus = meterStatus;
        this.meterCommKey = meterCommKey;
        this.meterCommNum = meterCommNum;
    }

    public Meter() {
        super();
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getMeterProdDate() {
        return meterProdDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getMeterEntryDate() {
        return meterEntryDate;
    }
}