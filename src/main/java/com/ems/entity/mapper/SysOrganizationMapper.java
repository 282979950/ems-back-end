package com.ems.entity.mapper;

import com.ems.entity.SysOrganization;

public interface SysOrganizationMapper {
    int insert(SysOrganization record);

    int insertSelective(SysOrganization record);
}