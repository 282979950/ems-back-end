package com.tdmh.entity.mapper;

import com.tdmh.entity.EvalItem;
import com.tdmh.param.EvalItemParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Liuxia on 2018/11/14.
 */
@Mapper
@Component
public interface EvalItemMapper {
    List<EvalItemParam> getAllEvalItem();

    int insert(EvalItem evalItem);

    EvalItem getEvalItemById(Integer eId);

    int deleteBatch(List<EvalItem> evalItemList);

    int update(EvalItem evalItem);

    List<EvalItemParam> selectEvalItem(@Param("evalItemContent") String evalItemContent);
}
