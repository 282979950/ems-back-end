package com.tdmh.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdmh.common.JsonData;
import com.tdmh.entity.StrikeNucleus;
import com.tdmh.entity.User;
import com.tdmh.entity.UserOrders;
import com.tdmh.entity.mapper.FillGasOrderMapper;
import com.tdmh.entity.mapper.StrikeNucleusMapper;
import com.tdmh.entity.mapper.UserMapper;
import com.tdmh.entity.mapper.UserOrdersMapper;
import com.tdmh.param.FillGasOrderParam;
import com.tdmh.service.IPreStrikeService;
import com.tdmh.utils.DateUtils;
import com.tdmh.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 账户变更实现类
 *
 * @author qh on 2018/10/17.
 */
@Service("iPreStrikeService")
@Transactional(readOnly = true)
public class PreStrikeServiceImp implements IPreStrikeService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserOrdersMapper orders;
    @Autowired
    private StrikeNucleusMapper strikeNucleus;
    @Autowired
    private FillGasOrderMapper fillGasOrder;

    public  JsonData selectUserByOrderTypeService(User user,Integer currentEmpId,String isAdmin,Integer userType,Integer pageNum, Integer pageSize){
        user.setEmployeeId(currentEmpId);
        List<User> u = null;
        /*
         *判断是否为admin或者用户类型为管理员，不是查询个人产生的记录
         */
        if(StringUtils.isNotBlank(isAdmin) && "admin".equals(isAdmin) ||userType.intValue()== 2){
            user.setEmployeeId(null);
            PageHelper.startPage(pageNum, pageSize);
            u = userMapper.selectUserByOrderType(user);
        }else{
            PageHelper.startPage(pageNum, pageSize);
            u = userMapper.selectUserByOrderType(user);
        }
        PageInfo<User> page = new PageInfo<>(u);
        return JsonData.success(page,"查询成功");
    }

    /**
     * 预冲账处理流程
     * @param user
     * @return
     */
    @Transactional(readOnly = false)
    public JsonData editUserOrdersService(User user,String  currentEmpName,Integer currentEmpId){
        UserOrders userOrders = new UserOrders();
        //根据iser_id,EmployeeId查看该笔充值记录是否存在(最后一笔)
        userOrders.setResltTime( new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        if(user.getUserId().intValue()==0){

            return JsonData.fail("未获取到数据,请刷新数据或联系管理员");
        }
        if(user.getEmployeeId().intValue()==0){

            return JsonData.fail("未获取到相关数据,请刷新数据或联系管理员");

        }

        Integer fillGasOrderStatus;
        Date tempDate3;
        Date tempDate4;
        int number;
        /*
         *查看查看选中的一条数据是否为最新的一条
         */
        Date tempDate1 = orders.getCreateTimeByOrderId(user.getOrderId());
        Date tempDate2 = orders.getNowCreateTimeByOrderId(user.getUserId());

        number = DateUtils.temporalComparison(tempDate1,tempDate2,"yyyy-MM-dd HH:mm:ss");
        if(number!=0){
            return JsonData.fail("请操作该户最近一笔记录：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tempDate2));
        }
        //查询该户最近一笔补气补缴单
        FillGasOrderParam fgp = new FillGasOrderParam();
        fgp = fillGasOrder.getCreateTimeByUserId(user.getUserId(),null);
        //若该户没有查到相关补缴记录则可以直接发起
        if( fgp != null && fgp.getFillGasOrderStatus()!= null && fgp.getUpdateTime()!=null){
            fillGasOrderStatus = fgp.getFillGasOrderStatus();
            tempDate3 = fgp.getUpdateTime();
            //查看该笔记录状态(0:未处理 1：已处理 2：已撤销)
            if(fillGasOrderStatus.intValue()== 0){
                number = DateUtils.temporalComparison(tempDate1,tempDate3,"yyyy-MM-dd HH:mm:ss");
                if(number == -1){
                    return JsonData.fail("该用户存在未处理的结算单,无法发起");
                }
            }
            if(fillGasOrderStatus.intValue()== 1){
                number = DateUtils.temporalComparison(tempDate1,tempDate3,"yyyy-MM-dd HH:mm:ss");
                if(number == -1){
                    return JsonData.fail("该笔充值已结算完成,无法发起");
                }
            }
            if(fillGasOrderStatus.intValue()== 2){
                //查询该笔记录的上一笔最近已处理记录
                FillGasOrderParam completeFgp = new FillGasOrderParam();
                completeFgp = fillGasOrder.getCreateTimeByUserId(user.getUserId(),1);
                if(completeFgp!= null && completeFgp.getFillGasOrderStatus()!= null && completeFgp.getUpdateTime()!= null){
                    tempDate4 = completeFgp.getUpdateTime();
                    number = DateUtils.temporalComparison(tempDate1,tempDate4,"yyyy-MM-dd HH:mm:ss");
                    if(number == -1){
                        return JsonData.fail("该笔充值已在历史结算中处理完成,无法发起");
                    }
                }
            }
        }

        userOrders.setUserId(user.getUserId());
        userOrders.setEmployeeId(user.getEmployeeId());
        userOrders.setOrderId(user.getOrderId());
        //设置发起冲账时间
        userOrders.setOrderStrikeTime(new Date());
        int count = orders.countUserOrdersByTimeEmployeeId(userOrders);
        //若查询出一条记录则直接预冲账申请成功
        int updateCount;
        //设置订单状态为已取消
        userOrders.setOrderStatus(4);
        if(count>0){
            //AccountState设置为，已冲帐
            userOrders.setAccountState(3);

            updateCount =orders.updateUserOrdersByOrderId(userOrders);
            if(updateCount>0){
                return  JsonData.successMsg("已成功撤销该笔充值");
            }else{
                return JsonData.fail("未成功撤销该笔冲账");
            }

        }else{
            //生成一笔需要上级审核的冲账记录,并设置该笔充值记录为“预冲账待审核”
            userOrders.setAccountState(1);

            updateCount =orders.updateUserOrdersByOrderId(userOrders);
            StrikeNucleus strike = new StrikeNucleus();
            strike.setOrderId(user.getOrderId());
            strike.setUserName(user.getUserName());
            strike.setNucleusGas(user.getOrderGas());
            strike.setNucleusPayment(user.getOrderPayment());
            strike.setNucleusLaunchingPerson(currentEmpName);
            strike.setRechargeTime(user.getOrderCreateTime());
            strike.setCreateTime(new Date());
            strike.setCreateBy(currentEmpId);
            strike.setUpdateTime(new Date());
            strike.setUpdateBy(currentEmpId);
            strike.setUsable(true);

           int countStrikeNucleus = strikeNucleus.insertStrikeNucleus(strike);
           if(updateCount>0 && countStrikeNucleus>0){

               return JsonData.successMsg("操作成功，请等待审核员审核结果");
           }else{

               return JsonData.fail("操作出错,请刷新重试!!");
           }

        }

    }

    /**
     * 显示需要审核的账单
      * @param strike
     * @return
     */
public JsonData selectStrikeNucleusListService(StrikeNucleus strike, Integer pageNum, Integer pageSize){
    PageHelper.startPage(pageNum, pageSize);
    List<StrikeNucleus> list =strikeNucleus.selectStrikeNucleusList(strike);
    PageInfo<StrikeNucleus> page = new PageInfo<>(list);
    return JsonData.success(page,"查询成功");
}
/**
 * 处理审批流程
 */
@Transactional(readOnly = false)
public JsonData updateStrikeService(StrikeNucleus strike,boolean flag){
    //若通过则直接更新数据,并设置订单表为
    UserOrders userOrders = new UserOrders();

    if(StringUtils.isBlank(strike.getNucleusOpinion())){

        return JsonData.fail("操作有误,请填写理由");

    }
    if(flag){

        strike.setNucleusStatus(1);
        userOrders.setAccountState(3);
        userOrders.setOrderId(strike.getOrderId());
        //若通过则更改此条记录状态为已取消
        userOrders.setOrderStatus(4);
       int count= strikeNucleus.updateStrikeNucleusById(strike);
       int countOrders =orders.updateUserOrdersByOrderId(userOrders);
       if(count>0 && countOrders>0){

           return JsonData.successMsg("已审核操作成功");
       }else{
           return JsonData.fail("数据有误，请刷新后重试，或联系管理员");
       }

    }else {
        //设置订单状态为已写卡
        userOrders.setOrderStatus(2);
        strike.setNucleusStatus(1);
        userOrders.setAccountState(2);
        userOrders.setOrderId(strike.getOrderId());
        int count= strikeNucleus.updateStrikeNucleusById(strike);
        int countOrders =orders.updateUserOrdersByOrderId(userOrders);
        if(count>0 && countOrders>0){

            return JsonData.successMsg("已审核操作成功");
        }else{
            return JsonData.fail("数据有误，请刷新后重试，或联系管理员");
        }

    }
}

    @Override
    public JsonData selectHistoryStrikeNucleusService(Integer userId) {
        List<StrikeNucleus> list = strikeNucleus.selectStrikeNucleusByUserId(userId);
        return JsonData.success(list,"查询成功");
    }

}
