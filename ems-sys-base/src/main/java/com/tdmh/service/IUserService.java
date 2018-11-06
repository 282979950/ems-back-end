package com.tdmh.service;

import com.tdmh.common.JsonData;
import com.tdmh.entity.User;
import com.tdmh.param.CreateAccountParam;
import com.tdmh.param.CreateArchiveParam;
import com.tdmh.param.InstallMeterParam;
import com.tdmh.param.LockAccountParam;

import java.math.BigDecimal;
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
     * @param userDistId
     * @param userAddress
     * @param userType
     * @param userGasType
     * @param userStatus
     * @return
     */
    JsonData searchArchive(Integer userId, Integer userDistId, String userAddress, Integer userType, Integer userGasType, Integer userStatus);

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
    JsonData deleteInstallMeter(List<Integer> ids, Integer currentEmpId);

    /**
     * 查询挂表
     *
     * @return
     */
    JsonData searchInstallMeter(Integer userId, Integer userDistId, String userAddress);

    /**
     * 用户开户
     *
     * @param param
     * @return
     */
    JsonData createAccount(CreateAccountParam param);

    /**
     * 依据用户ID获取用户
     *
     * @param userId
     * @return
     */
    User getUserById(Integer userId);

    /**
     * 查询所有未开户的信息
     *
     * @return
     */
    JsonData getAllNotAccountArchive();

    /**
     * 条件查询已开户的信息
     *
     * @param userId
     * @param userDistId
     * @param userAddress
     * @param userType
     * @param userGasType
     * @return
     */
    JsonData searchAllNotAccountArchive(Integer userId, Integer userDistId, String userAddress, Integer userType, Integer userGasType);

    /**
     * 查询所有可以锁定和解锁的用户信息
     *
     * @return
     */
    JsonData getAllAccountArchive();

    /**
     * 条件查询所有可以锁定和解锁的用户信息
     *
     * @param userId
     * @param userName
     * @param iccardId
     * @return
     */
    JsonData searchAllAccountArchive(Integer userId, String userName, Integer iccardId);

    /**
     * 锁定/解锁用户
     *
     * @return
     */
    JsonData updateLockStatus(LockAccountParam param);

    /**
     * 根据用户查询锁定记录
     *
     * @param userId
     */
    JsonData searchLockList(Integer userId);

    /**
     * 获取需要初始化的相关数据相关数据
     */
    JsonData cardService(Integer cardId);

    /**
     * 初始化卡
     */
    JsonData cardInitService(Integer cardId, String result);

    /**
     * 根据户号查询已开户的信息
     *
     * @param userId
     * @return
     */
    JsonData searchAccountById(Integer userId);

    /**
     * 更新维修补气状态(true为需要补气，false为不需要补气)
     *
     * @param userId
     * @return
     */
    int updateFillStatus(Integer userId, Boolean status);

    /**
     * 查询所有已开户相关用户信息
     */
    JsonData userChangeService(User user);

    /**
     * 开户账户查询
     * @return
     */
    JsonData searchAccountQueryList(String startDate,String endDate, Integer userDistId, String userAddress);

    /**
     * 查询异常用户
     * @return
     */
    JsonData searchAbnormalUserList(Integer notBuyDayCount, BigDecimal monthAveGas, BigDecimal monthAvePayment, Integer userDistId, String userAddress);

    void exportAccountQueryList(String startDate,String endDate, Integer userDistId, String userAddress);
    /**
     * 查询统计（用户信息查询）
     */
    JsonData userQueryListService();
    /**
     * 查询统计（筛选查询）
     */
    JsonData userQuerySearchService(User user);
    /**
     * 查询统计（用户卡相关查询）
     */
    JsonData selectHistoryUserCardQueryService(Integer userId);

    int updateServiceTimesByUserId(Integer userId);

    JsonData getBindNewCardParamByUserId(Integer userId);

    void exportUserQuerySearchService(User user);

    void exportAbnormalUserList(Integer notBuyDayCount, BigDecimal monthAveGas, BigDecimal monthAvePayment, Integer userDistId, String userAddress);
}
