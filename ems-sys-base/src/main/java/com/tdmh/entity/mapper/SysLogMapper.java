package com.tdmh.entity.mapper;

import com.tdmh.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper @Component
public interface SysLogMapper {
    int insert(SysLog record);

    int insertSelective(SysLog record);
}