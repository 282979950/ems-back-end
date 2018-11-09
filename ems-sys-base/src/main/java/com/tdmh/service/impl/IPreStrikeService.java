package com.tdmh.service.impl;

import com.tdmh.common.JsonData;
import com.tdmh.entity.StrikeNucleus;
import com.tdmh.entity.User;

/**
 * 用户服务接口
 *
 * @author qh on 2018/10/20.
 */
public interface IPreStrikeService {

    JsonData selectUserByOrderTypeService(User user, Integer currentEmpId);
    JsonData editUserOrdersService(User user, String currentEmpName, Integer currentEmpId);
    JsonData selectStrikeNucleusListService(StrikeNucleus strikeNucleus);
    JsonData updateStrikeService(StrikeNucleus strikeNucleus, boolean flag);

}
