package com.tdmh.param;

import com.tdmh.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


/**
 * @author liuxia on 2018/8/30.
 */
@Getter
@Setter
public class LockAccountParam  extends BaseEntity {
    /**
     * 用户编号
     */
    @NotNull(message = "用户编号不能为空")
    private Integer userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * IC卡卡号
     */
    private Integer iccardId;

    /**
     * 用户区域ID
     */
    private String distName;

    /**
     * 用户地址
     */
    private String userAddress;

    /**
     * 用户是否锁定
     */
    private Boolean isLock;

    /**
     * 锁定状态
     */
    private String lockStatus;

    /**
     * 用户锁定原因
     */
    private String lockReason;

    /**
     * 用户锁定原因
     */
    private String lastLockReason;


    public LockAccountParam(Integer userId, String userName, Integer iccardId, String distName, String userAddress, Boolean isLock, String lockStatus, String lockReason, String lastLockReason) {
        this.userId = userId;
        this.userName = userName;
        this.iccardId = iccardId;
        this.distName = distName;
        this.userAddress = userAddress;
        this.isLock = isLock;
        this.lockStatus = lockStatus;
        this.lockReason = lockReason;
        this.lastLockReason = lastLockReason;
    }

    public LockAccountParam(){
        super();
    }
}
