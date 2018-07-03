package com.ems.entity;

import java.util.Date;
/*
 *菜单表实体 
 */
public class SysMenu {
    private String id;//id

    private Long menuId;//菜单ID

    private String menuName;//菜单名称

    private String menuHref;//菜单路径

    private Boolean menuLevel;//菜单等级

    private Long menuParentId;//父级id

    private Boolean menuIsroot;//根节点

    private Boolean menuIsleaf;//分支

    private Boolean menuShowable;//是否显示

    private Date createTime;//创建时间

    private Long createBy;//创建者

    private Date updateTime;//更新时间

    private Long updateBy;//更新者

    private Boolean useable;//是否可用

    private String remarks;//备注

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