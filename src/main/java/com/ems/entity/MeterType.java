package com.ems.entity;

import java.util.Date;

public class MeterType {
    private String id;

    private Integer meterTypeId;

    private String meterCategory;

    private String meterType;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    private Boolean useable;

    private String remarks;

    public MeterType(String id, Integer meterTypeId, String meterCategory, String meterType, Date createTime, Long createBy, Date updateTime, Long updateBy, Boolean useable, String remarks) {
        this.id = id;
        this.meterTypeId = meterTypeId;
        this.meterCategory = meterCategory;
        this.meterType = meterType;
        this.createTime = createTime;
        this.createBy = createBy;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
        this.useable = useable;
        this.remarks = remarks;
    }

    public MeterType() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getMeterTypeId() {
        return meterTypeId;
    }

    public void setMeterTypeId(Integer meterTypeId) {
        this.meterTypeId = meterTypeId;
    }

    public String getMeterCategory() {
        return meterCategory;
    }

    public void setMeterCategory(String meterCategory) {
        this.meterCategory = meterCategory == null ? null : meterCategory.trim();
    }

    public String getMeterType() {
        return meterType;
    }

    public void setMeterType(String meterType) {
        this.meterType = meterType == null ? null : meterType.trim();
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