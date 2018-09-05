package com.ems.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 系统角色参数类
 *
 * @author litairan on 2018/7/18.
 */
@Getter
@Setter
public class SysRoleParam {

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 角色名称
     */
    @NotNull
    @Length(max = 20, message = "角色名称不能超过20个字")
    private String roleName;

    /**
     * 权限ID列表
     */
    private String permIds;

    /**
     * 区域ID列表
     */
    private String distIds;
    /**
     * 组织ID列表
     */
    private String orgIds;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 备注
     */
    private String remarks;
}
