package com.tdmh.controller;

import com.tdmh.common.JsonData;
import com.tdmh.service.IInvoiceService;
import com.tdmh.util.ShiroUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
    private IInvoiceService invoiceService;

    /**
     * 查询所有的分配的发票编号
     * @return
     */
    @RequiresPermissions("invoice:assign:visit")
    @RequestMapping("/assign/listData.do")
    @ResponseBody
    public JsonData getAllAssignInvoiceList(){
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        return invoiceService.getAllAssignInvoiceList(currentEmpId);
    }

    /**
     * 查询特定的发票
     * @return
     */
    @RequiresPermissions("invoice:assign:retrieve")
    @RequestMapping("/assign/search.do")
    @ResponseBody
    public JsonData searchAssignInvoiceList(@Param("invoiceCode") String invoiceCode, @Param("invoiceNumber") String invoiceNumber){
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        return invoiceService.searchAssignInvoiceList(invoiceCode, invoiceNumber, currentEmpId);
    }

    /**
     * 新增发票
     * @param invoiceCode
     * @param sInvoiceNumber
     * @param eInvoiceNumber
     * @return
     */
    @RequiresPermissions("invoice:assign:add")
    @RequestMapping("/assign/add.do")
    @ResponseBody
    public JsonData addInvoice(@Param("invoiceCode") String invoiceCode, @Param("sInvoiceNumber") Integer sInvoiceNumber,@Param("eInvoiceNumber") Integer eInvoiceNumber){
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        return invoiceService.addInvoice(invoiceCode,sInvoiceNumber,eInvoiceNumber,currentEmpId);
    }

    @RequiresPermissions("invoice:assign:assignment")
    @RequestMapping("/assign/assignment.do")
    @ResponseBody
    public JsonData assignInvoice(@Param("invoiceCode") String invoiceCode, @Param("sInvoiceNumber") Integer sInvoiceNumber,@Param("eInvoiceNumber") Integer eInvoiceNumber,@Param("empId") Integer empId){
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        return invoiceService.assignInvoice(invoiceCode,sInvoiceNumber,eInvoiceNumber,empId,currentEmpId);
    }

    /**
     * 查询已分配后的发票编号
     * @return
     */
    @RequiresPermissions("invoice:printCancel:visit")
    @RequestMapping("/printCancel/listData.do")
    @ResponseBody
    public JsonData getAllPrintCancelInvoiceList(){
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        return invoiceService.getAllPrintCancelInvoiceList(currentEmpId);
    }

    /**
     * 查询特定的已分配的发票
     * @return
     */
    @RequiresPermissions("invoice:printCancel:retrieve")
    @RequestMapping("/printCancel/search.do")
    @ResponseBody
    public JsonData searchPrintCancelInvoiceList(@Param("invoiceCode") String invoiceCode, @Param("invoiceNumber") String invoiceNumber,@Param("empId") Integer empId){
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        return invoiceService.searchPrintCancelInvoiceList(invoiceCode, invoiceNumber, empId, currentEmpId);
    }

    /**
     * 查出打印数据
     * @param orderId
     * @param userId
     * @param printType 代表三种打印，1是正常打印，2是原票补打，3是新票补打
     * @return
     */
    @RequestMapping("/findInvoice.do")
    @ResponseBody
    public JsonData findInvoice(@Param("orderId") Integer orderId , @Param("userId") Integer userId , @Param("printType") Integer printType){
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        return invoiceService.findInvoice(orderId, userId, currentEmpId , printType);
    }

    /**
     * 改变发票状态
     * @param orderId
     * @param invoiceCode
     * @param invoiceNumber
     * @return
     */
    @RequiresPermissions(value = {"recharge:order:print","recharge:order:old","recharge:order:new"},logical = Logical.OR)
    @RequestMapping("/print.do")
    @ResponseBody
    public JsonData printInvoice(@Param("orderId") Integer orderId, @Param("invoiceCode") String invoiceCode, @Param("invoiceNumber") String invoiceNumber){
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        return invoiceService.printInvoice(orderId, invoiceCode, invoiceNumber, currentEmpId);
    }


    /**
     * 注销已打印的发票
     * @param orderId
     * @param invoiceCode
     * @param invoiceNumber
     * @return
     */
    @RequiresPermissions("recharge:order:cancel")
    @RequestMapping("order/cancel.do")
    @ResponseBody
    public JsonData cancelInvoice(@Param("orderId") Integer orderId,@Param("userId") Integer userId, @Param("invoiceCode") String invoiceCode, @Param("invoiceNumber") String invoiceNumber){
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        return invoiceService.cancelInvoice(orderId, userId, invoiceCode, invoiceNumber, currentEmpId);
    }

    /**
     * 注销未打印的发票
     * @param invoiceCode
     * @param invoiceNumber
     * @return
     */
    @RequiresPermissions("recharge:printCancel:cancel")
    @RequestMapping("printCancel/cancel.do")
    @ResponseBody
    public JsonData cancelNotPrintInvoice(@Param("invoiceCode") String invoiceCode, @Param("invoiceNumber") String invoiceNumber){
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        return invoiceService.cancelNotPrintInvoice(invoiceCode, invoiceNumber, currentEmpId);
    }

}
