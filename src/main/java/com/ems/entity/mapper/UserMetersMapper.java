package com.ems.entity.mapper;

import com.ems.entity.UserMeters;

public interface UserMetersMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserMeters record);

    int insertSelective(UserMeters record);

    UserMeters selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserMeters record);

    int updateByPrimaryKey(UserMeters record);

    int installMeter(UserMeters userMeters);
}