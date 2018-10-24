package com.tdmh.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tdmh.utils.excel.annotation.ExcelField;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Administrator on 2018/10/22.
 */
@Getter
@Setter
public class AccountQueryParam {
    /**
     * 用户ID
     */
    @ExcelField(title="用户编号", type=1, align=2, sort=1)
    private Integer userId;
    /**
     * 用户名
     */
    @ExcelField(title="用户名", type=1, align=2, sort=2)
    private String userName;
    /**
     * 用户区域
     */
    @ExcelField(title="用户区域", type=1, align=2, sort=3)
    private String userDistName;
    /**
     * 用户地址
     */
    @ExcelField(title="用户地址", type=1, align=2, sort=4)
    private String userAddress;
    /**
     * 用户类型
     */
    @ExcelField(title="用户类型", type=1, align=2, sort=5)
    private String userTypeName;
    /**
     * 用气类型
     */
    @ExcelField(title="用户类型", type=1, align=2, sort=6)
    private String userGasTypeName;
    /**
     * 开户人
     */
    @ExcelField(title="开户人", type=1, align=2, sort=7)
    private String openByName;
    /**
     * 开户时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelField(title="开户时间", type=1, align=2, sort=8)
    private Date openTime;


    public AccountQueryParam() {
    }

}
