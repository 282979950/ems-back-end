package com.tdmh.controller.ems;

import com.tdmh.common.JsonData;
import com.tdmh.entity.StrikeNucleus;
import com.tdmh.service.IPreStrikeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 账务处理--冲账
 *
 * @author qh on 2018/10/20.
 */
@Controller
@RequestMapping("/strike/")
public class StrikeNucleusController {

    @Autowired
    private IPreStrikeService preStrikeService;

    /**
     * 数据查询
     * @return
     */

    @RequiresPermissions("financial:strike:visit")
    @RequestMapping(value = "/listData.do")
    @ResponseBody
    public JsonData selectUserListController(StrikeNucleus strikeNucleus, Integer pageNum, Integer pageSize){
        return strikeNucleus== null?JsonData.fail("未获取到相关数据，请刷新数据或联系管理员"): preStrikeService.selectStrikeNucleusListService(strikeNucleus, pageNum, pageSize);

    }
    /**
     * 数据审核flag标识
     */
    @RequiresPermissions("financial:strike:visit")
    @RequestMapping(value = "/edit.do")
    @ResponseBody
    public JsonData updateStrikeController(StrikeNucleus strikeNucleus ,boolean flag){
        return strikeNucleus ==null?JsonData.fail("未获取到相关数据，请刷新数据或联系管理员"): preStrikeService.updateStrikeService(strikeNucleus,flag);

    }
    /**
     * 数据查询(头部筛选)
     * @return
     */

    @RequiresPermissions("financial:strike:visit")
    @RequestMapping(value = "search.do")
    @ResponseBody
    public JsonData searchUserListController(StrikeNucleus strikeNucleus, Integer pageNum, Integer pageSize){
        return strikeNucleus== null?JsonData.fail("未获取到相关数据，请刷新数据或联系管理员"): preStrikeService.selectStrikeNucleusListService(strikeNucleus, pageNum, pageSize);

    }

}
