package com.ems.entity;

import java.util.Date;
/*
 * 用户表实体
 */
public class User {
    private String id;//普通id

    private Long userId;//用户ID

    private String userName;//机构名称

    private String userPhone;//用户电话

    private String userIdcard;//用户身份证号码

    private String userDeed;//用户房产证号码

    private Long userDistId;//用户所在区域ID

    private String userAddress;//用户住址

    private Byte userType;//用户类型

    private Byte userGasType;//用气类型

    private Byte userMeterType;//用户表类型

    private Long iccardId;//IC卡编号

    private String iccardIdentifier;//IC卡识别号

    private String iccardPassword;//IC卡密码

    private Boolean userLocked;//是否被锁定

    private Date createTime;//创建时间

    private Long createBy;//创建者

    private Date updateTime;//更新时间

    private Long updateBy;//更新者

    private Boolean useable;//是否可用

    private String remarks;//备注

    public User(String id, Long userId, String userName, String userPhone, String userIdcard, String userDeed, Long userDistId, String userAddress, Byte userType, Byte userGasType, Byte userMeterType, Long iccardId, String iccardIdentifier, String iccardPassword, Boolean userLocked, Date createTime, Long createBy, Date updateTime, Long updateBy, Boolean useable, String remarks) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userIdcard = userIdcard;
        this.userDeed = userDeed;
        this.userDistId = userDistId;
        this.userAddress = userAddress;
        this.userType = userType;
        this.userGasType = userGasType;
        this.userMeterType = userMeterType;
        this.iccardId = iccardId;
        this.iccardIdentifier = iccardIdentifier;
        this.iccardPassword = iccardPassword;
        this.userLocked = userLocked;
        this.createTime = createTime;
        this.createBy = createBy;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
        this.useable = useable;
        this.remarks = remarks;
    }

    public User() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public String getUserIdcard() {
        return userIdcard;
    }

    public void setUserIdcard(String userIdcard) {
        this.userIdcard = userIdcard == null ? null : userIdcard.trim();
    }

    public String getUserDeed() {
        return userDeed;
    }

    public void setUserDeed(String userDeed) {
        this.userDeed = userDeed == null ? null : userDeed.trim();
    }

    public Long getUserDistId() {
        return userDistId;
    }

    public void setUserDistId(Long userDistId) {
        this.userDistId = userDistId;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress == null ? null : userAddress.trim();
    }

    public Byte getUserType() {
        return userType;
    }

    public void setUserType(Byte userType) {
        this.userType = userType;
    }

    public Byte getUserGasType() {
        return userGasType;
    }

    public void setUserGasType(Byte userGasType) {
        this.userGasType = userGasType;
    }

    public Byte getUserMeterType() {
        return userMeterType;
    }

    public void setUserMeterType(Byte userMeterType) {
        this.userMeterType = userMeterType;
    }

    public Long getIccardId() {
        return iccardId;
    }

    public void setIccardId(Long iccardId) {
        this.iccardId = iccardId;
    }

    public String getIccardIdentifier() {
        return iccardIdentifier;
    }

    public void setIccardIdentifier(String iccardIdentifier) {
        this.iccardIdentifier = iccardIdentifier == null ? null : iccardIdentifier.trim();
    }

    public String getIccardPassword() {
        return iccardPassword;
    }

    public void setIccardPassword(String iccardPassword) {
        this.iccardPassword = iccardPassword == null ? null : iccardPassword.trim();
    }

    public Boolean getUserLocked() {
        return userLocked;
    }

    public void setUserLocked(Boolean userLocked) {
        this.userLocked = userLocked;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Boolean getUseable() {
        return useable;
    }

    public void setUseable(Boolean useable) {
        this.useable = useable;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }
}