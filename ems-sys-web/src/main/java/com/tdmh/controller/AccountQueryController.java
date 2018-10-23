package com.tdmh.controller;

import com.tdmh.common.JsonData;
import com.tdmh.service.IUserService;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @author Liuxia on 2018/10/22.
 */
@Controller
@RequestMapping("/accountQuery")
public class AccountQueryController {

    @Autowired
    private IUserService userService;

    /**
     * 查询所有订单
     * @return
     */
    @RequiresPermissions("querystats:account:visit")
    @RequestMapping("/listData.do")
    @ResponseBody
    public JsonData getAllAccountQueryList(){
        return JsonData.success();
    }


    @RequiresPermissions("querystats:account:retrieve")
    @RequestMapping("/search.do")
    @ResponseBody
    public JsonData searchAccountQueryList(@Param("accountDate") String accountDate, @Param("userDistId") Integer userDistId, @Param("userAddress") String userAddress){
        return userService.searchAccountQueryList(accountDate, userDistId, userAddress);
    }
}
