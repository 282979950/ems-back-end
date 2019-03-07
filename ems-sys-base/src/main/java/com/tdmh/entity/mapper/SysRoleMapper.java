package com.tdmh.entity.mapper;

import com.tdmh.entity.SysRole;
import com.tdmh.param.SysRoleParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper @Component
public interface SysRoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(SysRoleParam record);
    int update(SysRoleParam record);

    int insertDistPerms(SysRoleParam record);

    int insertOrgPerms(SysRoleParam record);

    int insertPerms(SysRoleParam record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    boolean checkRoleNameExist(@Param("roleName") String roleName);

    Integer getRoleIdByName(@Param("roleName") String roleName);

    SysRole getRoleByName(@Param("roleName") String roleName);

    List<SysRoleParam> select(@Param("roleName") String roleName);

    List<Integer> getDistIdsByRoleId(@Param("roleId") Integer roleId);

    List<String> getDistNamesByRoleId(@Param("roleId") Integer roleId);

    List<Integer> getOrgIdsByRoleId(@Param("roleId") Integer roleId);

    List<String> getOrgNamesByRoleId(@Param("roleId") Integer roleId);

    List<Integer> getPermIdsByRoleId(@Param("roleId") Integer roleId);

    List<String> getPermNamesByRoleId(@Param("roleId") Integer roleId);

    boolean deleteDistByRoleId(@Param("roleId") Integer roleId);

    boolean deleteOrgByRoleId(@Param("roleId") Integer roleId);

    boolean deletePermByRoleId(@Param("roleId") Integer roleId);

    int deleteRoleById(@Param("roleId") Integer roleId, @Param("updateBy") Integer updateBy);

    int deleteBatch(List<SysRoleParam> sysRoles);

    SysRoleParam getRoleById(@Param("roleId") Integer roleId);

    boolean checkRoleIdExist(@Param("roleId") Integer roleId);
}