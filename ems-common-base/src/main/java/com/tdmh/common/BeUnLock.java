package com.tdmh.common;

import java.lang.annotation.*;

/**
 * @author Administrator on 2018/12/3.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface BeUnLock {
    String value() default "";
}
