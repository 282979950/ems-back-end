package com.ems.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 区域表实体
 */
public class SysDistrict extends BaseEntity {

    /**
     * 区域ID
     */
    private Long distId;

    /**
     * 区域名称
     */
    private String distName;

    /**
     * 区域代码
     */
    private String distCode;

    /**
     * 区域类别
     */
    private String distCategory;

    /**
     * 区域等级
     */
    private String distAddress;

    /**
     * 父级区域
     */
    private Long distParentId;

    /**
     * 子节点列表
     */
    private List<SysDistrict> childrenDist;

    public SysDistrict(String id, Long distId, String distName, String distCode, String distCategory, String distAddress, Long distParentId, Date createTime, Long createBy, Date updateTime, Long updateBy, Boolean useable, String remarks) {
        super(id, createTime, createBy, updateTime, updateBy, useable, remarks);
        this.distId = distId;
        this.distName = distName;
        this.distCode = distCode;
        this.distCategory = distCategory;
        this.distAddress = distAddress;
        this.distParentId = distParentId;
        this.childrenDist = new ArrayList<>();
    }

    public SysDistrict() {
        super();
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

    public String getDistAddress() {
        return distAddress;
    }

    public void setDistAddress(String distAddress) {
        this.distAddress = distAddress;
    }

    public Long getDistParentId() {
        return distParentId;
    }

    public void setDistParentId(Long distParentId) {
        this.distParentId = distParentId;
    }

    public List<SysDistrict> getChildrenDist() {
        return childrenDist;
    }

    public void setChildrenDist(List<SysDistrict> childrenDist) {
        this.childrenDist = childrenDist;
    }
}