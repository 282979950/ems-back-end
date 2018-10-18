package com.tdmh.entity.mapper;


import com.tdmh.entity.User;
import com.tdmh.entity.UserChange;
import com.tdmh.entity.UserLock;
import com.tdmh.param.CreateAccountParam;
import com.tdmh.param.CreateArchiveParam;
import com.tdmh.param.InstallMeterParam;
import com.tdmh.param.LockAccountParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Mapper @Component
public interface UserChangeMapper {

    BigDecimal sumHistoryPurchasingAirVolume(Integer userId);
    BigDecimal sumHistoryTableCode(Integer userId);
    int insert(UserChange userChange);

}