package com.tdmh.service;

import com.tdmh.common.JsonData;

/**
 * @author litairan on 2018/10/21.
 */
public interface IWXLoginService {

    JsonData wxLogin(String code);

}
