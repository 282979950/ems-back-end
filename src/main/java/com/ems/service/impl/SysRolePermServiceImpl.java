package com.ems.service.impl;

import com.ems.common.JsonData;
import com.ems.entity.SysRolePerm;
import com.ems.entity.mapper.SysRolePermMapper;
import com.ems.exception.ParameterException;
import com.ems.service.ISysRolePermService;
import com.ems.shiro.utils.ShiroUtils;
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
    public JsonData changeRolePerms(Integer roleId, List<Integer> permList) {
        if (roleId == null) {
            throw new ParameterException("权限不能为空");
        }
        if (permList == null) {
            throw new ParameterException("权限列表不能为空");
        }
        List<SysRolePerm> rolePermList = getRolePermList(roleId,permList);
        //删除旧的权限
        deleteRolePerms(roleId);
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
    public int deleteRolePerms(Integer roleId){
        return rolePermMapper.deleteByRoleId(roleId);
    }

    private List<SysRolePerm> getRolePermList(Integer roleId, List<Integer> permList) {
        List<SysRolePerm> rolePermList = Lists.newArrayListWithCapacity(permList.size());
        for (Integer perm : permList) {
            SysRolePerm rolePerm = new SysRolePerm();
            rolePerm.setRoleId(roleId);
            rolePerm.setPermId(perm);
            Integer currentEmpId = ShiroUtils.getPrincipal().getId();
            rolePerm.setCreateBy(currentEmpId);
            rolePerm.setUpdateBy(currentEmpId);
            rolePermList.add(rolePerm);
        }
        return rolePermList;
    }
}
