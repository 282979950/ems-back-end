package com.tdmh.controller.ems;

import com.tdmh.common.JsonData;
import com.tdmh.param.CreateArchiveParam;
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
 * @author litairan on 2018/8/27.
 */
@Controller
@RequestMapping("/createArchive/")
public class CreateArchiveController {

    @Autowired
    private IUserService userService;

    /**
     * 新增档案
     *
     * @return
     */
    @RequiresPermissions("account:createArchive:visit")
    @RequestMapping(value = "/listData.do")
    @ResponseBody
    public JsonData getAllArchives(Integer pageNum, Integer pageSize) {
        return userService.getAllArchives(pageNum, pageSize);
    }

    /**
     * 新增档案
     *
     * @return
     */
    @RequiresPermissions("account:createArchive:create")
    @RequestMapping(value = "/add.do")
    @ResponseBody
    public JsonData addArchive(CreateArchiveParam param) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        param.setCreateBy(currentEmpId);
        param.setUpdateBy(currentEmpId);
        return userService.addArchive(param);
    }

    /**
     * 编辑档案
     *
     * @return
     */
    @RequiresPermissions("account:createArchive:update")
    @RequestMapping(value = "/edit.do")
    @ResponseBody
    public JsonData editArchive(CreateArchiveParam param) {
        Integer currentEmpId = ShiroUtils.getPrincipal().getId();
        param.setUpdateBy(currentEmpId);
        return userService.editArchive(param);
    }

    /**
     * 删除档案
     *
     * @return
     */
    @RequiresPermissions("account:createArchive:delete")
    @RequestMapping(value = "/delete.do")
    @ResponseBody
    public JsonData deleteArchive(@RequestParam(value = "ids[]") List<Integer> ids) {
        return userService.deleteArchive(ids);
    }

    /**
     * 查询档案
     *
     * @return
     */
    @RequiresPermissions("account:createArchive:retrieve")
    @RequestMapping(value = "/search.do")
    @ResponseBody
    public JsonData searchArchive(Integer userId, Integer userDistId, String userAddress, Integer userType, Integer userGasType, Integer userStatus,
                                  Integer pageNum, Integer pageSize) {
        return userService.searchArchive(userId, userDistId, userAddress, userType, userGasType, userStatus, pageNum, pageSize);
    }
}
