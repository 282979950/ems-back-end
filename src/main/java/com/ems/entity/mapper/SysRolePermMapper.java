package com.ems.entity.mapper;

import com.ems.entity.SysRolePerm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRolePermMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRolePerm record);

    int insertSelective(SysRolePerm record);

    SysRolePerm selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRolePerm record);

    int updateByPrimaryKey(SysRolePerm record);

    int deleteByRoleId(Integer roleId);

    int batchInsert(@Param("rolePermList") List<SysRolePerm> rolePermList);

    List<SysRolePerm> selectByPermissionId(@Param("permIds") List <Integer> permIds);
}