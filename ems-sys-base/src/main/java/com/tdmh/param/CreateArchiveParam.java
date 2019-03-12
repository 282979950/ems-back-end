package com.tdmh.param;


import com.tdmh.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 用户建档参数
 *
 * @author litairan on 2018/8/9.
 */
@Getter
@Setter
@ToString
public class CreateArchiveParam extends BaseEntity {

    /**
     * 用户编号
     */
    private Integer userId;

    /**
     * 用户区域ID
     */
    @NotNull(message = "用户区域不能为空")
    private Integer userDistId;

    /**
     * 用户区域名称
     */
    private String userDistName;

    /**
     * 用户地址
     */
    @NotBlank(message = "用户地址不能为空")
    @Length(max = 100, message = "用户地址长度不能超过100字")
    private String userAddress;

    /**
     * 用户类型
     */
    @NotNull(message = "用户类型不能为空")
    private Integer userType;

    /**
     * 用户类型
     */
    private String userTypeName;

    /**
     * 用户用气类型
     */
    @NotNull(message = "用户用气类型不能为空")
    private Integer userGasType;

    /**
     * 用户用气类型
     */
    private String userGasTypeName;

    /**
     * 用户状态
     */
    private Integer userStatus;

    /**
     * 用户状态
     */
    private String userStatusName;

    /**
     * 用户是否锁定
     */
    private Boolean userLocked;

    /**
     * 用户表具类型
     */
    private String meterCategory;

    public CreateArchiveParam(Integer userId, Integer userDistId, String userDistName, String userAddress, Integer  userType, String userTypeName, Integer
            userGasType, String userGasTypeName, Integer userStatus, String userStatusName, Boolean userLocked, String meterCategory, Date createTime,
            Integer createBy, Date updateTime, Integer updateBy, Boolean usable, String remarks) {
        super(createTime, createBy, updateTime, updateBy, usable, remarks);
        this.userId = userId;
        this.userDistId = userDistId;
        this.userDistName = userDistName;
        this.userAddress = userAddress;
        this.userType = userType;
        this.userTypeName = userTypeName;
        this.userGasType = userGasType;
        this.userGasTypeName = userGasTypeName;
        this.userStatus = userStatus;
        this.userStatusName = userStatusName;
        this.userLocked = userLocked;
        this.meterCategory = meterCategory;
    }

    public CreateArchiveParam() {
        super();
    }
}
