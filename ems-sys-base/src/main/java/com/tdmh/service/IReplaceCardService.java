package com.tdmh.service;


import com.tdmh.common.JsonData;
import com.tdmh.entity.UserOrders;
import com.tdmh.param.PrePaymentParam;

/**
 * @author Administrator on 2018/10/16.
 */
public interface IReplaceCardService {
    JsonData getAllReplaceCardInformation();

    JsonData selectFindListByPre(PrePaymentParam param);

    JsonData supplementCard(PrePaymentParam param, UserOrders userOrders);
}
