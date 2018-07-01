package com.ems.entity;

import java.util.Date;

public class SysDistrict {
    private String id;

    private Long distId;

    private String distName;

    private String distCode;

    private String distCategory;

    private Boolean distLevel;

    private Long distParentId;

    private Boolean distIsroot;

    private Boolean distIsleaf;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    private Boolean useable;

    private String remarks;

    public SysDistrict(String id, Long distId, String distName, String distCode, String distCategory, Boolean distLevel, Long distParentId, Boolean distIsroot, Boolean distIsleaf, Date createTime, Long createBy, Date updateTime, Long updateBy, Boolean useable, String remarks) {
        this.id = id;
        this.distId = distId;
        this.distName = distName;
        this.distCode = distCode;
        this.distCategory = distCategory;
        this.distLevel = distLevel;
        this.distParentId = distParentId;
        this.distIsroot = distIsroot;
        this.distIsleaf = distIsleaf;
        this.createTime = createTime;
        this.createBy = createBy;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
        this.useable = useable;
        this.remarks = remarks;
    }

    public SysDistrict() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Long getDistId() {
        return distId;
    }

    public void setDistId(Long distId) {
        this.distId = distId;
    }

    public String getDistName() {
        return distName;
    }

    public void setDistName(String distName) {
        this.distName = distName == null ? null : distName.trim();
    }

    public String getDistCode() {
        return distCode;
    }

    public void setDistCode(String distCode) {
        this.distCode = distCode == null ? null : distCode.trim();
    }

    public String getDistCategory() {
        return distCategory;
    }

    public void setDistCategory(String distCategory) {
        this.distCategory = distCategory == null ? null : distCategory.trim();
    }

    public Boolean getDistLevel() {
        return distLevel;
    }

    public void setDistLevel(Boolean distLevel) {
        this.distLevel = distLevel;
    }

    public Long getDistParentId() {
        return distParentId;
    }

    public void setDistParentId(Long distParentId) {
        this.distParentId = distParentId;
    }

    public Boolean getDistIsroot() {
        return distIsroot;
    }

    public void setDistIsroot(Boolean distIsroot) {
        this.distIsroot = distIsroot;
    }

    public Boolean getDistIsleaf() {
        return distIsleaf;
    }

    public void setDistIsleaf(Boolean distIsleaf) {
        this.distIsleaf = distIsleaf;
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