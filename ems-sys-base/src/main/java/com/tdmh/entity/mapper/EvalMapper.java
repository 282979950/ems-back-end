package com.tdmh.entity.mapper;

import com.tdmh.param.EvalParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Liuxia on 2018/11/15.
 */
@Mapper
@Component
public interface EvalMapper {
    List<EvalParam> getAllEvals(@Param("userName") String userName, @Param("applyRepairFlowNumber") String applyRepairFlowNumber);
}
