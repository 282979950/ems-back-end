package com.tdmh.controller.system;

import com.tdmh.common.JsonData;
import com.tdmh.param.EvalItemParam;
import com.tdmh.service.IEvalItemService;
import com.tdmh.util.ShiroUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Liuxia on 2018/11/14.
 */
@Controller
@RequestMapping("/evalItem/")
public class EvalItemController {
    @Autowired
    private IEvalItemService evalItemService;

    /**
     * 显示所有评价项信息
     * @return
     */
    @RequiresPermissions("sys:evalItem:visit")
    @RequestMapping(value = "/listData.do")
    @ResponseBody
    public JsonData getAllEvalItem() {
        return evalItemService.getAllEvalItem();
    }

    /**
     * 新增评价项
     *
     */
    @RequiresPermissions("sys:evalItem:create")
    @RequestMapping(value = "add.do")
    @ResponseBody
    public JsonData createEvalItem(EvalItemParam evalItemParam) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        evalItemParam.setCreateBy(currentEmpId);
        evalItemParam.setUpdateBy(currentEmpId);
        return evalItemService.createEvalItem(evalItemParam);
    }

    /**
     * 删除评价项
     */
    @RequiresPermissions("sys:evalItem:delete")
    @RequestMapping("delete.do")
    @ResponseBody
    public JsonData deleteEvalItem(@RequestParam(value = "ids[]")List<Integer> ids) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        return evalItemService.deleteEvalItem(ids, currentEmpId);
    }

    /**
     * 更新评价项
     */
    @RequiresPermissions("sys:evalItem:update")
    @RequestMapping("edit.do")
    @ResponseBody
    public JsonData updateEvalItem(EvalItemParam evalItemParam) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        evalItemParam.setUpdateBy(currentEmpId);
        return evalItemService.updateEvalItem(evalItemParam);
    }

    /**
     * 获取评价项
     */
    @RequiresPermissions("sys:evalItem:retrieve")
    @RequestMapping("search.do")
    @ResponseBody
    public JsonData selectEvalItem(@Param("evalItemContent") String evalItemContent) {
        return evalItemService.selectEvalItem(evalItemContent);
    }
}
