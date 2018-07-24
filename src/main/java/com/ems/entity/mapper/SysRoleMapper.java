package com.ems.entity.mapper;

import com.ems.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    int checkRoleNameExist(@Param("roleName") String roleName);

    Integer getRoleIdByName(@Param("roleName")String roleName);

    SysRole getRoleByName(@Param("roleName") String roleName);

    List<SysRole> selectAll();

    int deleteRoleById(@Param("roleId") Integer roleId, @Param("updateBy") Integer updateBy);
}