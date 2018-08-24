package com.ems.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ems.entity.SysOrganization;
import com.ems.entity.mapper.SysOrganizationMapper;

@Service
@Transactional(readOnly = true)
public class SysOrganizationService {
	@Resource
	private SysOrganizationMapper dao;

	//pc端查看机构所有数据
	public List<SysOrganization> findListOrganizationOnPc(){
		return dao.findList();
	}
	//查看表中是否存在一条数据
	public Integer findListByCountOnPc(){

		return dao.findListByCount();
	}
	//根据机构id获取对应信息
	public SysOrganization findByOrgNameOnPc (Integer OrgParentId){

		return dao.findByOrgName(OrgParentId);
	}
	//新增一条数据
	@Transactional(readOnly = false)
	public Integer insertOrganizationOnPc(SysOrganization sysz){
		return dao.insert(sysz);
	}
	//查询表中最大机构id
	public Integer maxOrganizationOnPc(){

		return dao.maxOrganization();
	}
	//根据id查看是否存在一条记录
	public Integer findIdByCountOnPc(Integer orgId){

		return dao.findIdByCount(orgId);
	}
	@Transactional(readOnly = false)
	public Integer updateOrgNameByIdOnPc(SysOrganization record){
		return dao.updateByPrimaryKeySelective(record);
	}
	//删除机构数据
	public int deleteByPrimaryKeyOnPc(String id){
		return dao.deleteByPrimaryKey(id);
	}
	//删除时查看是否存在子集
	public Integer selectOrganizationByidOnPc(SysOrganization sysz){

		return dao.selectOrganizationByid(sysz);
	}
	//删除一条数据
	@Transactional(readOnly = false)
	public Integer deleteOrganizationOnPc(SysOrganization sysz){

		return dao.deleteOrganization(sysz);
	}
	//pc端查看机构所有数据(根据条件筛选)
	public List<SysOrganization> findListOrganizationService(SysOrganization sysz){
		return dao.findListOrganization(sysz);
	}
}
