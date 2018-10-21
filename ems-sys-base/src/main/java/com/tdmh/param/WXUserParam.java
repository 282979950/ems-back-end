package com.tdmh.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author litairan on 2018/10/21.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WXUserParam {

    /**
     * ID
     */
    private Integer id;

    /**
     * 微信用户ID
     */
    private String wxUserId;

    /**
     * 户号
     */
    private Integer userId;

    /**
     * 燃气用户姓名
     */
    private String userName;

    /**
     * 创建时间
     */
    private Date createTime;
}
