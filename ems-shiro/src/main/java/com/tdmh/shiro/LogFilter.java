package com.tdmh.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.servlet.AdviceFilter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author Administrator on 2018/11/27.
 */
@Slf4j
public class LogFilter extends AdviceFilter {
    protected boolean preHandle (ServletRequest request, ServletResponse response){
        HttpServletRequest req = (HttpServletRequest)request;
        StringBuffer sb = new StringBuffer();
        Enumeration<String> paramEnums = req.getParameterNames();
        while (paramEnums.hasMoreElements()){
            String paraName = paramEnums.nextElement();
            sb.append(paraName+" : "+req.getParameter(paraName)+", ");
        }
        log.info("============url: "+req.getRequestURI()+" , ip: "+req.getRemoteAddr()+"，传参: "+(sb.length()!=0 ? sb.substring(0,sb.length()-2): null));
        return true;
    }
}
