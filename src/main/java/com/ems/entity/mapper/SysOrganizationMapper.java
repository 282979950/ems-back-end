package com.ems.entity.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ems.entity.SysOrganization;
@Repository
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
    int selectOrganizationByid(SysOrganization record );
    //删除一条记录
    int deleteOrganization(SysOrganization record);
    //查看所有数据
    List<SysOrganization>findListOrganization(SysOrganization record);
}