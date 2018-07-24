package com.ems.service;

import java.util.List;

/**
 * 角色权限服务接口
 *
 * @author litairan on 2018/7/19.
 */
public interface ISysRolePermService {
    void changeRolePerms(Integer roleId, List<Integer> permList);

    void deleteRolePerms(Integer roleId);

}
