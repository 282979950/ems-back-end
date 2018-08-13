package com.ems.service;

import com.ems.common.JsonData;
import com.ems.param.CreateAccountParam;
import com.ems.param.CreateArchiveParam;
import com.ems.param.InstallMeterParam;

/**
 * 用户服务接口
 *
 * @author litairan on 2018/8/9.
 */
public interface IUserService {

    /**
     * 用户建档
     *
     * @param param
     * @return
     */
    JsonData createArchive(CreateArchiveParam param);

    /**
     * 挂表
     *
     * @param param
     * @return
     */
    JsonData installMeter(InstallMeterParam param);

    /**
     * 用户开户
     *
     * @param param
     * @return
     */
    JsonData createAccount(CreateAccountParam param);
}
