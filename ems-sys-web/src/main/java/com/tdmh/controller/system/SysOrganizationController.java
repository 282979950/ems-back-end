package com.tdmh.controller.system;

import com.tdmh.common.BeanValidator;
import com.tdmh.common.JsonData;
import com.tdmh.entity.SysOrganization;
import com.tdmh.service.SysDictionaryService;
import com.tdmh.service.SysOrganizationService;
import com.tdmh.utils.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping(value = "/org")
public class SysOrganizationController {

	@Resource
	private SysOrganizationService sysOrganizationService;
	@Resource
    private SysDictionaryService sysDictionaryService;
	//显示机构所有数据
	@RequiresPermissions("sys:org:visit")
	@RequestMapping(value = "/listData.do")
	@ResponseBody
	public JsonData selectFindListOnPc(){

		List<SysOrganization> list =sysOrganizationService.findListOrganizationOnPc();
		if (list == null || list.size()==0) {
			return JsonData.successMsg("查询结果为空");
		}
		return JsonData.successData(list);

	}
	//机构新增
	@RequiresPermissions("sys:org:create")
	@RequestMapping(value = "/add.do")
	@ResponseBody
	public  JsonData addFindOnPc(SysOrganization sysz, HttpServletRequest request, HttpServletResponse response){
		BeanValidator.check(sysz);
		String msg = "新增【"+sysz.getOrgName()+"】机构成功";
		int type=0;//用户选择时类别
		int sys_type=0;//数据库查询类别;

		//查看表中是否存在数据，若不存在则新增顶级节点
		int count = sysOrganizationService.findListByCountOnPc();

		if(count<=0){
			sysz.setOrgParentId(null);
			sysz.setCreateTime(new Date());
			//创建人id(暂无使用模拟数据代替)
			sysz.setCreateBy(12324);
			sysz.setUsable(true);

			if(StringUtils.isBlank(sysz.getOrgCategory())){

				return JsonData.fail("获取机构类别失败，请选择机构类别或联系管理员");
			}

			if(!"1".equals(sysz.getOrgCategory())){

				return JsonData.fail("操作有误，新增根节点时机构类别需选择公司");
			}

			sysOrganizationService.insertOrganizationOnPc(sysz);

		}else if(count>0){
			//根据上级机构名称，获取对应信息
			SysOrganization sds= sysOrganizationService.findByOrgNameOnPc(sysz.getOrgParentId());

			if(sds==null){
				msg="新增时获取上级机构信息失败，请刷新重试或联系管理员";
				return JsonData.fail(msg);
			}
			//机构类别按大至小，分为以下几种：公司，总经办，部门，小组
			if(StringUtils.isBlank(sds.getOrgCategory())){


				return JsonData.fail("新增时获取上级机构类别失败，请刷新重试或联系管理员");
			}
			if(sds.getOrgCategory().equals(sysz.getOrgCategory())){

				return JsonData.fail("操作失败，上级机构类别与新增时机构类别一致");
			}
			if(StringUtils.isBlank(sysz.getOrgCategory())){
                return JsonData.fail("请填写机构类别");
            }
			sys_type=Integer.parseInt(sds.getOrgCategory());
            type = Integer.parseInt(sysz.getOrgCategory());

			if(sys_type==4){

				return JsonData.fail("父级机构类别已是最下级小组，请重新选择");
			}
			//新增时判断用户选择的机构类型是否大于等于父级机构类型：例如：1,。公司。大于2总经办
			if(type<= sys_type){

				return JsonData.fail("操作有误，机构类型不能大于父级机构类型，机构类型依次为：公司，总经办，部门，小组");

			}else if(type!= (++sys_type)){

				return JsonData.fail("新增机构类型只能比上级机构小一级，机构类型依次为：公司，总经办，部门，小组");
			}

			int maxOrgId = sysOrganizationService.maxOrganizationOnPc();

            sysz.setOrgCategory(type+"");
			sysz.setOrgId(++maxOrgId);
			sysz.setOrgParentId(sds.getOrgId());

			sysz.setCreateTime(new Date());
			//创建人id(暂无使用模拟数据代替)
			sysz.setCreateBy(12324);
			sysz.setCreateTime(new Date());
			sysz.setUpdateBy(12324);
			sysz.setUpdateTime(new Date());
			sysz.setUsable(true);
			if(StringUtils.isBlank(sysz.getOrgCode())){
                return JsonData.fail("操作失败，请填写机构编码");
            }

			if(StringUtils.isBlank(sysz.getOrgName())){

                return JsonData.fail("新增失败，请填写机构名称");
            }
            int countOrgName =sysOrganizationService.selectCountByorgNameService(sysz.getOrgName());
			if(countOrgName>0){
                return JsonData.fail("该机构名称已存在，请重新填写机构名称");
            }

			sysOrganizationService.insertOrganizationOnPc(sysz);

		}
		return JsonData.successMsg(msg);
	}
	//机构修改
	@RequiresPermissions("sys:org:update")
	@RequestMapping(value = "/edit.do")
	@ResponseBody
	public  JsonData editFindOnPc(SysOrganization sysz, HttpServletRequest request, HttpServletResponse response){

		String msg = "修改机构成功！！";
		int type=0;//用户选择时类别
		int sys_type=0;//数据库查询类别

			//查看是否存在一条数据
			int count = sysOrganizationService.findIdByCountOnPc(sysz.getOrgId());
			if(count>0){

				if(StringUtils.isBlank(sysz.getOrgCategory())){

					return JsonData.fail("获取机构类别失败，请选择机构类别或联系管理员");
				}
				//根据上级机构名称，获取对应信息
				SysOrganization sds= sysOrganizationService.findByOrgNameOnPc(sysz.getOrgParentId());

				if(sds==null){
					msg="获取上级机构信息失败";
					return JsonData.fail(msg);
				}
				//机构类别按大至小，分为以下几种：公司，总经办，部门，小组
				if(StringUtils.isBlank(sds.getOrgCategory())){

					return JsonData.fail("获取上级机构类别失败");
				}
                String dictValue= sysDictionaryService.dictCategoryByTypeService(sysz.getOrgCategory(),"org_type");
				if(sds.getOrgCategory().equals(dictValue)){

					return JsonData.fail("操作失败，上级机构类别与修改时机构类别一致");
				}
                sys_type = Integer.parseInt(sds.getOrgCategory());
                type =Integer.parseInt(dictValue);

				//判断用户选择的机构类型是否大于等于父级机构类型：例如：1,。公司。大于2总经办
				if(type<= sys_type){

					return JsonData.fail("操作有误，机构类型不能大于父级机构类型，机构类型依次为：公司，总经办，部门，小组");

				}else if(type!= (++sys_type)){

					return JsonData.fail("机构类型只能比上级机构小一级，机构类型依次为：公司，总经办，部门，小组");
				}
				//根据id修改机构信息
				sysz.setUpdateBy(12324);
				sysz.setOrgCategory(dictValue);
				sysz.setUpdateTime(new Date());
				sysOrganizationService.updateOrgNameByIdOnPc(sysz);
			}else{

				msg="数据有误请联系管理员";
				return JsonData.fail(msg);
			}
			return JsonData.successMsg(msg);
		}

