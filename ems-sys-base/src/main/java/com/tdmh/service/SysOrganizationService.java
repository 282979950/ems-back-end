package com.tdmh.service;

import com.tdmh.entity.SysOrganization;
import com.tdmh.entity.TreeNode;
import com.tdmh.entity.mapper.SysOrganizationMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    //根据名称查看是否存在同名数据
   public Integer selectCountByorgNameService(@Param("orgName") String orgName){
	    return dao.selectCountByorgName(orgName);
    }

	public List<TreeNode> loadTreeData() {
		List<TreeNode> nodes = new ArrayList<>();
		List<SysOrganization> organizations = findListOrganizationOnPc();
		if (organizations == null || organizations.size() == 0) {
			return nodes;
		}
		for (SysOrganization organization : organizations) {
			TreeNode node = new TreeNode();
			node.setId(organization.getOrgId());
			node.setValue(organization.getOrgId());
			node.setKey(organization.getOrgName());
			node.setTitle(organization.getOrgName());
			node.setPId(organization.getOrgParentId());
			nodes.add(node);
		}
		return nodes;
	}
}
