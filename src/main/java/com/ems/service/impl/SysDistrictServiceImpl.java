package com.ems.service.impl;

import com.ems.common.BeanValidator;
import com.ems.common.JsonData;
import com.ems.entity.SysDistrict;
import com.ems.entity.mapper.SysDistrictMapper;
import com.ems.exception.ParameterException;
import com.ems.service.ISysDistrictService;
import com.ems.shiro.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 区域服务实现类
 *
 * @author litairan on 2018/7/2.
 */
@Service("iSysDistrict")
@Transactional(readOnly = true)
public class SysDistrictServiceImpl implements ISysDistrictService {

    @Autowired
    private SysDistrictMapper districtMapper;

    @Override
    public boolean checkDistName(String distName) {
        return districtMapper.checkDistName(distName);
    }

    @Override
    public boolean checkUsable(String distName) {
        return districtMapper.checkUsable(distName);
    }

    @Override
    @Transactional(readOnly = false)
    public JsonData createDistrict(SysDistrict district) {
        BeanValidator.check(district);
        String distName = district.getDistName();
        if (checkUsable(distName)) {
            throw new ParameterException("区域名称:" + distName + "已存在");
        }
        district.setUsable(true);
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        district.setCreateBy(currentEmpId);
        district.setUpdateBy(currentEmpId);
        int resultCount = districtMapper.insert(district);
        if (resultCount == 0) {
            return JsonData.fail("创建区域失败");
        }
        return JsonData.successMsg("创建区域成功");
    }

    @Override
    public JsonData selectDistrict(String distName, String distCode) {
        List<SysDistrict> districts = districtMapper.selectDistrict(distName, distCode);
        return districts == null || districts.size() == 0 ? JsonData.successMsg("搜索结果为空") : JsonData.success(districts, "查询成功");
    }

    @Override
    @Transactional(readOnly = false)
    public JsonData updateSysDistrict(SysDistrict district) {
        BeanValidator.check(district);
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        String distName = district.getDistName();
        if (checkUsable(distName)) {
            throw new ParameterException("区域名称:" + distName + "已存在");
        }
        district.setUpdateBy(currentEmpId);
        int resultCount = districtMapper.updateByPrimaryKey(district);
        if (resultCount == 0) {
            return JsonData.fail("更新区域失败");
        }
        return JsonData.successMsg("更新区域成功");
    }

    @Override
    @Transactional(readOnly = false)
    public JsonData deleteSysDistrict(List<Integer> ids) {
        if (ids == null || ids.size()==0) {
            return JsonData.successMsg("请选中一个要删除的区域");
        }
        List<SysDistrict> preDelete = new ArrayList<>();
        for (Integer id : ids) {
            SysDistrict district = getDistrictById(id);
            if (district != null){
                List<SysDistrict> children = getChildrenDist(id);
                if (children != null && children.size() >0) {
                    return JsonData.fail("不能删除有下级的区域");
                }else {
                    preDelete.add(district);
                }
            }
        }
        if (preDelete.size() == 0) {
            return JsonData.successMsg("请选中一个要删除的区域");
        }
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        for (SysDistrict dist : preDelete) {
            dist.setUsable(false);
            dist.setUpdateBy(currentEmpId);
        }
        int resultCount = districtMapper.deleteBatch(preDelete);
        if (resultCount < preDelete.size()) {
            return JsonData.fail("删除区域失败");
        }
        return JsonData.successMsg("删除区域成功");
    }

    @Override
    public JsonData listData() {
        List<SysDistrict> districtList = districtMapper.selectAll();
        return districtList == null || districtList.size() == 0 ? JsonData.successMsg("区域树为空，需要新建") : JsonData.successData(districtList);
    }

    private List<SysDistrict> getChildrenDist(Integer distId) {
        return districtMapper.getChildrenDist(distId);
    }

    private  SysDistrict getDistrictById(Integer distId) {
        return districtMapper.selectByPrimaryKey(distId);
    }
}
