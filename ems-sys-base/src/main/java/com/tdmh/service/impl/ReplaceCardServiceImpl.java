package com.tdmh.service.impl;

import com.tdmh.common.JsonData;
import com.tdmh.entity.UserCard;
import com.tdmh.entity.UserOrders;
import com.tdmh.entity.mapper.PrePaymentMapper;
import com.tdmh.entity.mapper.UserCardMapper;
import com.tdmh.entity.mapper.UserOrdersMapper;
import com.tdmh.exception.ParameterException;
import com.tdmh.param.PrePaymentParam;
import com.tdmh.param.WriteCardParam;
import com.tdmh.service.IReplaceCardService;
import com.tdmh.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Administrator on 2018/10/16.
 */
@Service
public class ReplaceCardServiceImpl implements IReplaceCardService {

    @Autowired
    private PrePaymentMapper prePaymentMapper;

    @Autowired
    private UserCardMapper userCardMapper;

    @Autowired
    private UserOrdersMapper userOrdersMapper;

    @Override
    public JsonData getAllReplaceCardInformation() {
        List<PrePaymentParam> list = prePaymentMapper.getAllOrderInformation(null);
        return list == null || list.size() == 0 ? JsonData.successMsg("暂无可补卡用户") : JsonData.successData(list);
    }

    @Override
    public JsonData selectFindListByPre(PrePaymentParam param) {
        List<PrePaymentParam> list = prePaymentMapper.getAllOrderInformation(param);
        return list == null || list.size() == 0 ? JsonData.successMsg("暂无可补卡用户") : JsonData.success(list,"查询成功");
    }

    @Transactional
    @Override
    public JsonData supplementCard(PrePaymentParam param, UserOrders userOrders) {
        UserCard oldUserCard = userCardMapper.getUserCardByUserIdAndCardId(param.getUserId(),param.getIccardId());
        if(oldUserCard == null){
            return JsonData.fail("该用户没有可用卡");
        }
        if(!param.getIccardIdentifier().equals(oldUserCard.getCardIdentifier())){
            return JsonData.fail("该用户原卡与系统内不一致");
        }
        oldUserCard.setUsable(false);
        int resultCount =  userCardMapper.update(oldUserCard);
        if(resultCount == 0){
            throw new ParameterException("补卡失败");
        }
        UserCard userCard = new UserCard();
        userCard.setUserId(param.getUserId());
        userCard.setCardId(oldUserCard.getCardId());
        userCard.setCardIdentifier(param.getNIcCardIdentifier());
        userCard.setCardPassword(oldUserCard.getCardPassword());
        userCard.setCardInitialization(true);
        userCard.setCreateBy(userOrders.getCreateBy());
        userCard.setUpdateBy(userOrders.getUpdateBy());
        userCard.setUsable(true);
        int resultCount2 =  userCardMapper.insert(userCard);
        if(resultCount2 == 0){
            throw new ParameterException("补卡失败");
        }
        userOrders.setUserId(param.getUserId());
        userOrders.setFlowNumber(IdWorker.getId().nextId()+"");
        userOrders.setOrderStatus(2);
        userOrders.setUsable(true);
        int resultCount3 = userOrdersMapper.insert(userOrders);
        if(resultCount3 == 0){
            throw new ParameterException("补卡首充失败");
        }
        WriteCardParam wparam = new WriteCardParam();
        wparam.setIccardId(userCard.getCardId());
        wparam.setIccardPassword(userCard.getCardPassword());
        wparam.setOrderGas(userOrders.getOrderGas());
        wparam.setFlowNumber(userOrders.getFlowNumber());
        wparam.setServiceTimes(0);
        return JsonData.success(wparam,"补卡成功");
    }

    @Override
    public JsonData searchSupList(Integer userId) {
        List<UserCard> list = userCardMapper.getAllSupList(userId);
        return  list == null || list.size() == 0 ? JsonData.successMsg("搜索结果为空") : JsonData.success(list, "查询成功");
    }
}
