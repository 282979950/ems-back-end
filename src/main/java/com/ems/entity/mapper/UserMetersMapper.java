package com.ems.entity.mapper;

import com.ems.entity.UserMeters;

import java.util.List;

public interface UserMetersMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserMeters record);

    int insertSelective(UserMeters record);

    UserMeters selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserMeters record);

    int updateByPrimaryKey(UserMeters record);

    int installMeter(UserMeters userMeters);

    int updateMeter(UserMeters userMeters);

    UserMeters getUserMeterById(Integer id);

    int deleteInstallMeter(List<UserMeters> userMeters);
}