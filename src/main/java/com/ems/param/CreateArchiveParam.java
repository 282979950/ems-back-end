package com.ems.param;

import com.ems.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

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
     * 用户所在区域ID
     */
    @NotNull(message = "用户所在区域不能为空")
    private Integer userDistId;

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
     * 用户是否锁定
     */
    private Boolean userLocked;
}
