package com.tdmh.service.impl;


import com.tdmh.common.JsonData;
import com.tdmh.entity.*;
import com.tdmh.entity.mapper.*;
import com.tdmh.service.IGasPriceService;
import com.tdmh.service.IMeterService;
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
    @Autowired
    private UserCardMapper userCardMapper;

    @Autowired
    private IMeterService meterService;

    @Autowired
    private FillGasOrderMapper fillGasOrderMapper;

    @Autowired
    private RepairOrderMapper repairOrderMapper;

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
        userChange.setTableCode(userChange.getTableCode());
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
     * 标识。1，用户超用补缴，2.燃气公司退钱
     * userMoney 实补金额
     * OrderSupplement应补金额
     */
    @Override
    @Transactional
    public JsonData userEliminationHeadService(User user,BigDecimal userMoney,BigDecimal OrderSupplement,int flage,Integer Id){


        Integer userId =user.getUserId();
        UserCard userCard = new UserCard();
        //查询对应卡信息是否存在
        int userCardCount = userCardMapper.selectCountUserCard(userId);

        if(userCardCount<=0){
            return JsonData.fail("销户失败,未查询到卡相关信息");
        }
        //根据条件查询是否存在没有处理完的补气或者补缴单若存在则提示
        int fillGasOrderCount = fillGasOrderMapper.getFillGasOrderCountByUserId(userId, 0);
        if (fillGasOrderCount > 0) {
            return JsonData.fail("该户存在补气/补缴未结算完成请先前往结算");
        }
        BigDecimal number = new BigDecimal(0);

        //根据id获取当前所有购气总量
        BigDecimal PurchasingAirVolume =userChangeMapper.sumHistoryPurchasingAirVolume(userId);
        //根据id获取表址码
        BigDecimal HistoryTableCode= userChangeMapper.sumHistoryTableCode(userId);
        BigDecimal amount= PurchasingAirVolume.subtract(HistoryTableCode);
        //气量持平直接发消息到前台消户即可
        if(amount.compareTo(BigDecimal.ZERO)==0){

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
        //超用数据结算，新增一笔充值记录状态为1
        //燃气公司退钱生成一笔充值（数据为负的充值记录）状态为2
        if(flage==1 || flage==2){

            UserOrders orders = new UserOrders() ;
            orders.setUserId(user.getUserId());
            orders.setEmployeeId(Id);
            orders.setOrderGas(amount.negate());
            orders.setOrderType(6);
            orders.setCreateTime(new Date());
            orders.setCreateBy(Id);
            orders.setUpdateBy(Id);
            orders.setUpdateTime( new Date());
            orders.setUsable(true);

            if(flage==1){

                orders.setOrderPayment(userMoney);
                orders.setOrderSupplement(OrderSupplement);
                orders.setRemarks("用户销户时超用补缴充值记录");
            }else{

                orders.setOrderPayment(userMoney.negate());
                orders.setOrderSupplement(OrderSupplement.negate());
                orders.setRemarks("用户销户时补缴金额记录");
            }
            //查询是否存在报停拆表需要结算的数据（因维修单录入只存在一条待处理的数据该方法返回一条信息）
            boolean bFlag = repairOrderMapper.isDemolitionTable(user.getUserId());
            if(bFlag){
                //修改维修单录入为无需处理状态
                repairOrderMapper.updateDemolitionTableStatus(user.getUserId());
            }
            int count =  userOrdersMapper.createChangeUserOrder(orders);

            //执行销户操作
            userCard.setUserId(userId);
            userCard.setCardStatus(2);
            int resultcount =userCardMapper.updateUserCardByUserIdCardStatus(userCard);
            if( count>0 && resultcount>0){

                return JsonData.successMsg("销户成功");

            }else{

                return JsonData.successMsg("销户失败");
            }
        }

        BigDecimal resultToPayment = new BigDecimal(0);
        BigDecimal resultmoney = new BigDecimal(0);
        if(amount.compareTo(BigDecimal.ZERO)<0){
            number=new BigDecimal("1");

            //消户时用户超用按当时阶梯气价计算对应金额
            GasPrice gasPrice = gasPriceService.findGasPriceByType(user.getUserType() ,user.getUserGasType());
            BigDecimal hasUsedGasNum = gasPriceService.findHasUsedGasInYear(userId);
            if(gasPrice != null){
                if(hasUsedGasNum == null) hasUsedGasNum = new BigDecimal(0);
                BigDecimal orderPayment = CalculateUtil.gasToPayment((amount.negate()).add(hasUsedGasNum), gasPrice);
                BigDecimal hasOrderPayment = CalculateUtil.gasToPayment(hasUsedGasNum, gasPrice);

                BigDecimal[] res={orderPayment.subtract(hasOrderPayment),orderPayment.subtract(hasOrderPayment),number};
                return JsonData.success(res,"超用气量(单位:方):"+amount.negate()+",\n超用补缴金额(单位:元):"+orderPayment.subtract(hasOrderPayment)+",\n总购气总量:"+PurchasingAirVolume+"方"+",\n总止码"+HistoryTableCode);
            }
        }
        if(amount.compareTo(BigDecimal.ZERO)>0){
            //此处需要燃气公司退钱处理,设置标识为需要退钱处理（number）
            number=new BigDecimal("2");
            //销户时查看当时阶梯气价
            GasPrice gasPrice = gasPriceService.findGasPriceByType(user.getUserType() ,user.getUserGasType());
            //查看当年该用户购买总气量
            BigDecimal hasUsedGasNum = gasPriceService.findHasUsedGasInYear(userId);
            if(hasUsedGasNum==null){
                hasUsedGasNum=new BigDecimal("0");
            }
            //历史可用总量，当年充值总额做对比（同下）大于
            if(amount.compareTo(hasUsedGasNum)==1){
                //400 ,900,500
                resultToPayment= amount.subtract(hasUsedGasNum);

                if(gasPrice != null){
                    //算出当年所有充值气量对应的金额
                    if(hasUsedGasNum == null) hasUsedGasNum = new BigDecimal(0);
                    BigDecimal orderPayment = CalculateUtil.gasToPayment(hasUsedGasNum, gasPrice);
                    //剩余历史气量折算出第一阶梯气价对应金额
                    BigDecimal orderSurplusPayment = CalculateUtil.gasSurplusToPayment(resultToPayment, gasPrice);
                    resultmoney =  orderPayment.add(orderSurplusPayment);
                    BigDecimal[] res={resultmoney,resultmoney,number};
                    return JsonData.success(res,"剩余气量(单位:方):"+amount+",\n应退金额(单位:元):"+resultmoney+",\n总购气总量:"+PurchasingAirVolume+"方"+",\n总止码"+HistoryTableCode);

                }

            }
            //小于
            if(amount.compareTo(hasUsedGasNum)== -1){
                //400 ,900,500
                resultToPayment= hasUsedGasNum.subtract(amount);

                if(gasPrice != null){
                    //算出当年所有充值气量对应的金额
                    if(hasUsedGasNum == null) hasUsedGasNum = new BigDecimal(0);
                    BigDecimal orderPayment = CalculateUtil.gasToPayment(hasUsedGasNum, gasPrice);
                    //剩余气量计算当年对应金额
                    BigDecimal orderSurplusPayment = CalculateUtil.gasToPayment(resultToPayment, gasPrice);
                    resultmoney =  orderPayment.subtract(orderSurplusPayment);
                    BigDecimal[] res={resultmoney,resultmoney,number};
                    return JsonData.success(res,"剩余气量(单位:方):"+amount+",\n应退金额(单位:元):"+resultmoney+",\n总购气总量:"+PurchasingAirVolume+"方"+",\n总止码"+HistoryTableCode);

                }

            }
            //相等
            if(amount.compareTo(hasUsedGasNum)==0){
                //400 ,900,500
                resultToPayment= hasUsedGasNum;

                if(gasPrice != null){
                    //算出当年所有充值气量对应的金额
                    if(hasUsedGasNum == null) hasUsedGasNum = new BigDecimal(0);
                    BigDecimal orderPayment = CalculateUtil.gasToPayment(hasUsedGasNum, gasPrice);
                    resultmoney =  orderPayment;
                    BigDecimal[] res={resultmoney,resultmoney,number};
                    return JsonData.success(res,"剩余气量(单位:方):"+amount+",应退金额(单位:元):"+resultmoney);

                }

            }
        }
        return JsonData.fail("系统内部出错，请确认该条信息并刷新，或联系管理员");
    }

    public JsonData selectUserChangeListService(Integer userId) {
        List<UserChange> userChange = userChangeMapper.selectUserChangeList(userId);
        return JsonData.successData(userChange);
    }
}
