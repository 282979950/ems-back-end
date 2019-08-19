package com.tdmh.service;

import com.tdmh.common.JsonData;
import com.tdmh.entity.User;
import com.tdmh.param.*;

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
    JsonData getAllArchives(Integer pageNum, Integer pageSize);

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
    JsonData searchArchive(Integer userId, Integer userDistId, String userAddress, Integer userType, Integer userGasType, Integer userStatus, Integer pageNum,
                           Integer pageSize);

    /**
     * @return
     */
    JsonData getAllInstallMeters(Integer pageNum, Integer pageSize);

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
    JsonData searchInstallMeter(Integer userId, Integer userDistId, String userAddress, Integer pageNum, Integer pageSize);

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
    JsonData getAllNotAccountArchive(Integer pageNum, Integer pageSize);

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
    JsonData searchAllNotAccountArchive(Integer userId, Integer userDistId, String userAddress, Integer userType, Integer userGasType, Integer pageNum, Integer pageSize);

    /**
     * 查询所有可以锁定和解锁的用户信息
     *
     * @return
     */
    JsonData getAllAccountArchive(Integer pageNum, Integer pageSize);

    /**
     * 条件查询所有可以锁定和解锁的用户信息
     *
     * @param userId
     * @param userName
     * @param iccardId
     * @return
     */
    JsonData searchAllAccountArchive(Integer userId, String userName, Integer iccardId, Integer pageNum, Integer pageSize);

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
    JsonData cardService(String cardId);

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
    JsonData userChangeService(User user, Integer pageNum, Integer pageSize);

    /**
     * 开户账户查询
     * @return
     */
    JsonData searchAccountQueryList(String startDate, String endDate, Integer userDistId, String userAddress, Integer pageNum, Integer pageSize);

    /**
     * 查询异常用户
     * @return
     */
    JsonData searchAbnormalUserList(Integer notBuyDayCount, BigDecimal monthAveGas, BigDecimal monthAvePayment, Integer userDistId, String userAddress,
                                    String meterCode,Integer pageNum, Integer pageSize);

    JsonData exportAccountQueryList(String startDate, String endDate, Integer userDistId, String userAddress);
    /**
     * 查询统计（用户信息查询）
     */
    JsonData userQueryListService(Integer pageNum, Integer pageSize);
    /**
     * 查询统计（筛选查询）
     */
    JsonData userQuerySearchService(UserParam user, Integer pageNum, Integer pageSize);
    /**
     * 查询统计（用户卡相关查询）
     */
    JsonData selectHistoryUserCardQueryService(Integer userId);

    int updateServiceTimesByUserId(Integer userId);

    JsonData getBindNewCardParamByUserId(Integer userId);

    JsonData exportUserQuerySearchService(UserParam user);

    JsonData exportAbnormalUserList(Integer notBuyDayCount, BigDecimal monthAveGas, BigDecimal monthAvePayment, Integer userDistId, String userAddress, String meterCode);

    Integer getUserLockStatusById(Integer userId);

    JsonData exportAbnormalUserWithPageInfo(Integer notBuyDayCount, BigDecimal monthAveGas, BigDecimal monthAvePayment, Integer userDistId, String userAddress, String meterCode, Integer pageNum, Integer pageSize);

    JsonData exportAccountQueryWithPageInfo(String startDate, String endDate, Integer userDistId, String userAddress, Integer pageNum, Integer pageSize);

    JsonData exportUserQueryWithPageInfo(UserParam user, Integer pageNum, Integer pageSize);
    /**
     * 发起新增时根据用户号查看该户此时状态
     */
    String getUserStatusNameByUserId(Integer userId);
    /**
     * 查询统计（用户表相关查询）
     */
    JsonData selectUserMeterTypeService(Integer userId);

    JsonData listData(Integer userId, Integer userDistId, String userAddress, Integer userType, Integer userStatus, String meterCode, String cardIdentifier,
                      Integer pageNum, Integer pageSize);

    JsonData edit(UserParam userParam);

    JsonData ExportUserQueryService(User user);

    JsonData bindCard(CreateAccountParam param);

    JsonData checkFreeGasFlag(Integer userId);
}
