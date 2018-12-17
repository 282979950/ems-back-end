package com.tdmh.service;


import com.tdmh.common.JsonData;
import com.tdmh.param.SysDistrictParam;

import java.util.List;

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
     * 检查编辑时名称是否重名
     *
     * @param distName
     * @return
     */
    boolean checkIdAndName(Integer distId, String distName);

    /**
     * 新增区域
     * district中distName,distParentId不能为空
     *
     * @param district
     * @return
     */
    JsonData createDistrict(SysDistrictParam district);

    /**
     * 依据区域名称查询
     *
     * @param distName
     * @param distCode
     * @return
     */
    JsonData selectDistrict(String distName, String distCode);

    /**
     * 更新区域
     *
     * @param district
     * @return
     */
    JsonData updateSysDistrict(SysDistrictParam district);

    /**
     * 假删除(将usable设置为false)
     *
     * @param ids
     * @return
     */
    JsonData deleteSysDistrict(List<Integer> ids,Integer currentEmpId);

    /**
     * 获取所有区域的列表
     *
     * @return
     */
    List<SysDistrictParam> listData();

    SysDistrictParam treeData();
}
