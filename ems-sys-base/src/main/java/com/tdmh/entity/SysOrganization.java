package com.tdmh.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
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
    @NotNull(message = "机构名称不能为空")
    private String orgName;
    /*
     *机构代码
     */

    private String orgCode;
    /*
     *机构类别
     */
    @NotNull(message = "机构类别不能为空")
    private Integer orgCategory;

    private String orgCategoryName;
    /*
     *父级机构
     */
    @NotNull(message = "父级机构不能为空")
    private Integer orgParentId;
    /*
     *父级机构名称
     */
    private String orgParentName;
    /*
     *临时参数
     */
    private List<Integer> ids;
    private List<SysOrganization> children;


    public SysOrganization( Integer orgId, String orgName, String orgCode, Integer orgCategory, Integer orgParentId, Date createTime, Integer createBy, Date updateTime, Integer updateBy, Boolean usable, String remarks) {
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