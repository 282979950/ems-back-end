package com.tdmh.service.impl;

import com.tdmh.common.JsonData;
import com.tdmh.entity.mapper.OrderMapper;
import com.tdmh.param.OrderParam;
import com.tdmh.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator on 2018/10/20.
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

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
}
