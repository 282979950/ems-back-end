package com.ems.controller;
/*
 * 字典Controller
 */

import com.ems.entity.SysDictionary;
import com.ems.service.SysDictionaryService;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/sdy")
public class SysDictionaryController {

    @Resource
    private SysDictionaryService sysDictionaryService;

    @RequestMapping(value = {"/listData.do"})
    @ResponseBody
    //获取字典数据列(List)
    public Object selectFindListOnPc(HttpServletRequest request, HttpServletResponse respose) {

        List<SysDictionary> list = sysDictionaryService.findListOnPc();

        return list;
    }

    //新增数据
    @RequestMapping(value = {"/insert.do"})
    @ResponseBody
    public Map<String, Object> addSysDictionaryOnPc(SysDictionary sdy, HttpServletRequest request, HttpServletResponse respose) {
        Map<String, Object> resultMap = Maps.newHashMap();
        String msg = "";
        //若id为空则新增

//        if (StringUtils.isNotBlank(sdy.getId())) {
        if (true) {
            // TODO: 2018/7/23
            msg = "新增数据成功";
            //手动设置(因目前没有用户信息，所以设置固定参数)
            sdy.setCreateTime(new Date());
            sdy.setCreateBy(22223);
            sysDictionaryService.insertDictionaryOnPc(sdy);

            //否则修改
        } else {
            //查询是否存在该条数据
//            int count = sysDictionaryService.selectCountByIdOnPc(sdy.getId());
            // TODO: 2018/7/23
            int count = 1;
            if (count > 0) {
                msg = "修改数据成功";
                sdy.setUpdateTime(new Date());
                sdy.setUpdateBy(465642);
                sysDictionaryService.updateByPrimaryKeySelective(sdy);
            } else {

                msg = "数据有误请重新刷新";
            }

        }

        resultMap.put("success", true);
        resultMap.put("msg", msg);
        return resultMap;

    }

    //删除数据
    @RequestMapping(value = {"/delete.do"})
    @ResponseBody
    public Map<String, Object> deleteSysDictionaryOnPc(HttpServletRequest request, HttpServletResponse respose, String id) {
        Map<String, Object> resultMap = Maps.newHashMap();
        String msg = "删除成功";
        if (StringUtils.isBlank(id)) {

            msg = "获取该条数据失败,请联系管理员";
            resultMap.put("success", true);
            resultMap.put("msg", msg);
            return resultMap;
        }
        //查询是否存在该条数据
        int count = sysDictionaryService.selectCountByIdOnPc(id);

        if (count > 0) {

            sysDictionaryService.deleteSysDictionaryById(id);

        } else {

            msg = "未获取到该条记录,请刷新重试";
        }

        resultMap.put("success", true);
        resultMap.put("msg", msg);
        return resultMap;
    }

    //根据字典类型查看对应数据字典信息
    @RequestMapping(value = {"/dictByType.do"})
    @ResponseBody
    public Object selectDictByType(HttpServletRequest request, HttpServletResponse respose, String dictCategory) {
        //获取类型时查看是否为空
        if (StringUtils.isNotBlank(dictCategory)) {

            List<SysDictionary> list = sysDictionaryService.findListByTypeOnPc(dictCategory);
            return list;
        }

        return null;
    }
}
