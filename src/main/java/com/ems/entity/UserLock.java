package com.ems.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 用户锁定记录类
 * @author liuxia on 2018/8/30.
 */
@Getter
@Setter
public class UserLock extends BaseEntity{
    /**
     * 用户锁定记录ID
     */
    private Integer userLockId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 是否锁定
     */
    private Boolean isLock;

    /**
     * 锁定/解锁原因
     */
    private String lockReason;

    public UserLock(Integer userLockId, Integer userId, Boolean isLock, String lockReason, Date
            createTime, Integer createBy, Date updateTime, Integer updateBy, Boolean usable, String remarks) {
        super(createTime, createBy, updateTime, updateBy, usable, remarks);
        this.userLockId = userLockId;
        this.userId = userId;
        this.isLock = isLock;
        this.lockReason = lockReason;
    }
}
