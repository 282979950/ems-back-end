package com.tdmh.entity.mapper;

import com.tdmh.entity.GasPrice;
import com.tdmh.param.GasPriceParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author Administrator on 2018/10/11.
 */
@Mapper @Component
public interface GasPriceMapper {
    List<GasPriceParam> listAllGasPrice();

    GasPrice findGasPriceByType(@Param("userType") Integer userType, @Param("userGasType") Integer userGasType);

    int insert(GasPriceParam param);

    int update(GasPriceParam param);
}
