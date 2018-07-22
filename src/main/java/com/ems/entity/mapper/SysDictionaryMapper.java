package com.ems.entity.mapper;

import com.ems.entity.SysDictionary;

public interface SysDictionaryMapper {
    int insert(SysDictionary record);

    int insertSelective(SysDictionary record);
}