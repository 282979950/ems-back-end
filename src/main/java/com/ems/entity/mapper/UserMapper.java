package com.ems.entity.mapper;

import com.ems.entity.User;
import com.ems.param.CreateAccountParam;
import com.ems.param.CreateArchiveParam;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int createArchive(CreateArchiveParam param);

    int createAccount(CreateAccountParam param);

    int getAllCount();

    User getUserByIccardId(Integer iccardId);
}