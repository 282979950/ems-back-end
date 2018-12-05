package com.tdmh.controller;


import com.tdmh.exception.ParameterException;
import com.tdmh.exception.PermissionException;
import com.tdmh.service.IUserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @author Administrator on 2018/12/3.
 */
@Aspect
@Component
public class UnLockAspect {

    @Autowired
    private IUserService userService;

    @Pointcut("execution(public * com.tdmh.controller.*.*Controller.*(..)) && @annotation(com.tdmh.common.BeUnLock)") // 注解声明切点
    public void annotationPointcut() {
    }

    @Before("annotationPointcut()")
    public void before(JoinPoint jp){
       Integer userId = null;
       Object[] objs = jp.getArgs();
       Object o = objs[0];
        try {
            Field field = o.getClass().getDeclaredField("userId");
            field.setAccessible(true);
            userId = (Integer)field.get(o);
        } catch (Exception e) {
           throw new ParameterException("参数错误");
        }
        if(userId == null){ throw new ParameterException("参数为空");}
        Integer isLock = userService.getUserLockStatusById(userId);
        if(isLock!=null && isLock.intValue() == 1){
            throw new PermissionException("该用户已被锁定，不能进行操作");
        }
    }

}
