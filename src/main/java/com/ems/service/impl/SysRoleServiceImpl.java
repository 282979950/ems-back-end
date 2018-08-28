package com.ems.service.impl;

import com.ems.common.BeanValidator;
import com.ems.common.Const;
import com.ems.common.JsonData;
import com.ems.entity.SysPermission;
import com.ems.entity.SysRole;
import com.ems.entity.SysRolePerm;
import com.ems.entity.mapper.SysRoleMapper;
import com.ems.exception.ParameterException;
import com.ems.param.SysRoleParam;
import com.ems.service.ISysRolePermService;
import com.ems.service.ISysRoleService;
import com.ems.shiro.utils.ShiroUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @PostConstruct
    private void initializeSysRole() {
        sysRoleList = roleMapper.selectAll(); 
    }

    @Override
    @Transactional(readOnly = false)
    public JsonData createRole(SysRoleParam roleParam) {
        BeanValidator.check(roleParam);
        String roleName = roleParam.getRoleName();
        if (checkRoleNameExist(roleName)) {
            throw new ParameterException("角色名称已存在");
        }
        SysRole role = new SysRole();
        role.setRoleName(roleName);
        role.setRoleDists(StringUtils.join(roleParam.getDistIdList(), Const.DEFAULT_SEPARATOR));
        role.setRoleOrgs(StringUtils.join(roleParam.getOrgIdList(), Const.DEFAULT_SEPARATOR));
        role.setRemarks(roleParam.getRemarks());
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        role.setUsable(true);
        role.setCreateBy(currentEmpId);
        role.setUpdateBy(currentEmpId);
        int resultCount = roleMapper.insert(role);
        if (resultCount == 0) {
            return JsonData.fail("创建角色失败");
        }
        Integer roleId = roleMapper.getRoleIdByName(roleName);
        JsonData data = rolePermService.changeRolePerms(roleId, roleParam.getPermIdList());
        if (data.isStatus()) {
            Set<SysPermission> permissions = Sets.newHashSet();
            List<Integer> permIds = roleParam.getPermIdList();
            List<SysPermission> permissionList= SysPermissionServiceImpl.getPermissionList();
            for(Integer permId : permIds) {
                for(SysPermission sysPermission : permissionList){
                    if(permId.equals(sysPermission.getPermId())){
                        permissions.add(sysPermission);
                        continue;
                    }
                }
            }
            role.setPermissions(permissions);
            sysRoleList.add(role);
            return JsonData.successMsg("创建角色成功");
        } else {
            return JsonData.fail("创建角色失败");
        }
    }

    @Override
    @Transactional(readOnly = false)
    public JsonData updateRole(SysRoleParam roleParam) {
        Integer roleId = roleParam.getRoleId();
        if (!checkRoleIdExist(roleId)) {
            throw new ParameterException("角色ID不存在");
        }
        SysRole sysRole = getRoleById(roleId);
        if (sysRole == null) {
            throw new ParameterException("角色不存在");
        }
        sysRole.setRoleName(null);
        String roleName = roleParam.getRoleName();
        if (checkRoleNameExist(roleName)) {
            throw new ParameterException("角色名称已存在");
        }
        sysRole.setRoleName(roleName);
        sysRole.setRoleDists(StringUtils.join(roleParam.getDistIdList(), Const.DEFAULT_SEPARATOR));
        sysRole.setRoleOrgs(StringUtils.join(roleParam.getOrgIdList(), Const.DEFAULT_SEPARATOR));
        sysRole.setCreateBy(1000000000);
        sysRole.setUpdateBy(1000000000);
        int resultCount = roleMapper.updateByPrimaryKey(sysRole);
        if (resultCount == 0) {
            return JsonData.fail("更新角色失败");
        }
        JsonData data = rolePermService.changeRolePerms(roleId, roleParam.getPermIdList());
        if (data.isStatus()) {
            Set<SysPermission> permissions = Sets.newHashSet();
            List<Integer> permIds = roleParam.getPermIdList();
            List<SysPermission> permissionList= SysPermissionServiceImpl.getPermissionList();
            for(Integer permId : permIds) {
                for(SysPermission sysPermission : permissionList){
                    if(permId.equals(sysPermission.getPermId())){
                        permissions.add(sysPermission);
                        continue;
                    }
                }
            }
            sysRole.setPermissions(permissions);
            return JsonData.successMsg("创建角色成功");
        } else {
            return JsonData.fail("创建角色失败");
        }
    }

    @Override
    @Transactional(readOnly = false)
    public JsonData deleteRole(List<Integer> roleIds) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        List<SysRole> roleList = Lists.newArrayList();
        for(Integer rId : roleIds) {
            if (!checkRoleIdExist(rId)) {
                throw new ParameterException("角色ID不存在");
            }
            SysRole sysRole = getRoleById(rId);
            if (sysRole == null) {
                throw new ParameterException("角色不存在");
            }
            sysRole.setUpdateBy(currentEmpId);
            sysRole.setUsable(false);
            roleList.add(sysRole);
        }
        int resultCount = roleMapper.deleteBatch(roleList);
        if (resultCount == 0) {
            return JsonData.fail("删除角色失败");
        }
        rolePermService.deleteRolePerms(roleIds);
        return JsonData.successMsg("删除角色成功");
    }

    @Override
    public JsonData selectRole(String roleName) {
        List<SysRoleParam> roleList = Lists.newArrayList();
        for (SysRole sysRole : sysRoleList) {
            if (sysRole.getRoleName().contains(roleName)) {
            	SysRoleParam  sysRoleParam = new SysRoleParam();
            	sysRoleParam.setRoleId(sysRole.getRoleId());
            	sysRoleParam.setRoleName(sysRole.getRoleName());
            	sysRole.setRoleDistList();
            	sysRoleParam.setDistIdList(sysRole.getRoleDistList());
            	sysRole.setRoleOrgList();
            	sysRoleParam.setOrgIdList(sysRole.getRoleOrgList());
            	sysRole.setRolePermList();
            	sysRoleParam.setPermIdList(sysRole.getRolePermList());
                sysRoleParam.setCreateTime(sysRole.getCreateTime());
                sysRoleParam.setRemarks(sysRole.getRemarks());
                roleList.add(sysRoleParam);
            }
        }
        if (roleList.size() == 0) {
            return JsonData.successMsg("查询结果为空");
        } else {
            return JsonData.success(roleList,"查询成功");
        }
    }

    private boolean checkRoleIdExist(Integer roleId) {
        for (SysRole sysRole : sysRoleList) {
            if (sysRole.getRoleId().equals(roleId)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkRoleNameExist(String roleName) {
        for (SysRole sysRole : sysRoleList) {
            if (StringUtils.equals(sysRole.getRoleName(), roleName)) {
                return true;
            }
        }
        return false;
    }

    private SysRole getRoleById(Integer roleId) {
        for (SysRole sysRole : sysRoleList) {
            if (sysRole.getRoleId().equals(roleId)) {
                return sysRole;
            }
        }
        return null;
    }
}
