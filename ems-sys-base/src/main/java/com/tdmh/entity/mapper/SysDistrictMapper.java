package com.tdmh.entity.mapper;

import com.tdmh.entity.SysDistrict;
import com.tdmh.param.SysDistrictParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper @Component
public interface SysDistrictMapper {
    int deleteByPrimaryKey(Integer distId);

    int insert(SysDistrict record);

    int insertSelective(SysDistrict record);

    SysDistrict selectByPrimaryKey(Integer distId);

    int updateByPrimaryKeySelective(SysDistrict record);

    int updateByPrimaryKey(SysDistrict record);

    boolean checkDistName(String distName);

    boolean checkUsable(String distName);

    boolean checkIdAndName(@Param("distId") Integer distId, @Param("distName") String distName);

    List<SysDistrictParam> selectAll();

    int getCountWithUnusable();

    List<SysDistrict> getChildrenDist(@Param("distId") Integer distId);

    int deleteBatch(List<SysDistrict> districts);

    List<SysDistrictParam> selectDistrict(@Param("distName") String distName, @Param("distCode") String distCode);

    List<SysDistrictParam> getAllDist();

    int createDistrict(SysDistrictParam district);

    int updateDistrict(SysDistrictParam district);

    String getDistrictChildList(@Param("userDistId") Integer userDistId);
}