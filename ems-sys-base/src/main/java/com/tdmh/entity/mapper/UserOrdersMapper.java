package com.tdmh.entity.mapper;

import com.tdmh.entity.UserOrders;
import com.tdmh.param.WXOrderParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface UserOrdersMapper {

    int deleteByPrimaryKey(Integer orderId);

    int insert(UserOrders record);

    int insertSelective(UserOrders record);

    UserOrders selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(UserOrders record);

    int updateByPrimaryKey(UserOrders record);

    int createFirstOrder(UserOrders record);

    BigDecimal findHasUsedGasInYear(@Param("userId") Integer userId);

    int createChangeUserOrder(UserOrders record);

    int countUserOrdersByTimeEmployeeId(UserOrders record);

    int updateUserOrdersByOrderId(UserOrders record);

    List<WXOrderParam> getAllWXOrders(Integer userId);

    List<Integer> getAllTimeoutWXOrders();

    int finishWXOrder(Integer orderId);

    int cancelWxOrder(Integer userId);

    List<UserOrders> selectBusinessDataQuery(UserOrders record);
    List<UserOrders> selectordersListQuery(Integer userId);
    Date getCreateTimeByOrderId(@Param("orderId") Integer orderId);
    Date getNowCreateTimeByOrderId(@Param("userId") Integer userId);
    List<UserOrders> selectReportBusinessDataQuery(UserOrders record);
    //每天限定充值一次，查询当前是否存在：2普通订单，3补卡订单，5微信订单
    int queryCurrentDataByDate(String createTime);
}