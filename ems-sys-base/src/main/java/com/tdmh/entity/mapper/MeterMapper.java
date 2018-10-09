package com.tdmh.entity.mapper;

import com.tdmh.entity.Meter;
import com.tdmh.param.EntryMeterParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface MeterMapper {

    int insert(Meter record);

    int insertSelective(Meter record);

    Meter selectByPrimaryKey(Integer meterId);

    int updateByPrimaryKeySelective(Meter record);

    int updateByPrimaryKey(Meter record);

    List<EntryMeterParam> getAllEntryMeters();

    int addEntryMeter(EntryMeterParam param);

    int editEntryMeter(EntryMeterParam param);

    int deleteEntryMeter(List<Meter> meters);

    List<EntryMeterParam> searchEntryMeter(@Param("meterCode") String meterCode, @Param("meterCategory") String meterCategory, @Param("meterType") String
            meterType, @Param("meterDirection") Boolean meterDirection, @Param("meterProdDate") Date meterProdDate);

    boolean checkMeterExist(@Param("meterCode") String meterCode);

    List<Meter> selectAll();

    Integer getMeterIdByMeterCode(@Param("meterCode") String meterCode);

    Meter getMeterByMeterId(@Param("meterId") Integer meterId);

}