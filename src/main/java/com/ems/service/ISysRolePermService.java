package com.ems.service;

import com.ems.common.JsonData;

import java.util.List;

/**
 * 角色权限服务接口
 *
 * @author litairan on 2018/7/19.
 */
public interface ISysRolePermService {
    JsonData changeRolePerms(Integer roleId, List<Integer> permList);

    int deleteRolePerms(Integer roleId);
}
