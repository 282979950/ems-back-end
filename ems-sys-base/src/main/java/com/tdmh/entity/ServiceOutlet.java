package com.tdmh.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Liuxia on 2018/11/12.
 */
@Getter
@Setter
public class ServiceOutlet extends BaseEntity{
    /**
     * 网点Id
     */
    private Integer serviceOutletId;

    /**
     * 网点名称
     */
    private String serviceOutletName;

    /**
     * 网点地址
     */
    private String serviceOutletAddress;

    /**
     * 网点营业时间
     */
    private String serviceOutletOpenTime;

    /**
     * 网点联系方式
     */
    private String serviceOutletPhone;

    /**
     *营业范围
     */
    private String serviceOutletContent;

    /**
     * 腾讯经度
     */
    private String txLongitude;

    /**
     * 腾讯纬度
     */
    private String txLatitude;

    /**
     * 百度经度
     */
    private String bdLongitude;

    /**
     * 百度纬度
     */
    private String bdLatitude;

}
