package com.ems.entity.mapper;

import com.ems.entity.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysPermissionMapper {
    int deleteByPrimaryKey(Integer permId);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(Integer permId);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);

    List<SysPermission> selectAll();

    int update(SysPermission record);

    int deleteByPermId(SysPermission permId);

    List<SysPermission> select(@Param("permName") String permName, @Param("permType") Integer permType, @Param("menuName") String menuName);
}