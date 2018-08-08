package com.ems.common;

import lombok.Getter;

/**
 * 表向枚举类
 *
 * @author litairan on 2018/8/8.
 */
@Getter
public enum MeterDirectionEnum {
    LEFT(true, "左表"),
    RIGHT(false, "右表");

    private boolean value;

    private String name;

    MeterDirectionEnum(boolean value, String name) {
        this.value = value;
        this.name = name;
    }
}
