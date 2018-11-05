package com.tdmh.controller;

import com.tdmh.common.JsonData;
import com.tdmh.param.PrePaymentParam;
import com.tdmh.service.IPrePaymentService;
import com.tdmh.utils.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * qh,2018-10-22
 */

@Controller
@RequestMapping("/ardQuery/")
public class ArdQueryController {
    @Autowired
    private IPrePaymentService prePaymentService;

    /**
     * 查询统计
     * @param param
     * @return
     */
    @RequiresPermissions("querystats:ardQuery:visit")
    @RequestMapping(value = "/search.do")
    @ResponseBody
    public JsonData selectFindListArdQueryByIccardIdentifier(PrePaymentParam param){
        //屏蔽掉默认查询按钮
        if(StringUtils.isBlank(param.getIccardIdentifier())){
            return JsonData.fail("请点击'识别IC卡'按钮");
        }
        return prePaymentService.selectFindListArdQueryService(param);
    }
    /**
     * 查询统计
     * @return
     */
    @RequiresPermissions("querystats:ardQuery:visit")
    @RequestMapping("/listData.do")
    @ResponseBody
    public JsonData selectFindListArdQuery(){
        return JsonData.success();
    }
    /**
     * 查询统计-----数据导出
     * @param param
     * @return
     */
    @RequiresPermissions("querystats:ardQuery:visit")
    @RequestMapping(value = "/Export.do")
    @ResponseBody
    public void ardQueryExport(PrePaymentParam param,HttpServletResponse response){
        prePaymentService.selectFindListExportArdQueryService(param,response);
    }

}
