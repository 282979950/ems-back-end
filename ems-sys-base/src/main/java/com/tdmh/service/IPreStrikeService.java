package com.tdmh.service;

import com.tdmh.common.JsonData;
import com.tdmh.entity.StrikeNucleus;
import com.tdmh.entity.User;
import com.tdmh.param.CreateAccountParam;
import com.tdmh.param.CreateArchiveParam;
import com.tdmh.param.InstallMeterParam;
import com.tdmh.param.LockAccountParam;

import java.util.List;

/**
 * 用户服务接口
 *
 * @author qh on 2018/10/20.
 */
public interface IPreStrikeService {

    JsonData selectUserByOrderTypeService(User user,Integer currentEmpId);
    JsonData editUserOrdersService(User user,String  currentEmpName,Integer currentEmpId);
    JsonData selectStrikeNucleusListService(StrikeNucleus strikeNucleus);
    JsonData updateStrikeService(StrikeNucleus strikeNucleus,boolean flag);

}
