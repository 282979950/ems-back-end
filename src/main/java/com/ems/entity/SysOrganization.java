package com.ems.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
/**
 * 机构表实体
 */
@Getter
@Setter
public class SysOrganization extends BaseEntity{

    private Long orgId;//机构ID

    private String orgName;//机构名称

    private String orgCode;//机构代码

    private String orgCategory;//机构类别

    private Long orgParentId;//父级机构


    public SysOrganization( Long orgId, String orgName, String orgCode, String orgCategory, Long orgParentId,  Date createTime, Integer createBy, Date updateTime, Integer updateBy, Boolean usable, String remarks) {
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