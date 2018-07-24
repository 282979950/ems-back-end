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

    int updateByPrimaryKeySelective(SysOrganization record);

    int updateByPrimaryKey(SysOrganization record);
    //查看所有数据
    List<SysOrganization>findList();
    //查看表中是否存在一条数据
    int findListByCount();
    //根据机构名称获取对应机构等级机构类别
    SysOrganization findByOrgName(String org_name);
    //获取表中最大机构id
    long maxOrganization();
    
    int findIdByCount(String id);
    //修改一条数据
    int updateOrgNameById(String id);
    //根据id查看是否存在子集
    int selectOrganizationByid(Long orgid);
    //删除一条记录
    int deleteOrganization(String id);
}