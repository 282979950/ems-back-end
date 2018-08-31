package com.ems.controller;

import com.ems.common.JsonData;
import com.ems.param.EntryMeterParam;
import com.ems.service.IMeterService;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 表具入库
 *
 * @author litairan on 2018/8/24.
 */
@Controller
@RequestMapping("/entry/")
public class EntryMeterController {

    @Autowired
    private IMeterService meterService;

    /**
     * 查询所有入库的表具信息
     */
    @RequiresPermissions("sys:entryMeter:visit")
    @RequestMapping(value = "/listData.do")
    @ResponseBody
    public JsonData listData() {
        return meterService.getAllEntryMeters();
    }

    /**
     * 新增入库信息
     *
     * @param param
     * @return
     */
    @RequiresPermissions("sys:entryMeter:create")
    @RequestMapping(value = "/add.do")
    @ResponseBody
    public JsonData addEntryMeter(EntryMeterParam param) {
        return meterService.addEntryMeter(param);
    }

    /**
     * 编辑入库信息
     *
     * @param param
     * @return
     */
    @RequiresPermissions("sys:entryMeter:update")
    @RequestMapping(value = "/edit.do")
    @ResponseBody
    public JsonData editEntryMeter(EntryMeterParam param) {
        return meterService.editEntryMeter(param);
    }

    /**
     * 删除入库信息
     *
     * @param ids
     * @return
     */
    @RequiresPermissions("sys:entryMeter:delete")
    @RequestMapping(value = "/delete.do")
    @ResponseBody
    public JsonData deleteEntryMeter(@RequestParam(value = "ids[]") List<Integer> ids) {
        return meterService.deleteEntryMeter(ids);
    }

    /**
     * 查询入库信息
     *
     * @param meterCode
     * @param meterCategory
     * @param meterType
     * @param meterDirection
     * @return
     */
    @RequiresPermissions("sys:entryMeter:retrieve")
    @RequestMapping(value = "/search.do")
    @ResponseBody
    public JsonData searchEntryMeter(@Param("meterCode") String meterCode, @Param("meterCategory") String meterCategory, @Param("meterType") String
            meterType, @Param("meterDirection") Boolean meterDirection) {
        return meterService.searchEntryMeter(meterCode, meterCategory, meterType, meterDirection);
    }
}
