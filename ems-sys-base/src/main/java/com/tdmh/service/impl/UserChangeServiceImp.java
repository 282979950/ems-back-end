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
    /**
     * 标识。1，用户超用补缴，2.燃气公司退钱
     * userMoney 实补金额
     * OrderSupplement应补金额
     */

    @Override
    @Transactional(readOnly = false)
    public JsonData userEliminationHeadService(User user,BigDecimal userMoney,BigDecimal OrderSupplement,BigDecimal flage,Integer Id){

        Integer userId =user.getUserId();

        //根据id获取当前所有购气总量
       BigDecimal PurchasingAirVolume =userChangeMapper.sumHistoryPurchasingAirVolume(userId);
        //根据id获取表址码
        BigDecimal HistoryTableCode= userChangeMapper.sumHistoryTableCode(userId);
        BigDecimal amount= PurchasingAirVolume.subtract(HistoryTableCode);
//        BigDecimal a = new BigDecimal("7.0");
        //气量持平直接发消息到前台消户即可
        if(amount.compareTo(BigDecimal.ZERO)==0){

            //执行删除操作（添加状态为不可用）
          int  count = UserMapper.updateUserUsable(userId);

            if(count>0){

                return JsonData.successMsg("无超用或补缴信息，消户成功");

            }else{

                return JsonData.successMsg("消户失败");
            }

        }
        //超用数据结算，新增一笔充值记录
        if(flage.compareTo(BigDecimal.ZERO)==1){

            UserOrders orders = new UserOrders() ;
            orders.setUserId(user.getUserId());
            orders.setEmployeeId(Id);
            orders.setOrderPayment(userMoney);
            orders.setOrderGas(amount.negate());
            orders.setOrderType(5);
            orders.setCreateTime(new Date());
            orders.setCreateBy(Id);
            orders.setUpdateBy(Id);
            orders.setUpdateTime( new Date());
            orders.setUsable(true);
            orders.setOrderSupplement(OrderSupplement);
            orders.setRemarks("用户消户时超用补缴充值记录");
           int count =  userOrdersMapper.createChangeUserOrder(orders);

            //执行删除操作（添加状态为不可用）
            int  userCleanCount = UserMapper.updateUserUsable(userId);
            if(userCleanCount>0 && count>0){

                return JsonData.successMsg("消户成功");

            }else{

                return JsonData.successMsg("消户失败");
            }



        }
        if(amount.compareTo(BigDecimal.ZERO)<=0){
            flage = new BigDecimal("1");
            //消户时用户超用按当时阶梯气价计算对应金额
            GasPrice gasPrice = gasPriceService.findGasPriceByType(user.getUserType() ,user.getUserGasType());
            BigDecimal hasUsedGasNum = gasPriceService.findHasUsedGasInYear(userId);
            if(gasPrice != null){
                BigDecimal orderPayment = CalculateUtil.gasToPayment((amount.negate()).add(hasUsedGasNum), gasPrice);
                BigDecimal hasOrderPayment = CalculateUtil.gasToPayment(hasUsedGasNum, gasPrice);

                BigDecimal[] res={orderPayment.subtract(hasOrderPayment),orderPayment.subtract(hasOrderPayment),flage};
                return JsonData.success(res,"超用气量(单位:方):"+amount.negate()+",超用补缴金额(单位:元):"+orderPayment.subtract(hasOrderPayment));
            }
        }
        if(amount.compareTo(BigDecimal.ZERO)>=0){
            //此处需要燃气公司退钱处理,设置标识为需要退钱处理（flage）
            flage = new BigDecimal("2");
        }
        return JsonData.fail("系统内部出错，请确认该条信息并刷新，或联系管理员");
    }
   public  JsonData selectUserChangeListService(Integer userId){
       List<UserChange> userChange = userChangeMapper.selectUserChangeList(userId);
        return JsonData.successData(userChange);
    }
}
