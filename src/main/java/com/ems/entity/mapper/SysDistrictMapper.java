package com.ems.entity.mapper;

import com.ems.entity.SysDistrict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDistrictMapper {
    int deleteByPrimaryKey(Integer distId);

    int insert(SysDistrict record);

    int insertSelective(SysDistrict record);

    SysDistrict selectByPrimaryKey(Integer distId);

    int updateByPrimaryKeySelective(SysDistrict record);

    int updateByPrimaryKey(SysDistrict record);

    boolean checkDistName(String distName);

    boolean checkUsable(String distName);

    List<SysDistrict> selectAll();

    int getCountWithUnusable();

    List<SysDistrict> getChildrenDist(@Param("distId") Integer distId);

    int deleteBatch(List<SysDistrict> districts);

    List<SysDistrict> selectDistrict(@Param("distName")String distName, @Param("distCode")String distCode);
}