package com.tdmh.entity.mapper;

import com.tdmh.entity.Invoice;
import com.tdmh.param.InvoiceParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Liuxia on 2018/10/18.
 */

@Mapper @Component
public interface InvoiceMapper {
    List<Invoice> getAllAssignInvoiceList(@Param("invoiceCode") String invoiceCode, @Param("invoiceNumber") String invoiceNumber);
    int insertSelective(Invoice invoice);
    int insertBatch(@Param("invoiceList") List<Invoice> invoiceList);
    List<Invoice> findInvoiceByCodeAndNumber(@Param("invoiceCode") String invoiceCode, @Param("invoiceNumberList") List<String> invoiceNumberList, @Param("invoiceStatus") Integer invoiceStatus);
    int updateInvoiceToEmployee(@Param("invoiceCode") String invoiceCode, @Param("invoiceNumberList") List<String> invoiceNumberList, @Param("empId") Integer empId, @Param("currentEmpId") Integer currentEmpId);
    List<Invoice> getAllPrintCancelInvoiceList(@Param("invoiceCode") String invoiceCode, @Param("invoiceNumber") String invoiceNumber,@Param("empId") Integer empId);
    Invoice findCurrentInvoice(@Param("orderId") Integer orderId);
    Invoice findInvoice(@Param("currentEmpId") Integer currentEmpId);
    int printInvoice(@Param("orderId") Integer orderId, @Param("invoiceCode") String invoiceCode, @Param("invoiceNumber") String invoiceNumber, @Param("currentEmpId") Integer currentEmpId);
    int cancelInvoice(@Param("invoiceCode") String invoiceCode, @Param("invoiceNumber") String invoiceNumber , @Param("currentEmpId")Integer currentEmpId);
}
