package com.ems.service.impl;

import com.ems.common.BeanValidator;
import com.ems.common.JsonData;
import com.ems.entity.SysDistrict;
import com.ems.entity.mapper.SysDistrictMapper;
import com.ems.exception.ParameterException;
import com.ems.service.ISysDistrictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
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
     * 所有区域数据
     */
    private static List<SysDistrict> districtList = null;

    @Autowired
    private SysDistrictMapper districtMapper;

    /**
     * 初始化区域树
     *
     * @return
     */
    @PostConstruct
    private void initializeSysDistTree() {
        districtList = districtMapper.selectAll();
        if (districtList == null || districtList.size() == 0) return;
        for (SysDistrict dist : districtList) {
            Integer parentId = dist.getDistParentId();
            if (parentId != null) {
                SysDistrict parentDist = getDistById(parentId);
                if (parentDist != null) {
                    parentDist.getChildrenDist().add(dist);
                }
            }
        }
    }

    @Override
    public boolean checkDistName(String distName) {
        for (SysDistrict district : districtList) {
            if (StringUtils.equals(district.getDistName(), distName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkUsable(String distName) {
        for (SysDistrict district : districtList) {
            if (StringUtils.equals(district.getDistName(), distName) && district.getUsable()) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional(readOnly = false)
    public JsonData createDistrict(SysDistrict district) {
        BeanValidator.check(district);
        String distName = district.getDistName();
        if (checkDistName(distName)) {
            throw new ParameterException("区域名称:" + distName + "已存在");
        }
        district.setUsable(true);
        district.setChildrenDist(new ArrayList<SysDistrict>());
        // TODO: 2018/7/4  设置创建者和更新者
        int resultCount = districtMapper.insert(district);
        if (resultCount == 0) {
            return JsonData.fail("创建区域失败");
        }
        SysDistrict parentDist = getDistById(district.getDistParentId());
        if (parentDist != null) {
            parentDist.getChildrenDist().add(district);
        }
        districtList.add(district);
        return JsonData.successMsg("创建区域成功");
    }

    @Override
    public JsonData selectDistByName(String name) {
        List<SysDistrict> result = new ArrayList<>(districtList.size());
        for (SysDistrict district : districtList) {
            String distName = district.getDistName();
            if (distName.contains(name)) {
                result.add(district);
            }
        }
        return JsonData.successData(result);
    }

    @Override
    public JsonData selectDistByCode(String code) {
        List<SysDistrict> result = new ArrayList<>(districtList.size());
        for (SysDistrict district : districtList) {
            String distCode = district.getDistCode();
            if (distCode != null && distCode.contains(code)) {
                result.add(district);
            }
        }
        return JsonData.successData(result);
    }

    private SysDistrict getDistById(Integer distId) {
        for (SysDistrict district : districtList) {
            if (district.getDistId().equals(distId)) {
                return district;
            }
        }
        return null;
    }

    private SysDistrict getDistByName(String distName) {
        for (SysDistrict district : districtList) {
            if (district.getDistName().equals(distName)) {
                return district;
            }
        }
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public JsonData updateSysDistrict(SysDistrict newDistrict) {
        BeanValidator.check(newDistrict);
        SysDistrict oldDistrict = getDistById(newDistrict.getDistId());
        if (oldDistrict == null) {
            throw new ParameterException("需要更新的区域不存在");
        }
        oldDistrict.setDistName(newDistrict.getDistName());
        oldDistrict.setDistCode(newDistrict.getDistCode());
        oldDistrict.setDistAddress(newDistrict.getDistAddress());
        // TODO: 2018/7/6 设置更新者
        int resultCount = districtMapper.updateByPrimaryKey(oldDistrict);
        if (resultCount == 0) {
            return JsonData.fail("更新区域失败");
        }
        Integer oldParentId = oldDistrict.getDistParentId();
        Integer newParentId = newDistrict.getDistParentId();
        if (oldParentId != null && newParentId != null && !oldParentId.equals(newParentId)) {
            oldDistrict.setDistParentId(newParentId);
            // 更新区域树
            SysDistrict oldParentDist = getDistById(oldParentId);
            oldParentDist.getChildrenDist().remove(oldDistrict);
            SysDistrict newParentDist = getDistById(newParentId);
            newParentDist.getChildrenDist().add(oldDistrict);
        }
        return JsonData.successMsg("更新区域成功");
    }

    @Override
    @Transactional(readOnly = false)
    public JsonData deleteSysDistrict(SysDistrict district) {
        BeanValidator.check(district);
        SysDistrict oldDist = getDistById(district.getDistId());
        if (oldDist == null) {
            throw new ParameterException("需要被删除的区域不存在");
        }
        if (oldDist.getChildrenDist().size() != 0) {
            throw new ParameterException("拥有下级的区域不能被删除");
        }
        oldDist.setUsable(false);
        // TODO: 2018/7/6 设置更新者
        int resultCount = districtMapper.updateByPrimaryKey(oldDist);
        if (resultCount == 0) {
            return JsonData.fail("删除区域失败");
        }
        SysDistrict parentDist = getParentDist(oldDist);
        if (parentDist != null) {
            parentDist.getChildrenDist().remove(oldDist);
        }
        districtList.remove(oldDist);
        return JsonData.successMsg("删除区域成功");
    }

    @Override
    public SysDistrict getParentDist(SysDistrict district) {
        return getDistById(district.getDistParentId());
    }

    @Override
    public JsonData getDistRoot() {
        for (SysDistrict district : districtList) {
            if (district.getDistParentId() == null) {
                return JsonData.successData(district);
            }
        }
        return JsonData.successMsg("区域树为空，需要新建");
    }

    @Override
    public JsonData getDistrictList() {
        return districtList == null ? JsonData.successMsg("区域树为空，需要新建") : JsonData.successData(districtList);
    }
}
