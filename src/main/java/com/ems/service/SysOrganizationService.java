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
	//根据机构名称获取对应机构等级机构类别
	public SysOrganization findByOrgNameOnPc (String org_name){
		
		return dao.findByOrgName(org_name);
	}
	//新增一条数据
	@Transactional(readOnly = false)
	public Integer insertOrganizationOnPc(SysOrganization sysz){
		return dao.insert(sysz);
	}
	//查询表中最大机构id
	public Long maxOrganizationOnPc(){
		
		return dao.maxOrganization();
	}
	//根据id查看是否存在一条记录
	public Integer findIdByCountOnPc(String id){
		
		return dao.findIdByCount(id);
	}
	@Transactional(readOnly = false)
	public Integer updateOrgNameByIdOnPc(String id){
		return dao.updateOrgNameById(id);
	}
	//删除机构数据
	public int deleteByPrimaryKeyOnPc(String id){
		return dao.deleteByPrimaryKey(id);
	}
	//删除时查看是否存在子集
	public Integer selectOrganizationByidOnPc(Long orgid){
		
		return dao.selectOrganizationByid(orgid);
	}
	//删除一条数据
	@Transactional(readOnly = false)
	public Integer deleteOrganizationOnPc(String id){
		
		return dao.deleteOrganization(id);
	}
}
