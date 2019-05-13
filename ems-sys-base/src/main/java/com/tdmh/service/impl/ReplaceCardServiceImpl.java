package com.tdmh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdmh.common.BeanValidator;
import com.tdmh.common.JsonData;
import com.tdmh.entity.UserCard;
import com.tdmh.entity.UserOrders;
import com.tdmh.entity.mapper.ReplaceCardMapper;
import com.tdmh.entity.mapper.UserCardMapper;
import com.tdmh.entity.mapper.UserMapper;
import com.tdmh.entity.mapper.UserOrdersMapper;
import com.tdmh.exception.ParameterException;
import com.tdmh.param.CreateAccountParam;
import com.tdmh.param.PrePaymentParam;
import com.tdmh.param.WriteCardParam;
import com.tdmh.service.IReplaceCardService;
import com.tdmh.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Administrator on 2018/10/16.
 */
@Service
public class ReplaceCardServiceImpl implements IReplaceCardService {

    @Autowired
    private ReplaceCardMapper replaceCardMapper;

    @Autowired
    private UserCardMapper userCardMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserOrdersMapper userOrdersMapper;

    @Override
    public JsonData getAllReplaceCardInformation(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PrePaymentParam> list = replaceCardMapper.getAllReplaceCard(null);
        PageInfo<PrePaymentParam> info = new PageInfo<>(list);
        return JsonData.successData(info);
    }

    @Override
    public JsonData selectFindListByPre(PrePaymentParam param, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PrePaymentParam> list = replaceCardMapper.getAllReplaceCard(param);
        PageInfo<PrePaymentParam> info = new PageInfo<>(list);
        return JsonData.successData(info);
    }

    @Transactional
    @Override
    public JsonData supplementCard(PrePaymentParam param, UserOrders userOrders) {
        BeanValidator.check(param);
        int tempOrderGas = userOrders.getOrderGas().compareTo(BigDecimal.ZERO);
        //若充值气量小于0或者等于零则提示充值气量
        if(tempOrderGas == -1 || tempOrderGas == 0){
            return JsonData.fail("操作有误！充值金额需大于零");
        }
        UserCard oldUserCard = userCardMapper.getUserCardByUserIdAndCardId(param.getUserId(),param.getIccardId());
        if(oldUserCard == null){
            return JsonData.fail("该用户没有可用卡");
        }
        if(!param.getIccardIdentifier().equals(oldUserCard.getCardIdentifier())){
            return JsonData.fail("该用户原卡与系统内不一致");
        }
        int count = userCardMapper.getUserCardByUserIdAndnIcCardIdentifier(param.getUserId(),param.getNIcCardIdentifier());
        if(count>0){
            return JsonData.fail("该卡已绑定其他用户");
        }
        int count1 = userCardMapper.getUserCardByYouSelfIdAndnIcCardIdentifier(param.getUserId(),param.getIccardIdentifier(), param.getNIcCardIdentifier());
        if(count1>0){
            return JsonData.fail("该卡已变为无效卡");
        }
        userOrders.setUserId(param.getUserId());
        userOrders.setFlowNumber(IdWorker.getId().nextId()+"");
        userOrders.setOrderType(3); //3为补卡订单
        userOrders.setOrderStatus(1);
        userOrders.setUsable(true);
        int resultCount3 = userOrdersMapper.insert(userOrders);
        if(resultCount3 == 0){
            throw new ParameterException("补卡首充失败");
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
        userCard.setOrderId(userOrders.getOrderId());
        userCard.setCardCost(param.getCardCost());
        userCard.setCreateBy(param.getCreateBy());
        userCard.setUpdateBy(param.getUpdateBy());
        userCard.setUsable(true);
        int resultCount2 =  userCardMapper.insert(userCard);
        if(resultCount2 == 0){
            throw new ParameterException("补卡失败");
        }
        WriteCardParam wparam = new WriteCardParam();
        wparam.setIccardId(userCard.getCardId());
        wparam.setIccardPassword(userCard.getCardPassword());
        wparam.setOrderId(userOrders.getOrderId());
        wparam.setOrderGas(userOrders.getOrderGas());
        wparam.setFlowNumber(userOrders.getFlowNumber());
        int serviceTimes = userMapper.getServiceTimesByUserId(param.getUserId());
        wparam.setServiceTimes(serviceTimes);
        return JsonData.success(wparam,"补卡成功");
    }

    @Override
    public JsonData searchSupList(Integer userId) {
        List<CreateAccountParam> list = userCardMapper.getAllSupList(userId);
        return  list == null || list.size() == 0 ? JsonData.successMsg("搜索结果为空") : JsonData.success(list, "查询成功");
    }
}
