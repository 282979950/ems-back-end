package com.ems.service.impl;

import com.ems.entity.SysRolePerm;
import com.ems.entity.mapper.SysRolePermMapper;
import com.ems.service.ISysRolePermService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void changeRolePerms(Integer roleId, List<Integer> permList) {
        if (roleId == null || permList == null) {
            return;
        }
        List<SysRolePerm> rolePermList = getRolePermList(roleId,permList);
        //删除旧的权限
        deleteRolePerms(roleId);
        //批量插入新的权限
        rolePermMapper.batchInsert(rolePermList);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteRolePerms(Integer roleId){
        rolePermMapper.deleteByRoleId(roleId);
    }

    private List<SysRolePerm> getRolePermList(Integer roleId, List<Integer> permList) {
        List<SysRolePerm> rolePermList = Lists.newArrayListWithCapacity(permList.size());
        for (Integer perm : permList) {
            SysRolePerm rolePerm = new SysRolePerm();
            rolePerm.setRoleId(roleId);
            rolePerm.setPermId(perm);
            // TODO: 2018/7/19
            rolePerm.setCreateBy(1000000000);
            rolePerm.setUpdateBy(1000000000);
            rolePermList.add(rolePerm);
        }
        return rolePermList;
    }
}
