package com.ems.service;
/*
 * 字典表Service
 */

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ems.entity.SysDictionary;
import com.ems.entity.mapper.SysDictionaryMapper;

@Service
@Transactional(readOnly = true)
public class SysDictionaryService {
	@Resource
	private SysDictionaryMapper sysDictionaryMapper;
	//获取字典所有数据
	public List<SysDictionary> findListOnPc(){
		return sysDictionaryMapper.findList();
	}
	//新增数据
	@Transactional(readOnly = false)
	public Integer insertDictionaryOnPc(SysDictionary record){
		return sysDictionaryMapper.insert(record);
	}
	//修改数据
	@Transactional(readOnly = false)
	public Integer updateByPrimaryKeySelective(SysDictionary record){

		return sysDictionaryMapper.updateByPrimaryKeySelective(record);
	}
	//根据条件查看是否存在该条数据
	public Integer selectCountByIdOnPc(SysDictionary record){
		return sysDictionaryMapper.selectCountById(record);
	}
	//数据删除
	@Transactional(readOnly = false)
	public Integer deleteSysDictionaryById(SysDictionary record){
		return sysDictionaryMapper.deleteSysDictionary(record);
	}
	//根据字典类型查看对应所有字典数值
	public List<SysDictionary> findListByTypeOnPc(String dictCategory){

		return sysDictionaryMapper.findListByType(dictCategory);
	}
	//依据条件查看对应数据
	public List<SysDictionary> findListByService(SysDictionary sdy){

		return sysDictionaryMapper.findListByDict(sdy);
	}
}
