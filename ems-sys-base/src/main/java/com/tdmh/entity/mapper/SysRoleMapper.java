package com.tdmh.entity.mapper;

import com.tdmh.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper @Component
public interface SysRoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    int checkRoleNameExist(@Param("roleName") String roleName);

    Integer getRoleIdByName(@Param("roleName") String roleName);

    SysRole getRoleByName(@Param("roleName") String roleName);

    List<SysRole> selectAll();

    int deleteRoleById(@Param("roleId") Integer roleId, @Param("updateBy") Integer updateBy);

    int deleteBatch(List<SysRole> sysRoles);
}