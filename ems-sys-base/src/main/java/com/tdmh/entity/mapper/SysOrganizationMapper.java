package com.tdmh.entity.mapper;

import com.tdmh.entity.SysOrganization;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper @Component
public interface SysOrganizationMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysOrganization record);

    int insertSelective(SysOrganization record);

    SysOrganization selectByPrimaryKey(String id);
    //修改一条数据
    int updateByPrimaryKeySelective(SysOrganization record);

    int updateByPrimaryKey(SysOrganization record);
    //查看所有数据
    List<SysOrganization>findList();
    //查看表中是否存在一条数据
    int findListByCount();
    //根据机构名称获取对应机构等级机构类别
    SysOrganization findByOrgName(Integer OrgParentId);
    //获取表中最大机构id
    int maxOrganization();

    int findIdByCount(int orgId);

    //根据id查看是否存在子集
    int selectOrganizationByid(SysOrganization record);
    //删除一条记录
    int deleteOrganization(SysOrganization record);
    //查看所有数据
    List<SysOrganization>findListOrganization(SysOrganization record);
    //根据名称查看是否存在同名数据
    Integer selectCountByorgName(String orgName);
}