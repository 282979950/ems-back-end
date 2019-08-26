package com.tdmh.service;


import com.tdmh.common.JsonData;
import com.tdmh.entity.UserOrders;
import com.tdmh.param.PrePaymentParam;

/**
 * @author Administrator on 2018/10/16.
 */
public interface IReplaceCardService {
    /**
     * 查询所有可换卡的信息
     * @return
     */
    JsonData getAllReplaceCardInformation(Integer pageNum, Integer pageSize);

    JsonData selectFindListByPre(PrePaymentParam param, Integer pageNum, Integer pageSize);

    /**
     * 换卡操作
     * @param param
     * @return
     */
    JsonData supplementCard(PrePaymentParam param, UserOrders userOrders,String name, String userType);

    /**
     * 查询换卡记录
     * @param userId
     * @return
     */
    JsonData searchSupList(Integer userId);
}
