package com.tdmh.service;

import com.tdmh.common.JsonData;

/**
 * @author Administrator on 2018/10/20.
 */
public interface IOrderService {

    JsonData searchOrderAndInvoiceList(String userName, String iccardId, String iccardIdentifier, String invoiceCode, String invoiceNumber);

    JsonData updateOrderStatus(Integer orderId ,Integer orderStatus);
}
