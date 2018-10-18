package com.tdmh.entity.mapper;

import com.tdmh.entity.UserCard;
import com.tdmh.param.CreateAccountParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Administrator on 2018/10/10.
 */
@Mapper @Component
public interface UserCardMapper {

    UserCard getUserCardByUserIdAndCardId(@Param("userId") Integer userId , @Param("iccardId") Integer iccardId);

    int insert(UserCard card);

    int update(UserCard card);
    /*
    查询记录
     */
    int countUserCardBycardId(int cardId);
    /*
    查询密码
     */
    String userCardPwdBycardId(int cardId);
    /*
    初始化卡，刷新标记
     */
    int initCardPwdBycardId(UserCard card);

    List<CreateAccountParam> getAllSupList(Integer userId);

    int getUserCardByUserIdAndnIcCardIdentifier(@Param("userId") Integer userId , @Param("iccardIdentifier") String iccardIdentifier);

    int getUserCardByYouSelfIdAndnIcCardIdentifier(@Param("userId") Integer userId ,@Param("iccardIdentifier") String iccardIdentifier, @Param("niccardIdentifier") String niccardIdentifier);
}
