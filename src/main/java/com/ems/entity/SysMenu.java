package com.ems.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 系统菜单
 */
@Getter
@Setter
public class SysMenu extends BaseEntity{

    /**
     * 菜单ID
     */
    private Integer menuId;

    /**
     * 菜单名称
     */
    @NotNull
    @Length(max = 20, message = "菜单名称不能超过20个字")
    private String menuName;

    /**
     * 菜单url
     */
    @NotNull
    @Length(max = 20, message = "菜单URL不能超过50个字")
    private String menuHref;

    /**
     * 父级菜单ID
     */
    private Integer menuParentId;

    /**
     * 菜单是否显示
     */
    private Boolean menuShowable;

    public SysMenu(Integer menuId, String menuName, String menuHref, Integer menuParentId, Boolean menuShowable, Date createTime, Integer createBy, Date
            updateTime, Integer updateBy, Boolean usable, String remarks) {
        super(createTime, createBy, updateTime, updateBy, usable, remarks);
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuHref = menuHref;
        this.menuParentId = menuParentId;
        this.menuShowable = menuShowable;
    }

    public SysMenu() {
        super();
    }
}