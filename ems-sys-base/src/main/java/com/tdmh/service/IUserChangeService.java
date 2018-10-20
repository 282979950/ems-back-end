package com.tdmh.service;

import com.tdmh.common.JsonData;
import com.tdmh.entity.User;
import com.tdmh.entity.UserChange;
import com.tdmh.param.CreateAccountParam;
import com.tdmh.param.CreateArchiveParam;
import com.tdmh.param.InstallMeterParam;
import com.tdmh.param.LockAccountParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用户服务接口
 *
 * @author qh on 2018/10/16.
 */
public interface IUserChangeService {

    /**
     * 账户变更，清算处理
     */
    JsonData userChangeSettlementService(UserChange userChange, User user,Integer currentEmpId,double userMoney,double OrderSupplement);
    /**
     * 账户消户处理
     */
    JsonData userEliminationHeadService(User user,BigDecimal userMoney,BigDecimal OrderSupplement,int flage,Integer Id);
    /**
     *查询产生变更记录表
     */
    JsonData selectUserChangeListService(Integer user);
}
