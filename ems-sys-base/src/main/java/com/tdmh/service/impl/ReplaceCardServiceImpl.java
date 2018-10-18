package com.tdmh.service.impl;

import com.tdmh.common.JsonData;
import com.tdmh.entity.UserCard;
import com.tdmh.entity.UserOrders;
import com.tdmh.entity.mapper.*;
import com.tdmh.exception.ParameterException;
import com.tdmh.param.CreateAccountParam;
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
    private ReplaceCardMapper replaceCardMapper;

    @Autowired
    private UserCardMapper userCardMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public JsonData getAllReplaceCardInformation() {
        List<PrePaymentParam> list = replaceCardMapper.getAllreplaceCard(null);
        return list == null || list.size() == 0 ? JsonData.successMsg("暂无可补卡用户") : JsonData.successData(list);
    }

    @Override
    public JsonData selectFindListByPre(PrePaymentParam param) {
        List<PrePaymentParam> list = replaceCardMapper.getAllreplaceCard(param);
        return list == null || list.size() == 0 ? JsonData.successMsg("暂无可补卡用户") : JsonData.success(list,"查询成功");
    }

    @Transactional
    @Override
    public JsonData supplementCard(PrePaymentParam param) {
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
        wparam.setFlowNumber("");
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
