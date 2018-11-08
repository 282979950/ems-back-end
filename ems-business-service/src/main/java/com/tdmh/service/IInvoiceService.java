package com.tdmh.service;

import com.tdmh.common.JsonData;

/**
 * @author Liuxia on 2018/10/18.
 */
public interface IInvoiceService {
    JsonData getAllAssignInvoiceList(Integer currentEmpId);

    JsonData addInvoice(String invoiceCode, Integer sInvoiceNumber, Integer eInvoiceNumber, Integer currentEmpId);

    JsonData assignInvoice(String invoiceCode, Integer sInvoiceNumber, Integer eInvoiceNumber, Integer empId, Integer currentEmpId);

    JsonData getAllPrintCancelInvoiceList(Integer currentEmpId);

    JsonData searchAssignInvoiceList(String invoiceCode, String invoiceNumber, Integer currentEmpId);

    JsonData searchPrintCancelInvoiceList(String invoiceCode, String invoiceNumber, Integer empId, Integer currentEmpId);

    JsonData findInvoice(Integer orderId, Integer userId, Integer currentEmpId, Integer printType);

    JsonData printInvoice(Integer orderId, String invoiceCode, String invoiceNumber, Integer currentEmpId);

    JsonData cancelInvoice(Integer orderId, Integer userId, String invoiceCode, String invoiceNumber, Integer currentEmpId);

    JsonData cancelNotPrintInvoice(String invoiceCode, String invoiceNumber, Integer currentEmpId);
}
