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
    private String distCode;

    /**
     * 区域类型
     */
    @NotBlank(message = "区域类型不能为空")
    private String distCategory;

    private String distAddress;

    private Integer distParentId;

    private List<SysDistrict> childrenDist;

    public SysDistrict(Integer distId, String distName, String distCode, String distCategory, String distAddress, Integer distParentId, Date createTime,
                       Integer createBy, Date updateTime, Integer updateBy, Boolean useable, String remarks) {
        super(createTime, createBy, updateTime, updateBy, useable, remarks);
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