package com.tdmh.service;

import com.tdmh.common.JsonData;
import com.tdmh.entity.UserOrders;
import com.tdmh.param.OperatorDataQuery;
import com.tdmh.param.OrderParam;

import java.util.Date;

/**
 * @author Administrator on 2018/10/20.
 */
public interface IOrderService {

    JsonData searchOrderAndInvoiceList(String userName, String iccardId, String iccardIdentifier, String invoiceCode, String invoiceNumber, Integer pageNum, Integer pageSize, String startDate, String endDate);

    JsonData updateOrderStatus(Integer orderId, Integer orderStatus);

    JsonData BusinessDataQueryService(UserOrders orders,Integer pageNum, Integer pageSize);

    JsonData BusinessDataQuerySearchListService(UserOrders orders);

    JsonData selectHistoryOrdersService(Integer userId);

    void exportBusinessDataQueryListService(UserOrders orders);

    JsonData ReportBusinessDataQueryService(Integer orgId, Integer empId, String startDate, String endDate, Integer pageNum, Integer pageSize);

    JsonData OperatorDataQueryService(OperatorDataQuery dataQuery, Integer pageNum, Integer pageSize);

    JsonData checkNewInvoicePrint(Integer orderId);

    JsonData loadGas(String icCardId);

    JsonData loadGasCallBack(String flowNumber);
}