	//删除机构
	@RequiresPermissions("sys:org:delete")
	@RequestMapping(value = "/delete.do")
	@ResponseBody
	public JsonData deleteOrganization(@RequestParam(value = "ids[]")List <Integer> ids){
        SysOrganization sysz = new SysOrganization();

		String msg="";
		if(ids.size()==0){

			msg="获取数据失败请联系管理员";

			return JsonData.fail(msg);

		}
        sysz.setIds(ids);

        //根据机构id查看是否存在子集数据
		int countById = sysOrganizationService.selectOrganizationByidOnPc(sysz);
		if(countById>0){

			msg="该数据存在子集请重新选择!";
			return JsonData.fail(msg);

		}else{

			sysOrganizationService.deleteOrganizationOnPc(sysz);
			msg="删除成功";
		}

		return JsonData.successMsg(msg);
	}
	//显示机构所有数据
	@RequestMapping(value = "/TreeData.do")
	@ResponseBody
	public Object selectTreeListOnPc(HttpServletRequest request, HttpServletResponse response){

		List<SysOrganization> list= sysOrganizationService.findListOrganizationOnPc();

		return getChildren(list,null);

	}
	public static List<List<Object>> getChildren(List<SysOrganization> list,Integer pid){

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
    //依据条件查询对应数据
    @RequiresPermissions("sys:org:retrieve")
    @RequestMapping(value = "/search.do")
    @ResponseBody
    public JsonData selectFindListByOrg(SysOrganization sysz){
	    String msg="查询成功";
        List<SysOrganization> list = sysOrganizationService.findListOrganizationService(sysz);
		if (list == null || list.size()==0) {
			return JsonData.successMsg("查询结果为空");
		}
        return JsonData.success(list,msg);

    }
}
