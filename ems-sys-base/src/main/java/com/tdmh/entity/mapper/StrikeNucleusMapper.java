package com.tdmh.entity.mapper;


import com.tdmh.entity.StrikeNucleus;
import com.tdmh.entity.UserChange;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Mapper @Component
public interface StrikeNucleusMapper {

    int insertStrikeNucleus(StrikeNucleus strikeNucleus);

    List<StrikeNucleus> selectStrikeNucleusList(StrikeNucleus strikeNucleus);
    int updateStrikeNucleusById(StrikeNucleus strikeNucleus);
}