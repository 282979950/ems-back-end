package com.ems.service;

import com.ems.common.JsonData;
import com.ems.entity.SysDistrict;

/**
 * @author litairan on 2018/7/2.
 */
public interface ISysDistrictService {
    /**
     * 检查区域是否存在
     *
     * @param distName
     * @return
     */
    boolean checkDistName(String distName);

    /**
     * 检查区域是否可用
     *
     * @param distName
     * @return
     */
    boolean checkUsable(String distName);

    /**
     * 新增区域
     * district中distName,distParentId不能为空
     * @param district
     * @return
     */
    JsonData createDistrict(SysDistrict district);

    /**
     * 依据区域名称查询
     *
     * @param name
     * @return
     */
    JsonData selectDistByName(String name);

    /**
     * 依据区域名称查询
     *
     * @param code
     * @return
     */
    JsonData selectDistByCode(String code);

    /**
     * 更新区域
     *
     * @param district
     * @return
     */
    JsonData updateSysDistrict(SysDistrict district);

    /**
     * 假删除(将usable设置为false)
     */
    JsonData deleteSysDistrict(SysDistrict district);

    /**
     * 获取父节点
     *
     * @param district
     * @return
     */
    SysDistrict getParentDist(SysDistrict district);

    /**
     * 获取区域树结构
     *
     * @return
     */
    JsonData getDistRoot();

    /**
     * 获取所有区域的列表
     *
     * @return
     */
    JsonData getDistrictList();
}
