package com.ems.controller;

import com.ems.common.JsonData;
import com.ems.param.LockAccountParam;
import com.ems.service.IUserService;
import org.apache.ibatis.annotations.Param;
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
    @RequestMapping(value = "/listData.do")
    @ResponseBody
    public JsonData getAllAccountArchives() {
        return userService.getAllAccountArchive();
    }

    /**
     * 显示已经绑定了表具的未开户的用户信息
     * @return
     */
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
    @RequestMapping(value = "/lock.do")
    @ResponseBody
    public JsonData updateLockStatus(LockAccountParam param) {
        return userService.updateLockStatus(param);
    }

    /**
     * 查询锁定记录
     * @param userId
     * @return
     */
    @RequestMapping(value = "/lock.do")
    @ResponseBody
    public JsonData searchLockList(@Param("userId") Integer userId) {
        return userService.searchLockList(userId);
    }

}
