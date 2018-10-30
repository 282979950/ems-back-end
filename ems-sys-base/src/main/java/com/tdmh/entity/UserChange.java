package com.tdmh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

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
     * 当前表止码
     */
    private BigDecimal tableCode;

    /**
     * 时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    public UserChange(String id, Integer userId, String userChangeName, String userChangePhone, String userChangeIdcard, String userChangeDeed, String userOldName, String userOldPhone, String userOldIdcard, String userOldDeed,Date createTime) {
        this.id = id;
        this.userId = userId;
        this.userChangeName = userChangeName;
        this.userChangePhone = userChangePhone;
        this.userChangeIdcard = userChangeIdcard;
        this.userChangeDeed = userChangeDeed;
        this.userOldName = userOldName;
        this.userOldPhone = userOldPhone;
        this.userOldIdcard = userOldIdcard;
        this.userOldDeed = userOldDeed;
        this.createTime = createTime;
    }

    public UserChange() {
        super();
    }
}
