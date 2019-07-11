package com.tdmh.entity.mapper;

import com.tdmh.entity.UserCard;
import com.tdmh.param.BindNewCardParam;
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

    UserCard getUserCardByUserIdAndCardId(@Param("userId") Integer userId, @Param("iccardId") Integer iccardId);

    int insert(UserCard card);

    int update(UserCard card);
    /*
    查询记录
     */
    int countUserCardByCardId(String cardId);
    /*
    查询密码
     */
    String userCardPwdByCardId(String cardId);
    /*
    初始化卡，刷新标记
     */
    int initCardPwdBycardId(UserCard card);

    List<CreateAccountParam> getAllSupList(Integer userId);

    int getUserCardByUserIdAndnIcCardIdentifier(@Param("userId") Integer userId, @Param("iccardIdentifier") String iccardIdentifier);

    int getUserCardByYouSelfIdAndnIcCardIdentifier(@Param("userId") Integer userId, @Param("iccardIdentifier") String iccardIdentifier, @Param(
            "niccardIdentifier") String niccardIdentifier);

    int getCardByCardMessage(@Param("userId") Integer userId, @Param("iccardId") Integer iccardId, @Param("iccardIdentifier") String iccardIdentifier);

    List<UserCard> selectUserCardQuery(Integer userId);
    int selectCountUserCard(Integer userId);
    int updateUserCardByUserIdCardStatus(UserCard card);

    BindNewCardParam getBindNewCardParamByUserId(Integer userId);

    boolean checkNewCardIdentifier(String newCardIdentifier);

    List<UserCard> selectUserCardByUserId(@Param("userId") Integer userId,@Param("usable") Boolean usable);

    UserCard getUserCardByCardIdentifier(@Param("cardIdentifier") String cardIdentifier);
    //查询是否存在用户销户记录
    int countAccountCancellation(@Param("userId") Integer userId);
}
