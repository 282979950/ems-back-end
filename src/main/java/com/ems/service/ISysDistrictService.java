package com.ems.service;

import com.ems.entity.SysDistrict;

import java.util.List;

/**
 * Created by litairan litairan@whtdmh.com on 2018/7/2.
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
    boolean checkUseable(String distName);

    /**
     * 新增区域
     * district中distName,distParentId不能为空
     * @param district
     * @return
     */
    int createDistrict(SysDistrict district);

    /**
     * 依据区域名称查询
     *
     * @param name
     * @return
     */
    List<SysDistrict> selectDistByName(String name);

    /**
     * 依据区域名称查询
     *
     * @param code
     * @return
     */
    List<SysDistrict> selectDistByCode(String code);

    /**
     * 更新区域
     *
     * @param district
     * @return
     */
    int updateSysDistrict(SysDistrict district);

    /**
     * 假删除(将useable设置为false)
     */
    int deleteSysDistrict(SysDistrict district);

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
    SysDistrict getDistRoot();

    /**
     * 获取所有区域的列表
     *
     * @return
     */
    List<SysDistrict> getDistrictList();
}
