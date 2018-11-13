package com.tdmh.controller.ems;

import com.tdmh.common.JsonData;
import com.tdmh.param.EntryMeterParam;
import com.tdmh.service.IMeterService;
import com.tdmh.util.ShiroUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
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
    @RequiresPermissions("account:entryMeter:visit")
    @RequestMapping(value = "/listData.do")
    @ResponseBody
    public JsonData listData() {
        return meterService.getAllEntryMeters();
    }

    /**
     * 获取所有表具类型
     */
    @RequiresPermissions("account:entryMeter:visit")
    @RequestMapping(value = "/getAllMeterTypes.do")
    @ResponseBody
    public JsonData getAllMeterTypes() {
        return meterService.getAllMeterTypes();
    }

    /**
     * 新增入库信息
     *
     * @param param
     * @return
     */
    @RequiresPermissions("account:entryMeter:create")
    @RequestMapping(value = "/add.do")
    @ResponseBody
    public JsonData addEntryMeter(EntryMeterParam param) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        param.setCreateBy(currentEmpId);
        param.setUpdateBy(currentEmpId);
        return meterService.addEntryMeter(param);
    }

    /**
     * 编辑入库信息
     *
     * @param param
     * @return
     */
    @RequiresPermissions("account:entryMeter:update")
    @RequestMapping(value = "/edit.do")
    @ResponseBody
    public JsonData editEntryMeter(EntryMeterParam param) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        param.setUpdateBy(currentEmpId);
        return meterService.editEntryMeter(param);
    }

    /**
     * 删除入库信息
     *
     * @param ids
     * @return
     */
    @RequiresPermissions("account:entryMeter:delete")
    @RequestMapping(value = "/delete.do")
    @ResponseBody
    public JsonData deleteEntryMeter(@RequestParam(value = "ids[]") List<Integer> ids) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        return meterService.deleteEntryMeter(ids,currentEmpId);
    }

    /**
     * 查询入库信息
     *
     * @param meterCode
     * @param meterCategory
     * @param meterType
     * @param meterDirection
     * @param meterProdDate
     * @return
     */
    @RequiresPermissions("account:entryMeter:retrieve")
    @RequestMapping(value = "/search.do")
    @ResponseBody
    public JsonData searchEntryMeter(@Param("meterCode") String meterCode, @Param("meterCategory") String meterCategory, @Param("meterType") String
            meterType, @Param("meterDirection") Boolean meterDirection, @Param("meterProdDate") @DateTimeFormat(pattern="yyyy-MM") Date meterProdDate) {
        return meterService.searchEntryMeter(meterCode, meterCategory, meterType, meterDirection, meterProdDate);
    }

    @RequestMapping(value = "/getMeterByMeterCode.do")
    @ResponseBody
    public JsonData getMeterByMeterCode(@Param("meterCode") String meterCode) {
        return meterService.getMeterByMeterCode(meterCode);
    }
}
