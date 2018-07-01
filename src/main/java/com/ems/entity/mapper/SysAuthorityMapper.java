package com.ems.entity.mapper;

import com.ems.entity.SysAuthority;

public interface SysAuthorityMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysAuthority record);

    int insertSelective(SysAuthority record);

    SysAuthority selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysAuthority record);

    int updateByPrimaryKey(SysAuthority record);
}