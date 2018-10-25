package com.tdmh.controller;

import com.tdmh.common.JsonData;
import com.tdmh.entity.User;
import com.tdmh.entity.UserChange;
import com.tdmh.service.IPreStrikeService;
import com.tdmh.service.IUserChangeService;
import com.tdmh.service.IUserService;
import com.tdmh.util.ShiroUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * 账务处理--预冲账
 *
 * @author qh on 2018/10/20.
 */
@Controller
@RequestMapping("/preStrike/")
public class PreStrikeController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IPreStrikeService preStrikeService;

    /**
     * 预冲账数据查询
     * @return
     */

    @RequiresPermissions("financial:prestrike:visit")
    @RequestMapping(value = "/listData.do")
    @ResponseBody
    public JsonData selectUserListController(User user){
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        return preStrikeService.selectUserByOrderTypeService( user,currentEmpId);
    }

    /**
     * 发起一笔预冲账申请
     * @param user
     * @return
     */
    @RequiresPermissions("financial:prestrike:visit")
    @RequestMapping(value = "/edit.do")
    @ResponseBody
    public JsonData editPrestrikeController(User user){
        String  currentEmpName = ShiroUtils.getPrincipal().getLoginName();
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        return user== null? JsonData.fail("未获取到该条数据相关信息，请检查数据或联系管理员"): preStrikeService.editUserOrdersService(user,currentEmpName,currentEmpId);
    }
    /**
     * 预冲账数据查询(头部筛选)
     * @return
     */

    @RequiresPermissions("financial:prestrike:visit")
    @RequestMapping(value = "/search.do")
    @ResponseBody
    public JsonData searchUserListController(User user){
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        return preStrikeService.selectUserByOrderTypeService( user,currentEmpId);
    }

}