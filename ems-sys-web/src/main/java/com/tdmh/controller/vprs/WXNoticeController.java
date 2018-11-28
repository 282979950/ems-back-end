package com.tdmh.controller.vprs;

import com.tdmh.common.JsonData;
import com.tdmh.param.WXNoticeParam;
import com.tdmh.service.IWXNoticeService;
import com.tdmh.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author litairan on 2018/11/16.
 */
@Controller
@RequestMapping("/wxNotice/")
@ResponseBody
public class WXNoticeController {

    @Autowired
    private IWXNoticeService wxNoticeService;

    @RequiresPermissions("sys:wxNotice:visit")
    @RequestMapping("listData.do")
    public JsonData listData(Integer pageNum, Integer pageSize) {
        return wxNoticeService.listDataWithPagination(pageNum, pageSize);
    }

    @RequiresPermissions("sys:wxNotice:create")
    @RequestMapping("add.do")
    public JsonData create(WXNoticeParam param) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        param.setCreateBy(currentEmpId);
        param.setUpdateBy(currentEmpId);
        return wxNoticeService.create(param);
    }

    @RequiresPermissions("sys:wxNotice:create")
    @RequestMapping("delete.do")
    public JsonData delete(@RequestParam(value = "ids[]") List<Integer> ids) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        return wxNoticeService.delete(ids, currentEmpId);
    }

    @RequiresPermissions("sys:wxNotice:create")
    @RequestMapping("search.do")
    public JsonData search(String wxNoticeTitle, Integer pageNum, Integer pageSize) {
        return wxNoticeService.search(wxNoticeTitle, pageNum, pageSize);
    }
}
