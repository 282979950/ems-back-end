package com.tdmh.service;

import com.tdmh.common.JsonData;
import com.tdmh.entity.UserOrders;

/**
 * @author Lucia on 2018/10/12.
 */
public interface IPrePaymentService {

    JsonData getAllOrderInformation();

    JsonData createUserOrder(UserOrders userOrders);
}
