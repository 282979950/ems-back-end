package com.ems.entity.mapper;

import com.ems.entity.SysOrganization;

public interface SysOrganizationMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysOrganization record);

    int insertSelective(SysOrganization record);

    SysOrganization selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysOrganization record);

    int updateByPrimaryKey(SysOrganization record);
}