package com.ems.entity.mapper;

import com.ems.entity.User;
import com.ems.param.CreateAccountParam;
import com.ems.param.CreateArchiveParam;
import com.ems.param.InstallMeterParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    List<CreateArchiveParam> searchArchive(@Param("userId") Integer userId, @Param("distName") String distName, @Param("userAddress") String userAddress, @Param
            ("userType") Integer userType, @Param("userGasType") Integer userGasType, @Param("userStatus") Integer userStatus);

    List<InstallMeterParam> getAllInstallMeters();

    int updateUserStatus(List<User> users);

    int createAccount(CreateAccountParam param);

    int getAllCount();

    User getUserByIccardId(@Param("iccardId") Integer iccardId);

    User getUserById(@Param("userId") Integer userId);

    List<InstallMeterParam> searchInstallMeter(@Param("userId")Integer userId, @Param("distName") String distName, @Param("userAddress")String userAddress);
}