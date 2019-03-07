package com.tdmh.param;

import com.tdmh.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 系统角色参数类
 *
 * @author litairan on 2018/7/18.
 */
@Getter
@Setter
public class SysRoleParam extends BaseEntity {

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @Length(max = 20, message = "角色名称不能超过20个字")
    private String roleName;

    /**
     * 权限ID列表
     */
    @NotNull(message = "角色拥有权限不能为空")
    private List<Integer> rolePermIds;

    private List<String> rolePermNames;

    /**
     * 区域ID列表
     */
    @NotNull(message = "角色所属区域不能为空")
    private List<Integer> roleDistIds;

    private List<String> roleDistNames;
    /**
     * 组织ID列表
     */
    @NotNull(message = "角色所属机构不能为空")
    private List<Integer> roleOrgIds;

    private List<String> roleOrgNames;

    /**
     * 是否是管理员
     */
    @NotNull(message = "是否是管理员不能为空")
    private Boolean isAdmin;
}
