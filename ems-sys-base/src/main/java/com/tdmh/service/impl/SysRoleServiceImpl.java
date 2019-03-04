package com.tdmh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tdmh.common.BeanValidator;
import com.tdmh.common.Const;
import com.tdmh.common.JsonData;
import com.tdmh.entity.SysPermission;
import com.tdmh.entity.SysRole;
import com.tdmh.entity.mapper.SysRoleMapper;
import com.tdmh.exception.ParameterException;
import com.tdmh.param.SysRoleParam;
import com.tdmh.service.ISysRolePermService;
import com.tdmh.service.ISysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Iterator;
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
        role.setRoleDists(roleParam.getDistIds());
        role.setRoleOrgs(roleParam.getOrgIds());
        role.setIsAdmin(roleParam.getIsAdmin());
        role.setRemarks(roleParam.getRemarks());
        role.setUsable(true);
        role.setCreateBy(roleParam.getCreateBy());
        role.setCreateTime(new Date());
        role.setUpdateBy(roleParam.getUpdateBy());
        int resultCount = roleMapper.insert(role);
        if (resultCount == 0) {
            return JsonData.fail("创建角色失败");
        }
        Integer roleId = roleMapper.getRoleIdByName(roleName);
        role.setRoleId(roleId);
        List<Integer> permList = null;
        if(StringUtils.isNotEmpty(roleParam.getPermIds())){
            String[] perms = roleParam.getPermIds().split(Const.DEFAULT_SEPARATOR);
            permList = Lists.newArrayListWithCapacity(perms.length);
            for(int i=0;i<perms.length;i++) {
                int perm = Integer.parseInt(perms[i]);
                permList.add(perm);
            }
        }
        JsonData data = rolePermService.changeRolePerms(roleId, permList,roleParam.getCreateBy());
        if (data.getStatus() == 0) {
            Set<SysPermission> permissions = Sets.newHashSet();
            List<Integer> permIds = permList;
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
        BeanValidator.check(roleParam);
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
        sysRole.setRoleDists(roleParam.getDistIds());
        sysRole.setRoleOrgs(roleParam.getOrgIds());
        sysRole.setIsAdmin(roleParam.getIsAdmin());
        sysRole.setRemarks(roleParam.getRemarks());
        sysRole.setUpdateBy(roleParam.getUpdateBy());
        int resultCount = roleMapper.updateByPrimaryKey(sysRole);
        if (resultCount == 0) {
            return JsonData.fail("更新角色失败");
        }
        List<Integer> permList = null;
        if(StringUtils.isNotEmpty(roleParam.getPermIds())){
            String[] perms = roleParam.getPermIds().split(Const.DEFAULT_SEPARATOR);
            permList = Lists.newArrayListWithCapacity(perms.length);
            for(int i=0;i<perms.length;i++) {
                int perm = Integer.parseInt(perms[i]);
                permList.add(perm);
            }
        }
        JsonData data = rolePermService.changeRolePerms(roleId, permList, roleParam.getUpdateBy());
        if (data.getStatus() == 0) {
            Set<SysPermission> permissions = Sets.newHashSet();
            List<Integer> permIds = permList;
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
            return JsonData.successMsg("更新角色成功");
        } else {
            return JsonData.fail("更新角色失败");
        }
    }

    @Override
    @Transactional(readOnly = false)
    public JsonData deleteRole(List<Integer> roleIds, Integer currentEmpId) {
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
        Iterator<Integer> it = roleIds.iterator();
        while (it.hasNext()){
            Integer roleId = it.next();
            Iterator<SysRole> its = sysRoleList.iterator();
            while (its.hasNext()){
                SysRole perm = its.next();
                if(roleId.equals(perm.getRoleId())){
                    sysRoleList.remove(perm);
                    break;
                }
            }
        }
        return JsonData.successMsg("删除角色成功");
    }

    @Override
    public JsonData selectRole(String roleName, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if (roleName == null) {
            List<SysRole> roles = roleMapper.selectAll();
            if (roles == null || roles.size() == 0) {
                return JsonData.successMsg("搜索结果为空");
            }
            PageInfo<SysRole> pageInfo = new PageInfo<>(roles);
            return JsonData.success(pageInfo, "查询成功");
        }
        List<SysRoleParam> roleList = Lists.newArrayList();
        for (SysRole sysRole : sysRoleList) {
            if (sysRole.getRoleName().contains(roleName)) {
                SysRoleParam sysRoleParam = new SysRoleParam();
                sysRoleParam.setRoleId(sysRole.getRoleId());
                sysRoleParam.setRoleName(sysRole.getRoleName());
                sysRole.setRoleDistList();
                sysRoleParam.setDistIds(StringUtils.join(sysRole.getRoleDistList(), Const.DEFAULT_SEPARATOR));
                sysRole.setRoleOrgList();
                sysRoleParam.setOrgIds(StringUtils.join(sysRole.getRoleOrgList(), Const.DEFAULT_SEPARATOR));
                sysRole.setRolePermList();
                sysRoleParam.setPermIds(StringUtils.join(sysRole.getRolePermList(), Const.DEFAULT_SEPARATOR));
                sysRoleParam.setIsAdmin(sysRole.getIsAdmin());
                sysRoleParam.setCreateTime(sysRole.getCreateTime());
                sysRoleParam.setRemarks(sysRole.getRemarks());
                roleList.add(sysRoleParam);
            }
        }
        if (roleList.size() == 0) {
            return JsonData.successMsg("查询结果为空");
        } else {
            return JsonData.success(roleList, "查询成功");
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
