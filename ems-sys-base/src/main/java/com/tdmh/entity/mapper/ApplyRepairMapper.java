package com.tdmh.entity.mapper;

import com.tdmh.param.ApplyRepairParam;
import com.tdmh.param.ApplyRepairUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author litairan on 2018/11/12.
 */
@Mapper
@Component
public interface ApplyRepairMapper {

    List<ApplyRepairParam> selectAll();

    int create(ApplyRepairParam param);

    int update(ApplyRepairParam param);

    int delete(List<ApplyRepairParam> ids);

    List<ApplyRepairParam> search(@Param("userId") Integer userId, @Param("userName") String userName, @Param("userPhone") String userPhone,
                                  @Param("userTelPhone") String userTelPhone);

    ApplyRepairUserInfo getApplyRepairUserInfoById(@Param("userId") Integer userId);

    ApplyRepairParam getApplyRepairParamById(@Param("applyRepairId") Integer applyRepairId);
}