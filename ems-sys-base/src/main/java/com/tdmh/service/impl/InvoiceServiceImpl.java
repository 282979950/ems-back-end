package com.tdmh.service.impl;

import com.google.common.collect.Lists;
import com.tdmh.common.JsonData;
import com.tdmh.entity.Invoice;
import com.tdmh.entity.mapper.InvoiceMapper;
import com.tdmh.exception.ParameterException;
import com.tdmh.service.IInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Liuxia on 2018/10/18.
 */
@Service
public class InvoiceServiceImpl implements IInvoiceService {

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Override
    public JsonData getAllAssignInvoiceList() {
        List<Invoice> list = invoiceMapper.getAllAssignInvoiceList();
        return list == null || list.size() == 0 ? JsonData.successMsg("暂无分配的发票") : JsonData.successData(list);
    }

    @Override
    @Transactional
    public JsonData addInvoice(String invoiceCode, Integer sInvoiceNumber, Integer eInvoiceNumber, Integer currentEmpId) {
        if (sInvoiceNumber > eInvoiceNumber) {
            return JsonData.fail("发票起始号码不能大于终止号码");
        }
        List<Invoice> invoiceList = Lists.newArrayListWithCapacity(eInvoiceNumber - sInvoiceNumber + 1);
        for (int i = sInvoiceNumber; i <= eInvoiceNumber; i++) {
            Invoice invoice = new Invoice();
            invoice.setInvoiceCode(invoiceCode);
            invoice.setInvoiceNumber(String.format("%08d",i));
            invoice.setInvoiceStatus(1);//已生成未分配操作员
            invoice.setCreateBy(currentEmpId);
            invoice.setUpdateBy(currentEmpId);
            invoice.setUsable(true);
            invoiceList.add(invoice);
        }
        List<String> invoiceNumberList = Lists.newArrayListWithCapacity(eInvoiceNumber - sInvoiceNumber + 1);
        for (int i = sInvoiceNumber; i <= eInvoiceNumber; i++) {
            invoiceNumberList.add(String.format("%08d",i));
        }
        List<Invoice> hasinvoiceList = invoiceMapper.findInvoiceByCodeAndNumber(invoiceCode, invoiceNumberList, null);
        if(hasinvoiceList != null && hasinvoiceList.size()>0){
            return JsonData.fail("包含已经生成过的发票，录入发票失败");
        }
        int count = invoiceMapper.insertBatch(invoiceList);
        if (count == eInvoiceNumber - sInvoiceNumber + 1) {
            return JsonData.successMsg("录入发票成功");
        }
        return JsonData.successMsg("录入发票失败");
    }

    @Override
    @Transactional
    public JsonData assignInvoice(String invoiceCode, Integer sInvoiceNumber, Integer eInvoiceNumber, Integer empId, Integer currentEmpId) {
        if (sInvoiceNumber > eInvoiceNumber) {
            return JsonData.fail("发票起始号码不能大于终止号码");
        }
        List<String> invoiceNumberList = Lists.newArrayListWithCapacity(eInvoiceNumber - sInvoiceNumber + 1);
        for (int i = sInvoiceNumber; i <= eInvoiceNumber; i++) {
            invoiceNumberList.add(String.format("%08d",i));
        }
        List<Invoice> invoiceList = invoiceMapper.findInvoiceByCodeAndNumber(invoiceCode, invoiceNumberList, 2);
        if (invoiceList != null && invoiceList.size() != 0) {
            return JsonData.fail("号码段中包括已分配的发票");
        }
        List<Invoice> invoiceList2 = invoiceMapper.findInvoiceByCodeAndNumber(invoiceCode, invoiceNumberList, 1);
        if (invoiceList2 == null || invoiceList2.size() == 0) {
            return JsonData.fail("此发票号码段并未生成");
        }
        if (invoiceList2.size() != (eInvoiceNumber - sInvoiceNumber + 1)) {
            return JsonData.fail("号码段中包括未生成的发票");
        }
        int count = invoiceMapper.updateInvoiceToEmployee(invoiceCode,invoiceNumberList,empId, currentEmpId);
        if(count != (eInvoiceNumber - sInvoiceNumber + 1)){
            throw new ParameterException("分配操作人员失败");
        }
        return JsonData.successMsg("分配发票成功");
    }

    @Override
    public JsonData getAllPrintCancelInvoiceList() {
        List<Invoice> list = invoiceMapper.getAllPrintCancelInvoiceList();
        return list == null || list.size() == 0 ? JsonData.successMsg("暂无分配的发票") : JsonData.successData(list);
    }
}
