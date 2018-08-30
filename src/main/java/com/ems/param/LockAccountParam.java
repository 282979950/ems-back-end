package com.ems.param;

import com.ems.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

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
     * 用户锁定原因
     */
    private String lockReason;


    public LockAccountParam(Integer userId, String userName, Integer iccardId, String distName, String userAddress, Boolean isLock, String lockReason) {
        this.userId = userId;
        this.userName = userName;
        this.iccardId = iccardId;
        this.distName = distName;
        this.userAddress = userAddress;
        this.isLock = isLock;
        this.lockReason = lockReason;
    }

    public LockAccountParam(){
        super();
    }
}
