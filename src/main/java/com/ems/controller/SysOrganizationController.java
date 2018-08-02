package com.ems.controller;

import com.ems.entity.SysOrganization;
import com.ems.service.SysOrganizationService;
import com.ems.utils.RandomUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/org")
public class SysOrganizationController {

	@Resource
	private SysOrganizationService sysOrganizationService;
	private static Logger logger = Logger.getLogger(SysOrganizationController.class);
	//显示机构所有数据
	@RequiresRoles("sys:org:visit")
	@RequestMapping(value = "/listData.do")
	@ResponseBody
	public Object selectFindListOnPc(HttpServletRequest request,HttpServletResponse response){

		return sysOrganizationService.findListOrganizationOnPc();

	}
	//机构新增
	@RequiresRoles("sys:org:create")
	@RequestMapping(value = "/add.do")
	@ResponseBody
	public  Map<String,Object> addFindOnPc(SysOrganization sysz,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> resultMap = Maps.newHashMap();
		String msg = "新增机构成功！！";
		boolean bFlag = true;
		int type=0;//用户选择时类别
		int sys_type=0;//数据库查询类别;

		//判断是新增还是修改(若获取不到id，则为新增数据)
		if(StringUtils.isNotBlank(sysz.getId())){
			long base_orgId=10000000000L;
			//查看表中是否存在数据，若不存在则新增顶级节点
			int count = sysOrganizationService.findListByCountOnPc();
			logger.info(count);

			if(count<=0){

				sysz.setId(new RandomUtils().getUUID());
				sysz.setOrgId(base_orgId);
				sysz.setOrgParentId(null);
				sysz.setCreateTime(new Date());
				//创建人id(暂无使用模拟数据代替)
				sysz.setCreateBy(12324L);

				if(StringUtils.isBlank(sysz.getOrgCategory())){

					msg="获取机构类别失败，请选择机构类别或联系管理员";
					bFlag=false;
					resultMap.put("success", bFlag);
					resultMap.put("msg", msg);
					return resultMap;
				}

				if(!"公司".equals(sysz.getOrgCategory())){

					msg="操作有误，新增根节点时机构类别需选择公司";
					bFlag=false;
					resultMap.put("success", bFlag);
					resultMap.put("msg", msg);
					return resultMap;
				}

				sysOrganizationService.insertOrganizationOnPc(sysz);

			}else if(count>0){
				//根据上级机构名称，获取对应信息
				SysOrganization sds= sysOrganizationService.findByOrgNameOnPc(sysz.getOrgName());
				//机构类别按大至小，分为以下几种：公司，总经办，部门，小组
				if(StringUtils.isBlank(sds.getOrgCategory())){

					msg="新增时获取上级机构类别失败，请刷新重试或联系管理员";
					bFlag=false;
					resultMap.put("success", bFlag);
					resultMap.put("msg", msg);
					return resultMap;
				}
				if(sds.getOrgCategory().equals(sysz.getOrgCategory())){

					msg="操作失败，上级机构类别与新增时机构类别一致";
					bFlag=false;
					resultMap.put("success", bFlag);
					resultMap.put("msg", msg);
					return resultMap;
				}
				//数据转换开始
				if(sds.getOrgCategory().equals("公司")){

					sys_type=1;

				}else if(sds.getOrgCategory().equals("总经办")){

					sys_type=2;
				}else if(sds.getOrgCategory().equals("部门")){

					sys_type=3;
				}else{

					sys_type=4;
				}
				if(sysz.getOrgCategory().equals("公司")){

					type=1;
				}else if(sysz.getOrgCategory().equals("总经办")){

					type=2;
				}else if(sysz.getOrgCategory().equals("部门")){

					type=3;
				}else{

					type=4;
				}
				//数据转换结束
				if(sys_type==4){

					msg="父级机构类别已是最下级小组，请重新选择";
					bFlag=false;
					resultMap.put("success", bFlag);
					resultMap.put("msg", msg);
					return resultMap;
				}
				//新增时判断用户选择的机构类型是否大于等于父级机构类型：例如：1,。公司。大于2总经办
				if(type<= sys_type){

					msg="操作有误，机构类型不能大于父级机构类型，机构类型依次为：公司，总经办，部门，小组";
					bFlag=false;
					resultMap.put("success", bFlag);
					resultMap.put("msg", msg);
					return resultMap;

				}else if(type!= (sys_type++)){

					msg="新增机构类型只能比上级机构小一级，机构类型依次为：公司，总经办，部门，小组";
					bFlag=false;
					resultMap.put("success", bFlag);
					resultMap.put("msg", msg);
					return resultMap;
				}

				long maxOrgId = sysOrganizationService.maxOrganizationOnPc();
				logger.info(maxOrgId);

				sysz.setId(new RandomUtils().getUUID());
				sysz.setOrgId(maxOrgId++);

				if(sds.getOrgParentId().intValue()==0){
					//若没有获取到父级id则说明为顶级节点设置默认值
					sysz.setOrgParentId(base_orgId);
				}else{

					sysz.setOrgParentId(sds.getOrgParentId());
				}

				sysz.setCreateTime(new Date());
				//创建人id(暂无使用模拟数据代替)
				sysz.setCreateBy(12324L);
				sysOrganizationService.insertOrganizationOnPc(sysz);

			}
		//否则修改
		}else{

			msg="修改数据成功";
			//查看是否存在一条数据
			int count = sysOrganizationService.findIdByCountOnPc(sysz.getId());
			if(count>0){

				//根据id修改机构名称
				sysOrganizationService.updateOrgNameByIdOnPc(sysz.getId());
			}else{

				msg="数据有误请联系管理员";
				bFlag=false;
				resultMap.put("success", bFlag);
				resultMap.put("msg", msg);
				return resultMap;
			}

		}
		resultMap.put("success", bFlag);
 		resultMap.put("msg", msg);

		return resultMap;
	}
	//删除机构
	@RequiresRoles("sys:org:delete")
	@RequestMapping(value = "/delete.do")
	@ResponseBody
	public Map<String,Object> deleteOrganization(SysOrganization sysz,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> resultMap = Maps.newHashMap();
		String msg="";
		boolean bFlag=true;

		if(StringUtils.isBlank(sysz.getId())){

			msg="获取数据失败请联系管理员";
			bFlag=false;
			resultMap.put("success", bFlag);
			resultMap.put("msg", msg);
			return resultMap;

		}
		//查看是否存在一条数据
		int count = sysOrganizationService.findIdByCountOnPc(sysz.getId());
		if(count<=0){

			msg="获取数据失败请联系管理员";
			bFlag=false;
			resultMap.put("success", bFlag);
			resultMap.put("msg", msg);
			return resultMap;

		}
		//根据机构id查看是否存在子集数据
		int countById = sysOrganizationService.selectOrganizationByidOnPc(sysz.getOrgId());
		if(countById>0){

			msg="该条数据存在子集请重新选择!";
			bFlag=false;
			resultMap.put("success", bFlag);
			resultMap.put("msg", msg);
			return resultMap;

		}else{

			sysOrganizationService.deleteOrganizationOnPc(sysz.getId());
			msg="删除"+sysz.getOrgName()+"成功";

		}
		resultMap.put("success", bFlag);
		resultMap.put("msg", msg);
		return resultMap;
	}
	//显示机构所有数据
	@RequiresRoles("sys:org:retrieve")
	@RequestMapping(value = "/TreeData.do")
	@ResponseBody
	public Object selectTreeListOnPc(HttpServletRequest request,HttpServletResponse response){

		List<SysOrganization> list= sysOrganizationService.findListOrganizationOnPc();

		return getChildren(list,null);

	}
	public static List<List<Object>> getChildren(List<SysOrganization> list,Long pid){

		List<List<Object>> res=new ArrayList<List<Object>>();

		List<Object> node=null;

		for(SysOrganization dist:list){


			if((pid==null&&dist.getOrgParentId()==null)||(pid!=null&&dist.getOrgParentId()!=null&&pid.longValue()==dist.getOrgParentId().longValue())){
				node=new ArrayList<Object>();

				node.add( dist);
				node.add( getChildren(list,dist.getOrgId()));

				res.add(node);

			}

		}

		return res;
	}
}
