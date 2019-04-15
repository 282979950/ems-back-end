package com.tdmh.service.impl;

import com.google.common.collect.Lists;
import com.tdmh.common.JsonData;
import com.tdmh.entity.Invoice;
import com.tdmh.entity.mapper.EmployeeMapper;
import com.tdmh.entity.mapper.InvoiceMapper;
import com.tdmh.entity.mapper.OrderMapper;
import com.tdmh.entity.mapper.SysRoleMapper;
import com.tdmh.exception.ParameterException;
import com.tdmh.param.EmployeeParam;
import com.tdmh.param.InvoiceParam;
import com.tdmh.param.SysRoleParam;
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

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public JsonData getAllAssignInvoiceList(Integer currentEmpId) {
        EmployeeParam emp = employeeMapper.getEmpById(currentEmpId);
        if(emp == null){
            return JsonData.fail("该操作员不存在");
        }
        SysRoleParam role = sysRoleMapper.getRoleById(emp.getRoleId());
        List<Invoice> list =  Lists.newArrayList();
        if(role.getIsAdmin()) {
            list = invoiceMapper.getAllAssignInvoiceList(null, null,null);
        }else{
            list = invoiceMapper.getAllAssignInvoiceList(null, null, currentEmpId);
        }
        return list == null || list.size() == 0 ? JsonData.successMsg("暂无分配的发票") : JsonData.successData(list);
    }

    @Override
    public JsonData searchAssignInvoiceList(String invoiceCode, String invoiceNumber, Integer currentEmpId) {
        EmployeeParam emp = employeeMapper.getEmpById(currentEmpId);
        if(emp == null){
            return JsonData.fail("该操作员不存在");
        }
        SysRoleParam role = sysRoleMapper.getRoleById(emp.getRoleId());
        List<Invoice> list = Lists.newArrayList();
        if(role.getIsAdmin()) {
            list = invoiceMapper.getAllAssignInvoiceList(invoiceCode, invoiceNumber,null);
        }else {
            list = invoiceMapper.getAllAssignInvoiceList(invoiceCode, invoiceNumber, currentEmpId);
        }
        return list == null || list.size() == 0 ? JsonData.successMsg("暂无分配的发票") : JsonData.success(list,"查询成功");
    }

    @Override
    @Transactional
    public JsonData addInvoice(String invoiceCode, Integer sInvoiceNumber, Integer eInvoiceNumber, Integer currentEmpId) {
        if(invoiceCode == null || invoiceCode == ""){
            return JsonData.fail("发票代码不能为空");
        }
        if(sInvoiceNumber == null){
            return JsonData.fail("发票起始号码不能为空");
        }
        if(eInvoiceNumber == null){
            return JsonData.fail("发票终止号码不能为空");
        }
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
        if(invoiceCode == null || invoiceCode == ""){
            return JsonData.fail("发票代码不能为空");
        }
        if(sInvoiceNumber == null){
            return JsonData.fail("发票起始号码不能为空");
        }
        if(eInvoiceNumber == null){
            return JsonData.fail("发票终止号码不能为空");
        }
        if (sInvoiceNumber > eInvoiceNumber) {
            return JsonData.fail("发票起始号码不能大于终止号码");
        }
        if(empId == null){
            return JsonData.fail("发票所属员工不能为空");
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
            return JsonData.fail("此发票代码或者号码段并未生成");
        }
        if (invoiceList2.size() != (eInvoiceNumber - sInvoiceNumber + 1)) {
            return JsonData.fail("此代码或号码段中包括未生成的发票");
        }
        int count = invoiceMapper.updateInvoiceToEmployee(invoiceCode,invoiceNumberList,empId, currentEmpId);
        if(count != (eInvoiceNumber - sInvoiceNumber + 1)){
            throw new ParameterException("分配操作人员失败");
        }
        return JsonData.successMsg("分配发票成功");
    }

    @Override
    public JsonData getAllPrintCancelInvoiceList(Integer currentEmpId) {
        EmployeeParam emp = employeeMapper.getEmpById(currentEmpId);
        if(emp == null){
            return JsonData.fail("该操作员不存在");
        }
        SysRoleParam role = sysRoleMapper.getRoleById(emp.getRoleId());
        List<Invoice> list = Lists.newArrayList();
        if(role.getIsAdmin()) {
            list = invoiceMapper.getAllPrintCancelInvoiceList(null, null, null, null);
        }else {
            list = invoiceMapper.getAllPrintCancelInvoiceList(null, null, null, currentEmpId);
        }
        return list == null || list.size() == 0 ? JsonData.successMsg("暂无分配的发票") : JsonData.successData(list);
    }

    @Override
    public JsonData searchPrintCancelInvoiceList(String invoiceCode,String invoiceNumber,Integer empId, Integer currentEmpId){
        EmployeeParam emp = employeeMapper.getEmpById(currentEmpId);
        if(emp == null){
            return JsonData.fail("该操作员不存在");
        }
        SysRoleParam role = sysRoleMapper.getRoleById(emp.getRoleId());
        List<Invoice> list = Lists.newArrayList();
        if(role.getIsAdmin()) {
            list = invoiceMapper.getAllPrintCancelInvoiceList(invoiceCode, invoiceNumber, empId, null);
        }else {
            list = invoiceMapper.getAllPrintCancelInvoiceList(invoiceCode, invoiceNumber, empId, currentEmpId);
        }
        return list == null || list.size() == 0 ? JsonData.successMsg("暂无分配的发票") : JsonData.success(list,"查询成功");
    }

    @Override
    public JsonData findInvoice(Integer orderId, Integer userId, Integer currentEmpId , Integer printType) {

        EmployeeParam emp = employeeMapper.getEmpById(currentEmpId);
        if(emp == null){
            return JsonData.fail("该操作员不存在");
        }
        if(printType == 2 || printType == 3) {
            SysRoleParam role = sysRoleMapper.getRoleById(emp.getRoleId());
            if (!role.getIsAdmin()) {
                int resultCount = orderMapper.hasAuthorityToInvoice(orderId, userId);
                if (resultCount == 0) {
                    return JsonData.fail("您没有权限操作");
                }
            }
        }
        InvoiceParam invoiceParam = orderMapper.findOrderById(orderId);
        if(invoiceParam == null){
            return JsonData.fail("订单有误");
        }
        Invoice invoice = null;
        if(printType == 1){
            invoice = invoiceMapper.findInvoice(currentEmpId);
        }else if(printType == 2){
            invoice = invoiceMapper.findCurrentInvoice(orderId);
        }else if(printType == 3){
            invoice = invoiceMapper.findCurrentInvoice(orderId);
            int count = invoiceMapper.cancelInvoice(invoice.getInvoiceCode(),invoice.getInvoiceNumber(),currentEmpId); //撤销之前的
            if(count == 0){
                return JsonData.fail("新票补打时没有找到之前的打印记录");
            }
            invoice = invoiceMapper.findInvoice(currentEmpId);
        }
        if(invoice == null){
            return JsonData.fail("暂无可用发票");
        }
        invoiceParam.setInvoiceCode(invoice.getInvoiceCode());
        invoiceParam.setInvoiceNumber(invoice.getInvoiceNumber());
        return JsonData.successData(invoiceParam);
    }

    @Override
    public JsonData printInvoice(Integer orderId, String invoiceCode, String invoiceNumber, Integer currentEmpId) {
        int count = invoiceMapper.printInvoice(orderId, invoiceCode, invoiceNumber, currentEmpId);
        if(count>0){
            return JsonData.successMsg("打印成功");
        }
        return JsonData.fail("打印失败");
    }

    @Override
    public JsonData cancelInvoice(Integer orderId,Integer userId, String invoiceCode, String invoiceNumber, Integer currentEmpId) {
        EmployeeParam emp = employeeMapper.getEmpById(currentEmpId);
        if(emp == null){
            return JsonData.fail("该操作员不存在");
        }
        SysRoleParam role = sysRoleMapper.getRoleById(emp.getRoleId());
        if (!role.getIsAdmin()) {
            int resultCount = orderMapper.hasAuthorityToInvoice(orderId, userId);
            if (resultCount == 0) {
                return JsonData.fail("您没有权限操作");
            }
        }
        int count = invoiceMapper.cancelInvoice(invoiceCode, invoiceNumber, currentEmpId); //撤销之前的
        if(count == 0){
            return JsonData.fail("作废发票失败");
        }
        return JsonData.successMsg("作废发票成功");
    }

    @Override
    public JsonData cancelNotPrintInvoice(String invoiceCode, String invoiceNumber, Integer currentEmpId) {
        int count = invoiceMapper.cancelInvoice(invoiceCode, invoiceNumber, currentEmpId); //撤销之前的
        if(count == 0){
            return JsonData.fail("作废发票失败");
        }
        return JsonData.successMsg("作废发票成功");
    }


}
