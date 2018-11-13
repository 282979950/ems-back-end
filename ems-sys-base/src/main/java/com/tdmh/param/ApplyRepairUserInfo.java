package com.tdmh.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author litairan on 2018/11/13.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplyRepairUserInfo {

    private Integer userId;

    private String userName;

    private String distName;

    private String userAddress;

    private String userPhone;
}
