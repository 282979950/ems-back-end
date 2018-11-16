package com.tdmh.exception;

/**
 * 用户自定义异常
 *
 * @author litairan on 2018/11/15.
 */
public class CustomException extends RuntimeException{

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(Throwable cause) {
        super(cause);
    }

    public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
