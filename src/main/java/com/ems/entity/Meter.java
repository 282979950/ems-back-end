package com.ems.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Meter {
    private String id;

    private Long meterId;

    private String meterCode;

    private BigDecimal meterStopcode;

    private Integer meterTypeId;

    private Boolean meterDirection;

    private Date meterProdDate;

    private Date meterEntryDate;

    private Date meterInstallTime;

    private Date meterScrapTime;

    private Integer meterValidityperiod;

    private Integer meterStatus;

    private String meterCommKey;

    private String meterCommNum;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    private Boolean useable;

    private String remarks;

    public Meter(String id, Long meterId, String meterCode, BigDecimal meterStopcode, Integer meterTypeId, Boolean meterDirection, Date meterProdDate, Date meterEntryDate, Date meterInstallTime, Date meterScrapTime, Integer meterValidityperiod, Integer meterStatus, String meterCommKey, String meterCommNum, Date createTime, Long createBy, Date updateTime, Long updateBy, Boolean useable, String remarks) {
        this.id = id;
        this.meterId = meterId;
        this.meterCode = meterCode;
        this.meterStopcode = meterStopcode;
        this.meterTypeId = meterTypeId;
        this.meterDirection = meterDirection;
        this.meterProdDate = meterProdDate;
        this.meterEntryDate = meterEntryDate;
        this.meterInstallTime = meterInstallTime;
        this.meterScrapTime = meterScrapTime;
        this.meterValidityperiod = meterValidityperiod;
        this.meterStatus = meterStatus;
        this.meterCommKey = meterCommKey;
        this.meterCommNum = meterCommNum;
        this.createTime = createTime;
        this.createBy = createBy;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
        this.useable = useable;
        this.remarks = remarks;
    }

    public Meter() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Long getMeterId() {
        return meterId;
    }

    public void setMeterId(Long meterId) {
        this.meterId = meterId;
    }

    public String getMeterCode() {
        return meterCode;
    }

    public void setMeterCode(String meterCode) {
        this.meterCode = meterCode == null ? null : meterCode.trim();
    }

    public BigDecimal getMeterStopcode() {
        return meterStopcode;
    }

    public void setMeterStopcode(BigDecimal meterStopcode) {
        this.meterStopcode = meterStopcode;
    }

    public Integer getMeterTypeId() {
        return meterTypeId;
    }

    public void setMeterTypeId(Integer meterTypeId) {
        this.meterTypeId = meterTypeId;
    }

    public Boolean getMeterDirection() {
        return meterDirection;
    }

    public void setMeterDirection(Boolean meterDirection) {
        this.meterDirection = meterDirection;
    }

    public Date getMeterProdDate() {
        return meterProdDate;
    }

    public void setMeterProdDate(Date meterProdDate) {
        this.meterProdDate = meterProdDate;
    }

    public Date getMeterEntryDate() {
        return meterEntryDate;
    }

    public void setMeterEntryDate(Date meterEntryDate) {
        this.meterEntryDate = meterEntryDate;
    }

    public Date getMeterInstallTime() {
        return meterInstallTime;
    }

    public void setMeterInstallTime(Date meterInstallTime) {
        this.meterInstallTime = meterInstallTime;
    }

    public Date getMeterScrapTime() {
        return meterScrapTime;
    }

    public void setMeterScrapTime(Date meterScrapTime) {
        this.meterScrapTime = meterScrapTime;
    }

    public Integer getMeterValidityperiod() {
        return meterValidityperiod;
    }

    public void setMeterValidityperiod(Integer meterValidityperiod) {
        this.meterValidityperiod = meterValidityperiod;
    }

    public Integer getMeterStatus() {
        return meterStatus;
    }

    public void setMeterStatus(Integer meterStatus) {
        this.meterStatus = meterStatus;
    }

    public String getMeterCommKey() {
        return meterCommKey;
    }

    public void setMeterCommKey(String meterCommKey) {
        this.meterCommKey = meterCommKey == null ? null : meterCommKey.trim();
    }

    public String getMeterCommNum() {
        return meterCommNum;
    }

    public void setMeterCommNum(String meterCommNum) {
        this.meterCommNum = meterCommNum == null ? null : meterCommNum.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Boolean getUseable() {
        return useable;
    }

    public void setUseable(Boolean useable) {
        this.useable = useable;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }
}