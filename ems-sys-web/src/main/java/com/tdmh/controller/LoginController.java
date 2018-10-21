package com.tdmh.controller;

import com.tdmh.common.JsonData;
import com.tdmh.entity.Principal;
import com.tdmh.service.IWXLoginService;
import com.tdmh.shiro.CustomFormAuthenticationFilter;
import com.tdmh.util.ShiroUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author litairan on 2018/7/27.
 */
@Controller
@RequestMapping(value = "/")
public class LoginController {

    @Autowired
    private IWXLoginService wxLoginService;

    /**
     * 进入登录页面
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        System.out.println("进入login");
        Subject subject = SecurityUtils.getSubject();
        Principal principal = (Principal) subject.getPrincipal();
        if (principal != null) {
            return "redirect:/index";
        }
        return "login";
    }

    /**
     * 登录失败
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String loginFailure(HttpServletRequest request, Model model) {
        String message = (String) request.getAttribute(CustomFormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
        if (message != null) {
            model.addAttribute(CustomFormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);
        }
        String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
        boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
        String exception = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);

        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
        return "login";
    }

    /**
     * 进入主页
     */
    @RequiresRoles("user")
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(Model model) {
        Principal principal = ShiroUtils.getPrincipal();
        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, principal.getLoginName());
        return "index";
    }

    /**
     * 员工登出
     */
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public String logout(HttpServletRequest request, Model model) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }

    @RequestMapping(value = "wxLogin", method = RequestMethod.GET)
    @ResponseBody
    public JsonData wxLogin(@Param("code") String code) {
        return wxLoginService.wxLogin(code);
    }
}
