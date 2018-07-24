package com.ems.service.impl;

import com.ems.common.Const;
import com.ems.dto.SysRoleDTO;
import com.ems.entity.SysRole;
import com.ems.entity.mapper.SysRoleMapper;
import com.ems.service.ISysRolePermService;
import com.ems.service.ISysRoleService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
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

    @PostConstruct
    private void initializeSysRole() {
        sysRoleList = roleMapper.selectAll();
    }

    @Override
    @Transactional(readOnly = false)
    public int createRole(SysRoleDTO roleDTO) {
        String roleName = roleDTO.getRoleName();
        if (checkRoleNameExist(roleName)) {
            return 0;
        }
        SysRole role = new SysRole();
        role.setRoleName(roleName);
        role.setRoleDists(StringUtils.join(roleDTO.getDistIdList(), Const.DEFAULT_SEPARATOR));
        role.setRoleOrgs(StringUtils.join(roleDTO.getOrgIdList(), Const.DEFAULT_SEPARATOR));
        role.setRemarks(roleDTO.getRemarks());
        // TODO: 2018/7/18
        role.setCreateBy(1000000000);
        role.setUpdateBy(1000000000);
        int resultCount = roleMapper.insertSelective(role);
        Integer roleId = roleMapper.getRoleIdByName(roleName);
        rolePermService.changeRolePerms(roleId, roleDTO.getPermIdList());
        return resultCount;
    }

    @Override
    public int updateRole(SysRoleDTO roleDTO) {
        Integer roleId = roleDTO.getRoleId();
        //检查roleId是否存在
        if (!checkRoleIdExist(roleId)) {
            return 0;
        }
        SysRole sysRole = getRoleById(roleId);
        sysRole.setRoleName(null);
        String roleName = roleDTO.getRoleName();
        //检查新的roleName是否存在
        if (checkRoleNameExist(roleName)) {
            return 0;
        }
        sysRole.setRoleName(roleName);
        sysRole.setRoleDists(StringUtils.join(roleDTO.getDistIdList(), Const.DEFAULT_SEPARATOR));
        sysRole.setRoleOrgs(StringUtils.join(roleDTO.getOrgIdList(), Const.DEFAULT_SEPARATOR));
        sysRole.setCreateBy(1000000000);
        sysRole.setUpdateBy(1000000000);
        int resultCount = roleMapper.updateByPrimaryKey(sysRole);
        rolePermService.changeRolePerms(roleId, roleDTO.getPermIdList());
        return resultCount;
    }

    @Override
    public int deleteRole(Integer roleId) {
        //检查roleId是否存在
        if (!checkRoleIdExist(roleId)) {
            return 0;
        }
        SysRole sysRole = getRoleById(roleId);
        sysRole.setUsable(false);
        // TODO: 2018/7/19
        int resultCount = roleMapper.deleteRoleById(roleId, 1000000000);
        rolePermService.deleteRolePerms(roleId);
        return resultCount;
    }

    @Override
    public List<SysRole> selectRole(String roleName) {
        List<SysRole> roleList = Lists.newArrayList();
        for (SysRole sysRole : sysRoleList) {
            if(sysRole.getRoleName().contains(roleName)){
                roleList.add(sysRole);
            }
        }
        return roleList;
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
