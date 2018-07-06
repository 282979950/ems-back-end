package com.ems.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by litairan litairan@whtdmh.com on 2018/7/3.
 * 基础实体类
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID（唯一标识）
     */
    private String id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建者
     */
    private Long createBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新着
     */
    private Long updateBy;
    /**
     * 是否可用
     */
    private Boolean useable;

    /**
     * 备注
     */
    private String remarks;

    public BaseEntity(String id, Date createTime, Long createBy, Date updateTime, Long updateBy, Boolean useable, String remarks) {
        this.id = id;
        this.createTime = createTime;
        this.createBy = createBy;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
        this.useable = useable;
        this.remarks = remarks;
    }

    public BaseEntity() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
