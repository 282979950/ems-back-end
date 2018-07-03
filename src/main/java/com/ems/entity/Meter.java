package com.ems.entity;

import java.math.BigDecimal;
import java.util.Date;
/*
 * 表具
 */
public class Meter {
    private String id;//id

    private Long meterId;//表具ID

    private String meterCode;//表编号

    private BigDecimal meterStopcode;//表止码

    private Integer meterTypeId;//表具型号ID

    private Boolean meterDirection;//表向

    private Date meterProdDate;//表具生产日期

    private Date meterEntryDate;//表具录入时间

    private Date meterInstallTime;//表具安装时间

    private Date meterScrapTime;//表具报废时间

    private Integer meterValidityperiod;//表具有效期(单位：年)

    private Integer meterStatus;//表具状态

    private String meterCommKey;//通讯密钥

    private String meterCommNum;//通讯号码

    private Date createTime;//创建时间

    private Long createBy;//创建者

    private Date updateTime;//更新时间

    private Long updateBy;//更新者

    private Boolean useable;//是否可用

    private String remarks;//注释

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