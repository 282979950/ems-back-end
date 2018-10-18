package com.tdmh.controller;

import com.tdmh.common.JsonData;
import com.tdmh.param.LockAccountParam;
import com.tdmh.service.IUserService;
import com.tdmh.util.ShiroUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author liuxia on 2018/8/30.
 */
@Controller
@RequestMapping("/lockAccount/")
public class LockAccountController {

    @Autowired
    private IUserService userService;

    /**
     * 显示已经绑定了表具的未开户的用户信息
     * @return
     */
    @RequiresPermissions("account:lockAccount:visit")
    @RequestMapping(value = "/listData.do")
    @ResponseBody
    public JsonData getAllAccountArchives() {
        return userService.getAllAccountArchive();
    }

    /**
     * 显示已经绑定了表具的未开户的用户信息
     * @return
     */
    @RequiresPermissions("account:lockAccount:retrieve")
    @RequestMapping(value = "/search.do")
    @ResponseBody
    public JsonData searchAccountArchives(@Param("userId") Integer userId, @Param("userName") String userName, @Param("iccardId") Integer iccardId) {
        return userService.searchAllAccountArchive(userId, userName, iccardId);
    }

    /**
     * 锁定/解锁操作
     * @param param
     * @return
     */
    @RequiresPermissions("account:lockAccount:lock")
    @RequestMapping(value = "/lock.do")
    @ResponseBody
    public JsonData updateLockStatus(LockAccountParam param) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        param.setCreateBy(currentEmpId);
        param.setUpdateBy(currentEmpId);
        return userService.updateLockStatus(param);
    }

    /**
     * 查询锁定记录
     * @param userId
     * @return
     */
    @RequiresPermissions("account:lockAccount:lockList")
    @RequestMapping(value = "/List.do")
    @ResponseBody
    public JsonData searchLockList(@Param("userId") Integer userId) {
        return userService.searchLockList(userId);
    }

}
