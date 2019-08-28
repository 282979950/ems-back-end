package com.tdmh.controller.system;

import com.tdmh.common.JsonData;
import com.tdmh.entity.Principal;
import com.tdmh.exception.CustomException;
import com.tdmh.util.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author litairan on 2018/7/27.
 */
@Controller
@RequestMapping(value = "/")
public class LoginController {

    /**
     * 登录失败
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public JsonData login(@RequestParam("userName") String userName, @RequestParam("password") String password, String type) {
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);
        } catch (UnknownAccountException | IncorrectCredentialsException uae) {
            throw new CustomException("用户名或密码错误");
        } catch (LockedAccountException lae) {
            throw new CustomException("LockedAccountException");
        } catch (ExcessiveAttemptsException eae) {
            throw new CustomException("ExcessiveAttemptsException");
        } catch (AuthenticationException ae) {
            throw new CustomException("AuthenticationException");
        }
        return JsonData.successData(currentUser.getPrincipal());
    }

    /**
     * 进入主页
     */
    @RequestMapping(value = "currentUser", method = RequestMethod.POST)
    @ResponseBody
    public Principal index() {
        return ShiroUtils.getPrincipal();
    }

    /**
     * 员工登出
     */
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    @ResponseBody
    public JsonData logout(HttpServletRequest request, Model model) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return JsonData.success();
    }
}
