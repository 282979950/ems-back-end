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
    //查询充值类型为	普通订单和超用补缴订单数据
    List<User>  selectUserByOrderType(User user);

    List<AccountQueryParam> searchAccountQueryList(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("distIds") String distIds, @Param("userAddress") String userAddress);

    List<AbnormalUser> searchAbnormalUserList(@Param("notBuyDayCount") Integer notBuyDayCount, @Param("monthAveGas") BigDecimal monthAveGas, @Param(
            "monthAvePayment") BigDecimal monthAvePayment, @Param("distIds") String distIds, @Param("userAddress") String userAddress);
    List<User> userListByUserQuery();
    List<User> userQuerySearch(User user);

    boolean getUserLockStatusById(Integer userId);
}