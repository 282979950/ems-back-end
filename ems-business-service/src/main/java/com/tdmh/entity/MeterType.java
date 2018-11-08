package com.tdmh.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/**
 * 表具类型实体类
 *
 * @author litairan
 */
@Getter
@Setter
public class MeterType extends BaseEntity {

    /**
     * 表具类型ID
     */
    private Integer meterTypeId;

    /**
     * 表具类别
     */
    private String meterCategory;

    /**
     * 表具型号
     */
    private String meterType;

    public MeterType(Integer meterTypeId, String meterCategory, String meterType, Date createTime, Integer createBy, Date updateTime, Integer updateBy,
                     Boolean usable, String remarks) {
        super(createTime, createBy, updateTime, updateBy, usable, remarks);
        this.meterTypeId = meterTypeId;
        this.meterCategory = meterCategory;
        this.meterType = meterType;
    }

    public MeterType() {
        super();
    }
}