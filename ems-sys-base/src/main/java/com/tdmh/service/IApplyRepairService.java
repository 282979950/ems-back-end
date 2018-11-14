package com.tdmh.service;

import com.tdmh.common.JsonData;
import com.tdmh.param.ApplyRepairParam;

import java.util.List;

/**
 * 报修单管理接口
 *
 * @author litairan on 2018/11/12.
 */
public interface IApplyRepairService {

    /**
     * 查询全部报修单
     *
     * @return
     */
    JsonData listData();

    /**
     * 新增报修单
     *
     * @param param
     * @return
     */
    JsonData create(ApplyRepairParam param);

    /**
     * 编辑报修单
     *
     * @param param
     * @return
     */
    JsonData update(ApplyRepairParam param);

    /**
     * 删除报修单
     *
     * @param ids
     * @return
     */
    JsonData delete(List<Integer> ids, Integer currentEmpId);

    /**
     * 查询报修单
     *
     * @return
     */
    JsonData search(Integer userId, String userName, String userPhone, String userTelPhone);

    /**
     * 获取报修用户信息
     *
     * @param userId
     * @return
     */
    JsonData getApplyRepairUserInfoById(Integer userId);
}