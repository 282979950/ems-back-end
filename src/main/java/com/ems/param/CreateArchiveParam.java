package com.ems.param;

import com.ems.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

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
    private String distName;

    /**
     * 用户地址
     */
    @NotNull(message = "用户地址不能为空")
    @Length(max = 100, message = "用户地址长度不能超过100字")
    private String userAddress;

    /**
     * 用户类型
     */
    @NotNull(message = "用户类型不能为空")
    private Integer userType;

    /**
     * 用户用气类型
     */
    @NotNull(message = "用户用气类型不能为空")
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
     * 用户表具类型
     */
    private String meterCategory;

    public CreateArchiveParam(Integer userId, String distName, String userAddress, Integer userType, Integer userGasType, Integer userStatus, Boolean
            userLocked, String meterCategory, Date createTime, Integer createBy, Date updateTime, Integer updateBy, Boolean usable, String remarks) {
        super(createTime, createBy, updateTime, updateBy, usable, remarks);
        this.userId = userId;
        this.distName = distName;
        this.userAddress = userAddress;
        this.userType = userType;
        this.userGasType = userGasType;
        this.userStatus = userStatus;
        this.userLocked = userLocked;
        this.meterCategory = meterCategory;
    }

    public CreateArchiveParam() {
        super();
    }
}
