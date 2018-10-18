package com.tdmh.entity.mapper;

import com.tdmh.param.FillGasOrderParam;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author litairan on 2018/10/17.
 */
@Mapper
@Component
public interface FillGasOrderMapper {

    int createFillGasOrder(FillGasOrderParam param);

    int editFillGasOrder(FillGasOrderParam param);

    List<FillGasOrderParam> listData();

    BigDecimal getSumOrderGasByUserId(Integer id);

    BigDecimal getSumMeterStopCodeByUserId(Integer id);
}
