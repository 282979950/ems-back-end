package com.tdmh.entity.mapper;


import com.tdmh.entity.UserMeterType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper @Component
public interface UserMeterTypeMapper {

   List<UserMeterType> selectUserMeterTypeByUserId(@Param("userId") Integer userId);
}