package com.tdmh.param;


import com.tdmh.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author litairan on 2018/9/4.
 */
@Getter
@Setter
public class SysDistrictParam extends BaseEntity {
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
    @NotNull(message = "区域类型不能为空")
    private Integer distCategory;

    /**
     * 区域类型
     */
    private String distCategoryName;

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
     * 父级区域名称
     */
    private String distParentName;

    public SysDistrictParam(Integer distId, String distName, String distCode, Integer distCategory, String distCategoryName, String distAddress, Integer
            distParentId, String distParentName, Date  createTime, Integer createBy, Date updateTime, Integer updateBy, Boolean usable, String remarks) {
        super(createTime, createBy, updateTime, updateBy, usable, remarks);
        this.distId = distId;
        this.distName = distName;
        this.distCode = distCode;
        this.distCategory = distCategory;
        this.distCategoryName = distCategoryName;
        this.distAddress = distAddress;
        this.distParentId = distParentId;
        this.distParentName = distParentName;
    }

    public SysDistrictParam() {
        super();
    }
}
