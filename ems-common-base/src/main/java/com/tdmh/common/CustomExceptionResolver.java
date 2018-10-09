package com.tdmh.common;


import com.tdmh.exception.ParameterException;
import com.tdmh.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义异常处理类
 *
 * @author litairan on 2018/7/20.
 */
@Slf4j
public class CustomExceptionResolver implements HandlerExceptionResolver {

    private static final String DEFAULT_ERROR_MESSAGE = "系统错误";

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        String url = request.getRequestURL().toString();
        ModelAndView mv;

        // 这里我们要求项目中所有请求json数据，都使用.json结尾
        if (url.endsWith(".do")) {
            if (e instanceof PermissionException || e instanceof ParameterException || e instanceof AuthenticationException || e instanceof AuthorizationException) {
                JsonData result = JsonData.fail(e.getMessage());
                mv = new ModelAndView("jsonView", result.toMap());
            } else {
                log.error("unknown json exception, url:" + url, e);
                JsonData result = JsonData.fail(DEFAULT_ERROR_MESSAGE);
                mv = new ModelAndView("jsonView", result.toMap());
            }
        } else if (url.endsWith(".jsp")) {
            log.error("unknown page exception, url:" + url, e);
            JsonData result = JsonData.fail(DEFAULT_ERROR_MESSAGE);
            mv = new ModelAndView("exception", result.toMap());
        } else {
            log.error("unknow exception, url:" + url, e);
            JsonData result = JsonData.fail(DEFAULT_ERROR_MESSAGE);
            mv = new ModelAndView("jsonView", result.toMap());
        }

        return mv;
    }
}
