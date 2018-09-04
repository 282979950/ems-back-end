package com.ems.controller;
/*
 * 字典Controller
 */

import com.ems.common.JsonData;
import com.ems.entity.SysDictionary;
import com.ems.service.SysDictionaryService;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/dic")
public class SysDictionaryController {

    @Resource
    private SysDictionaryService sysDictionaryService;
    @RequiresPermissions("sys:dic:visit")
    @RequestMapping(value = {"/listData.do"})
    @ResponseBody
    //获取字典数据列(List)
    public JsonData selectFindListOnPc(HttpServletRequest request, HttpServletResponse respose) {

        List<SysDictionary> list = sysDictionaryService.findListOnPc();
        return JsonData.successData(list);

    }

    //新增数据
    @RequiresPermissions("sys:dic:create")
    @RequestMapping(value = {"/add.do"})
    @ResponseBody
    public JsonData addSysDictionaryOnPc(SysDictionary sdy, HttpServletRequest request, HttpServletResponse respose) {
        String msg = "";
        msg = "新增类型为:"+sdy.getDictCategory()+"成功";
        int count = sysDictionaryService.selectCountRecordService(sdy);
        if(count>0){
            msg="操作失败，该类型下存在重复的字典键或值";
            return JsonData.fail(msg);
        }
        //手动设置(因目前没有用户信息，所以设置固定参数)
        sdy.setUsable(true);
        sdy.setCreateTime(new Date());
        sdy.setCreateBy(22223);
        sdy.setUpdateTime(new Date());
        sdy.setUpdateBy(465642);
        sysDictionaryService.insertDictionaryOnPc(sdy);

        return JsonData.successMsg(msg);

    }
    /*
     * 修改数据
     */
    @RequiresPermissions("sys:dic:update")
    @RequestMapping(value = {"/edit.do"})
    @ResponseBody
    public JsonData updateSysDictionaryOnPc(SysDictionary sdy, HttpServletRequest request, HttpServletResponse respose) {
        String msg = "";

        //查看重复值
        int countRecord = sysDictionaryService.keyCountRecordService(sdy);
        if(countRecord>0){
            msg="操作失败，该类型下存在重复的字典键";
            return JsonData.fail(msg);
        }
        //查询是否存在该条数据
        int count = sysDictionaryService.selectCountByIdOnPc(sdy);
        if (count > 0) {
            msg = "修改数据成功";
            sdy.setUpdateTime(new Date());
            sdy.setUpdateBy(465642);
            sysDictionaryService.updateByPrimaryKeySelective(sdy);
        } else {

            msg = "数据有误请重新刷新";
        }
        return JsonData.successMsg(msg);

    }

    /*
     *删除数据
     */
    @RequiresPermissions("sys:dic:delete")
    @RequestMapping(value = {"/delete.do"})
    @ResponseBody
    public JsonData deleteSysDictionaryOnPc(@RequestParam(value = "ids[]")List <Integer> ids) {
        SysDictionary sdy = new SysDictionary();
        String msg = "删除字典成功";
        if (ids.size()<=0) {

            msg = "获取该条数据失败,请联系管理员";
            return JsonData.successMsg(msg);
        }
        sdy.setIds(ids);
        //查询是否存在该条数据
        int count = sysDictionaryService.selectCountByIdOnPc(sdy);

        if (count > 0) {

            sysDictionaryService.deleteSysDictionaryById(sdy);

        } else {

            msg = "未获取到该条记录,请刷新重试";
        }


        return JsonData.successMsg(msg);
    }

    //根据字典类型查看对应数据字典信息
    @RequiresPermissions("sys:dic:retrieve")
    @RequestMapping(value = {"/dictByType.do"})
    @ResponseBody
    public JsonData selectDictByType(String orgCategory ,HttpServletRequest request, HttpServletResponse respose) {

        //获取类型时查看是否为空
        if (StringUtils.isNotBlank(orgCategory)) {

            List<SysDictionary> list = sysDictionaryService.findListByTypeOnPc(orgCategory);
            if(list.size()<=0){

                return JsonData.fail("该字段没有对应数据字典值");
            }

            return JsonData.successData(list);
        }

        return JsonData.fail("未获取到数据");
    }
    //依据条件查询对应数据
    @RequiresPermissions("sys:dic:retrieve")
    @RequestMapping(value = "/search.do")
    @ResponseBody
    public JsonData selectFindListByDict(SysDictionary sdy,HttpServletRequest request, HttpServletResponse respose){
        List<SysDictionary> list = sysDictionaryService.findListByService(sdy);

        return JsonData.success(list,"查询成功");
    }
}
