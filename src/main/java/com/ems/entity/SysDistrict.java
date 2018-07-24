package com.ems.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 区域表实体
 */
@Getter
@Setter
public class SysDistrict extends BaseEntity {

    /**
     * 区域ID
     */
    private Integer distId;

    /**
     * 区域名称
     */
    @NotBlank(message = "区域名称不能为空")
    @Length(max = 20, message = "区域名称不能超过20个字")
    private String distName;

    /**
     * 区域编码
     */
    @Length(max = 20, message = "区域编码不能超过20个字")
    private String distCode;

    /**
     * 区域类型
     */
    @NotBlank(message = "区域类型不能为空")
    @Length(max = 20, message = "区域类型不能超过20个字")
    private String distCategory;

    /**
     * 区域地址
     */
    @Length(max = 50, message = "区域地址不能超过50个字")
    private String distAddress;

    /**
     * 父级区域ID
     */
    private Integer distParentId;

    /**
     * 子区域列表
     */
    private List<SysDistrict> childrenDist;

    public SysDistrict(Integer distId, String distName, String distCode, String distCategory, String distAddress, Integer distParentId, Date createTime,
                       Integer createBy, Date updateTime, Integer updateBy, Boolean usable, String remarks) {
        super(createTime, createBy, updateTime, updateBy, usable, remarks);
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
}