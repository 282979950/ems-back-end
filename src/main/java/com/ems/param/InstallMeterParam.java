package com.ems.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 挂表参数
 *
 * @author litairan on 2018/8/9.
 */
@Getter
@Setter
public class InstallMeterParam {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Integer userId;

    /**
     * 表具编号
     */
    @NotNull(message = "表具编号不能为空")
    private String meterCode;
}
