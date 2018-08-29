package com.ems.controller;

import com.ems.common.JsonData;
import com.ems.param.InstallMeterParam;
import com.ems.service.IMeterService;
import com.ems.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 挂表
 *
 * @author litairan on 2018/8/28.
 */
@Controller
@RequestMapping("/installMeter/")
public class InstallMeterController {
    @Autowired
    private IMeterService meterService;

    @Autowired
    private IUserService userService;

    /**
     * 查询所有挂表信息
     */
    @RequestMapping(value = "/listData.do")
    @ResponseBody
    public JsonData listData() {
        return userService.getAllInstallMeters();
    }

    /**
     * 新增挂表信息
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/add.do")
    @ResponseBody
    public JsonData addInstallMeter(InstallMeterParam param) {
        return userService.addInstallMeter(param);
    }

    /**
     * 编辑挂表信息
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/edit.do")
    @ResponseBody
    public JsonData editInstallMeter(InstallMeterParam param) {
        return userService.editInstallMeter(param);
    }

    /**
     * 删除挂表信息
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete.do")
    @ResponseBody
    public JsonData deleteInstallMeter(@RequestParam(value = "ids[]") List<Integer> ids) {
        return userService.deleteInstallMeter(ids);
    }

    /**
     * 查询挂表信息
     *
     * @return
     */
    @RequestMapping(value = "/search.do")
    @ResponseBody
    public JsonData searchInstallMeter(@RequestParam("userId") Integer userId, @RequestParam("distName") String distName, @RequestParam("userAddress") String
            userAddress) {
        return userService.searchInstallMeter(userId, distName, userAddress);
    }
}
