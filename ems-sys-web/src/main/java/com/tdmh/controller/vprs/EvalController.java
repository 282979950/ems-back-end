package com.tdmh.controller.vprs;

import com.tdmh.common.JsonData;
import com.tdmh.service.IEvalService;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Liuxia on 2018/11/15.
 */
@Controller
@RequestMapping("/eval/")
public class EvalController {

    @Autowired
    private IEvalService evalService;

    /**
     * 获取所有的评价信息
     *
     * @return
     */
    @RequiresPermissions("querystats:eval:visit")
    @RequestMapping("/listData.do")
    @ResponseBody
    public JsonData listData() {
        return evalService.listData();
    }

    /**
     * 查询指定的评价信息
     *
     * @return
     */
    @RequiresPermissions("querystats:eval:retrieve")
    @RequestMapping("/search.do")
    @ResponseBody
    public JsonData selectEval(@Param("userName") String userName, @Param("applyRepairFlowNumber") String applyRepairFlowNumber) {
        return evalService.selectEval(userName,applyRepairFlowNumber);
    }
}
