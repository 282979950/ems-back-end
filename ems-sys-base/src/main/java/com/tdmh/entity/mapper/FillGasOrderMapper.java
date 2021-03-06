package com.tdmh.entity.mapper;

import com.tdmh.param.FillGasOrderParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    List<FillGasOrderParam> listData(@Param("createBy") Integer createBy);

    BigDecimal getSumOrderGasByUserId(Integer id);

    BigDecimal getSumMeterStopCodeByUserId(Integer id);

    List<FillGasOrderParam> getFillGasOrderByUserId(Integer userId);

    int cancelFillGasByUserId(Integer userId);

    boolean hasUnfinishedFillGasOrder(Integer userId);

    boolean hasFillGasOrderResolved(@Param("userId") Integer userId, @Param("repairOrderId") String repairOrderId);
    List<FillGasOrderParam>selectFillGasOrderQuery(Integer userId);

    List<FillGasOrderParam> searchFillGasOrder(@Param("repairOrderId") String repairOrderId, @Param("userId") Integer userId,
                                               @Param("fillGasOrderType") Integer fillGasOrderType, @Param("createBy") Integer createBy, @Param("userName") String userName);

    BigDecimal getHistoryMeterStopCodeByUserId(@Param("userId") Integer userId);

    /**
     *根据UserId查看指定状态下的最近一笔记录
     * @param userId
     * @return
     */
    FillGasOrderParam getCreateTimeByUserId(@Param("userId") Integer userId,@Param("fillGasOrderStatus") Integer fillGasOrderStatus);

    Integer getFillGasOrderCountByUserId(@Param("userId") Integer userId,@Param("fillGasOrderStatus") Integer fillGasOrderStatus);
}
