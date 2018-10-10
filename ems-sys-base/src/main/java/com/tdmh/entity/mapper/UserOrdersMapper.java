package com.tdmh.entity.mapper;

import com.tdmh.entity.UserOrders;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper @Component
public interface UserOrdersMapper {
    int deleteByPrimaryKey(Integer orderId);

    int insert(UserOrders record);

    int insertSelective(UserOrders record);

    UserOrders selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(UserOrders record);

    int updateByPrimaryKey(UserOrders record);

    int createFirstOrder(UserOrders record);
}