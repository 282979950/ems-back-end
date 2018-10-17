package com.tdmh.service.impl;

import com.tdmh.common.JsonData;
import com.tdmh.entity.UserCard;
import com.tdmh.entity.UserOrders;
import com.tdmh.entity.mapper.PrePaymentMapper;
import com.tdmh.entity.mapper.UserCardMapper;
import com.tdmh.entity.mapper.UserOrdersMapper;
import com.tdmh.param.PrePaymentParam;
import com.tdmh.param.WriteCardParam;
import com.tdmh.service.IPrePaymentService;
import com.tdmh.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Lucia on 2018/10/12.
 */
@Service
public class PrePaymentServiceImpl implements IPrePaymentService {

    @Autowired
    private PrePaymentMapper prePaymentMapper;

    @Autowired
    private UserOrdersMapper userOrdersMapper;

    @Autowired
    private UserCardMapper userCardMapper;

    @Override
    public JsonData getAllOrderInformation() {
        List<PrePaymentParam> list = prePaymentMapper.getAllOrderInformation(null);
        return list == null || list.size() == 0 ? JsonData.successMsg("暂无可充值用户") : JsonData.successData(list);
    }

    @Transactional
    @Override
    public JsonData createUserOrder(UserOrders userOrders) {
        userOrders.setUsable(true);
        userOrders.setFlowNumber(IdWorker.getId().nextId()+"");
        userOrders.setOrderType(2); //2为普通充值类型
        userOrders.setUpdateTime(new Date());
        userOrders.setOrderStatus(2);
        int resultCount = userOrdersMapper.insert(userOrders);
        if (resultCount == 0) {
            return JsonData.fail("充值订单失败");
        }
        UserCard userCard = userCardMapper.getUserCardByUserIdAndCardId(userOrders.getUserId(), null);
        WriteCardParam param = new WriteCardParam();
        param.setIccardId(userCard.getCardId());
        param.setIccardPassword(userCard.getCardPassword());
        param.setOrderGas(userOrders.getOrderGas());
        param.setServiceTimes(0);
        return JsonData.success(param, "充值订单成功");
    }

    @Override
    public JsonData selectFindListByPre(PrePaymentParam param) {
        List<PrePaymentParam> list = prePaymentMapper.getAllOrderInformation(param);
        return list == null || list.size() == 0 ? JsonData.successMsg("暂无可充值用户") : JsonData.success(list,"查询成功");
    }
}
