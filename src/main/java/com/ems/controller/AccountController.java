package com.ems.controller;

import com.ems.common.JsonData;
import com.ems.param.MeterEntryParam;
import com.ems.service.IMeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 账户管理控制器
 *
 * @author litairan on 2018/8/8.
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private IMeterService meterService;

    /**
     * 表具入库
     *
     * @param param
     * @return
     */
    @RequestMapping("/entryMeter")
    @ResponseBody
    public JsonData entryMeter(MeterEntryParam param) {
        return meterService.entryMeter(param);
    }

    /**
     * 获取所有表具信息
     *
     * @return
     */
    @RequestMapping(value = "/selectAllMeters",method = RequestMethod.GET)
    @ResponseBody
    public JsonData selectAll() {
        return meterService.selectAll();
    }
}
