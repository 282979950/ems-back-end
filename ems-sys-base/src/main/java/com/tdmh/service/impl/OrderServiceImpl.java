package com.tdmh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdmh.common.JsonData;
import com.tdmh.entity.UserOrders;
import com.tdmh.entity.mapper.OrderMapper;
import com.tdmh.entity.mapper.UserOrdersMapper;
import com.tdmh.param.OrderParam;
import com.tdmh.service.IOrderService;
import com.tdmh.utils.DateUtils;
import com.tdmh.utils.StringUtils;
import com.tdmh.utils.excel.ExportExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator on 2018/10/20.
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserOrdersMapper userOrdersMapper;

    @Override
    public JsonData searchOrderAndInvoiceList(String userName,String iccardId, String iccardIdentifier, String invoiceCode, String invoiceNumber, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<OrderParam> list = orderMapper.searchOrderAndInvoiceList(userName, iccardId, iccardIdentifier, invoiceCode,invoiceNumber);
        PageInfo<OrderParam> info = new PageInfo<>(list);
        return JsonData.success(info,"查询成功");
    }

    @Override
    public JsonData updateOrderStatus(Integer orderId, Integer orderStatus) {
        int resultCount = orderMapper.updateOrderStatus(orderId,orderStatus);
        if(resultCount == 0){
            return JsonData.fail("写卡失败");
        }else{
            return JsonData.successMsg("写卡成功");
        }
    }
    @Override
    public JsonData BusinessDataQueryService(UserOrders orders,Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<UserOrders> list= userOrdersMapper.selectBusinessDataQuery(orders);
        PageInfo<UserOrders> page = new PageInfo<>(list);
        return JsonData.successData(page);
    }

    @Override
    public JsonData BusinessDataQuerySearchListService(UserOrders orders) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1;
        Date dt2;
        List<UserOrders> list;
        if(StringUtils.isNotBlank(orders.getStartTime())&& StringUtils.isNotBlank(orders.getEndTime())){
            try {

                dt1= df.parse(orders.getStartTime());
                dt2= df.parse(orders.getEndTime());
                if (dt1.getTime() > dt2.getTime()) {

                    return JsonData.fail("查询有误，起始时间不能大于结束时间");
                } else if (dt1.getTime() < dt2.getTime()) {

                    list= userOrdersMapper.selectBusinessDataQuery(orders);
                    return list.size()==0?JsonData.fail("未查询到相关数据"):JsonData.success(list,"查询成功!");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        if((StringUtils.isBlank(orders.getStartTime()) && StringUtils.isNotBlank(orders.getEndTime()))||
                StringUtils.isNotBlank(orders.getStartTime()) && StringUtils.isBlank(orders.getEndTime())){

            return JsonData.fail("查询有误，若查询某时间段请输入起始日期和截止日期");

        }

        list= userOrdersMapper.selectBusinessDataQuery(orders);
        return list.size()==0?JsonData.fail("未查询到相关数据"):JsonData.success(list,"查询成功!");
    }

    @Override
    public JsonData selectHistoryOrdersService(Integer userId) {
        List<UserOrders> list= userOrdersMapper.selectordersListQuery(userId);
        return list==null?JsonData.successMsg("未查询到相关数据"):JsonData.success(list,"查询成功!");
    }

    /**
     * 数据导出
     * @param orders
     */

    @Override
    public void exportBusinessDataQueryListService(UserOrders orders) {

        List<UserOrders> list= userOrdersMapper.selectBusinessDataQuery(orders);
        String fileName = "营业数据信息-"+DateUtils.getDate()+".xlsx";
        try {
            new ExportExcel("营业数据信息", UserOrders.class).setDataList(list).write(fileName).dispose();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public JsonData ReportBusinessDataQueryService(UserOrders orders,Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserOrders> list= userOrdersMapper.selectReportBusinessDataQuery(orders);
        PageInfo<UserOrders> page = new PageInfo<>(list);
        return JsonData.success(page,"查询成功");
    }

}
