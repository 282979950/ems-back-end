package com.tdmh.entity.mapper;

import com.tdmh.entity.SysRolePerm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SysRolePermMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRolePerm record);

    int insertSelective(SysRolePerm record);

    SysRolePerm selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRolePerm record);

    int updateByPrimaryKey(SysRolePerm record);

    int deleteByRoleId(@Param("roleIds") List<Integer> roleIds);

    int batchInsert(@Param("rolePermList") List<SysRolePerm> rolePermList);

    List<SysRolePerm> selectByPermissionId(@Param("permIds") List<Integer> permIds);
}