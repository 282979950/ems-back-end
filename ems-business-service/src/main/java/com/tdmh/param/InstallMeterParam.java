package com.tdmh.param;

import com.tdmh.entity.BaseEntity;
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
public class InstallMeterParam extends BaseEntity {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Integer userId;

    /**
     * 用户区域ID
     */
    @NotNull(message = "用户区域不能为空")
    private Integer userDistId;

    /**
     * 用户区域
     */
    private String userDistName;

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
     * 用户状态
     */
    private String userStatusName;

    /**
     * 表具编号
     */
    @NotNull(message = "表具编号不能为空")
    private String meterCode;
}
