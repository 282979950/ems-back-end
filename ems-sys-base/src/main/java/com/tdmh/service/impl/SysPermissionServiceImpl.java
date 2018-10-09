package com.tdmh.service.impl;

import com.tdmh.common.BeanValidator;
import com.tdmh.common.JsonData;
import com.tdmh.entity.SysPermission;
import com.tdmh.entity.SysRolePerm;
import com.tdmh.entity.mapper.SysPermissionMapper;
import com.tdmh.entity.mapper.SysRolePermMapper;
import com.tdmh.exception.ParameterException;
import com.tdmh.service.ISysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
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
    private SysRolePermMapper sysRolePermMapper;

    private static List<SysPermission> permissionList;

    /**
     * 初始化权限列表
     */
    @PostConstruct
    private void initializeSysAuthorization() {
//        permissionList = permissionMapper.selectAll();
    }

    public static List<SysPermission> getPermissionList(){
        return  permissionList;
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
        permission.setUsable(true);
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
        oldPermission.setPermCaption(permission.getPermCaption());
        oldPermission.setPermHref(permission.getPermHref());
        oldPermission.setPermParentId(permission.getPermParentId());
        oldPermission.setIsButton(permission.getIsButton());
        oldPermission.setUsable(permission.getUsable());
        oldPermission.setRemarks(permission.getRemarks());
        oldPermission.setUpdateBy(permission.getUpdateBy());
        int resultCount = permissionMapper.updateByPrimaryKeySelective(oldPermission);
        if (resultCount == 0) {
            return JsonData.fail("更新权限失败");
        } else {
            return JsonData.successMsg("更新权限成功");
        }
    }

    @Override
    @Transactional
    public JsonData deletePermission(List <Integer> permIds , Integer currentEmpId) {
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
        List<SysRolePerm> role_perm = sysRolePermMapper.selectByPermissionId(permIds);
        if(role_perm.size()>0){
            throw new ParameterException("原权限不能删除");
        }
        int resultCount = permissionMapper.deleteBatch(permList);
        if (resultCount == 0) {
            return JsonData.fail("删除权限失败");
        }
        Iterator<Integer> it = permIds.iterator();
        while (it.hasNext()){
            Integer perId = it.next();
            Iterator<SysPermission> its = permissionList.iterator();
            while (its.hasNext()){
                SysPermission perm = its.next();
                if(perId.equals(perm.getPermId())){
                    permissionList.remove(perm);
                    break;
                }
            }
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
        List<SysPermission> permissions =  new ArrayList<SysPermission>();
        for(SysPermission perm : permissionList) {
        	if(!perm.getIsButton()) {
        		permissions.add(perm);
        	}
        }
        if (permissions == null || permissions.size()==0) {
            return JsonData.successMsg("查询结果为空");
        }
        else {
            return JsonData.successData(permissions);
        }
    }

    @Override
    public JsonData listAllMenusAndPerms() {
        List<SysPermission> permissions = permissionList;
        if (permissions == null || permissions.size()==0) {
            return JsonData.successMsg("查询结果为空");
        }
        else {
            return JsonData.successData(permissions);
        }
    }
}
