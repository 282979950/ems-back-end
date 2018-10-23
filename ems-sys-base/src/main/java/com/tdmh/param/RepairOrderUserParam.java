package com.tdmh.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author litairan on 2018/10/22.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RepairOrderUserParam {

    /**
     * 用户编号
     */
    private Integer userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户手机号码
     */
    private String userPhone;

    /**
     * 用户地址
     */
    private String userAddress;

    /**
     * 表具Id
     */
    private Integer meterId;

    /**
     * 旧表编号
     */
    private String meterCode;

    /**
     * 旧表类型Id
     */
    private Integer meterTypeId;

    /**
     * 旧表类型名称
     */
    private String meterTypeName;

    /**
     * 旧表表向
     */
    private Boolean meterDirection;

    /**
     * 旧表表向名称
     */
    private String meterDirectionName;
}
