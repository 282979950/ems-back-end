package com.ems.entity.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ems.entity.SysDictionary;
@Repository
public interface SysDictionaryMapper {
    int deleteByPrimaryKey(int dictId);

    int insert(SysDictionary record);

    int insertSelective(SysDictionary record);

    SysDictionary selectByPrimaryKey(String dictId);

    int updateByPrimaryKeySelective(SysDictionary record);

    int updateByPrimaryKey(SysDictionary record);
    //查看字典所有数据
    List<SysDictionary> findList();
    //根据条件查看是否存在该条记录
    int selectCountById(SysDictionary record);
    //根据字典类型查看对应字典相关数值
    List<SysDictionary>findListByType(String dictCategory);
    //删除数据
    int deleteSysDictionary(SysDictionary record);
    //依据条件查看对应数据
    List<SysDictionary>findListByDict(SysDictionary record);
}