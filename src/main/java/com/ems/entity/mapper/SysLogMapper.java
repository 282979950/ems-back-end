package com.ems.entity.mapper;

import com.ems.entity.SysLog;

public interface SysLogMapper {
    int insert(SysLog record);

    int insertSelective(SysLog record);
}