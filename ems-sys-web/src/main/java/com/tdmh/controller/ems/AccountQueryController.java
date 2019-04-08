package com.tdmh.controller.ems;

import com.tdmh.common.JsonData;
import com.tdmh.service.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @RequiresPermissions("queryStats:account:visit")
    @RequestMapping("/listData.do")
    @ResponseBody
    public JsonData getAllAccountQueryList(){
        return JsonData.success();
    }


    @RequiresPermissions("queryStats:accountQuery:retrieve")
    @RequestMapping("/search.do")
    @ResponseBody
    public JsonData searchAccountQueryList(String startDate, String endDate, Integer userDistId, String userAddress, Integer pageNum, Integer pageSize) {
        return userService.searchAccountQueryList(startDate, endDate, userDistId, userAddress, pageNum, pageSize);
    }

    @RequiresPermissions("queryStats:accountQuery:export")
    @RequestMapping("/export.do")
    @ResponseBody
    public JsonData exportAccountQueryList(String startDate, String endDate, Integer userDistId, String userAddress) {
        return userService.exportAccountQueryList(startDate, endDate, userDistId, userAddress);
    }

    @RequiresPermissions("queryStats:accountQuery:export")
    @RequestMapping("/exportWithPageInfo.do")
    @ResponseBody
    public JsonData exportAccountQueryWithPageInfo(String startDate, String endDate, Integer userDistId, String userAddress, Integer pageNum, Integer pageSize) {
        return userService.exportAccountQueryWithPageInfo(startDate, endDate, userDistId, userAddress, pageNum, pageSize);
    }
}
