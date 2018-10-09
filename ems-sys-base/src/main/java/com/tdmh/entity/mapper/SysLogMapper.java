package com.tdmh.entity.mapper;

import com.tdmh.entity.SysLog;
import org.springframework.stereotype.Repository;

@Repository
public interface SysLogMapper {
    int insert(SysLog record);

    int insertSelective(SysLog record);
}