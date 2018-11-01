package com.tdmh.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 权限实体
 */
@Getter
@Setter
public class SysPermission extends BaseEntity {

    /**
     * 权限ID
     */
    private Integer permId;

    /**
     * 权限名称
     */
    @NotNull(message = "权限名称不能为空")
    @Length(max = 50, message = "权限名称不能超过50个字")
    private String permName;

    /**
     * 权限标题
     */
    @NotNull(message = "权限标题不能为空")
    private String permCaption;

    /**
     * 权限路径
     */
    private String permHref;

    /**
     * 权限父级ID
     */
    @NotNull(message = "菜单名称不能为空")
    private Integer permParentId;

    /**
     * 是否按钮
     */
    @NotNull(message = "是否是按钮不能为空")
    private Boolean isButton;

    private String permParentCaption;

    public SysPermission(Integer permId, String permName, String permCaption, String permHref, Integer permParentId, Boolean isButton, Date createTime, Integer createBy, Date updateTime, Integer
            updateBy, Boolean usable, String remarks) {
        super(createTime, createBy, updateTime, updateBy, usable, remarks);
        this.permId = permId;
        this.permName = permName;
        this.permCaption = permCaption;
        this.permHref = permHref;
        this.permParentId = permParentId;
        this.isButton = isButton;
    }

    public SysPermission() {
        super();
    }
}