package com.tdmh.entity.mapper;

import com.tdmh.entity.ServiceOutlet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Liuxia on 2018/11/12.
 */

@Mapper @Component
public interface ServiceOutletMapper {
    List<ServiceOutlet> getAllSOLet();

    int insert(ServiceOutlet serviceOutlet);

    int update(ServiceOutlet serviceOutlet);

    List<ServiceOutlet> selectSOLet(@Param("serviceOutletName") String serviceOutletName,@Param("serviceOutletAddress") String serviceOutletAddress);

    int deleteBatch(List<ServiceOutlet> list);

    ServiceOutlet getSOLetById(@Param("serviceOutletId") Integer sId);
}
