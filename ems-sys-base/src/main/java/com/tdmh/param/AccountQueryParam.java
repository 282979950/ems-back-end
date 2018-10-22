package com.tdmh.param;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户区域名字
     */
    private String userDistName;
    /**
     * 用户地址
     */
    private String userAddress;
    /**
     * 用户类型
     */
    private String userTypeName;
    /**
     * 用气类型
     */
    private String userGasTypeName;
    /**
     * 开户人
     */
    private String openByName;
    /**
     * 开户时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date openTime;
}
