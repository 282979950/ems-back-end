package com.tdmh.service.impl;


import com.tdmh.common.JsonData;
import com.tdmh.entity.User;
import com.tdmh.entity.UserCard;
import com.tdmh.entity.UserChange;
import com.tdmh.entity.mapper.*;
import com.tdmh.service.IGasPriceService;
import com.tdmh.service.IMeterService;
import com.tdmh.service.IUserChangeService;
import com.tdmh.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 账户变更实现类
 *
 * @author qh on 2018/10/17.
 */
@Service("iUserChangeService")
@Transactional(readOnly = true)
public class UserChangeServiceImp implements IUserChangeService {
    @Autowired
    private UserChangeMapper userChangeMapper;
    @Autowired
    private UserMapper UserMapper;
    @Autowired
    private IGasPriceService gasPriceService;
    @Autowired
    private UserOrdersMapper userOrdersMapper;
    @Autowired
    private UserCardMapper userCardMapper;

    @Autowired
    private IMeterService meterService;

    @Autowired
    private FillGasOrderMapper fillGasOrderMapper;

    @Override
    @Transactional(readOnly = false)
    public JsonData userChangeSettlementService(UserChange userChange, User user, Integer currentEmpId, double userMoney, double OrderSupplement) {
        if (userChange == null) {
            return JsonData.fail("未获取录入的相关信息");
        }
        if ((userChange.getUserChangeIdcard().length() < 15) || (userChange.getUserChangeIdcard().length() > 18)) {
            return JsonData.fail("录入信息失败，身份证号15位或18位");
        }
        Integer userId = user.getUserId();

        //根据条件查询是否存在没有处理完的补气或者补缴单若存在则提示
        int fillGasOrderCount = fillGasOrderMapper.getFillGasOrderCountByUserId(userId,0);
        if(fillGasOrderCount>0){
            return JsonData.fail("该户存在补气/补缴未结算完成请先前往结算");
        }
        userChange.setId(RandomUtils.getUUID());
        userChange.setUserId(user.getUserId());
        userChange.setUserOldName(user.getUserName());
        userChange.setUserOldPhone(user.getUserPhone());
        userChange.setUserOldIdcard(user.getUserIdcard());
        userChange.setUserOldDeed(user.getUserDeed());
        userChange.setCreateTime(new Date());
        userChange.setCreateBy(currentEmpId);
        userChange.setUpdateBy(currentEmpId);
        userChange.setUpdateTime(new Date());
        userChange.setUsable(true);

        //新增记录
        userChangeMapper.insert(userChange);
        //变更用户信息
        user.setUserName(userChange.getUserChangeName());
        user.setUserPhone(userChange.getUserChangePhone());
        user.setUserIdcard(userChange.getUserChangeIdcard());
        user.setUserDeed(userChange.getUserChangeDeed());
        UserMapper.updateUserById(user);
        return JsonData.successMsg("已变更账户");
    }

    /**
     * 处理销户
     */
    @Override
    @Transactional(readOnly = false)
    public JsonData userEliminationHeadService(User user){

        Integer userId = user.getUserId();
        UserCard userCard = new UserCard();
        //查询对应卡信息是否存在
        int userCardCount = userCardMapper.selectCountUserCard(userId);

        if (userCardCount <= 0) {
            return JsonData.fail("销户失败,未查询到卡相关信息");
        }
        //根据条件查询是否存在没有处理完的补气或者补缴单若存在则提示
        int fillGasOrderCount = fillGasOrderMapper.getFillGasOrderCountByUserId(userId, 0);
        if (fillGasOrderCount > 0) {
            return JsonData.fail("该户存在补气/补缴未结算完成请先前往结算");
        }
        //执行销户操作
        userCard.setUserId(userId);
        userCard.setCardStatus(2);
        int resultCount = userCardMapper.updateUserCardByUserIdCardStatus(userCard);

        if (resultCount > 0) {

            return JsonData.successMsg("销户成功");

        } else {

            return JsonData.fail("销户失败");
        }

    }

    public JsonData selectUserChangeListService(Integer userId) {
        List<UserChange> userChange = userChangeMapper.selectUserChangeList(userId);
        return JsonData.successData(userChange);
    }
}
