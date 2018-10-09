package com.tdmh.shiro;

import com.tdmh.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 1.自定义用户鉴权过滤器
 * 2.扩展异步请求认证提示功能;
 *
 * @author litairan
 */
@Slf4j
public class CustomAuthorizationFilter extends AuthorizationFilter {

    @Autowired
    private SystemService systemService;

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        Subject subject = getSubject(request, response);
        Session currSession = subject.getSession();
        Object obj = currSession.getAttribute("kickout");
        if (obj != null) {
            if (ServletUtils.isAjaxRequest(httpRequest)) {
                httpResponse.setHeader("sessionstatus", "kickout");
//                systemService.getSessionDao().delete(currSession);
            } else {
                saveRequestAndRedirectToLogin(request, response);
            }
        } else {
            if (subject.getPrincipal() == null) {
                if (ServletUtils.isAjaxRequest(httpRequest)) {
                    httpResponse.setHeader("sessionstatus", "timeout");
                } else {
                    saveRequestAndRedirectToLogin(request, response);
                }
            } else {
                return true;
            }
        }
        return false;
    }
}
