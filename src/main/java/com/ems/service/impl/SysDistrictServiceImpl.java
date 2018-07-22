package com.ems.service.impl;

import com.ems.common.BeanValidator;
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
    public boolean checkUseable(String distName) {
        for (SysDistrict district : districtList) {
            if (StringUtils.equals(district.getDistName(), distName) && district.getUseable()) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional(readOnly = false)
    public int createDistrict(SysDistrict district) {
        BeanValidator.check(district);
        String distName = district.getDistName();
        if (checkDistName(distName)) {
            throw new ParameterException("区域名称:" + distName + "已存在");
        }
        district.setUseable(true);
        district.setChildrenDist(new ArrayList<SysDistrict>());
        // TODO: 2018/7/4  设置创建者和更新者
        SysDistrict parentDist = getDistById(district.getDistParentId());
        if (parentDist != null) {
            parentDist.getChildrenDist().add(district);
        }
        districtList.add(district);
        return districtMapper.insert(district);
    }

    @Override
    public List<SysDistrict> selectDistByName(String name) {
        List<SysDistrict> result = new ArrayList<>(districtList.size());
        for (SysDistrict district : districtList) {
            String distName = district.getDistName();
            if (distName.contains(name)) {
                result.add(district);
            }
        }
        return result;
    }

    @Override
    public List<SysDistrict> selectDistByCode(String code) {
        List<SysDistrict> result = new ArrayList<>(districtList.size());
        for (SysDistrict district : districtList) {
            String distCode = district.getDistCode();
            if (distCode != null && distCode.contains(code)) {
                result.add(district);
            }
        }
        return result;
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
    public int updateSysDistrict(SysDistrict newDistrict) {
        BeanValidator.check(newDistrict);
        SysDistrict oldDistrict = getDistById(newDistrict.getDistId());
        if (oldDistrict == null) {
            throw new ParameterException("需要更新的区域不存在");
        }
        oldDistrict.setDistName(newDistrict.getDistName());
        oldDistrict.setDistCode(newDistrict.getDistCode());
        oldDistrict.setDistAddress(newDistrict.getDistAddress());
        // TODO: 2018/7/6 设置更新者
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
        return districtMapper.updateByPrimaryKey(oldDistrict);
    }

    @Override
    @Transactional(readOnly = false)
    public int deleteSysDistrict(SysDistrict district) {
        BeanValidator.check(district);
        SysDistrict oldDist = getDistById(district.getDistId());
        if (oldDist == null) {
            throw new ParameterException("需要被删除的区域不存在");
        }
        if (oldDist.getChildrenDist().size() != 0) {
            throw new ParameterException("拥有下级的区域不能被删除");
        }
        oldDist.setUseable(false);
        // TODO: 2018/7/6 设置更新者
        SysDistrict parentDist = getParentDist(oldDist);
        if (parentDist != null) {
            parentDist.getChildrenDist().remove(oldDist);
        }
        districtList.remove(oldDist);
        return districtMapper.updateByPrimaryKey(oldDist);
    }

    @Override
    public SysDistrict getParentDist(SysDistrict district) {
        return getDistById(district.getDistParentId());
    }

    @Override
    public SysDistrict getDistRoot() {
        for (SysDistrict district : districtList) {
            if (district.getDistParentId() == null) {
                return district;
            }
        }
        return null;
    }

    @Override
    public List<SysDistrict> getDistrictList() {
        return districtList;
    }
}
