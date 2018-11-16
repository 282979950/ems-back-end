package com.tdmh.controller.vprs;

import com.tdmh.common.JsonData;
import com.tdmh.param.WxEvalParam;
import com.tdmh.service.IEvalItemService;
import com.tdmh.service.IServiceOutletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Liuxia on 2018/11/14.
 */
@RestController
@RequestMapping("/wx/")
public class WXServiceOutletController {

    @Autowired
    private IServiceOutletService serviceOutletService;

    @Autowired
    private IEvalItemService evalItemService;

    /**
     * 获取网点信息
     * @return
     */
    @GetMapping("/getAllServiceOutlet")
    public JsonData getAllServiceOutlet(){
        return serviceOutletService.getAllSOLet();
    }

    /**
     * 获取评价项信息
     * @return
     */
    @GetMapping("/getEvalItem")
    public JsonData getEvalItem(){
        return evalItemService.getWXEvalItem();
    }

    /**
     * 保存评价
     * @param evalParam
     * @return
     */
    @PostMapping("/saveEval")
    public JsonData saveEval(@RequestBody WxEvalParam evalParam){
        return evalItemService.saveEval(evalParam);
    }
}
