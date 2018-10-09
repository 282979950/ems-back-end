package com.tdmh.entity.mapper;

import com.tdmh.entity.UserOrders;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOrdersMapper {
    int deleteByPrimaryKey(Integer orderId);

    int insert(UserOrders record);

    int insertSelective(UserOrders record);

    UserOrders selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(UserOrders record);

    int updateByPrimaryKey(UserOrders record);

    int createFirstOrder(UserOrders record);
}