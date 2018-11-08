package com.tdmh.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author litairan on 2018/10/22.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WXUserInfoParam {

    /**
     * 户号
     */
    private Integer userId;

    /**
     * 燃气用户姓名
     */
    private String userName;

    /**
     * 燃气用户地址
     */
    private String userAddress;
}
