package com.ems.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 机构实体
 */
@Getter
@Setter
public class SysOrganization extends BaseEntity {
    /**
     * 机构ID
     */
    private Integer orgId;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 机构编码
     */
    private String orgCode;

    /**
     * 机构类别
     */
    private String orgCategory;

    /**
     * 父机构ID
     */
    private Integer orgParentId;

    public SysOrganization(Integer orgId, String orgName, String orgCode, String orgCategory, Integer orgParentId, Date createTime, Integer createBy, Date
            updateTime, Integer updateBy, Boolean useable, String remarks) {
        super(createTime, createBy, updateTime, updateBy, useable, remarks);
        this.orgId = orgId;
        this.orgName = orgName;
        this.orgCode = orgCode;
        this.orgCategory = orgCategory;
        this.orgParentId = orgParentId;
    }

    public SysOrganization() {
        super();
    }
}