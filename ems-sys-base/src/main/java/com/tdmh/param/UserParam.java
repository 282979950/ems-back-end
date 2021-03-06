package com.tdmh.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 账户信息类
 *
 * @author litairan
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserParam {

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
     * 用户区域名称
     */
    private String userDistName;

    /**
     * 用户地址
     */
    private String userAddress;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 用户类型名称
     */
    private String userTypeName;

    /**
     * 用户用气类型
     */
    private Integer userGasType;

    /**
     * 用气类型名称
     */
    private String userGasTypeName;

    /**
     * 用户状态
     */
    private Integer userStatus;

    /**
     * 用户状态名称
     */
    private String userStatusName;

    /**
     * 用户是否锁定
     */
    private Boolean userLocked;

    /**
     * 表具ID
     */
    private Integer meterId;

    /**
     * 表具编号
     */
    private String meterCode;

    /**
     * IC卡卡号
     */
    private Integer userCardId;

    /**
     * IC卡号
     */
    private Integer cardId;

    /**
     * IC卡识别号
     */
    private String cardIdentifier;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建者
     */
    private Integer createBy;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 更新者
     */
    private Integer updateBy;

    /**
     * 是否可用
     */
    private Boolean usable;

    /**
     * 备注
     */
    @Length(max = 255, message = "备注长度不能超过255个字")
    private String remarks;

    /**
     * 购气次数
     */
    private Integer totalOrderTimes;

    /**
     * 购气总量
     */
    private BigDecimal totalOrderGas;

    public UserParam(Integer userId, String userName, String userPhone, String userIdcard, String userDeed, Integer userDistId, String userDistName, String userAddress, Integer userType, String userTypeName, Integer userGasType, String userGasTypeName, Integer userStatus, String userStatusName, Boolean userLocked, Integer meterId, String meterCode, Integer userCardId, Integer cardId, String cardIdentifier, Date createTime, Integer createBy, Date updateTime, Integer updateBy, Boolean usable, @Length(max = 255, message = "备注长度不能超过255个字") String remarks) {
        this.userId = userId;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userIdcard = userIdcard;
        this.userDeed = userDeed;
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
        this.meterId = meterId;
        this.meterCode = meterCode;
        this.userCardId = userCardId;
        this.cardId = cardId;
        this.cardIdentifier = cardIdentifier;
        this.createTime = createTime;
        this.createBy = createBy;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
        this.usable = usable;
        this.remarks = remarks;
    }
}