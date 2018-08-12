package com.ems.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 用户表具类，记录用户使用过的所有表具
 *
 * @author litairan
 */
@Getter
@Setter
public class UserMeters extends BaseEntity {

    /**
     * ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 表具ID
     */
    private Integer meterId;

    public UserMeters(Long id, Integer userId, Integer meterId, Date createTime, Integer createBy, Date updateTime, Integer updateBy, Boolean usable, String
            remarks) {
        super(createTime, createBy, updateTime, updateBy, usable, remarks);
        this.id = id;
        this.userId = userId;
        this.meterId = meterId;
    }

    public UserMeters() {
        super();
    }
}