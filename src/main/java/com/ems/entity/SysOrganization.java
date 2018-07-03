package com.ems.entity;

import java.util.Date;
/*
 * 机构表实体
 */
public class SysOrganization {
    private String id;//id

    private Long orgId;//机构ID

    private String orgName;//机构名称

    private String orgCode;//机构代码

    private String orgCategory;//机构类别

    private Boolean orgLevel;//机构等级

    private Long orgParentId;//父级机构

    private Boolean orgIsroot;//根节点

    private Boolean orgIsleaf;//分支

    private Date createTime;//创建时间

    private Long createBy;//创建者

    private Date updateTime;//更新时间

    private Long updateBy;//更新者

    private Boolean useable;//是否可用

    private String remarks;//注释

    public SysOrganization(String id, Long orgId, String orgName, String orgCode, String orgCategory, Boolean orgLevel, Long orgParentId, Boolean orgIsroot, Boolean orgIsleaf, Date createTime, Long createBy, Date updateTime, Long updateBy, Boolean useable, String remarks) {
        this.id = id;
        this.orgId = orgId;
        this.orgName = orgName;
        this.orgCode = orgCode;
        this.orgCategory = orgCategory;
        this.orgLevel = orgLevel;
        this.orgParentId = orgParentId;
        this.orgIsroot = orgIsroot;
        this.orgIsleaf = orgIsleaf;
        this.createTime = createTime;
        this.createBy = createBy;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
        this.useable = useable;
        this.remarks = remarks;
    }

    public SysOrganization() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public String getOrgCategory() {
        return orgCategory;
    }

    public void setOrgCategory(String orgCategory) {
        this.orgCategory = orgCategory == null ? null : orgCategory.trim();
    }

    public Boolean getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(Boolean orgLevel) {
        this.orgLevel = orgLevel;
    }

    public Long getOrgParentId() {
        return orgParentId;
    }

    public void setOrgParentId(Long orgParentId) {
        this.orgParentId = orgParentId;
    }

    public Boolean getOrgIsroot() {
        return orgIsroot;
    }

    public void setOrgIsroot(Boolean orgIsroot) {
        this.orgIsroot = orgIsroot;
    }

    public Boolean getOrgIsleaf() {
        return orgIsleaf;
    }

    public void setOrgIsleaf(Boolean orgIsleaf) {
        this.orgIsleaf = orgIsleaf;
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