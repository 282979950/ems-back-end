package com.tdmh.entity.mapper;

import com.tdmh.entity.UserMeters;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
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