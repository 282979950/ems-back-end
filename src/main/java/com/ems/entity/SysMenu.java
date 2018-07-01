package com.ems.entity;

import java.util.Date;

public class SysMenu {
    private String id;

    private Long menuId;

    private String menuName;

    private String menuHref;

    private Boolean menuLevel;

    private Long menuParentId;

    private Boolean menuIsroot;

    private Boolean menuIsleaf;

    private Boolean menuShowable;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    private Boolean useable;

    private String remarks;

    public SysMenu(String id, Long menuId, String menuName, String menuHref, Boolean menuLevel, Long menuParentId, Boolean menuIsroot, Boolean menuIsleaf, Boolean menuShowable, Date createTime, Long createBy, Date updateTime, Long updateBy, Boolean useable, String remarks) {
        this.id = id;
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuHref = menuHref;
        this.menuLevel = menuLevel;
        this.menuParentId = menuParentId;
        this.menuIsroot = menuIsroot;
        this.menuIsleaf = menuIsleaf;
        this.menuShowable = menuShowable;
        this.createTime = createTime;
        this.createBy = createBy;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
        this.useable = useable;
        this.remarks = remarks;
    }

    public SysMenu() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getMenuHref() {
        return menuHref;
    }

    public void setMenuHref(String menuHref) {
        this.menuHref = menuHref == null ? null : menuHref.trim();
    }

    public Boolean getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(Boolean menuLevel) {
        this.menuLevel = menuLevel;
    }

    public Long getMenuParentId() {
        return menuParentId;
    }

    public void setMenuParentId(Long menuParentId) {
        this.menuParentId = menuParentId;
    }

    public Boolean getMenuIsroot() {
        return menuIsroot;
    }

    public void setMenuIsroot(Boolean menuIsroot) {
        this.menuIsroot = menuIsroot;
    }

    public Boolean getMenuIsleaf() {
        return menuIsleaf;
    }

    public void setMenuIsleaf(Boolean menuIsleaf) {
        this.menuIsleaf = menuIsleaf;
    }

    public Boolean getMenuShowable() {
        return menuShowable;
    }

    public void setMenuShowable(Boolean menuShowable) {
        this.menuShowable = menuShowable;
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