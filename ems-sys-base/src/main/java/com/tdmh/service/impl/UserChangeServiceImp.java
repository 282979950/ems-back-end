package com.tdmh.service.impl;


import com.tdmh.common.JsonData;
import com.tdmh.entity.GasPrice;
import com.tdmh.entity.User;
import com.tdmh.entity.UserChange;
import com.tdmh.entity.UserOrders;
import com.tdmh.entity.mapper.UserChangeMapper;
import com.tdmh.entity.mapper.UserMapper;
import com.tdmh.entity.mapper.UserOrdersMapper;
import com.tdmh.service.IGasPriceService;
import com.tdmh.service.IUserChangeService;
import com.tdmh.util.CalculateUtil;
import com.tdmh.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

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
    @Override
    @Transactional(readOnly = false)
    public JsonData userChangeSettlementService(UserChange userChange, User user,Integer currentEmpId,double userMoney,double OrderSupplement){

        if(userChange == null){
            return JsonData.fail("未获取录入的相关信息");
        }
        BigDecimal  code = userChange.getTableCode();
        Integer userId= user.getUserId();

        //根据id获取当前所有购气总量
        BigDecimal PurchasingAirVolume =userChangeMapper.sumHistoryPurchasingAirVolume(userId);
        BigDecimal HistoryTableCode= userChangeMapper.sumHistoryTableCode(userId);
        BigDecimal amount;
        if(null!=HistoryTableCode){
            amount= PurchasingAirVolume.subtract(HistoryTableCode).subtract(code);
        }else{
            amount= PurchasingAirVolume.subtract(code);
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

        if(amount.compareTo(BigDecimal.ZERO)>=0){
            //新增记录
            userChangeMapper.insert(userChange);
            //变更用户信息
            user.setUserName(userChange.getUserChangeName());
            user.setUserPhone(userChange.getUserChangePhone());
            user.setUserIdcard(userChange.getUserChangeIdcard());
            user.setUserDeed(userChange.getUserChangeDeed());
            UserMapper.updateUserById(user);
            return JsonData.successMsg("已成功变更账户,当前可用总量为："+amount+"方");


        }else if(userMoney!=0){
            //新增记录
            userChangeMapper.insert(userChange);
            //变更用户信息
            user.setUserName(userChange.getUserChangeName());
            user.setUserPhone(userChange.getUserChangePhone());
            user.setUserIdcard(userChange.getUserChangeIdcard());
            user.setUserDeed(userChange.getUserChangeDeed());
            UserMapper.updateUserById(user);
            //生成一笔充值记录
            UserOrders orders = new UserOrders() ;
            orders.setUserId(userChange.getUserId());
            orders.setEmployeeId(currentEmpId);
            orders.setOrderPayment(new BigDecimal(userMoney+""));
            orders.setOrderGas(amount.negate());
            orders.setOrderType(5);
            orders.setCreateTime(new Date());
            orders.setCreateBy(currentEmpId);
            orders.setUpdateBy(currentEmpId);
            orders.setUpdateTime( new Date());
            orders.setUsable(true);
            orders.setOrderSupplement(new BigDecimal(OrderSupplement));
            orders.setRemarks("超用补缴充值记录");
            userOrdersMapper.createChangeUserOrder(orders);
            return JsonData.successMsg("已成功变更账户,补缴成功");

        }else{
            GasPrice gasPrice = gasPriceService.findGasPriceByType(user.getUserType() ,user.getUserGasType());
            BigDecimal hasUsedGasNum = gasPriceService.findHasUsedGasInYear(userId);
            if(gasPrice != null){
                BigDecimal orderPayment = CalculateUtil.gasToPayment((amount.negate()).add(hasUsedGasNum), gasPrice);
                BigDecimal hasOrderPayment = CalculateUtil.gasToPayment(hasUsedGasNum, gasPrice);

                BigDecimal[] res={orderPayment.subtract(hasOrderPayment),orderPayment.subtract(hasOrderPayment)};
                //return JsonData.success(orderPayment.subtract(hasOrderPayment),"超用气量(单位:方):"+amount.negate()+",超用补缴金额(单位:元):"+orderPayment.subtract(hasOrderPayment));
                return JsonData.success(res,"超用气量(单位:方):"+amount.negate()+",超用补缴金额(单位:元):"+orderPayment.subtract(hasOrderPayment));

            }
        }

        return JsonData.fail("服务内部出错");

    }
}
