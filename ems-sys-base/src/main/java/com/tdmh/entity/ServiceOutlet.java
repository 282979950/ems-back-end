package com.tdmh.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

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
    @NotNull(message = "网点名称不能为空")
    private String serviceOutletName;

    /**
     * 网点地址
     */
    @NotNull(message = "网点地址不能为空")
    private String serviceOutletAddress;

    /**
     * 网点营业时间
     */
    @NotNull(message = "网点营业时间不能为空")
    private String serviceOutletOpenTime;

    /**
     * 网点联系方式
     */
    @NotNull(message = "网点联系方式不能为空")
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

    public ServiceOutlet(Integer serviceOutletId, String serviceOutletName, String serviceOutletAddress, String serviceOutletOpenTime, String serviceOutletPhone, String serviceOutletContent, String txLongitude, String txLatitude, String bdLongitude, String bdLatitude) {
        this.serviceOutletId = serviceOutletId;
        this.serviceOutletName = serviceOutletName;
        this.serviceOutletAddress = serviceOutletAddress;
        this.serviceOutletOpenTime = serviceOutletOpenTime;
        this.serviceOutletPhone = serviceOutletPhone;
        this.serviceOutletContent = serviceOutletContent;
        this.txLongitude = txLongitude;
        this.txLatitude = txLatitude;
        this.bdLongitude = bdLongitude;
        this.bdLatitude = bdLatitude;
    }
}
