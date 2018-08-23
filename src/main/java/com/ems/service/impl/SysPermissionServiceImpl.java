package com.ems.service.impl;

import com.ems.common.BeanValidator;
import com.ems.common.JsonData;
import com.ems.entity.Employee;
import com.ems.entity.SysMenu;
import com.ems.entity.SysPermission;
import com.ems.entity.mapper.SysMenuMapper;
import com.ems.entity.mapper.SysPermissionMapper;
import com.ems.exception.ParameterException;
import com.ems.service.ISysPermissionService;
import com.ems.shiro.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
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

    @Autowired
    private SysMenuMapper sysMenuMapper;

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
    public JsonData createPermission(SysPermission permission) {
        BeanValidator.check(permission);
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        permission.setCreateBy(currentEmpId);
        permission.setUpdateBy(currentEmpId);
        int resultCount = permissionMapper.insert(permission);
        if (resultCount == 0) {
            return JsonData.fail("新建权限失败");
        }
        permissionList.add(permission);
        return JsonData.successMsg("新建权限成功");
    }

    @Override
    @Transactional
    public JsonData updatePermission(SysPermission permission) {
        BeanValidator.check(permission);
        SysPermission oldPermission = getPermissionById(permission.getPermId());
        if (oldPermission == null) {
            throw new ParameterException("原权限不存在");
        }
        oldPermission.setPermName(permission.getPermName());
        oldPermission.setMenuId(permission.getMenuId());
        oldPermission.setPermCaption(permission.getPermCaption());
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        oldPermission.setUpdateBy(currentEmpId);
        int resultCount = permissionMapper.update(oldPermission);
        if (resultCount == 0) {
            return JsonData.fail("更新权限失败");
        } else {
            return JsonData.successMsg("更新权限成功");
        }
    }

    @Override
    @Transactional
    public JsonData deletePermission(List <Integer> permIds) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        List<SysPermission> permList = new ArrayList<SysPermission>();
        for(Integer pId : permIds) {
            SysPermission permission = getPermissionById(pId);
            if (permission == null) {
                throw new ParameterException("原权限不存在");
            }
            permission.setUpdateBy(currentEmpId);
            permission.setUsable(false);
            permList.add(permission);
        }
        int resultCount = permissionMapper.deleteBatch(permList);
        if (resultCount == 0) {
            return JsonData.fail("删除权限失败");
        }
        return JsonData.successMsg("删除权限成功");
    }

    @Override
    public JsonData selectPermission(String permName, String permCaption, String menuName) {
        List<SysPermission> permissions = permissionMapper.select(permName, permCaption, menuName);
        if (permissions == null || permissions.size()==0) {
            return JsonData.successMsg("查询结果为空");
        }
        else {
            return JsonData.success(permissions,"查询成功");
        }
    }

    @Override
    public JsonData listAllMenus() {
        List<SysMenu> menus = sysMenuMapper.selectAll();
        if (menus == null || menus.size()==0) {
            return JsonData.successMsg("查询结果为空");
        }
        else {
            return JsonData.successData(menus);
        }
    }
}
