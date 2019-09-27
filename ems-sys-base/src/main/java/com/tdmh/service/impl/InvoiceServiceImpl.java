package com.tdmh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import com.tdmh.utils.RmbConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public JsonData getAllAssignInvoiceList(Integer currentEmpId, Integer pageNum, Integer pageSize) {
        EmployeeParam emp = employeeMapper.getEmpById(currentEmpId);
        if (emp == null) {
            return JsonData.fail("该操作员不存在");
        }
        SysRoleParam role = sysRoleMapper.getRoleById(emp.getRoleId());
        if (role.getIsAdmin()) {
            return getAllInvoice(pageNum, pageSize);
        } else {
            return getInvoiceByEmpId(currentEmpId, pageNum, pageSize);
        }
    }

    private JsonData getAllInvoice(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Invoice> list = invoiceMapper.getAllAssignInvoiceList(null, null, null);
        PageInfo<Invoice> page = new PageInfo<>(list);
        return JsonData.successData(page);
    }

    private JsonData getInvoiceByEmpId(Integer currentEmpId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Invoice> list = invoiceMapper.getAllAssignInvoiceList(null, null, currentEmpId);
        PageInfo<Invoice> page = new PageInfo<>(list);
        return JsonData.successData(page);
    }

    @Override
    public JsonData searchAssignInvoiceList(String invoiceCode, String invoiceNumber, Integer currentEmpId,Integer pageNum,Integer pageSize) {
        EmployeeParam emp = employeeMapper.getEmpById(currentEmpId);
        PageHelper.startPage(pageNum, pageSize);
        if(emp == null){
            return JsonData.fail("该操作员不存在");
        }
        SysRoleParam role = sysRoleMapper.getRoleById(emp.getRoleId());
        //若为Admin则查询所有，否则查询部分
        if(role.getIsAdmin()) {
          return  searchAssignInvoiceListIsAdmin(invoiceCode, invoiceNumber, currentEmpId,pageNum, pageSize);
        }else {
          return  searchAssignInvoiceListIsNotAdmin( invoiceCode, invoiceNumber, currentEmpId,pageNum, pageSize);
        }
    }

    public JsonData searchAssignInvoiceListIsAdmin(String invoiceCode, String invoiceNumber, Integer currentEmpId,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Invoice> list = invoiceMapper.getAllAssignInvoiceList(invoiceCode, invoiceNumber,null);
        PageInfo<Invoice> page = new PageInfo<>(list);
        return   JsonData.success(page,"查询成功");
    }
    public JsonData searchAssignInvoiceListIsNotAdmin(String invoiceCode, String invoiceNumber, Integer currentEmpId,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Invoice>  list = invoiceMapper.getAllAssignInvoiceList(invoiceCode, invoiceNumber, currentEmpId);
        PageInfo<Invoice> page = new PageInfo<>(list);
        return JsonData.success(page,"查询成功");
    }

    @Override
    @Transactional
    public JsonData addInvoice(String invoiceCode, Integer sInvoiceNumber, Integer eInvoiceNumber, Integer orgId, Integer currentEmpId) {
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
            invoice.setOrgId(orgId);
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
    public JsonData getAllPrintCancelInvoiceList(Integer currentEmpId, Integer pageNum, Integer pageSize) {
        EmployeeParam emp = employeeMapper.getEmpById(currentEmpId);
        if(emp == null){
            return JsonData.fail("该操作员不存在");
        }
        SysRoleParam role = sysRoleMapper.getRoleById(emp.getRoleId());
        if(role.getIsAdmin()) {
            return getAllPrintCancelInvoice(pageNum, pageSize);
        }else {
            return getAllPrintCancelInvoiceByEmpId(currentEmpId, pageNum, pageSize);
        }
    }

    private JsonData getAllPrintCancelInvoice(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Invoice> list = invoiceMapper.getAllPrintCancelInvoiceList(null, null, null, null);
        PageInfo<Invoice> info = new PageInfo<>(list);
        return JsonData.successData(info);
    }

    private JsonData getAllPrintCancelInvoiceByEmpId(Integer currentEmpId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Invoice> list = invoiceMapper.getAllPrintCancelInvoiceList(null, null, null, currentEmpId);
        PageInfo<Invoice> info = new PageInfo<>(list);
        return JsonData.successData(info);
    }

    @Override
    public JsonData searchPrintCancelInvoiceList(String invoiceCode, String invoiceNumber, Integer empId, Integer currentEmpId, Integer pageNum,
                                                 Integer pageSize) {
        EmployeeParam emp = employeeMapper.getEmpById(currentEmpId);
        if (emp == null) {
            return JsonData.fail("该操作员不存在");
        }
        SysRoleParam role = sysRoleMapper.getRoleById(emp.getRoleId());
        if (role.getIsAdmin()) {
            return searchPrintCancelInvoiceList(invoiceCode, invoiceNumber, empId, pageNum, pageSize);
        } else {
            return searchPrintCancelInvoiceListByEmpId(invoiceCode, invoiceNumber, empId, currentEmpId, pageNum, pageSize);
        }
    }

    private JsonData searchPrintCancelInvoiceList(String invoiceCode, String invoiceNumber, Integer empId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Invoice> list = invoiceMapper.getAllPrintCancelInvoiceList(invoiceCode, invoiceNumber, empId, null);
        PageInfo<Invoice> info = new PageInfo<>(list);
        return JsonData.successData(info);
    }

    private JsonData searchPrintCancelInvoiceListByEmpId(String invoiceCode, String invoiceNumber, Integer empId, Integer currentEmpId, Integer pageNum,
                                                         Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Invoice> list = invoiceMapper.getAllPrintCancelInvoiceList(invoiceCode, invoiceNumber, empId, currentEmpId);
        PageInfo<Invoice> info = new PageInfo<>(list);
        return JsonData.successData(info);
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
    public JsonData printInvoice(Integer orderId, String invoiceCode, String invoiceNumber, Integer currentEmpId, String name, BigDecimal orderPayment, BigDecimal cardCost) {
        int count = invoiceMapper.printInvoice(orderId, invoiceCode, invoiceNumber, currentEmpId);
        //获取充值金额大写
        RmbConvert rmb = new RmbConvert();
        Map<String,Object> map = new HashMap<>();
        map.put("name", name);
        if(cardCost == null){
            map.put("rmbBig", rmb.simpleToBig(orderPayment.doubleValue()));
        }else{
            map.put("rmbBig", rmb.simpleToBig((orderPayment.add(cardCost)).doubleValue()));
        }
        if(count>0){
            return JsonData.success(map,"打印成功");
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

    @Override
    public JsonData transfer(Integer empId, Integer currentEmpId) {
        invoiceMapper.transfer(empId, currentEmpId);
        return JsonData.successMsg("移交发票成功");
    }

    @Override
    public JsonData getInvoiceInfo(Integer currentEmpId) {
        List<Invoice> list = invoiceMapper.getUnusedInvoiceByEmpId(currentEmpId);
        return JsonData.successData(list);
    }

    @Override
    public JsonData deleteInvoice(Integer invoiceId, Integer currentEmpId) {
        if(invoiceId.intValue() == 0){
            return JsonData.fail("获取数据失败请刷新重试");
        }
        return invoiceMapper.updateInvoiceById(invoiceId)>0?JsonData.successMsg("操作成功"):JsonData.fail("操作失败");
    }


}
