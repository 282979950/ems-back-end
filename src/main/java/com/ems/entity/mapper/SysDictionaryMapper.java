package com.ems.entity.mapper;

import com.ems.entity.SysDictionary;

public interface SysDictionaryMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysDictionary record);

    int insertSelective(SysDictionary record);

    SysDictionary selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysDictionary record);

    int updateByPrimaryKey(SysDictionary record);
}