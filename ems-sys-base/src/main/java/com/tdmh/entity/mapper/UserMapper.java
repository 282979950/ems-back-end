package com.tdmh.entity.mapper;


import com.tdmh.entity.User;
import com.tdmh.entity.UserLock;
import com.tdmh.param.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Mapper @Component
public interface UserMapper {

    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<CreateArchiveParam> getAllArchives();

    int addArchive(CreateArchiveParam param);

    int editArchive(CreateArchiveParam param);

    int deleteArchive(List<User> users);

    List<CreateArchiveParam> searchArchive(@Param("userId") Integer userId, @Param("userDistId") Integer userDistId, @Param("userAddress") String userAddress
            , @Param
            ("userType") Integer userType, @Param("userGasType") Integer userGasType, @Param("userStatus") Integer userStatus);

    List<InstallMeterParam> getAllInstallMeters();

    int updateUserStatus(List<User> users);

    int createAccount(CreateAccountParam param);

    int getAllCount();

    User getUserByIccardId(@Param("iccardId") Integer iccardId);

    User getUserById(@Param("userId") Integer userId);

    List<InstallMeterParam> searchInstallMeter(@Param("userId") Integer userId, @Param("userDistId") Integer userDistId, @Param("userAddress") String userAddress);

    List<LockAccountParam> searchAccountArchive(@Param("userId") Integer userId, @Param("userName") String userName, @Param("iccardId") Integer iccardId,
                                                @Param("userStatus") Integer userStatus);

    int insertUserLock(UserLock userLock);

    List<UserLock> searchLockList(@Param("userId") Integer userId);

    List<CreateArchiveParam> searchAccountById(Integer userId);
    List<User> userChangeList(User user);
    int updateUserById(User record);

    int updateServiceTimesByUserId(Integer userId);

    int getServiceTimesByUserId(Integer userId);

    int updateFillStatus(@Param("userId") Integer userId, @Param("status") Boolean status);
    int updateUserUsable(@Param("userId") Integer userId);
    //查询当天充值类型为	普通订单数据
    List<User> selectUserByOrderType(User user);

    List<AccountQueryParam> searchAccountQueryList(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("distIds") String distIds, @Param("userAddress") String userAddress);

    List<AbnormalUser> searchAbnormalUserList(@Param("userId") Integer userId, @Param("notBuyDayCount") Integer notBuyDayCount, @Param("monthAveGas") BigDecimal monthAveGas, @Param(
            "monthAvePayment") BigDecimal monthAvePayment, @Param("distIds") String distIds, @Param("userAddress") String userAddress, @Param("meterCode") String meterCode);
    List<UserParam> userListByUserQuery();
    List<UserInfoParam> userQuerySearch(UserParam user);

    Integer getUserLockStatusById(Integer userId);
    String getUserStatusNameByUserId(Integer userId);

    List<UserParam> queryUser(@Param("userId") Integer userId, @Param("userName") String userName, @Param("userDistId") Integer userDistId, @Param("userAddress") String userAddress,
                                @Param("userType") Integer userType, @Param("userStatus") Integer userStatus, @Param("meterCode") String meterCode,
                                @Param("cardIdentifier") String cardIdentifier);

    int edit(UserParam userParam);

    UserParam getUserByUserId(@Param("userId") Integer userId);

    List<UserInfoParam> ExportUser(UserParam user);

    int bindCard(CreateAccountParam param);

    void updateAllFreeGasFlag();

    boolean checkFreeGasFlag(@Param("userId") Integer userId);

    void updateFreeGasFlagByUserId(@Param("userId") Integer userId, @Param("flag") Boolean flag);

    /**
     * 微信充值时添加验证，只允许民用户在手机上正常充值
     * @param userId
     * @return
     */
    int userVerify(@Param("userId") Integer userId);
}