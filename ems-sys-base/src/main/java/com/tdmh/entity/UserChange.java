package com.tdmh.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author qh on 2018/10/16.
 */
@Getter
@Setter
public class UserChange extends BaseEntity {

    /**
     * id
     */
    private String id ;
    /**
     *
     * 关联用户ID
     */
    private Integer userId;
    /**
     * 用户名称
     */
    private String userChangeName;
    /**
     * 用户电话
     */
    private String userChangePhone;
    /**
     * 用户身份证号码
     */
    private String userChangeIdcard;
    /**
     * 用户房产证号码
     */
    private String userChangeDeed;
    /**
     * 旧用户名称
     */
    private String userOldName;
    /**
     * 旧用户电话
     */
    private String userOldPhone;
    /**
     * 旧用户身份证号码
     */
    private String userOldIdcard;
    /**
     * 旧用户房产证号码
     */
    private String userOldDeed;
    /**
     * 当前表址码
     */
    private BigDecimal tableCode;

}
