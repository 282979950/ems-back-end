package com.tdmh.controller.ems;

import com.tdmh.common.JsonData;
import com.tdmh.param.InstallMeterParam;
import com.tdmh.service.IMeterService;
import com.tdmh.service.IUserService;
import com.tdmh.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
    @RequiresPermissions("account:installMeter:visit")
    @RequestMapping(value = "/listData.do")
    @ResponseBody
    public JsonData listData(Integer pageNum, Integer pageSize) {
        return userService.getAllInstallMeters(pageNum, pageSize);
    }

    /**
     * 新增挂表信息
     *
     * @param param
     * @return
     */
    @RequiresPermissions("account:installation:create")
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
    @RequiresPermissions("account:installMeter:update")
    @RequestMapping(value = "/edit.do")
    @ResponseBody
    public JsonData editInstallMeter(InstallMeterParam param) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        param.setUpdateBy(currentEmpId);
        return userService.editInstallMeter(param);
    }

    /**
     * 删除挂表信息
     *
     * @param ids
     * @return
     */
    @RequiresPermissions("account:installation:delete")
    @RequestMapping(value = "/delete.do")
    @ResponseBody
    public JsonData deleteInstallMeter(@RequestParam(value = "ids[]") List<Integer> ids) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        return userService.deleteInstallMeter(ids,currentEmpId);
    }

    /**
     * 查询挂表信息
     *
     * @return
     */
    @RequiresPermissions("account:installMeter:retrieve")
    @RequestMapping(value = "/search.do")
    @ResponseBody
    public JsonData searchInstallMeter(Integer userId, Integer userDistId, String userAddress, Integer pageNum, Integer pageSize) {
        return userService.searchInstallMeter(userId, userDistId, userAddress, pageNum, pageSize);
    }
}
