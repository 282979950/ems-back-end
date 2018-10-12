package com.tdmh.entity.mapper;

import com.tdmh.entity.UserCard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author Administrator on 2018/10/10.
 */
@Mapper @Component
public interface UserCardMapper {

    UserCard getUserCardByUserIdAndCardId(@Param("userId") Integer userId , @Param("iccardId") Integer iccardId);

    int insert(UserCard card);

    int update(UserCard card);
}
