package com.tdmh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tdmh.utils.excel.annotation.ExcelField;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 导出用户信息实体类
 *
 * @author litairan
 */
@Getter
@Setter
public class UserExport implements Serializable {

    /**
     * 用户ID
     */
    @ExcelField(title="用户编号", type=1, align=2, sort=1)
    private Integer userId;

    /**
     * 用户名称
     */
    @ExcelField(title="用户名称", type=1, align=2, sort=2)
    private String userName;

    /**
     * 用户手机号码
     */
    @ExcelField(title="用户手机号码", type=1, align=2, sort=3,length=5000)
    private String userPhone;

    /**
     * 用户身份证号
     */
    @ExcelField(title="用户身份证号", type=1, align=2, sort=4,length=5000)
    private String userIdcard;

    /**
     * 用户房产证号
     */
    private String userDeed;

    /**
     * 用户所在区域ID
     */
    private Integer userDistId;

    /**
     * 用户地址
     */
    @ExcelField(title="用户地址", type=1, align=2, sort=5,length=5000)
    private String userAddress;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ExcelField(title="创建时间", type=1, align=2, sort=6,length=5000)
    private Date createTime;


}