package com.tdmh.service.impl;


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
    JsonData getAllReplaceCardInformation();

    JsonData selectFindListByPre(PrePaymentParam param);

    /**
     * 换卡操作
     * @param param
     * @return
     */
    JsonData supplementCard(PrePaymentParam param, UserOrders userOrders);

    /**
     * 查询换卡记录
     * @param userId
     * @return
     */
    JsonData searchSupList(Integer userId);
}
