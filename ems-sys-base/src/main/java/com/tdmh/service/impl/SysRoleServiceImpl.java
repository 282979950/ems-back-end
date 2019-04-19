package com.tdmh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.tdmh.common.BeanValidator;
import com.tdmh.common.JsonData;
import com.tdmh.entity.SysRole;
import com.tdmh.entity.mapper.SysRoleMapper;
import com.tdmh.exception.ParameterException;
import com.tdmh.param.SysRoleParam;
import com.tdmh.service.ISysRolePermService;
import com.tdmh.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统角色服务实现类
 *
 * @author litairan on 2018/7/18.
 */
@Service("iSysRoleService")
@Transactional(readOnly = true)
public class SysRoleServiceImpl implements ISysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private ISysRolePermService rolePermService;

    private static List<SysRole> sysRoleList;

    @Override
    @Transactional(readOnly = false)
    public JsonData createRole(SysRoleParam roleParam) {
        BeanValidator.check(roleParam);
        String roleName = roleParam.getRoleName();
        if (checkRoleNameExist(roleName)) {
            throw new ParameterException("角色名称已存在");
        }
        roleMapper.insert(roleParam);
        roleMapper.insertDistPerms(roleParam);
        roleMapper.insertOrgPerms(roleParam);
        roleMapper.insertPerms(roleParam);
        return JsonData.successMsg("新增成功");
    }

    @Override
    @Transactional(readOnly = false)
    public JsonData updateRole(SysRoleParam roleParam) {
        BeanValidator.check(roleParam);
        Integer roleId = roleParam.getRoleId();
        if (!checkRoleIdExist(roleId)) {
            throw new ParameterException("角色ID不存在");
        }
        SysRoleParam sysRole = getRoleById(roleId);
        if (sysRole == null) {
            throw new ParameterException("角色不存在");
        }
        // 清理原关联关系
        roleMapper.deleteDistByRoleId(roleId);
        roleMapper.deleteOrgByRoleId(roleId);
        roleMapper.deletePermByRoleId(roleId);
        // 更新新关联关系
        roleMapper.update(roleParam);
        roleMapper.insertDistPerms(roleParam);
        roleMapper.insertOrgPerms(roleParam);
        roleMapper.insertPerms(roleParam);
        return JsonData.successMsg("编辑成功");
    }

    @Override
    @Transactional(readOnly = false)
    public JsonData deleteRole(List<Integer> roleIds, Integer currentEmpId) {
        List<SysRoleParam> roleList = Lists.newArrayList();
        for(Integer rId : roleIds) {
            if (!checkRoleIdExist(rId)) {
                throw new ParameterException("角色ID不存在");
            }
            SysRoleParam role = getRoleById(rId);
            if (role == null) {
                throw new ParameterException("角色不存在");
            }
            role.setUpdateBy(currentEmpId);
            role.setUsable(false);
            roleList.add(role);
            roleMapper.deleteDistByRoleId(rId);
            roleMapper.deleteOrgByRoleId(rId);
            roleMapper.deletePermByRoleId(rId);
        }
        roleMapper.deleteBatch(roleList);
        return JsonData.successMsg("删除角色成功");
    }

    @Override
    public JsonData selectRole(String roleName, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysRoleParam> roles = roleMapper.select(roleName);
        if (roles == null || roles.size() == 0) {
            return JsonData.successMsg("搜索结果为空");
        }
        PageInfo<SysRoleParam> pageInfo = new PageInfo<>(roles);
        return JsonData.success(pageInfo, "查询成功");
    }

    @Override
    public JsonData getAllRole() {
        List<SysRoleParam> roles = roleMapper.select(null);
        return JsonData.success(roles, "查询成功");
    }

    private boolean checkRoleIdExist(Integer roleId) {
        return roleMapper.checkRoleIdExist(roleId);
    }

    private boolean checkRoleNameExist(String roleName) {
        return roleMapper.checkRoleNameExist(roleName);
    }

    private SysRoleParam getRoleById(Integer roleId) {
        return roleMapper.getRoleById(roleId);
    }
}
