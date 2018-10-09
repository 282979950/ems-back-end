package com.tdmh.entity.mapper;


import com.tdmh.entity.User;
import com.tdmh.entity.UserLock;
import com.tdmh.param.CreateAccountParam;
import com.tdmh.param.CreateArchiveParam;
import com.tdmh.param.InstallMeterParam;
import com.tdmh.param.LockAccountParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
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

    List<CreateArchiveParam> searchArchive(@Param("userId") Integer userId, @Param("userDistId") Integer userDistId, @Param("userAddress") String userAddress, @Param
            ("userType") Integer userType, @Param("userGasType") Integer userGasType, @Param("userStatus") Integer userStatus);

    List<InstallMeterParam> getAllInstallMeters();

    int updateUserStatus(List<User> users);

    int createAccount(CreateAccountParam param);

    int getAllCount();

    User getUserByIccardId(@Param("iccardId") Integer iccardId);

    User getUserById(@Param("userId") Integer userId);

    List<InstallMeterParam> searchInstallMeter(@Param("userId") Integer userId, @Param("userDistId") Integer userDistId, @Param("userAddress") String userAddress);

    List<LockAccountParam> searchAccountArchive(@Param("userId") Integer userId, @Param("userName") String userName, @Param("iccardId") Integer iccardId, @Param("userStatus") Integer userStatus);

    int insertUserLock(UserLock userLock);

    List<UserLock> searchLockList(@Param("userId") Integer userId);
}