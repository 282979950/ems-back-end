package com.tdmh.entity.mapper;

import com.tdmh.entity.Invoice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Liuxia on 2018/10/18.
 */

@Mapper @Component
public interface InvoiceMapper {
    List<Invoice> getAllAssignInvoiceList();
    int insertSelective(Invoice invoice);
    int insertBatch(@Param("invoiceList") List<Invoice> invoiceList);
    List<Invoice> findInvoiceByCodeAndNumber(@Param("invoiceCode") String invoiceCode, @Param("invoiceNumberList") List<String> invoiceNumberList, @Param("invoiceStatus") Integer invoiceStatus);
    int updateInvoiceToEmployee(@Param("invoiceCode") String invoiceCode, @Param("invoiceNumberList") List<String> invoiceNumberList, @Param("empId") Integer empId, @Param("currentEmpId") Integer currentEmpId);
    List<Invoice> getAllPrintCancelInvoiceList();
}
