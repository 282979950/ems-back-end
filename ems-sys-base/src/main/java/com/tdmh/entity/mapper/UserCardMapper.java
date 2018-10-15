package com.tdmh.entity.mapper;

import com.tdmh.entity.UserCard;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author Administrator on 2018/10/10.
 */
@Mapper @Component
public interface UserCardMapper {
    int insert(UserCard card);
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
}
