package com.tdmh.controller;

import com.tdmh.common.JsonData;
import com.tdmh.param.AccountQueryParam;
import com.tdmh.service.IUserService;
import com.tdmh.utils.excel.TableField;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

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
    public JsonData searchAccountQueryList(@Param("startDate") String startDate,@Param("endDate") String endDate, @Param("userDistId") Integer userDistId, @Param("userAddress") String userAddress){
        return userService.searchAccountQueryList(startDate, endDate, userDistId, userAddress);
    }

    @RequiresPermissions("querystats:account:export")
    @RequestMapping("/export.do")
    @ResponseBody
    public void exportAccountQueryList(@Param("startDate") String startDate,@Param("endDate") String endDate, @Param("userDistId") Integer userDistId, @Param("userAddress") String userAddress,HttpServletRequest request, HttpServletResponse response ){
        userService.exportAccountQueryList(startDate, endDate, userDistId, userAddress, request, response);
    }
}
