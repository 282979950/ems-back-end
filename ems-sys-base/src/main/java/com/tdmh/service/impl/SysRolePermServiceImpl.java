package com.tdmh.service.impl;

import com.google.common.collect.Lists;
import com.tdmh.common.JsonData;
import com.tdmh.entity.SysRolePerm;
import com.tdmh.entity.mapper.SysRolePermMapper;
import com.tdmh.exception.ParameterException;
import com.tdmh.service.ISysRolePermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色权限服务实现类
 *
 * @author litairan on 2018/7/19.
 */
@Service("iSysRolePermService")
@Transactional(readOnly = true)
public class SysRolePermServiceImpl implements ISysRolePermService {

    @Autowired
    private SysRolePermMapper rolePermMapper;

    @Override
    @Transactional(readOnly = false)
    public JsonData changeRolePerms(Integer roleId, List<Integer> permList,Integer currentEmpId) {
        if (roleId == null) {
            throw new ParameterException("权限不能为空");
        }
        if (permList == null) {
            throw new ParameterException("权限列表不能为空");
        }
        List<SysRolePerm> rolePermList = getRolePermList(roleId,permList,currentEmpId);
        //删除旧的权限
        List<Integer> roleIds = Lists.newArrayList();
        roleIds.add(roleId);
        deleteRolePerms(roleIds);
        //批量插入新的权限
        int size = permList.size();
        if (size == 0) {
            return JsonData.successMsg("更新权限成功");
        }
        int resultCount = rolePermMapper.batchInsert(rolePermList);
        if (size == resultCount) {
            return JsonData.successMsg("更新权限成功");
        }
        else {
            return JsonData.fail("更新权限失败");
        }
    }

    @Override
    @Transactional(readOnly = false)
    public int deleteRolePerms(List<Integer> roleIds){
        return rolePermMapper.deleteByRoleId(roleIds);
    }

    private List<SysRolePerm> getRolePermList(Integer roleId, List<Integer> permList,Integer currentEmpId ) {
        List<SysRolePerm> rolePermList = Lists.newArrayListWithCapacity(permList.size());
        for (Integer perm : permList) {
            SysRolePerm rolePerm = new SysRolePerm();
            rolePerm.setRoleId(roleId);
            rolePerm.setPermId(perm);
            rolePerm.setCreateBy(currentEmpId);
            rolePerm.setUpdateBy(currentEmpId);
            rolePermList.add(rolePerm);
        }
        return rolePermList;
    }
}
