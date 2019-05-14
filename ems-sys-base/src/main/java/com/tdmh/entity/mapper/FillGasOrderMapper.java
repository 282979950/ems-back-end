package com.tdmh.entity.mapper;

import com.tdmh.param.FillGasOrderParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
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

    List<FillGasOrderParam> getFillGasOrderByUserId(Integer userId);

    int cancelFillGasByUserId(Integer userId);

    boolean hasUnfinishedFillGasOrder(Integer userId);

    boolean hasFillGasOrderResolved(@Param("userId") Integer userId, @Param("repairOrderId") String repairOrderId);
    List<FillGasOrderParam>selectFillGasOrderQuery(Integer userId);

    List<FillGasOrderParam> searchFillGasOrder(@Param("repairOrderId") String repairOrderId, @Param("userId") Integer userId,
                                               @Param("fillGasOrderType") Integer fillGasOrderType);

    BigDecimal getHistoryMeterStopCodeByUserId(@Param("userId") Integer userId);

    /**
     *根据UserId查看指定状态下的创建时间
     * @param userId
     * @return
     */
    Date getCreateTimeByUserId(@Param("userId") Integer userId);
}
