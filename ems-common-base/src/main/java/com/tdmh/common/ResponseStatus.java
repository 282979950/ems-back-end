package com.tdmh.common;

import lombok.Getter;

/**
 * @author litairan on 2018/12/14.
 */
@Getter
public enum ResponseStatus {
    SUCCESS(0, "成功"),
    FAIL(1, "失败"),
    UNAUTHENTICATED(2, "用户未登录"),
    UNAUTHORIZED(3, "无权限操作");

    private int status;

    private String desc;

    ResponseStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

}
