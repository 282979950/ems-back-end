package com.tdmh.service.impl;

import com.tdmh.common.BeanValidator;
import com.tdmh.common.JsonData;
import com.tdmh.entity.SysDistrict;
import com.tdmh.entity.mapper.SysDistrictMapper;
import com.tdmh.exception.ParameterException;
import com.tdmh.param.SysDistrictParam;
import com.tdmh.service.ISysDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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

    /**
     * 根节点ID
     */
    private static int ROOT_DIST_ID = 1000;

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
    public boolean checkIdAndName(Integer distId, String distName) {
        return districtMapper.checkIdAndName(distId, distName);
    }

    @Override
    @Transactional(readOnly = false)
    public JsonData createDistrict(SysDistrictParam district) {
        BeanValidator.check(district);
        String distName = district.getDistName();
        if (checkUsable(distName)) {
            throw new ParameterException("区域名称:" + distName + "已存在");
        }
        district.setUsable(true);
        int resultCount = districtMapper.createDistrict(district);
        if (resultCount == 0) {
            return JsonData.fail("创建区域失败");
        }
        return JsonData.successMsg("创建区域成功");
    }

    @Override
    public JsonData selectDistrict(String distName, String distCode) {
        List<SysDistrictParam> districts = districtMapper.selectDistrict(distName, distCode);
        return districts == null || districts.size() == 0 ? JsonData.successMsg("搜索结果为空") : JsonData.success(districts, "查询成功");
    }

    @Override
    @Transactional(readOnly = false)
    public JsonData updateSysDistrict(SysDistrictParam district) {
        BeanValidator.check(district);
        Integer distId = district.getDistId();
        String distName = district.getDistName();
        if (distId.equals(ROOT_DIST_ID) && district.getDistParentName() != null) {
            throw new ParameterException("根节点父级区域不能修改");
        }
        if (checkIdAndName(distId, distName)) {
            throw new ParameterException("区域名称:" + distName + "已存在");
        }
        int resultCount = districtMapper.updateDistrict(district);
        if (resultCount == 0) {
            return JsonData.fail("更新区域失败");
        }
        return JsonData.successMsg("更新区域成功");
    }

    @Override
    @Transactional(readOnly = false)
    public JsonData deleteSysDistrict(List<Integer> ids , Integer currentEmpId) {
        if (ids == null || ids.size()==0) {
            return JsonData.successMsg("请选中一个要删除的区域");
        }
        List<SysDistrict> preDelete = new ArrayList<>();
        for (Integer id : ids) {
            if (id.equals(ROOT_DIST_ID)) {
                throw new ParameterException("根节点区域不能删除");
            }
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
        List<SysDistrictParam> districtList = districtMapper.getAllDist();
        return districtList == null || districtList.size() == 0 ? JsonData.successMsg("区域树为空，需要新建") : JsonData.successData(districtList);
    }

    private List<SysDistrict> getChildrenDist(Integer distId) {
        return districtMapper.getChildrenDist(distId);
    }

    private  SysDistrict getDistrictById(Integer distId) {
        return districtMapper.selectByPrimaryKey(distId);
    }
}