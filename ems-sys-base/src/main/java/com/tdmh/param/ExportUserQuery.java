package com.tdmh.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tdmh.utils.excel.annotation.ExcelField;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 2 * @Author: Auser_Qh
 * 3 * @Date: 2019/7/25 11:28
 * 4
 */
@Getter
@Setter
public class ExportUserQuery {

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
    @ExcelField(title="用户手机号码", type=1, align=2, sort=3)
    private String userPhone;

    /**
     * 用户身份证号
     */
    @ExcelField(title="用户身份证号", type=1, align=2, sort=4)
    private String userIdcard;

    /**
     * 用户地址
     */
    @ExcelField(title="用户地址", type=1, align=2, sort=5)
    private String userAddress;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ExcelField(title="创建时间", type=1, align=2, sort=6)
    private Date createTime;
}
