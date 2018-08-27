package com.ems.controller;

import com.ems.common.JsonData;
import com.ems.param.CreateArchiveParam;
import com.ems.service.IUserService;
import org.apache.ibatis.annotations.Param;
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
    @RequestMapping(value = "/listData.do")
    @ResponseBody
    public JsonData getAllArchives() {
        return userService.getAllArchives();
    }

    /**
     * 新增档案
     *
     * @return
     */
    @RequestMapping(value = "/add.do")
    @ResponseBody
    public JsonData addArchive(CreateArchiveParam param) {
        return userService.addArchive(param);
    }

    /**
     * 编辑档案
     *
     * @return
     */
    @RequestMapping(value = "/edit.do")
    @ResponseBody
    public JsonData editArchive(CreateArchiveParam param) {
        return userService.editArchive(param);
    }

    /**
     * 删除档案
     *
     * @return
     */
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
    @RequestMapping(value = "/search.do")
    @ResponseBody
    public JsonData searchArchive(@Param("userId") Integer userId, @Param("distName") String distName, @Param("userAddress") String userAddress, @Param
            ("userType") Integer userType, @Param("userGasType") Integer userGasType, @Param("userStatus") Integer userStatus) {
        return userService.searchArchive(userId, distName, userAddress, userType, userGasType, userStatus);
    }
}
