package com.tdmh.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 用户表相关实体
 *
 * @author Auser_Qh
 */
@Getter
@Setter
public class UserMeterType implements Serializable {

    private static final long serialVersionUID = 3L;
    /**
     * id
     */
    private Integer userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户电话
     */
    private String userPhone;
    /**
     * 用户身份证号码
     */
    private String userIdcard;
    /**
     * 用户住址
     */
    private String userAddress;
    /**
     * 是否被锁定
     */
    private Integer userLocked;
    /**
     * 表具类别
     */
    private String meterCategory;
    /**
     * 表具型号
     */
    private String meterType;

    public UserMeterType() {
    }

    public UserMeterType(Integer userId, String userName, String userPhone, String userIdcard, String userAddress, Integer userLocked, String meterCategory, String meterType) {
        this.userId = userId;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userIdcard = userIdcard;
        this.userAddress = userAddress;
        this.userLocked = userLocked;
        this.meterCategory = meterCategory;
        this.meterType = meterType;
    }


}