package com.tdmh.entity.mapper;


import com.tdmh.entity.StrikeNucleus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper @Component
public interface StrikeNucleusMapper {

    int insertStrikeNucleus(StrikeNucleus strikeNucleus);

    List<StrikeNucleus> selectStrikeNucleusList(StrikeNucleus strikeNucleus);
    int updateStrikeNucleusById(StrikeNucleus strikeNucleus);
    List<StrikeNucleus> selectStrikeNucleusByUserId(@Param("userId") Integer userId);
}