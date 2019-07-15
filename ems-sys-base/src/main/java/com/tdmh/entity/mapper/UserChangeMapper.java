package com.tdmh.entity.mapper;


import com.tdmh.entity.UserChange;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Mapper @Component
public interface UserChangeMapper {

    BigDecimal sumHistoryPurchasingAirVolume(Integer userId);
    BigDecimal sumHistoryTableCode(Integer userId);
    BigDecimal userChangeSumHistoryTableCode(Integer userId);
    int insert(UserChange userChange);
    List<UserChange> selectUserChangeList(Integer userId);


}