package com.tdmh.controller;

import com.tdmh.common.JsonData;
import com.tdmh.service.IInvoiceService;
import com.tdmh.util.ShiroUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Liuxia on 2018/10/18.
 */
@Controller
@RequestMapping("")
public class InvoiceController {

    @Autowired
    private IInvoiceService iInvoiceService;

    /**
     * 查询分配的发票编号
     * @return
     */
    @RequestMapping("/assign/listData.do")
    @ResponseBody
    public JsonData getAllAssignInvoiceList(){
        return iInvoiceService.getAllAssignInvoiceList();
    }

    /**
     * 新增发票
     * @param invoiceCode
     * @param sInvoiceNumber
     * @param eInvoiceNumber
     * @return
     */
    @RequestMapping("/assign/add.do")
    @ResponseBody
    public JsonData addInvoice(@Param("invoiceCode") String invoiceCode, @Param("sInvoiceNumber") Integer sInvoiceNumber,@Param("eInvoiceNumber") Integer eInvoiceNumber){
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        return iInvoiceService.addInvoice(invoiceCode,sInvoiceNumber,eInvoiceNumber,currentEmpId);
    }

    @RequestMapping("/assign/assignment.do")
    @ResponseBody
    public JsonData assignInvoice(@Param("invoiceCode") String invoiceCode, @Param("sInvoiceNumber") Integer sInvoiceNumber,@Param("eInvoiceNumber") Integer eInvoiceNumber,@Param("empId") Integer empId){
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        return iInvoiceService.assignInvoice(invoiceCode,sInvoiceNumber,eInvoiceNumber,empId,currentEmpId);
    }
}
