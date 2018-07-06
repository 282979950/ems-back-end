package com.ems.service.impl;

import com.ems.entity.SysDistrict;
import com.ems.entity.mapper.SysDistrictMapper;
import com.ems.service.ISysDistrictService;
import com.ems.utils.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by litairan litairan@whtdmh.com on 2018/7/2.
 */
@Service("iSysDistrict")
@Transactional(readOnly = true)
public class SysDistrictServiceServiceImpl implements ISysDistrictService {

    private static final Logger logger = LoggerFactory.getLogger(SysDistrictServiceServiceImpl.class);

    /**
     * 区域管理根节点ID
     */
    private static final long DISTRICT_ROOT_ID = 1000000000L;

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
            Long parentId = dist.getDistParentId();
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
        String distName = district.getDistName();
        if (distName == null || checkDistName(distName)) {
            return 0;
        }
        district.setId(RandomUtils.getUUID());
        district.setDistId(createDistrictID(district));
        district.setUseable(true);
        Date now = new Date();
        district.setCreateTime(now);
        district.setUpdateTime(now);
        district.setChildrenDist(new ArrayList<SysDistrict>());
        // TODO: 2018/7/4  设置创建者和更新者
        SysDistrict parentDist = getDistById(district.getDistParentId());
        if (parentDist != null) {
            parentDist.getChildrenDist().add(district);
        }
        districtList.add(district);
        return districtMapper.insert(district);
    }

    /**
     * 生成新区域的ID
     *
     * @param district
     * @return
     */
    private Long createDistrictID(SysDistrict district) {
        return DISTRICT_ROOT_ID + getCountWithUnusable();
    }

    /**
     * 获取所有区域（包含useable为false的区域）
     *
     * @return
     */
    private int getCountWithUnusable() {
        return districtMapper.getCountWithUnusable();
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

    private SysDistrict getDistById(Long distId) {
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
        SysDistrict oldDistrict = getDistById(newDistrict.getDistId());
        if (oldDistrict != null) {
            oldDistrict.setDistName(newDistrict.getDistName());
            oldDistrict.setDistCode(newDistrict.getDistCode());
            oldDistrict.setDistAddress(newDistrict.getDistAddress());
            oldDistrict.setUpdateTime(new Date());
            // TODO: 2018/7/6 设置更新者
            Long oldParentId = oldDistrict.getDistParentId();
            Long newParentId = newDistrict.getDistParentId();
            if (oldParentId != null && newParentId != null && !oldParentId.equals(newParentId)) {
                oldDistrict.setDistParentId(newParentId);
                // 更新区域树
                SysDistrict oldParentDist = getDistById(oldParentId);
                oldParentDist.getChildrenDist().remove(oldDistrict);
                SysDistrict newParentDist = getDistById(newParentId);
                newParentDist.getChildrenDist().add(oldDistrict);
            }
        }
        return districtMapper.updateByPrimaryKey(oldDistrict);
    }

    @Override
    @Transactional(readOnly = false)
    public int deleteSysDistrict(SysDistrict district) {
        SysDistrict oldDist = getDistById(district.getDistId());
        // 如果区域不存在或者含有子区域则不能删除
        if (oldDist == null || oldDist.getChildrenDist().size() != 0) {
            return 0;
        }
        oldDist.setUseable(false);
        oldDist.setUpdateTime(new Date());
        // TODO: 2018/7/6 设置更新者
        //更新区域树
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
