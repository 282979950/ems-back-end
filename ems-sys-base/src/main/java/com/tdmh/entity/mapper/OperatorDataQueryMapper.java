package com.tdmh.entity.mapper;


import com.tdmh.param.OperatorDataQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface OperatorDataQueryMapper {
    List<OperatorDataQuery> getOperatorDataQuery(OperatorDataQuery dataQuery);
}