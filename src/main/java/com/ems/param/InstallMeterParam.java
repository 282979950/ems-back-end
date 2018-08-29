package com.ems.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 挂表参数
 *
 * @author litairan on 2018/8/9.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstallMeterParam {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
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
     * 用户状态
     */
    @NotNull(message = "用户状态不能为空")
    private Integer userStatus;

    /**
     * 表具编号
     */
    @NotNull(message = "表具编号不能为空")
    private String meterCode;

    public InstallMeterParam(Integer userId, String distName, String userAddress, Integer userStatus) {
        this.userId = userId;
        this.distName = distName;
        this.userAddress = userAddress;
        this.userStatus = userStatus;
        this.meterCode = null;
    }
}
