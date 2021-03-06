package com.tdmh.controller.system;
/*
 * 字典Controller
 */

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tdmh.common.JsonData;
import com.tdmh.entity.ListNode;
import com.tdmh.entity.SysDictionary;
import com.tdmh.service.SysDictionaryService;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping(value = "/dic")
public class SysDictionaryController {

    @Resource
    private SysDictionaryService sysDictionaryService;
    @RequiresPermissions("sys:dic:visit")
    @RequestMapping(value = {"/listData.do"})
    @ResponseBody
    //获取字典数据列(List)
    public JsonData selectFindListOnPc(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysDictionary> list = sysDictionaryService.findListOnPc();
        PageInfo<SysDictionary> page = new PageInfo<>(list);
        return JsonData.successData(page);
    }

    @RequiresPermissions("sys:dic:visit")
    @RequestMapping(value = {"/loadListData.do"})
    @ResponseBody
    public JsonData loadListData(String category) {
        if (StringUtils.isNotBlank(category)) {
            List<SysDictionary> list = sysDictionaryService.findListByTypeOnPc(category);
            if (list == null || list.size() <= 0) {
                return JsonData.fail("该字段没有对应数据字典值");
            }
            List<ListNode> nodes = new ArrayList<>();
            for (SysDictionary sysDictionary : list) {
                ListNode node = new ListNode();
                node.setKey(sysDictionary.getDictKey());
                node.setValue(sysDictionary.getDictValue());
                nodes.add(node);
            }
            return JsonData.successData(nodes);
        }
        return JsonData.fail("未获取到数据");
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
        if(StringUtils.isBlank(sdy.getDictKey())){

            return JsonData.fail("字典键不能为空");
        }
        if (sdy.getDictValue() == null) {
            return JsonData.fail("字典值不能为空");
        }
        if(StringUtils.isBlank(sdy.getDictCategory())){
            return JsonData.fail("字典类型不能为空");
        }
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
    @RequestMapping(value = {"/dictByType.do"})
    @ResponseBody
    public JsonData selectDictByType(String category) {

        //获取类型时查看是否为空
        if (StringUtils.isNotBlank(category)) {

            List<SysDictionary> list = sysDictionaryService.findListByTypeOnPc(category);
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
    public JsonData selectFindListByDict(SysDictionary sdy, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysDictionary> list = sysDictionaryService.findListByService(sdy);
        PageInfo<SysDictionary> page = new PageInfo<>(list);
        return JsonData.success(page, "查询成功");
    }
}
