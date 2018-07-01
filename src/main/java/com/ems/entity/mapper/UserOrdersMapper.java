package com.ems.entity.mapper;

import com.ems.entity.UserOrders;

public interface UserOrdersMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserOrders record);

    int insertSelective(UserOrders record);

    UserOrders selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserOrders record);

    int updateByPrimaryKey(UserOrders record);
}