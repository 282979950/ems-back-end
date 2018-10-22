package com.tdmh.entity.mapper;

import com.tdmh.param.WXUserInfoParam;
import com.tdmh.param.WXUserParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author litairan on 2018/10/21.
 */
@Mapper
@Component
public interface WXMapper {
    List<WXUserParam> getBindUsersByWXUserId(@Param("wxUserId") String wxUserId);

    int bindUser(@Param("wxUserId") String wxUserId, @Param("userId") Integer userId);

    int unBindUser(@Param("wxUserId") String wxUserId, @Param("userId") Integer userId);

    int checkUserExists(@Param("userId") Integer userId, @Param("userName") String userName);

    WXUserInfoParam getUserInfo(@Param("userId")Integer userId);
}
