package com.tdmh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
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
     * 用户状态
     */
    private Integer userStatus;

    /**
     * 用户是否锁定
     */
    private Boolean userLocked;

    /**
     * 维修次数
     */
    private Integer serviceTimes;

    private Boolean freeGasFlag;

    /**
     *临时参数，区域名称
     */
    private String distName;

    /**
     *用户类型名称
     */
    private String userTypeName;


    /**
     *用气类型名称
     */
    private String userGasTypeName;
    /**
     * 临时参数，实际充值金额
     */
    private BigDecimal orderPayment;
    /**
     * 临时参数，充值气量
     */
    private BigDecimal orderGas;
    /**
     * 临时参数,充值时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderCreateTime;
    /**
     * 订单类型名称
     */
    private String orderTypeName;
    /**
     * 账务状态
     */
    private String accountState;
    /**
     * 账务状态名称
     */
    private String accountStateName;

    /**
     *用户状态名称
     */

    private String userStatusName;
    /**
     * 临时参数，员工id号码
     */
    private Integer employeeId;
    /**
     * 临时参数订单ID
     */
    private Integer orderId;

    /**
     * （临时参数）发起冲账时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date orderStrikeTime;


    public User(Integer userId, String userName, String userPhone, String userIdcard, String userDeed, Integer userDistId, String userAddress, Integer
            userType, Integer userGasType, Integer userStatus, Boolean userLocked, Integer serviceTimes, Boolean freeGasFlag, Date
            createTime, Integer createBy, Date updateTime, Integer updateBy, Boolean usable, String remarks) {
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
        this.userStatus = userStatus;
        this.userLocked = userLocked;
        this.serviceTimes = serviceTimes;
        this.freeGasFlag = freeGasFlag;
    }

    public User() {
        super();
    }
}