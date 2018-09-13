package com.ems.entity;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
/**
 * 机构表实体
 */
@Getter
@Setter
public class SysOrganization extends BaseEntity{
    /*
     *机构ID
     */
    private Integer orgId;
    /*
     *机构名称
     */
    private String orgName;
    /*
     *机构代码
     */

    private String orgCode;
    /*
     *机构类别
     */

    private String orgCategory;

    private String orgCategoryName;
    /*
     *父级机构
     */

    private Integer orgParentId;
    /*
     *父级机构名称
     */
    private String orgParentName;
    /*
     *临时参数
     */
    private List<Integer> ids;


    public SysOrganization( Integer orgId, String orgName, String orgCode, String orgCategory, Integer orgParentId, Date createTime, Integer createBy, Date updateTime, Integer updateBy, Boolean usable, String remarks) {
        super(createTime, createBy, updateTime, updateBy, usable, remarks);
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