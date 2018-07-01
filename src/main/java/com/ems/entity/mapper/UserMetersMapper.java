package com.ems.entity.mapper;

import com.ems.entity.UserMeters;

public interface UserMetersMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserMeters record);

    int insertSelective(UserMeters record);

    UserMeters selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserMeters record);

    int updateByPrimaryKey(UserMeters record);
}