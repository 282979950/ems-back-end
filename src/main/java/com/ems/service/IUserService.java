package com.ems.service;

import com.ems.common.JsonData;
import com.ems.param.CreateAccountParam;
import com.ems.param.CreateArchiveParam;
import com.ems.param.InstallMeterParam;

import java.util.List;

/**
 * 用户服务接口
 *
 * @author litairan on 2018/8/9.
 */
public interface IUserService {

    /**
     * 查询所有档案
     *
     * @return
     */
    JsonData getAllArchives();

    /**
     * 新增档案
     *
     * @param param
     * @return
     */
    JsonData addArchive(CreateArchiveParam param);

    /**
     * 编辑档案
     *
     * @param param
     * @return
     */
    JsonData editArchive(CreateArchiveParam param);

    /**
     * 删除档案
     *
     * @param ids
     * @return
     */
    JsonData deleteArchive(List<Integer> ids);

    /**
     * 查询档案
     *
     * @param userId
     * @param distName
     * @param userAddress
     * @param userType
     * @param userGasType
     * @param userStatus
     * @return
     */
    JsonData searchArchive(Integer userId, String distName, String userAddress, Integer userType, Integer userGasType, Integer userStatus);

    /**
     * @return
     */
    JsonData getAllInstallMeters();

    /**
     * 新增挂表
     *
     * @param param
     * @return
     */
    JsonData addInstallMeter(InstallMeterParam param);

    /**
     * 编辑挂表
     *
     * @param param
     * @return
     */
    JsonData editInstallMeter(InstallMeterParam param);

    /**
     * 删除挂表
     *
     * @param ids
     * @return
     */
    JsonData deleteInstallMeter(List<Integer> ids);

    /**
     * 查询挂表
     *
     * @return
     */
    JsonData searchInstallMeter(Integer userId, String distName, String userAddress);

    /**
     * 用户开户
     *
     * @param param
     * @return
     */
    JsonData createAccount(CreateAccountParam param);


}
