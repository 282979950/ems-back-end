package com.tdmh.service;

import com.tdmh.common.JsonData;

import java.util.List;

/**
 * 角色权限服务接口
 *
 * @author litairan on 2018/7/19.
 */
public interface ISysRolePermService {
    JsonData changeRolePerms(Integer roleId, List<Integer> permList,Integer currentEmpId);

    int deleteRolePerms(List<Integer> roleIds);
}
