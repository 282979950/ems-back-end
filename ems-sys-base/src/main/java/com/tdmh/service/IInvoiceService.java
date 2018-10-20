package com.tdmh.service;

import com.tdmh.common.JsonData;

/**
 * @author Liuxia on 2018/10/18.
 */
public interface IInvoiceService {
    JsonData getAllAssignInvoiceList();

    JsonData addInvoice(String invoiceCode, Integer sInvoiceNumber, Integer eInvoiceNumber, Integer currentEmpId);

    JsonData assignInvoice(String invoiceCode, Integer sInvoiceNumber, Integer eInvoiceNumber,Integer empId, Integer currentEmpId);

    JsonData getAllPrintCancelInvoiceList();

    JsonData searchAssignInvoiceList(String invoiceCode,String invoiceNumber);

    JsonData searchPrintCancelInvoiceList(String invoiceCode,String invoiceNumber,Integer empId);

    JsonData findInvoice(Integer orderId, Integer currentEmpId);

    JsonData printInvoice(Integer orderId, String invoiceCode, String invoiceNumber, Integer currentEmpId);
}
