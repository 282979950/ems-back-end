package com.ems.shiro.authc;

import com.ems.common.Const;
import com.ems.utils.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 表单验证（包含验证码）过滤类
 *
 * @author hml
 * @version 2016-5-19
 */
@Service
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

    public static final String DEFAULT_MESSAGE_PARAM = "message";

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        if (password == null) {
            password = "";
        }
        boolean rememberMe = isRememberMe(request);
        String host = StringUtils.getRemoteAddr((HttpServletRequest) request);
        return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host);
    }

    /**
     * 登录成功之后跳转URL
     */
    @Override
    public String getSuccessUrl() {
        return super.getSuccessUrl();
    }

    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
    }

    /**
     * 登录失败调用事件
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        String className = e.getClass().getName();
        String message;
        if (e instanceof UnknownAccountException) {
            message = Const.EMP_LOGIN_NOTEXIST;
        } else if (e instanceof IncorrectCredentialsException) {
            message = Const.EMP_LOGIN_ERRORPASSWORD;
        } else if (e.getMessage() != null && StringUtils.startsWith(e.getMessage(), "msg:")) {
            // TODO: 2018/7/26 清理msg：开头的异常消息
            message = StringUtils.replace(e.getMessage(), "msg:", "");
        } else {
            e.printStackTrace();
            message = Const.EMP_LOGIN_EXCEPTION;
        }
        request.setAttribute(getFailureKeyAttribute(), className);
        request.setAttribute(DEFAULT_MESSAGE_PARAM, message);
        return true;
    }

}