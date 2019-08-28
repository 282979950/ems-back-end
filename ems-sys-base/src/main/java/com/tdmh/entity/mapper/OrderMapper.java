package com.tdmh.entity.mapper;

import com.tdmh.param.InvoiceParam;
import com.tdmh.param.OrderParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Administrator on 2018/10/20.
 */
@Mapper @Component
public interface OrderMapper {
    List<OrderParam> searchOrderAndInvoiceList(@Param("userName") String userName, @Param("iccardId") String iccardId,
                                               @Param("iccardIdentifier") String iccardIdentifier, @Param("invoiceCode") String invoiceCode,
                                               @Param("invoiceNumber") String invoiceNumber, @Param("startDate") String startDate,
                                               @Param("endDate") String endDate);

    InvoiceParam findOrderById(@Param("orderId") Integer orderId);

    int updateOrderStatus(@Param("orderId") Integer orderId, @Param("orderStatus") Integer orderStatus);

    int hasAuthorityToInvoice(@Param("orderId") Integer orderId, @Param("userId") Integer userId);

    List<OrderParam> selectReportBusinessDataQuery(OrderParam record);

    int checkNewInvoicePrint(@Param("orderId") Integer orderId);
}
