package com.tdmh.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Lucia on 2018/10/10.
 */
@Getter
@Setter
public class UserCard extends BaseEntity {

    private Integer userCardId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * IC卡卡号
     */
    private Integer cardId;

    /**
     * IC卡识别号
     */
    private String cardIdentifier;

    /**
     * IC卡密码
     */
    private String cardPassword;
    /**
     * 初始化卡标记
     */
    private Boolean cardInitialization;
}
