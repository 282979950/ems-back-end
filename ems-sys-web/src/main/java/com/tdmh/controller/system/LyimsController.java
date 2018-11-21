package com.tdmh.controller.system;

import com.tdmh.common.JsonData;
import com.tdmh.service.IApplyRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Liuxia on 2018/11/19.
 */
@Controller
@RequestMapping("/lyimsstandard")
public class LyimsController {

    @Autowired
    private IApplyRepairService applyRepairService;

    @RequestMapping("/updateApplyRepair")
    @ResponseBody
    public JsonData updateApplyRepair(String applyRepairFlowNumber, Integer applyRepairStatus){
        return applyRepairService.updateApplyRepair(applyRepairFlowNumber,applyRepairStatus);
    }
}
