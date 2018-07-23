package com.ems.service.impl;

import com.ems.entity.Employee;
import com.ems.entity.SysPermission;
import com.ems.entity.mapper.SysPermissionMapper;
import com.ems.service.ISysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

/**
 * 系统权限服务实现类
 * @author litairan on 2018/7/13.
 */
@Service("iSysPermissionService")
public class SysPermissionServiceImpl implements ISysPermissionService {

    @Autowired
    private SysPermissionMapper permissionMapper;

    private static List<SysPermission> permissionList;

    /**
     * 初始化权限列表
     */
    @PostConstruct
    private void initializeSysAuthorization() {
        permissionList = permissionMapper.selectAll();
    }

    /**
     * 依据权限ID获取权限
     *
     * @param permId
     * @return
     */
    private SysPermission getPermissionById(Integer permId) {
        for (SysPermission permission : permissionList) {
            if (permission.getPermId().equals(permId)) {
                return permission;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public int createPermission(SysPermission permission, Employee currentEmp) {
        if (permission.getPermId() == null || permission.getPermName() == null || permission.getMenuId() == null || permission.getPermType() == null) {
            return 0;
        }
        permission.setCreateBy(currentEmp.getEmpId());
        Date date = new Date();
        permission.setCreateTime(date);
        permission.setUpdateBy(currentEmp.getEmpId());
        permission.setUpdateTime(date);
        permission.setUsable(true);
        permissionList.add(permission);
        return permissionMapper.insert(permission);
    }

    @Override
    @Transactional
    public int updatePermission(SysPermission permission, Employee currentEmp) {
        if (permission.getPermId() == null || permission.getPermName() == null || permission.getMenuId() == null || permission.getPermType() == null) {
            return 0;
        }
        SysPermission oldPermission = getPermissionById(permission.getPermId());
        if (oldPermission == null) {
            return 0;
        }
        oldPermission.setPermName(permission.getPermName());
        oldPermission.setMenuId(permission.getMenuId());
        oldPermission.setPermType(permission.getPermType());
        oldPermission.setUpdateTime(new Date());
        oldPermission.setUpdateBy(currentEmp.getEmpId());
        oldPermission.setRemarks(currentEmp.getRemarks());
        return permissionMapper.update(oldPermission);
    }

    @Override
    @Transactional
    public int deletePermission(Integer permId, Employee currentEmp) {
        SysPermission permission = getPermissionById(permId);
        if (permission == null) {
            return 0;
        }
        permission.setUpdateTime(new Date());
        permission.setUpdateBy(currentEmp.getEmpId());
        permission.setUsable(false);
        int resultCount = permissionMapper.deleteByPermId(permission);
        if (resultCount > 0) {
            permissionList.remove(permission);
        }
        return resultCount;
    }

    @Override
    public List<SysPermission> selectPermission(String permName, Integer permType, String menuName) {
        return permissionMapper.select(permName, permType, menuName);
    }
}
