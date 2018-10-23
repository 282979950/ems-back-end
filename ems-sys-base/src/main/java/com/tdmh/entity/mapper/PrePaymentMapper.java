package com.tdmh.entity.mapper;

import com.tdmh.param.PrePaymentParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Lucia on 2018/10/12.
 */

@Mapper @Component
public interface PrePaymentMapper {
     List<PrePaymentParam> getAllOrderInformation(@Param("param") PrePaymentParam param);
     List<PrePaymentParam> getOrderInformationCardOrderGas(@Param("param") PrePaymentParam param);
}
