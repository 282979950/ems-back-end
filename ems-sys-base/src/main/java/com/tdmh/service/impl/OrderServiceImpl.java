package com.tdmh.service.impl;

import com.tdmh.common.JsonData;
import com.tdmh.entity.UserOrders;
import com.tdmh.entity.mapper.OrderMapper;
import com.tdmh.entity.mapper.UserOrdersMapper;
import com.tdmh.param.OrderParam;
import com.tdmh.service.IOrderService;
import com.tdmh.utils.StringUtils;
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
    public JsonData searchOrderAndInvoiceList(String userName,String iccardId, String iccardIdentifier, String invoiceCode, String invoiceNumber) {
        List<OrderParam> list = orderMapper.searchOrderAndInvoiceList(userName, iccardId, iccardIdentifier, invoiceCode,invoiceNumber);
        return list == null || list.size() == 0 ? JsonData.successMsg("暂无订单") : JsonData.success(list,"查询成功");
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
    public JsonData BusinessDataQueryService(UserOrders orders){
        List<UserOrders> list= userOrdersMapper.selectBusinessDataQuery(orders);
        return list==null?JsonData.fail("未查询到相关数据"):JsonData.successData(list);
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
        if(userId.intValue()==0){
            return JsonData.fail("未获取到此操作相关信息,请刷新重试或联系管理员");
        }
        List<UserOrders> list= userOrdersMapper.selectordersListQuery(userId);
        return list==null?JsonData.fail("未查询到相关数据"):JsonData.success(list,"查询成功!");
    }

}
