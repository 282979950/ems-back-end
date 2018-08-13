package com.ems.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 用户实体类
 *
 * @author litairan
 */
@Getter
@Setter
public class User extends BaseEntity {

    /**
     * 用户ID
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
     * 用户身份证号
     */
    private String userIdcard;

    /**
     * 用户房产证号
     */
    private String userDeed;

    /**
     * 用户所在区域ID
     */
    private Integer userDistId;

    /**
     * 用户地址
     */
    private String userAddress;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 用户用气类型
     */
    private Integer userGasType;

    /**
     * IC卡卡号
     */
    private Integer iccardId;

    /**
     * IC卡识别号
     */
    private String iccardIdentifier;

    /**
     * IC卡密码
     */
    private String iccardPassword;

    /**
     * 用户是否锁定
     */
    private Boolean userLocked;

    public User(Integer userId, String userName, String userPhone, String userIdcard, String userDeed, Integer userDistId, String userAddress, Integer
            userType, Integer userGasType, Integer iccardId, String iccardIdentifier, String iccardPassword, Boolean userLocked, Date createTime, Integer
            createBy, Date updateTime, Integer updateBy, Boolean usable, String remarks) {
        super(createTime, createBy, updateTime, updateBy, usable, remarks);
        this.userId = userId;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userIdcard = userIdcard;
        this.userDeed = userDeed;
        this.userDistId = userDistId;
        this.userAddress = userAddress;
        this.userType = userType;
        this.userGasType = userGasType;
        this.iccardId = iccardId;
        this.iccardIdentifier = iccardIdentifier;
        this.iccardPassword = iccardPassword;
        this.userLocked = userLocked;
    }

    public User() {
        super();
    }
}