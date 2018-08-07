package com.ems.service.impl;

import com.ems.common.BeanValidator;
import com.ems.common.JsonData;
import com.ems.entity.Employee;
import com.ems.entity.SysPermission;
import com.ems.entity.mapper.SysPermissionMapper;
import com.ems.exception.ParameterException;
import com.ems.service.ISysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 系统权限服务实现类
 *
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
    public JsonData createPermission(SysPermission permission, Employee currentEmp) {
        BeanValidator.check(permission);
        permission.setCreateBy(currentEmp.getEmpId());
        permission.setUpdateBy(currentEmp.getEmpId());
        int resultCount = permissionMapper.insert(permission);
        if (resultCount == 0) {
            return JsonData.fail("新建权限失败");
        }
        permissionList.add(permission);
        return JsonData.successMsg("新建权限成功");
    }

    @Override
    @Transactional
    public JsonData updatePermission(SysPermission permission, Employee currentEmp) {
        BeanValidator.check(permission);
        SysPermission oldPermission = getPermissionById(permission.getPermId());
        if (oldPermission == null) {
            throw new ParameterException("原权限不存在");
        }
        oldPermission.setPermName(permission.getPermName());
        oldPermission.setMenuId(permission.getMenuId());
        oldPermission.setPermCaption(permission.getPermCaption());
        oldPermission.setUpdateBy(currentEmp.getEmpId());
        oldPermission.setRemarks(currentEmp.getRemarks());
        int resultCount = permissionMapper.update(oldPermission);
        if (resultCount == 0) {
            return JsonData.fail("更新权限失败");
        } else {
            return JsonData.successMsg("更新权限成功");
        }
    }

    @Override
    @Transactional
    public JsonData deletePermission(Integer permId, Employee currentEmp) {
        SysPermission permission = getPermissionById(permId);
        if (permission == null) {
            throw new ParameterException("原权限不存在");
        }
        permission.setUpdateBy(currentEmp.getEmpId());
        int resultCount = permissionMapper.deleteByPermId(permission);
        if (resultCount == 0) {
            return JsonData.fail("删除权限失败");
        }
        permissionList.remove(permission);
        return JsonData.successMsg("删除权限成功");
    }

    @Override
    public JsonData selectPermission(String permName, String permCaption, String menuName) {
        List<SysPermission> permissions = permissionMapper.select(permName, permCaption, menuName);
        if (permissions == null || permissions.size()==0) {
            return JsonData.successMsg("查询结果为空");
        }
        else {
            return JsonData.successData(permissions);
        }
    }
}
