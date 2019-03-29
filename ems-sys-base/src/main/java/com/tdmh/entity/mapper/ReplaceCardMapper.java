package com.tdmh.entity.mapper;

import com.tdmh.param.PrePaymentParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Administrator on 2018/10/18.
 */

@Mapper @Component
public interface ReplaceCardMapper {
    List<PrePaymentParam> getAllReplaceCard(@Param("param") PrePaymentParam param);
}
