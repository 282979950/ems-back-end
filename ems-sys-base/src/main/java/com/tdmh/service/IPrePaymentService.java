package com.tdmh.service;

import com.tdmh.common.JsonData;
import com.tdmh.entity.UserOrders;
import com.tdmh.param.PrePaymentParam;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Lucia on 2018/10/12.
 */
public interface IPrePaymentService {

    JsonData getAllOrderInformation();

    JsonData createUserOrder(UserOrders userOrders);

    JsonData selectFindListByPre(PrePaymentParam param, Integer pageNum, Integer pageSize);

    JsonData verifyCard(PrePaymentParam param);
    JsonData selectFindListArdQueryService(PrePaymentParam param);
    void selectFindListExportArdQueryService(PrePaymentParam param, HttpServletResponse response);

}
